package system.homebank.entity;

import java.io.Serializable;

public class DataDict implements Serializable 
{
  private static final long serialVersionUID = -2692788275202369407L;
  
  private Integer id;
  private String catalog;
  private String code;
  private String codename;
  public Integer getId()
  {
    return id;
  }
  public void setId(Integer id)
  {
    this.id = id;
  }
  public String getCatalog()
  {
    return catalog;
  }
  public void setCatalog(String catalog)
  {
    this.catalog = catalog;
  }
  public String getCode()
  {
    return code;
  }
  public void setCode(String code)
  {
    this.code = code;
  }
  public String getCodename()
  {
    return codename;
  }
  public void setCodename(String codename)
  {
    this.codename = codename;
  }
  
  
}
