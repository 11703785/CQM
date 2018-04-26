var RateTask = new Object();

RateTask.render = function(data,type,row,meta) {
	var result = "";
	var str = row.statusStr;
	if(str=="暂存"){
		result = "<a href='#' onclick=\"RateTask.edit('"+data+"')\">编辑</a>&nbsp;&nbsp;"+
					"<a href='#' onclick=\"RateTask.deleteAll('"+data+"')\">删除</a>&nbsp;&nbsp;"+
					"<a href='#' onclick=\"RateTask.publishTask('"+data+"')\">发布</a>&nbsp;&nbsp;"+
					"<a href='#' onclick=\"RateTask.viewTask('"+data+"')\">详情</a>";
	}else if(str=="已发布"){
		result = "<span style='color: #969696;'>编辑</span>&nbsp;&nbsp;"+
					"<span style='color: #969696;'>删除</span>&nbsp;&nbsp;"+
					"<a href='#' onclick=\"RateTask.publishTask('"+data+"')\">停用</a>&nbsp;&nbsp;"+
					"<a href='#' onclick=\"RateTask.viewTask('"+data+"')\">详情</a>";
	}else if(str=="停用"){
		result = "<span style='color: #969696;'>编辑</span>&nbsp;&nbsp;"+
					"<span style='color: #969696;'>删除</span>&nbsp;&nbsp;"+
					"<a href='#' onclick=\"RateTask.publishTask('"+data+"')\">启用</a>&nbsp;&nbsp;"+
					"<a href='#' onclick=\"RateTask.viewTask('"+data+"')\">详情</a>";
	}
	return result;
}

/*
 * 创建评级任务
 */
RateTask.create = function(){
	$("#myModal").load("createRateTask.action",function() {
		$("#myModal").modal('show');   
	});
};

/*
 * 编辑评级任务
 */
RateTask.edit = function(id){
	$("#myModal").load("editRateTask.action",{"rateTask.id":id},function() {
		$("#myModal").modal('show');   
	});
};

/*
 * 删除评级任务
 */
RateTask.deleteAll = function(id){
	Confirm.show('提示', '是否确认要删除所选评级任务？',{
    	'删除': {
            'primary': true,
            'callback': function() {
            	$.post('deleteRateTask.action',{'rateTask.id':id},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	Confirm.hide();
                        $('#rateTaskTable').DataTable().ajax.reload();
                    }
                });
            }
        }
    });
};	

/*
 * 评级任务详情 
 */
RateTask.viewTask = function(id){
	$("#myModal").load("viewReport.action",{"id":id},function() {
		$("#myModal").modal('show');   
	});
};

/*
 * 保存评级任务
 */
RateTask.submit = function(){
	
	var tempids = "";
	$('#templists option:selected').each(function(i){
		if(tempids==""){
			tempids = ""+$(this).val()+"";
        }else{
        	tempids += ","+$(this).val()+"";
        }
    }); 
	$('#tempids').val(tempids);
	
	//if(tempids==""){
		//$("#templists").next("span").html("请为该评级任务配置评级模板！");
		//return;
	//}
	if($("#rateTaskyears").val()==""){
		$("#rateTaskyears").next("span").html("请选择该评级任务的所属年份！");
		return;
	}
	
	$('#rateTaskForm').data('bootstrapValidator').validate();  
	if($('#rateTaskForm').data('bootstrapValidator').isValid()){  
		$.ajax({
			cache: true,
			type: "post",
			url: "saveRateTask.action",
			data: $('#rateTaskForm').serialize(),
			async: true,
			error: function(request) {
				//alert("error");
			},
			success: function(result) {
				var rArr = result.split(";");
				if (rArr[0]=="true"){
					$("#myModal").modal('hide');
					$('#rateTaskTable').DataTable().ajax.reload();
				}else{
					Confirm.show('提示', rArr[1]);
				}
			}
		});
	}else{
		$('#rateTaskForm').submit();
	}
}

/*
 * 发布 或 停用 任务
 */
RateTask.publishTask = function(id){
	Confirm.show('提示', '是否确认要发布/停用/启用所选评级任务？',{
    	'提交': {
            'primary': true,
            'callback': function() {
            	$.post('publishTask.action',{'rateTask.id':id},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	Confirm.hide();
                        $('#rateTaskTable').DataTable().ajax.reload();
                    }
                });
            }
        }
    });
}

/*
 * 执行任务列表
 */
RateTask.renders = function(data,type,row,meta) {
					//"<a href='#' onclick=\"RateTask.download('"+data+"')\">错误日志下载</a>";
	var str = row.statusStr;
	if(str=="暂存"){
		result = "<span style='color: #969696;'>手动执行</span>&nbsp;&nbsp;"+
					"<span style='color: #969696;'>定时执行</span>&nbsp;&nbsp;";
	}else if(str=="已发布"){
		var result = "<a href='#' onclick=\"RateTask.manualPerformTask('"+data+"')\">手动执行</a>&nbsp;&nbsp;"+
					"<a href='#' onclick=\"RateTask.timingPerformTask('"+data+"')\">定时执行</a>&nbsp;&nbsp;";
	}else if(str=="停用"){
		result = "<span style='color: #969696;'>手动执行</span>&nbsp;&nbsp;"+
					"<span style='color: #969696;'>定时执行</span>&nbsp;&nbsp;";
	}
	return result;
}

/*
 * 手动执行
 */
RateTask.manualPerformTask = function(id){
	$("#myModal").load("initManualPerformTask.action",{"id":id},function() {
		$("#myModal").modal('show');   
	});
}

/*
 * 手动执行评级任务
 */
RateTask.manualPerformSubmit = function(){
	
	var areaids = "";
	$("input[name='checkname']:checked").each(function(){
		if(areaids==""){
			areaids = $(this).val();
		}else{
			areaids += ","+$(this).val();
		}
	});
	$('#areaids').val(areaids);
	startBlockUI();//打开遮罩
	$.ajax({
		cache: true,
		type: "post",
		url: "rateTaskManual.action",
		data: $('#rateTaskManualForm').serialize(),
		async: true,
		error: function(request) {
			//alert("error");
		},
		success: function(result) {
			var rArr = result.split(";");
			closeBlockUI();
			if (rArr[0]=="true"){
				$("#myModal").modal('hide');
				Confirm.show('提示', rArr[1]);
				$('#rateTaskTable').DataTable().ajax.reload();
			}else{
				Confirm.show('错误提示', rArr[1]);
			}
		}
	});
}

/*
 * 定时执行
 */
RateTask.timingPerformTask = function(id){
	$("#myModal").load("initTimingPerformTask.action",{"id":id},function() {
		$("#myModal").modal('show');   
	});
}

RateTask.saveTimingTaskSubmit = function(){
	var areaids = "";
	$("input[name='checkname']:checked").each(function(){
		if(areaids==""){
			areaids = $(this).val();
		}else{
			areaids += ","+$(this).val();
		}
	});
	$('#areaids').val(areaids);
	
	$.ajax({
		cache: true,
		type: "post",
		url: "saveTimingTask.action",
		data: $('#rateTaskTimingForm').serialize(),
		async: true,
		error: function(request) {
			//alert("error");
		},
		success: function(result) {
			var rArr = result.split(";");
			if (rArr[0]=="true"){
				$("#myModal").modal('hide');
				Confirm.show('提示', rArr[1]);
				$('#rateTaskTable').DataTable().ajax.reload();
			}else{
				Confirm.show('错误提示', rArr[1]);
			}
		}
	});
}

/*
 * 错误日志下载
 */
RateTask.download = function(id){
	
}

/*
 * 个人评级任务执行
 */
RateTask.personalPerformTask = function(id){
	$("#myModal").load("getRateTaskByStatus.action?id="+id,function() {
		$("#myModal").modal('show');   
	});

}
RateTask.personalTask = function(){
	
	var rateSelect = document.getElementById("rateSelect").value;
	var mainid = $('#mainid').val();
	startBlockUI();//打开遮罩
	$.ajax({
		cache: true,
		type: "post",
		url: "personalPerformTask.action",
		data:  {code:rateSelect,mainid:mainid},
		async: true,
		error: function(request) {
			//alert("error");
		},
		success: function(result) {
			var rArr = result.split(";");
			closeBlockUI();
			if (rArr[0]=="true"){
				$("#myModal").modal('hide');
				Confirm.show('提示', rArr[1]);
			}else{
				Confirm.show('错误提示', rArr[1]);
			}
		}
	});

	
}
/*
 * 执行评级任务 
 */
RateTask.performTask = function(id){

	Confirm.show('提示', '是否确认要执行所选评级任务？',{
    	'执行': {
            'primary': true,
            'callback': function() {
            	$.post('performTask.action',{'rateTask.id':id},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	Confirm.show('提示', rArr[1]);
                    }
                });
            }
        }
    });
}

/************************任务日志 begin****************************/
var RateTaskLog = new Object();

RateTaskLog.formatter = function(data,type,row,meta) {
	var result = data.split(".")[0];
	return result;
}

RateTaskLog.render = function(data,type,row,meta) {
	var result = "<a href='#' onclick=\"RateTaskLog.view('"+data+"')\">详情</a>";
	return result;
}

/*
 * 日志详情 
 */
RateTaskLog.view = function(id){
	
}
/************************任务日志 end****************************/
