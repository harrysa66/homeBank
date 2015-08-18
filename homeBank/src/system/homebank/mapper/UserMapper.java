package system.homebank.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import system.homebank.entity.User;

public interface UserMapper extends BaseMapper{

	public User findUserByUsername(String username);

	public int passwordCorrect(Map<String, String> param);

	public void updatePassword(Map<String, String> param);

	public void insert(User user);

	public void update(User user);

	public int isRepeatUsername(String username);

	public List<User> queryPage(Map<String, Object> filter, RowBounds rb);

	public int getTotal(Map<String, Object> map);

	public void runUser(Map<String, Object> map);

	public void deleteUser(Map<String, Object> map);

}
