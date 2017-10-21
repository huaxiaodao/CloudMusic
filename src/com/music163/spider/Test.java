package com.music163.spider;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Test{
	public static void main(String[] args) throws Exception{
		    String url = "http://music.163.com/playlist?id=133647062";
		    String result = getResult(url);
		    Document doc = Jsoup.parse(result);
      		Elements es=doc.select("ul[class=f-hide]").select("a[href*=song?id]");
      		//System.out.println("结果：" + es.text());
      		 for (Element e : es) {
                 //String tt = e.attr("title");
      			System.out.println("结果：" + e.text() + " " +e.attr("href"));
             }
	}
        public static String getResult(String url) throws Exception {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(new HttpGet(url))) {
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            System.out.println("获取失败");
            return "";
        }
    }
      		 
      		/** HtmlUnit请求web页面 
      	    try {
      	    WebClient wc = new WebClient();
      	    wc.getOptions().setUseInsecureSSL(true);
      	    wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
      	    wc.getOptions().setCssEnabled(false); // 禁用css支持
      	    wc.setAjaxController(new NicelyResynchronizingAjaxController());
      	    wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
      	    wc.getOptions().setTimeout(100000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
      	    wc.getOptions().setDoNotTrackEnabled(false);
      	    HtmlPage page = wc.getPage(url);
      	   Elements msk1 = doc.getElementsByClass("m-cvrlst f-cb");
      	   System.out.println(msk.toString());
           Document mskDoc1 = Jsoup.parse(msk.toString());
   		   Elements es1=mskDoc1.select("a[href*=playlist]");
   		   System.out.println("结果：" + es1.attr("href"));
      	    }catch (Exception e) {
      	    	 
                e.printStackTrace();
            }
      	    
    }  */
}