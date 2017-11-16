package com.music163.thread;


import java.util.ArrayList;

public class MyThread extends Thread{
	public static void main(String[] agrs) {
		int num = 10;
		//ArrayList<MyThread> ThreadList = new ArrayList<MyThread>();
		for (int i = 0; i < num; i++) {
			MyThread myThread = new MyThread("myThread");
			MyThread myThread1 = new MyThread("myThread1");
			//ThreadList.add(myThread);
			myThread.start();
			myThread1.start();
			//System.out.println(Thread.currentThread().getName());
		}
	}
	public MyThread(String name){
        super(name);
    }
	 public void run() {
		 System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getId());
	 }
}
