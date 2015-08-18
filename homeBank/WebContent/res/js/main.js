$(function(){
	//修改密码绑定事件
	$('.modify-pwd-btn').click(function(){
		$("#pwdForm").resetForm();
		$('#modify-pwd-win').dialog('open');
	});
	$('#btn-pwd-close').click(function(){
		$('#modify-pwd-win').dialog('close');
	});
	$('#btn-pwd-submit').click(function(){
		modifyPwd();
	});
	
	//修改用户信息绑定事件
	$('.update-info-btn').click(function(){
		$("#infoForm").resetForm();
		$('#update-info-win').dialog('open');
	});
	$('#btn-info-close').click(function(){
		$('#update-info-win').dialog('close');
	});
	$('#btn-info-submit').click(function(){
		updateInfo();
	});
	
	//新增用户信息绑定事件
	$('.add-info-btn').click(function(){
		$("#addinfoForm").resetForm();
		$('#add-info-win').dialog('open');
	});
	$('#btn-addinfo-close').click(function(){
		$('#add-info-win').dialog('close');
	});
	$('#btn-addinfo-submit').click(function(){
		addInfo();
	});
	
	//查看用户信息绑定事件
	$('.user-info-btn').click(function(){
		$('#datagrid_user').datagrid({
        url:'queryUser.do',
        singleSelect:true,
        height:$('#tabs').height() - 50 - $('#btn').height(),
        pagination:true,
        pageSize:10,
        pageList:[10,20,30,50],
        columns:[[
            {field:'id',title:'id',hidden:true},
            {field:'username',title:'用户名',width:100},
            {field:'nickname',title:'昵称',width:100},
            {field:'createTime',title:'创建时间',width:150,sortable:true,
				formatter:function(value,row,index){  
					if(value != null && value != ''){
						var unixTimestamp = new Date(value);  
                        return unixTimestamp.toLocaleString();
					}
                }
            },
			{field:'modifyTime',title:'修改时间',width:150,sortable:true,
				formatter:function(value,row,index){  
                	if(value != null && value != ''){
						var unixTimestamp = new Date(value);  
                        return unixTimestamp.toLocaleString();
					} 
                 }
            },
            {field:'isvalid',title:'状态',width:80,align:'center',sortable:true,styler:function(value,row,index){
								if(value == 'N'){
								  return 'color:red;';  
								}
							},
							formatter:function(value,row,index){
								if(value == 'Y'){
									return "可用";
								}
								if(value == 'N'){
									return "禁用";
								}
							}}
        ]],
        toolbar: [{
            text:'启停',
            iconCls: 'icon-run',
            handler: function(){
            	var select = $('#datagrid_user').datagrid('getSelected');
                if (select == null){
                    $.messager.alert("Info","请选择记录！","Info");
                    return ;
                }
                else{
                	if(select.username != "admin"){
                		$.messager.confirm('提示信息', '确认操作吗?', function(r){
                		if (r){
                			$.ajax({
                                type: 'POST',   
                                url: 'runUser.do',
                                data: 'username='+select.username,
                                dataType:'text',
                                success: function(msg){
                                    var temp = $.parseJSON(msg); 
                                    if (temp.success) {
                                        $.messager.alert('操作成功！',temp.msg,'Info');
                                    }
                                    else{
                                        $.messager.alert('操作失败',temp.msg,'Info');
                                    }
                                    $('#datagrid_user').datagrid('load');
                                  }
                            });
                		}
                	});
                	}else{
                		Love.alert('警告','不能对管理员进行该操作','warning');  
                	}
                }
            }
        },{
            text:'删除',
            iconCls: 'icon-remove',
            handler: function(){
            	var select = $('#datagrid_user').datagrid('getSelected');
                if (select == null){
                    $.messager.alert("Info","请选择记录！","Info");
                    return ;
                }
                else{
                	if(select.username != "admin"){
                		$.messager.confirm('提示信息', '确认删除吗?', function(r){
                		if (r){
                			$.ajax({
                                type: 'POST',   
                                url: 'deleteUser.do',
                                data: 'id='+select.id,
                                dataType:'text',
                                success: function(msg){
                                    var temp = $.parseJSON(msg); 
                                    if (temp.success) {
                                        $.messager.alert('删除成功！',temp.msg,'Info');
                                    }
                                    else{
                                        $.messager.alert('删除失败',temp.msg,'Info');
                                    }
                                    $('#datagrid_user').datagrid('load');
                                  }
                            });
                		}
                	});
                	}else{
                		Love.alert('警告','不能对管理员进行该操作','warning');  
                	}
                }
            }
        }],
        onLoadSuccess:function(data){
        }
    });
		$('#user-info-win').dialog('open');
	});
})

function modifyPwd(){
			var pwdForm = $("#pwdForm");
			if(pwdForm.form('validate')){
				Love.saveForm(pwdForm,function(data){
					if(data.success){
						$('#modify-pwd-win').dialog('close');
				    	pwdForm.resetForm();
			       		Love.alert('提示',data.msg,'info',function(){
			       			window.location.href="/homeBank/j_spring_security_logout";
			       		});
		        	}else{
		       	  	 Love.alert('提示',data.msg,'error');  
		        	}
				});
			 }
		}
		
function updateInfo(){
			var infoForm = $("#infoForm");
			if(infoForm.form('validate')){
				Love.saveForm(infoForm,function(data){
					if(data.success){
						$('#update-info-win').dialog('close');
				    	infoForm.resetForm();
			       		Love.alert('提示',data.msg,'info');
		        	}else{
		       	  	 Love.alert('提示',data.msg,'error');  
		        	}
				});
			 }
		}
		
function addInfo(){
			var infoForm = $("#addinfoForm");
			if(infoForm.form('validate')){
				Love.saveForm(infoForm,function(data){
					if(data.success){
						$('#add-info-win').dialog('close');
				    	infoForm.resetForm();
			       		Love.alert('提示',data.msg,'info');
		        	}else{
		       	  	 Love.alert('提示',data.msg,'error');  
		        	}
				});
			 }
		}