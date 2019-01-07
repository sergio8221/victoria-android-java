package com.badlogic.androidgames.jumper;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Piedra extends DynamicGameObject{
	public static final int PIEDRA_STATE_FLY = 0;
	public static final int PIEDRA_STATE_HIT = 1;
	public static final float PIEDRA_FLY_VELOCITY = 11;
	public static final float PIEDRA_WIDTH = 0.8f;
	public static final float PIEDRA_HEIGHT = 0.8f;
	
	int state;
	float stateTime;
	
	public Piedra(float x, float y){
		super(x,y,PIEDRA_WIDTH,PIEDRA_HEIGHT);
		state = PIEDRA_STATE_FLY;
		stateTime = 0;
	}
	
	public void update(float deltaTime){
		velocity.set(0, PIEDRA_FLY_VELOCITY);
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width/2,bounds.height/2);
		
		
		stateTime += deltaTime;
	}
	
	public void hitAlgo(){
		velocity.set(0,0);
		state = PIEDRA_STATE_HIT;
		stateTime = 0;
	}

}
