package com.itheima.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午3:58:47 <br/>       
 */
@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype") 
@Controller 
public class SubAreaAction extends CommonAction<SubArea>{
    @Autowired
private SubAreaService subAreaService;
    
    @Action(value="subareaAction_save",results={@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
    public String save(){
        subAreaService.save(model);
         return SUCCESS;
    }
    @Action(value="subareaAction_pageQuery")
    public String pageQuery() throws Exception{
        PageRequest pageRequest = new PageRequest(page-1, rows);
        Page<SubArea> page= subAreaService.findAll(pageRequest);
      JsonConfig jsonConfig = new  JsonConfig();
      jsonConfig.setExcludes(new String[] { "subareas" });
      
        return page2json(page, jsonConfig);
    }
    @Action(value="subAreaAction_listajax")
    public String listajax() throws Exception{
      List<SubArea> list= subAreaService.findCouriers();
      JsonConfig jsonConfig = new JsonConfig();
      jsonConfig.setExcludes(new String[] {"fixedArea", "area"});
        return list2json(list, jsonConfig);
    }
}
  
