package org.tedu.cloudnote.service;

import org.tedu.cloudnote.util.NoteResult;

public interface NoteService {
	public NoteResult loadRecycles(String userId);
	public NoteResult noteManager(
		String userId,String title,String status);
	public NoteResult moveNote(
		String bookId,String noteId);
	public NoteResult recycleNote(String noteId);
	public NoteResult addNote(
		String userId,String title,String bookId);
	public NoteResult updateNote(
		String id,String title,String body);
	public NoteResult loadNote(String noteId);
	public NoteResult loadBookNotes(String bookId);
}
