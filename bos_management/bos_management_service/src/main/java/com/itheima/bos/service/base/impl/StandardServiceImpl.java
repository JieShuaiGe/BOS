package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午4:41:33 <br/>       
 */

@Service
public class StandardServiceImpl implements StandardService{
    @Autowired
private StandardRepository standardRepository;
    @Transactional
    @Override
    public void save(Standard standard){
        standardRepository.save(standard);
       
        
    }
    @Override
    public Page<Standard> findAll(PageRequest pageable) {
          
           return standardRepository.findAll(pageable);
    }

}
  
