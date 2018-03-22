package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;


import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;





/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午8:23:00 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends CommonAction<Courier>{
    @Autowired
    private CourierService courierService;

    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save(){
        courierService.save(model);
        return SUCCESS;
    }
    
    
    @Action(value="courierAction_pageQuery")
    public String pageQuery() throws Exception{
        final String courierNum = model.getCourierNum();
        // 获取收派标准,用户获取标准名字
        final Standard standard = model.getStandard();
        // 获取所属单位
        final String company = model.getCompany();
        // 获取类型
        final String type = model.getType();
        
        Specification<Courier> specification=new Specification<Courier>(){

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                // 如果快递员编号不为空,构造等值查询
                if (StringUtils.isNotEmpty(courierNum)) {
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                // 如果所属单位不为空,构造模糊查询
                if (StringUtils.isNotEmpty(company)) {
                    Predicate p2 = cb.like(root.get("company").as(String.class), company);
                    list.add(p2);
                }
                // 如果类型不为空,构造等值查询
                if (StringUtils.isNotEmpty(type)) {
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }
                // 如果收派标准不为空,构造等值查询
                if (standard != null && StringUtils.isNotEmpty(standard.getName())) {
                    // 根据收派标准的名字进行级联查询
                    Join<Object, Object> join = root.join("standard");
                    Predicate p4 = cb.equal(join.get("name").as(String.class), standard.getName());
                    list.add(p4);
                }
                // 如果集合size为空,return null
                if (list.size() == 0) {
                    return null;
                }
                Predicate[] array = new Predicate[list.size()];
                list.toArray(array);
                // 构造查询条件
                return cb.and(array);
               
            }
            
        };
        PageRequest pageRequest = new PageRequest(page-1, rows);
        Page<Courier> page= courierService.findAll(pageRequest,specification);
       JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});
        return page2json(page, jsonConfig);
      
    }
    private String ids;
    
    public void setIds(String ids) {
        this.ids = ids;
    }
    @Action(value="courierAction_batchDel",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String batchDel() throws IOException{
        courierService.batchDel(ids);
        
        return SUCCESS;
    }
    @Action(value="courierAction_listajax")
    public String listajax() throws Exception{
      List<Courier> list= courierService.findCouriers();
      JsonConfig jsonConfig = new JsonConfig();
      jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});
        return list2json(list, jsonConfig);
    }
}
  
