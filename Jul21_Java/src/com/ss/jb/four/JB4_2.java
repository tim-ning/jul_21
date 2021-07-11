package com.ss.jb.four;

/**
 * Java Basics 4 - Assignment 2
 * 
 * Create a deadlock between two threads.
 * 
 * @author Tim Ning
 *
 */
public class JB4_2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String s1 = "foo";
		String s2 = "bar";

		System.out.println("Start deadlock test.");
		
		Runnable t1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("Thread 1 started.");
				
				try {
					//lock resource s1
					synchronized (s1) {
						Thread.sleep(100);
						
						//request resource s2
						synchronized (s2) {
							System.out.println(s2);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("Thread 1 finished.");
			}
		};

		Runnable t2 = new Runnable() {
			@Override
			public void run() {
				System.out.println("Thread 2 started.");
				
				try {
					//lock resource s2
					synchronized (s2) {
						Thread.sleep(100);
						
						//request resource s1
						synchronized (s1) {
							System.out.println(s1);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("Thread 2 finished.");
			}
		};
		
		//start both threads
		new Thread(t1).start();
		new Thread(t2).start();

	}

}
