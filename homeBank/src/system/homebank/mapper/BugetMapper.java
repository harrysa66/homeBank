package system.homebank.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import system.homebank.entity.Buget;

public interface BugetMapper extends BaseMapper
{
  public Buget getBugetByMonth(String month);

  public List<Buget> queryPage(Map<String, Object> filter, RowBounds rb);

  public int getTotal(Map<String, Object> map);

  public void addBuget(Buget model);

  public void deleteBuget(String id);

public Buget getBugetById(String id);

public void updateBuget(Buget model);

public Buget repeatMonth(Buget model); 
}
