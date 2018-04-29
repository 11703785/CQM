$.extend($.fn.validatebox.defaults.rules,
		{
			anc : {
				validator : function(value) {
					return /^[^\s\uA1A1]/.test(value)
							&& /^[\u4E00-\u9FA5\uFE30-\uFFA0\u0020-\u007E\u3001-\u3002]+$/
									.test(value) && /[^\s\uA1A1]$/.test(value);
				},
				message : "只能包含中文、字母、数字、半角字符"
			},
			num : {
				validator : function(value) {
					return /^[0-9]+$/.test(value);
				},
				message : "只能包含数字"
			},
			tel : {
				validator : function(value) {
					return /^[0-9\-]+$/.test(value);
				},
				message : "只能包含数字、-"
			},
			zip : {
				validator : function(value) {
					return /^[1-9][0-9]{5}$/.test(value);
				},
				message : "只能为数字且开头不能为0,共6位"
			},
			email : {
				validator : function(value) {
					return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test(value);
				},
				message : "不是有效的电子邮件格式"
			},
			forcelength : {
				validator : function(value, length) {
					var len = $.trim(value).length;
					return len == length[0];
				},
				message : "长度只能为{0}位"
			},
			updatePwd: {
				validator : function(value) {
					return /^[a-zA-Z]\w{5,17}$/.test(value);
				},
				message : "以字母开头长度在6~18之间,只能包含字符、数字和下划线"
			},
			mark : {
				validator : function(value) {
					return /^[a-zA-Z0-9\u4e00-\u9fa5\uFF00-\uFFFF@\/\。\（\）.]+$/.test(value);
				},
				message : "只能输入数字、字母、中文、全角符号、@和."
			},
			username : {
				validator : function(value) {
					return /^[a-zA-Z0-9_\-\/@]+$/i.test(value);
				},
				message : "只能包含数字、字母、'_'、'-'、'/'"
			},
			creditusername : {
				validator : function(value) {
					return /^[a-zA-Z0-9_\-\/]+$/i.test(value);
				},
				message : "只能包含数字、字母、'_'、'-'、'/'"
			},
			creditpwd : {
				validator : function(value) {
					return /^[a-zA-Z0-9_\-\#\@]+$/i.test(value);
				},
				message : "只能包含数字、字母、'_'、'-'、'@'、'#'"
			},
			credituserorg : {
				validator : function(value) {
					return /^[a-zA-Z0-9]+$/i.test(value);
				},
				message : "只能包含数字、字母"
			},
			name : {
				validator : function(value) {
					return /^[a-z0-9A-Z\\.·\u4e00-\u9fa5]*$/i.test(value);
				},
				message : "客户姓名只能包含英文、数字、中文或.特殊符号"
			},
			certno : {
				validator : function(value, params) {
					var form = params[0];
					var field = params[1];
					var o = $("#" + form).find(":input[name=" + field + "]");
					if (o == null) {
						return true;
					} else {
						var v = o.val();
						if (v == '0') {
							// 身份证验证
							if (!/^([0-9]{15}|[0-9]{17}[0-9X])$/i.test(value)) {
								return false;
							} else {
								if (value.length == 18) {
									var vc = [ "1", "0", "X", "9", "8", "7", "6", "5", "4", "3",
											"2" ];
									var intQuan = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
											8, 4, 2, 1 ];
									var total = 0;
									for (var i = 0; i < value.length - 1; i++) {
										total = total + parseInt(value.substring(i, i + 1))
												* intQuan[i];
									}
									total = total % 11;
									var y = value.substring(17);
									var z = vc[total];
									if (y == z) {
										return true;
									} else {
										return false;
									}
								}
								return true;
							}
						} else {
							// 普通证件验证
							return true;
						}
					}
				},
				message : "身份证号不符合规范."
			},
			cert: {
				validator : function(value, params) {
					var form = params[0];
					var field = params[1];
					var o = $("#" + form).find(":input[name=" + field + "]");
					if (o == null || o == '') {
						return true;
					}
					var v = o.val();
					if (v != '0') {
						return /^[a-zA-Z0-9\u4e00-\u9fa5]+$/i.test(value);
					} else {
						return true;
					}
				},
				message : "普通证件号不符合规范."
			},
			stringLength : {
				validator : function(value, params) {
					var start = params[0];
					var end = params[1];
					var l = stringLength(value);
					if(l >= start && l <= end) {
						return true;
					} else {
						return false;
					}
				},
				message : '输入字节大小介于{0}至{1}之间'
			},
			periodCheck : {
				validator : function(value, params) {
					var form = params[0];
					var flg = params[1];
					if (flg == "s") {
						var el = $("#" + form).find("input[name=" + params[3] + "]").val();
						if (el) {
							return el.replace(/\-/g, "\/") >= value.replace(/\-/g, "\/");
						} else {
							return true;
						}
					} else if (flg == "e") {
						var sl = $("#" + form).find("input[name=" + params[2] + "]").val();
						if (sl) {
							return value.replace(/\-/g, "\/") >= sl.replace(/\-/g, "\/");
						} else {
							return true;
						}
					}
					else
						return false;
				},
				message : "开始日期必须不迟于结束日期！"
			},
			timeIntervalCheck : {
				validator : function(value, params) {
					var form = params[0];
					var dateBegin = $("#" + form).find("input[name=" + params[1] + "]").val();
					var days = parseInt(params[2]);
			        var sDate = new Date(dateBegin.replace(/\-/g, "\/"));
			        var eDate = new Date(value.replace(/\-/g, "\/"));
			        var fen      = Math.abs(((eDate.getTime()-sDate.getTime())/1000)/60);
			        var distance = parseInt(fen/(24*60));
			        if(dateBegin) {
				        return distance <= days;
			        } else {
			        	return true;
			        }
				},
				message : "查询日期间隔必须小于等于{2}天！"
			},
			certnoCheck : {
				validator : function(value, params) {
					var form = params[0];
					var type = $("#" + form).find("input[name='certtype']").val();
					if (type) {
						if (value) {
							if (type == '0')
								return IdCardValidate(value);
							else
								return /^[a-zA-Z0-9\u4e00-\u9fa5]+$/i.test(value);
						} else {
							return false;
						}
					} else {
						return /^[a-zA-Z0-9\u4e00-\u9fa5]+$/i.test(value);
					}

				},
				message : "请输入正确的证件号码！"
			},
			eqPassword : {/* 校验两次输入的密码是否一致 */
				validator : function(value, param) {
					return value == $(param[0]).val();
				},
				message : '两次输入的密码不一致'
			},
			loancardCheck : {/* 校验贷款卡编码是否合法 */
				validator : function(value) {
					if(value){
						return checkLoanCardno(value);
					}
				},
				message : '中征码错误'
			}
		});

var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ]; // 加权因子
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]; // 身份证验证位值.10代表X
function IdCardValidate(idCard) {
	idCard = trim(idCard.replace(/ /g, "")); // 去掉字符串头尾空格
	if (idCard.length == 15) {
		return isValidityBrithBy15IdCard(idCard); // 进行15位身份证的验证
	} else if (idCard.length == 18) {
		var a_idCard = idCard.split(""); // 得到身份证数组
		if (isValidityBrithBy18IdCard(idCard)
				&& isTrueValidateCodeBy18IdCard(a_idCard)) { // 进行18位身份证的基本验证和第18位的验证
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}
/**
 * 判断身份证号码为18位时最后的验证位是否正确
 * 
 * @param a_idCard
 *          身份证号码数组
 * @return
 */
function isTrueValidateCodeBy18IdCard(a_idCard) {
	var sum = 0; // 声明加权求和变量
	if (a_idCard[17].toLowerCase() == 'x') {
		a_idCard[17] = 10; // 将最后位为x的验证码替换为10方便后续操作
	}
	for (var i = 0; i < 17; i++) {
		sum += Wi[i] * a_idCard[i]; // 加权求和
	}
	valCodePosition = sum % 11; // 得到验证码所位置
	if (a_idCard[17] == ValideCode[valCodePosition]) {
		return true;
	} else {
		return false;
	}
}
/**
 * 验证18位数身份证号码中的生日是否是有效生日
 * 
 * @param idCard
 *          18位书身份证字符串
 * @return
 */
function isValidityBrithBy18IdCard(idCard18) {
	var year = idCard18.substring(6, 10);
	var month = idCard18.substring(10, 12);
	var day = idCard18.substring(12, 14);
	var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
	// 这里用getFullYear()获取年份，避免千年虫问题
	if (temp_date.getFullYear() != parseFloat(year)
			|| temp_date.getMonth() != parseFloat(month) - 1
			|| temp_date.getDate() != parseFloat(day)) {
		return false;
	} else {
		return true;
	}
}
/**
 * 验证15位数身份证号码中的生日是否是有效生日
 * 
 * @param idCard15
 *          15位书身份证字符串
 * @return
 */
function isValidityBrithBy15IdCard(idCard15) {
	var year = idCard15.substring(6, 8);
	var month = idCard15.substring(8, 10);
	var day = idCard15.substring(10, 12);
	var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
	// 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法
	if (temp_date.getYear() != parseFloat(year)
			|| temp_date.getMonth() != parseFloat(month) - 1
			|| temp_date.getDate() != parseFloat(day)) {
		return false;
	} else {
		return true;
	}
}
// 去掉字符串头尾空格
function trim(str) {
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

// 字节长度
function stringLength(s) {
    var totalLength = 0;
    var i;
    var charCode;
    if(s == null) {
    	return totalLength;
    }
    for (i = 0; i < s.length; i++) {
      charCode = s.charCodeAt(i);
      if (charCode < 0x007f) {
        totalLength = totalLength + 1;
      } else if ((0x0080 <= charCode) && (charCode <= 0x07ff)) {
        totalLength += 2;
      } else if ((0x0800 <= charCode) && (charCode <= 0xffff)) {
        totalLength += 3;
      }
    }
    return totalLength;
}

//校验贷款卡编码
function checkLoanCardno(loanCardNo) {
     if (trim(loanCardNo).length != 16)
        return false;     
     var checkCode;
     var weightValue = new Array();
     var checkValue = new Array();
     var totalValue = 0;
     var c = 0;
     // 生成的校验码
     var str1;
     checkCode = trim(loanCardNo).substring(0, 14);   
     for (var i = 0; i < 14; i++) {
        var tempvalue = checkCode.charCodeAt(i);
        if (tempvalue >= "a".charCodeAt() && tempvalue <= "z".charCodeAt()) {
            return false;
        }
     }
     weightValue[0] = 1;
     weightValue[1] = 3;
     weightValue[2] = 5;
     weightValue[3] = 7;
     weightValue[4] = 11;
     weightValue[5] = 2;
     weightValue[6] = 13;
     weightValue[7] = 1;
     weightValue[8] = 1;
     weightValue[9] = 17;
     weightValue[10] = 19;
     weightValue[11] = 97;
     weightValue[12] = 23;
     weightValue[13] = 29;
     
     for (var j = 0; j < 3; j++) {
         var tempValue = checkCode.substring(j, j+1);
         if (tempValue >= "A" && tempValue <= "Z") {
             checkValue[j] = tempValue.charCodeAt() - 55;
         } else {
             checkValue[j] = tempValue;
         }
         
         totalValue = totalValue + weightValue[j] * checkValue[j];
     }
     
     for (var j = 3; j < 14; j++) {
     	checkValue[j] = checkCode.substring(j, j+1);
     	totalValue = totalValue + weightValue[j] * checkValue[j];
     }
     
     c = 1 + (totalValue % 97);
     str1 = String(c);
     
     if (str1.length == 1) {
     	str1 = '0' + str1;
     }
     
     if (str1 == trim(loanCardNo).substring(14))  
       return true;
     else 
       return false;         
 };