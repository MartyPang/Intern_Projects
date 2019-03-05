package Thread;

import java.util.Timer;
import java.util.TimerTask;
import com.sinocontact.action.WXDateAction;

public class ThreadTimer extends Thread implements Runnable{
	GetWXMessage getWXMessage = new GetWXMessage();
	
	@Override
	public void run() {
		 Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
	            public void run() {
	            	getWXMessage.getAccessToken();
	            	//wXDateAction.getJsapi_ticket();
	            }
	        }, 100, 1000*60*60);
	}
}
