package org.tedu.cloudnote.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tedu.cloudnote.dao.FavorDao;
import org.tedu.cloudnote.dao.NoteDao;
import org.tedu.cloudnote.dao.ShareDao;
import org.tedu.cloudnote.entity.Favor;
import org.tedu.cloudnote.entity.Note;
import org.tedu.cloudnote.entity.Share;
import org.tedu.cloudnote.util.NoteResult;
import org.tedu.cloudnote.util.NoteUtil;

@Service("shareService")
@Transactional
public class ShareServiceImpl implements ShareService{
	@Resource
	private ShareDao shareDao;
	@Resource
	private NoteDao noteDao;
	@Resource
	private FavorDao favorDao;
	
	public NoteResult shareNote(String noteId) {
		NoteResult result = new NoteResult();
		//检测是否已分享过该笔记
		Share has_share = 
			shareDao.findByNoteId(noteId);
		if(has_share!=null){//分享过
			result.setStatus(1);
			result.setMsg("该笔记已分享过");
			return result;
		}
		//分享处理
		Share share = new Share();
		share.setCn_note_id(noteId);//笔记ID
		String shareId = NoteUtil.createId();
		share.setCn_share_id(shareId);//分享ID
		//获取笔记标题和内容
		Note note = noteDao.findById(noteId);
		share.setCn_share_title(
			note.getCn_note_title());//标题
		share.setCn_share_body(
			note.getCn_note_body());//内容
		shareDao.save(share);//插入分享记录
		//返回NoteResult结果
		result.setStatus(0);
		result.setMsg("分享笔记成功");
		return result;
	}

	public NoteResult searchShare(String keyword) {
		String title = "%";
		if(keyword!=null&&!"".equals(keyword)){
			title = "%"+keyword+"%";
		}//根据笔记标题模糊查询
		List<Share> list = 
			shareDao.findLikeTitle(title);
		//返回NoteResult
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(list);//返回笔记
		result.setMsg("搜索分享笔记完毕");
		return result;
	}

	public NoteResult loadShare(String shareId) {
		Share share = 
			shareDao.findById(shareId);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(share);
		result.setMsg("加载分享笔记完成");
		return result;
	}

	public NoteResult favorShare(
		String shareId, String userId) {
		NoteResult result = new NoteResult();
		//检测该笔记是否已收藏过
		Favor favor = new Favor();
		favor.setCn_user_id(userId);
		favor.setCn_share_id(shareId);
		int size = favorDao.findFavor(favor);
		if(size>0){
			result.setStatus(2);
			result.setMsg("该笔记已收藏过");
			return result;
		}
		//检测是否为自己分享的笔记
		Share share = shareDao.findById(shareId);
		Note note_tmp = noteDao.findById(
				share.getCn_note_id());//根据noteId查note
		if(note_tmp.getCn_user_id().equals(userId)){
			//用户id相等表示为自己分享的笔记
			result.setStatus(1);
			result.setMsg("该笔记是自己分享的笔记,不允许收藏");
			return result;
		}
		//收藏处理
		Note note = new Note();
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);//笔记id
		note.setCn_user_id(userId);//用户id
		note.setCn_notebook_id("");//收藏笔记本
		note.setCn_note_status_id("1");//normal
		note.setCn_note_type_id("2");//favor收藏
		long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);//创建日期
		note.setCn_note_last_modify_time(time);//修改日期
		//加载笔记标题和内容
		note.setCn_note_title(share.getCn_share_title());
		note.setCn_note_body(share.getCn_share_body());
		noteDao.save(note);//插入收藏笔记
		//添加收藏记录cn_favors
		favorDao.save(favor);
		//返回NoteResult
		result.setStatus(0);
		result.setMsg("收藏笔记成功");
		return result;
	}

}
