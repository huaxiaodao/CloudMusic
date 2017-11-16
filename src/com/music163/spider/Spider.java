package com.music163.spider;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Spider {
	public static  String getUrl(String url) throws Exception {
	        try {
	        	  CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	        	  HttpGet httpGet = new HttpGet(url);
	              httpGet.setHeader("Accept", "text/html,application/xhtml+xml," +
	                      "application/xml;q=0.9,image/webp,*/*;q=0.8");
	              httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch, br");
	              httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
	              httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36" +
	                      " (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
	              System.out.println("executing request "+httpGet.getURI());
	              CloseableHttpResponse response = httpClient.execute(httpGet);
	              String result = EntityUtils.toString(response.getEntity());
	              return result;
	        } catch (Exception e) {
	            System.out.println("ªÒ»° ß∞‹");
	            return "";
	        }
	    }
}
