package test.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tedu.cloudnote.dao.NoteDao;

public class TestBatchDelete {
	private NoteDao noteDao;

	@Before//每次调用@Test方法时先执行@Before方法
	public void init(){//实例化Spring容器获取noteDao
		String conf = "conf/spring-mybatis.xml";
		ApplicationContext ac = 
		new ClassPathXmlApplicationContext(conf);
		noteDao = ac.getBean(
			"noteDao",NoteDao.class);
	}
	@Test
	public void test1(){
		String[] ids = {
		"fed920a0-573c-46c8-ae4e-368397846efd",
		"ebd65da6-3f90-45f9-b045-782928a5e2c0",
		"9187ffd3-4c1e-4768-9f2f-c600e835b823"};
		int size = noteDao.batchDelete(ids);
		System.out.println("删除的记录数量："+size);
	}
	
}
