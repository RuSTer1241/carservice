package com.car.service.advertizement;

import android.os.Handler;
import android.os.Message;
import com.car.service.utils.WLog;

/**
 * Created by r.savuschuk on 9/29/2014.
 *
 */
public class AdvertiseThread extends  Thread {
	Handler handler;
	private boolean pause=true;
    private final long  ADVERTIZEMENT_TIMEDELAY=120; //second

	public AdvertiseThread( Handler handler) {

		this.handler=handler;
	}

	@Override
	public void run() {
		super.run();
		long counter=1;
		while(true) {
			synchronized (this) {
				try {
					this.wait(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if((counter%ADVERTIZEMENT_TIMEDELAY)==0){
				Message msg=new Message();
				msg.what=10;
				handler.sendMessage(msg);
			}
			if(!pause)
			   counter++;
		}

	}
	public void pauseTimer(){
		WLog.d("Timer", "paused");
		pause=true;
	}
	public void resumeTimer(){
       pause=false;
		WLog.d("Timer", "resumed");
	}

}
