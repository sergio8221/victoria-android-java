package com.badlogic.androidgames.jumper;

import java.util.Random;

import com.badlogic.androidgames.framework.DynamicGameObject;

public class Portero extends DynamicGameObject{
	
	public static final int PORTERO_STATE_NORMAL = 0;
	public static final int PORTERO_STATE_HIT = 1;
	public static final float PORTERO_WIDTH = 1;
	public static final float PORTERO_HEIGHT = 0.6f;
	public static final float PORTERO_VELOCITY = 4f;
	Random rand = new Random();
	public int type = rand.nextInt(8);
	
	int state;
	float stateTime = 0;
	int life = 5;
	
	public Portero(float x,float y){
		super(x,y,PORTERO_WIDTH,PORTERO_HEIGHT);
		velocity.set(PORTERO_VELOCITY,0);
	}
	
	public void update(float deltaTime){
		position.add(velocity.x * deltaTime,velocity.y * deltaTime);
		bounds.lowerLeft.set(position).sub(PORTERO_WIDTH/2, PORTERO_HEIGHT/2);
		
		if(position.x < PORTERO_WIDTH/2){
			position.x = PORTERO_WIDTH/2;
			velocity.x = PORTERO_VELOCITY;
		}
		
		if(position.x > World.WORLD_WIDTH - PORTERO_WIDTH/2){
			position.x = World.WORLD_WIDTH - PORTERO_WIDTH/2;
			velocity.x = -PORTERO_VELOCITY;
		}
		
		stateTime += deltaTime;
	}
	
	public void hitPiedra(){
		life -= 1;
		if(life == 0)
			muerto();
	}
	
	public void muerto(){
		velocity.set(0,0);
		state = PORTERO_STATE_HIT;
		stateTime = 0;
	}

}
