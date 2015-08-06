package test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import system.ssq.model.HttpRespons;
import system.ssq.utils.HttpRequester;


 
public class Test {
  public static void main(String[] args) {
    try
    {
      Document doc = Jsoup.connect("http://baidu.lecai.com/lottery/draw/list/50")
          .data("ds", "2013-01-01")
          .data("de", "2013-12-03")
          .data("lottery_type","50")
          .data("page", "1").get();
      Elements elms = doc.getElementsByClass("bgcolor1");
      String fmt = "日期：%s，期数：%s，号码：%s，销量：%s";
      for (Element e:elms)
      {
        System.out.println(String.format(fmt, 
            e.getElementsByClass("td1").text(),
            e.getElementsByClass("td2").text(),
            e.getElementsByClass("td3").text(),
            e.getElementsByClass("td4").text()));
      }
      elms = doc.getElementsByClass("bgcolor2");
      for (Element e:elms)
      {
        System.out.println(String.format(fmt, 
            e.getElementsByClass("td1").text(),
            e.getElementsByClass("td2").text(),
            e.getElementsByClass("td3").text(),
            e.getElementsByClass("td4").text()));
      }
      
    } catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
