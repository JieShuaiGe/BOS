package com.itheima.bos.web.action.base;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.crm.domain.Customer;

import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午8:54:28 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea>{
   @Autowired 
 private FixedAreaService fixedAreaService;   
    
    @Action(value="fixedAreaAction_save",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String save(){
        fixedAreaService.save(model);
        return SUCCESS;
    }
    @Action(value="fixedAreaAction_pageQuery")
    public String pageQuery() throws Exception{
        PageRequest pageRequest = new PageRequest(page-1, rows);
        Page<FixedArea> page= fixedAreaService.findAll(pageRequest);
      JsonConfig jsonConfig = new  JsonConfig();
      jsonConfig.setExcludes(new String[] { "subareas","couriers" });
      
        return page2json(page, jsonConfig);
    }
    @Action(value="fixedAreaAction_findCustomersNotAssociated")
    public String findCustomersNotAssociated() throws Exception{
        List<Customer> list  = (List<Customer>) WebClient.create("http://localhost:8180/crm_/service/customerService/findCustomersNotAssociated")
        .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        return list2json(list,null);
    }
    @Action(value="fixedAreaAction_findCustomersAssociated")
    public String findCustomersAssociated() throws Exception{
        List<Customer> list  = (List<Customer>) WebClient.create("http://localhost:8180/crm_/service/customerService/findCustomersAssociatedToFixedArea")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).query("id", model.getId()).getCollection(Customer.class);
                return list2json(list,null);
    }
    private List<Long> customerIds;
    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }
    @Action(value="fixedAreaAction_assignCustomers2FixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String assignCustomers2FixedArea(){
        WebClient.create("http://localhost:8180/crm_/service/customerService/assignCustomers2FixedArea")
        .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).query("fixedAreaId", model.getId()).query("ids",customerIds).put(null);
        return SUCCESS;
    }
    private Long courierId;
    private Long takeTimeId;
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    @Action(value="fixedAreaAction_associationCourierToFixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String associationCourierToFixedArea(){
        fixedAreaService.associationCourierToFixedArea(model.getId(),courierId,takeTimeId);
        return SUCCESS;
    }
    private Long subareasId;
    public void setSubareasId(Long subareasId) {
        this.subareasId = subareasId;
    }
    @Action(value="fixedAreaAction_associationSubAreaToFixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String associationSubAreaToFixedArea(){
        fixedAreaService.associationSubAreaToFixedArea(model.getId(),subareasId);
        return SUCCESS;
    }
}
  
