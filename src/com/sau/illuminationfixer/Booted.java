package com.sau.illuminationfixer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Booted extends BroadcastReceiver{
	
	 @Override
	    public void onReceive(Context context, Intent intent) {
	     Intent myIntent = new Intent(context, Control.class);
	     context.startService(myIntent);
	     Intent myIntent2 = new Intent(context, LightService.class);
	     context.startService(myIntent2);
	    }

}
