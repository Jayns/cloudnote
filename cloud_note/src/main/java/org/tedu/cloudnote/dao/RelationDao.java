package org.tedu.cloudnote.dao;

import org.tedu.cloudnote.entity.Book;
import org.tedu.cloudnote.entity.User;

public interface RelationDao {
	//���������ӳ��
	public Book findBook(String bookId);
	public Book findBook1(String bookId);
	//���Ϲ���ӳ��
	public User findUserAndBooks(String userId);
	public User findUserAndBooks1(String userId);
}
