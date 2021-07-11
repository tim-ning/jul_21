package com.ss.jb.four;

import java.util.Random;

/**
 * Java Basics 4 - Assignment 3
 * 
 * Multiple producer & consumer threads are all adding & removing random
 * characters from a shared array at random intervals.
 * 
 * @author Tim Ning
 *
 */
public class JB4_3 {

	// set the number of cycles to run each thread for
	private final int iterations = 30;

	// spacer for console output
	private final String spacer = "                         ";

	private Character[] buf;
	private Random rand;

	// constructor
	public JB4_3() {

		// create an empty array
		buf = new Character[7];

		rand = new Random();

		// create 3 producer threads & 3 consumer threads
		createProducer("Alice");
		createProducer("Bob  ");
		createProducer("Carol");

		createConsumer("David");
		createConsumer("Eve  ");
		createConsumer("Frank");
	}

	// define the behavior of a producer thread
	private void createProducer(String name) {
		Runnable producer = new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(name + ": Started.");

					for (int j = 0; j < iterations; j++) {
						boolean found = false;

						// lock the buffer
						synchronized (buf) {

							// find an empty cell & put a random ascii char in it
							for (int i = 0; i < buf.length; i++)
								if (buf[i] == null) {
									buf[i] = (char) (rand.nextInt(93) + 33);
									System.out.println(name + ": put [" + i + "] " + buf[i]);
									found = true;
									break;
								}
						}

						if (!found)
							System.out.println(name + ": Array is full.");

						// wait for a random interval
						Thread.sleep(rand.nextInt(100));
					}

					System.out.println(name + ": Finished.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// start thread
		new Thread(producer).start();
	}

	// define the behavior of a consumer thread
	private void createConsumer(String name) {
		Runnable consumer = new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(spacer + name + ": Started.");

					for (int j = 0; j < iterations; j++) {
						boolean found = false;

						// lock the buffer
						synchronized (buf) {

							// find a filled cell & remove the item from it
							for (int i = 0; i < buf.length; i++)
								if (buf[i] != null) {
									System.out.println(spacer + name + ": get [" + i + "] " + buf[i]);
									buf[i] = null;
									found = true;
									break;
								}
						}

						if (!found)
							System.out.println(spacer + name + ": Array is empty.");

						// wait for a random interval
						Thread.sleep(rand.nextInt(100));
					}

					System.out.println(spacer + name + ": Finished.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		// start thread
		new Thread(consumer).start();
	}

	/**
	 * Start the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new JB4_3();
	}

}
