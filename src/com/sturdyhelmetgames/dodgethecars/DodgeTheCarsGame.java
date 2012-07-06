package com.sturdyhelmetgames.dodgethecars;

import com.badlogic.gdx.Game;
import com.sturdyhelmetgames.dodgethecars.assets.Art;
import com.sturdyhelmetgames.dodgethecars.assets.Sound;
import com.sturdyhelmetgames.dodgethecars.screen.SplashScreen;

/**
 * Main game class for the game.
 * 
 * @author Antti 18.6.2012
 * 
 */
public class DodgeTheCarsGame extends Game {

	public static boolean DEBUG = false;

	@Override
	public void create() {
		// load the assets and splash screen
		Art.load();
		Sound.load();
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		if (getScreen() != null) {
			getScreen().dispose();
		}
		Art.dispose();
		Sound.dispose();
	}

}
