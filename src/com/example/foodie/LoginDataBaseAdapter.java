package com.example.foodie;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.foodie.databasehelper;


public class LoginDataBaseAdapter {
	static final String DATABASE_NAME = "login.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	Cursor cursor;
	// TODO: Create public field for each column in your table.
	// SQL Statement to create a new database.
	static final String DATABASE_CREATE = "create table "+"LOGIN"+
	                             "( " +"ID"+" integer primary key autoincrement,"+ "USERNAME  text,PASSWORD text,EMAIL text,ADDRESS text); ";
	// Variable to hold the database instance
	public  SQLiteDatabase db;
	// Context of the application using the database.
	private final Context context;
	// Database open/upgrade helper
	private databasehelper dbHelper;
	public  LoginDataBaseAdapter(Context _context) 
	{
		context = _context;
		dbHelper = new databasehelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public  LoginDataBaseAdapter open() throws SQLException 
	{
		db = dbHelper.getWritableDatabase();
		return this;
	}
	public void close() 
	{
		db.close();
	}

	public  SQLiteDatabase getDatabaseInstance()
	{
		return db;
	}

	public void insertEntry(String userName,String password,String address,String email)
	{
       ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("USERNAME", userName);
		newValues.put("PASSWORD",password);
		newValues.put("ADDRESS",address);
		newValues.put("EMAIL",email);

		// Insert the row into your table
		db.insert("LOGIN", null, newValues);
		///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
	}
	public int deleteEntry(String UserName)
	{
		//String id=String.valueOf(ID);
	    String where="USERNAME=?";
	    int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
       // Toast.makeText(context, "Number for Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
	}	
	public String getSinlgeEntry(String userEmail)
	{
		db=dbHelper.getReadableDatabase();
		cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{userEmail}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
        	cursor.close();
        	return "NOT EXIST";
        }
	    cursor.moveToFirst();
		String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
    	cursor.close();
		return password;				
	}
	public void  updateEntry(String email,String password)
	{
		db = dbHelper.getWritableDatabase();
		// Define the updated row content.
		ContentValues updatedValues = new ContentValues();
		// Assign values for each row.
		updatedValues.put("EMAIL", email);
		updatedValues.put("PASSWORD",password);

        String where="EMAIL = ?";
	    db.update("LOGIN",updatedValues, where, new String[]{email});			   
	}		
	public String getname(String userEmail){
		db=dbHelper.getReadableDatabase();
		cursor=db.query("LOGIN", null, " EMAIL=?", new String[]{userEmail}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
        	cursor.close();
        	return "NOT EXIST";
        }
	    cursor.moveToFirst();	
       String name=cursor.getString(cursor.getColumnIndex("USERNAME"));
       cursor.close();
		return name;
	}
}
