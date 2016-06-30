//商品相关js

//执行商品上架操作
function UpToSale(goodsNum){
	//alert("准备上架");
	
	if(goodsNum != ""){
		$.post("./HomeController?a=UpToSale",{"goodsNum":goodsNum}, function(rst){
			if(rst == "success"){
				window.location.href="./HomeController?a=EnterGoodsImport";
			}else{
				alert("商品上架错误，请重试！");
			}
		} );
	}else{
		alert("操作错误！");
	}
}

//商品执行下架操作
function DownToSale(goodsNum){
	if(goodsNum != ""){
		$.post("./HomeController?a=DownToSale",{"goodsNum":goodsNum}, function(rst){
			if(rst == "success"){
				window.location.href="./HomeController?a=EnterGoodsImport";
			}else{
				alert("商品上架错误，请重试！");
			}
		} );
	}else{
		alert("操作错误！");
	}
}

//保存新增的商品类型
function SaveNewCategory(){
	var cateName = $("#txtCateName").val();
	var cateRemark = $("#txtCateRemark").val();
	
	
	if(cateName != ""){
		$.post("./HomeController?a=AddNewCategory",{"cateName":cateName, "cateRemark":cateRemark},function(rst){
			if(rst == "success"){
				$("#txtCateName").val("");
				$("#txtCateRemark").val("");
				$("#msgSaveCategory").removeClass("error");
				$("#msgSaveCategory").addClass("success");
				$("#msgSaveCategory").html("保存成功！");
			}else if(rst == "error1"){
				
				$("#txtCateName").val("");
				$("#txtCateRemark").val("");
				$("#msgSaveCategory").removeClass("success");
				$("#msgSaveCategory").addClass("error");
				
				$("#msgSaveCategory").html("商品分类已存在！");
			}else if(rst == "error2"){
				$("#msgSaveCategory").removeClass("success");
				$("#msgSaveCategory").addClass("error");
				
				$("#msgSaveCategory").html("新增失败！");
			}
		});
		
	}else{
		$("#msgSaveCategory").removeClass("success");
		$("#msgSaveCategory").addClass("error");
		
		$("#msgSaveCategory").html("商品类型名不能为空！");
	}
}


//商品名字input焦点获取出发事件
$("#txtCateName").focus(function(){
	$("#msgSaveCategory").html("");
});

$("#txtCateName").blur(function(){
	if($(this).val() == ""){
		$("#msgSaveCategory").removeClass("success");
		$("#msgSaveCategory").addClass("error");
		$("#msgSaveCategory").html("商品类型名不能为空！");
	}
});



//新增商品
function SaveNewGoodsInfo(){
	var newGoodsName = $("#txtNewGoodsName").val();
	var newCategory = $("#selNewCategory").val();
	var newSalePrice = $("#txtNewSalePrice").val();
	var newRemark = $("#txtNewRemark").val();
	
	
	
//	alert(newGoodsName+"___"+newCategory+"___"+newSalePrice+"___"+newRemark+"___");
//	return;
	if(newGoodsName != ""){
		if(newCategory != 0){
			if(newSalePrice != ""){
				if(newRemark.length < 250){
					//执行上传
					$.post("./HomeController?a=AddNewGoodsInfo", {"newGoodsName":newGoodsName, "newCategory":newCategory, "newSalePrice":newSalePrice, "newRemark":newRemark}, function(rst){
						if(rst == "success"){
							showMsg("#msgNewGoodsInfo","添加成功！ [2秒后跳转]", "error", "success");
							setTimeout(function(){
								window.location.href="./HomeController?a=EnterGoodsImport";
							}, 2000);
						}else{
							showMsg("#msgNewGoodsInfo","添加失败！", "success", "error");
						}
					});
				}else{
					showMsg("#msgNewGoodsInfo","备注信息过长！", "success", "error");
				}
			}else{
				showMsg("#msgNewGoodsInfo","请输入出售价格！", "success", "error");
			}
		}else{
			showMsg("#msgNewGoodsInfo","请选择商品类型！", "success", "error");
		}
	}else{
		showMsg("#msgNewGoodsInfo","商品名字不能为空！", "success", "error");
	}
}

//展示信息
function showMsg(id, msg, removeClassName, addClassName){
	$(id).removeClass(removeClassName);
	$(id).addClass(addClassName);
	$(id).html(msg);
}


$("#txtNewGoodsName").focus(function(){ 
	showMsg("#msgNewGoodsInfo","", "error", "success")
	});

$("#txtNewSalePrice").focus(function(){ 
	showMsg("#msgNewGoodsInfo","", "error", "success")
});
$("#selNewCategory").focus(function(){ 
	showMsg("#msgNewGoodsInfo","", "error", "success")
});


//新增商品信息取消按钮事件
function CancelNewGoodsInfo(){
	aelrt("cancel");
	$("#txtNewGoodsName").val("");
	$("#selNewCategory").val("请选择--");
	$("#txtNewSalePrice").val("");
	$("#txtNewRemark").val("");
}


//供货商下拉列表的改变事件
function ShowImportInfo(){
	var providerName = $("#selProviderName").val();
	if(providerName == "0"){
		$("#div_importInfo").hide();
	}else{
		$("#div_importInfo").show();
	}
	
}



