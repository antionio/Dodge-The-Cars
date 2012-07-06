package com.sturdyhelmetgames.dodgethecars.assets;

import java.io.File;

import com.badlogic.gdx.Gdx;

/**
 * Entry point for game sounds.
 * 
 * @author Antti 19.6.2012
 * 
 */
public class Sound {

	private Sound() {
		// method hidden
	}

	private static final String FOLDER_SOUNDS = "data" + File.separator + "sounds" + File.separator;
	private static final String ASSET_MUSIC_HAPPYGOLUCKY = FOLDER_SOUNDS + "happygolucky.mp3";
	private static final String ASSET_SOUND_SQUIRRELDIE = FOLDER_SOUNDS + "squirreldie.wav";
	private static final String ASSET_SOUND_SQUIRRELHIT = FOLDER_SOUNDS + "squirrelhit.wav";
	private static final String ASSET_SOUND_COLLECT = FOLDER_SOUNDS + "collect.wav";

	public static com.badlogic.gdx.audio.Music musicHappyGoLucky;
	public static com.badlogic.gdx.audio.Sound soundSquirrelDie;
	public static com.badlogic.gdx.audio.Sound soundSquirrelHit;
	public static com.badlogic.gdx.audio.Sound soundCollect;

	public static void load() {
		musicHappyGoLucky = loadMusic(ASSET_MUSIC_HAPPYGOLUCKY);
		musicHappyGoLucky.setVolume(0.5f);
		musicHappyGoLucky.setLooping(true);

		soundSquirrelDie = loadSound(ASSET_SOUND_SQUIRRELDIE);
		soundSquirrelHit = loadSound(ASSET_SOUND_SQUIRRELHIT);
		soundCollect = loadSound(ASSET_SOUND_COLLECT);
	}

	/**
	 * Disposes assets.
	 */
	public static void dispose() {
		musicHappyGoLucky.dispose();
		soundSquirrelDie.dispose();
		soundSquirrelHit.dispose();
		soundCollect.dispose();
	}

	/**
	 * Loads a music file.
	 * 
	 * @param name
	 * @return
	 */
	private static com.badlogic.gdx.audio.Music loadMusic(String name) {
		return Gdx.audio.newMusic(Gdx.files.internal(name));
	}

	/**
	 * Loads a sound file.
	 * 
	 * @param name
	 * @return
	 */
	private static com.badlogic.gdx.audio.Sound loadSound(String name) {
		return Gdx.audio.newSound(Gdx.files.internal(name));
	}
}
