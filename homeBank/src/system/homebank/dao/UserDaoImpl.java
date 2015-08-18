package system.homebank.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import system.homebank.entity.User;
import system.homebank.mapper.UserMapper;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Resource
	UserMapper userMapper;

	@Override
	public User findUserByUsername(String username) {
		return this.userMapper.findUserByUsername(username);
	}

	@Override
	public int passwordCorrect(Map<String, String> param) {
		return this.userMapper.passwordCorrect(param);
	}

	@Override
	public void updatePassword(Map<String, String> param) {
		this.userMapper.updatePassword(param);
	}

	@Override
	public void insert(User user) {
		this.userMapper.insert(user);
	}

	@Override
	public void update(User user) {
		this.userMapper.update(user);
	}

	@Override
	public int isRepeatUsername(String username) {
		return this.userMapper.isRepeatUsername(username);
	}

	@Override
	public List<User> query(Map<String, Object> filter, int start, int rows) {
		RowBounds rb = new RowBounds(start,rows);
	    return this.userMapper.queryPage(filter,rb);
	}

	@Override
	public int getTotal(Map<String, Object> map) {
		return this.userMapper.getTotal(map);
	}

	@Override
	public void runUser(Map<String, Object> map) {
		this.userMapper.runUser(map);
	}

	@Override
	public void deleteUser(Map<String, Object> map) {
		this.userMapper.deleteUser(map);
	}

}
