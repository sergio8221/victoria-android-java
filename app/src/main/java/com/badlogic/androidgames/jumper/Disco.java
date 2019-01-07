package com.badlogic.androidgames.jumper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import android.util.Log;

import com.badlogic.androidgames.framework.Game;
//import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.math.OverlapTester;
import com.badlogic.androidgames.framework.math.Vector2;

public class Disco {
	public interface DiscoListener{
		public void jump();
		public void highJump();
		public void hit();
		public void coin();
	}
	
	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	
	public final MarleneDisco marlene;
	public final List<AmigaDisco> amigas;
	public final List<Piedra> piedras;
	public final List<Bala> balas;
	public final Portero portero;
	public final DiscoListener listener;
	public final Random rand;
	Game game;
	Camera2D guiCam;
	Vector2 touchPoint;
	
	public int score;
	public int state;
	public float timecount=0;
	public int pantalla;
	
	public Disco(DiscoListener listener,Game game,int stage){
		this.marlene = new MarleneDisco(5,1);
		this.amigas = new ArrayList<AmigaDisco>();
		this.balas = new ArrayList<Bala>();
		this.piedras = new ArrayList<Piedra>();
		this.portero = new Portero(5,13);
		this.listener = listener;
		this.game = game;
		this.pantalla=stage;
		rand = new Random();
		generateLevel();
		
		this.score = 0;
		this.state = WORLD_STATE_RUNNING;
	}
	
	
	private void generateLevel(){
		for(int i=0;i<3;i++){
			AmigaDisco amiga = new AmigaDisco((i+1)*2, 5 + (i+1)*1.5f);
			amiga.type = i;
			amiga.velocity.set(i+1,0);
			amigas.add(amiga);
		}
		
	}
	
	public void generatePiedra(float x, float y){
		Piedra piedra = new Piedra(x,y);
		piedras.add(piedra);
	}
	
	public void generateBala(float x, float y){
		Bala bala = new Bala(x,y);
		balas.add(bala);
	}
	
	public void update(float deltaTime, float accelX){
		updateMarlene(deltaTime,accelX);
		updateAmigas(deltaTime);
		updatePortero(deltaTime);
		updatePiedras(deltaTime);
		updateBalas(deltaTime);
		if(marlene.state!=MarleneDisco.BOB_STATE_HIT)
			checkCollisions();
		checkGameOver();
	}
	
	private void updateMarlene(float deltaTime,float accelX){
		if(marlene.state!=Bob.BOB_STATE_HIT)
			marlene.velocity.x = -accelX/10 * MarleneDisco.BOB_MOVE_VELOCITY;
		marlene.update(deltaTime);
	}
	
	private void updateAmigas(float deltaTime){
		int len = amigas.size();
		for(int i=0;i<len;i++){
			AmigaDisco amiga = amigas.get(i);
			amiga.update(deltaTime);
		}
	}
	
	private void updatePortero(float deltaTime){
		portero.update(deltaTime);
		//if(portero.state != Portero.PORTERO_STATE_HIT){
			timecount += deltaTime;
			if(timecount>0.7f){
				generateBala(portero.position.x,portero.position.y);
				Assets.playSound(Assets.shootBullet);
				timecount=0;
			}
		//}
	}
	
	private void updatePiedras(float deltaTime){
		int len = piedras.size();
		for(int i=0;i<len;i++){
			Piedra piedra = piedras.get(i);
			piedra.update(deltaTime);
		}
	}
	
	private void updateBalas(float deltaTime){
		int len = balas.size();
		for(int i=0;i<len;i++){
			Bala bala = balas.get(i);
			bala.update(deltaTime);
		}
	}
	
	
	private void checkCollisions(){
		checkAmigaCollisions();
		checkPorteroCollisions();
		checkMarleneCollisions();
	}
	
	private void checkAmigaCollisions(){
		int len = amigas.size();
		int len2 = piedras.size();
		for(int i=0;i<len;i++){
			AmigaDisco amiga = amigas.get(i);
			if(amiga.state!=AmigaDisco.AMIGA_STATE_HIT){
				for(int j=0;j<len2;j++){
					Piedra piedra = piedras.get(j);
					if(piedra.state!=Piedra.PIEDRA_STATE_HIT){
						if(OverlapTester.overlapRectangles(amiga.bounds, piedra.bounds)){
							amiga.hitPiedra();
							piedra.hitAlgo();
							score += 10;
						}
					}
				}
			}
		}
	}
	
	private void checkPorteroCollisions(){
		int len = piedras.size();
		for(int i=0;i<len;i++){
			Piedra piedra = piedras.get(i);
			if(piedra.state != Piedra.PIEDRA_STATE_HIT){
				if(OverlapTester.overlapRectangles(portero.bounds, piedra.bounds)){
					portero.hitPiedra();
					piedra.hitAlgo();
					score += 10;
					if(portero.state == Portero.PORTERO_STATE_HIT)
						state=WORLD_STATE_NEXT_LEVEL;
				}
		    }
		}
	}
	
	private void checkMarleneCollisions(){
		int len = balas.size();
		for(int i=0;i<len;i++){
			Bala bala = balas.get(i);
			if(bala.state != Bala.BALA_STATE_HIT){
				if(OverlapTester.overlapRectangles(marlene.bounds, bala.bounds)){
					marlene.hitBala();
					bala.hitAlgo();
					if(marlene.state == MarleneDisco.BOB_STATE_HIT)
						state=WORLD_STATE_GAME_OVER;
				}
			}
		}
	}
	
	private void checkGameOver(){
		if(marlene.state == MarleneDisco.BOB_STATE_HIT){
			state = WORLD_STATE_GAME_OVER;
		}
	}

}