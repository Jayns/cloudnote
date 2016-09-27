package org.tedu.cloudnote.controller.book;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tedu.cloudnote.service.BookService;
import org.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/book")
public class RenameBookController {
	@Resource
	private BookService bookService;
	
	@RequestMapping("/rename")
	@ResponseBody
	public NoteResult execute(
		String bookId,String bookName){
		NoteResult result = 
		bookService.renameBook(bookId, bookName);
		return result;
	}
	
}
