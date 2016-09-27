//"保存笔记"按钮处理
function updateNote(){
	//获取请求参数
	var body = um.getContent();//笔记内容
	var title = $("#input_note_title").val();//笔记标题
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");//笔记ID
	//TODO 格式检查
	//发送Ajax请求
	$.ajax({
		url:"http://localhost:8080/cloud_note/note/update.do",
		type:"post",
		data:{"noteId":noteId,
			"title":title,"body":body},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//更新列表标题名称
		      var sli='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
				sli+= title;
				sli+='<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
				$li.find("a").html(sli);//替换<a>元素中的内容
				//提示保存完成
				alert(result.msg);
			}
		},
		error:function(){
			alert("保存笔记失败");
		}
	});
};
//单击笔记加载笔记内容
function loadNoteContent(){
		//将点击的笔记设置为选中
		$("#note_ul a").removeClass("checked");
		$(this).find("a").addClass("checked");
		//获取请求参数
		var noteId = $(this).data("noteId");
		//发送Ajax请求
		$.ajax({
			url:"http://localhost:8080/cloud_note/note/load.do",
			type:"post",
			data:{"noteId":noteId},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					var title = 
						result.data.cn_note_title;//标题
					var body = 
						result.data.cn_note_body;//内容
					//显示到编辑区
					$("#input_note_title").val(title);
					um.setContent(body);//获取使用um.getContent();
					//显示编辑区,隐藏预览区
					$("#pc_part_3").show();
					$("#pc_part_5").hide();
				}
			},
			error:function(){
				alert("加载笔记内容失败");
			}
		});
	};

//根据笔记本加载笔记列表
function loadBookNotes(){
	//切换列表显示区
	$("#pc_part_6").hide();
	$("#pc_part_2").show();
	$("#pc_part_4").hide();
	$("#pc_part_7").hide();
	$("#pc_part_8").hide();
	//将点击的笔记本设置为选中状态
	$("#book_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取点击的笔记本li的ID值
	var bookId = $(this).data("bookId");
	//发送Ajax请求加载笔记
	$.ajax({
		url:"http://localhost:8080/cloud_note/note/loadnotes.do",
		type:"post",
		data:{"bookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){//成功
				var notes = result.data;//获取返回的笔记集合
				//清除原有列表li
				$("#note_ul").empty();
				//循环生成笔记列表li
				for(var i=0;i<notes.length;i++){
					var noteTitle = 
							notes[i].cn_note_title;//笔记标题
					var noteId = notes[i].cn_note_id;//笔记ID
					//创建一个笔记li元素
					createNoteLi(noteId,noteTitle);
				}
			}
		},
		error:function(){
			alert("加载笔记列表失败");
		}
	});
};

//创建一个笔记li
function createNoteLi(noteId,noteTitle){
	var sli = "";
	sli+='<li class="online">';
	sli+='	<a>';
	sli+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
	sli+= noteTitle;
	sli+='		<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';
	sli+='	</a>';
	sli+='	<div class="note_menu" tabindex="-1">';
	sli+='		<dl>';
	sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>';
	sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>';
	sli+='			<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>';
	sli+='		</dl>';
	sli+='	</div>';
	sli+='</li>';
	var $li = $(sli);
	$li.data("noteId",noteId);//给li元素绑定笔记id值
	//添加到笔记列表ul元素中
	$("#note_ul").append($li);
};

//创建笔记
function sureAddNote(){
	//获取请求参数
	var title = $("#input_note").val().trim();//笔记标题
	var $li = $("#book_ul a.checked").parent();
	var bookId = $li.data("bookId");//笔记本ID
	//TODO格式检查
	//发送Ajax请求
	$.ajax({
		url:"http://localhost:8080/cloud_note/note/add.do",
		type:"post",
		data:{"userId":userId,
			"title":title,"bookId":bookId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//关闭对话框
				closeAlertWindow();
				//添加笔记li
				var noteId = result.data;//返回的笔记ID
				createNoteLi(noteId,title);
				//弹出提示框
				alert(result.msg);
			}
		},
		error:function(){
			alert("创建笔记失败");
		}
	});
};

//显示笔记菜单
function showNoteMenu(){
	//将所有菜单隐藏
	$("#note_ul .note_menu").hide();
	//显示当前li的菜单
	var menu = $(this).parent().next();
	menu.slideDown(1000);//显示菜单div
	//设置当前li选中
	$("#note_ul a").removeClass("checked");//取消所有a元素的class值
	$(this).parent().addClass("checked");//定位a元素追加class值
	//阻止后续body事件冒泡
	return false;
};

//确定删除笔记
function sureRecycleNote(){
	//获取请求参数:笔记ID
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	//发送Ajax请求
	$.ajax({
		url:"http://localhost:8080/cloud_note/note/recycle.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//关闭对话框
				closeAlertWindow();
				//删除笔记li
				$li.remove();
				//清空编辑区笔记内容
				um.setContent("");
				$("#input_note_title").val("");
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("删除笔记失败");
		}
	});
};

//笔记转移操作
function sureMoveNote(){
	//获取请求参数
	var $li = $("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	var bookId = $("#moveSelect").val();
	//TODO 检查参数格式
	//发送Ajax请求
	$.ajax({
		url:"http://localhost:8080/cloud_note/note/move.do",
		type:"post",
		data:{"bookId":bookId,"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//a关闭对话框
				closeAlertWindow();
				//b删除笔记li
				$li.remove();
				//c提示转移成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("转移笔记失败");
		}
	});
};

