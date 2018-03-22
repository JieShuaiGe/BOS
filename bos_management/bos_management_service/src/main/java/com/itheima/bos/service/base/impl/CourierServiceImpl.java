package com.itheima.bos.service.base.impl;

import java.util.List;

import org.quartz.spi.InstanceIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:41:44 <br/>       
 */
@Service
public class CourierServiceImpl implements CourierService{
    @Autowired
    private CourierRepository courierRepository;
    
    
    
    @Transactional
    @Override
    public void save(Courier courier) {
        courierRepository.save(courier); 
       
        
    }



    @Override
    public Page<Courier> findAll(PageRequest pageable,Specification<Courier> specification) {
       
       
        return  courierRepository.findAll(specification,pageable);
    }


    @Transactional
    @Override
    public void batchDel(String ids) {
          
        String[] id = ids.split(",");
        
       for (String string : id) {
           courierRepository.updateDelTagById(Long.parseLong(string));
    }
    }



    @Override
    public List<Courier> findCouriers() {
          
        
        return courierRepository.findByDeltagIsNull();
    }



    

}
  
