package com.badlogic.androidgames.jumper;

import com.badlogic.androidgames.framework.Music;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets {
	
	public static Texture background;
	public static TextureRegion backgroundRegion;
	public static Texture backgroundDisco;
	public static TextureRegion backgroundDiscoRegion;
	public static Texture backgroundVaquero;
	public static TextureRegion backgroundVaqueroRegion;

	public static Texture items;
	public static TextureRegion mainMenu;
	public static TextureRegion pauseMenu;
	public static TextureRegion ready;
	public static TextureRegion gameOver;
	public static TextureRegion highScoresRegion;
	public static TextureRegion logo;
	public static TextureRegion soundOn;
	public static TextureRegion soundOff;
	public static TextureRegion arrow;
	public static TextureRegion cross;
	public static TextureRegion pause;
	public static TextureRegion spring;
	public static TextureRegion castle;
	public static TextureRegion bullet;
	public static TextureRegion stone;
	public static Animation coinAnim;
	public static Animation bobJump;
	public static Animation bobFall;
	public static TextureRegion bobHit;
	public static TextureRegion blood;
	public static TextureRegion heart;
	public static Animation squirrelFly;
	public static Animation squirrelFly2;
	public static Animation squirrelFly3;
	public static Animation rober;
	public static Animation victor;
	public static Animation vaquero;
	public static Animation vaquero2;
	public static Animation alberto;
	public static Animation mario;
	public static Animation jeni;
	public static Animation lucia;
	public static Animation patri;
	public static TextureRegion platform;
	public static TextureRegion torch;
	public static Animation brakingPlatform;
	public static Animation torchlight;
	public static Font font;
	
	public static Music music;
	
	public static Sound jumpSound;
	public static Sound highJumpSound;
	public static Sound hitSound;
	public static Sound coinSound;
	public static Sound clickSound;
	public static Sound shootStone;
	public static Sound shootBullet;
	
	public static void load(GLGame game){
		background = new Texture(game,"background.png");
		backgroundRegion = new TextureRegion(background,0,0,320,480);
		backgroundDisco = new Texture(game,"backgrounddisco.png");
		backgroundDiscoRegion = new TextureRegion(backgroundDisco,0,0,320,480);
		backgroundVaquero = new Texture(game,"backgroundvaquero.png");
		backgroundVaqueroRegion = new TextureRegion(backgroundVaquero,0,0,320,480);

		items = new Texture(game,"items.png");
		
		mainMenu = new TextureRegion(items,0,448,600,220);
		pauseMenu = new TextureRegion(items,448,256,384,192);
		ready = new TextureRegion(items,640,448,384,64);
		gameOver = new TextureRegion(items,704,512,320,192);
		highScoresRegion = new TextureRegion(items,0,520,600,220/3);
		logo = new TextureRegion(items,0,704,548,284);
		soundOff = new TextureRegion(items,0,0,128,128);
		soundOn = new TextureRegion(items,128,0,128,128);
		arrow = new TextureRegion(items,0,128,128,110);
		cross = new TextureRegion(items,640,768,128,128);
		pause = new TextureRegion(items,128,128,128,110);
		spring = new TextureRegion(items,256,0,64,64);
		castle = new TextureRegion(items,256,128,128,120);
		bullet = new TextureRegion(items,384,192,64,64);
		stone = new TextureRegion(items,384,128,64,64);
		coinAnim = new Animation(0.2f,
				new TextureRegion(items,256,64,64,64),
				new TextureRegion(items,320,64,64,64),
				new TextureRegion(items,384,64,64,64),
				new TextureRegion(items,320,64,64,64));
		bobJump = new Animation(0.2f,
				new TextureRegion(items,0,248,64,70),
				new TextureRegion(items,64,248,64,70));
		bobFall = new Animation(0.2f,
				new TextureRegion(items,128,248,64,70),
				new TextureRegion(items,192,248,64,70));
		bobHit = new TextureRegion(items,256,248,64,70);
		blood = new TextureRegion(items,896,704,64,64);
		heart = new TextureRegion(items,960,704,64,64);
		torch = new TextureRegion(items,640,896,128,128);
		squirrelFly = new Animation(0.2f,
				new TextureRegion(items,0,320,64,64),
				new TextureRegion(items,64,320,64,64));
		squirrelFly2 = new Animation(0.2f,
				new TextureRegion(items,0,384,64,64),
				new TextureRegion(items,64,384,64,64));		
		squirrelFly3 = new Animation(0.2f,
				new TextureRegion(items,256,320,64,64),
				new TextureRegion(items,320,320,64,64));
		rober = new Animation(0.2f,
				new TextureRegion(items,768,704,64,64),
				new TextureRegion(items,832,704,64,64));
		victor = new Animation(0.2f,
				new TextureRegion(items,256,384,64,64),
				new TextureRegion(items,320,384,64,64));
		vaquero = new Animation(0.2f,
				new TextureRegion(items,640,704,64,64),
				new TextureRegion(items,704,704,64,64));
		vaquero2 = new Animation(0.2f,
				new TextureRegion(items,768,896,128,128),
				new TextureRegion(items,896,896,128,128));
		alberto = new Animation(0.2f,
				new TextureRegion(items,768,768,64,64),
				new TextureRegion(items,832,768,64,64));
		mario = new Animation(0.2f,
				new TextureRegion(items,320,0,64,64),
				new TextureRegion(items,384,0,64,64));
		jeni = new Animation(0.2f,
				new TextureRegion(items,768,832,64,64),
				new TextureRegion(items,832,832,64,64));
		lucia = new Animation(0.2f,
				new TextureRegion(items,896,768,64,64),
				new TextureRegion(items,960,768,64,64));
		patri = new Animation(0.2f,
				new TextureRegion(items,896,832,64,64),
				new TextureRegion(items,960,832,64,64));
		platform = new TextureRegion(items,128,320,128,32);
		brakingPlatform = new Animation(0.2f,
				new TextureRegion(items,128,320,128,32),
				new TextureRegion(items,128,352,128,32),
				new TextureRegion(items,128,384,128,32),
				new TextureRegion(items,128,416,128,32));
		torchlight = new Animation(0.08f,
				new TextureRegion(items,896,256,64,64),
				new TextureRegion(items,960,256,64,64),
				new TextureRegion(items,896,320,64,64),
				new TextureRegion(items,960,320,64,64));
		font = new Font(items,448,0,16,32,40);
		music = game.getAudio().newMusic("music.mp3");
		music.setLooping(true);
		music.setVolume(0.8f);
		if(Settings.soundEnabled)
			music.play();
		jumpSound = game.getAudio().newSound("jump.ogg");
		highJumpSound = game.getAudio().newSound("highjump.ogg");
		hitSound = game.getAudio().newSound("hit.ogg");
		coinSound = game.getAudio().newSound("coin.ogg");
		clickSound = game.getAudio().newSound("click.ogg");
		shootStone = game.getAudio().newSound("stone.ogg");
		shootBullet = game.getAudio().newSound("shotbullet.ogg");
		
	}
	
	public static void reload(){
		background.reload();
		items.reload();
		if(Settings.soundEnabled)
			music.play();
	}
	
	public static void playSound(Sound sound){
		if(Settings.soundEnabled)
			sound.play(1);
	}

}
