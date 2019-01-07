package com.badlogic.androidgames.jumper;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class VaqueroRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	GLGraphics glGraphics;
	//World world;
	VaqueroWorld disco;
	Camera2D cam;
	SpriteBatcher batcher;
	
	public VaqueroRenderer(GLGraphics glGraphics,SpriteBatcher batcher,VaqueroWorld disco){
		this.glGraphics = glGraphics;
		this.disco = disco;
		this.cam = new Camera2D(glGraphics,FRUSTUM_WIDTH,FRUSTUM_HEIGHT);
		this.batcher = batcher;
	}
	
	public void render(){
		//if(world.bob.position.y > cam.position.y)
			//cam.position.y = world.bob.position.y;
		cam.setViewportAndMatrices();
		renderBackground();
		renderObjects();
	}
	
	public void renderBackground(){
		batcher.beginBatch(Assets.backgroundVaquero);
		batcher.drawSprite(cam.position.x, cam.position.y, FRUSTUM_WIDTH, FRUSTUM_HEIGHT, Assets.backgroundVaqueroRegion);
		batcher.endBatch();
	}
	
	public void renderObjects(){
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		renderLights();
		renderMarlene();
		renderPortero();
		renderAmigas();
		renderBalas();
		renderPiedras();
		renderAntorchas();	
		batcher.endBatch();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	private void renderMarlene(){
		TextureRegion keyFrame;
		switch(disco.marlene.state){
		case MarleneDisco.BOB_STATE_NORMAL:
			keyFrame = Assets.bobFall.getKeyFrame(disco.marlene.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case MarleneDisco.BOB_STATE_HIT:
		default:
			keyFrame = Assets.blood;
		}
		
		float side = disco.marlene.velocity.x < 0 ? -1:1;
		batcher.drawSprite(disco.marlene.position.x, disco.marlene.position.y, side*1.3f, 1.3f, keyFrame);
	}
	
	private void renderPortero(){
		TextureRegion keyFrame;
		switch(disco.portero.state){
		case Portero.PORTERO_STATE_NORMAL:
			keyFrame = Assets.vaquero2.getKeyFrame(disco.portero.stateTime, Animation.ANIMATION_LOOPING);
			break;
		case Portero.PORTERO_STATE_HIT:
		default:
			keyFrame = Assets.blood;
			break;
		}
		
		float side = disco.portero.velocity.x < 0 ? -1:1;
		batcher.drawSprite(disco.portero.position.x, disco.portero.position.y, side*1.6f, 1.6f, keyFrame);
	}
	
	private void renderPiedras(){
		int len = disco.piedras.size();
		for(int i=0;i<len;i++){
			Piedra piedra = disco.piedras.get(i);
			if(piedra.state != Piedra.PIEDRA_STATE_HIT){
				//TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(piedra.stateTime, Animation.ANIMATION_LOOPING);
				batcher.drawSprite(piedra.position.x, piedra.position.y, 0.8f, 0.8f,360*piedra.stateTime, Assets.stone);
			}
		}
	}
	
	private void renderBalas(){
		int len = disco.balas.size();
		for(int i=0;i<len;i++){
			Bala bala = disco.balas.get(i);
			if(bala.state != Bala.BALA_STATE_HIT){
				//TextureRegion keyFrame = Assets.coinAnim.getKeyFrame(bala.stateTime, Animation.ANIMATION_LOOPING);
				batcher.drawSprite(bala.position.x, bala.position.y, 0.8f, 0.8f, Assets.bullet);
			}
		}
	}
	
	private void renderAmigas(){
		int len = disco.amigas.size();
		for(int i=0;i<len;i++){
			AmigaDisco amiga = disco.amigas.get(i);
			if(amiga.state != AmigaDisco.AMIGA_STATE_HIT){
			switch(amiga.type){
			case 0:
				TextureRegion keyFrame = Assets.mario.getKeyFrame(amiga.stateTime,Animation.ANIMATION_LOOPING);
				float side = amiga.velocity.x < 0 ? -1:1;
				batcher.drawSprite(amiga.position.x,amiga.position.y,side*1.3f,1.3f,keyFrame);
				break;
			case 1:
				TextureRegion keyFrame2 = Assets.jeni.getKeyFrame(amiga.stateTime,Animation.ANIMATION_LOOPING);
				float side2 = amiga.velocity.x < 0 ? -1:1;
				batcher.drawSprite(amiga.position.x,amiga.position.y,side2*1.3f,1.3f,keyFrame2);
				break;
			case 2:
				TextureRegion keyFrame3 = Assets.lucia.getKeyFrame(amiga.stateTime,Animation.ANIMATION_LOOPING);
				float side3 = amiga.velocity.x < 0 ? -1:1;
				batcher.drawSprite(amiga.position.x,amiga.position.y,side3*1.3f,1.3f,keyFrame3);
				break;
			}
			}
			if(amiga.state == AmigaDisco.AMIGA_STATE_HIT){
				batcher.drawSprite(amiga.position.x,amiga.position.y,1,1,Assets.blood);
			}
		}
	}
	
	private void renderAntorchas(){
		batcher.drawSprite(0.3f, 12, -2.5f, 2.5f, Assets.torch);
		batcher.drawSprite(0.3f, 8, -2.5f, 2.5f, Assets.torch);
		batcher.drawSprite(0.3f, 4, -2.5f, 2.5f, Assets.torch);
		batcher.drawSprite(9.7f, 12, 2.5f, 2.5f, Assets.torch);
		batcher.drawSprite(9.7f, 8, 2.5f, 2.5f, Assets.torch);
		batcher.drawSprite(9.7f, 4, 2.5f, 2.5f, Assets.torch);
	}
	
	public void renderLights(){
		TextureRegion keyFrame = Assets.torchlight.getKeyFrame(disco.tiempoTotal, Animation.ANIMATION_LOOPING);
		TextureRegion keyFrame2 = Assets.torchlight.getKeyFrame(disco.tiempoTotal+0.1f, Animation.ANIMATION_LOOPING);
		TextureRegion keyFrame3 = Assets.torchlight.getKeyFrame(disco.tiempoTotal+0.2f, Animation.ANIMATION_LOOPING);
		float size = 15;
		batcher.drawSprite(0.7f, 11, size, size, keyFrame);
		batcher.drawSprite(0.7f, 7, size, size, keyFrame2);
		batcher.drawSprite(0.7f, 3, size, size, keyFrame3);
		batcher.drawSprite(9.3f, 11, size, size, keyFrame2);
		batcher.drawSprite(9.3f, 7, size, size, keyFrame3);
		batcher.drawSprite(9.3f, 3, size, size, keyFrame);
	}
	

}