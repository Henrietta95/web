$(function () {
	$("#btn-changepassword").click(function () {
		var oldPassowrd=	$("#oldPassowrd").val();
		var newPassword=	$("#newPassword").val();
		var Repassword=	$("#Repassword").val();
		if(newPassword!=Repassword){alert("两次新密码必须一致");return false;}

//		alert(Account+Password);
		  if(oldPassowrd==null||oldPassowrd==""||oldPassowrd==undefined){alert("旧密码就不能为空");return false;}
		     if(newPassword==null||newPassword==""||newPassword==undefined){alert("新密码不能为空");return false;}
		     if(Repassword==null||Repassword==""||Repassword==undefined){alert("重复输入新密码不能为空");return false;}
				$.ajax({
			async : true,
			dataType : 'json',
			type : "POST",
			url : 'user/changePassword.do',
			data : {
				oldPassowrd :oldPassowrd,
				newPassword : newPassword,

			},
			error : function(request) {
				alert("网络请求失败");
			},
			success : function(data) {
			console.log(data);
			alert(data.message);
			if(data.data=="true")
			location.href=data.href;
			}
		});
		return false;
	})
	
	
	
});