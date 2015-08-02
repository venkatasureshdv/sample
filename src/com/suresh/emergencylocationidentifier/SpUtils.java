package com.suresh.emergencylocationidentifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtils {
	//Global Variables
	SharedPreferences spf;
	Editor editor;
	Context context;
	//The shared preference file can be accessed by calling the application
	int PRIVATE_MODE=0;
	private static final String spfname="emergency";
	public static final String user_name="name";
	public static final String personname="pname";
	public static final String isServiceRunnig="isServiceRunnig";


	public static final String mobile="mobile";
	public static final String password="password";
	public static final String is_login="isLoggedIn";

	//Constructor
	public SpUtils(Context context){
		this.context=context;
		spf=context.getSharedPreferences(spfname, PRIVATE_MODE);
		editor = spf.edit();
	}
	public void addDetails(String name, String number){
		SharedPreferences.Editor editor = spf.edit();
		editor.putString(personname, name);
		editor.putString(mobile, number);

		editor.commit();

	}
	public void setEmergencyServiceStatus(boolean isServiceRunnig){
		SharedPreferences.Editor editor = spf.edit();
		editor.putBoolean(SpUtils.isServiceRunnig,isServiceRunnig);
		editor.commit();
		
	}
	public boolean isEmergencyServiceRunnig(){
		return spf.getBoolean(isServiceRunnig, false);
		
	}
	public String getDetails(){

		return  spf.getString(personname, "")+","+
				spf.getString(mobile, "");
	}
	public void logoutUser(){
		// After logout redirect user to Login Activity
		Intent i = new Intent(context, LoginActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	public boolean isValidNumber(CharSequence number){

		Pattern p=Pattern.compile("[0|91]?[789][0-9]{9}");
		Matcher m=p.matcher(number);
		if(m.find()&&m.group().equals(number)){
			return true;
		}
		else {
			return false;
		}

	}

}
