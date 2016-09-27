package test.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tedu.cloudnote.dao.UserDao;
import org.tedu.cloudnote.entity.User;

public class TestUserDao {
	@Test//测试UserDao
	public void test2(){
		String conf = "conf/spring-mybatis.xml";
		ApplicationContext ac = 
	new ClassPathXmlApplicationContext(conf);
		UserDao dao = ac.getBean(
			"userDao",UserDao.class);
		User user = dao.findByName("demo1");
		if(user == null){
			System.out.println("用户不存在");
		}else{
			System.out.println(user.getCn_user_password());
		} 
	}

	@Test//测试dbcp连接池
	public void test1() throws SQLException{
		String conf = "conf/spring-mybatis.xml";
		ApplicationContext ac = 
	new ClassPathXmlApplicationContext(conf);
		DataSource ds = ac.getBean(
				"dbcp",DataSource.class);
		System.out.println(ds.getConnection());
	}
	
}
