package com.music163.spider;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FvMusic {
	public String MuName;
	public String MuNumber;
	public String PyNumber;
	public String Comment;
	public ArrayList<Song> MuList;
	public FvMusic(String result2) throws Exception{
		MuName = "";
		MuNumber = "";
		PyNumber = "";
		Comment = "";
		MuList = new ArrayList<Song>();
		
		Document doc = Jsoup.parse(result2);
		Elements ns=doc.select("meta[name=description]");
		MuName = ns.attr("content");
		System.out.println(MuName);
	    Elements as=doc.select("span[id=playlist-track-count]");
	    MuNumber = as.text();
	    System.out.println("歌曲数量： " +MuNumber);
	    Elements bs=doc.select("strong[id=play-count]");
	    PyNumber = bs.text();
	    System.out.println("播放量： " +PyNumber);
	    Elements cs=doc.select("span[id=cnt_comment_count]");
	    PyNumber = cs.text();
	    System.out.println("评论数： " +PyNumber);
	    
	    Elements es=doc.select("ul[class=f-hide]").select("a[href*=song?id]");
  		for (Element e : es) {
             Song sg = new Song(e);
             sg.SgName = e.text();
             sg.SongURL = "http://music.163.com" + e.attr("href");
             MuList.add(sg);
         }
  		
  		for (Song s : MuList) {
  			System.out.println("歌名：" + s.SgName + "  歌曲url: " + s.SongURL);
  		}
	}
}
