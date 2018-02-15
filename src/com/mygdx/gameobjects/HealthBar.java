package com.mygdx.gameobjects;



public class HealthBar {
	private int health;
	private int soldierAmt;
	
	public HealthBar(int health) {
		this.health = health;
		this.soldierAmt = health/10;
	}
	

	public int getHealth() {
		return health;
	}

	public void addHealth(int health) {
		this.health += health;
	}
	
	public void takeHealth(int health) {
		this.health -= health;
	}

	public int getSoldierAmt() {
		return soldierAmt;
	}

	public void killSoldiers(int soldierAmt) {
		this.soldierAmt -= soldierAmt;
	}
	
	public void setSoldiers(int soldierAmt) {
		this.soldierAmt = soldierAmt;
	}
	
	public void addSoldier() {
		this.health += 10;
		this.soldierAmt += 1;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

}
