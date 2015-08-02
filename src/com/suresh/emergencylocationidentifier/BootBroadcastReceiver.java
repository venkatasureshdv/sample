package com.suresh.emergencylocationidentifier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {     
     
    @Override   
    public void onReceive(Context context, Intent intent) {   
        // BOOT_COMPLETED” start Service    
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {   
        	SpUtils utils=new SpUtils(context);
        	if(utils.isEmergencyServiceRunnig()){
        		  Intent serviceIntent = new Intent(context, Location_Msg_Shake.class);       
                  context.startService(serviceIntent);
        	}
        	
            //Service    
           //Intent serviceIntent = new Intent(context, MyService.class);       
           // context.startService(serviceIntent);   
        }   
    }    
}   
