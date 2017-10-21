package com.music163.spider;

import org.jsoup.nodes.Element;

public class Song {
	public String SgName;
	public String SongURL;

	
	public Song(Element result){
		SgName = "";
		SongURL = "";
	   }
}
