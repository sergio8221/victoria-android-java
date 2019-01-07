package com.badlogic.androidgames.jumper;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class MarleneDisco extends DynamicGameObject{
	public static final int BOB_STATE_NORMAL = 0;
	public static final int BOB_STATE_HIT = 1;
	public static final float BOB_JUMP_VELOCITY = 11;
	public static final float BOB_MOVE_VELOCITY = 20;
	public static final float BOB_WIDTH = 0.8f;
	public static final float BOB_HEIGHT = 0.8f;
	
	int state;
	float stateTime;
	int life=2;
	
	public MarleneDisco(float x, float y){
		super(x,y,BOB_WIDTH,BOB_HEIGHT);
		state = BOB_STATE_NORMAL;
		stateTime = 0;
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(bounds.width/2,bounds.height/2);
		
		
		if(position.x > Disco.WORLD_WIDTH)
			position.x = Disco.WORLD_WIDTH;
		if(position.x < 0)
			position.x = 0;
		
		stateTime += deltaTime;
	}
	
	public void hitBala(){
		life -= 1;
		if(life == 0)
			muerto();
	}
	
	public void muerto(){
		velocity.set(0,0);
		state = BOB_STATE_HIT;
		stateTime = 0;
	}
	

}