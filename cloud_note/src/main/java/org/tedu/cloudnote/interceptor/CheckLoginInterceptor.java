package org.tedu.cloudnote.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CheckLoginInterceptor 
implements HandlerInterceptor{

	//�ڵ���Controller֮ǰִ��
	public boolean preHandle(
		HttpServletRequest request, 
		HttpServletResponse response, Object handler)
			throws Exception {
		//��ȡ����������������Ϣ(userId��token)
		String author = request.getHeader("author");
		//���û��,��ֹ����ִ��
		if(author==null||"".equals(author)){
			return false;
		}
		//TODO �����userId��token,����db���
		//����������,��ֹ����ִ��return false
		//������ͨ��,����
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
