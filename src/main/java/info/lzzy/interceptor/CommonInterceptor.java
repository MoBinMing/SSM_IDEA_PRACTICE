package info.lzzy.interceptor;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class CommonInterceptor
  extends HandlerInterceptorAdapter
{
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception
  {
    String requstUrl = request.getRequestURI();
    String contextPath = request.getContextPath();
    //String url = requstUrl.substring(contextPath.length());
    String role=null;
    if(request.getSession().getAttribute("role")!=null) {
    	role=(String) request.getSession().getAttribute("role");
    }
    if (role!=null) {
        if (requstUrl.contains(contextPath+"/"+role+"/")) {
          if (request.getSession().getAttribute("user")!=null){
            return true;
          }
        }
    }
    Enumeration em = request.getSession().getAttributeNames();
    while (em.hasMoreElements()) {
      request.getSession().removeAttribute(em.nextElement().toString());
    }
    response.sendRedirect("/Practice");
    return false;
  }
  
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception
  {}
  
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    throws Exception
  {}
}
