package com.music163.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Program {
	public String PgName;   //歌名
	public String RadioID;
	public String PyNumber; //播放量
	public String PeNumber; //点赞数
	public String UpTime;   //上传时间
	public String Duration; //时长
	
public Program(Element result){
	PgName = "";
	RadioID = "";
	PyNumber = "";
	PeNumber = "";
	UpTime = "";
	Duration = "";
   }
}
