package com.briansparks.jump;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.view.GestureDetector;



public class game extends MainActivity
{
		
	   
	    
	    
	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
		   draw v = new draw(game.this);
	        
	        v = new draw(this);
	        setContentView(v); 
	   
        
	    }

}
	    
	


		
		  

