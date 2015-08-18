package system.security;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import system.homebank.entity.User;
import system.homebank.service.UserService;

public class UserDetailServiceImpl implements UserDetailsService {
	static Logger log = Logger.getLogger(UserDetailServiceImpl.class.getName());

	@Resource
	private UserService userService;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		User user = userService.findUserByUsername(username);
		if ((user == null)
				|| ("N".equals(user.getIsvalid()))
				|| ("DELETED".equals(user.getStatus()))) {
			throw new UsernameNotFoundException("登录名错误, username=" + username);
		}
		user.addAuthoritie("ROLE_USER");
		log.info("登录名：" + user.toString());
		return user;
	}

}
