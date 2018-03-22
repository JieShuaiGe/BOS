package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午8:58:33 <br/>       
 */
@Service
public class FixedAreaServiceImpl implements FixedAreaService{
    @Autowired
   private FixedAreaRepository fixedAreaRepository ;
@Transactional
    @Override
    public void save(FixedArea model) {
          
    fixedAreaRepository.save(model);
        
    }
@Override
public Page<FixedArea> findAll(PageRequest pageable) {
      
    
    return fixedAreaRepository.findAll(pageable);
}
@Autowired
private CourierRepository courierRepository;
@Autowired
private TakeTimeRepository takeTimeRepository;
@Transactional
@Override
public void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId) {
    FixedArea fixedArea = fixedAreaRepository.findOne(id);
    
    Courier courier = courierRepository.findOne(courierId);
    fixedArea.getCouriers().add(courier);
    TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
    courier.setTakeTime(takeTime);
    
    
}
@Autowired
private SubAreaRepository subAreaRepository;
@Transactional
@Override
public void associationSubAreaToFixedArea(Long id, Long subareasId) {
   
    FixedArea fixedArea = fixedAreaRepository.findOne(id);
    SubArea subArea = subAreaRepository.findOne(subareasId);
    subArea.setFixedArea(fixedArea);
    
}
}
  
