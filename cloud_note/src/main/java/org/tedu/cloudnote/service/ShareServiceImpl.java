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
		//����Ƿ��ѷ�����ñʼ�
		Share has_share = 
			shareDao.findByNoteId(noteId);
		if(has_share!=null){//�����
			result.setStatus(1);
			result.setMsg("�ñʼ��ѷ����");
			return result;
		}
		//������
		Share share = new Share();
		share.setCn_note_id(noteId);//�ʼ�ID
		String shareId = NoteUtil.createId();
		share.setCn_share_id(shareId);//����ID
		//��ȡ�ʼǱ��������
		Note note = noteDao.findById(noteId);
		share.setCn_share_title(
			note.getCn_note_title());//����
		share.setCn_share_body(
			note.getCn_note_body());//����
		shareDao.save(share);//��������¼
		//����NoteResult���
		result.setStatus(0);
		result.setMsg("����ʼǳɹ�");
		return result;
	}

	public NoteResult searchShare(String keyword) {
		String title = "%";
		if(keyword!=null&&!"".equals(keyword)){
			title = "%"+keyword+"%";
		}//���ݱʼǱ���ģ����ѯ
		List<Share> list = 
			shareDao.findLikeTitle(title);
		//����NoteResult
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(list);//���رʼ�
		result.setMsg("��������ʼ����");
		return result;
	}

	public NoteResult loadShare(String shareId) {
		Share share = 
			shareDao.findById(shareId);
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setData(share);
		result.setMsg("���ط���ʼ����");
		return result;
	}

	public NoteResult favorShare(
		String shareId, String userId) {
		NoteResult result = new NoteResult();
		//���ñʼ��Ƿ����ղع�
		Favor favor = new Favor();
		favor.setCn_user_id(userId);
		favor.setCn_share_id(shareId);
		int size = favorDao.findFavor(favor);
		if(size>0){
			result.setStatus(2);
			result.setMsg("�ñʼ����ղع�");
			return result;
		}
		//����Ƿ�Ϊ�Լ�����ıʼ�
		Share share = shareDao.findById(shareId);
		Note note_tmp = noteDao.findById(
				share.getCn_note_id());//����noteId��note
		if(note_tmp.getCn_user_id().equals(userId)){
			//�û�id��ȱ�ʾΪ�Լ�����ıʼ�
			result.setStatus(1);
			result.setMsg("�ñʼ����Լ�����ıʼ�,�������ղ�");
			return result;
		}
		//�ղش���
		Note note = new Note();
		String noteId = NoteUtil.createId();
		note.setCn_note_id(noteId);//�ʼ�id
		note.setCn_user_id(userId);//�û�id
		note.setCn_notebook_id("");//�ղرʼǱ�
		note.setCn_note_status_id("1");//normal
		note.setCn_note_type_id("2");//favor�ղ�
		long time = System.currentTimeMillis();
		note.setCn_note_create_time(time);//��������
		note.setCn_note_last_modify_time(time);//�޸�����
		//���رʼǱ��������
		note.setCn_note_title(share.getCn_share_title());
		note.setCn_note_body(share.getCn_share_body());
		noteDao.save(note);//�����ղرʼ�
		//����ղؼ�¼cn_favors
		favorDao.save(favor);
		//����NoteResult
		result.setStatus(0);
		result.setMsg("�ղرʼǳɹ�");
		return result;
	}

}
