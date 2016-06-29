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







