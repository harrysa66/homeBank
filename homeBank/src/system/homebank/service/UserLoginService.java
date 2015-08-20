package system.homebank.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import system.homebank.model.Page;

public interface UserLoginService {

	public void insertLogin(String username, HttpServletRequest request) throws IOException;
	
	public Page query(Map<String, Object> filter);

}
