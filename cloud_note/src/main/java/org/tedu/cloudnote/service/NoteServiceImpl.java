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
@Transactional//该组件每个方法都采用事务控制
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
		result.setMsg("查询笔记列表成功");
		result.setData(list);
		return result;
	}

	public NoteResult loadNote(String noteId) {
		NoteResult result = new NoteResult();
		Note note = noteDao.findById(noteId);
		result.setStatus(0);
		result.setMsg("加载笔记内容成功");
		result.setData(note);
		return result;
	}

	//@Transactional//表示方法中的SQL一起成功或一起撤销
	public NoteResult updateNote(
		String id, String title, String body) {
		NoteResult result = new NoteResult();
		//更新笔记cn_note表操作
		Note note = new Note();
		note.setCn_note_id(id);//笔记ID
		note.setCn_note_title(title);//标题
		note.setCn_note_body(body);//内容
		long time = System.currentTimeMillis();
		note.setCn_note_last_modify_time(time);//最后更新时间
		noteDao.dynamicUpdate(note);
		//更新分享cn_share表title,body
		Share share = new Share();
		share.setCn_note_id(id);
		share.setCn_share_title(title);
		share.setCn_share_body(body);
		shareDao.updateShare(share);
		result.setStatus(0);
		result.setMsg("保存笔记完成");
		return result;
	}

	public NoteResult addNote(
		String userId, String title, String bookId) {
		Note note = new Note();
		note.setCn_note_title(title);//标题
		note.setCn_user_id(userId);//用户ID
		note.setCn_notebook_id(bookId);//笔记本ID
		note.setCn_note_status_id("1");//normal
		note.setCn_note_type_id("1");//normal
		note.setCn_note_body("");//空串
		long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);//创建时间
		note.setCn_note_last_modify_time(time);//最后修改时间
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);//笔记ID
		noteDao.save(note);//插入cn_note表
		//返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("创建笔记成功");
		result.setData(noteId);//返回笔记ID
		return result;
	}

	public NoteResult recycleNote(String noteId) {
//		Map<String,Object> params = 
//			new HashMap<String, Object>();
//		params.put("noteId", noteId);//笔记ID
//		params.put("status", "2");//删除状态
//		noteDao.updateStatus(params);//更新笔记状态
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_note_status_id("2");
		noteDao.dynamicUpdate(note);
		//TODO 删除分享表cn_share分享的笔记记录
		//返回NoteResult
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("删除笔记成功");
		return result;
	}

	public NoteResult moveNote(
		String bookId, String noteId) {
		//更新笔记本ID
//		Map<String, Object> params = 
//			new HashMap<String, Object>();
//		params.put("noteId", noteId);
//		params.put("bookId", bookId);
//		noteDao.updateBookId(params);
		Note note = new Note();
		note.setCn_note_id(noteId);
		note.setCn_notebook_id(bookId);
		noteDao.dynamicUpdate(note);
		//返回结果
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("转移笔记成功");
		return result;
	}

	public NoteResult noteManager(
	String userId, String title, String status) {
		Map<String,Object> params = 
			new HashMap<String, Object>();
		//设置userId参数
		params.put("userId", userId);
		//设置title参数
		if(title!=null&&!"".equals(title)){
			params.put("title", "%"+title+"%");
		}
		//设置status参数
		if(!"0".equals(status)){
			params.put("status", status);
		}
		//执行动态查询
		List<Note> list = 
			noteDao.findNotes(params);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(list);
		result.setMsg("搜索完毕");
		return result;
	}

	public NoteResult loadRecycles(String userId) {
		List<Note> list = 
			noteDao.findByStatus(userId);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(list);
		result.setMsg("查询完毕");
		return result;
	}

}
