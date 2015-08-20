package system.homebank.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import system.homebank.entity.Menu;
import system.homebank.entity.User;
import system.homebank.service.CommonService;
import system.homebank.service.UserLoginService;
import system.homebank.service.UserService;
import system.homebank.utils.HtmlUtil;
import system.homebank.utils.IPUtil;
import system.homebank.utils.MD5Util;
import system.homebank.utils.MenuUtils;
import system.homebank.utils.VerifyCodeUtils;
import system.security.SpringSecurityUtils;

@Controller
public class HomePageController
{
  @Resource
  private CommonService commonService;
  
  @Resource
  private UserService userService;
  
  @Resource
  private UserLoginService userLoginService;
  
  @RequestMapping("/login.do")
  public String login(HttpServletRequest request)
  {
	  boolean isLogin = false;
		Object loginObject = request.getSession().getAttribute("isLogin");
		if(loginObject != null){
			isLogin = (boolean) loginObject;
		}
		if(isLogin){
			return "redirect:/home.do";
		}else{
			return "/login";
		}
  }
  
  @RequestMapping("/home.do")
  public String forward(Model model,HttpServletRequest request)
  {
    List<Menu> list = this.commonService.getAllMenu();
    String menus = MenuUtils.buildMenus(list);
    User user = SpringSecurityUtils.getCurrentUser();
    String userIp = IPUtil.getIp(request);
    model.addAttribute("user", user);
    model.addAttribute("userIp", userIp);
    model.addAttribute("menus", menus);
    return "/index";
  }
  @RequestMapping("/verifyCode.do")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		//生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		//存入会话session
		HttpSession session = request.getSession(true);
		session.setAttribute("rand_verifyCode", verifyCode.toLowerCase());
		//生成图片
		int w = 200, h = 80;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
  
  	@RequestMapping("/modifyPassword.do")
	public void modifyPassword(String id,String oldPwd,String newPwd,HttpServletResponse response){
		Map<String, String> param = new HashMap<String, String>();
		param.put("id", id);
		param.put("oldPassword", MD5Util.MD5(oldPwd));
		param.put("newPassword", MD5Util.MD5(newPwd));
		Map<String, Object> result = userService.updatePassword(param);
		HtmlUtil.writerJson(response, result);
	}
  	
  	@RequestMapping("/updateInfo.do")
	public void updateInfo(User user,HttpServletResponse response){
  		Map<String, Object> result = userService.updateUser(user);
		HtmlUtil.writerJson(response, result);
	}
  
  	@RequestMapping("/queryUser.do")
    @ResponseBody
    public Object queryUser(@RequestParam Map<String,Object> filter)
    {
      return userService.query(filter);
    }
  	
  	@RequestMapping("/runUser.do")
    @ResponseBody
    public void runUser(String username,HttpServletResponse response)
    {
  		Map<String, Object> result = userService.runUser(username);
  		HtmlUtil.writerJson(response, result);
    }
  	
  	@RequestMapping("/deleteUser.do")
    @ResponseBody
    public void deleteUser(String id,HttpServletResponse response)
    {
  		Map<String, Object> result = userService.deleteUser(id);
  		HtmlUtil.writerJson(response, result);
    }
  	
  	@RequestMapping("/viewLogin.do")
    @ResponseBody
    public Object viewLogin(@RequestParam Map<String,Object> filter)
    {
      return userLoginService.query(filter);
    }
  
  @RequestMapping("/payincome.do")
  @ResponseBody
  public Object getPayincomeData()
  {
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    
    String monday = this.getCurrentMonday();
    String sunday = this.getPreviousSunday();
    String stryear = String.valueOf(year);
    String smonth = String.valueOf(month);
    if (month < 10)
      smonth = String.format("0%s", month);
    String strmonth = String.valueOf(year)+"-"+ smonth;
    List<Map<String,String>> result = this.commonService.getPayincomeData(stryear,strmonth,monday,sunday);
    return result;
  }
  @RequestMapping("/getMonthSumByType.do")
  @ResponseBody
  public Object getMonthSumByType(@RequestParam String paymenttype)
  {
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;
    String smonth = String.valueOf(month);
    if (month < 10)
      smonth = String.format("0%s", month);
    String strmonth = String.valueOf(year)+"-"+ smonth;
    
    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    Map<String,String> ret = this.commonService.getMonthSumByType(strmonth,days,paymenttype);
    return ret;
  }
  // 获得当前日期与本周一相差的天数
  private int getMondayPlus()
  {
    Calendar cd = Calendar.getInstance();
    // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
    int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
    if (dayOfWeek == 1)
    {
      return -6;
    } else
    {
      return 2 - dayOfWeek;
    }
  }
  
  // 获得当前周- 周一的日期
  private String getCurrentMonday()
  {
    int mondayPlus = getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus);
    Date monday = currentDate.getTime();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String preMonday = df.format(monday);
    return preMonday;
  }
  
  // 获得当前周- 周日 的日期
  private String getPreviousSunday()
  {
    int mondayPlus = getMondayPlus();
    GregorianCalendar currentDate = new GregorianCalendar();
    currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
    Date monday = currentDate.getTime();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String preMonday = df.format(monday);
    return preMonday;
  }
}
