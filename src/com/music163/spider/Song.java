package com.music163.spider;

import org.jsoup.nodes.Element;

public class Song {
	public String SgName;
	public String SongURL;
	public String ListID;
	
	public Song(Element result){
		SgName = "";
		SongURL = "";
		ListID = "";
	   }
}
