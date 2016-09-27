//收藏分享笔记
function sureFavorNote(){
	//获取请求参数
	var $li = $(this).parents("li");
	var shareId = $li.data("shareId");
	//发送Ajax请求
	$.ajax({
		url:"http://localhost:8080/cloud_note/share/favor.do",
		type:"post",
		data:{"shareId":shareId,"userId":userId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.msg);
			}else if(result.status==1){
				alert(result.msg);
			}else if(result.status==2){
				alert(result.msg);
			}
		},
		error:function(){
			alert("收藏笔记失败");
		}
	});
};
//搜索分享笔记
function searchShareNote(event){
	//判断是否按回车
	var code = event.keyCode;//获取键的ASCII
	if(code==13){//按回车了
		//获取请求参数
		var keyword = 
			$("#search_note").val().trim();
		//发送Ajax请求
		$.ajax({
			url:"http://localhost:8080/cloud_note/share/search.do",
			type:"post",
			data:{"keyword":keyword},
			dataType:"json",
			success:function(result){
				if(result.status==0){
					//显示搜索列表区
					$("#pc_part_6").show();
					$("#pc_part_2").hide();
					$("#pc_part_4").hide();
					$("#pc_part_7").hide();
					$("#pc_part_8").hide();
					//清空之前生成的列表
					$("#search_ul").empty();
					var shares = result.data;
					//循环生成列表li
					for(var i=0;i<shares.length;i++){
						var shareId=shares[i].cn_share_id;
						var shareTitle = shares[i].cn_share_title;
						//拼一个li
						var sli = "";
						sli+='<li class="online">';
						sli+='	<a>';
						sli+='		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>';
						sli+= shareTitle;
						sli+='		<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-star"></i></button>';
						sli+='	</a>';
						sli+='</li>';
						//绑定shareId
						var $li = $(sli);
						$li.data("shareId",shareId);
						//添加到search_ul列表中
						$("#search_ul").append($li);
					}
				}
			},
			error:function(){
				alert("搜索分享笔记失败");
			}
		});
	}
};
//笔记分享操作
function sureShareNote(){
	//获取请求参数
	var $li=$("#note_ul a.checked").parent();
	var noteId = $li.data("noteId");
	//发送Ajax请求
	$.ajax({
		url:"http://localhost:8080/cloud_note/share/share.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){//成功
				alert(result.msg);
			}else if(result.status==1){//分享过
				alert(result.msg);
			}
		},
		error:function(){
			alert("分享笔记失败");
		}
	});
};

//查看分享笔记内容
function loadShareContent(){
	//设置选中状态
	$("#search_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var shareId=$(this).data("shareId");
	//发送Ajax请求
	$.ajax({
		url:"http://localhost:8080/cloud_note/share/load.do",
		type:"post",
		data:{"shareId":shareId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var title=result.data.cn_share_title;
				var body=result.data.cn_share_body;
				//显示到预览区
				$("#noput_note_title").html(title);
				$("#noput_note_body").html(body);
				//显示预览区,隐藏编辑区
				$("#pc_part_3").hide();
				$("#pc_part_5").show();
			}
		},
		error:function(){
			alert("加载分享笔记失败");
		}
	});
};

