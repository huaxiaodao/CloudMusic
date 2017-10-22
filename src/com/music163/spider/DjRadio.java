package com.music163.spider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.music163.data.MysqlConnect;

public class DjRadio {
	public String RoName;    //电台名字
	public String RadioID; 
	public String Anchor;    //主播
	public String RoInfo;    //电台简介
	public String SubNumber; //订阅量
	public String AnchorUrl; //主播URL
	public ArrayList<Program> PgList;
	//节目列表信息
	public DjRadio(String result) throws Exception{
		RoName = "";
		RadioID = "";
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
  		Elements us=doc.select("a[href*=/user/home?id]").select("[class=s-fc7]");
		Anchor = us.text();
		Elements rs=doc.select("span[class=ply]");
		RadioID = rs.attr("data-res-radioid");
  		addRadio(RoName, RadioID, Anchor, RoInfo, SubNumber);
  		//匹配抓取电台的简介信息
  		Elements es=doc.select("tr[id*=songlist]");
  		for (Element e : es) {
             Program pg = new Program(e);
             pg.PgName = e.select("a[href*=program?id]").attr("title");
             pg.RadioID = RadioID;
             pg.PyNumber = e.select("td[class=col3]").text();
             pg.PeNumber= e.select("td[class=col4]").text();
             pg.UpTime = e.select("td[class=col5]").text();
             pg.Duration = e.select("td[class=f-pr]").text();;
             addProgram(pg.PgName,RadioID,pg.PyNumber,pg.PeNumber,pg.UpTime,pg.Duration);
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
	public static void addProgram(String PgName, String RadioID, String PyNumber, String PeNumber, String UpTime, String Duration) {
		// TODO Auto-generated method stub
		Connection conn = MysqlConnect.getConnection();	
		String addSQL = "insert into tb_program (PgName,RadioID,PyNumber,PeNumber,UpTime,Duration) values(?,?,?,?,?,?)";
		PreparedStatement pstmt = null;					
		try {
			pstmt = conn.prepareStatement(addSQL);
			pstmt.setString(1,PgName);  
			pstmt.setString(2,RadioID);
			pstmt.setString(3,PyNumber);
			pstmt.setString(4,PeNumber);  
			pstmt.setString(5,UpTime);
			pstmt.setString(6,Duration);
			pstmt.executeUpdate();							
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			MysqlConnect.close(pstmt);							
			MysqlConnect.close(conn);							
		}
	}
	public static void addRadio(String RoName, String RadioID, String Anchor, String RoInfo, String SubNumber) {
		// TODO Auto-generated method stub
		Connection conn = MysqlConnect.getConnection();	
		String addSQL = "insert into tb_radio (RoName,RadioID,Anchor,RoInfo,SubNumber) values(?,?,?,?,?)";
		PreparedStatement pstmt = null;					
		try {
			pstmt = conn.prepareStatement(addSQL);
			pstmt.setString(1,RoName);  
			pstmt.setString(2,RadioID);
			pstmt.setString(3,Anchor);
			pstmt.setString(4,RoInfo);  
			pstmt.setString(5,SubNumber);
			pstmt.executeUpdate();							
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			MysqlConnect.close(pstmt);							
			MysqlConnect.close(conn);							
		}
	}
}
