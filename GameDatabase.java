package com.finalexam;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDatabase extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "records.db";
	public static final String DATABASE_TABLE = "gamerecord";
	private static final int DATABASE_VERSION = 1;
	public final String COLUMN_ID = "id";
	public final String COLUMN_NUMBER = "guestnumber";
	public final String COLUMN_ATTEMPTS = "attemptsnumber";
	
	public Cursor cursor;
	public String sql = null;
	
	
	public GameDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(android.database.sqlite.SQLiteDatabase conn) {
		conn.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + COLUMN_ID + " Integer PRIMARY KEY AUTOINCREMENT, " + COLUMN_NUMBER + " text, " + COLUMN_ATTEMPTS + " text)");
	}
	

	@Override
	public void onUpgrade(android.database.sqlite.SQLiteDatabase conn, int OldVersion, int NewVersion) {
		// TODO Auto-generated method stub
		conn.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
		onCreate(conn);
	}
	
	public boolean AddRecords(String guestnumber, String attemptsnumber) {
		android.database.sqlite.SQLiteDatabase conn = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(COLUMN_NUMBER, guestnumber);
		values.put(COLUMN_ATTEMPTS, attemptsnumber);
		conn.insert(DATABASE_TABLE, null, values);
		
		return true;
	}
	
	public ArrayList <String> GetAllData(){
		android.database.sqlite.SQLiteDatabase conn = this.getReadableDatabase();
		
		ArrayList<String> recordsList = new ArrayList<String>();
		
		sql = "SELECT * FROM " + DATABASE_TABLE;
		
		Cursor cursor = conn.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
	        do {
	            String guestNumber = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER));
	            String attemptsNumber = cursor.getString(cursor.getColumnIndex(COLUMN_ATTEMPTS));

	            // Create a formatted record string
	            String record = "Guest Number: " + guestNumber + ", Attempts: " + attemptsNumber;
	            recordsList.add(record);
	        } while (cursor.moveToNext());
	    }
		cursor.close();
		return recordsList;
		
	}

}
