package com.suresh.emergencylocationidentifier;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.SmsManager;
import android.util.FloatMath;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class Location_Msg_Shake extends Service implements SensorEventListener{

	private static final float SHAKE_THRESHOLD_GRAVITY = 1.7F;
	private static final int SHAKE_SLOP_TIME_MS = 500;
	private static final int SHAKE_COUNT_RESET_TIME_MS = 3000;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	//private ShakeDetector mShakeDetector;
	private OnShakeListener mListener;
	private long mShakeTimestamp;
	private int mShakeCount;
	GPSTracker gps;
	PowerManager pm;
	PowerManager.WakeLock wl;
	SpUtils utils;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		gps = new GPSTracker(Location_Msg_Shake.this);
		utils=new SpUtils(getApplicationContext());
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
		wl.acquire();

		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		// ShakeDetector initialization
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		//	mShakeDetector = new ShakeDetector();

		setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake(int count) {



				// check if GPS enabled     
				if(gps.canGetLocation()){

					double latitude = gps.getLatitude();
					double longitude = gps.getLongitude();
					LocationAddress locationAddress = new LocationAddress();
					locationAddress.getAddressFromLocation(latitude, longitude, getApplicationContext(), 
							new GeocoderHandler());

					Toast.makeText(getApplicationContext(), "GPS SHAKED!!!!!!!!!", Toast.LENGTH_SHORT).show();

				}else{
					Toast.makeText(getApplicationContext(), "SHAKED!!!!!!!!!", Toast.LENGTH_SHORT).show();
					SpUtils utils=new SpUtils(getApplicationContext());

					String userDetails=utils.getDetails();
					String[] listOfDetails = userDetails.split(",");
					if(listOfDetails.length == 2){
						String name = listOfDetails[0];
						String mobile = listOfDetails[1];
						sendMyEmergencyInfo(mobile, "Cant Get Address");
					}
				}


				

			}
		});
		// it continuously running until it is stopped
		mSensorManager.registerListener(Location_Msg_Shake.this, mAccelerometer,    SensorManager.SENSOR_DELAY_UI);
		return START_STICKY;

	}

	public void setOnShakeListener(OnShakeListener listener) {
		this.mListener = listener;
	}

	public interface OnShakeListener {
		public void onShake(int count);
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {


	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		if (mListener != null) {
			float x = event.values[0];
			float y = event.values[1];
			float z = event.values[2];

			float gX = x / SensorManager.GRAVITY_EARTH;
			float gY = y / SensorManager.GRAVITY_EARTH;
			float gZ = z / SensorManager.GRAVITY_EARTH;

			// gForce will be close to 1 when there is no movement.
			float gForce = FloatMath.sqrt(gX * gX + gY * gY + gZ * gZ);

			if (gForce > SHAKE_THRESHOLD_GRAVITY) {
				final long now = System.currentTimeMillis();
				// ignore shake events too close to each other (500ms)
				if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
					return;
				}

				// reset the shake count after 3 seconds of no shakes
				if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
					mShakeCount = 0;
				}

				mShakeTimestamp = now;
				mShakeCount++;

				mListener.onShake(mShakeCount);
			}
		}
	}
	@Override
	public void onDestroy() {
		super.onDestroy();

		Intent in = new Intent();
		in.setAction("YouWillNeverKillMe");
		getApplicationContext().sendBroadcast(in);
		Log.d("MyService.class", "onDestroy()...");

		try{
			mSensorManager.unregisterListener(Location_Msg_Shake.this);
			wl.release();
			Toast.makeText(this, "Your Background Service is Stopped", Toast.LENGTH_LONG).show();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	
	}
	private void sendMyEmergencyInfo(String mobile,String locationAddress){
		try {
			SmsManager sms = SmsManager.getDefault();
			ArrayList<String> parts = sms.divideMessage("Your friend may be in danger , he is at this location. "+locationAddress); 
			sms.sendMultipartTextMessage(mobile, null, parts, null, null);
			//sms.sendTextMessage(mobile, null, locationAddress, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private  class  GeocoderHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
			String locationAddress;
			switch (message.what) {
			case 1:
				Bundle bundle = message.getData();
				locationAddress = bundle.getString("address");
				break;
			default:
				locationAddress = "";
			}
			SpUtils utils=new SpUtils(getApplicationContext());

			String userDetails=utils.getDetails();
			String[] listOfDetails = userDetails.split(",");
			if(listOfDetails.length == 2){
				String name = listOfDetails[0];
				String mobile = listOfDetails[1];
				sendMyEmergencyInfo(mobile, locationAddress);
			}





		}
	}
}
