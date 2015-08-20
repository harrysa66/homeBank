package system.homebank.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSONObject;

public class ConnectionURL {
	
	 public static JSONObject getConnectionData(String urlInfo,Integer timeout,String method,String propertyKey,String propertyValue) throws IOException{
		HttpURLConnection connection; 
	    StringBuilder sb = null; 
	    BufferedReader br;// 读取data数据流 
	    URL url = new URL(urlInfo); 
	    connection = (HttpURLConnection)url.openConnection(); 
	    if(null == timeout || 0 == timeout){
	    	timeout = 1000;
	    }
	    connection.setConnectTimeout(timeout);
	    if(null == method || method.equals("")){
	    	method = "GET";
	    }
	    connection.setRequestMethod(method);
	    if(null != propertyKey && !"".equals(propertyKey)){
	    	connection.setRequestProperty(propertyKey, propertyValue);
	    }
	    try { 
	        br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")); 
	        sb = new StringBuilder(); 
	        String line = null; 
	        while ((line = br.readLine()) != null) 
	            sb.append(line); 
	    } catch (SocketTimeoutException e) { 
	        System.out.println("连接超时"); 
	    } catch (FileNotFoundException e) { 
	        System.out.println("加载文件出错"); 
	    } 
	       String datas = sb.toString();   
	       JSONObject jsonData = JSONObject.parseObject(datas);
	       return jsonData;
	 }
	 
	 public static JSONObject getConnectionData(String urlInfo) throws IOException{
		 return getConnectionData(urlInfo, null, null, null, null);
	 }
	 
	 public static JSONObject getConnectionData(String urlInfo,Integer timeout) throws IOException{
		 return getConnectionData(urlInfo, timeout, null, null, null);
	 }
	 
	 public static JSONObject getConnectionData(String urlInfo,String method) throws IOException{
		 return getConnectionData(urlInfo, null, method, null, null);
	 }
	 
	 public static JSONObject getConnectionData(String urlInfo,Integer timeout,String method) throws IOException{
		 return getConnectionData(urlInfo, timeout, method, null, null);
	 }
	 
	 public static JSONObject getConnectionData(String urlInfo,String propertyKey,String propertyValue) throws IOException{
		 return getConnectionData(urlInfo, null, null, propertyKey, propertyValue);
	 }
	 
	 public static JSONObject getConnectionData(String urlInfo,String method,String propertyKey,String propertyValue) throws IOException{
		 return getConnectionData(urlInfo, null, method, propertyKey, propertyValue);
	 }
	 
	 public static void main(String[] args) {
		try {
			JSONObject json = getConnectionData("http://apis.baidu.com/apistore/iplookupservice/iplookup?ip=192.168.1.1","apikey","0f903257ac0e7b4f21fa16a0829db280");
			System.out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
}
