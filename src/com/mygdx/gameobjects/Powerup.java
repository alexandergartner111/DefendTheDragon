package com.mygdx.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.gameworld.GameWorld;

public class Powerup {

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
	private int form;
	private int prevForm;
	private int curIcon;
	private boolean triggered;
	private boolean wasTapped;
	private boolean wasCollected;

	private Rectangle boundingRect;
	public Rectangle iconBoundingRect;

	public Random rng;

	private HealthBar dragonHealth;
	private GameWorld world;

	/*
	 * All of the various options for powerups: 0. Back to original, starter
	 * powe -- WORST 1. Blue fire powerup  2. Digital clock powerup 
	 * 3. green lightening 4. Ghostly kitten face! Meows and everything. 5. Purple eye
	 * powerup -- BEST 6. Dragon Health + 25 7. Dragon Health + 50 8. Extra
	 * enemy soldier: Dragon health - 25.  9. Extra enemy assassin: Dragon - 50.
	 */

	public Powerup(GameWorld world, float x, float y, int width, int height, float gameWidth,
			HealthBar dragonHealth) {
		this.world = world;
		this.width = width;
		this.height = height;
		yCoord = y;
		this.gameWidth = (int) gameWidth;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0, 10);

		terminal = 10;

		boundingRect = new Rectangle();
		iconBoundingRect = new Rectangle();
		touchPosition = new Vector2(0, 0);
		rng = new Random();

		this.dmg = 1;
		this.form = rng.nextInt(10);
		this.prevForm = -1;
		this.curIcon = -1;
			
		this.dragonHealth = dragonHealth;

		triggered = false;
		wasTapped = false;
		wasCollected = false;
	}

	
	// When the powerup gets shot (the crate coming down)
	// the user then has to tap whatever is left, giving them the choice
	// the collect the powerup-health if they want it.
	// EXCEPTION: enemy troops pop out immediately
	public void powerupHit(int screenX, int screenY, HealthBar enemyHealth) {
					
		screenY -= 5; // to allow for motion delay
		touchPosition.set(screenX / 2.0f, screenY / 2.0f);

		if (boundingRect.contains(touchPosition) == true) {
			
			this.triggered = false;
			if (wasCollected == false) {
			this.wasTapped = true;
			curIcon = form; // for gameRenderer to draw it
			}
			 
//			// special exceptions: enemy soldiers
//			if (this.form == 9) {
//				dragonHealth.takeHealth(25);
//				triggered = false;
//				position.y = -35;
//				position.x = rng.nextInt(gameWidth - 10);
//
//			}
//			if (this.form == 10) {
//				dragonHealth.takeHealth(50);
//				triggered = false;
//				position.y = -35;
//				position.x = rng.nextInt(gameWidth - 10);
//			}
			
			// now test and see if the new icon was clicked 
			// or if it expired
			if (wasTapped == true) {
				// NEED DELAY HERE!!!!
				iconBoundingRect.setPosition(position.x, position.y+20);
				iconBoundingRect.setSize(14, 14);
			if (iconHit(touchPosition) == true) {
				triggered = false;
				wasCollected = true;
				wasTapped = false;
				// set the new powerup and remove the icon
				// then assign the next powerup a new form
				this.prevForm = world.getCurPowerup();
				world.setCurPowerup(form);
				position.y = -35;
				position.x = rng.nextInt(gameWidth - 10);
				//iconBoundingRect.setPosition(position.x, position.y+20);
				
				this.form = rng.nextInt(10);
				wasCollected = false;
			} }
			
			
		}
	}
	
	public boolean iconHit(Vector2 touchPosition) {
		return iconBoundingRect.contains(touchPosition);
	}

	// if it supposed to be on screen, do its movements
	public void update(float delta, int midPointY) {
		if (triggered == true) {
			velocity.add(acceleration.cpy().scl(delta));

			// Check terminal velocity allowed
			if (velocity.y > terminal) {
				velocity.y = terminal;
			}

			// powerup didn't get clicked in time
			// move it out of the way and stop its movements
			if (position.y >= midPointY + 25) {
				triggered = false;
				position.y = -35;
				position.x = rng.nextInt(gameWidth - 10);
			}

			position.add(velocity.cpy().scl(delta));

			boundingRect.setPosition(position.x, position.y);
			boundingRect.setSize(width, height);
			
			
			// do I need this?
			touchPosition.scl(delta);

		}
	}
	
	public boolean wasItCollected() {
		return wasCollected;
	}
	
	public void setCollected(boolean b) {
		this.wasCollected = b;
	}
	
	public boolean wasItTapped() {
		return wasTapped;
	}

	public void setTapped(boolean b) {
		this.wasTapped = b;
	}

	public void setForm(int i) {
		this.form = i;
	}

	public int getForm() {
		return this.form;
	}

	public Rectangle getIconRect() {
		return iconBoundingRect;
	}
	
	public void triggerPowerup(boolean t) {
		triggered = t;
	}

	public boolean isTriggered() {
		return this.triggered;
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

	public void setAcceleration(float acceleration) {
		this.acceleration.y = acceleration;
	}

	public int getPrevForm() {
		return prevForm;
	}
	
	public void setPrevForm(int f) {
		prevForm = f;
	}
	
	public int getIcon() {
		return curIcon;
	}
	
	}
	

