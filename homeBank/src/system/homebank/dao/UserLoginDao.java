package system.homebank.dao;

import java.util.List;
import java.util.Map;

import system.homebank.entity.UserLogin;

public interface UserLoginDao {

	public void insert(UserLogin userLogin);

	public List<UserLogin> query(Map<String, Object> filter, int start, int rows);

	public int getTotal(Map<String, Object> map);

}
