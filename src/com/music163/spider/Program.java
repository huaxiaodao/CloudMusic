package com.music163.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Program {
	public String PgName;   //����
	public String PyNumber; //������
	public String PeNumber; //������
	public String UpTime;   //�ϴ�ʱ��
	public String duration; //ʱ��
	
public Program(Element result){
	PgName = "";
	PyNumber = "";
	PeNumber = "";
	UpTime = "";
	duration = "";
   }
}