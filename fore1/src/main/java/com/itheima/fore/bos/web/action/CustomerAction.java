package com.itheima.fore.bos.web.action;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.crm.domain.Customer;
import com.itheima.utils.MailUtils;
import com.itheima.utils.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午7:08:26 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
   private Customer customer=new Customer();
    @Override
    public Customer getModel() {
       return customer;
    }
   
   @Action("customerAction_sendSMS")
   public String sendSMS(){
       String code = RandomStringUtils.randomNumeric(6);
       ServletActionContext.getRequest().getSession().setAttribute("serverCode", code);
       try {
        SmsUtils.sendSms(customer.getTelephone(), code);
        } catch (ClientException e){
           e.printStackTrace();  
        
        }
       return NONE; 
   }
   private String checkcode;

   public void setCheckcode(String checkcode) {
       this.checkcode = checkcode;
   }
   @Autowired
   private RedisTemplate<String, String> redisTemplate;
   @Action(value = "customerAction_regist", results = {@Result(name = "success", location = "/signup-success.html", type = "redirect"),
           @Result(name = "error", location = "/signup-fail.html", type = "redirect") })
   public String regist(){
       String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("serverCode");
       if (StringUtils.isNotEmpty(serverCode) && StringUtils.isNotEmpty(checkcode) && checkcode.equals(serverCode)){
           WebClient.create("http://localhost:8180/crm_/service/customerService/save")
           .type(MediaType.APPLICATION_JSON)//
           .accept(MediaType.APPLICATION_JSON)//
           .post(customer);
          /* 激活邮箱发送邮件部分*/
           String code = RandomStringUtils.randomNumeric(18);
           redisTemplate.opsForValue().set(customer.getTelephone(), code, 1, TimeUnit.DAYS);
           String emailBody="感谢您注册速运快递 ,请您在24小时内点击下面的链接激活您的帐号<a href='http://localhost:8280/fore1/customerAction_active.action?activeCode="
                            + code + "&telephone=" + customer.getTelephone()+"'>激活链接</a>";
           MailUtils.sendMail(customer.getEmail(), "速运通注册激活", emailBody);
           return SUCCESS;
       }else{
           return ERROR;
       }
   }
   private String activeCode;
   public void setActiveCode(String activeCode) {
    this.activeCode = activeCode;
}
   @Action(value = "customerAction_active", results = {@Result(name = "success", location = "/login.html", type = "redirect"),
           @Result(name = "error", location = "/signup-fail.html", type = "redirect") })
   public String active(){
       String code = redisTemplate.opsForValue().get(customer.getTelephone());
       if (StringUtils.isNotEmpty(activeCode) && StringUtils.isNotEmpty(code) && activeCode.equals(code)){
           WebClient.create(
                   "http://localhost:8180/crm_/service/customerService/active")
                   .type(MediaType.APPLICATION_JSON)
                   .query("telephone", customer.getTelephone())
                   .accept(MediaType.APPLICATION_JSON).put(null);
           return SUCCESS;
       }
       return ERROR;
   }
  
           //customerAction_login
   @Action(value = "customerAction_login", results = {@Result(name = "success", location = "/index.html", type = "redirect"),
           @Result(name = "error", location = "/login.html", type = "redirect") })
   public String login(){
       String telephone = customer.getTelephone();
        String password = customer.getPassword();
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
       if (StringUtils.isNotEmpty(checkcode) && StringUtils.isNotEmpty(code) && checkcode.equals(code)){
           Customer customer2 = WebClient.create(
                   "http://localhost:8180/crm_/service/customerService/login")
                   .type(MediaType.APPLICATION_JSON)
                   .query("telephone", customer.getTelephone())
                   .query("password", customer.getPassword())
                   .accept(MediaType.APPLICATION_JSON).get(Customer.class);
           
           if(customer2!=null){
               ServletActionContext.getRequest().getSession().setAttribute("customer", customer2);
               return SUCCESS; 
           }
        }

       return ERROR; 
   }
}
  
