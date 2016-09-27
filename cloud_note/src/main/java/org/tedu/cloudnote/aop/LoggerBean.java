package org.tedu.cloudnote.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component//扫描,等价于<bean>定义
@Aspect//等价于<aop:aspect>定义
public class LoggerBean {
	
	//等价于<aop:before>
@Before("within(org.tedu.cloudnote.controller..*)")
	public void logController(){
		//编写追加逻辑
		System.out.println(
			"进入Controller组件的execute方法处理");
	}
	
//	public void logService(){
//		
//	}
	
}
