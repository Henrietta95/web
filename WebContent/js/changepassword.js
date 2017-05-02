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

	$("#btn-changepassword").click(function () {
		var oldPassowrd=	$("#oldPassowrd").val();
		var newPassword=	$("#newPassword").val();
		var Repassword=	$("#Repassword").val();
		if(newPassword!=Repassword){alert("两次新密码必须一致");return false;}

//		alert(Account+Password);
		  if(oldPassowrd==null||oldPassowrd==""||oldPassowrd==undefined){alert("旧密码就不能为空");return false;}
		     if(newPassword==null||newPassword==""||newPassword==undefined){alert("新密码不能为空");return false;}
		     if(Repassword==null||Repassword==""||Repassword==undefined){alert("重复输入新密码不能为空");return false;}
		$("#loadTip").html("修改中...");
		$("#loadProgress").show();		     
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
	      	$("#loadProgress").hide();
				alert("网络请求失败");
			},
			success : function(data) {
	      	$("#loadProgress").hide();
			console.log(data);
			alert(data.message);
			if(data.data=="true")
			location.href=data.href;
			}
		});
		return false;
	});
	


    function isPassword(obj){   
        reg=/^[0-9a-zA-Z]{6,}$/;   
        if(!reg.test(obj)){   
            $("#passwordTips").html("请输入6位以上英文、数字字符");   
        }else{   
            $("#passwordTips").html("");   
        }   
    }


    $("#newPassword").blur(function(){
    	isPassword(this.value);
    });

    $("#Repassword").blur(function(){
        var newPassword=$("#newPassword").val();
        var Repassword=$("#Repassword").val();
    	if(newPassword!=Repassword){   
            $("#repasswordTips").html("两次输入的密码不一致");   
        }else{   
            $("#repasswordTips").html(""); 
        } 

    });	
	
});