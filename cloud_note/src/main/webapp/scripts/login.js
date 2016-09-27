//注入口
$(function(){
	//登录按钮处理
	$("#login").click(login);
	//按回车实现登录
	$("body").keydown(function(event){
		var code = event.keyCode;
		if(code==13){//回车
			login();//调用login函数
		}
	});
	//注册按钮处理
	$("#regist_button").click(regist);
});
//注册处理
function regist(){
	//清空提示信息
	$("#warning_1 span").html("");
	$("#warning_2 span").html("");
	$("#warning_3 span").html("");
	$("#warning_1").hide();
	$("#warning_2").hide();
	$("#warning_3").hide();
	//获取请求参数
	var name = 
		$("#regist_username").val().trim();
	var nick = $("#nickname").val().trim();
	var password = 
		$("#regist_password").val().trim();
	var final_password = 
		$("#final_password").val().trim();
	//检查格式
	var check = true;
	if(name==""){
		$("#warning_1 span").html("用户名为空");
		$("#warning_1").show();
		check = false;
	}
	if(password==""){
		$("#warning_2 span").html("密码为空");
		$("#warning_2").show();
		check = false;
	}else if(password.length<6){
		$("#warning_2 span").html("密码大于等于6位");
		$("#warning_2").show();
		check = false;
	}
	if(final_password==""){
		$("#warning_3 span").html("确认密码为空");
		$("#warning_3").show();
		check = false;
	}else if(final_password!=password){
		$("#warning_3 span").html("与密码不一致");
		$("#warning_3").show();	
		check = false;
	}
	//发送Ajax请求
	if(check){
		$.ajax({
			url:"http://localhost:8080/cloud_note/user/regist.do",
			type:"post",
			data:{"name":name,"nick":nick,"password":password},
			dataType:"json",
			success:function(result){
				if(result.status==0){//成功
					//提示成功
					alert(result.msg);
					//切换到登录界面
					$("#back").click();//触发单击操作
				}else if(result.status==1){
					//提示用户名已占用
					$("#warning_1 span").html(result.msg);
					$("#warning_1").show();
				}
			},
			error:function(){
				alert("注册发生异常");
			}
		});
	}
};
//登录处理
function login(){
	//清空提示信息
	$("#count_span").html("");
	$("#password_span").html("");
	//获取请求参数
	var name = $("#count").val().trim();
	var password = $("#password").val().trim();
	//检查参数格式
	var check = true;//true通过检测;false未通过
	if(name==""){
		$("#count_span").html("用户名为空");
		check = false;
	}
	if(password==""){
		$("#password_span").html("密码为空");
		check = false;
	}
	//发送Ajax请求
	if(check){
		$.ajax({
			url:"http://localhost:8080/cloud_note/user/login.do",
			type:"post",
			data:{"name":name,"password":password},
			dataType:"json",
			success:function(result){
				if(result.status==0){//成功
					//获取返回的用户ID,存入Cookie
					var userId = result.data.cn_user_id;
					//获取返回的令牌号,存入Cookie
					var token  = result.data.cn_user_token;
					//调用cookie_util.js函数写入Cookie
					addCookie("userId",userId,2);
					addCookie("token",token,2);
					window.location.href = "edit.html";
				}else if(result.status==1){//用户名错误
					$("#count_span").html(result.msg);
				}else if(result.status==2){
					$("#password_span").html(result.msg);
				}
			},
			error:function(){
				alert("发生登录异常");
			}
		});
	}
};