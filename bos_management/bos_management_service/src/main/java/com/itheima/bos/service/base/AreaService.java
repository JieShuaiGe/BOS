package com.itheima.bos.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:08:17 <br/>       
 */
public interface AreaService {

    void save(ArrayList<Area> list);

    Page<Area> findAll(PageRequest pageRequest);

    List<Area> fingQ(String q);

    List<Object[]> exportCharts();

}
  
