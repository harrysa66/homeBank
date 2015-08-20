package system.homebank.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import system.homebank.entity.UserLogin;

public interface UserLoginMapper extends BaseMapper {

	public void insert(UserLogin userLogin);

	public List<UserLogin> queryPage(Map<String, Object> filter, RowBounds rb);

	public int getTotal(Map<String, Object> map);

}
