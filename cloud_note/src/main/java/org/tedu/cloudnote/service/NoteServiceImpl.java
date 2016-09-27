package org.tedu.cloudnote.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tedu.cloudnote.dao.NoteDao;
import org.tedu.cloudnote.dao.ShareDao;
import org.tedu.cloudnote.entity.Note;
import org.tedu.cloudnote.entity.Share;
import org.tedu.cloudnote.util.NoteResult;
import org.tedu.cloudnote.util.NoteUtil;

@Service("noteService")
@Transactional//�����ÿ�������������������
public class NoteServiceImpl implements NoteService{
	@Resource
	private NoteDao noteDao;
	@Resource
	private ShareDao shareDao;
	
	public NoteResult loadBookNotes(String bookId) {
		NoteResult result = new NoteResult();
		List<Note> list = 
			noteDao.findByBookId(bookId);
		result.setStatus(0);
		result.setMsg("��ѯ�ʼ��б�ɹ�");
		result.setData(list);
		return result;
	}

	public NoteResult loadNote(String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findById(noteId);
		result.setStatus(0);
		result.setMsg("���رʼ����ݳɹ�");
		result.setData(note);
		return result;
	}

	//@Transactional//��ʾ�����е�SQLһ��ɹ���һ����
	public NoteResult updateNote(
		String id, String title, String body) {
		NoteResult result = new NoteResult();
		//���±ʼ�cn_note�����
		Note note = new Note();
		note.setCn_note_id(id);//�ʼ�ID
		note.setCn_note_title(title);//����
		note.setCn_note_body(body);//����
		long time = System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);//������ʱ��
		noteDao.dynamicUpdate(note);
		//���·���cn_share��title,body
		Share share = new Share();
		share.setCn_note_id(id);
		share.setCn_share_title(title);
		share.setCn_share_body(body);
		shareDao.updateShare(share);
		result.setStatus(0);
		result.setMsg("����ʼ����");
		return result;
	}

	public NoteResult addNote(
		String userId, String title, String bookId) {
		Note note = new Note();
		note.setCn_note_title(title);//����
		note.setCn_user_id(userId);//�û�ID
		note.setCn_notebook_id(bookId);//�ʼǱ�ID
		note.setCn_note_status_id("1");//normal
		note.setCn_note_type_id("1");//normal
		note.setCn_note_body("");//�մ�
		long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);//����ʱ��
		note.setCn_note_last_modify_time(time);//����޸�ʱ��
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);//�ʼ�ID
		noteDao.save(note);//����cn_note��
		//���ؽ��
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("�����ʼǳɹ�");
		result.setData(noteId);//���رʼ�ID
		return result;
	}

	public NoteResult recycleNote(String noteId) {
//		Map<String,Object> params = 
//			new HashMap<String, Object>();
//		params.put("noteId", noteId);//�ʼ�ID
//		params.put("status", "2");//ɾ��״̬
//		noteDao.updateStatus(params);//���±ʼ�״̬
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_status_id("2");
		noteDao.dynamicUpdate(note);
		//TODO ɾ�������cn_share����ıʼǼ�¼
		//����NoteResult
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("ɾ���ʼǳɹ�");
		return result;
	}

	public NoteResult moveNote(
		String bookId, String noteId) {
		//���±ʼǱ�ID
//		Map<String, Object> params = 
//			new HashMap<String, Object>();
//		params.put("noteId", noteId);
//		params.put("bookId", bookId);
//		noteDao.updateBookId(params);
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		noteDao.dynamicUpdate(note);
		//���ؽ��
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("ת�Ʊʼǳɹ�");
		return result;
	}

	public NoteResult noteManager(
	String userId, String title, String status) {
		Map<String,Object> params = 
			new HashMap<String, Object>();
		//����userId����
		params.put("userId", userId);
		//����title����
		if(title!=null&&!"".equals(title)){
			params.put("title", "%"+title+"%");
		}
		//����status����
		if(!"0".equals(status)){
			params.put("status", status);
		}
		//ִ�ж�̬��ѯ
		List<Note> list = 
			noteDao.findNotes(params);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(list);
		result.setMsg("�������");
		return result;
	}

	public NoteResult loadRecycles(String userId) {
		List<Note> list = 
			noteDao.findByStatus(userId);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(list);
		result.setMsg("��ѯ���");
		return result;
	}

}
