package system.homebank.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.homebank.dao.BugetDao;
import system.homebank.entity.Buget;
import system.homebank.entity.Payments;
import system.homebank.model.Page;

@Service
public class BugetServiceImpl implements BugetService
{
  @Resource
  BugetDao dao;

  @Override
  public Buget getBuget(String month)
  {
    if (month == null || month.equals(""))
      return null;
    return this.dao.getBuget(month);
  }

  @Override
  public Page query(Map<String, Object> filter)
  {
    int pageno = Integer.parseInt(filter.get("page").toString());
    int rows = Integer.parseInt(filter.get("rows").toString());
    int start = (pageno-1)*rows;
    
    filter.remove("page");
    filter.remove("rows");
    
    Map<String, Object> map = new HashMap<String, Object>();
    for (String o : filter.keySet())
    {
      if (filter.get(o) == null || filter.get(o).equals(""))
        continue;
      map.put(o, filter.get(o));
    }
    List<Buget> list = this.dao.query(map,start,rows);
    int total = this.dao.getTotal(map);
    Page page = new Page();
    page.setRows(list);
    page.setTotal(total);
    return page;
  }

  @Override
  @Transactional
  public void addBuget(Buget model)
  {
    this.dao.addBuget(model);
    
  }

  @Override
  @Transactional
  public void deleteBuget(String id)
  {
    this.dao.deleteBuget(id);
    
  }

@Override
public Buget getBugetById(String id) {
	return this.dao.getBugetById(id);
}

@Override
@Transactional
public void updateBuget(Buget model) {
	this.dao.updateBuget(model);
}
}
