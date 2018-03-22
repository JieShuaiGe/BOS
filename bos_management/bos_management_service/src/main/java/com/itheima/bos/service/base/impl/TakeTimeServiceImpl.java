package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;

/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 上午11:49:15 <br/>       
 */
@Service
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
   private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findCouriers() {
          
        
        return takeTimeRepository.findAll();
    }
}
  
