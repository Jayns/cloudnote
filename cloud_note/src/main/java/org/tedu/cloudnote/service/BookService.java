package org.tedu.cloudnote.service;

import org.tedu.cloudnote.util.NoteResult;

public interface BookService {
	public NoteResult renameBook(
		String bookId,String bookName);
	public NoteResult addBook(
		String userId,String bookName);
	public NoteResult loadUserBooks(String userId);
}
