package test.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tedu.cloudnote.service.UserService;
import org.tedu.cloudnote.util.NoteResult;

public class TestCheckLogin {
	//demo1 1234 预期 status:1,msg:用户名错误
	//demo 1234 预期 status:2,msg:密码错误
	//demo 123456 预期 status:0,msg:登录成功
	private UserService service;
	
	@Before//每次调用@Test方法之前执行
	public void init(){
		String[] conf = 
			{"conf/spring-mvc.xml",
			"conf/spring-aop.xml",
			"conf/spring-mybatis.xml"};
		ApplicationContext ac = 
	new ClassPathXmlApplicationContext(conf);
		service = ac.getBean(
			"userService",UserService.class);
		System.out.println(
			service.getClass().getName());
	}
	
	@Test
	public void test3(){
		NoteResult result = 
		service.checkLogin("demo","123456");
		//System.out.println(result.getStatus());
		//System.out.println(result.getMsg());
		Assert.assertEquals(0, result.getStatus());
		Assert.assertEquals("登录成功", result.getMsg());
	}
	
	@Test
	public void test2(){
		NoteResult result = 
		service.checkLogin("demo","1234");
		//System.out.println(result.getStatus());
		//System.out.println(result.getMsg());
		Assert.assertEquals(2, result.getStatus());
		Assert.assertEquals("密码错误", result.getMsg());
	}
	
	@Test
	public void test1(){
		NoteResult result = 
		service.checkLogin("demo1","1234");
		//System.out.println(result.getStatus());
		//System.out.println(result.getMsg());
		Assert.assertEquals(1, result.getStatus());
		Assert.assertEquals("用户名错误", result.getMsg());
	}
}
