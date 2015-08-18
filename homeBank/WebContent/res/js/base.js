var Love={
	/*Json 工具类*/
	isJson:function(str){
		var obj = null; 
		try{
			obj = Love.paserJson(str);
		}catch(e){
			return false;
		}
		var result = typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length; 
		return result;
	},
	paserJson:function(str){
		return eval("("+str+")");
	},
	/*弹出框*/
	alert:function(title, msg, icon, callback){
		$.messager.alert(title,msg,icon,callback);
	},
	/*弹出框*/
	confirm:function(title, msg,callback){
		$.messager.confirm(title,msg,callback);
	},
	progress:function(title,msg){
		 var win = $.messager.progress({  
            title: title ||'Please waiting',  
            msg: msg ||'Loading data...'  
         }); 
	},
	closeProgress:function(){
		$.messager.progress('close');
	},
	/*重新登录页面*/
	toLogin:function(){
		window.top.location= urls['msUrl']+"/login.s";
	},
	checkLogin:function(data){//检查是否登录超时
		if(data.logoutFlag){
			Love.closeProgress();
			Love.alert('提示',"登录超时,点击确定重新登录.",'error',Love.toLogin);
			return false;
		}
		return true;
	},
	ajaxSubmit:function(form,option){
		form.ajaxSubmit(option);
	},
	ajaxJson: function(url,option,callback){
		$.ajax(url,{
			type:'post',
			 	dataType:'json',
			 	data:option,
			 	success:function(data){
			 		//坚持登录
			 		if(!Love.checkLogin(data)){
			 			return false;
			 		}		 	
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error:function(response, textStatus, errorThrown){
			 		try{
			 			Love.closeProgress();
			 			var data = $.parseJSON(response.responseText);
				 		//检查登录
				 		if(!Love.checkLogin(data)){
				 			return false;
				 		}else{
					 		Love.alert('提示', data.msg || "请求出现异常,请联系管理员",'error');
					 	}
			 		}catch(e){
			 			alert(e);
			 			Love.alert('提示',"请求出现异常,请联系管理员",'error');
			 		}
			 	},
			 	complete:function(){
			 	
			 	}
		});
	},
	submitForm:function(form,callback,dataType){
			var option =
			{
			 	type:'post',
			 	dataType: dataType||'json',
			 	success:function(data){
			 		if($.isFunction(callback)){
			 			callback(data);
			 		}
			 	},
			 	error:function(response, textStatus, errorThrown){
			 		try{
			 			Love.closeProgress();
			 			var data = $.parseJSON(response.responseText);
				 		//检查登录
				 		if(!Love.checkLogin(data)){
				 			return false;
				 		}else{
					 		Love.alert('提示', data.msg || "请求出现异常,请联系管理员",'error');
					 	}
			 		}catch(e){
			 			alert(e);
			 			Love.alert('提示',"请求出现异常,请联系管理员",'error');
			 		}
			 	},
			 	complete:function(){
			 	
			 	}
			 }
			 Love.ajaxSubmit(form,option);
	},
	saveForm:function(form,callback){
		if(form.form('validate')){
			Love.progress('Please waiting','Save ing...');
			//ajax提交form
			Love.submitForm(form,function(data){
				Love.closeProgress();
			 	if(data.success){
			 		if(callback){
				       	callback(data);
				    }else{
			       		Love.alert('提示','保存成功.','info');
			        } 
		        }else{
		       	   Love.alert('提示',data.msg,'error');  
		        }
			});
		 }
	},
	/**
	 * 
	 * @param {} url
	 * @param {} option {id:''} 
	 */
	getById:function(url,option,callback){
		Love.progress();
		Love.ajaxJson(url,option,function(data){
			Love.closeProgress();
			if(data.success){
				if(callback){
			       	callback(data);
			    }
			}else{
				Love.alert('提示',data.msg,'error');  
			}
		});
	},
	deleteForm:function(url,option,callback){
		Love.progress();
		Love.ajaxJson(url,option,function(data){
				Love.closeProgress();
				if(data.success){
					if(callback){
				       	callback(data);
				    }
				}else{
					Love.alert('提示',result.msg,'error');  
				}
		});
	},
	runForm:function(url,option,callback){
		Love.progress();
		Love.ajaxJson(url,option,function(data){
				Love.closeProgress();
				if(data.success){
					if(callback){
				       	callback(data);
				    }
				}else{
					Love.alert('提示',result.msg,'error');  
				}
		});
	},/*,
	getLocalTime:function(date){
		return new Date(parseInt(date) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');
	}*/
	refreshTab:function(){
		var currTab =  self.parent.$('#tab-box').tabs('getSelected'); //获得当前tab
    	var url = $(currTab.panel('options').content).attr('src');
    	if(url != null && url != "" && url != "undifined"){
    		self.parent.$('#tab-box').tabs('update', {
      			tab : currTab,
      			options : {
       				content:'<iframe src="'+url+'" width="1130" height="520" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>'
      			}
     		});
    	}
	}
}

var fileview = $.extend({}, $.fn.datagrid.defaults.view, { onAfterRender: function (target) {checkboxUtil.ischeckItem(target); } });
var checkedItems = [];
var checkboxUtil={
	ischeckItem:function(checkgrid){
		for (var i = 0; i < checkedItems.length; i++) {
            $('#'+checkgrid.id+'').datagrid('selectRecord', checkedItems[i]); //根据id选中行 
        }
	},
	findCheckedItem:function(ID){
		for (var i = 0; i < checkedItems.length; i++) {
            if (checkedItems[i] == ID) return i;
        }
        return -1;
	},
	addcheckItem:function(row){
		var rows = [row];
		//var row = checkgrid.datagrid('getChecked');
        for (var i = 0; i < rows.length; i++) {
            if (this.findCheckedItem(rows[i].id) == -1) {
                checkedItems.push(rows[i].id);
            }
        }
	},
	removeAllItem:function(rows){
		for (var i = 0; i < rows.length; i++) {
            var k = this.findCheckedItem(rows[i].id);
            if (k != -1) {
                checkedItems.splice(i, 1);
            }
        }
	},
	removeSingleItem:function(rowIndex, rowData){
		var k = this.findCheckedItem(rowData.id);
        if (k != -1) {
            checkedItems.splice(k, 1);
        }
	}
}

Date.prototype.toLocaleString = function() {
  return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + ":" + this.getMinutes() + ":" + this.getSeconds();
 };
 
/*Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "H+": this.getHours(), //小时 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}*/

/* 自定义密码验证*/
$.extend($.fn.validatebox.defaults.rules, {  
    equals: {  
        validator: function(value,param){  
            return value == $(param[0]).val();  
        },  
        message: '密码不一致'  
    }  
});  

/*表单转成json数据*/
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

/* easyui datagrid 添加和删除按钮方法*/
$.extend($.fn.datagrid.methods, {  
    addToolbarItem: function(jq, items){  
        return jq.each(function(){  
            var toolbar = $(this).parent().prev("div.datagrid-toolbar");
            for(var i = 0;i<items.length;i++){
                var item = items[i];
                if(item === "-"){
                    toolbar.append('<div class="datagrid-btn-separator"></div>');
                }else{
                    var btn=$("<a href=\"javascript:void(0)\"></a>");
                    btn[0].onclick=eval(item.handler||function(){});
                    btn.css("float","left").appendTo(toolbar).linkbutton($.extend({},item,{plain:true}));
                }
            }
            toolbar = null;
        });  
    },
    removeToolbarItem: function(jq, param){  
        return jq.each(function(){  
            var btns = $(this).parent().prev("div.datagrid-toolbar").children("a");
            var cbtn = null;
            if(typeof param == "number"){
                cbtn = btns.eq(param);
            }else if(typeof param == "string"){
                var text = null;
                btns.each(function(){
                    text = $(this).data().linkbutton.options.text;
                    if(text == param){
                        cbtn = $(this);
                        text = null;
                        return;
                    }
                });
            } 
            if(cbtn){
                var prev = cbtn.prev()[0];
                var next = cbtn.next()[0];
                if(prev && next && prev.nodeName == "DIV" && prev.nodeName == next.nodeName){
                    $(prev).remove();
                }else if(next && next.nodeName == "DIV"){
                    $(next).remove();
                }else if(prev && prev.nodeName == "DIV"){
                    $(prev).remove();
                }
                cbtn.remove();    
                cbtn= null;
            }                        
        });  
    }                 
});