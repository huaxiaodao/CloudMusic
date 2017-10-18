package com.music163.spider;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test{
	public static void main(String[] args) throws Exception{
		    String url = "http://music.163.com/djradio?id=5811013";
		    String result = getResult(url);
		    Document doc = Jsoup.parse(result);
      		Elements es=doc.select("tr[id*=songlist]").select("td[class=f-pr]");
      		System.out.println(es.text());
      		 for (Element e : es) {
                 //String tt = e.attr("title");
                 //System.out.println(tt);
             }
    }
	public static String getResult(String url) throws Exception {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(url))) {
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            System.out.println("ªÒ»° ß∞‹");
            return "";
        }
    }
}