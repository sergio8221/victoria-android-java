package com.badlogic.androidgames.jumper;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
//import com.badlogic.androidgames.framework.gl.FPSCounter;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Rectangle;
import com.badlogic.androidgames.framework.math.Vector2;
import com.badlogic.androidgames.jumper.Disco.DiscoListener;
import com.badlogic.androidgames.jumper.World.WorldListener;

public class DiscoScreen extends GLScreen{
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	
	int state;
	Camera2D guiCam;
	Vector2 touchPoint;
	SpriteBatcher batcher;
	Disco disco;
	DiscoListener discoListener;
	WorldListener worldListener;
	DiscoRenderer renderer;
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	Rectangle nextBounds;
	int lastScore;
	String scoreString;
	int pantalla;
	
	public DiscoScreen(Game game,int score,int stage){
		super(game);
		state = GAME_READY;
		guiCam = new Camera2D(glGraphics,320,480);
		touchPoint = new Vector2();
		batcher = new SpriteBatcher(glGraphics,1000);
		discoListener = new DiscoListener(){
			public void jump(){
				Assets.playSound(Assets.jumpSound);
			}
			
			public void highJump(){
				Assets.playSound(Assets.highJumpSound);
			}
			
			public void hit(){
				Assets.playSound(Assets.hitSound);
			}
			
			public void coin(){
				Assets.playSound(Assets.coinSound);
			}
		};
		
		disco = new Disco(discoListener,game,stage);
		renderer = new DiscoRenderer(glGraphics,batcher,disco);
		pauseBounds = new Rectangle(320-64,480-64,64,64);
		resumeBounds = new Rectangle(160-96,240,192,36);
		quitBounds = new Rectangle(160-96,240-36,192,36);
		nextBounds = new Rectangle(320-64,480-64,64,64);
		lastScore = 0;
		scoreString = "Puntos: 0";
		pantalla = stage;
		disco.score=score;
	}
	
	@Override
	public void update(float deltaTime){
		if(deltaTime > 0.1f)
			deltaTime = 0.1f;
		
		switch(state){
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}
	
	private void updateReady(){
		if(game.getInput().getTouchEvents().size()>0){
			state = GAME_RUNNING;
		}
	}
	
	private void updateRunning(float deltaTime){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i=0;i<len;i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type!=TouchEvent.TOUCH_UP)
				continue;
			touchPoint.set(event.x,event.y);
			guiCam.touchToWorld(touchPoint);
			
			if(OverlapTester.pointInRectangle(pauseBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				state = GAME_PAUSED;
				return;
			}
			if(touchPoint.y < 350){
				Assets.playSound(Assets.shootStone);
				disco.generatePiedra(disco.marlene.position.x,disco.marlene.position.y);
				return;
			}
		}
		
		
		
		disco.update(deltaTime, game.getInput().getAccelX());
		if(disco.score!=lastScore){
			lastScore = disco.score;
			scoreString= "Puntos: " + lastScore;
		}
		if(disco.state == Disco.WORLD_STATE_NEXT_LEVEL){
			state = GAME_LEVEL_END;
		}
		if(disco.state == Disco.WORLD_STATE_GAME_OVER){
			state = GAME_OVER;
			if(lastScore >= Settings.highscores[4])
				scoreString = "Puntos: " + lastScore;
			else
				scoreString = "Puntos: " + lastScore;
			Settings.addScore(lastScore);
			Settings.save(game.getFileIO());
		}
	}
	
	private void updatePaused(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i=0;i<len;i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type != TouchEvent.TOUCH_UP)
				continue;
			
			touchPoint.set(event.x,event.y);
			guiCam.touchToWorld(touchPoint);
			
			if(OverlapTester.pointInRectangle(resumeBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				return;
			}
			
			if(OverlapTester.pointInRectangle(quitBounds, touchPoint)){
				Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}
	
	private void updateLevelEnd(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i=0;i<len;i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type!=TouchEvent.TOUCH_UP)
				continue;
			
			touchPoint.set(event.x,event.y);
			guiCam.touchToWorld(touchPoint);
			
			if(OverlapTester.pointInRectangle(nextBounds, touchPoint)){
				if(pantalla==6)
					game.setScreen(new StoryEnd1(game,lastScore,pantalla));
				else
					game.setScreen(new StoryCastle(game,lastScore,pantalla+1));
			}
		}
	}
	
	private void updateGameOver(){
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for(int i=0;i<len;i++){
			TouchEvent event = touchEvents.get(i);
			if(event.type!=TouchEvent.TOUCH_UP)
				continue;
			game.setScreen(new MainMenuScreen(game));
		}
	}
	
	@Override
	public void present(float deltaTime){
		GL10 gl= glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		renderer.render();
		
		guiCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.items);
		
		switch(state){
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void presentReady(){
		//batcher.drawSprite(160, 240, 192, 32, Assets.ready);
		String topText = "Toca para";
		String bottomText = "apedrear!!!";
		float topWidth = 16 * topText.length();
		float bottomWidth = 16 * bottomText.length();
		Assets.font.drawText(batcher, topText, 160-topWidth/2, 480-190);
		Assets.font.drawText(batcher,bottomText,160-bottomWidth/2,190);
	}
	
	private void presentRunning(){
		batcher.drawSprite(320-32, 480-32, 64, 64, Assets.pause);
		Assets.font.drawText(batcher, scoreString, 16, 480-20);
		int lifeportero = disco.portero.life;
		for(int i=0;i<lifeportero;i++){
			batcher.drawSprite(15+(40*i),(480-50),25,25,Assets.heart);
		}
		int lifemarlene = disco.marlene.life;
		for(int i=0;i<lifemarlene;i++){
			batcher.drawSprite(15+(40*i),20,25,25,Assets.heart);
		}
	}
	
	private void presentPaused(){
		batcher.drawSprite(160, 240, 192, 96, Assets.pauseMenu);
		Assets.font.drawText(batcher, scoreString, 16, 480-20);
	}
	
	private void presentLevelEnd(){
		String topText = "Entra";
		String bottomText = "padentro!!!";
		float topWidth = 16 * topText.length();
		float bottomWidth = 16 * bottomText.length();
		Assets.font.drawText(batcher, topText, 160-topWidth/2, 480-190);
		Assets.font.drawText(batcher,bottomText,160-bottomWidth/2,190);
		batcher.drawSprite(320-32, 480-10, -64, 64, Assets.arrow);
	}
	
	private void presentGameOver(){
		batcher.drawSprite(160, 240, 160, 96, Assets.gameOver);
		float scoreWidth = 16 * scoreString.length();
		Assets.font.drawText(batcher, scoreString, 160-scoreWidth/2, 480-20);
	}
	
	@Override
	public void pause(){
		if(state==GAME_RUNNING)
			state = GAME_PAUSED;
	}
	
	@Override
	public void resume(){
		
	}
	
	@Override
	public void dispose(){
		
	}

}