package system.homebank.dao;

import java.util.List;
import java.util.Map;

import system.homebank.entity.Payments;
import system.homebank.model.Page;

public interface PaymentsDao
{

  List<Payments> query(Map<String, Object> filter, int start, int rows);

  int getTotal(Map<String, Object> map);

  void addPayments(Payments model);

  void delPayments(String id);

  Payments getPaymentsById(String id);

  void updatePayments(Payments model);
  
}
