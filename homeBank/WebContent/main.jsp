<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path ;
%>
<script>
$(function(){
	$('#datagrid_payincome').datagrid({
		title:'收支表',
        url:'<%=basePath%>/payincome.do',
        border:false,
        singleSelect:true,
        height:105,
        onSelect:function(rowIndex, rowData){
        	var paymenttype = "";
        	if(rowData.type == "收入"){
        		paymenttype = "1";
        	}
        	if(rowData.type == "支出"){
        		paymenttype = "2";
        	}
        	getMonthSumByType(paymenttype);
        },
        columns:[[
                  {field:'type',title:'类型',width:250,
                	  styler: function(value,row,index){
                		  if (index == 0)
                              return 'color:red;';
                          else
                              return 'color:blue;';	  
                      }
                  },
                  {field:'week',title:'本周',width:250,
                      styler: function(value,row,index){
                          if (index == 0)
                              return 'color:red;';
                          else
                              return 'color:blue;';   
                      }},
                  {field:'month',title:'本月',width:250,
                          styler: function(value,row,index){
                              if (index == 0)
                                  return 'color:red;';
                              else
                                  return 'color:blue;';   
                          }},
                  {field:'year',title:'本年',width:250,
                              styler: function(value,row,index){
                                  if (index == 0)
                                      return 'color:red;';
                                  else
                                      return 'color:blue;';   
                              }}
          ]]
    });
	getMonthSumByType("2");
});
function getMonthSumByType(paymenttype){
	$.ajax({
	    type: 'POST',   
	    url: '<%=basePath%>/getMonthSumByType.do',
	    data: 'paymenttype='+paymenttype,
	    dataType:'text',
	    success: function(msg){
	    	var temp = $.parseJSON(msg); 
	    	var title = "";
	    	if(paymenttype == "1"){
	    		title = "收入"
	    	}
	    	if(paymenttype == "2"){
	    		title = "支出"
	    	}
	    	createChart('zcqxt','本月'+title+'去向图',eval(temp.data1),'pie',350,390,'元');
	    	createChart('zcqst','本月'+title+'趋势图',eval(temp.data2),'line',360,950,'元');
	    }
	});
}
</script>
<div id="layout" class="easyui-layout" data-options="fit:true">
   <div data-options="region:'north',split:true,border:false" style="height:110px">
       <table id="datagrid_payincome"></table>
   </div>
   <div data-options="region:'west',split:true,border:false" style="width:400px">
       <div id="zcqxt"></div>
   </div>
   <div data-options="region:'center',border:false">
       <div id="zcqst"></div>
   </div>
</div>
