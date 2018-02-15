package com.mygdx.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.gamehelpers.AssetLoader;
import com.mygdx.gameobjects.HealthBar;
import com.mygdx.gameobjects.Powerup;
import com.mygdx.gameobjects.Projectile;

public class GameRenderer {

	private GameWorld myWorld;
	private OrthographicCamera cam;
	private ShapeRenderer shapeRenderer;

	private SpriteBatch batcher;

	private int midPointY;
	private int gameHeight;
	private int gameWidth;
	public String dHP;
	public String eHP;
	public String eSC;

	// Gameplay-Related Numbers
	private int touchX;
	private int touchY;
	private boolean fingerDown;
	private TimeUtils timer;
	private int level;
	
	// Game Objects
	private Projectile projectile;
	private HealthBar dragonHealth;
	private HealthBar enemyArmy;
	private Powerup powerup;

	// Game Assets
	private TextureRegion arrow, missile, bomb;
	private TextureRegion dragonf01;
	private TextureRegion attack01f01;
	private Animation attack01, attack02, eyeAnimation, lightningAnimation, catAnimation, digitalAnimation;
	private TextureRegion castle, cloud;
	private TextureRegion puBalloon;
	private TextureRegion blueFlamesIcon, explosionIcon, catIcon, lightningIcon, eyeIcon, digitalIcon;
	private TextureRegion dragonHealth25, dragonHealth50;

	public GameRenderer(GameWorld world, int gameHeight, int gameWidth,
			int midPointY) {
		myWorld = world;
		this.dragonHealth = myWorld.getDragonHealth();
		this.enemyArmy = myWorld.getEnemyArmy();
		dHP = (String) Integer.toString(dragonHealth.getHealth());
		eHP = (String) Integer.toString(enemyArmy.getHealth());
		eSC = (String) Integer.toString(enemyArmy.getSoldierAmt());
		this.level = world.getLevel();

		this.gameHeight = gameHeight;
		this.gameWidth = gameWidth;
		this.midPointY = midPointY;
		this.touchX = -100;
		this.touchY = -100;
		this.fingerDown = false;
		
		this.timer = world.getTime();

		cam = new OrthographicCamera();
		cam.setToOrtho(true, 136, gameHeight);

		batcher = new SpriteBatch();
		batcher.setProjectionMatrix(cam.combined);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setProjectionMatrix(cam.combined);

		// Call helper methods to initialize instance variables
		initGameObjects();
		initAssets();
	}

	long startTime = System.currentTimeMillis(); 
	public void render(float runTime) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.Filled);
				
		// Draw Grass
		shapeRenderer.setColor(10 / 255.0f, 142 / 255.0f, 8 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 55, 136, 40);

		// Draw Dirt
		shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 77, 136, 52);

		shapeRenderer.setColor(0 / 255.0f, 0 / 255.0f, 0 / 255.0f, 1);
		shapeRenderer.rect(0, midPointY + 87, 136, 32);

		shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, 1);
		shapeRenderer.line(gameWidth - 1 - 10 * eHP.length(), midPointY + 89,
				gameWidth - 1 - 10 * eHP.length(), midPointY + 99);
			
		shapeRenderer.end();

		// Sprite work begins here
		batcher.begin();
		batcher.enableBlending();
		
		if (myWorld.getKillSoldier() == true) {
			// gameScreen pauses:
			// do dragon blowing fire animation
			// trigger big explosions and enemy soldier falling down
			// make sure timer allows this to run for like 5 seconds
			// unpause game
			myWorld.setKillSoldier(false);
			myWorld.getScreen().resume();
		}
		
		// Draw castle background
		batcher.draw(castle, 0, midPointY - 200);

		// Draw the powerup balloon OR the icon was the balloon is gone
		if (powerup.wasItTapped() == false) {
		batcher.draw(puBalloon, powerup.getX(), powerup.getY(),
				powerup.getWidth(), powerup.getHeight());
		}
		else {
			// write a method here that takes an int -- powerup.getIcon()
			// if (powerup.getIcon() == 1) {
		batcher.draw(blueFlamesIcon, powerup.getIconRect().x, powerup.getIconRect().y, 14, 14);
		}

		// Decide which arrows to draw based on arrows form
		if (projectile.getForm() == 'a') {
			batcher.draw(arrow, projectile.getX(), projectile.getY(),
					projectile.getWidth(), projectile.getHeight());
		} else if (projectile.getForm() == 'b') {
			batcher.draw(missile, projectile.getX(), projectile.getY(),
					projectile.getWidth(), projectile.getHeight());
		} else {
			batcher.draw(bomb, projectile.getX(), projectile.getY(),
					projectile.getWidth(), projectile.getHeight());
		}

		// Draw cloud 1 on a timer
		drawCloud1(batcher, startTime);
		
		
		// Execute code if finger is currently pressed to screen
		if (fingerDown == true) {
			drawPowerupAnimations(runTime, batcher);			
		}

		// Draw the dragon (and later his animations based on Timer)
		batcher.draw(dragonf01, 0, midPointY + 34, 137, 55);

		// Draw fonts and values
		String scoreStr = "" + myWorld.getScore();
		AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2)
				- (3 * scoreStr.length() - 2), 11);

		AssetLoader.font.draw(batcher, "" + dragonHealth.getHealth(), 1,
				midPointY + 89);
		AssetLoader.font.draw(batcher, "" + enemyArmy.getHealth(), gameWidth
				- 10 * eHP.length(), midPointY + 89);
		AssetLoader.font.draw(batcher, "" + enemyArmy.getSoldierAmt(),
				gameWidth - 3 - 10 * (eHP.length() + eSC.length()),
				midPointY + 89);

		batcher.disableBlending();

		batcher.end();
	}
	
	float cloudX = -100f;
	float cloudY = 2f;
	public void drawCloud1(SpriteBatch batcher, long startTime) {
		long currentTime = System.currentTimeMillis() / 1000;
		if (currentTime % 1 == 0) {
			cloudX += 0.03f;
		}
		if (cloudX > gameWidth) {
			cloudX = -100f; }
		
		batcher.draw(cloud, cloudX, cloudY);
		
	}
	
	public void drawPowerupAnimations(float runTime, SpriteBatch batcher) {
		if (myWorld.getCurPowerup() == 3)  { // green lightning
		// this one draws differently than others
		batcher.draw(lightningAnimation.getKeyFrame(runTime), touchX
				- lightningAnimation.getKeyFrame(runTime).getRegionWidth() / 2f + 12f,
				touchY - lightningAnimation.getKeyFrame(runTime).getRegionHeight()
						/ 2f - 10f, lightningAnimation.getKeyFrame(runTime)
						.getRegionWidth(), lightningAnimation.getKeyFrame(runTime)
						.getRegionHeight());
		}
		if (myWorld.getCurPowerup() == 2)  {
		batcher.draw(digitalAnimation.getKeyFrame(runTime), touchX
				- digitalAnimation.getKeyFrame(runTime).getRegionWidth() / 2f,
				touchY - digitalAnimation.getKeyFrame(runTime).getRegionHeight()
						/ 2f, digitalAnimation.getKeyFrame(runTime)
						.getRegionWidth(), digitalAnimation.getKeyFrame(runTime)
						.getRegionHeight());
		}
		if (myWorld.getCurPowerup() == 4)  {
			batcher.draw(catAnimation.getKeyFrame(runTime), touchX
					- catAnimation.getKeyFrame(runTime).getRegionWidth() / 2f,
					touchY - catAnimation.getKeyFrame(runTime).getRegionHeight()
							/ 2f, catAnimation.getKeyFrame(runTime)
							.getRegionWidth(), catAnimation.getKeyFrame(runTime)
							.getRegionHeight());
			}
		if (myWorld.getCurPowerup() == 5)  {
			batcher.draw(eyeAnimation.getKeyFrame(runTime), touchX
					- eyeAnimation.getKeyFrame(runTime).getRegionWidth() / 2f,
					touchY - eyeAnimation.getKeyFrame(runTime).getRegionHeight()
							/ 2f, eyeAnimation.getKeyFrame(runTime)
							.getRegionWidth(), eyeAnimation.getKeyFrame(runTime)
							.getRegionHeight());
			}
		if (myWorld.getCurPowerup() == 1)  {
			batcher.draw(attack02.getKeyFrame(runTime), touchX
					- attack02.getKeyFrame(runTime).getRegionWidth() / 2f,
					touchY - attack02.getKeyFrame(runTime).getRegionHeight()
							/ 2f, attack02.getKeyFrame(runTime)
							.getRegionWidth(), attack02.getKeyFrame(runTime)
							.getRegionHeight());
			}
		if (myWorld.getCurPowerup() == 0)  {
			batcher.draw(attack01.getKeyFrame(runTime), touchX
					- attack01.getKeyFrame(runTime).getRegionWidth() / 2f,
					touchY - attack01.getKeyFrame(runTime).getRegionHeight()
							/ 2f, attack01.getKeyFrame(runTime)
							.getRegionWidth(), attack01.getKeyFrame(runTime)
							.getRegionHeight());
			}
	}
	
		
	public void setFingerDown(boolean b) {
		this.fingerDown = b;
	}

	public void setTouchX(int touchX) {
		this.touchX = touchX;
	}

	public void setTouchY(int touchY) {
		this.touchY = touchY;
	}

	private void initGameObjects() {
		projectile = myWorld.getProjectile();
		powerup = myWorld.getPowerup();
	}

	private void initAssets() {
		arrow = AssetLoader.arrow;
		missile = AssetLoader.missile;
		bomb = AssetLoader.bomb;
		dragonf01 = AssetLoader.dragonf01;
		attack01f01 = AssetLoader.attack01f01;
		attack01 = AssetLoader.attack01;
		attack02 = AssetLoader.attack02;
		eyeAnimation = AssetLoader.eyeAnimation;
		catAnimation = AssetLoader.catAnimation;
		digitalAnimation = AssetLoader.digitalAnimation;
		lightningAnimation = AssetLoader.lightningAnimation;
		castle = AssetLoader.castle;
		puBalloon = AssetLoader.puBalloon;
		blueFlamesIcon = AssetLoader.blueFlamesIcon;
		explosionIcon = AssetLoader.explosionIcon;
		catIcon = AssetLoader.ghostCatIcon;
		lightningIcon = AssetLoader.greenLightIcon;
		eyeIcon = AssetLoader.darkEyeIcon;
		digitalIcon = AssetLoader.digitalIcon;
		dragonHealth25 = AssetLoader.dragonHealthIcon25;
		dragonHealth50 = AssetLoader.dragonHealthIcon50;
		cloud = AssetLoader.cloud;
	}

}
