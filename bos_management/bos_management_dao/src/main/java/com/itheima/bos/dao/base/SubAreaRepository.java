package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:04:12 <br/>       
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long>{

    List<SubArea> findByFixedAreaIsNull();

}
  
