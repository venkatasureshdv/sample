package com.suresh.emergencylocationidentifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceStartReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {

		SpUtils utils = new SpUtils(context);
		if(utils.isEmergencyServiceRunnig()){
			Intent i=new Intent(context,Location_Msg_Shake.class);
			context.startService(i);
		}
		
		
	}

}
