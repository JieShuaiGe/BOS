package com.itheima.crm.service;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.itheima.crm.domain.Customer;



/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午4:45:30 <br/>       
 */
@Consumes({ "application/json" })
@Produces({  "application/json" })
public interface CustomerService {
    
    @GET
    @Path("/fingAll")
    List<Customer> findAll();
    
    @GET
    @Path("/findCustomersNotAssociated")
    List<Customer> findCustomersNotAssociated();
    
    @GET
    @Path("/findCustomersAssociatedToFixedArea")
    List<Customer> findCustomersAssociatedToFixedArea( @QueryParam("id") String fixedAreaId);
    
    @PUT
    @Path("/assignCustomers2FixedArea")
    void assignCustomers2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId,@QueryParam("ids") List<Long> ids);
    
    @POST
    @Path("/save")
    void save(Customer customer);
    @PUT
    @Path("/active")
    void active(@QueryParam("telephone") String telephone);
    
    @GET
    @Path("/login")
    Customer login(@QueryParam("telephone") String telephone,@QueryParam("password") String password);
}
  
