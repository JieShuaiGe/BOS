package com.itheima.crm.service.impl;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;



/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午4:46:02 <br/>       
 * @param <Customer>
 */
@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
   private CustomerRepository customerRepository;
    
    
   public  List<Customer> findAll(){
       
       return customerRepository.findAll();
   }


@Override
public List<Customer> findCustomersNotAssociated() {
      
    
    return customerRepository.findByFixedAreaIdIsNull();
}


@Override
public List<Customer> findCustomersAssociatedToFixedArea(String fixedAreaId){
      
      
    return customerRepository.findByFixedAreaId(fixedAreaId);
}

@Transactional
@Override
public void assignCustomers2FixedArea(String fixedAreaId, List<Long> ids) {
      
    customerRepository.updateFixedAreaNullByFixedAreaId(fixedAreaId);
    for (Long id : ids) {
        customerRepository.updateFixedAreaIdByCustomerId(fixedAreaId, id); 
    }
}

@Transactional
@Override
public void save(Customer customer) {
    customerRepository.save(customer);
    
    
}

@Transactional
@Override
public void active(String telephone) {
    customerRepository.active(telephone);
    
    
}


@Override
public Customer login(String telephone, String password) {
      
    
    return customerRepository.login(telephone,password); 
}
}
  
