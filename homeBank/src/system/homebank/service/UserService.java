package system.homebank.service;

import java.util.Map;

import system.homebank.entity.User;
import system.homebank.model.Page;

public interface UserService {
	
	public User findUserByUsername(String username);

	public Map<String, Object> updatePassword(Map<String, String> param);

	public Map<String, Object> updateUser(User user);

	public Page query(Map<String, Object> filter);

	public Map<String, Object> runUser(String username);

	public Map<String, Object> deleteUser(String id);

}
