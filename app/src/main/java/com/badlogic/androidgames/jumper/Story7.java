package com.badlogic.androidgames.jumper;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

//import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;

public class Story7 extends GLScreen{
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle nextBounds;
	Rectangle skipBounds;
	Rectangle backBounds;
	Vector2 touchPoint;
	Texture helpImage;
	TextureRegion helpRegion;
	Texture text;
	TextureRegion textRegion;
	public final List<Squirrel> squirrels;
	long startTime;
	public static final float SQUIRREL_WIDTH = 1;
	public static final float SQUIRREL_HEIGHT = 0.6f;
	public static final float SQUIRREL_VELOCITY = 30f;
	
	public Story7(Game game){
		super(game);
		guiCam = new Camera2D(glGraphics,320,480);
		nextBounds = new Rectangle(320-102,15,64,64);
		skipBounds = new Rectangle(150,20,40,40);
		backBounds = new Rectangle(50,15,64,64);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics,6);
		this.squirrels = new ArrayList<Squirrel>();
		startTime = System.nanoTime();
		generateSquirrels();
		//Log.v("tag", "squirrels: " + squirrels.size());
	}
	
	private void generateSquirrels(){
		for(int i=0;i<3;i++){
			Squirrel squirrel = new Squirrel((i+1)*85, 380);
			squirrel.velocity.x = SQUIRREL_VELOCITY;
			squirrel.type = i;
			squirrels.add(squirrel);
		}
	}
	
	public void updateSquirrel(float deltaTime,int i){
		Squirrel squirrel = squirrels.get(i);
		squirrel.position.add(squirrel.velocity.x * deltaTime,squirrel.velocity.y * deltaTime);
		squirrel.bounds.lowerLeft.set(squirrel.position).sub(SQUIRREL_WIDTH/2, SQUIRREL_HEIGHT/2);
		
		if(squirrel.position.x < 50){
			squirrel.position.x = 50;
			squirrel.velocity.x = SQUIRREL_VELOCITY;
		}
		
		if(squirrel.position.x > 320-50 - SQUIRREL_WIDTH/2){
			squirrel.position.x = 320-50 - SQUIRREL_WIDTH/2;
			squirrel.velocity.x = -SQUIRREL_VELOCITY;
		}
		
		squirrel.stateTime += deltaTime;
	}
	
	@Override
	public void resume(){
		helpImage = new Texture(glGame,"storybackground.png");
		helpRegion = new TextureRegion(helpImage,0,0,320,480);
		text = new Texture(glGame,"text7.png");
		textRegion = new TextureRegion(text,0,0,256,256);
	}
	
	@Override
	public void pause(){
		helpImage.dispose();
		text.dispose();
	}
	
	@Override
	public void update(float deltaTime){
		//update squirrels
		for(int i=0;i<3;i++){
			updateSquirrel(deltaTime,i);
		}
		//hasta aquí
		
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for(int i=0;i<len;i++){
			TouchEvent event = touchEvents.get(i);
			touchPoint.set(event.x,event.y);
			guiCam.touchToWorld(touchPoint);
			
			if(event.type == TouchEvent.TOUCH_UP){
				if(OverlapTester.pointInRectangle(nextBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new GameScreen(game,0,1));
					return;
				}
				if(OverlapTester.pointInRectangle(backBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new Story6(game));
					return;
				}
				if(OverlapTester.pointInRectangle(skipBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new GameScreen(game,0,1));
					return;
				}
			}
		}
	}
	
	@Override
	public void present(float deltaTime){
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.setViewportAndMatrices();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		batcher.beginBatch(helpImage);
		batcher.drawSprite(160, 240, 320, 480, helpRegion);
		batcher.endBatch();
		
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(text);
		batcher.drawSprite(160, 210, 250, 250, textRegion);
		batcher.endBatch();
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(250, 47, -64, 64, Assets.arrow);
		batcher.drawSprite(82, 47, 64, 64, Assets.arrow);
		batcher.drawSprite(170, 40, 40, 40, Assets.cross);
		//render squirrels
		
		for(int i=0;i<3;i++){
			Squirrel squirrel = squirrels.get(i);
			switch(squirrel.type){
			case 0:
				TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(squirrel.stateTime,Animation.ANIMATION_LOOPING);
				float side = squirrel.velocity.x < 0 ? -1:1;
				batcher.drawSprite(squirrel.position.x,squirrel.position.y,side*64,64,keyFrame);
				break;
			case 1:
				TextureRegion keyFrame2 = Assets.squirrelFly2.getKeyFrame(squirrel.stateTime,Animation.ANIMATION_LOOPING);
				float side2 = squirrel.velocity.x < 0 ? -1:1;
				batcher.drawSprite(squirrel.position.x,squirrel.position.y,side2*64,64,keyFrame2);
				break;
			case 2:
				TextureRegion keyFrame3 = Assets.squirrelFly3.getKeyFrame(squirrel.stateTime,Animation.ANIMATION_LOOPING);
				float side3 = squirrel.velocity.x < 0 ? -1:1;
				batcher.drawSprite(squirrel.position.x,squirrel.position.y,side3*64,64,keyFrame3);
				break;
			}
			
			
		}
		//hasta aquí
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}
	
	@Override
	public void dispose(){
		
	}

}