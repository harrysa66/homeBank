package system.homebank.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import system.homebank.entity.UserLogin;
import system.homebank.mapper.UserLoginMapper;

@Repository
public class UserLoginDaoImpl implements UserLoginDao {
	
	@Resource
	private UserLoginMapper userLoginMapper;

	@Override
	public void insert(UserLogin userLogin) {
		userLoginMapper.insert(userLogin);
	}

	@Override
	public List<UserLogin> query(Map<String, Object> filter, int start, int rows) {
		RowBounds rb = new RowBounds(start,rows);
	    return userLoginMapper.queryPage(filter,rb);
	}

	@Override
	public int getTotal(Map<String, Object> map) {
		return userLoginMapper.getTotal(map);
	}

}
