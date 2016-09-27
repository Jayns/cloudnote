package org.tedu.cloudnote.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tedu.cloudnote.service.UserService;
import org.tedu.cloudnote.util.NoteResult;
@Controller//É¨Ãè
@RequestMapping("/user")
public class UserLoginController {
	@Resource//×¢Èë
	private UserService userService;
	
	@RequestMapping("/login.do")
	@ResponseBody
	public NoteResult execute(
			HttpServletResponse response,
		String name,String password){
		NoteResult result = 
		userService.checkLogin(name, password);
//		response.setHeader("token", xxx);
//		response.setHeader("userId",xxx);
		return result;
	}
	
}
