package test.dao;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tedu.cloudnote.dao.BookDao;
import org.tedu.cloudnote.entity.Book;

public class TestLoadBooks {
	@Test
	public void test1(){
		String conf = "conf/spring-mybatis.xml";
		ApplicationContext ac = 
		new ClassPathXmlApplicationContext(conf);
		BookDao dao = ac.getBean(
			"bookDao",BookDao.class);
		List<Book> list = dao.findByUserId(
			"48595f52-b22c-4485-9244-f4004255b972");
		for(Book book:list){
			System.out.println(book.getCn_notebook_name());
		}
	}
	
}
