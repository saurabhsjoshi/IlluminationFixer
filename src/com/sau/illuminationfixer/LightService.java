package com.sau.illuminationfixer;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class LightService extends Service implements SensorEventListener {

	 private SensorManager mSensorManager;
	 private Sensor mLight;
	 private int val = 0;
	 private int waiter = 0;
	 private int TIME_OUT = 5;
	 @Override
     public void onCreate() {
         super.onCreate();
         mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
         mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
         mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
         }
	 
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		if(arg0.sensor.getType() == Sensor.TYPE_LIGHT){
			int lums = Math.round(arg0.values[0]);
			
			if (lums < 150){
				if (val!=1){
					waiter += 1;
					if (waiter > TIME_OUT)
						sendBroad(1);}
					}
			else{
				if(val!=2){
					waiter += 1;
					if (waiter > TIME_OUT)
						sendBroad(2);}
			}
		}
	}
	
	private void sendBroad(int value){
		val = value;
		waiter = 0;
		Intent i = new Intent();
		i.setAction("com.sau.lightch");
		i.putExtra("Level", value);
		sendBroadcast(i);
	}
	
	@Override
	public void onDestroy(){
		mSensorManager.unregisterListener(this);
		  super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
