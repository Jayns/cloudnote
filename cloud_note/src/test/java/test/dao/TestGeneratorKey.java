package test.dao;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tedu.cloudnote.dao.EmpDao;
import org.tedu.cloudnote.entity.Emp;

public class TestGeneratorKey {
	
	@Test
	public void test1(){
		String conf = "conf/spring-mybatis.xml";
		ApplicationContext ac = 
		new ClassPathXmlApplicationContext(conf);
		EmpDao dao = ac.getBean(
			"empDao",EmpDao.class);
		Emp emp = new Emp();
		emp.setName("tiger");
		emp.setSal(4000.0);
		dao.save(emp);
		//获取新追加的记录的主键值
		int key = emp.getNo();
		System.out.println("追加记录的主键值:"+key);
	}
	
}
