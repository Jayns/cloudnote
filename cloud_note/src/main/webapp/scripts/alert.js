//双击弹出修改笔记本名对话框
function alertRenameBookWindow(){
	//获取双击的笔记本名称
	var bookName = $(this).text().trim();
	//弹出对话框
	$("#can").load("alert/alert_rename.html",function(){
		//给对话框中输入框设置笔记本名称
		$("#input_notebook_rename").val(bookName);
		//显示出对话框灰色背景
		$(".opacity_bg").show();
	});
};
//弹出转移笔记对话框
function alertMoveNoteWindow(){
	//弹出对话框
	$("#can").load("alert/alert_move.html",function(){
		//该function逻辑是在alert_move.html加载到#can之后执行
		//显示出对话框灰色背景
		$(".opacity_bg").show();
		//给moveSelect加载笔记本选项
		var books = $("#book_ul li");
		for(var i=0;i<books.length;i++){
			var bookId=$(books[i]).data("bookId");
			var bookName=$(books[i]).text().trim();
			//创建一个option
			var opt = "<option value='"+bookId+"'>"
					+bookName+"</option>";
			//添加到select中
			$("#moveSelect").append(opt);
		}
	});
	
};
//弹出删除笔记对话框
function alertRecycleNoteWindow(){
	//弹出对话框
	$("#can").load("alert/alert_delete_note.html");
	//显示出对话框灰色背景
	$(".opacity_bg").show();
}
//弹出创建笔记对话框
function alertAddNoteWindow(){
	//检查是否选中笔记本
	var $li = $("#book_ul a.checked").parent();
	if($li.length==0){
		alert("请选择笔记本");
		return;
	}
	//弹出对话框
	$("#can").load("alert/alert_note.html");
	//显示出对话框灰色背景
	$(".opacity_bg").show();
};

//弹出创建笔记本对话框
function alertAddBookWindow(){
	//弹出对话框
	$("#can").load("alert/alert_notebook.html");
	//显示出对话框灰色背景
	$(".opacity_bg").show();
};
//关闭弹出对话框
function closeAlertWindow(){
	$("#can").empty();//清空对话框div
	$(".opacity_bg").hide();//隐藏背景色
}