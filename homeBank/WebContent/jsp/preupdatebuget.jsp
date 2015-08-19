<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path ;
%>

<script>
$.extend($.fn.validatebox.defaults.rules, {
	month:{
		validator : function(value) {
			var reg = /^\d{4}\-\d{2}$/;
			return reg.test(value);
		},
		message:'请输入正确年月格式：2015-08'
	}
})
$(function(){
	
});
function submit(){
	$('#myform').form('submit', {
        url : '<%=basePath%>/bugetController/updateBuget.do',
        onSubmit : function() {
            
            if ($(this).form("validate")) {
                return true;
            } else {
                return false;
            }
        },
        success : function(data) {
        	var temp = $.parseJSON(data); 
        	if (temp.success) {
        		$.messager.alert('修改成功',temp.msg,'Info',function(){
        			$('#window').window('close');
                	$('#datagrid_buget').datagrid('load');
        		});
        	}
        	else{
        		$.messager.alert('修改失败',temp.msg,'error');
        	}
        }
    });
}
function cancel() {
    $('#myform').form('clear');
    $('#window').window('close');
}	
</script>
<div id="main">
    <form id="myform" method="post">
    <input type="hidden" value="${entity.id}" name="id" id="id"/>
        <table align="center">
            <tr>
                <td>
                <label>月份:</label>
                </td>
                <td>
                <input class="easyui-validatebox" id = "month" name="month" value="${entity.month }"
		        style="width: 190px;" data-options="required:true,validType:'month'" />
                </td>
            </tr>
            <tr>
                <td>
                <label>预算金额:</label>
                </td>
                <td>
                <input class="easyui-validatebox" type="text" id="value" name="value" value="${entity.value }"
                    style="width: 190px;" data-options="required:true">
                <input class="easyui-combobox" id = "unit" name="unit" value="${entity.unit }"
                data-options="required:true,editable:false,
                    valueField:'code',
                    textField:'codename',
                    width:80,
                    url:'<%=basePath%>/commonController/listDatadictCata.do?catalog=currency'
                " />
                </td>
            </tr>
        </table>
</form>
</div>
<div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submit()">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancel()">取消</a>
</div>
