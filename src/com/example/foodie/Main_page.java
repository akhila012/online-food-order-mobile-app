package com.example.foodie;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Main_page extends ActionBarActivity {
LinearLayout linear;
LinearLayout loginl;
EditText lemail,lpassword,address,semail,confirm_password,name,spassword;
LoginDataBaseAdapter data;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        assign();
      }      private void assign(){
        	linear =(LinearLayout)findViewById(R.id.linearLayout1);
            loginl=(LinearLayout)findViewById(R.id.login_layout);
            lemail=(EditText)findViewById(R.id.lemail);
            lpassword=(EditText)findViewById(R.id.lpassword);
            name=(EditText)findViewById(R.id.name);
            semail=(EditText)findViewById(R.id.semail);
            spassword=(EditText)findViewById(R.id.spassword);
            confirm_password=(EditText)findViewById(R.id.conpassword);
            address=(EditText)findViewById(R.id.address);
            data=new LoginDataBaseAdapter(this);

      }
   public void signup(View v){
     //  Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
          Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

          if(linear.getVisibility()==View.GONE){

              linear.startAnimation(slideDown);
              linear.setVisibility(View.VISIBLE);
    }
          else
         	 linear.setVisibility(View.GONE);
    }
    public void login(View v){
        //  Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
             Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

             if(loginl.getVisibility()==View.GONE){

                 loginl.startAnimation(slideDown);
                 loginl.setVisibility(View.VISIBLE);
       }
             else
            	 loginl.setVisibility(View.GONE);
       }

   public void account(View v){
	   data=data.open();
if(semail.getText().toString().equals("")||spassword.getText().toString().equals("")||confirm_password.getText().toString().equals("")){
        dis("Field Vaccant");
	return;
}
if(!spassword.getText().toString().equals(confirm_password.getText().toString())){
		dis("password does not match");
		return;
	}else{
 data.insertEntry(name.getText().toString(),spassword.getText().toString(),address.getText().toString(),semail.getText().toString());
 dis("Account Successfully Created ");
 Intent intent=new Intent(this,Menu_page.class);
 intent.putExtra("email",semail.getText().toString());
  startActivity(intent);
	}
   }
   public void submit(View v){
	   String user_id=lemail.getText().toString();
	   String user_pass=lpassword.getText().toString();
	   String stored_pass=data.getSinlgeEntry(user_id);
	   if(user_pass.equals(stored_pass)){
		   Intent intent=new Intent(this,Menu_page.class);
		   intent.putExtra("email", user_id);
		  startActivity(intent);
		  dis("login");
	   }else
             dis("wrong password");
   }
    public void update(View v){
	 final Dialog box=new Dialog(this);
	 box.setTitle("Reset Password");
	 box.setContentView(R.layout.new_password);
	 final EditText uemail=(EditText)box.findViewById(R.id.uemail);
	 final EditText upass=(EditText)box.findViewById(R.id.upassword);
	 final EditText uconpass=(EditText)box.findViewById(R.id.uconpassword);
     Button chn=(Button)box.findViewById(R.id.change);
     box.show();
    
    chn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		String uconp=uconpass.getText().toString();
		 String email=uemail.getText().toString();
	     String password=upass.getText().toString();
    if(!password.equals(uconp))
    { 
    	dis("password does not match");
        upass.setText("");
        uconpass.setText("");	
    	}
    else{
    	data.updateEntry(email, password);
        dis("updation successful");
           box.dismiss();
	}
		}
});
   }
    private void dis(String str) {
		Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    	protected void onDestroy() {
    		// TODO Auto-generated method stub
    		super.onDestroy();
    		//data.close();
    	}
}
