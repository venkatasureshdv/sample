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

public class LoginActivity extends Activity {
	EditText username,password;
	Button login,register;
	SpUtils utils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		utils=new SpUtils(getApplicationContext());

		//doing all inflation
		username=(EditText) findViewById(R.id.username);
		password=(EditText) findViewById(R.id.password);
		login=(Button)findViewById(R.id.loginbtn);
		
		//Button ONCLICK LISTENER with anonymous inner class
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				//Checking if fields are filled r not
				if(!username.getText().toString().equals("")&& !password.getText().toString().equals("")){

					if(username.getText().toString().equals("suresh")&& password.getText().toString().equals("dv")){

						SpUtils objSpf = new SpUtils(LoginActivity.this);
						String name="",mail="",mobile="";
						String details = objSpf.getDetails();
						String[] listOfDetails = details.split(",");
						if(listOfDetails.length == 2){
							name = listOfDetails[0];
							mobile = listOfDetails[1];


						}

						if(name.length()>0 && mobile.length()>0){

							Intent i = new Intent(getApplicationContext(), Function_Button_Activity.class);


							startActivity(i);
							finish();
						}else{

							Intent i = new Intent(getApplicationContext(), Instructions.class);


							startActivity(i);
							finish();
						}

					}
					else{
						Toast.makeText(getApplicationContext(), "user name and password not matched", Toast.LENGTH_LONG).show();

					}

				}
				else{
					Toast.makeText(getApplicationContext(), "please enter user name and password", Toast.LENGTH_LONG).show();
				}

			}
		});
		
	}


}
