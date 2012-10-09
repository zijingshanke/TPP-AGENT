package com.fordays.fdpay.cooperate;

import java.util.LinkedList;

import com.fordays.fdpay.cooperate.WorkQueue;

public class MainTask implements Runnable {
	private static LinkedList<Runnable> tasks;
	private static MainTask instance = null;

	private MainTask() {
	}

	public static MainTask getInstance() {
		if (instance == null) {
			System.out.println("[MainTask|getInstance]instance main task>>>");
			instance = new MainTask();
			tasks = new LinkedList<Runnable>();
		}
		return instance;
	}

	public static void put(Runnable r) {
		synchronized (tasks) {  //同步  synchronized 块
			tasks.addLast(r);
			tasks.notifyAll();
		}
	}
	

	public void run() {
		WorkQueue work = new WorkQueue(10);// 10个工作线程
		Runnable r;
		while (true) {
			synchronized (tasks) {
				while (tasks.isEmpty()) {// 如果任务队列中没有任务，等待
					try {
						tasks.wait();
					} catch (InterruptedException ignored) {
						
					}
				}
				r = (Runnable) tasks.removeFirst();// 有任务时，取出任务
			}
			try {
				System.out.println(">>>>>>>>>>>>>执行任务..");
				work.execute(r);
			} catch (Exception ex) {
			}
		}
	}
}
