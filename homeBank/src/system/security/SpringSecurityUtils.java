package system.security;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import system.homebank.entity.User;

public class SpringSecurityUtils {
	public static User getCurrentUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (!(principal instanceof UserDetails)) {
			return null;
		}
		return ((User) principal);
	}

	public static String getCurrentUserName() {
		Authentication authentication = getAuthentication();
		if ((authentication == null) || (authentication.getPrincipal() == null)) {
			return "";
		}
		return authentication.getName();
	}

	public static String getCurrentUserIp() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return "";
		}
		Object details = authentication.getDetails();
		if (!(details instanceof WebAuthenticationDetails)) {
			return "";
		}
		WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
		return webDetails.getRemoteAddress();
	}

	public static boolean hasAnyAuthority(String[] authorities) {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return false;
		}
		Collection<GrantedAuthority> grantedAuthorityList = (Collection<GrantedAuthority>) authentication.getAuthorities();
		for (String authority : authorities) {
			for (GrantedAuthority grantedAuthority : grantedAuthorityList) {
				if (authority.equals(grantedAuthority.getAuthority())) {
					return true;
				}
			}
		}
		return false;
	}

	public static void saveUserDetailsToContext(UserDetails userDetails,
			HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
				userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		if (request != null) {
			authentication.setDetails(new WebAuthenticationDetails(request));
		}
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null) {
			return null;
		}
		return context.getAuthentication();
	}

	public static Map<String, Object> getExtend(String key) {
		return ((Map) getCurrentUser().getExtend().get(key));
	}

	public static Map<String, Object> addExtend(String key, Object obj) {
		return ((Map) getCurrentUser().getExtend().put(key, obj));
	}

	public static void clearExtend() {
		getCurrentUser().getExtend().clear();
	}
}
