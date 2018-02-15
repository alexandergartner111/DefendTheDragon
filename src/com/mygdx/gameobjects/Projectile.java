package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gameworld.GameWorld;

/**
 * Created by Alex Gartner on 2/24/2015.
 */
public class Projectile {

	// Vectors regarding speed and difficulty
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration;
	private float terminal;

	private Vector2 touchPosition;

	private int width;
	private int height;
	private float yCoord;
	private int gameWidth;
	private int dmg;
	private char form;

	private Rectangle boundingRect;

	public Random rng;
	
	private GameWorld myWorld;
	private HealthBar dragonHealth;
	

	public Projectile(GameWorld world, float x, float y, int width, int height, float gameWidth,
			HealthBar dragonHealth, int score) {
		this.myWorld = world;
		
		this.width = width;
		this.height = height;
		yCoord = y;
		this.gameWidth = (int) gameWidth;
		
		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 90);

		terminal = 90;

		boundingRect = new Rectangle();
		touchPosition = new Vector2(0, 0);
		rng = new Random();

		this.dmg = 1;
		this.form = 'a';
		this.dragonHealth = dragonHealth;
		
	}


	public void setForm(char z) {
		this.form = z;
	}

	public char getForm() {
		return this.form;
	}

	public int counter = 0;

	
	// Here, the "current powerup" will affect this;
	// Each powerup will either enlarge or make smaller the
	// bounding rect
	public void arrowHit(int screenX, int screenY, HealthBar hp) {
		

		screenY -= 5; // to account for a little bit of delay?
		touchPosition.set(screenX / 2.0f, screenY / 2.0f);

		if (boundingRect.contains(touchPosition) == true) {
			hp.takeHealth(1);
			myWorld.addScore(incrementScore());
			
			// if (hp.getHealth() % 10 == 0)
			position.x = rng.nextInt(gameWidth - 10);
			position.y = yCoord;
			velocity.y = 0;
			counter++;
			if (counter == 10) {
				hp.killSoldiers(1);
				myWorld.setKillSoldier(true);
				counter = 0;
			}
		}
	}

	private int incrementScore() {
		int addOn = 0;
		// Different scoring based on projectile type
		if (this.form == 'a') {
			addOn++;
		}
		else if (this.form == 'b') {
			addOn+= 10;
		}
		else if (this.form == 'c') {
			addOn += 35;
		}
		// INSERT POWERUP EFFECTS ON SCORE HERE
		 if (myWorld.getCurPowerup() == 1) {
		 addOn+=2; }
		 if (myWorld.getCurPowerup() == 2) {
			 addOn+=5; }
		 if (myWorld.getCurPowerup() == 3) {
			 addOn+=10; }
		 if (myWorld.getCurPowerup() == 4) {
			 addOn+=20; }
		 if (myWorld.getCurPowerup() == 5) {
			 addOn+=40; }
		
		return addOn;
	}

	public void update(float delta, int midPointY) {

		velocity.add(acceleration.cpy().scl(delta));

		// Check terminal velocity allowed
		if (velocity.y > terminal) {
			velocity.y = terminal;
		}

		// WHEN AN ARROW ISN'T DESTROYED AND GETS THROUGH
		if (position.y >= midPointY + 25) {
			if (this.form == 'a')
				this.dmg = 5;
			if (this.form == 'b')
				this.dmg = 20;
			if (this.form == 'c')
				this.dmg = 40;
			position.y = yCoord;
			position.x = rng.nextInt(gameWidth - 10);
			dragonHealth.takeHealth(dmg);
		}

		position.add(velocity.cpy().scl(delta));

		boundingRect.setPosition(position.x, position.y);
		boundingRect.setSize(width, height);

		// do I need this?
		touchPosition.scl(delta);

	}

	public void setTerminal(float terminal) {
		this.terminal = terminal;
	}

	public float getX() {
		return position.x;
	}

	public float getY() {
		return position.y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public Rectangle getBoundingRect() {
		return boundingRect;
	}
	
	public void setBoundingRect(Rectangle rect) {
		this.boundingRect = rect;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration.y = acceleration;
	}
	
	public float getAcceleration() {
		return this.acceleration.y;
	}
	
	public float getTerminal() {
		return this.terminal;
	}
	

}
