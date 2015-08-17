package system.homebank.service;

import java.util.Map;

import system.homebank.entity.Buget;
import system.homebank.model.Page;

public interface BugetService
{
  public Buget getBuget(String month);

  public Page query(Map<String, Object> filter);

  public void addBuget(Buget model);

  public void deleteBuget(String id);

public Buget getBugetById(String id);

public void updateBuget(Buget model);
}
