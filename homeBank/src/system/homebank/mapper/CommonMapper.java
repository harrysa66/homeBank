package system.homebank.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import system.homebank.entity.DataDict;
import system.homebank.entity.Menu;

public interface CommonMapper  extends BaseMapper
{
  public List<DataDict> getDatadictList();
  public List<Menu> getAllMenu();
  public List<DataDict> getDatadictCataList(String catalog);
  public void addDatadictData(DataDict model);
  public void delDatadictData(String id);
  public DataDict getDatadictDataById(String id);
  public void updateDatadictData(DataDict model);
  public Integer getWeekSum(Map<String,String> map);
  public Integer getMonthSum(Map<String, String> map);
  public List<Map> getMonthSumByType(String month);
  public Integer getDayValue(String day);
}
