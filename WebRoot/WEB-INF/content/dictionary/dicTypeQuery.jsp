<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
</script>
<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h4 class="modal-title" id="myModalLabel">
				字典类型查看
			</h4>
		</div>
		<div class="modal-body">
			<form class="form-horizontal" id="dicTypeForm">
			  <input name="dictype.id" value="${dicType.id}" id="dicType.id" hidden="true">
              <div class="box-body">
                <div class="form-group">
                  <label class="col-sm-3 control-label">字典类型名称：</label>
                  <div class="col-sm-9">
                    <input class="form-control" id="name" disabled="disabled" name="dicType.name" value="${dicType.name}" >
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-3 control-label">字典类型编码：</label>
                  <div class="col-sm-9">
                    <input class="form-control" disabled="disabled" id="code" name="dicType.code" value="${dicType.code}" >
                  </div>
                </div>
             </div>
           </form>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		</div>
	</div>
</div>