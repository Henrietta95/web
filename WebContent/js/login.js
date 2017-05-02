$(function () {
$("#btn-login").click(function () {
	var Account=	$("#Account").val();
	var Password=	$("#Password").val();
	  if(Account==null||Account==""||Account==undefined){alert("账号不能为空");return false;}
	     if(Password==null||Password==""||Password==undefined){alert("密码不能为空");return false;}
		$("#loadTip").html("登录中...");
		$("#loadProgress").show();
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
	     	$("#loadProgress").hide();
			alert("网络请求失败");
		},
		success : function(data) {
	    $("#loadProgress").hide();
		console.log(data);
		if(data.success==true){
			location.href=data.href;
		}else{
			alert(data.message);
		}
		}
	});
	return false;
});

    function isUser(obj){   
        reg=/^[0-9a-zA-Z\u4e00-\u9fa5]{2,12}$/;   
        if(!reg.test(obj)){  
            $("#userTips").html("请输入2-12位中文、英文、数字字符");   
        }else{   
            $("#userTips").html("");   
        }   
    }

    $("#Account").blur(function(){
    	isUser(this.value);
    });

    function isPassword(obj){   
        reg=/^[0-9a-zA-Z]{6,}$/;   
        if(!reg.test(obj)){   
            $("#passwordTips").html("请输入6位以上英文、数字字符");   
        }else{   
            $("#passwordTips").html("");   
        }   
    }

    $("#Password").blur(function(){
    	isPassword(this.value);
    });

});