package org.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import org.tedu.cloudnote.entity.Book;

public interface BookDao {
	public int updateName(
			Map<String,Object> params);
	public void save(Book book);
	public List<Book> findByUserId(String userId);
}
