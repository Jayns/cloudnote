package test.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tedu.cloudnote.dao.RelationDao;
import org.tedu.cloudnote.entity.Book;
import org.tedu.cloudnote.entity.User;

public class TestRelationDao {

	private RelationDao relationDao;
	@Before
	public void init(){
		String conf = "conf/spring-mybatis.xml";
		ApplicationContext ac = 
		new ClassPathXmlApplicationContext(conf);
		relationDao = ac.getBean(
		"relationDao",RelationDao.class);
	}
	@Test
	public void test1(){
		Book book = relationDao.findBook1(
		"e46239d6-4f54-426c-a448-f7a0d45f9425");
		if(book!=null){
			System.out.println(
			"笔记本名："+book.getCn_notebook_name());
			System.out.println("所属用户："
			 +book.getUser().getCn_user_name());
		}else{
			System.out.println("未找到记录");
		}
	}
	
	@Test
	public void test2(){
		User user = relationDao.findUserAndBooks1(
			"48595f52-b22c-4485-9244-f4004255b972");
		if(user != null){
			System.out.println(
				"用户名："+user.getCn_user_name());
			System.out.println("拥有的笔记本列表");
			for(Book book:user.getBooks()){
				System.out.println(
				"笔记本："+book.getCn_notebook_name());
			} 
		}else{
			System.out.println("未找到数据");
		}
	}
	
	
}
