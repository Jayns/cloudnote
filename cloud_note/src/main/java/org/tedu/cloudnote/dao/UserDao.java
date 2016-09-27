package org.tedu.cloudnote.dao;

import org.tedu.cloudnote.entity.User;

public interface UserDao {
	public int updateToken(User user);
	public void save(User user);
	public User findByName(String name);
}
