package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.gamehelpers.AssetLoader;
import com.mygdx.screens.GameScreen;

/*
 * GOALS:
 * 1. Implement logic of tapping and destroying arrows
 * 2. Figure out how to duplicate arrows
 * 3. From here, develop game loop:
 * 	a. difficulty and how it increases
 * 	b. how graphics change over time
 * 	c. how game is played, what is "winning"
 * 	d. modify speed/multitude of arrows as game progresses
 * 4. Dragon animation and collision, health bar graphics
 * 5. Create graphics for other projectiles, power-ups, etc.
 * 6. Implementing additions like power-ups
 * 7. Sound
 */

/*
 * IDEAS:
 * -- Game rules: keep going till die? keep going till
 * 	game ends and dragon is alive-- then high score == dragon's
 *  remaining health? 
 * -- Different power-ups have different screen-tap animations?
 * 
 */

public class MyGdxGame extends Game {
	
	@Override
	public void create () {
        Gdx.app.log("ZBGame", "created");
        AssetLoader.load();
        
        setScreen(new GameScreen(this));

	}

    @Override
    public void dispose() {
        super.dispose();
       // AssetLoader.dispose();
    }

}
	
	
	
	
	
