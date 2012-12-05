/*
 * Copyright 2012 Antti Kolehmainen
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sturdyhelmetgames.dodgethecars.assets;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Entry point for game art.
 * 
 * @author Antti 19.6.2012
 * 
 */
public class Art {

	private Art() {
		// method hidden
	}

	private static final String FOLDER_IMAGES = "data" + File.separator
			+ "images" + File.separator;
	private static final String ASSET_TEXTURE_SPLASHSCREEN = FOLDER_IMAGES
			+ "sturdyhelmetgames.png";
	private static final String ASSET_TEXTURE_TITLESCREEN = FOLDER_IMAGES
			+ "title.png";
	private static final String ASSET_TEXTURE_BACKGROUND = FOLDER_IMAGES
			+ "background.png";
	private static final String ASSET_TEXTURE_CAR = FOLDER_IMAGES
			+ "cartextures.png";
	private static final String ASSET_TEXTURE_GAMETEXTURES = FOLDER_IMAGES
			+ "gametextures.png";
	private static final String ASSET_TEXTURE_CONTROLTEXTURES = FOLDER_IMAGES
			+ "onscreencontrols.png";

	private static final String FOLDER_FONTS = "data" + File.separator
			+ "fonts" + File.separator;
	private static final String ASSET_FONT_DEBUGFONT = FOLDER_FONTS
			+ "creativeblock15_black.fnt";
	private static final String ASSET_FONT_SCOREFONT = FOLDER_FONTS
			+ "creativeblock30_white.fnt";

	// textures
	public static Texture backgroundTexture;
	private static Texture splashScreenTexture;
	public static Texture titleScreenTexture;
	private static Texture gameTexture;
	private static Texture carTexture;
	private static Texture controlTexture;

	// texture regions and animations for drawing
	public static TextureRegion sturdyHelmetLogoTex;
	public static TextureRegion splashBackgroundTex;
	public static Animation squirrelWalkDownAnimation;
	public static TextureRegion squirrelStandTex;
	public static TextureRegion shadowTex;
	public static TextureRegion heartTex;
	public static TextureRegion heartBlackTex;
	public static TextureRegion pineconeBronzeTex;
	public static TextureRegion pineconeSilverTex;
	public static TextureRegion pineconeGoldTex;
	public static TextureRegion points500;
	public static TextureRegion points1000;
	public static TextureRegion points1500;

	public static Animation carRedDriveLeftAnimation;
	public static Animation carRedDriveRightAnimation;
	public static Animation carCyanDriveLeftAnimation;
	public static Animation carCyanDriveRightAnimation;
	public static Animation carGreenDriveLeftAnimation;
	public static Animation carGreenDriveRightAnimation;

	public static TextureRegion trafficLightRed;
	public static TextureRegion trafficLightYellow;
	public static TextureRegion trafficLightGreen;
	public static TextureRegion gameOverScreen;
	public static TextureRegion pauseScreen;

	// fonts
	public static BitmapFont debugFont;
	public static BitmapFont scoreFont;

	// skin
	public static Skin skin;
	public static TextureRegionDrawable controlBackgroundTex;
	public static TextureRegionDrawable controlKnobTex;

	/**
	 * Loads assets for game.
	 */
	public static void load() {
		// splash screen images
		splashScreenTexture = loadTexture(ASSET_TEXTURE_SPLASHSCREEN);
		titleScreenTexture = loadTexture(ASSET_TEXTURE_TITLESCREEN);
		sturdyHelmetLogoTex = new TextureRegion(splashScreenTexture);
		splashBackgroundTex = new TextureRegion(splashScreenTexture, 5, 5);

		// load textures
		backgroundTexture = loadTexture(ASSET_TEXTURE_BACKGROUND);
		gameTexture = loadTexture(ASSET_TEXTURE_GAMETEXTURES);
		carTexture = loadTexture(ASSET_TEXTURE_CAR);
		controlTexture = loadTexture(ASSET_TEXTURE_CONTROLTEXTURES);

		squirrelWalkDownAnimation = new Animation(0.2f, new TextureRegion(
				gameTexture, 86, 0, 85, 170), new TextureRegion(gameTexture,
				172, 0, 85, 170));
		squirrelStandTex = new TextureRegion(gameTexture, 85, 170);
		shadowTex = new TextureRegion(gameTexture, 172, 205, 40, 11);
		heartTex = new TextureRegion(gameTexture, 172, 171, 37, 33);
		heartBlackTex = new TextureRegion(gameTexture, 211, 171, 37, 33);
		pineconeBronzeTex = new TextureRegion(gameTexture, 0, 171, 54, 62);
		pineconeSilverTex = new TextureRegion(gameTexture, 57, 170, 54, 62);
		pineconeGoldTex = new TextureRegion(gameTexture, 115, 171, 54, 62);
		points500 = new TextureRegion(gameTexture, 256, 179, 70, 34);
		points1000 = new TextureRegion(gameTexture, 328, 179, 90, 34);
		points1500 = new TextureRegion(gameTexture, 419, 179, 90, 43);

		trafficLightRed = new TextureRegion(gameTexture, 259, 0, 84, 177);
		trafficLightYellow = new TextureRegion(gameTexture, 343, 0, 84, 177);
		trafficLightGreen = new TextureRegion(gameTexture, 427, 0, 84, 177);
		gameOverScreen = new TextureRegion(gameTexture, 21, 249, 231, 231);
		pauseScreen = new TextureRegion(gameTexture, 255, 249, 231, 231);

		final TextureRegion[][] carFrames = new TextureRegion(carTexture)
				.split(256, 174);
		final TextureRegion[][] carFramesFlipped = new TextureRegion(carTexture)
				.split(256, 174);
		// flip frames
		for (TextureRegion[] frames : carFramesFlipped) {
			for (TextureRegion frame : frames) {
				frame.flip(true, false);
			}
		}
		carRedDriveLeftAnimation = new Animation(0.1f, carFrames[0][0],
				carFrames[0][1], carFrames[1][0], carFrames[1][1],
				carFrames[0][2]);
		carRedDriveRightAnimation = new Animation(0.1f, carFramesFlipped[0][0],
				carFramesFlipped[0][1], carFramesFlipped[1][0],
				carFramesFlipped[1][1], carFramesFlipped[0][2]);

		carCyanDriveLeftAnimation = new Animation(0.1f, carFrames[2][1],
				carFrames[3][1], carFrames[2][2], carFrames[3][2],
				carFrames[2][3]);
		carCyanDriveRightAnimation = new Animation(0.1f,
				carFramesFlipped[2][1], carFramesFlipped[3][1],
				carFramesFlipped[2][2], carFramesFlipped[3][2],
				carFramesFlipped[2][3]);

		carGreenDriveLeftAnimation = new Animation(0.1f, carFrames[1][2],
				carFrames[0][3], carFrames[1][3], carFrames[2][0],
				carFrames[3][0]);
		carGreenDriveRightAnimation = new Animation(0.1f,
				carFramesFlipped[1][2], carFramesFlipped[0][3],
				carFramesFlipped[1][3], carFramesFlipped[2][0],
				carFramesFlipped[3][0]);

		// load fonts
		debugFont = new BitmapFont(Gdx.files.internal(ASSET_FONT_DEBUGFONT),
				false);
		scoreFont = new BitmapFont(Gdx.files.internal(ASSET_FONT_SCOREFONT),
				false);

		controlBackgroundTex = new TextureRegionDrawable(new TextureRegion(
				controlTexture, 176, 176));
		controlKnobTex = new TextureRegionDrawable(new TextureRegion(
				controlTexture, 179, 0, 77, 77));
		controlKnobTex.setMinWidth(100);
		controlKnobTex.setMinHeight(100);

		skin = new Skin();
		final TouchpadStyle touchpadStyle = new TouchpadStyle(
				controlBackgroundTex, controlKnobTex);
		skin.add("touchpad", touchpadStyle);
	}

	/**
	 * Disposes assets.
	 */
	public static void dispose() {
		// dispose textures
		splashScreenTexture.dispose();
		titleScreenTexture.dispose();
		backgroundTexture.dispose();
		carTexture.dispose();
		gameTexture.dispose();
		controlTexture.dispose();

		// dispose fonts
		scoreFont.dispose();

		// dispose skin
		skin.dispose();
	}

	/**
	 * Loads a texture and sets TextureFilter to Linear.
	 * 
	 * @param path
	 *            Path to texture.
	 * @return Texture that was loaded.
	 */
	protected static Texture loadTexture(String path) {
		Texture texture = new Texture(Gdx.files.internal(path));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;
	}

}
