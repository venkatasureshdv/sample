package com.suresh.emergencylocationidentifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.emergencylocationidentifier.R;

public class Function_Button_Activity extends Activity{
	Button start,stop,update,logout;

	SpUtils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.functionality);
		utils=new SpUtils(getApplicationContext());
		//details=(TextView)findViewById(R.id.details);
		start=(Button)findViewById(R.id.start);
		stop=(Button)findViewById(R.id.stop);
		update=(Button)findViewById(R.id.update);
		logout=(Button)findViewById(R.id.logout);

		if(utils.isEmergencyServiceRunnig()){
			start.setVisibility(View.INVISIBLE);
			
		}
		else{
			stop.setVisibility(View.INVISIBLE);
		}

		String name="",mail="",mobile="";
		String userDetails=utils.getDetails();
		String[] listOfDetails = userDetails.split(",");
		if(listOfDetails.length == 2){
			name = listOfDetails[0];
			mobile = listOfDetails[1];
			
			TextView	details=(TextView)findViewById(R.id.number);
			details.setText("Name: "  +name+"\n"+"\n"+"Mobile: "  +mobile);

		}


		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				utils.logoutUser();
				finish();
			}
		});

		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			GPSTracker gps = new GPSTracker(Function_Button_Activity.this);
			if(gps.canGetLocation()){
				stop.setVisibility(View.VISIBLE);
				start.setVisibility(View.INVISIBLE);
				utils.setEmergencyServiceStatus(true);
				Intent i=new Intent(getApplicationContext(), Location_Msg_Shake.class);
				startService(i);


				
			}
			else{

			gps.showSettingsAlert();
			}
				

			}
		});
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				start.setVisibility(View.VISIBLE);
				stop.setVisibility(View.INVISIBLE);
				utils.setEmergencyServiceStatus(false);
				Intent i=new Intent(getApplicationContext(), Location_Msg_Shake.class);
				stopService(i);

			}
		});
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i=new Intent(getApplicationContext(), EmergencyDetails.class);
				String name="",mail="",mobile="";
				String userDetails=utils.getDetails();
				String[] listOfDetails = userDetails.split(",");
				if(listOfDetails.length == 2){
					name = listOfDetails[0];
					mobile = listOfDetails[1];

					i.putExtra("name", name);
					i.putExtra("mobile", mobile);


				}

				startActivity(i);
				finish();

			}
		});
	}


}
