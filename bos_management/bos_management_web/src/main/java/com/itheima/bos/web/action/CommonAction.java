package com.itheima.bos.web.action;


import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.itheima.bos.domain.base.Standard;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommonAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午8:23:46 <br/>       
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T>{
    protected T model;
private Class<T> clazz;
    @Override
    public T getModel() {
        
        try {
            model = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();  
          }
        return model;
    }
  public CommonAction(){
   ParameterizedType pt=(ParameterizedType) this.getClass().getGenericSuperclass();
       clazz=  (Class) pt.getActualTypeArguments()[0];
  }
  protected int page;// 第几页
  protected int rows;

  public void setPage(int page) {
      this.page = page;
  }
  public void setRows(int rows) {
      this.rows = rows;
  } 
  public String page2json(Page<T> page,JsonConfig jsonConfig) throws Exception{
      long total = page.getTotalElements();
      List<T> content = page.getContent();
      Map<String, Object> map = new HashMap<String,Object>();
      map.put("total", total);
      map.put("rows", content);
      String json;

      if (jsonConfig != null) {
          json = JSONObject.fromObject(map, jsonConfig).toString();
      } else {
          json = JSONObject.fromObject(map).toString();
      }
      HttpServletResponse response = ServletActionContext.getResponse();
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write( json);
      return NONE;
  }
  public String list2json(List list,JsonConfig jsonConfig) throws Exception{
      String json;
      if (jsonConfig != null) {
          json = JSONArray.fromObject(list, jsonConfig).toString();
      } else {
          json = JSONArray.fromObject(list).toString();
      }
      HttpServletResponse response = ServletActionContext.getResponse();
      response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(json);
      return NONE;
  }
}
  
