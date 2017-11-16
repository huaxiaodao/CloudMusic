package com.music163.thread;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    static Thread mainThread = null;
    public static void main(String[] args) {
        //��ȡ���߳�
        mainThread = Thread.currentThread();
        //�½��̲߳�����
        MyThread thread1 = new MyThread("thread1");
        thread1.start();
        //ģ���̹߳�����ʼ
        System.out.println(Thread.currentThread().getName() + "-----�� runs now!");
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "-----�� running step " + i);
            //��ǰ�߳�˯��1��
            sleepOneSecond();
            if(i == 2){
                System.out.println(Thread.currentThread().getName() + "-----�� now pack main thread��������������������");
                //�����߳�����
                LockSupport.park();
            }
        }
        System.out.println(Thread.currentThread().getName() + "-----�� run over!");
        
    }
    
    /**��ǰ�߳���ͣһ���� */
    public static void sleepOneSecond(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    static class MyThread extends Thread {

        public MyThread(String name){
            super(name);
        }
        
        @Override
        public void run() {
            synchronized (this) {
                //ģ�⹤����ʼ
                System.out.println(Thread.currentThread().getName() + "-----�� runs now!");
                for (int i = 0; i < 6; i++) {
                    System.out.println(Thread.currentThread().getName() + "-----�� running step " + i);
                    //��ǰ�߳�˯��1��
                    sleepOneSecond();
                }
                //ģ�⹤������
                System.out.println(Thread.currentThread().getName() + "-----�� run over!");
                
            }
            System.out.println(Thread.currentThread().getName() + "-----�� now unpack main thread���������������� ");
            //������̵߳�����
            LockSupport.unpark(mainThread);
        }       
    }
}