package system.homebank.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import system.homebank.entity.Payments;
import system.homebank.mapper.PaymentsMapper;
import system.homebank.model.Page;

@Repository
public class PaymentsDaoImpl implements PaymentsDao
{
  @Resource
  private PaymentsMapper maper;

  @Override
  public List<Payments> query(Map<String, Object> filter, int start, int rows)
  {
    RowBounds rb = new RowBounds(start,rows);
    return this.maper.queryPage(filter,rb);
  }

  @Override
  public int getTotal(Map<String, Object> map)
  {
    return this.maper.getTotal(map);
  }

  @Override
  public void addPayments(Payments model)
  {
    this.maper.addPayments(model);
    
  }

  @Override
  public void delPayments(String id)
  {
    this.maper.delPayments(id);
    
  }

  @Override
  public Payments getPaymentsById(String id)
  {
    return this.maper.getPaymentsById(id);
  }

  @Override
  public void updatePayments(Payments model)
  {
    this.maper.updatePayments(model);
    
  }
}
