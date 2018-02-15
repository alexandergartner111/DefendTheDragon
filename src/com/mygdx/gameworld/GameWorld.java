package com.mygdx.gameworld;

import java.util.Random;

import com.mygdx.gameobjects.HealthBar;
import com.mygdx.gameobjects.Powerup;
import com.mygdx.gameobjects.Projectile;
import com.mygdx.screens.GameScreen;
import com.badlogic.gdx.utils.TimeUtils;

public class GameWorld {

	// Game Objects
	private GameScreen gameScreen;
	private Projectile projectile;
	private HealthBar dragonHealth;
	private HealthBar enemyArmy;
	private Powerup powerup;

	// Game Loop-Related
	private int level;
	private boolean isAlive;
	private int midPointY;
	public long time;
	private TimeUtils timer;
	private float gameWidth;
	private int score;
	// corresponds to a number between
	private int curPowerup;
	private boolean killASoldier;
	private Random rng;

	public GameWorld(GameScreen gameScreen, int midPointY, float gameWidth) {

		this.gameScreen = gameScreen;
		this.midPointY = midPointY;
		this.gameWidth = gameWidth;

		rng = new Random();

		this.enemyArmy = new HealthBar(20);
		this.dragonHealth = new HealthBar(200);
		this.powerup = new Powerup(this, rng.nextInt((int) gameWidth - 10),
				-35, 14, 32, gameWidth, dragonHealth);
		this.projectile = new Projectile(this, 33, -22, 12, 24, gameWidth,
				dragonHealth, score);

		this.level = 1;
		this.curPowerup = 1;

		killASoldier = false;
		isAlive = true;

	}

	long startTime = System.currentTimeMillis();

	public void update(float delta) {
		long currentTime = System.currentTimeMillis() / 1000 - startTime / 1000;

				
		projectile.update(delta, midPointY);
		projectile.setTerminal(getTerminalSpeed());
		projectile.setAcceleration(getAccel());
		projectile.setForm(getForm());

		// Control the leveling up and increasing difficulty
		if (enemyArmy.getHealth() == 0) {
			levelUp();
			enemyArmy.setHealth(getHPbyLevel());
			enemyArmy.setSoldiers(enemyArmy.getHealth() / 10);
		}

		if (currentTime % 8 == 0 && powerup.wasItTapped() == false) {
			powerup.triggerPowerup(true);
		}
		powerup.update(delta, midPointY);

		// Will appropriate the dragon health given/taken by collected powerups.
		System.out.println(curPowerup);
		enactPowerup();

		// Check if trigger dragon-killing-soldier animation
		if (killASoldier == true) {
			gameScreen.pause();
			// use GameRenderer to trigger animation
		}

		if (isAlive) {
			// Clean up on game over
			// AssetLoader.dead.play(); PLAYS DEATH SOUND
			// isAlive = false;
		}
	}

	/*
	 * Methods that handle gameplay progression, including speeding up the
	 * arrows and deciding the arrows' strength
	 */

	public void enactPowerup() {
		if (curPowerup == 6) {
			dragonHealth.addHealth(25);
			curPowerup = powerup.getPrevForm();
		} else if (curPowerup == 7) {
			dragonHealth.addHealth(50);
			curPowerup = powerup.getPrevForm();
		} else if (curPowerup == 8) {
			dragonHealth.takeHealth(25);
			curPowerup = powerup.getPrevForm();
		} else if (curPowerup == 9) {
			dragonHealth.takeHealth(50);
			curPowerup = powerup.getPrevForm();
		}
		// if the black magic powerup, slowly take away health
		if (curPowerup == 5) {
			long currentTime = System.currentTimeMillis() / 100 - startTime / 100;
			if (currentTime % 70 == 0) {
				dragonHealth.takeHealth(1);
			}
		}
		// if the digital clock powerup, slowly slow down the arrows
		if (curPowerup == 2) {
			long currentTime = System.currentTimeMillis() - startTime;
			if (currentTime % 5000 == 0) {
				projectile.setAcceleration(projectile.getAcceleration() - 15);
				projectile.setTerminal(projectile.getTerminal() - 10);
			}
		}
		// if the ghost cat powerup, slowly add health
		if (curPowerup == 4) {
			long currentTime = System.currentTimeMillis() - startTime;
			if (currentTime % 5000 == 0) {
				dragonHealth.addHealth(1);
			}
		}
	}

	public char getForm() {
		if (this.level <= 2) {
			return 'a';
		} else if (this.level <= 4)
			return 'b';
		else {
			return 'c';
		}
	}

	public int getHPbyLevel() {
		if (this.level == 1) {
			return 20;
		} else if (this.level == 2) {
			return 30;
		} else if (this.level == 3) {
			return 40;
		} else if (this.level == 4) {
			return 50;
		} else if (this.level == 5) {
			return 60;
		} else if (this.level == 6) {
			return 70;
		} else if (this.level == 7) {
			return 100;
		} else {
			return 200;
		}
	}

	public float getTerminalSpeed() {
		if (this.level == 1) {
			return 90;
		} else if (this.level == 2) {
			return 120;
		} else if (this.level == 3) {
			return 150;
		} else if (this.level == 4) {
			return 180;
		} else if (this.level == 5) {
			return 210;
		} else if (this.level == 6) {
			return 240;
		} else if (this.level == 7) {
			return 270;
		} else {
			return 300 + this.level * 2;
		}
	}

	public float getAccel() {
		if (this.level == 1) {
			return 90;
		} else if (this.level == 2) {
			return 120;
		} else if (this.level == 3) {
			return 160;
		} else if (this.level == 4) {
			return 210;
		} else if (this.level == 5) {
			return 250;
		} else if (this.level == 6) {
			return 280;
		} else if (this.level == 7) {
			return 310;
		} else {
			return 340;
		}
	}

	/*
	 * Getters and setters. May not need all of these.
	 */

	public boolean getKillSoldier() {
		return killASoldier;
	}

	public GameScreen getScreen() {
		return gameScreen;
	}

	public void setKillSoldier(boolean b) {
		this.killASoldier = b;
	}

	public int getCurPowerup() {
		return curPowerup;
	}

	public void setCurPowerup(int p) {
		this.curPowerup = p;
	}

	public int getScore() {
		return this.score;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public HealthBar getDragonHealth() {
		return dragonHealth;
	}

	public Powerup getPowerup() {
		return this.powerup;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel() {
		this.level = level;
	}

	public TimeUtils getTime() {
		return timer;
	}

	public HealthBar getEnemyArmy() {
		return enemyArmy;
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public void levelUp() {
		level++;
	}

}
