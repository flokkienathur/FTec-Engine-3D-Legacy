package com.rt96h.timer;

public class Timer {

	private long startTime;
	private long endTime;

	public Timer() {

	}

	public void start() {
		startTime = System.nanoTime();
	}
	
	public void stop(){
		endTime = System.nanoTime();
	}

	public float getTimeSeconds() {
		return getTimeMillis() / 1000f;
	}

	public float getTimeMillis() {
		return getTimeNanos() / 1000000f;
	}

	public long getTimeNanos() {
		return endTime - startTime;
	}
}
