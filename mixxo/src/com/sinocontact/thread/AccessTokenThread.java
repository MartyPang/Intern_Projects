package com.sinocontact.thread;

import java.util.Timer;
import java.util.TimerTask;


public class AccessTokenThread extends Thread implements Runnable{
	GetWXMessage getWXMessage = new GetWXMessage();
	
	@Override
	public void run() {
		 Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
	            public void run() {
	            	getWXMessage.getAccessToken();
	            }
	        }, 100, 1000*60*60);
	}
}
