$(function () {
$("#btn-login").click(function () {
	var Account=	$("#Account").val();
	var Password=	$("#Password").val();
//	alert(Account+Password);
	  if(Account==null||Account==""||Account==undefined){alert("账号不能为空");return false;}
	     if(Password==null||Password==""||Password==undefined){alert("密码不能为空");return false;}
	$.ajax({
		async : true,
		dataType : 'json',
		type : "POST",
		url : 'user/login.do',
		data : {
			loginName :Account,
			password : Password,

		},
		error : function(request) {
			alert("网络请求失败");
		},
		success : function(data) {
		console.log(data);
		if(data.success==true){
			location.href=data.href;
		}else{
			alert(data.message);
		}
		}
	});
	return false;
})
})