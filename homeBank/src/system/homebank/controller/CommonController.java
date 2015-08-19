package system.homebank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import system.homebank.entity.DataDict;
import system.homebank.service.CommonService;

@Controller
@RequestMapping("/commonController")
public class CommonController
{
  @Resource
  private CommonService service;
  
  @RequestMapping("/listDatadict.do")
  @ResponseBody
  public Object getDatadictList(Model model)
  {
    return this.service.getDatadictList();
  }
  @RequestMapping("/listDatadictCata.do")
  @ResponseBody
  public Object getDatadictCataList(@RequestParam String catalog)
  {
    return this.service.getDatadictCataList(catalog);
  }
  @RequestMapping("/addDatadictData.do")
  @ResponseBody
  public Object addDatadictData(DataDict model)
  {
    this.service.addDatadictData(model);
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("success", true);
    map.put("msg", "添加成功！");
    return map;
  }
  @RequestMapping("/deleteDatadictData.do")
  @ResponseBody
  public Object delDatadictData(@RequestParam String id)
  {
    this.service.delDatadictData(id);
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("success", true);
    map.put("msg", "删除成功！");
    return map;
  }
  @RequestMapping("/preUpdateDatadictData.do")
  public Object preUpdateDatadictData(@RequestParam String id,Model model)
  {
    DataDict data = this.service.getDatadictDataById(id);
    model.addAttribute("entity", data);
    return "/preupdatedatadictdata";
  }
  @RequestMapping("/updateDatadictData.do")
  @ResponseBody
  public Object updateDatadictData(DataDict model)
  {
    this.service.updateDatadictData(model);
    Map<String,Object> map = new HashMap<String,Object>();
    map.put("success", true);
    map.put("msg", "修改成功！");
    return map;
  }
  
}
