package com.music163.spider;

import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DjRadio {
	public String RoName;    //��̨����
	public String Anchor;    //����
	public String RoInfo;    //��̨���
	public String SubNumber; //������
	public String AnchorUrl; //����URL
	public ArrayList<Program> PgList;
	//��Ŀ�б���Ϣ
	public DjRadio(String result) throws Exception{
		RoName = "";
		Anchor = "";
		RoInfo = "";
		SubNumber = ""; 
		PgList = new ArrayList<Program>();
		
	    Document doc = Jsoup.parse(result);
	    addUrl(doc);
	    Elements as=doc.select("title");
	    RoName = as.text();
	    System.out.println("��̨�� " +RoName);
	    //ƥ��ץȡ��̨����
	    Elements bs=doc.select("a[class=s-fc7]");
	    Anchor = bs.attr("title");
  		System.out.println("������ " +Anchor);
  		//ƥ��ץȡ��̨����������
  		Elements cs=doc.select("meta[name=description]");
  		RoInfo = cs.attr("content");
  		System.out.println("��̨�ļ����Ϣ��"+RoInfo);
  		//ƥ��ץȡ��̨�ļ����Ϣ
  		Elements es=doc.select("tr[id*=songlist]");
  		for (Element e : es) {
             Program pg = new Program(e);
             pg.PgName = e.select("a[href*=program?id]").attr("title");
             pg.PyNumber = e.select("td[class=col3]").text();
             pg.PeNumber= e.select("td[class=col4]").text();
             pg.UpTime = e.select("td[class=col5]").text();
             pg.Duration = e.select("td[class=f-pr]").text();;
             PgList.add(pg);
         }
  		for (Program p : PgList) {
  			System.out.println("��Ŀ����" + p.PgName + "  ������: " + p.PyNumber + "  �������� " + p.PeNumber + "  �ϴ�ʱ�䣺 " + p.UpTime + "  ʱ��: " + p.Duration);
  		}
	}
	public String addUrl(Document doc) {
		Elements us=doc.select("a[href*=user]");
		AnchorUrl = "http://music.163.com" + us.attr("href");
		System.out.println(AnchorUrl);
		return AnchorUrl;
	}
}
