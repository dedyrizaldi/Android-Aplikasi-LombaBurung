package com.hobuku.hobu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class Loading extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		 new Thread() {
			   public void run() {
			      try{Thread.sleep(800);}
			      catch (Exception e) {}
			            Intent i = new Intent(Loading.this, Login.class);
			            Loading.this.finish();
			           startActivity(i); 
			     } }.start(); 
	}

}
