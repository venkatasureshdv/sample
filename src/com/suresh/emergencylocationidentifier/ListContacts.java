/*package com.suresh.emergencylocationidentifier;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.EditText;

import com.example.emergencylocationidentifier.R;

public class ListContacts {
	Cursor c1;
	Context mContext;
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	    if (resultCode == RESULT_OK) {  
	        switch (requestCode) {  
	        case CONTACT_PICKER_RESULT:
	            final EditText phoneInput = (EditText) findViewById(R.id.phoneNumberInput);
	            Cursor cursor = null;  
	            String phoneNumber = "";
	            List<String> allNumbers = new ArrayList<String>();
	            int phoneIdx = 0;
	            try {  
	                Uri result = data.getData();  
	                String id = result.getLastPathSegment();  
	                cursor = getContentResolver().query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + "=?", new String[] { id }, null);  
	                phoneIdx = cursor.getColumnIndex(Phone.DATA);
	                if (cursor.moveToFirst()) {
	                    while (cursor.isAfterLast() == false) {
	                        phoneNumber = cursor.getString(phoneIdx);
	                        allNumbers.add(phoneNumber);
	                        cursor.moveToNext();
	                    }
	                } else {
	                    //no results actions
	                }  
	            } catch (Exception e) {  
	               //error actions
	            } finally {  
	                if (cursor != null) {  
	                    cursor.close();
	                }

	                final CharSequence[] items = allNumbers.toArray(new String[allNumbers.size()]);
	                AlertDialog.Builder builder = new AlertDialog.Builder(your_class.this);
	                builder.setTitle("Choose a number");
	                builder.setItems(items, new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int item) {
	                        String selectedNumber = items[item].toString();
	                        selectedNumber = selectedNumber.replace("-", "");
	                        phoneInput.setText(selectedNumber);
	                    }
	                });
	                AlertDialog alert = builder.create();
	                if(allNumbers.size() > 1) {
	                    alert.show();
	                } else {
	                    String selectedNumber = phoneNumber.toString();
	                    selectedNumber = selectedNumber.replace("-", "");
	                    phoneInput.setText(selectedNumber);
	                }

	                if (phoneNumber.length() == 0) {  
	                    //no numbers found actions  
	                }  
	            }  
	            break;  
	        }  
	    } else {
	       //activity result error actions
	    }  
	}
}*/