package test.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tedu.cloudnote.dao.NoteDao;
import org.tedu.cloudnote.entity.Note;

public class TestFindNotes {

	private NoteDao noteDao;

	@Before//每次调用@Test方法时先执行@Before方法
	public void init(){//实例化Spring容器获取noteDao
		String conf = "conf/spring-mybatis.xml";
		ApplicationContext ac = 
		new ClassPathXmlApplicationContext(conf);
		noteDao = ac.getBean(
			"noteDao",NoteDao.class);
	}
	
	@Test//测试select * from cn_note where cn_user_id=#{userId}
	public void test1(){
		Map<String,Object> params = 
			new HashMap<String,Object>();
		params.put("userId", 
			"48595f52-b22c-4485-9244-f4004255b972");
		List<Note> list = 
			noteDao.findNotes(params);
		System.out.println("查询结果："+list.size());
	}
	@Test//select * from cn_note where cn_user_id=#{userId} and cn_note_title like #{title}
	public void test2(){
		Map<String,Object> params = 
			new HashMap<String,Object>();
		params.put("userId", "48595f52-b22c-4485-9244-f4004255b972");
		params.put("title", "%2%");
		List<Note> list = 
			noteDao.findNotes(params);
		System.out.println("查询结果："+list.size());
	}
	@Test//select * from cn_note where cn_user_id=#{userId} and cn_note_status_id=#{status}
	public void test3(){
		Map<String,Object> params = 
				new HashMap<String,Object>();
		params.put("userId", "48595f52-b22c-4485-9244-f4004255b972");
		params.put("status", "2");//删除
		List<Note> list = 
			noteDao.findNotes(params);
		System.out.println("查询结果："+list.size());
	}
	
	@Test//select * from cn_note where cn_user_id=#{userId} and cn_note_title like #{title} and cn_note_status_id=#{status}
	public void test4(){
		Map<String,Object> params = 
				new HashMap<String,Object>();
		params.put("userId", "48595f52-b22c-4485-9244-f4004255b972");
		params.put("status", "1");//normal
		params.put("title", "%2%");//标题带2的
		List<Note> list = 
			noteDao.findNotes(params);
		System.out.println("查询结果："+list.size());
	}
	
}
