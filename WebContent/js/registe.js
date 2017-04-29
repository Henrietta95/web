$(function () {

	$("#btn-register").click(function () {
		var Account=	$("#Account").val();
		var Password=	$("#Password").val();
		var Repassword=	$("#Repassword").val();
		if(Password!=Repassword){alert("两次密码必须一致");return false;}
//		alert(Account+Password);
     if(Account==null||Account==""||Account==undefined){alert("账号不能为空");return false;}
     if(Password==null||Password==""||Password==undefined){alert("密码不能为空");return false;}
     if(Repassword==null||Repassword==""||Repassword==undefined){alert("重复密码不能为空");return false;}
		$.ajax({
			async : true,
			dataType : 'json',
			type : "POST",
			url : 'user/register.do',
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
				alert(data.message);
				location.href=data.href;
			}else{
				alert(data.message);
			}
			}
		});
		return false;
	})
	
	
	
});