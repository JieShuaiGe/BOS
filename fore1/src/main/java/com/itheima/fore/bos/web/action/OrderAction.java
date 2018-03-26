package com.itheima.fore.bos.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:48:56 <br/>       
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
private Order order=new Order();
    @Override
    public Order getModel(){
       return order;
    }
    private String sendAreaInfo;
    private String recAreaInfo;
    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }
    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }
   
    @Action(value = "orderAction_add",results ={@Result(name = "success",
            location = "/index.html", type = "redirect")})
    public String  add(){
        if(StringUtils.isNotEmpty(sendAreaInfo)){
            String[] split = sendAreaInfo.split("/");
            Area area = new Area();
            area.setProvince(split[0].substring(0, split[0].length()-1));
            area.setCity(split[1].substring(0, split[1].length()-1));
            area.setDistrict(split[2].substring(0, split[2].length()-1));
            order.setSendArea(area);
        }
        if(StringUtils.isNotEmpty(recAreaInfo)){
            String[] split = recAreaInfo.split("/");
            Area area = new Area();
            area.setProvince(split[0].substring(0, split[0].length()-1));
            area.setCity(split[1].substring(0, split[1].length()-1));
            area.setDistrict(split[2].substring(0, split[2].length()-1));
            order.setRecArea(area);
        }
        WebClient.create("http://localhost:8080/bos_management_web/service/order/save").accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON).post(order);
       return SUCCESS; 
    }
}
  
