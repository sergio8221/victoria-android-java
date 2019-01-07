package com.badlogic.androidgames.jumper;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGraphics;

public class DiscoRenderer {
	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 15;
	GLGraphics glGraphics;
	//World world;
	Disco disco;
	Camera2D cam;
	SpriteBatcher batcher;
	
	public DiscoRenderer(GLGraphics glGraphics,SpriteBatcher batcher,Disco disco){
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
		batcher.beginBatch(Assets.backgroundDisco);
		batcher.drawSprite(cam.position.x, cam.position.y, FRUSTUM_WIDTH, FRUSTUM_HEIGHT, Assets.backgroundDiscoRegion);
		batcher.endBatch();
	}
	
	public void renderObjects(){
		GL10 gl = glGraphics.getGL();
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		batcher.beginBatch(Assets.items);
		renderMarlene();
		renderPortero();
		renderAmigas();
		renderBalas();
		renderPiedras();
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
			switch(disco.pantalla){
			case 2:
				keyFrame = Assets.rober.getKeyFrame(disco.portero.stateTime, Animation.ANIMATION_LOOPING);
				break;
			case 4:
				keyFrame = Assets.alberto.getKeyFrame(disco.portero.stateTime, Animation.ANIMATION_LOOPING);
				break;
			case 6:
			default:
				keyFrame = Assets.patri.getKeyFrame(disco.portero.stateTime, Animation.ANIMATION_LOOPING);
				break;
			}
			break;
		case Portero.PORTERO_STATE_HIT:
		default:
			keyFrame = Assets.blood;
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
				TextureRegion keyFrame = Assets.squirrelFly.getKeyFrame(amiga.stateTime,Animation.ANIMATION_LOOPING);
				float side = amiga.velocity.x < 0 ? -1:1;
				batcher.drawSprite(amiga.position.x,amiga.position.y,side*1.3f,1.3f,keyFrame);
				break;
			case 1:
				TextureRegion keyFrame2 = Assets.squirrelFly2.getKeyFrame(amiga.stateTime,Animation.ANIMATION_LOOPING);
				float side2 = amiga.velocity.x < 0 ? -1:1;
				batcher.drawSprite(amiga.position.x,amiga.position.y,side2*1.3f,1.3f,keyFrame2);
				break;
			case 2:
				TextureRegion keyFrame3 = Assets.squirrelFly3.getKeyFrame(amiga.stateTime,Animation.ANIMATION_LOOPING);
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
	

}