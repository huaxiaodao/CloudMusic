package com.music163.spider;


public class Main {
	public static void main(String[] args) throws Exception{
	    String url = "http://music.163.com/djradio?id=5811013";
	    Spider spider = new Spider();
	    String result = spider.getUrl(url);
	    DjRadio radio = new DjRadio(result);
         }
}
		     