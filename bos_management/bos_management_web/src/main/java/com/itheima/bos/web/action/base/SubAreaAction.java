package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
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
import com.itheima.utils.FileDownloadUtils;

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
      jsonConfig.setExcludes(new String[] { "subareas","couriers" });
      
        return page2json(page, jsonConfig);
    }
    @Action(value="subAreaAction_listajax")
    public String listajax() throws Exception{
      List<SubArea> list= subAreaService.findCouriers();
      JsonConfig jsonConfig = new JsonConfig();
      jsonConfig.setExcludes(new String[] {"fixedArea", "area"});
        return list2json(list, jsonConfig);
    }
    @Action("subAreaAction_exportExcel")
    public String exportExcel() throws IOException {
        Page<SubArea> page= subAreaService.findAll(null);
        List<SubArea> list = page.getContent();
        // 在内存中创建Excel文件
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // 创建工作簿
        HSSFSheet sheet = hssfWorkbook.createSheet("分区数据统计");
        // 创建第1行,即表头
        HSSFRow titleRow = sheet.createRow(0);
        // 创建列
        titleRow.createCell(0).setCellValue("分区编号");
        titleRow.createCell(1).setCellValue("分区开始编号");
        titleRow.createCell(2).setCellValue("分区结束编号");
        titleRow.createCell(3).setCellValue("分区关键字");
        titleRow.createCell(4).setCellValue("辅助关键字");
        titleRow.createCell(5).setCellValue("区域信息");
        for (SubArea subArea : list) {
            // 创建数据行
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(subArea.getId());
            dataRow.createCell(1).setCellValue(subArea.getStartNum());
            dataRow.createCell(2).setCellValue(subArea.getEndNum());
            dataRow.createCell(3).setCellValue(subArea.getKeyWords());
            dataRow.createCell(4).setCellValue(subArea.getAssistKeyWords());
            dataRow.createCell(5).setCellValue(subArea.getArea().getName());
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();
        String fileName = "统计数据.xls";
        // 从请求头中获取客户端类型
        String agent = ServletActionContext.getRequest().getHeader("User-Agent");
        // 对文件名进行重新编码,避免乱码
        fileName = FileDownloadUtils.encodeDownloadFilename(fileName, agent);
        // 指定以附件的形式处理文件
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentType(ServletActionContext.getServletContext().getMimeType(fileName));
        // 写出文件
        hssfWorkbook.write(outputStream);
        // 释放资源
        hssfWorkbook.close();
        return NONE;
    }
}
  
