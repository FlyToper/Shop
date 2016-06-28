//--------------------------------修改管理员密码-------------------------------
function updateAdminPwd(){
	
	var oldPwd=$("#oldPwd").val();
	var newPwd1=$("#newPwd1").val();
	var newPwd2=$("#newPwd2").val();
	
	if(oldPwd!=""){
		if(newPwd1!=""){
			if(newPwd2!=""){
				if(newPwd1==newPwd2){
			
					$.post('./AdminController', {"oldPwd" : oldPwd , "newPwd" : newPwd1 , "a" : "updateAdminPwd"
					} , function(rst) {
						if (rst == "success") { // 返回"success"，密码修改成功
							alert("修改成功！");
						} else if (rst == "error") { // 返回"error",用户名和密码验证失败
							alert("旧密码错误！");
						} else {
							alert("deng");
						}
					});
				}else{
					alert("前后输入的新密码不一致！");
				}
			}else{
				alert("请确认新密码！");
			}
		}else{
			alert("请输入新密码！");
		}
	}else{
		alert("请输入旧密码！");
	}
}

//--------------------------------用户信息修改按钮事件-------------------------------

function updateAdmin() {
	//
	$("#txtName").css("border", "solid");
	$("#txtName").removeAttr("readonly");
	
	$("#txtEmail").css("border", "solid");
	$("#txtEmail").removeAttr("readonly");
	
	$("#txtPhone").css("border", "solid");
	$("#txtPhone").removeAttr("readonly");
	
	//
	$("#div_update").css("display","none");;
	//$("#div_update").addClass("myHide");
	
	$("#div_save").css("display","inline");;
	//$("#div_save").addClass("myShow");
	
	//$(".canEdit").css("display","inline");
}
//------------------------------------------------------------------------------------------------------------
//员工信息取消按钮事件
function updateAdminCancel() { 
	//
	$("#txtName").attr("readonly", "readonly");
	$("#txtName").css("border", "none");
	
	$("#txtEmail").attr("readonly", "readonly");
	$("#txtEmail").css("border", "none");
	
	$("#txtPhone").attr("readonly", "readonly");
	$("#txtPhone").css("border", "none");
	
	$("#txtGender").attr("readonly", "readonly");
	$("#txtGender").css("border", "none");
	
	//
	$("#div_update").css("display","inline");
	$("#div_save").css("display","none");
	
}
//-------------------------------------------------------------------------------------
//保存员工信息
function saveAdmin(){
	var UserNum = $("#txtNum").val();

	var UserName = $("#txtName").val();
	var Email = $("#txtEmail").val();
	var Phone = $("#txtPhone").val();
	
	if(UserName != ""){
		if(Phone != ""){
			if(Email!=""){
				alert("进入js,准备跳转");
				//全部验证完成，可以执行提交
				$.post("./AdminController", {"UserName":UserName , "Email":Email , "Phone":Phone , "a":"UpdateAdminInfo"}, function(rst){
					if(rst == "success"){
						alert("保存成功！");
						window.location.href="./AdminController";
					}else{
						alert("修改失败，请重试！");
					}
				});
			}else{
				alert("邮箱不能为空！");
				$("#txtEmail").focus();
			}
		}else{
			alert("电话不能为空！");
			$("#txtPhone").focus();
		}
	}else{
		alert("姓名不能为空！");
		$("#txtName").focus();
	}
	
}