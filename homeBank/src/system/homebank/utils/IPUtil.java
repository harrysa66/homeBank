package system.homebank.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class IPUtil {
	
	public static String getIp(HttpServletRequest request) {   
	     String ipAddress = null;   
	     //ipAddress = this.getRequest().getRemoteAddr();   
	     ipAddress = request.getHeader("x-forwarded-for");   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getHeader("Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getRemoteAddr();   
	      if(ipAddress.equals("127.0.0.1")){   
	       //根据网卡取本机配置的IP   
	       InetAddress inet=null;   
	    try {   
	     inet = InetAddress.getLocalHost();   
	    } catch (UnknownHostException e) {   
	     e.printStackTrace();   
	    }   
	    ipAddress= inet.getHostAddress();   
	      }   
	            
	     }   
	  
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	     }   
	     return ipAddress;    
	  }
	
	public static String getIpAddress(String ip) throws IOException {
		StringBuilder ipAddress = new StringBuilder();
		if(isInnerIP(ip)){
			ipAddress.append("本地局域网");
		}else{
			String url = "http://apis.baidu.com/apistore/iplookupservice/iplookup?ip="+ip;
			JSONObject json = ConnectionURL.getConnectionData(url, "apikey", Constants.API_STORE_KEY);
			if(json != null){
				JSONObject retData = json.getJSONObject("retData");
				if(retData != null){
					ipAddress.append(retData.getString("country")).append("-");
					ipAddress.append(retData.getString("province")).append("-");
					ipAddress.append(retData.getString("city")).append("-");
					ipAddress.append(retData.getString("district")).append("-");
					ipAddress.append(retData.getString("carrier"));
				}
			}
		}
		return ipAddress.toString();
	}
	


    public static boolean isInnerIP(String ipAddress){   
            boolean isInnerIp = false;   
            long ipNum = getIpNum(ipAddress);   
            /**  
            私有IP：A类  10.0.0.0-10.255.255.255  
                   B类  172.16.0.0-172.31.255.255  
                   C类  192.168.0.0-192.168.255.255  
            当然，还有127这个网段是环回地址  
            **/  
            long aBegin = getIpNum("10.0.0.0");   
            long aEnd = getIpNum("10.255.255.255");   
            long bBegin = getIpNum("172.16.0.0");   
            long bEnd = getIpNum("172.31.255.255");   
            long cBegin = getIpNum("192.168.0.0");   
            long cEnd = getIpNum("192.168.255.255");   
            isInnerIp = isInner(ipNum,aBegin,aEnd) || isInner(ipNum,bBegin,bEnd) || isInner(ipNum,cBegin,cEnd) || ipAddress.equals("127.0.0.1");   
            return isInnerIp;              
    }  



    private static long getIpNum(String ipAddress) {   
        String [] ip = ipAddress.split("\\.");   
        long a = Integer.parseInt(ip[0]);   
        long b = Integer.parseInt(ip[1]);   
        long c = Integer.parseInt(ip[2]);   
        long d = Integer.parseInt(ip[3]);   
      
        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;   
        return ipNum;   
    }  

    private static boolean isInner(long userIp,long begin,long end){   
        return (userIp>=begin) && (userIp<=end);   
   } 

}
