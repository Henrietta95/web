var deleteId;
$(function () {
	$.ajax({//进入页面获取是否登录
		async : true,
		dataType : 'json',
		type : "POST",
		url : 'user/getUser.do',
		/*data : {
			loginName :Account,
			password : Password,

		},*/
		error : function(request) {
			alert("网络请求失败");
		},
		success : function(data) {
//		console.log(data);
		if(data.success==true){
//			location.href=data.href;
			$("#myUserName").html(data.data.username);
			selectLinkMan();
		}else{
			alert("你还没有登录，请登录");
			location.href="login.html";
		}
		}
	});
	

	
	$("#userOut").click(function () {//用户退出
		$.ajax({
			async : true,
			dataType : 'json',
			type : "POST",
			url : 'user/OutUser.do',
			error : function(request) {
				alert("网络请求失败");
			},
			success : function(data) {
//			console.log(data);
			if(data.success==true){
				location.href="login.html";
			}else{
				alert("失败");
			}
			}
		});
		return false;
	});	
	
	
	$("#btn-saveLinkMan").click(function () {//添加联系人
		var AddAccount=	$("#AddAccount").val();
		var AddTel=	$("#AddTel").val();
	  if(AddAccount==null||AddAccount==""||AddAccount==undefined){alert("姓名不能为空");return false;}
	  if(AddTel==null||AddTel==""||AddTel==undefined){alert("号码不能为空");return false;}
		$("#loadTip").html("添加中...");
		$("#loadProgress").show();
		$.ajax({
			async : true,
			dataType : 'json',
			type : "POST",
			url : 'user/AddLinkMan.do',
			data : {
				Account :AddAccount,
				Tel : AddTel,
			},
			error : function(request) {
	           $("#loadProgress").hide();
				alert("网络请求失败");
			},
			success : function(data) {
			$("#loadProgress").hide();
			if(data.success==true){
				alert(data.message);
				selectLinkMan();
		       $("#AddAccount").val("");
		       $("#AddTel").val("");
			}else{
				alert(data.message);
				if(data.data=="-1")location.href=json.href;
			}
			}
		});
		return false;
	})	
	

});

function selectLinkMan() {//查询联系人
	var exampleInputAmount=	$("#exampleInputAmount").val();
	$("#loadTip").html("加载中...");
	$("#loadProgress").show();
	$.ajax({
		async : true,
		dataType : 'json',
		type : "POST",
		url : 'user/selectLinkMan.do',
		data : {
			Account :exampleInputAmount,
			Tel : exampleInputAmount,
		},
		error : function(request) {
	        $("#loadProgress").hide();
			alert("网络请求失败");
		},
		success : function(data) {
	    $("#loadProgress").hide();
		if(data.success==true){
//            console.log(data);
            initTable(data.data);
	        $("#loadProgress").hide();
		}else{
	        $("#loadProgress").hide();
			alert(data.message);
			location.href=data.href;
		}
		}
	});
	return false;
}

function initTable(data){//初始化表格
	$("#listTbody").html("");
//	console.log(data);
	for(var i=0;i<data.length;i++){
	$("#listTbody").append(
	'<tr>'+
		'			<td class="td-name">'+data[i].account+'</td>'+
				'	<td class="td-tel">'+data[i].tel+'</td>'+
			'		<td class="td-btn">'+
				'		<button type="button" class="btn btn-primary btn-sm col-md-3 col-md-offset-3 btn-change"  data-toggle="modal" data-target="#changeModal"onclick="editLinkMan('+data[i].account+','+data[i].tel+','+data[i].id+')">'+
							'<span class="glyphicon glyphicon-edit" aria-hidden="true" ></span>&nbsp;编辑'+
						'</button>'+
						'<button type="button" class="btn btn-primary btn-sm col-md-3 col-md-offset-1" data-toggle="modal" data-target="#deleteModal" onclick="deleteLinkMan('+data[i].id+')" >'+
							'<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>&nbsp;删除'+
					'	</button>'+
					'</td>'+
			'	</tr>'		
	
	);
}
	
}
function showAllTable(){//查询表格
	$("#loadTip").html("查询中...");
	$("#loadProgress").show();
	$("#exampleInputAmount").val("");
	selectLinkMan();
	$("#loadProgress").hide();	
}
function editLinkMan(account,tel,id){//编辑联系人
//	alert(account+";"+tel+";"+id);return;
	$("#EidtAccount").val(account);
	$("#EidtTel").val(tel);
	$("#EidtId").val(id); 
}
function editSummit(){//编辑联系人提交
	var EidtAccount= $("#EidtAccount").val();
	var EidtTel=$("#EidtTel").val();
	var EidtId=	$("#EidtId").val();
	if(EidtId==undefined||EidtId==""||EidtId==null){alert("出错,请重新点击编辑按钮");return;}
	if(EidtTel==undefined||EidtTel==""||EidtTel==null){alert("名称不能为空");return;}
	if(EidtAccount==undefined||EidtAccount==""||EidtAccount==null){alert("电话不能为空");return;}
	$("#loadTip").html("保存中...");
	$("#loadProgress").show();
	$.ajax({
		async : true,
		dataType : 'json',
		type : "POST",
		url : 'user/editLinkMan.do',
		data:{
			Account :EidtAccount,
			Tel : EidtTel,
			id:EidtId
		},
		error : function(request) {
	        $("#loadProgress").hide();
			alert("网络请求失败");
		},
		success : function(data) {
	    $("#loadProgress").hide();
		if(data.success==true){
			alert(data.message);
	          
		}else{
			alert(data.message);
			if(data.data=="-1")location.href=data.href;
		}
		}
	});
}
function deleteLinkMan(id){//删除联系人
//	alert(id);
	deleteId=id;
}
function deleteSummit(){//删除联系人
	if(deleteId==undefined||deleteId==""||deleteId==null){alert("id不能为空");return;}
	$("#loadTip").html("删除中...");
	$("#loadProgress").show();
	$.ajax({
		async : true,
		dataType : 'json',
		type : "POST",
		url : 'user/deleteLinkMan.do',
		data:{
			id:deleteId
		},
		error : function(request) {
	       $("#loadProgress").hide();
			alert("网络请求失败");
		},
		success : function(data) {
	    $("#loadProgress").hide();
		if(data.success==true){
			alert("删除成功");
	            initTable(data.data);
		}else{
			alert("删除失败");
		}
		}
	});
}


