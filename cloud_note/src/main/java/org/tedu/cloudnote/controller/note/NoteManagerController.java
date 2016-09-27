package org.tedu.cloudnote.controller.note;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tedu.cloudnote.service.NoteService;
import org.tedu.cloudnote.util.NoteResult;

@Controller
@RequestMapping("/note")
public class NoteManagerController {
	@Resource
	private NoteService noteService;
	@RequestMapping("/manager")
	@ResponseBody
	public NoteResult execute(
	String userId,String title,String status){
		NoteResult result = 
			noteService.noteManager(
			userId, title, status);
		return result;
	}
	
}
