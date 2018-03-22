package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:41:24 <br/>       
 */
public interface CourierService {

    void save(Courier courier);

    Page<Courier> findAll(PageRequest pageRequest, Specification<Courier> specification);

    void batchDel(String ids);

    List<Courier> findCouriers();

    

   

}
  
