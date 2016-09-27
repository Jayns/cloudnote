//加载用户笔记本列表
function loadUserBooks(){
	
	$.ajax({
		url:"http://localhost:8080/cloud_note/book/loadbooks.do",
		type:"post",
		data:{"userId":userId},
		dataType:"json",
		beforeSend:function(xhr){
			//TODO 获取Cookie中的userId和token
			//TODO 设置到request请求header中
			xhr.setRequestHeader("author","xxx");
		},
		success:function(result){
			if(result.status==0){//成功
				var books = result.data;//获取服务器返回的笔记本信息
				//循环笔记本数组
				for(var i=0;i<books.length;i++){
					var bookName = 
						books[i].cn_notebook_name;//获取笔记本名
					var bookId = books[i].cn_notebook_id;//获取笔记本ID
					//生成笔记本列表li元素
					createBookLi(bookId,bookName);
				}
			}
		},
		error:function(){
			alert("加载用户笔记本列表失败");
		}
	});
};

//创建笔记本列表li
function createBookLi(bookId,bookName){
 var sli = '<li class="online">';
	 sli+= '	<a>';
	 sli+= '		<i class="fa fa-book" title="online" rel="tooltip-bottom">';
	 sli+= '		</i>';
	 sli+= bookName;
	 sli+= '	</a>';
	 sli+= '</li>';
	//将bookId绑定到li元素上
	var $li = $(sli);
	$li.data("bookId",bookId);
	//添加到笔记本列表ul元素中
	$("#book_ul").append($li);
};

//创建笔记本
function sureAddBook(){
	//获取请求参数
	var bookName = $("#input_notebook").val().trim();
	//TODO检查格式
	//发送Ajax请求
	$.ajax({
		url:"http://localhost:8080/cloud_note/book/add.do",
		type:"post",
		data:{"bookName":bookName,"userId":userId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//关闭对话框
				//closeAlertWindow();
				//添加笔记本li
				var bookId = result.data;//笔记本ID
				createBookLi(bookId,bookName);
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("创建笔记本失败");
		}
	});
};

//确定修改笔记本名称
function sureRenameBook(){
	//获取请求参数
	var bookName =
		$("#input_notebook_rename").val().trim();
	var $li = $("#book_ul a.checked").parent();
	var bookId = $li.data("bookId");
	//发送Ajax请求
	$.ajax({
		url:"http://localhost:8080/cloud_note/book/rename.do",
		type:"post",
		data:{"bookId":bookId,"bookName":bookName},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//关闭对话框
				closeAlertWindow();
				//修改笔记本列表li
				var sli ='<i class="fa fa-book" title="online" rel="tooltip-bottom">';
				    sli+='</i>';
				    sli+= bookName;
				//将li中<a>内容替换成sli字符串
				$li.find("a").html(sli);
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("修改笔记本名失败");
		}
	});
};

