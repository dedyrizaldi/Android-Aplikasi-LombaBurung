package com.hobuku.hobu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class Slider extends Activity {
	private ViewFlipper viewFlipper;
	private float lastX;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slider);
		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);
	}

	
	 @SuppressLint("ResourceType")
	 public boolean onTouchEvent(MotionEvent touchevent) {
	    	switch (touchevent.getAction()) {
	        
	        case MotionEvent.ACTION_DOWN: 
	        	lastX = touchevent.getX();
	            break;
	        case MotionEvent.ACTION_UP: 
	            float currentX = touchevent.getX();
	            if (lastX < currentX) {
	                if (viewFlipper.getDisplayedChild() == 0)
	                	break;
	                
	                viewFlipper.setInAnimation(this, R.layout.slide_in_from_left);
	                viewFlipper.setOutAnimation(this, R.layout.slide_out_to_right);
	                viewFlipper.showNext();
	             }
	             if (lastX > currentX) {
	            	 if (viewFlipper.getDisplayedChild() == 1)
	            		 break;
	    
	            	 viewFlipper.setInAnimation(this, R.layout.slide_in_from_right);
	            	 viewFlipper.setOutAnimation(this, R.layout.slide_out_to_left);
	                 viewFlipper.showPrevious();
	             }
	             break;
	    	 }
	         return false;
	    }

}
