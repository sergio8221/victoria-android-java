package com.badlogic.androidgames.jumper;

import java.util.Random;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class AmigaDisco extends DynamicGameObject{
	public static final int AMIGA_STATE_NORMAL = 0;
	public static final int AMIGA_STATE_HIT = 1;
	public static final float AMIGA_WIDTH = 1;
	public static final float AMIGA_HEIGHT = 0.6f;
	public static final float AMIGA_VELOCITY = 3f;
	Random rand = new Random();
	public int type = rand.nextInt(8);
	
	int state;
	float stateTime = 0;
	
	public AmigaDisco(float x,float y){
		super(x,y,AMIGA_WIDTH,AMIGA_HEIGHT);
		velocity.set(AMIGA_VELOCITY,0);
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime,velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(AMIGA_WIDTH/2, AMIGA_HEIGHT/2);
		
		if(position.x < AMIGA_WIDTH/2){
			position.x = AMIGA_WIDTH/2;
			velocity.x = AMIGA_VELOCITY;
		}
		
		if(position.x > World.WORLD_WIDTH - AMIGA_WIDTH/2){
			position.x = World.WORLD_WIDTH - AMIGA_WIDTH/2;
			velocity.x = -AMIGA_VELOCITY;
		}
		
		stateTime += deltaTime;
	}
	
	public void hitPiedra(){
		velocity.set(0,0);
		state = AMIGA_STATE_HIT;
		stateTime = 0;
	}

}