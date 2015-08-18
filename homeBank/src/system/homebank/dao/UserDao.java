package system.homebank.dao;

import java.util.List;
import java.util.Map;

import system.homebank.entity.User;

public interface UserDao {

	public User findUserByUsername(String username);

	public int passwordCorrect(Map<String, String> param);

	public void updatePassword(Map<String, String> param);

	public void insert(User user);

	public void update(User user);

	public int isRepeatUsername(String username);

	public List<User> query(Map<String, Object> map, int start, int rows);

	public int getTotal(Map<String, Object> map);

	public void runUser(Map<String, Object> map);

	public void deleteUser(Map<String, Object> map);

}
