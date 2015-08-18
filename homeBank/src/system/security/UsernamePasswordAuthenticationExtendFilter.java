package system.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import system.exception.ValidateCodeException;

public class UsernamePasswordAuthenticationExtendFilter extends
		UsernamePasswordAuthenticationFilter {
	private String validateCodeParameter = "vercode";
	private String checkCodeParameter = "checkCode";
	private boolean openValidateCode = false;

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (isOpenValidateCode()) {
			checkValidateCode(request);
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		setDetails(request, authRequest);

		return getAuthenticationManager().authenticate(authRequest);
	}

	public void checkValidateCode(HttpServletRequest request) {
		String jcaptchaCode = obtainValidateCodeParameter(request)
				.toUpperCase();
		String checkCode = request.getSession().getAttribute("rand_verifyCode")
				.toString().toUpperCase();
		if ((jcaptchaCode == null) || ("".equals(jcaptchaCode)))
			throw new ValidateCodeException("请输入验证码!");
		if (!(checkCode.equals(jcaptchaCode)))
			throw new ValidateCodeException("验证码不正确,请重新输入!");
	}

	public String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(getValidateCodeParameter());
		return ((obj == null) ? "" : obj.toString().trim());
	}

	public String obtainCheckCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(getCheckCodeParameter());
		return ((obj == null) ? "" : obj.toString().trim());
	}

	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(getUsernameParameter());
		return ((obj == null) ? "" : obj.toString().trim());
	}

	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(getPasswordParameter());
		return ((obj == null) ? "" : obj.toString().trim());
	}

	public String getValidateCodeParameter() {
		return this.validateCodeParameter;
	}

	public void setValidateCodeParameter(String validateCodeParameter) {
		this.validateCodeParameter = validateCodeParameter;
	}

	public String getCheckCodeParameter() {
		return this.checkCodeParameter;
	}

	public void setCheckCodeParameter(String checkCodeParameter) {
		this.checkCodeParameter = checkCodeParameter;
	}

	public boolean isOpenValidateCode() {
		return this.openValidateCode;
	}

	public void setOpenValidateCode(boolean openValidateCode) {
		this.openValidateCode = openValidateCode;
	}

}
