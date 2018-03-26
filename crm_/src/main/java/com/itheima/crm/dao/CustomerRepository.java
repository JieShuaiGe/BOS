package com.itheima.crm.dao;

import java.util.List;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午4:43:20 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>{

    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId =null where fixedAreaId = ?")
    void updateFixedAreaNullByFixedAreaId(String fixedAreaId);

    @Modifying
    @Query("update Customer set fixedAreaId =? where id = ?")
    void updateFixedAreaIdByCustomerId(String fixedAreaId, Long id);
    
    @Modifying
    @Query("update Customer set type =1 where telephone = ?")
    void active(String telephone);
    
    @Query(" from Customer where telephone = ? and password = ?")
    Customer login(String telephone, String password);
    
    @Query("select fixedAreaId from Customer where address = ?")
    String findFixedAreaIdByAdddress(String address);
    
   
}
  
