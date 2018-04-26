/************校验规则 begin*****************/
var Rules = new Object();

/*
 * 创建校验规则
 */
Rules.create = function(){
	$("#myModal").load("createRules.action",function() {
		$("#myModal").modal('show');   
	});
};

/*
 * 编辑校验规则
 */
Rules.edit = function(id){
	$("#myModal").load("editRules.action",{"rules.id":id},function() {
		$("#myModal").modal('show');   
	});
};

/*
 * 保存校验规则
 */
Rules.submit = function(){
	$('#rulesForm').data('bootstrapValidator').validate();  
    if($('#rulesForm').data('bootstrapValidator').isValid()){  
    	$.ajax({
			cache: true,
			type: "post",
			url: "saveRules.action",
			data: $('#rulesForm').serialize(),
			async: true,
			error: function(request) {
				alert("error");
			},
			success: function(result) {
				var rArr = result.split(";");
	            if (rArr[0]=="true"){
	            	$("#myModal").modal('hide');
	            	$('#rulesListTable').DataTable().ajax.reload();
	            }else{
	            	Confirm.show('提示', rArr[1]);
	            }
			}
		});
    }else{
		$('#rulesForm').submit();
    }
};

/*
 * 删除角色
 */
Rules.deleteAll = function(id){
	Confirm.show('提示', '是否确认要删除所选校验规则？',{
    	'删除': {
            'primary': true,
            'callback': function() {
            	$.post('deleteRules.action',{'rules.id':id},function(result){
               	 var rArr = result.split(";");
                    if (rArr[0]=="false"){
                    	Confirm.show('错误提示', rArr[1]);
                    } else {
                    	Confirm.hide();
                        $('#rulesListTable').DataTable().ajax.reload();
                    }
                });
            }
        }
    });
};
/************校验规则 end*******************/


