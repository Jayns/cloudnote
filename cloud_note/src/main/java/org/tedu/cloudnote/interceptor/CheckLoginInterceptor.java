package org.tedu.cloudnote.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CheckLoginInterceptor 
implements HandlerInterceptor{

	//在调用Controller之前执行
	public boolean preHandle(
		HttpServletRequest request, 
		HttpServletResponse response, Object handler)
			throws Exception {
		//获取请求带过来的身份信息(userId和token)
		String author = request.getHeader("author");
		//如果没有,阻止后续执行
		if(author==null||"".equals(author)){
			return false;
		}
		//TODO 如果有userId和token,进入db检查
		//如果检查有误,阻止后续执行return false
		//如果检查通过,放行
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
