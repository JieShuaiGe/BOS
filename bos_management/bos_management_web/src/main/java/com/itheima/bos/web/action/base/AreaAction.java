package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

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
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.web.action.CommonAction;
import com.itheima.utils.PinYin4jUtils;



import net.sf.json.JsonConfig;

/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:01:35 <br/>       
 */

@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype") 
@Controller 
public class AreaAction extends CommonAction<Area>{
    @Autowired
    private AreaService areaService;
    private File areaFile;
    

public void setAreaFile(File areaFile) {
        this.areaFile = areaFile;
    }


@Action(value="areaAction_importXLS",results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
public String importXLS() throws Exception{
    HSSFWorkbook workbook= new HSSFWorkbook(new FileInputStream(areaFile));
    HSSFSheet sheet = workbook.getSheetAt(0);
    ArrayList<Area> list = new ArrayList<>();
        for (Row row : sheet) {
           if(row.getRowNum()==0){
               continue;
           }
           String province = row.getCell(1).getStringCellValue();
           String city = row.getCell(2).getStringCellValue();
           String district = row.getCell(3).getStringCellValue();
           String postcode = row.getCell(4).getStringCellValue();
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            String citycode = PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
            String[] string = PinYin4jUtils.getHeadByString(province + city + district);
            String shortcode = PinYin4jUtils.stringArrayToString(string);
            Area area = new Area(null, province, city, district, postcode, citycode, shortcode, null);
           
            list.add(area);
        }
   
        areaService.save(list);
        workbook.close();
         return SUCCESS;
}

@Action(value="areaAction_pageQuery")
  public String pageQuery() throws Exception{
    PageRequest pageRequest = new PageRequest(page-1, rows);
    Page<Area> page= areaService.findAll(pageRequest);
    JsonConfig jsonConfig = new JsonConfig();
    jsonConfig.setExcludes(new String[] { "subareas" });
    return page2json(page, jsonConfig);
   
}
private String q;

public void setQ(String q) {
    this.q = q;
}


@Action(value="areaAction_findAll")
public String findAll() throws Exception{
    List<Area> content;
    if(StringUtils.isNotEmpty(q)){
        content=areaService.fingQ(q);
    }else{
        Page<Area> page= areaService.findAll(null);
        content = page.getContent();
    }
    
    JsonConfig jsonConfig = new JsonConfig();
    jsonConfig.setExcludes(new String[] { "subareas" });
    return list2json(content, jsonConfig);
    
}
//获取HighCharts图表数据
@Action(value = "areaAction_exportCharts")
public String exportCharts() throws Exception {
    List<Object[]> list = areaService.exportCharts();
    
    return list2json(list, null);
}


}
  
