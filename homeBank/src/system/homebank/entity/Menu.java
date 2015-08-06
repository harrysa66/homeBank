package system.homebank.entity;

import java.io.Serializable;

public class Menu implements Serializable 
{
  private static final long serialVersionUID = -2589245519866726792L;
  private Integer id;
  private String menuname;
  private String url;
  private Integer parentid;
  public Integer getId()
  {
    return id;
  }
  public void setId(Integer id)
  {
    this.id = id;
  }
  public String getMenuname()
  {
    return menuname;
  }
  public void setMenuname(String menuname)
  {
    this.menuname = menuname;
  }
  public String getUrl()
  {
    return url;
  }
  public void setUrl(String url)
  {
    this.url = url;
  }
  public Integer getParentid()
  {
    return parentid;
  }
  public void setParentid(Integer parentid)
  {
    this.parentid = parentid;
  }
  
  
}
