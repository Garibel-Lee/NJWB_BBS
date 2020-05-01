$(function () {
    $('#form-tab a:first').tab('show');
    //login表单邮箱验证
	$('#login_mail').blur(function() {
		Login_mail();
	});

	//login表单密码验证
	$('#login_password').blur(function() {
		Login_pwd();
	});
	
	//register表单邮箱验证
	$('#register_mail').blur(function() {
		Regist_mail();
	});
	
	//register表单密码验证
	$('#register_password').blur(function() {
		Regist_pwd();
	});
	
	$(document).keypress(function(e) {
	       var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
	        if (eCode == 13){
	            $("#login_btn").click();
	        }
	});
	
	$("#register_btn").click(function(){
		if(Regist_mail()&&Regist_pwd()){
			var mail = $("#register_mail").val().trim();
			var password = $("#register_password").val().trim();
			var sex= $("#register_form input[type='radio']:checked").val().trim();
			var name = $("#register_name").val().trim();
//			var checkCode = $("#register_form HumanCheck").val().trim();
			
			var param = "mail=" + mail + "&password=" + password + "&sex=" + sex + "&nickName=" + name;

			$.ajax({
				type:"POST",
				url:"user/regist.do",
				data:param,
				dataType:"json",
				success:function(result){
					if(result.code == "200"){//ok
						$("#login_mail").val(result.mail);
						$("#login_tab").click();
					}else{
						$("#register_mail").val(result.mail);
						$("#register_mail").siblings(".form_info").find("font").remove();
						$("#register_mail").siblings('.form_info').prepend("<font style='color:#DC143C'>" +result.msg+ "</font>");
						$("#register_password").val(result.password);
						$("#register_name").val(result.nickName);
						if(0 == result.sex){
							$("#register_form input[type='radio']:first").attr('checked', 'true');
						}
						else{
							$("#register_form input[type='radio']:last").attr('checked', 'true');
						}
//						$("#register_form HumanCheck").val(result.checkCode);
					}
				}
			});
		}
	})
	
	$("#login_btn").click(function() {
		if(Login_mail()&&Login_pwd()){
			var mail = $("#login_mail").val().trim();
			var password = $("#login_password").val().trim();
			
			var param = "mail=" + mail + "&password=" + password;
			$.ajax({
				type:"POST",
				url:"user/login.do",
				data:param,
				dataType:"json",
				success:function(result){
					if(result.code == "200"){
						location.href = "index.jsp?userId=" + result.id;
					}
					else if(result.code  == "202"){
						$("#login_mail").siblings(".form_info").find("font").remove();
						$("#login_mail").siblings('.form_info').prepend("<font style='color:#DC143C'>" +result.msg+ "</font>");
					}
					else if(result.code  == "203"){
						$("#login_password").siblings(".form_info").find("font").remove();
						$("#login_password").siblings('.form_info').prepend("<font style='color:#DC143C'>" +result.msg+ "</font>");
					}
				}
			});
		}
	});
	
	
	$("#Login_form").submit(function(){
		return Login_mail()&&Login_pwd();
	})
	
	//给密码框绑定一个keyup事件(当键盘抬起来会自动触发)
	$('#register_password').keyup(function(){
		var pwd = $(this).val().trim();
		$(this).siblings('.form_info').find("font").remove();
		if(pwd.length > 6 && pwd.length <= 11){
			$(this).siblings(".form_info").find("div").removeClass("div_hidden");
			if(/[0-9]/.test(pwd) && /[a-zA-Z]/.test(pwd) && /[%!@$#*&]/.test(pwd)){
				//中
				$(this).siblings(".form_info").find("div").css("background-position","-60px 37px");
			}
			else {
				//弱
				$(this).siblings(".form_info").find("div").css("background-position","-100px 38px");
			}
		}
		else if(pwd.length > 11 && pwd.length <= 20){
			$(this).siblings(".form_info").find("div").removeClass("div_hidden");
			if(/[0-9]/.test(pwd) && /[a-zA-Z]/.test(pwd) && /[%!@$#*&]/.test(pwd)){
				//强
				$(this).siblings(".form_info").find("div").css("background-position","-12px 37px");
			}
			else {
				//中
				$(this).siblings(".form_info").find("div").css("background-position","-60px 37px");
			}
		}
		else{
			$(this).siblings(".form_info").find("div").addClass("div_hidden");
		}
	});
	
	function Login_mail() {
		$('#login_mail').siblings(".form_info").find("font").remove();
		if(""==($('#login_mail').val().trim())){
			$('#login_mail').siblings('.form_info').prepend("<font style='color:#DC143C'>请输入邮箱</font>");
			return false;
		}
		else {
			if($('#login_mail').val().trim().match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)){
				$('#login_mail').siblings('.form_info').prepend("");
				return true;
				
			}
			else{
				$('#login_mail').siblings('.form_info').prepend("<font style='color:#DC143C'>邮箱格式不正确</font>");
				return false;
			}
		}
	}
	
	function Login_pwd(){
		$('#login_password').siblings(".form_info").find("font").remove();
		if(""==($('#login_password').val().trim())){
			$('#login_password').siblings('.form_info').prepend("<font style='color:#DC143C'>请输入密码</font>");
			return false;
		}
		else {
			$('#login_password').siblings('.form_info').prepend("");
			return true;
		}
	}
	
	function Regist_mail (){
		$("#register_mail").siblings(".form_info").find("font").remove();
		if(""==($("#register_mail").val().trim())){
			$("#register_mail").siblings('.form_info').prepend("<font style='color:#DC143C'>请输入邮箱</font>");
			return false;
		}
		else {
			if($("#register_mail").val().trim().match(/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/)){
				$("#register_mail").siblings(".form_info").find("div").removeClass("div_hidden");
				$("#register_mail").siblings(".form_info").find("div").css("background-position","-147px 37px");
				return true;
			}
			else{
				$("#register_mail").siblings(".form_info").find("div").addClass("div_hidden");
				$("#register_mail").siblings('.form_info').prepend("<font style='color:#DC143C'>邮箱格式不正确</font>");
				return false;
			}
		}
	}
	
	function Regist_pwd(){
		$("#register_password").siblings(".form_info").find("font").remove();
		if(""==($("#register_password").val().trim())){
			$("#register_password").siblings('.form_info').prepend("<font style='color:#DC143C'>请输入密码</font>");
			return false;
		}
		else {
			if(!$("#register_password").val().trim().match(/^[0-9a-zA-Z%!@$#*&]{6,20}$/)){
				$("#register_password").siblings('.form_info').prepend("<font style='color:#DC143C'>密码格式不正确：6-20位字母/数字/字符组合</font>");
				return false;
			}
			else {
				return true;
			}
		}
	}
});
