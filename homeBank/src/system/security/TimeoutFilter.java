package system.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

public class TimeoutFilter extends GenericFilterBean
{
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException
  {
    HttpServletRequest httpServletRequest = (HttpServletRequest)request;
    if (httpServletRequest.getSession(false) == null) {
      String basePath = String.format("%s://%s:%s/", new Object[] { httpServletRequest.getScheme(), httpServletRequest.getServerName(), Integer.valueOf(httpServletRequest.getServerPort()) });
      String url = String.format("%s%s%s", new Object[] { basePath, httpServletRequest.getContextPath(), "/j_spring_cas_security_logout" });
      HttpServletResponse httpServletResponse = (HttpServletResponse)response;
      httpServletResponse.sendRedirect(url);
    }
    chain.doFilter(request, response);
  }
}
