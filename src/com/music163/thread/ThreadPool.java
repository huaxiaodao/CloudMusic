package com.music163.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.music163.spider.Spider;
import com.music163.spider.Ready;

public class ThreadPool {
	public static void main(String[] args){   
	        //ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5));
	    	ExecutorService executor = Executors.newCachedThreadPool(); 
	        for(int i=0;i<5;i++){
	            Task myTask = new Task(i);
	            executor.execute(myTask);
	            System.out.println("线程池中线程数目："+((ThreadPoolExecutor) executor).getPoolSize()+"，队列中等待执行的任务数目："+
	            ((ThreadPoolExecutor) executor).getQueue().size()+"，已执行完别的任务数目："+((ThreadPoolExecutor) executor).getCompletedTaskCount());
	        }
	        executor.shutdown(); 
	    } 
	    public static ExecutorService newCachedThreadPool() {
	        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
	    }
	}
	class Task implements Runnable {
	   private int taskNum;
	    
	   public Task(int num) {
	       this.taskNum = num;
	   }
	    
	   @Override
	   public void run() {
		    System.out.println("正在执行task "+taskNum);
		    String url;
			String href = "http://music.163.com/discover/djradio/category?id=2001&order=1&_hash=allradios&limit=30&offset=";
			int offset = 0;
			for (int i = (3*taskNum);i < (3*taskNum + 3);i++) {
				offset = i * 30;
				url= href + offset;
				//System.out.println(url);
				try {
					spider(url);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 System.out.println("task "+taskNum+"执行完毕****************************************");
	   }
	   public static void spider(String url) throws Exception{
		    String result = Spider.getUrl(url);
		    Document doc = Jsoup.parse(result);
		    //String  Anchor;
		    Elements us=doc.select("h3[class=f-fs3]").select("a[href*=/djradio?id]");
		    for (Element u : us) {
		    //Anchor = u.attr("title");
		    String radioURL = "http://music.163.com" + u.attr("href");
		    Ready.go(radioURL);
		    //System.out.println("电台："+ Anchor + "   ===链接： " + radioURL);
		    }
	 		/*
	 		Elements es=doc.select("ul[class=f-hide]").select("a[href*=song?id]");
	 		System.out.println("结果：" + es.text());
	 		  for (Element e : es) {
	            //String tt = e.attr("title");
	 			 String SgName = e.text();
	 			 String musicURL = "http://music.163.com" + e.attr("href");
	 			 //addSong(SgName,musicURL);
	 			 System.out.println("结果：" + SgName + " " + musicURL);
	        } 
	        */
	}
}
