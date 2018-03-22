package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午8:58:08 <br/>       
 */
public interface FixedAreaService {

    void save(FixedArea model);

    Page<FixedArea> findAll(PageRequest pageRequest);

    void associationCourierToFixedArea(Long id, Long courierId, Long takeTimeId);

    void associationSubAreaToFixedArea(Long id, Long subareasId);

}
  
