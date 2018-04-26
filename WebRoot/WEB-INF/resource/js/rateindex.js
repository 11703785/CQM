/** RateIndex Begin*/
var RateIndex = new Object();
RateIndex.create = function(type){
	var url;
	if(type==1) {
		url = "createIndex.action";
		}    //创建根资源
	else if(type==2){
		//if($('#ls').val() == 0){
		url = "createIndex.action?id="+$('#rateIndexId').val(); 
		//}else{
		//Confirm.show('提示', '该指标下已经增加了评分标准，不能再新建指标！');	
		//} 
		}   //创建子资源
	else{
		url = "editRateIndex.action?id="+$('#rateIndexId').val();
	}
	$("#myModal").load(url,function() {
		$("#myModal").modal('show');   
	});
};

RateIndex.submit = function(){
var rateIndexId = $("#parentId").val();
var flag = true;
//校验必填项
$(".form-control").each(function() {
	var val=$(this).val();
	if(val.length>0){
		$(this).next("span").html("");
	}else{
		$(this).next("span").html("不能为空！");
		flag=false;
	}

});
if(flag){  
	$.ajax({
		cache: true,
		type: "post",
		url: "saveRateIndex.action?id="+rateIndexId,
		data: $('#rateIndexForm').serialize(),
		async: true,
		error: function(request) {
		},
		success: function(result) {
			var rArr = result.split(";");
            if (rArr[0]=="true"){
            	$("#myModal").modal('hide');
            	var zTree = $.fn.zTree.getZTreeObj("rateIndexTree");
				var node = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
				zTree.reAsyncChildNodes(node, "refresh");
            }else{
            	Confirm.show('提示', rArr[1]);
            }
			}
		});
	}
};
RateIndex.deleteAll = function(){
	Confirm.show('提示', '是否确认要删除当前指标信息？',{
    	'删除': {
            'primary': true,
            'callback': function() {
            	$.post('deleteRateIndex.action',{'id':$('#rateIndexId').val()},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	Confirm.hide();
                    	
                    	var zTree = $.fn.zTree.getZTreeObj("rateIndexTree");
        				var node = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
        				zTree.reAsyncChildNodes(node, "refresh");
                    }
                });
            }
        }
    });
};
RateIndex.submitEdit = function(){
	var types = $("#types").val();
	var flag = true;
	//校验必填项
	$(".form-control").each(function() {
		var val=$(this).val();
		if(val.length>0){
			$(this).next("span").html("");
		}else{
			$(this).next("span").html("不能为空！");
			flag=false;
		}

	});
	if(flag){  
		$.ajax({
			cache: true,
			type: "post",
			url: "saveRateIndexEdit.action?types="+types,
			data: $('#rateIndexEdForm').serialize(),
			async: true,
			error: function(request) {
				alert("error");
			},
			success: function(result) {
				var rArr = result.split(";");
	            if (rArr[0]=="true"){
	            	$("#myModal").modal('hide');
	            	var zTree = $.fn.zTree.getZTreeObj("rateIndexTree");
					var node = zTree.getNodesByFilter(function (node) { return node.level == 0 }, true);
					zTree.reAsyncChildNodes(node, "refresh");
	            }else{
	            	Confirm.show('提示', rArr[1]);
	            }
				}
			});
		}
	};


/** RateIndex End*/

/** RateLevel Begin*/
var RateLevel = new Object();
/*
 * 创建信用等级
 */
RateLevel.create=function(){
   $("#myModal").load("createRateLevel.action",function() {
     $("#myModal").modal('show');   
		});
};
/*
 * 保存信用等级
 */
RateLevel.submit=function(){
	var flag = true;
	//校验必填项
	$(".form-control").each(function() {
		var val=$(this).val();
		if(val.length>0){
			$(this).next("span").html("");
		}else{
			$(this).next("span").html("不能为空！");
			flag=false;
		}
	});
	if(flag){
	    $.ajax({
			cache: true,
			type: "post",
			url: "saveRateLevel.action",
			data: $('#rateLevelForm').serialize(),
			async: true,
			error: function(request) {
				alert("error");
			},
			success: function(result) {
				var rArr = result.split(";");
		          if (rArr[0]=="true"){
		           	$("#myModal").modal('hide');
		           	$('#rateLevelListTable').DataTable().ajax.reload();
		           }else{
		            Confirm.show('提示', rArr[1]);
		           }
			}
		});
	    }
	};

RateLevel.edit=function(id){
	$("#myModal").load("editRateLevel.action",{"rateLevel.id":id},function() {
		$("#myModal").modal('show');   
	});	
}

RateLevel.query=function(id){
	$("#myModal").load("queryRateLevel.action",{"rateLevel.id":id},function() {
		$("#myModal").modal('show');   
	});	
};

RateLevel.deleteAll=function(id){
	Confirm.show('提示', '是否确认要删除所选信用等级？',{
    	'删除': {
            'primary': true,
            'callback': function() {
            	$.post('deleteRateLevel.action',{'rateLevel.id':id},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	Confirm.hide();
                        $('#rateLevelListTable').DataTable().ajax.reload();
                    }
                });
            }
        }
    });
};

/** RateLevel end*/

/**RateTemplate start **/
var RateTemplate = new Object();
RateTemplate.openIndexManage = function(id){
	var url = "loadRateTempIndexManage.action?rateTemplate.id="+id;
	$('#content-wrapper').load(url);
};
RateTemplate.openIndexReport = function(id){
	 window.open("viewRateIndexReport.action?rateTemplate.id="+id);
};
RateTemplate.create = function(){
 $("#myModal").load("createRateTemplate.action",function() {
		 $("#myModal").modal('show');   
    });	
};
RateTemplate.submit = function(){
	/*var areaids = "";
	$('#arealists option:selected').each(function(i){
		if(areaids==""){
			areaids = ""+$(this).val()+"";
        }else{
        	areaids += ","+$(this).val()+"";
        }
    }); 
	$('#areaids').val(areaids);
	
	if(areaids==""){
		Confirm.show('提示', "请为该评级模板配置所属地区！");		$("#arealists").next("span").html("请为该评级模板配置所属地区！");
		return;
	}*/
	
	$('#rateTemplateForm').data('bootstrapValidator').validate();  
	  if($('#rateTemplateForm').data('bootstrapValidator').isValid()){  
	    $.ajax({
			cache: true,
			type: "post",
			url: "saveRateTemplate.action",
			data: $('#rateTemplateForm').serialize(),
			async: true,
			error: function(request) {
				alert("error");
			},
			success: function(result) {
				var rArr = result.split(";");
		          if (rArr[0]=="true"){
		           	$("#myModal").modal('hide');
		           	$('#rateTempListTable').DataTable().ajax.reload();
		           }else{
		            Confirm.show('提示', rArr[1]);
		           }
			}
		});
	   }else{
		$('#rateTemplateForm').submit();
	   }
};



RateTemplate.edit = function(id,status){
	if (status==0) {
		$("#myModal").load("editRateTemplate.action",{"rateTemplate.id":id},function() {
			$("#myModal").modal('show');   
		});	
	}else{
		Confirm.show('提示', '评级模板已启用,请停用后编辑!');
	}
	
};

	
RateTemplate.deleteAll = function(id,status){
	if (status==0) {
		Confirm.show('提示', '是否确认要删除所选评级模板？',{
			'删除': {
				'primary': true,
				'callback': function() {
					$.post('deleteTemplate.action',{'rateTemplate.id':id},function(result){
						var rArr = result.split(";");
						if (rArr[0]=="false"){
							Confirm.show('错误提示', rArr[1]);
						} else {
							Confirm.hide();
							$('#rateTempListTable').DataTable().ajax.reload();
						}
					});
				}
			}
		});
	}else {
		Confirm.show('提示', '评级模板已启用,请停用后删除!');
	}
};
RateTemplate.enable = function(){
	var ids = "";
	$(".checkchild:checked").each(function(i){ 
      if(ids==""){
        	ids = ""+$(this).val()+"";
        }else{
        	ids += ","+$(this).val()+"";
        }
    }); 
	
	if(ids==""){
		Confirm.show('提示', '请选择你要停/启用评级模板！');
	}else{
		Confirm.show('提示', '是否确认要停/启用评级模板？',{
	    	'启用': {
	            'primary': true,
	            'callback': function() {
	            	$.post('enableRateTemplate.action',{'ids':ids},function(result){
	               	 var rArr = result.split(";");
	                    if (rArr[0]=="false"){
	                    	Confirm.show('错误提示', rArr[1]);
	                    } else {
	                    	Confirm.hide();
	                    	$('#rateTempListTable').DataTable().ajax.reload();
	                    }
	                });
	            }
	        }
	    });
	}
};

RateTemplate.openRateTempIndexScore = function(id){
	var url = "loadRateTempIndexScore.action?rateTemplate.id="+id;
	$('#content-wrapper').load(url);
};
/**RateTemplate end **/
/**RateTempIndexScore start **/
var RateTempIndexScore = new Object();
RateTempIndexScore.create = function(){
	var url;
	var isParent = false;
	var treeObj = $.fn.zTree.getZTreeObj("rateTempIndexScoreTree");
	var sNodes = treeObj.getSelectedNodes();
	if (sNodes.length > 0) {
		isParent = sNodes[0].isParent;
	}
	if(isParent == true){
		Confirm.show('提示', '不能在父节点下创建评分标准选项！');
	}else{
		url = "createIndexTempScore.action?ids="+$('#ids').val(); 
	}
	$("#myModal").load(url,function() {
		$("#myModal").modal('show');   
	});
	  //创建选项资源
};
RateTempIndexScore.submit = function(){
	var flag = true;
	var rateid = $("#rateId").val();
	//校验必填项
	$(".form-control").each(function() {
		var val=$(this).val();
		if(val.length>0){
			$(this).next("span").html("");
		}else{
			$(this).next("span").html("不能为空！");
			flag=false;
		}
	});
	if(flag){
		var status=true;
		var num = parseInt($("#num").val());
		var arr = new Array();
		var n=0;
		var error="值 ";
//		for(var i=65;i<num;i++){
//			var nextChar = String.fromCharCode(i);
//			if(arr.length>0){
//				for(var j =0; j<arr.length; j++){
//					var data=arr[j].split("@");
//					if(data[0]==$("#"+nextChar+"0").val()){
//						status=false;
//						error=error+data[0]+";";
//					}
//				}
//			}
//			var b = $("#"+nextChar+"0").val();
//			arr[n]=b;
//			n=n+1;
//		}
		if(!status){
			Confirm.show('提示', error+"存在相同的值，请修改");
		}
		else{
			$.ajax({
				cache: true,
				type: "post",
				url: "saveRateTempScore.action?id="+rateid,
				data: $('#rateIndexOptionForm').serialize(),
				async: true,
				error: function(request) {
					alert("error");
				},
				success: function(result) {
					var rArr = result.split(";");
		            if (rArr[0]=="true"){
		            	$("#myModal").modal('hide');
	                    $('#rateIndexListTable').DataTable().ajax.reload();
		            }else{
			            Confirm.show('提示', rArr[1]);
			           }
				}
			});
		}
	}
};
RateTempIndexScore.deleteOptions = function(id){
	Confirm.show('提示', '是否确认要删除所选评分标准选项？',{
    	'删除': {
            'primary': true,
            'callback': function() {
            	$.post('deleteRateIndexScore.action',{'rateTempIndexScore.id':id},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	Confirm.hide();
                        $('#rateIndexListTable').DataTable().ajax.reload();
                    }
                });
            }
        }
    });
};
/**RateTempIndexScore end **/
