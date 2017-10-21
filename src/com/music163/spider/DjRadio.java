package com.music163.spider;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DjRadio {
	public String RoName;    //电台名字
	public String Anchor;    //主播
	public String RoInfo;    //电台简介
	public String SubNumber; //订阅量
	public String AnchorUrl; //主播URL
	public ArrayList<Program> PgList;
	//节目列表信息
	public DjRadio(String result) throws Exception{
		RoName = "";
		Anchor = "";
		RoInfo = "";
		SubNumber = ""; 
		PgList = new ArrayList<Program>();
		
	    Document doc = Jsoup.parse(result);
	    addUrl(doc);
	    Elements as=doc.select("title");
	    RoName = as.text();
	    System.out.println("电台： " +RoName);
	    //匹配抓取电台名字
	    Elements bs=doc.select("a[class=s-fc7]");
	    Anchor = bs.attr("title");
  		System.out.println("主播： " +Anchor);
  		//匹配抓取电台的主播名字
  		Elements cs=doc.select("meta[name=description]");
  		RoInfo = cs.attr("content");
  		System.out.println("电台的简介信息："+RoInfo);
  		//匹配抓取电台的简介信息
  		Elements es=doc.select("tr[id*=songlist]");
  		for (Element e : es) {
             Program pg = new Program(e);
             pg.PgName = e.select("a[href*=program?id]").attr("title");
             pg.PyNumber = e.select("td[class=col3]").text();
             pg.PeNumber= e.select("td[class=col4]").text();
             pg.UpTime = e.select("td[class=col5]").text();
             pg.Duration = e.select("td[class=f-pr]").text();;
             PgList.add(pg);
         }
  		for (Program p : PgList) {
  			System.out.println("节目名：" + p.PgName + "  播放量: " + p.PyNumber + "  点赞数： " + p.PeNumber + "  上传时间： " + p.UpTime + "  时长: " + p.Duration);
  		}
	}
	public String addUrl(Document doc) {
		Elements us=doc.select("a[href*=user]");
		AnchorUrl = "http://music.163.com" + us.attr("href");
		System.out.println(AnchorUrl);
		return AnchorUrl;
	}
}
