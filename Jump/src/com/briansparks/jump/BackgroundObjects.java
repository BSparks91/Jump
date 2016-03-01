package com.briansparks.jump;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BackgroundObjects {
	
	public BackgroundObjects() {}
	
	int PlaneX, PlaneY;
	Bitmap plane; 
	
	private int PlaneOffsetX = 10, PlaneOffsetY = 10;
	private int GroundOffSetX = 10, GroundOffsetY = 10;
	
	public void AdjustBackgroundSceneryLocation(int x, int y)
	{
		
	}
	
	public void AdjustPlaneLocation(int x, int y)
	{
		
	}
	
	public void AdjustGroundLocation(int x, int y)
	{
		
	}
	
    public static void populateBackgroundScenery(Canvas canvas, int height, int sceneryX, int sceneryY, Bitmap bitmap, Paint paint)
    {
 	
    	//Draw clouds
    	if (height < 1000)
    	{
    		canvas.drawBitmap(bitmap, sceneryX, sceneryY, paint);
    		
    	}
    	
    	//Draw something in the middle etc.	
    		
    	//Draw stars
 
    }
    
    public static int[] generateRandomBackgroundSceneryCoordinates()
    {
    	int[] result = new int[2];
    	
    	Random generateCoordinate = new Random();
    	
    	int valuex = generateCoordinate.nextInt(1000);
    	int valuey = generateCoordinate.nextInt(1000 - 100) + 100;
    	
    	result[0] = valuex;
    	result[1] = valuey;
    	
    	return result;
    }
}
