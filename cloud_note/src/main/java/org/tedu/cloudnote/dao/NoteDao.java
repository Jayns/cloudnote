package org.tedu.cloudnote.dao;

import java.util.List;
import java.util.Map;

import org.tedu.cloudnote.entity.Note;

public interface NoteDao {
	
	public List<Note> findByStatus(String userId);
	
	public int batchDelete(String[] ids);
	
	public int dynamicUpdate(Note note);
	
	public List<Note> findNotes(
		Map<String, Object> params);
	/**
	 * 根据笔记ID更新笔记本ID
	 * @param params noteId:笔记ID;bookId:笔记本ID
	 * @return 受影响记录行数
	 */
//	public int updateBookId(
//		Map<String, Object> params);
//	public int updateStatus(
//		Map<String, Object> params);
	public void save(Note note);
//	public int update(Note note);
	public Note findById(String id);
	public List<Note> findByBookId(String bookId);
}
