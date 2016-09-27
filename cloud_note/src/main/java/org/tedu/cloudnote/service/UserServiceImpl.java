package org.tedu.cloudnote.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tedu.cloudnote.dao.UserDao;
import org.tedu.cloudnote.entity.User;
import org.tedu.cloudnote.util.NoteResult;
import org.tedu.cloudnote.util.NoteUtil;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	@Resource//注入
	private UserDao userDao;
	
	public NoteResult checkLogin(String name, String password) {
		NoteResult result = new NoteResult();
		User user = userDao.findByName(name);
		//检测用户名
		if(user == null){
			result.setStatus(1);
			result.setMsg("用户名错误");
//			result.setData(null);
			return result;
		}
		//检测密码
		//将用户输入的明文加密
		String md5Password = NoteUtil.md5(password);
		//比对数据库中密码
		if(!user.getCn_user_password().equals(md5Password)){
			result.setStatus(2);
			result.setMsg("密码错误");
			return result;
		}
		//用户名和密码正确
		//生成一个令牌号
		String token = NoteUtil.createToken();
		//更新到cn_user的cn_user_token
		user.setCn_user_token(token);
		userDao.updateToken(user);
		//将令牌号返回给客户端
		result.setStatus(0);
		result.setMsg("登录成功");
		//将用户身份ID返回
		Map<String, Object> data = 
			new HashMap<String,Object>();
		data.put("cn_user_id", user.getCn_user_id());
		data.put("cn_user_token", token);
		result.setData(data);
		return result;
	}

	public NoteResult registUser(
		String name, String nick, String password) {
		
		NoteResult result = new NoteResult();
		//检测用户名是否存在
		User has_user = userDao.findByName(name);
		if(has_user!=null){//已存在
			result.setStatus(1);
			result.setMsg("用户名已存在");
			return result;
		}
		//执行注册逻辑
		User user = new User();
		//设置用户ID
		String userId = NoteUtil.createId();
		user.setCn_user_id(userId);
		user.setCn_user_name(name);//设置用户名
		user.setCn_user_nick(nick);//设置昵称
		//密码加密,设置密码
		String md5_password = NoteUtil.md5(password);
		user.setCn_user_password(md5_password);
		userDao.save(user);//添加用户
		
		result.setStatus(0);
		result.setMsg("注册成功");
		return result;
	}
	


}
