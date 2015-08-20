package system.homebank.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import system.homebank.dao.UserLoginDao;
import system.homebank.entity.UserLogin;
import system.homebank.model.Page;
import system.homebank.utils.IPUtil;
import system.homebank.utils.UUIDGenerator;

@Service
public class UserLoginServiceImpl implements UserLoginService {
	
	@Resource
	private UserLoginDao userLoginDao;

	@Override
	public void insertLogin(String username, HttpServletRequest request) throws IOException {
		UserLogin userLogin = new UserLogin();
		userLogin.setId(UUIDGenerator.getUUID());
		userLogin.setUsername(username);
		String ip = IPUtil.getIp(request);
		userLogin.setIp(ip);
		String ipAddress = IPUtil.getIpAddress(ip);
		userLogin.setIpAddress(ipAddress);
		userLogin.setLoginTime(new Date());
		userLoginDao.insert(userLogin);
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
	    List<UserLogin> list = userLoginDao.query(map,start,rows);
	    int total = userLoginDao.getTotal(map);
	    Page page = new Page();
	    page.setRows(list);
	    page.setTotal(total);
	    return page;
	}

}
