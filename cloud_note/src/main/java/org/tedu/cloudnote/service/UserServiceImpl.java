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
	@Resource//ע��
	private UserDao userDao;
	
	public NoteResult checkLogin(String name, String password) {
		NoteResult result = new NoteResult();
		User user = userDao.findByName(name);
		//����û���
		if(user == null){
			result.setStatus(1);
			result.setMsg("�û�������");
//			result.setData(null);
			return result;
		}
		//�������
		//���û���������ļ���
		String md5Password = NoteUtil.md5(password);
		//�ȶ����ݿ�������
		if(!user.getCn_user_password().equals(md5Password)){
			result.setStatus(2);
			result.setMsg("�������");
			return result;
		}
		//�û�����������ȷ
		//����һ�����ƺ�
		String token = NoteUtil.createToken();
		//���µ�cn_user��cn_user_token
		user.setCn_user_token(token);
		userDao.updateToken(user);
		//�����ƺŷ��ظ��ͻ���
		result.setStatus(0);
		result.setMsg("��¼�ɹ�");
		//���û����ID����
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
		//����û����Ƿ����
		User has_user = userDao.findByName(name);
		if(has_user!=null){//�Ѵ���
			result.setStatus(1);
			result.setMsg("�û����Ѵ���");
			return result;
		}
		//ִ��ע���߼�
		User user = new User();
		//�����û�ID
		String userId = NoteUtil.createId();
		user.setCn_user_id(userId);
		user.setCn_user_name(name);//�����û���
		user.setCn_user_nick(nick);//�����ǳ�
		//�������,��������
		String md5_password = NoteUtil.md5(password);
		user.setCn_user_password(md5_password);
		userDao.save(user);//����û�
		
		result.setStatus(0);
		result.setMsg("ע��ɹ�");
		return result;
	}
	


}
