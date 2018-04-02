package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:03:02 <br/>       
 */
@Service
public class SubAreaServiceImpl implements SubAreaService{
    @Autowired
  private SubAreaRepository subAreaRepository;
@Transactional
    @Override
    public void save(SubArea model) {
          
        subAreaRepository.save(model) ;
        
    }
@RequiresPermissions("SubAreafindAll")
@Override
public Page<SubArea> findAll(PageRequest pageable) {
     
    return subAreaRepository.findAll(pageable);
}
@Override
public List<SubArea> findCouriers() {
      
    
    return subAreaRepository.findByFixedAreaIsNull();
}
}
  
