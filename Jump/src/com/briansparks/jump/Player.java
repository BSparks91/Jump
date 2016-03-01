package com.briansparks.jump;

public class Player {

	public Player() {}
	
	public int speed;
	public int XCoordinate, YCoordinate;
	public boolean animateXThresholdReached, animateYThresholdReached;
	public boolean hasCrashed = false;
	
    // This function will be used to calculate the location in which the animated character will crash into the ground.
    // The returned coordinates will be used as p3 in the Animate function in order to hydrate Pend for the quadradic Bezier curve.
    public float[] CalculateCrashPoint(float x1, float y1, float x2, float y2, int velocity)
    {
    	float coordinates[] = new float[2];
    	
    	boolean left = false;
    	boolean right = false;
    	
    	//Placeholder for testing
    	coordinates[0] = x2+100;
    	coordinates[1] = y1;
    	
    	float slope = 0;
    	
    	//Calculate slope to make sure new point is on the same directional path
    	slope = ((y2-y1)/(x2-x1));
    	
    	// First, determine whether the swipe was left or right, then determine verticality
    	//Right?
    	if (x2 > x1)
    	{
    		right = true;
    	}
    	else left = true;
    	
    	// upward swipe
    	if (y2 < y1)
    	{
    		if (right == true)
    		{
        		coordinates[0] = x2 + 100;
    		}
    		else coordinates[0] = x2 - 100;
    		
    		coordinates[1] = y1;
    	}
    	// downward swipe
    	else if (y2 > y1)
    	{
    		
    	}
    	
		return coordinates;
    } 
	
    public int GetVelocity(float x1, float y1, float x2, float y2)
    {
    	int velocity = 0;
    	float xDiv, yDiv;
    	
    	//Calculate X distance between 1 and 2
    	float xDistance = x2 - x1;
    	
    	//Calculate Y distance between 1 and 2
    	float yDistance = y2 - y1;
    	
    	if ((xDistance <= 100.0) && (yDistance <= 100.0))
    	{
    		velocity = 1;
    	}  	
    	else if ((xDistance > 100.0) || (yDistance > 100.0))
    	{
    		xDiv = (xDistance / 100);
    		yDiv = (yDistance / 100);
    		
    		if (xDiv > yDiv)
    		{
    			velocity = (int) xDiv;
    		}
    		else 
    			velocity = (int) yDiv;
    	}
    	
    	
    	return velocity;
    }
}
