package com.finalexam;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class RecordsActivity extends ListActivity{
	
	public GameDatabase conn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		conn = new GameDatabase(this);
		
		try {
			ArrayList<String> recordsList = conn.GetAllData();
			if(recordsList.size() > 0) {
				setListAdapter(new ArrayAdapter <String> (RecordsActivity.this,android.R.layout.simple_list_item_1,recordsList));
			}else {
				Toast.makeText(getApplicationContext(), "NO RECORD!", Toast.LENGTH_SHORT).show();
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT).show();
		}
	}

}
