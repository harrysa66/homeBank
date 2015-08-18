package system.homebank.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.homebank.dao.UserDao;
import system.homebank.entity.User;
import system.homebank.model.Page;
import system.homebank.utils.Constants;
import system.homebank.utils.MD5Util;
import system.homebank.utils.UUIDGenerator;

@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	UserDao userDao;

	@Override
	public User findUserByUsername(String username) {
		return this.userDao.findUserByUsername(username);
	}

	@Override
	@Transactional
	public Map<String, Object> updatePassword(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (userDao.passwordCorrect(param) > 0) {
			userDao.updatePassword(param);
			result.put("success", true);
			result.put("msg", "修改密码成功");
			return result;
		} else {
			result.put("success", false);
			result.put("msg", "原密码错误");
			return result;
		}
	}

	@Override
	@Transactional
	public Map<String, Object> updateUser(User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isEmpty(user.getId())) {
			if(userDao.isRepeatUsername(user.getUsername()) > 0){
				result.put("success", false);
				result.put("msg", "用户名已存在");
				return result;
			}else{
				user.setId(UUIDGenerator.getUUID());
				user.setPassword(MD5Util.MD5(Constants.INIT_PASSWORD));
				user.setCreateTime(new Date());
				user.setStatus(Constants.STATUS_DEFAULT);
				user.setIsvalid(Constants.ISVALIAD_SHOW);
				userDao.insert(user);
				result.put("success", true);
				result.put("msg", "添加成功");
				return result;
			}
		}else{
			user.setModifyTime(new Date());
			userDao.update(user);
			result.put("success", true);
			result.put("msg", "修改成功");
			return result;
		}
	}

	@Override
	public Page query(Map<String, Object> filter) {
		int pageno = Integer.parseInt(filter.get("page").toString());
	    int rows = Integer.parseInt(filter.get("rows").toString());
	    int start = (pageno-1)*rows;
	    
	    filter.remove("page");
	    filter.remove("rows");
	    
	    Map<String, Object> map = new HashMap<String, Object>();
	    for (String o : filter.keySet())
	    {
	      if (filter.get(o) == null || filter.get(o).equals(""))
	        continue;
	      map.put(o, filter.get(o));
	    }
	    List<User> list = userDao.query(map,start,rows);
	    int total = userDao.getTotal(map);
	    Page page = new Page();
	    page.setRows(list);
	    page.setTotal(total);
	    return page;
	}

	@Override
	@Transactional
	public Map<String, Object> runUser(String username) {
		Map<String, Object> result = new HashMap<String, Object>();
		String message = "";
		User user = findUserByUsername(username);
		String isvalid = user.getIsvalid();
		if ((isvalid == Constants.ISVALIAD_SHOW)
				|| (Constants.ISVALIAD_SHOW.equals(isvalid))) {
			isvalid = Constants.ISVALIAD_HIDDEN;
			message = "禁用成功";
		} else if ((isvalid == Constants.ISVALIAD_HIDDEN)
				|| (Constants.ISVALIAD_HIDDEN.equals(isvalid))) {
			isvalid = Constants.ISVALIAD_SHOW;
			message = "启用成功";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", user.getId());
		map.put("isvalid", isvalid);
		userDao.runUser(map);
		result.put("success", true);
		result.put("msg", message);
		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> deleteUser(String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", Constants.STATUS_DELETED);
		userDao.deleteUser(map);
		result.put("success", true);
		result.put("msg", "删除成功");
		return result;
	}

}
