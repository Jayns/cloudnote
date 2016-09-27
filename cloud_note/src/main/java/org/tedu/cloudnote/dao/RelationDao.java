package org.tedu.cloudnote.dao;

import org.tedu.cloudnote.entity.Book;
import org.tedu.cloudnote.entity.User;

public interface RelationDao {
	//单对象关联映射
	public Book findBook(String bookId);
	public Book findBook1(String bookId);
	//集合关联映射
	public User findUserAndBooks(String userId);
	public User findUserAndBooks1(String userId);
}
