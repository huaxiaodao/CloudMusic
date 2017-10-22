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

public class SongList {
	public String MuName;
	public String Anchor; 
	public String ListID;
	public String MuNumber;
	public String PyNumber;
	public String Comment;
	public ArrayList<Song> MuList;
	public SongList(String result2) throws Exception{
		MuName = "";
		Anchor = "";
		ListID = "";
		MuNumber = "";
		PyNumber = "";
		Comment = "";
		MuList = new ArrayList<Song>();
		
		Document doc = Jsoup.parse(result2);
		Elements ns=doc.select("meta[name=description]");
		MuName = ns.attr("content");
		System.out.println(MuName);
		Elements us=doc.select("a[href*=/user/home?id]").select("[class=s-fc7]");
		Anchor = us.text();
		System.out.println(Anchor);
		Elements ls=doc.select("div[id=content-operation]");
		ListID = ls.attr("data-rid");
		System.out.println(ListID);
	    Elements as=doc.select("span[id=playlist-track-count]");
	    MuNumber = as.text();
	    System.out.println("歌曲数量： " +MuNumber);
	    Elements bs=doc.select("strong[id=play-count]");
	    PyNumber = bs.text();
	    System.out.println("播放量： " +PyNumber);
	    Elements cs=doc.select("span[id=cnt_comment_count]");
	    Comment = cs.text();
	    System.out.println("评论数： " +Comment);
	    addSongList(MuName,Anchor,ListID,MuNumber,PyNumber,Comment);
	    Elements es=doc.select("ul[class=f-hide]").select("a[href*=song?id]");
  		for (Element e : es) {
             Song sg = new Song(e);
             sg.SgName = e.text();
             sg.SongURL = "http://music.163.com" + e.attr("href");
             sg.ListID = ListID;
             addSong(sg.SgName, sg.SongURL,sg.ListID);
             MuList.add(sg);
         }
  		
  		for (Song s : MuList) {
  			System.out.println("歌名：" + s.SgName + "  歌曲url: " + s.SongURL);
  		}
	}
	public static void addSong(String SgName, String musicURL, String ListID) {
		// TODO Auto-generated method stub
		Connection conn = MysqlConnect.getConnection();	
		String addSQL = "insert into tb_song (SgName,SongURL,ListID) values(?,?,?)";
		PreparedStatement pstmt = null;					
		try {
			pstmt = conn.prepareStatement(addSQL);
			pstmt.setString(1,SgName);  
			pstmt.setString(2,musicURL);
			pstmt.setString(3,ListID);
			pstmt.executeUpdate();							
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			MysqlConnect.close(pstmt);							
			MysqlConnect.close(conn);							
		}
	}
	public static void addSongList(String MuName, String Anchor, String ListID, String MuNumber, String PyNumber, String Comment) {
		// TODO Auto-generated method stub
		Connection conn = MysqlConnect.getConnection();	
		String addSQL = "insert into tb_songlist (MuName,Anchor,ListID,MuNumber,PyNumber,Comment) values(?,?,?,?,?,?)";
		PreparedStatement pstmt = null;					
		try {
			pstmt = conn.prepareStatement(addSQL);
			pstmt.setString(1,MuName);  
			pstmt.setString(2,Anchor);
			pstmt.setString(3,ListID);
			pstmt.setString(4,MuNumber);  
			pstmt.setString(5,PyNumber);
			pstmt.setString(6,Comment);
			pstmt.executeUpdate();							
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			MysqlConnect.close(pstmt);							
			MysqlConnect.close(conn);							
		}
	}
}
