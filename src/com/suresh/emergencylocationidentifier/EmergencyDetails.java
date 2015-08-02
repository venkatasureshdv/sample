package com.suresh.emergencylocationidentifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emergencylocationidentifier.R;

public class EmergencyDetails extends Activity {
	EditText name,number,email;
	Button save;
	SpUtils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emergencyinfo);

		utils=new SpUtils(getApplicationContext());
		name=(EditText)findViewById(R.id.name);
		number=(EditText)findViewById(R.id.number);

		save=(Button)findViewById(R.id.save);
		// these r for getting intents while updating
		String pname=getIntent().getStringExtra("name");
		String pnumber=getIntent().getStringExtra("mobile");

		name.setText(pname);
		number.setText(pnumber);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(name.getText().toString().equals("")||number.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "All Fields Are Mandatory", Toast.LENGTH_LONG).show();
				}
				else{
					if(utils.isValidNumber(number.getText().toString())){
						utils.addDetails(name.getText().toString(), number.getText().toString());
						Toast.makeText(getApplicationContext(), "Success!!!", Toast.LENGTH_LONG).show();
						Intent i=new Intent(getApplicationContext(), Function_Button_Activity.class);
						startActivity(i);
						finish();
					}
					else{
						number.setError("Plz Enter a Vaild Mobile Number");
					}
				}

			}
		});
	}

}
