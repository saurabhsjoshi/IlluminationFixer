package com.sau.illuminationfixer;

import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
	private int SCREEN_STATE = 0;
	 
    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	Intent i = new Intent(context, Control.class);    	
        
    	if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF) || intent.getAction().equals(Intent.ACTION_SHUTDOWN) ) {
            SCREEN_STATE = 0;
        	i.putExtra("value", 0);
            i.putExtra("screen_state", SCREEN_STATE);
            
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
        	SCREEN_STATE = 1;
        	 i.putExtra("value", ran());
        	 i.putExtra("screen_state", SCREEN_STATE);
        }
        
        else if (intent.getAction().equals("com.sau.lightch")) {
        	int val = intent.getIntExtra("Level", 2);
        	
        	 
        	
        	i.putExtra("screen_state", SCREEN_STATE);
        	
        	if (val == 1)
        		i.putExtra("value", ran());
        	else
        		i.putExtra("value", 0);
       }
        context.startService(i);
    }
    
    private int ran(){
    	//WHY DOSNT IT WORK IF I PUT SAME VALUE TWICE :/
    	int Low = 75;
   	    int High = 150;
   	    Random r = new Random();
   	    int R = r.nextInt(High-Low) + Low;
   	    Log.i("SR" , "Setting Value to: " + R);
   	    return R;
    }
 
}
