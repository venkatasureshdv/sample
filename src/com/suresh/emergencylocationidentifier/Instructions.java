package com.suresh.emergencylocationidentifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.emergencylocationidentifier.R;



public class Instructions extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
		Button addDetails=(Button) findViewById(R.id.Add);
		addDetails.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(getApplicationContext(), EmergencyDetails.class);
				startActivity(i);
				finish();
				
			}
		});
			
	}

	}
