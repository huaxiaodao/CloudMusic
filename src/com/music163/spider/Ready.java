package com.music163.spider;


public class Ready {
	public static  void go(String radioURL) throws Exception{
	    String url = radioURL;
	   // Spider spider = new Spider();
	    String result = Spider.getUrl(url);
	    DjRadio radio = new DjRadio(result);
	    String url1 = radio.AnchorUrl;
	    String result1 = Spider.getUrl(url1);
	    Anchor anchor = new Anchor(result1);
         }
}
		     