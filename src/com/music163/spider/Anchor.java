package com.music163.spider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.music163.data.MysqlConnect;

public class Anchor {
	public String Anchor;          //主播名字
	public String ID;         //主播ID
	public static String Level;         //等级
	public String Fans;          //粉丝数
	public String Introduction;  //个人介绍
	public String MusicUrl;
	public String BASE_URL;
	public String POST_URL;
	public ArrayList<String> SongList;
	
public Anchor(String result1) throws Exception{
	Anchor = "";
	Fans = "";
	Introduction = "";
	SongList = new ArrayList<String>();
	BASE_URL= "";
	POST_URL = "http://music.163.com/weapi/user/playlist?csrf_token=";
	 Document doc = Jsoup.parse(result1);
	 Elements as=doc.select("span[class=tit f-ff2 s-fc0 f-thide]");
	 Anchor = as.text();
	 System.out.println("主播名字： " +Anchor);
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
	 addAnchor(Anchor,ID,Level,Fans,Introduction,BASE_URL,POST_URL);
	 //System.out.println(BASE_URL);
	 appendUrl(doc);
	}
public String appendUrl(Document doc) throws Exception {
	//Spider spider = new Spider();
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
       String result2 = Spider.getUrl(url2);
       SongList music = new SongList(result2);
       ll.add(url2);
    } 
    return "";
  }
public static void addAnchor(String Anchor, String ID, String ListID, String Fans, String Introduction,String BASE_URL,String POST_URL) {
	// TODO Auto-generated method stub
	Connection conn = MysqlConnect.getConnection();	
	String addSQL = "insert into tb_anchor (Anchor,ID,Level,Fans,Introduction,BASE_URL,POST_URL) values(?,?,?,?,?,?,?)";
	PreparedStatement pstmt = null;					
	try {
		pstmt = conn.prepareStatement(addSQL);
		pstmt.setString(1,Anchor);  
		pstmt.setString(2,ID);
		pstmt.setString(3,Level);
		pstmt.setString(4,Fans);  
		pstmt.setString(5,Introduction);
		pstmt.setString(6,BASE_URL);
		pstmt.setString(7,POST_URL);
		pstmt.executeUpdate();							
	} catch (SQLException e) {
		e.printStackTrace();
	} finally{
		MysqlConnect.close(pstmt);							
		MysqlConnect.close(conn);							
	}
}
}
