package com.badlogic.androidgames.jumper;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

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

public class StoryEnd3 extends GLScreen{
	Camera2D guiCam;
	SpriteBatcher batcher;
	Rectangle nextBounds;
	//Rectangle skipBounds;
	Rectangle backBounds;
	Vector2 touchPoint;
	Texture helpImage;
	TextureRegion helpRegion;
	Texture text;
	TextureRegion textRegion;
	TextureRegion victor;
	
	public StoryEnd3(Game game){
		super(game);
		guiCam = new Camera2D(glGraphics,320,480);
		nextBounds = new Rectangle(320-102,15,64,64);
		//skipBounds = new Rectangle(150,20,40,40);
		backBounds = new Rectangle(50,15,64,64);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics,4);
	}
	
	@Override
	public void resume(){
		helpImage = new Texture(glGame,"storybackground.png");
		helpRegion = new TextureRegion(helpImage,0,0,320,480);
		text = new Texture(glGame,"textend3.png");
		textRegion = new TextureRegion(text,0,0,256,256);
		victor = Assets.victor.getKeyFrame(0.1f,Animation.ANIMATION_NONLOOPING);
	}
	
	@Override
	public void pause(){
		helpImage.dispose();
		text.dispose();
	}
	
	@Override
	public void update(float deltaTime){
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
					game.setScreen(new StoryEnd4(game));
					return;
				}
				/*if(OverlapTester.pointInRectangle(skipBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new GameScreen(game,0,1));
					return;
				}*/
				if(OverlapTester.pointInRectangle(backBounds, touchPoint)){
					Assets.playSound(Assets.clickSound);
					game.setScreen(new StoryEnd2(game));
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
		batcher.drawSprite(175, 160, 260, 260, textRegion);
		batcher.endBatch();
		
		batcher.beginBatch(Assets.items);
		batcher.drawSprite(200, 360, 70, 70, victor);
		batcher.drawSprite(130, 360, 70, 70, Assets.bobHit);
		batcher.drawSprite(250, 47, -64, 64, Assets.arrow);
		batcher.drawSprite(82, 47, 64, 64, Assets.arrow);
		//batcher.drawSprite(170, 40, 40, 40, Assets.cross);
		batcher.endBatch();
		
		gl.glDisable(GL10.GL_BLEND);
	}
	
	@Override
	public void dispose(){
		
	}

}