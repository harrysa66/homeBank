package system.homebank.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import system.homebank.entity.DataDict;
import system.homebank.entity.Menu;
import system.homebank.mapper.CommonMapper;

@Repository
public class CommonDaoImpl implements CommonDao
{

  @Resource
  private CommonMapper mapper;
  
  @Override
  public List<DataDict> getDatadictList()
  {
    return this.mapper.getDatadictList();
  }
  @Override
  public List<Menu> getAllMenu()
  {
    return this.mapper.getAllMenu();
  }
  @Override
  public List<DataDict> getDatadictCataList(String catalog)
  {
    return this.mapper.getDatadictCataList(catalog);
  }
  @Override
  public void addDatadictData(DataDict model)
  {
    this.mapper.addDatadictData(model);
    
  }
  @Override
  public void delDatadictData(String id)
  {
    this.mapper.delDatadictData(id);
    
  }
  @Override
  public DataDict getDatadictDataById(String id)
  {
    return this.mapper.getDatadictDataById(id);
  }
  @Override
  public void updateDatadictData(DataDict model)
  {
    this.mapper.updateDatadictData(model);
    
  }
  @Override
  public Integer getWeekSum(Map<String,String> map)
  {
    return this.mapper.getWeekSum(map);
  }
  @Override
  public Integer getMonthSum(Map<String, String> map)
  {
    return this.mapper.getMonthSum(map);
  }
  @Override
  public List<Map> getMonthSumByType(String month)
  {
    return this.mapper.getMonthSumByType(month);
  }
  @Override
  public Integer getDayValue(String day)
  {
    return this.mapper.getDayValue(day);
  }
}
