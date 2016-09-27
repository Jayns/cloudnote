package test.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tedu.cloudnote.service.UserService;
import org.tedu.cloudnote.util.NoteResult;

public class TestCheckLogin {
	//demo1 1234 Ԥ�� status:1,msg:�û�������
	//demo 1234 Ԥ�� status:2,msg:�������
	//demo 123456 Ԥ�� status:0,msg:��¼�ɹ�
	private UserService service;
	
	@Before//ÿ�ε���@Test����֮ǰִ��
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
		Assert.assertEquals("��¼�ɹ�", result.getMsg());
	}
	
	@Test
	public void test2(){
		NoteResult result = 
		service.checkLogin("demo","1234");
		//System.out.println(result.getStatus());
		//System.out.println(result.getMsg());
		Assert.assertEquals(2, result.getStatus());
		Assert.assertEquals("�������", result.getMsg());
	}
	
	@Test
	public void test1(){
		NoteResult result = 
		service.checkLogin("demo1","1234");
		//System.out.println(result.getStatus());
		//System.out.println(result.getMsg());
		Assert.assertEquals(1, result.getStatus());
		Assert.assertEquals("�û�������", result.getMsg());
	}
}
