package system.homebank.entity;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import system.homebank.utils.JsonDateSerializer;

public class Payments implements Serializable 
{
  private static final long serialVersionUID = 1256273897024133599L;
  
  private Integer id;
  private String value;
  private String name;
  private String paymenttype;
  private String unit;
  private String unitname;
  private String descript;
  private String day;
  private Date crttime;
  private String type;
  private String typename;
  public Integer getId()
  {
    return id;
  }
  public void setId(Integer id)
  {
    this.id = id;
  }
  public String getValue()
  {
    return value;
  }
  public void setValue(String value)
  {
    this.value = value;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getPaymenttype()
  {
    return paymenttype;
  }
  public void setPaymenttype(String paymenttype)
  {
    this.paymenttype = paymenttype;
  }
  public String getUnit()
  {
    return unit;
  }
  public void setUnit(String unit)
  {
    this.unit = unit;
  }
  public String getUnitname()
  {
    return unitname;
  }
  public void setUnitname(String unitname)
  {
    this.unitname = unitname;
  }
  public String getDescript()
  {
    return descript;
  }
  public void setDescript(String descript)
  {
    this.descript = descript;
  }
  public String getDay()
  {
    return day;
  }
  public void setDay(String day)
  {
    this.day = day;
  }
  @JsonSerialize(using=JsonDateSerializer.class)
  public Date getCrttime()
  {
    return crttime;
  }
  public void setCrttime(Date crttime)
  {
    this.crttime = crttime;
  }
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getTypename()
  {
    return typename;
  }
  public void setTypename(String typename)
  {
    this.typename = typename;
  }
  
}
