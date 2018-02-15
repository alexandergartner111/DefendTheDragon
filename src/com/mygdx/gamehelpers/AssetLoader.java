package com.mygdx.gamehelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static Texture texture;
	public static Texture castleTexture;
	public static Texture animationsSheet1;
	public static Texture powerupIcons;
	public static Texture animationsSheet2;
	public static TextureRegion arrow;
	public static TextureRegion missile;
	public static TextureRegion bomb;
	public static TextureRegion dragonHealthIcon25, dragonHealthIcon50;
	public static TextureRegion darkEyeIcon, ghostCatIcon, greenLightIcon,
			explosionIcon, digitalIcon;
	public static TextureRegion attack01f01, attack01f02, attack01f03,
			attack01f04, attack01f05, attack01f06;
	public static TextureRegion attack02f01, attack02f02, attack02f03,
			attack02f04, attack02f05, attack02f06, attack02f07, blueFlamesIcon;
	public static TextureRegion digitalf01, digitalf02, digitalf03, digitalf04,
			digitalf05;
	public static TextureRegion eyef01, eyef02, eyef03, eyef04, eyef05, eyef06;
	public static TextureRegion lightningf01, lightningf02, lightningf03,
			lightningf04, lightningf05, lightningf06;
	public static TextureRegion catf01, catf02, catf03, catf04, catf05;
	public static TextureRegion dragonf01;
	public static TextureRegion castle, cloud;
	public static TextureRegion puBalloon;
	public static Animation attack01, attack02, eyeAnimation, catAnimation, digitalAnimation, lightningAnimation;


	public static BitmapFont font;

	// Need to clean this up
	public static void load() {

		texture = new Texture(
				Gdx.files.internal("fullTextureMapTransparency.png"));
		texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		castleTexture = new Texture(Gdx.files.internal("castle wall.png"));
		castleTexture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		animationsSheet1 = new Texture(
				Gdx.files.internal("animations sprite sheet 1.png"));
		animationsSheet1
				.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		powerupIcons = new Texture(Gdx.files.internal("power up icons.png"));
		powerupIcons.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		animationsSheet2 = new Texture(
				Gdx.files.internal("animations sprite sheet 2.png"));
		animationsSheet2
				.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		// The three projectile graphics & powerup balloon
		arrow = new TextureRegion(texture, 0, 0, 12, 24);
		arrow.flip(false, true);
		missile = new TextureRegion(texture, 166, 0, 12, 24);
		missile.flip(false, true);
		bomb = new TextureRegion(texture, 150, 0, 12, 24);
		bomb.flip(false, true);
		puBalloon = new TextureRegion(texture, 181, 0, 13, 32);
		puBalloon.flip(false, true);

		// Dragon graphics
		dragonf01 = new TextureRegion(texture, 0, 25, 137, 55);
		dragonf01.flip(false, true);

		// Dragon health powerup icons
		dragonHealthIcon25 = new TextureRegion(powerupIcons, 0, 0, 14, 14);
		dragonHealthIcon50 = new TextureRegion(powerupIcons, 15, 0, 14, 14);
		dragonHealthIcon25.flip(false, true);
		dragonHealthIcon50.flip(false, true);
		greenLightIcon = new TextureRegion(powerupIcons, 0, 15, 14, 14);
		greenLightIcon.flip(false, true);
		darkEyeIcon = new TextureRegion(powerupIcons, 15, 15, 14, 14);
		darkEyeIcon.flip(false, true);
		ghostCatIcon = new TextureRegion(powerupIcons, 0, 30, 14, 14);
		ghostCatIcon.flip(false, true);
		digitalIcon = new TextureRegion(powerupIcons, 15, 30, 14, 14);
		digitalIcon.flip(false, true);
		explosionIcon = new TextureRegion(powerupIcons, 0, 45, 14, 14);
		explosionIcon.flip(false, true);

		// Explosion graphic and create its animation
		attack01f01 = new TextureRegion(texture, 24, 3, 18, 18);
		attack01f02 = new TextureRegion(texture, 44, 3, 18, 18);
		attack01f03 = new TextureRegion(texture, 64, 3, 18, 18);
		attack01f04 = new TextureRegion(texture, 84, 3, 18, 18);
		attack01f05 = new TextureRegion(texture, 104, 3, 18, 18);
		attack01f06 = new TextureRegion(texture, 124, 3, 18, 18);
		TextureRegion[] attack01Frames = { attack01f01, attack01f02,
				attack01f03, attack01f04, attack01f05, attack01f06 };
		attack01 = new Animation(0.06f, attack01Frames);
		attack01.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

		// Blue fire graphic and create its animation
		blueFlamesIcon = new TextureRegion(animationsSheet1, 1, 1, 14, 14);
		attack02f01 = new TextureRegion(animationsSheet1, 16, 0, 4, 6);
		attack02f02 = new TextureRegion(animationsSheet1, 21, 0, 7, 8);
		attack02f03 = new TextureRegion(animationsSheet1, 29, 0, 11, 11);
		attack02f04 = new TextureRegion(animationsSheet1, 41, 0, 17, 18);
		attack02f05 = new TextureRegion(animationsSheet1, 58, 0, 23, 23);
		attack02f06 = new TextureRegion(animationsSheet1, 82, 0, 27, 28);
		attack02f07 = new TextureRegion(animationsSheet1, 111, 0, 32, 28);
		blueFlamesIcon.flip(false, true);
		attack02f01.flip(false, true);
		attack02f02.flip(false, true);
		attack02f03.flip(false, true);
		attack02f04.flip(false, true);
		attack02f05.flip(false, true);
		attack02f06.flip(false, true);
		attack02f07.flip(false, true);
		TextureRegion[] attack02Frames = { attack02f01, attack02f02,
				attack02f03, attack02f04, attack02f05, attack02f06, attack02f07 };
		attack02 = new Animation(0.1f, attack02Frames);
		attack02.setPlayMode(Animation.PlayMode.LOOP);
		
		// eye attack and animation
		eyef01 = new TextureRegion(animationsSheet1, 0, 56, 32, 28);
		eyef02 = new TextureRegion(animationsSheet1, 33, 56, 32, 28);
		eyef03 = new TextureRegion(animationsSheet1, 66, 56, 32, 28);
		eyef04 = new TextureRegion(animationsSheet1, 99, 56, 32, 28);
		eyef05 = new TextureRegion(animationsSheet1, 132, 56, 32, 28);
		eyef06 = new TextureRegion(animationsSheet1, 166, 40, 52, 47);
		eyef01.flip(false, true);
		eyef02.flip(false, true);
		eyef03.flip(false, true);
		eyef04.flip(false, true);
		eyef05.flip(false, true);
		eyef06.flip(false, true);
		TextureRegion[] eyeFrames = { eyef01, eyef02, eyef03, eyef04, eyef05, eyef06 };
		eyeAnimation = new Animation(0.1f, eyeFrames);
		eyeAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		// lightning attack and animation
		lightningf01 = new TextureRegion(animationsSheet2, 0, 0, 36, 34);
		lightningf02 = new TextureRegion(animationsSheet2, 37, 0, 36, 34);
		lightningf03 = new TextureRegion(animationsSheet2, 74, 0, 36, 34);
		lightningf04 = new TextureRegion(animationsSheet2, 111, 0, 36, 34);
		lightningf05 = new TextureRegion(animationsSheet2, 148, 0, 36, 34);
		lightningf06 = new TextureRegion(animationsSheet2, 184, 0, 36, 34);
		lightningf01.flip(false, true);
		lightningf02.flip(false, true);
		lightningf03.flip(false, true);
		lightningf04.flip(false, true);
		lightningf05.flip(false, true);
		lightningf06.flip(false, true);
		TextureRegion[] lightningFrames = { lightningf01, lightningf02, lightningf03, lightningf04, lightningf05, lightningf06 };
		lightningAnimation = new Animation(0.1f, lightningFrames);
		lightningAnimation.setPlayMode(Animation.PlayMode.LOOP);		
		
		// cat attack and animation
		catf01 = new TextureRegion(animationsSheet2, 0, 35, 39, 28);
		catf02 = new TextureRegion(animationsSheet2, 39, 35, 45, 36);
		catf03 = new TextureRegion(animationsSheet2, 84, 36, 43, 36);
		catf04 = new TextureRegion(animationsSheet2, 128, 36, 40, 36);
		catf05 = new TextureRegion(animationsSheet2, 168, 36, 43, 36);
		catf01.flip(false, true);
		catf02.flip(false, true);
		catf03.flip(false, true);
		catf04.flip(false, true);
		catf05.flip(false, true);
		TextureRegion[] catFrames = { catf01, catf02, catf03, catf04, catf05 };
		catAnimation = new Animation(0.15f, catFrames);
		catAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		// digital clock attack and animation
		digitalf01 = new TextureRegion(animationsSheet1, 0, 29, 26, 26);
		digitalf02 = new TextureRegion(animationsSheet1, 27, 29, 26, 26);
		digitalf03 = new TextureRegion(animationsSheet1, 53, 29, 26, 26);
		digitalf04 = new TextureRegion(animationsSheet1, 79, 29, 26, 26);
		digitalf05 = new TextureRegion(animationsSheet1, 145, 1, 46, 34);
		digitalf01.flip(false, true);
		digitalf02.flip(false, true);
		digitalf03.flip(false, true);
		digitalf04.flip(false, true);
		digitalf05.flip(false, true);
		TextureRegion[] digitalFrames = { digitalf01, digitalf02, digitalf03, digitalf04, digitalf05 };
		digitalAnimation = new Animation(0.2f, digitalFrames);
		digitalAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		// font
		font = new BitmapFont(Gdx.files.internal("fontattempt2.fnt"));
		font.setScale(.2f, -.2f);

		
		// Sounds for each powerup and for each projectile hitting dragon
		// Maybe a "moooo" when dragon hit? 

		// Castle background & cloud graphic
		castle = new TextureRegion(castleTexture, 0, 0, 137, 260);
		castle.flip(false, true);
		cloud = new TextureRegion(powerupIcons, 0, 62, 100, 44);
		cloud.flip(false, true);
	}

	public static void dispose() {
		// We must dispose of the texture when we are finished.
		texture.dispose();
		castleTexture.dispose();
		animationsSheet1.dispose();
		animationsSheet2.dispose();
		powerupIcons.dispose();
		font.dispose();
	}

}
