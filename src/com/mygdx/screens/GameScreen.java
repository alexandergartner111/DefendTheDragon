package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.MyGdxGame;
import com.mygdx.gamehelpers.InputHandler;
import com.mygdx.gameobjects.HealthBar;
import com.mygdx.gameworld.GameRenderer;
import com.mygdx.gameworld.GameWorld;

public class GameScreen implements Screen {

	private MyGdxGame game;
	private GameWorld world;
    private GameRenderer renderer;
    private float runTime = 0;

    /* Here is where a new class will enforce game logic
     * by passing parameters into world, renderer, and input processor
     */
    public GameScreen(MyGdxGame game) {
        Gdx.app.log("GameScreen", "Attached");
        this.game = game;
        
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);

        int midPointY = (int) (gameHeight / 2);
        
        world = new GameWorld(this, midPointY, gameWidth);
        renderer = new GameRenderer(world, (int)gameHeight, (int)gameWidth, midPointY); // initialize renderer
           
        // INPUT PROCESSOR CONSTRUCTOR -- MODIFY
        Gdx.input.setInputProcessor(new InputHandler(world, renderer));
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen", "resizing");
    }

    @Override
    public void show() {
        Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        Gdx.app.log("GameScreen", "hide called");
    }

    @Override
    public void pause() {
        Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }
}