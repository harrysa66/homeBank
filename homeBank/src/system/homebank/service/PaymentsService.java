package system.homebank.service;

import java.util.List;
import java.util.Map;

import system.homebank.entity.Payments;
import system.homebank.model.Page;

public interface PaymentsService
{

  Page query(Map<String, Object> filter);

  void addPayments(Payments model);

  void delPayments(String id);

  Payments getPaymentsById(String id);

  void updatePayments(Payments model);
  
}
