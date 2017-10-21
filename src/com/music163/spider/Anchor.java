package com.music163.spider;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Anchor {
	public String Name;          //主播名字
	public String ID;         //主播ID
	public String Level;         //等级
	public String Fans;          //粉丝数
	public String Introduction;  //个人介绍
	public String MusicUrl;
	public String BASE_URL;
	public String POST_URL;
	public ArrayList<String> SongList;
	
public Anchor(String result1) throws Exception{
	Name = "";
	Fans = "";
	Introduction = "";
	SongList = new ArrayList<String>();
	BASE_URL= "";
	POST_URL = "http://music.163.com/weapi/user/playlist?csrf_token=";
	 Document doc = Jsoup.parse(result1);
	 Elements as=doc.select("span[class=tit f-ff2 s-fc0 f-thide]");
	 Name = as.text();
	 System.out.println("主播名字： " +Name);
	 Elements ds=doc.select("div[class=m-record f-hide]");
	 ID = ds.attr("data-uid");
	 System.out.println("主播ID： " +ID);
	 Elements ls=doc.select("span[class=lev u-lev u-icn2 u-icn2-lev]");
	 Level = ls.text();
	 System.out.println("主播等级： " +Level);
	 Elements bs=doc.select("strong[id=fan_count]");
	 Fans = bs.text();
	 System.out.println("粉丝数： " +Fans);
	 Elements cs=doc.select("div[class=inf s-fc3 f-brk]");
	 Introduction = cs.text();
	 System.out.println(Introduction);
	 Elements rs=doc.select("link[rel=canonical]");
	 BASE_URL = "http:" + rs.attr("href");
	 //System.out.println(BASE_URL);
	 appendUrl(doc);
	}
public String appendUrl(Document doc) throws Exception {
	Spider spider = new Spider();
	Encrypt encrypt = new Encrypt();
	String tt = encrypt.crawlAjaxUrl(ID,BASE_URL,POST_URL);
    //System.out.println(tt);
		ArrayList<String> ll = new ArrayList<String>();
		Pattern pattern;  
        Matcher matcher;  
    // 匹配标题  
    pattern = Pattern.compile("\"id\":(.+?)[} ,]");  
    matcher = pattern.matcher(tt);
    System.out.println("歌单URL:");
    while (matcher.find()) {
       String url2 = "http://music.163.com/playlist?id=" + matcher.group(1);
       System.out.println(url2);
       String result2 = spider.getUrl(url2);
       FvMusic music = new FvMusic(result2);
       ll.add(url2);
    } 
    return "";
  }
}
