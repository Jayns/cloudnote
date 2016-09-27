package org.tedu.cloudnote.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.tedu.cloudnote.dao.BookDao;
import org.tedu.cloudnote.entity.Book;
import org.tedu.cloudnote.util.NoteResult;
import org.tedu.cloudnote.util.NoteUtil;

@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService{
	@Resource
	private BookDao bookDao;
	
	public NoteResult loadUserBooks(
			String userId) {
		NoteResult result = new NoteResult();
		//�����û�ID��ѯ�ʼǱ���Ϣ
		List<Book> list = 
			bookDao.findByUserId(userId);
		result.setStatus(0);
		result.setMsg("��ѯ�ʼǱ��ɹ�");
		result.setData(list);//���رʼǱ���Ϣ
		return result;
	}

	public NoteResult addBook(
		String userId, String bookName) {

		Book book = new Book();
		book.setCn_user_id(userId);//�û�ID
		book.setCn_notebook_name(bookName);//�ʼǱ���
		book.setCn_notebook_type_id("5");//normal
		book.setCn_notebook_desc("");
		Timestamp time = new Timestamp(
			System.currentTimeMillis());
		book.setCn_notebook_createtime(time);//����ʱ��
		String bookId = NoteUtil.createId();
		book.setCn_notebook_id(bookId);//ID
		bookDao.save(book);//��ӱʼǱ�
		//ģ��һ���쳣�������쳣��־��¼
		String s = null;
		s.length();
		//���ؽ��
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("�����ʼǱ��ɹ�");
		result.setData(bookId);//���رʼǱ�ID
		return result;
	}

	public NoteResult renameBook(
		String bookId, String bookName) {
		//���±��еıʼǱ���
		Map<String, Object> params = 
			new HashMap<String, Object>();
		params.put("id", bookId);//��Ӧ#{id}
		params.put("name", bookName);//��Ӧ#{name}
		bookDao.updateName(params);
		//����NoteResult���
		NoteResult result = new NoteResult();
		result.setStatus(0);
		result.setMsg("�޸ıʼǱ��ɹ�");
		return result;
	}

}
