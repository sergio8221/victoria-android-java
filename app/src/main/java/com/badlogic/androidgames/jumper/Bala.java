package com.badlogic.androidgames.jumper;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Bala extends DynamicGameObject{
	public static final int BALA_STATE_FLY = 0;
	public static final int BALA_STATE_HIT = 1;
	public static final float BALA_FLY_VELOCITY = 11;
	public static final float BALA_WIDTH = 0.8f;
	public static final float BALA_HEIGHT = 0.8f;
	
	int state;
	float stateTime;
	
	public Bala(float x, float y){
		super(x,y,BALA_WIDTH,BALA_HEIGHT);
		state = BALA_STATE_FLY;
		stateTime = 0;
	}
	
	public void update(float deltaTime){
		velocity.set(0, -BALA_FLY_VELOCITY);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width/2,bounds.height/2);
		
		
		stateTime += deltaTime;
	}
	
	public void hitAlgo(){
		velocity.set(0,0);
		state = BALA_STATE_HIT;
		stateTime = 0;
	}

}
