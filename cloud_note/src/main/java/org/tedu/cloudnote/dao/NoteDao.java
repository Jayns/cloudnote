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
	 * ���ݱʼ�ID���±ʼǱ�ID
	 * @param params noteId:�ʼ�ID;bookId:�ʼǱ�ID
	 * @return ��Ӱ���¼����
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
