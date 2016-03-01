package com.briansparks.jump;


import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Entity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class draw extends View implements OnGestureListener, Runnable {

	Bitmap ground;
	Bitmap character;
	Bitmap bgCloud1;
	int x, y;
	int dx, dy;
	int charVelocity;
	float x1, x2, y1, y2, xend, yend;
	boolean fling;
	float i;
	float increment;
	boolean freeFallTrigger = false;
	int degrees = 0;
	int characterHeight = 0;
	int bgScenery[];
	
	Entity e = new Entity(null);

	Camera camera = new Camera();
	float cameraX, cameraY;
	
	public GestureDetector detector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener());
	
	int planeX, planeY;
	
	Player player = new Player();
	BackgroundObjects backgroundObjects = new BackgroundObjects();

	int pos[] = new int[2];
	
	Paint p = new Paint();
	
	int groundX, groundY;
	int origGroundX, origGroundY;
	
	public draw(Context context) {
		super(context);
		
		backgroundObjects.plane = BitmapFactory.decodeResource(getResources(), R.drawable.planeright2left);
		character = BitmapFactory.decodeResource(getResources(), R.drawable.drawing);
		ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
		bgCloud1 = BitmapFactory.decodeResource(getResources(),R.drawable.cloud1);
		
		x = 0;
		y = 0;
		
		groundX = -50;
		groundY = 1250;
		
		origGroundX = groundX;
		origGroundY = groundY;
		
		increment = 0;
		
		x1 = 0;
		x2 = 0;
		y1 = 0;
		y2 = 0;
		
		i = (float) 0.01;
		
		charVelocity = 1;
		
		planeY = 300;
		player.XCoordinate = 500;
		player.YCoordinate = 900;
		
		fling = false;
		
 		bgScenery = BackgroundObjects.generateRandomBackgroundSceneryCoordinates(); 
		
		setBackgroundResource(R.drawable.bk_sky);
	}
	
	
    @SuppressLint("NewApi")
	@Override
    protected void onDraw(Canvas canvas)
    {
   	
    	super.onDraw(canvas);
    	
    	BackgroundObjects.populateBackgroundScenery(canvas, characterHeight, bgScenery[0], bgScenery[1], bgCloud1, p);
    	
    	if (fling == false)
    	{
    		drawChar(canvas, 1, player.XCoordinate, player.YCoordinate);
    	}
    	
    	if (fling == false)
    	{
    		drawPlane(canvas, 1);
    	}
        
        if (fling == true)
        {
        	Animate(canvas);
        	
        	if (freeFallTrigger == false)
    		{     	
        		drawChar(canvas, 1, player.XCoordinate, player.YCoordinate); 
    		}
        	else 
    		{
        		degrees+= 4;
        		
    			canvas.save();
    			if (degrees <= 180)
    			{
    				canvas.rotate(degrees, player.XCoordinate, player.YCoordinate + (character.getHeight()/2));
    			}
    			else
    				canvas.rotate(180, player.XCoordinate, player.YCoordinate + (character.getHeight()/2));
    				
           		drawChar(canvas, 1, player.XCoordinate, player.YCoordinate); 
    			canvas.restore(); 
    		}	
        		
        	//canvas.save();
        	if (player.animateYThresholdReached == true)
        	{
	        	if (freeFallTrigger == false)
	        	{
	        		planeY += 10;
	        		groundY += 10;
	    			bgScenery[1] = bgScenery[1] + 5;
	        	}
	        	else 
	        	{
	        		if (groundY >= origGroundY)
	        		{
	        			planeY -= 10;
	        			groundY -= 10;
	        			bgScenery[1] = bgScenery[1] - 5;
	        		}
	        	}
        	}
        	
        	if ((player.animateXThresholdReached == true) && (player.hasCrashed == false))
        	{
        		if (player.XCoordinate < 200)
        		{
        			planeX += 10;
        			groundX += 10;
        		}
        		else if (player.XCoordinate > 800)
        		{
        			planeX -= 10;
        			groundX -= 10;
        		}
        	}
        	
        	//camera.translate(planeX, planeY+1, 1);
        	//camera.applyToCanvas(canvas);
        	
        	drawPlane(canvas, 1);
      
        	//canvas.restore();
        }
        
        canvas.drawBitmap(ground, groundX,  groundY, p);
        
        invalidate();
    }
    
    
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        float touchX = event.getX();  // or getRawX();
        float touchY = event.getY();
        
        switch(action){
        case MotionEvent.ACTION_DOWN:
            if (touchX >= player.XCoordinate && touchX < (player.XCoordinate + character.getWidth()) 
             && touchY >= player.YCoordinate && touchY < (player.YCoordinate + character.getHeight())) 
            {
  
           // image has been pressed, fling code here
            	if (detector.onTouchEvent(event)) 
            	{
                    return true;
                }
            	
            	x1 = touchX;
            	y1 = touchY;
            	
            	break;
   
            }
        case MotionEvent.ACTION_UP:
        	if (!(touchX >= player.XCoordinate && touchX < (player.XCoordinate + character.getWidth()) 
            && touchY >= player.YCoordinate && touchY < (player.YCoordinate + character.getHeight()))) 
        	{
    			if (detector.onTouchEvent(event)) 
    			{
    				return true;
    			}
    			
    			x2 = touchX;
    			y2 = touchY-800;
    			
    			fling = true;
    			charVelocity = player.GetVelocity(x1, y1, x2, y2);
    			
    			float CrashCoords[] = new float[2];
    			
    			CrashCoords = player.CalculateCrashPoint(x1, y1, x2, y2, charVelocity);
    			xend = CrashCoords[0];
    			yend = CrashCoords[1];
    			
                break;
        	}
 
        }
		return true;
    }
    
    
    
    protected void drawChar(Canvas canvas, int speed, float x, float y)
    {
    	canvas.drawBitmap(character, x, y, p);
    	
    	// y will equal 0 for a slight period before the transformations are processed.
    	if ((fling == true) && (player.YCoordinate < 400) && (player.YCoordinate != 0))
    	{
    		player.animateYThresholdReached = true;
    	}
    	else
    	{
    		player.animateYThresholdReached = false;
    	}
    	
    	if ((fling == true) && ((player.XCoordinate < 300) || (player.XCoordinate > 700)))
    	{
    		player.animateXThresholdReached = true;
    	}
    	else
    	{
    		player.animateXThresholdReached = false;
    	}
    	
    	if (player.YCoordinate >= 900)
    		player.hasCrashed = true;
    	else
    		player.hasCrashed = false;
    }
    
    
    protected void drawPlane(Canvas canvas, int speed)
    {
    	canvas.drawBitmap(backgroundObjects.plane, planeX, planeY, p);
    	
    
    	if (planeX < canvas.getWidth())
    	{
    		planeX += 2 * speed;
    	}
    	else
    	{
    		planeX = -400;
    	}	
    }

    

    
    
    public void Animate(Canvas canvas)
    {
    	float xa, ya, xb, yb;
    	float drawx, drawy;
    	
    //	for( float i = 0 ; i < 1 ; i += 0.01 )
    //	{
    	// The Green Line
    	if (increment < 1)
    	{
		    xa = getPt( x1 , x2 , increment );
		    ya = getPt( y1 , y2 , increment );
		    xb = getPt( x2 , xend , increment );
		    yb = getPt( y2 , yend , increment );
	
		    // The Black Dot
		    drawx = getPt( xa , xb , increment );
		    drawy = getPt( ya , yb , increment );
	
		    player.XCoordinate = (int) drawx;
		    player.YCoordinate = (int) drawy;
		    
		    increment += 0.01;
		    
		    if (increment > 0.5)
		    {
		    	freeFallTrigger = true;
		    }
    	}

	    
	    
	//    drawChar(canvas, charVelocity, player.XCoordinate, player.YCoordinate);

    //	}

    	
    }
    
    float getPt( float n1 , float n2 , float perc )
    {
        float diff = n2 - n1;

        return n1 + ( diff * perc );
    }  
    
    
    public void Gravity()
    {
    	
    	
    }
    
    

    
    // This function will calcuate the second point of the Bezier curve (actual 2nd point, as x2 will only represent where the user ends the swipe), 
    // the character will head towards this coordinate until reaching t = 0.5
    public float[] CalculateP2(float x1, float y1, float x2, float y2, int velocity)
    {
    	float coordinates[] = new float[2];


    	
    	return coordinates;
    }

  
    
    

    

    
    
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


 


}
