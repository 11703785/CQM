var rjhc = rjhc || {};
$.ajaxSetup({
    contentType: 'application/json'
});
/**
 * 动态生成加载窗口
 *
 * @param options
 * @returns
 */
rjhc.showDialog = function (options) {
    var dialog = $("<div/>").appendTo("body").attr("id", options.id);
    $(dialog).data("key", options);
    var cfg = {
        border: false,
        resizable: false,
        collapsible: false,
        minimizable: false,
        maximizable: false,
        cache: false,
        closable: false,
        /** 关闭面板之后 */
        onClose: function () {
            $(dialog).window('destroy');
        },
        close: function () {
            $(dialog).window('destroy');
        }
    };
    $.extend(cfg, options);
    $(dialog).window(cfg);
    return $(dialog);
};
rjhc.convert = function (data) {
    return (new Function("return " + data))();
};
/**
 * 日期比较
 */
function validatorDate(s, e) {
    var arr = s.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();
    var arrs = e.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();
    if (lktimes - starttimes === 0) {
        return 0;
    } else if (lktimes - starttimes > 0) {
        return 1;
    } else if (lktimes - starttimes < 0) {
        return -1;
    }
}
$(function(){
	$.fn.combotree.defaults.editable = true;
	$.extend($.fn.combotree.defaults.keyHandler,{
		up:function(){
			console.log('up');
		},
		down:function(){
			console.log('down');
		},
		enter:function(){
			console.log('enter');
		},
		query: function(q){
			var t =$(this).combotree('tree');
			var nodes = t.tree('getChildren');
			for(var i = 0; i< nodes.length; i++){
				var node = nodes[i];
				if(node.text.indexOf(q) >= 0){
					$(node.target).show();
				} else {
					$(node.target).hide();
				}
			}
			var opts = $(this).combotree('options');
			if(!opts.hasSetEvents){
				opts.hasSetEvents = true;
				var onShowPanel = opts.onShowPanel;
				opts.onShowPanel = function(){
					var nodes = t.tree('getChildren');
					for(var i = 0; i< nodes.length; i++){
						$(nodes[i].target).show();
					}
					onShowPanel.call(this);
				}
				$(this).combo('options').onShowPanel = opts.onShowPanel;
			}
		}
	});
});
// 日期间隔天数
function diff(s, e, d) {
    var arr = s.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();
    var arrs = e.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();
    iDays = parseInt(Math.abs(lktimes - starttimes) / 1000 / 60 / 60 / 24);
    if (iDays < d) {
        return false;
    } else {
        return true;
    }
}
// 日期间隔天数
function diffDay(s, e) {
    //var arr = s.split("-");
    var starttime = new Date(s.replace(/-/g, "/"));
    var starttimes = starttime.getTime();
    //var arrs = e.split("-");
    var lktime = new Date(e.replace(/-/g, "/"));
    var lktimes = lktime.getTime();
    iDays = parseInt((lktimes - starttimes) / (1000 * 60 * 60 * 24));
    return (iDays + 1);
}
function navClick(id, node) {
    if ($(id).tree('isLeaf', node.target)) {
        if ($('#frametabs').tabs('exists', node.text)) {
            $('#frametabs').tabs('select', node.text);
        } else {
            addTab(node);
        }
    } else {
        $(id).tree('toggle', node.target);
    }
};
function addTab(node) {
    $('#frametabs').tabs('add', {
        title: node.text, href: node.attributes.url, iconCls: 'icon-save', //content : "<iframe src='"+node.attributes.url+"' frameborder='0' width='100%'	height='100%'></iframe>",
        style: 'padding:10px', closable: true
    });
};
function getFormObject(form) {
    var a = serializeForm(form);
    var o = {};
    $.each(a, function () {
        if (this.value) {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        }
    });
    return o;
};
function serializeForm(form) {
    return form.map(function () {
        return this.elements ? $.makeArray(this.elements) : this
    }).filter(function () {
        return this.name &&
            (this.checked || /select|textarea/i.test(this.nodeName) || /color|date|datetime|email|hidden|month|number|password|range|search|tel|text|time|url|week/i.test(this.type))
    }).map(function (a, b) {
        a = $(this).val();
        return a == null ? null : $.isArray(a) ? $.map(a, function (d) {
            return {name: b.name, value: d}
        }) : {name: b.name, value: a}
    }).get();
}
function loader(_224, _225, _226) {
    var target = $(this);
    var opts = target.datagrid("options");
    if (!opts.url) {
        return false;
    }
    $.ajax({
        type: "post", url: opts.url, data: JSON.stringify(_224), dataType: "json", success: function (data) {
            if (data.status) {
                target.datagrid("clearSelections");
                target.datagrid("clearChecked");
                _225(data);
            } else {
                responseerror(data);
            }
        }, error: ajaxerror
    });
}
function creategrid(model, idField, singleSelect) {
    if (singleSelect == undefined) {
        singleSelect = true;
    }
    var formObj = $("#" + model + "_qf");
    if (formObj != null) {
        formObj.form('clear');
    }
    $("#" + model + "_dg").datagrid({
        rownumbers: true,
        singleSelect: singleSelect,
        height: 15,
        pagination: true,
        pageList: [10, 20, 30],
        pageSize: 20,
        fitColumns: true,
        fit: true,
        url: model + "/find",
        toolbar: "#" + model + "_tb",
        loader: loader,
        view: detailview,
        expandrow: -1,
        idField: idField,
        detailFormatter: function (index, row) {
            return '<div class="ddv" style="padding:1px 0"></div>';
        },
        onExpandRow: function (index, row) {
            if (this.expandrow >= 0 && this.expandrow != index) {
                $(this).datagrid('collapseRow', this.expandrow);
                $("#" + model + "_df").parent().remove();
            }
            $(this).datagrid('selectRow', index);
            var grid = this;
            var ddv = $(this).datagrid('getRowDetail', index).find('div.ddv');
            ddv.panel({
                height: "auto",
                border: false,
                cache: false,
                href: model + "/showdetail/" + eval("row." + $(this).datagrid('options').idField),
                onLoad: function () {
                    $(grid).datagrid('fixDetailRowHeight', index);
                    disableform(model + "_df");
                }
            });
            $(grid).datagrid('fixDetailRowHeight', index);
            this.expandrow = index;
        },
        onCollapseRow: function (index, row) {
            if (this.expandrow == index) {
                this.expandrow = -1;
                $("#" + model + "_df").parent().remove();
            }
        }
    });
}
function disableform(formid) {
    var field = $("#" + formid).find('span input[disabled]');
    field.addClass('textbox-text-disabled');
    field.next().removeAttr("disabled");
    field.parent().addClass('textbox-text-disabled');
}
function responseerror(data) {
    if (data.error === "loginfailure") {
        $.messager.alert('登录信息失效', '登录信息失效，请重新登录系统！', 'error', function () {
            window.top.location = 'login';
        });
    } else {
        $.messager.alert('操作失败', data.error, 'error');
    }
}
function logout() {
    var logoutURL = "login/logout";
    var userAgent = navigator.userAgent.toLowerCase();
    if (userAgent.indexOf("msie") > -1) { // IE
        $.ajax({
            type: "post", url: logoutURL, crossDomain: true, async: false, dataType: "jsonp"
        });
    } else { // FireFox Chrome
        $.ajax({
            type: "post", url: logoutURL, async: false
        });
    }
    window.top.location = 'login';
}
function ajaxerror() {
    $.messager.alert("操作失败", "系统异常，请联系管理员");
}
function addrow(model, modelName) {
    var formObj = $("#" + model + "_af");
    if (formObj.form("validate")) {
        var opts = formObj.form("options");
        var form = getFormObject(formObj);
        $.ajax({
            type: "post",
            url: opts.url,
            async: false,
            data: JSON.stringify(form),
            dataType: "json",
            success: function (data) {
                if (data.status) {
                    $.messager.alert("新增成功", modelName + " 新增成功", "info", function () {
                        $("#" + model + "_dg").datagrid('load');
                        $("#" + model + "_newwin").window('close');
                    });
                } else {
                    $.messager.alert("新增失败", data.error, 'error');
                }
            },
            error: ajaxerror
        });
    }
}
function deleterow(model, modelName) {
    var idField = $("#" + model + "_dg").datagrid('options').idField;
    var id = eval("form." + idField);
    $.messager.confirm("确认", "是否删除" + modelName + " " + id + " ?", function (r) {
        if (r) {
            $.ajax({
                type: "delete", url: model + "/" + id, dataType: "json", success: function (data) {
                    if (data.status) {
                        $.messager.alert("删除成功", modelName + " " + id + " 删除成功", "info", function () {
                            $("#" + model + "_df").parent().remove();
                            $("#" + model + "_dg").datagrid("reload");
                        });
                    } else {
                        $.messager.alert("删除失败", data.error, 'error');
                    }
                }, error: ajaxerror
            });
        }
    });
}
function changeTheme(themeName) {/* 更换主题 */
    var $easyuiTheme = $('#easyuiTheme');
    var url = $easyuiTheme.attr('href');
    var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
    $easyuiTheme.attr('href', href);
    var $iframe = $('iframe');
    if ($iframe.length > 0) {
        for (var i = 0; i < $iframe.length; i++) {
            var ifr = $iframe[i];
            $(ifr).contents().find('#easyuiTheme').attr('href', href);
        }
    }
    $.cookie('easyuiThemeName', themeName, {
        expires: 7
    });
}
function changepwd() {
    new rjhc.showDialog({
        id: 'changepwd_newwin',
        modal: true,
        closed: true,
        iconCls: 'icon-edit',
        draggable: false,
        width: 300,
        title: "修改密码",
        height: 200,
        href: 'user/showchangepwd'
    }).window('open');
}
function queryrow(model) {
    var search = $("#" + model + "_search_input");
    if (search.length > 0) {
        search.searchbox('setValue', '');
    }
    var formObj = $("#" + model + "_qf");
    if (formObj.form("validate")) {
        $("#" + model + "_dg").datagrid("load", getFormObject(formObj));
        $("#" + model + "_querywin").window('close');
    }
}
function updaterow(model, modelName) {
    var formRef = $("#" + model + "_df");
    if (!formRef.form('validate')) {
        return false;
    }
    var form = getFormObject(formRef);
    var idField = $("#" + model + "_dg").datagrid('options').idField;
    $.messager.confirm("确认", "是否修改" + "?", function (r) {
        if (r) {
            $.ajax({
                type: "put", url: model, data: JSON.stringify(form), dataType: "json", success: function (data) {
                    if (data.status) {
                        $.messager.alert("修改成功", " 修改成功", "info", function () {
                            $("#" + model + "_dg").datagrid("reload");
                        });
                    } else {
                        $.messager.alert("修改失败", data.error, 'error');
                    }
                }, error: ajaxerror
            });
        }
    });
}
function orgTreeExpand(obj, node) {
    $(obj).tree("options").url = 'org/orgtree/' + node.id;
}
$(function () {
    $.extend($.fn.window.defaults, {closable: true});
    $.extend($.fn.combobox.defaults, {
        formatter: function (row) {
            var _5f = $(this).combobox("options");
            if (row[_5f.textField] == "") {
                return "&nbsp;";
            } else {
                return row[_5f.textField];
            }
        },
        loadFilter: function (_65) {
            var opts = $(this).combobox("options");
            if (opts.required) {
                return _65;
            } else {
                var obj = {};
                obj[opts.valueField] = "";
                obj[opts.textField] = "";
                var objArr = $.makeArray(obj);
                objArr = $.merge(objArr, _65);
                return objArr;
            }
        }
    });
})
function certTypeFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.cerType]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}
function archiveStatusFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.archiveStatus]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}
function archiveTypeFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.archiveType]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}
function queryReasonFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.queryReason]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}
function queryTypeFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.queryType]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}
function queryFormatFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.queryFormat]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}
function checkStatusFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.checkStatus]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}
function queryStatusFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.queryStatus]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}
function resultStatusFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.resultStatus]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}
function querySourceFormatter(value, row, index) {
    var arr = appDicMap[appDicKey.querySource]
    for (var i = 0; i < arr.length; i++) {
        if (value === arr[i].key) {
            return "<span style='color:green;'>" + arr[i].value + "</span>";
        }
    }
    return "<span style='color:green;'>" + value + "</span>";
}