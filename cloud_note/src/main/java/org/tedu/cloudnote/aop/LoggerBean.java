package org.tedu.cloudnote.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component//ɨ��,�ȼ���<bean>����
@Aspect//�ȼ���<aop:aspect>����
public class LoggerBean {
	
	//�ȼ���<aop:before>
@Before("within(org.tedu.cloudnote.controller..*)")
	public void logController(){
		//��д׷���߼�
		System.out.println(
			"����Controller�����execute��������");
	}
	
//	public void logService(){
//		
//	}
	
}
