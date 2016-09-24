package com.example.foodie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Menu_page extends ActionBarActivity {
	TextView text,summary;
	Button burger,pizza;
	int prize=0;
	String mess="Transaction Successfull\nSUMMARY:\nNAME : ";
	LoginDataBaseAdapter obj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		init();	
		Intent inten=getIntent();
		String name=obj.getname(inten.getStringExtra("email"));
		mess+=name+"\nItems\n";
		burger.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				prize+=50;
				mess+="Burger : Rs.50\n";
			text.setText("TOTAL : Rs."+prize);	
			}
		});
     pizza.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				prize+=100;
				mess+="Pizza : Rs.100\n";
			text.setText("TOTAL : Rs."+prize);	
			}
		});
	}
private void init() {
	text=(TextView)findViewById(R.id.total);
	burger=(Button)findViewById(R.id.button1);
	pizza=(Button)findViewById(R.id.button2);
	obj=new LoginDataBaseAdapter(this);
	}
public void order(View v) {
	final Dialog box=new Dialog(this);
	box.setTitle("Order Summary");
	box.setContentView(R.layout.last_page);
	mess+="Total : Rs."+prize;
	summary=(TextView)box.findViewById(R.id.isummary);
	Button exit=(Button)box.findViewById(R.id.exit);
	Button logout=(Button)box.findViewById(R.id.logout);
	summary.setText(mess);
	exit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		text.setText("TOTAL : Rs.0");
      		
      		mess=mess.replaceAll("Total : Rs."+prize,"");
      		prize=0;
      		mess=mess.replaceAll(".*Pizza : Rs.100.*", "");
      		mess=mess.replaceAll(".*Burger : Rs.50.*", "");
      		box.dismiss();	
		}
	});
	logout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(), Main_page.class));
		}
	});
	box.show();
	
}

public void removeb(View v) {
	Pattern p=Pattern.compile(".*\\bBurger\\b.*",Pattern.CASE_INSENSITIVE);
	Matcher m=p.matcher(mess);
	if(m.find())
	{
		prize-=50;
		text.setText("TOTAL : Rs."+prize);
		mess=mess.replaceAll(".*Burger : Rs.50.*", "");
	}		
}
public void removep(View v) {
	Pattern p=Pattern.compile(".*\\bPizza\\b.*",Pattern.CASE_INSENSITIVE);
	Matcher m=p.matcher(mess);
	if(m.find())
	{
		prize-=100;
		text.setText("TOTAL : Rs."+prize);
		mess=mess.replaceAll(".*Pizza : Rs.100.*", "");
	}		
}
}
