package com.sau.illuminationfixer;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.CommandCapture;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class Control extends Service{
	
	 @Override
     public void onCreate() {
         super.onCreate();
         // REGISTER RECEIVER THAT HANDLES SCREEN ON AND SCREEN OFF LOGIC
         IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
         filter.addAction(Intent.ACTION_SCREEN_OFF);
         filter.addAction(Intent.ACTION_SHUTDOWN);
         filter.addAction("com.sau.lightch");
         BroadcastReceiver mReceiver = new ScreenReceiver();
         registerReceiver(mReceiver, filter);
     }
	 
     @Override
     public void onStart(Intent intent, int startId) {
    	 int val = intent.getIntExtra("value", 0);
    	 
    	 int state = intent.getIntExtra("screen_state", 0);
    	 Log.i("Sensor changed" , "LEVEL: " + state );
         if (state == 1)
        	 changeLED(val);
         
         else{changeLED(0);}
     }
     
     private void changeLED(int val){
    	 String pre = "echo " + Integer.toString(val) + " > ";
    	 if (RootTools.isAccessGiven()) {
    		 CommandCapture command = new CommandCapture(0, pre +  "/sys/class/leds/so34-led0/brightness" , pre +  "/sys/class/leds/so34-led1/brightness",pre +  "/sys/class/leds/so34-led2/brightness");
    		 try {
    			 RootTools.getShell(true).add(command).waitForFinish();} catch (Exception e) {e.printStackTrace();} 
    			
    	 }
    	 
     }
     
     
     @Override 
     public void onDestroy(){
    	 changeLED(0);
    	 this.stopSelf();
    	 super.onDestroy();
     }
     

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
		
	}
}
