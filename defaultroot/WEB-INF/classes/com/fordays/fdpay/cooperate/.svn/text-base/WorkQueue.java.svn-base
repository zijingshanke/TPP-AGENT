package com.fordays.fdpay.cooperate;

import java.util.LinkedList;

import com.fordays.fdpay.transaction.OrderDetails;

public class WorkQueue {
	private final PoolWorker[] threads;// 用数组实现线程池
	private final LinkedList<Runnable> queue;// 任务队列

	public WorkQueue(int nThreads) {
		threads = new PoolWorker[nThreads];
		queue = new LinkedList<Runnable>();
		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();// 启动所有工作线程
		}
	}

	public void execute(Runnable r){// 执行任务
		synchronized (queue) {
			queue.addLast(r);
			queue.notifyAll();
		}
	}

	private class PoolWorker extends Thread {// 工作线程类
		public void run() {
			Runnable r;
			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()){// 如果任务队列中没有任务，等待
						try {
							queue.wait();
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
					r = (Runnable) queue.removeFirst();// 有任务时，取出任务
				}
				try {
					r.run();// 执行任务
				} catch (RuntimeException e) {
				}
			}
		}
	}
}
