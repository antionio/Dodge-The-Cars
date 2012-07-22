package com.sturdyhelmetgames.dodgethecars;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sturdyhelmetgames.dodgethecars.DodgeTheCarsGame;

/**
 * Main activity for Dodge The Cars.
 * 
 * @author Antti 25.6.2012
 * 
 */
public class DodgeTheCarsActivity extends AndroidApplication {

	/**
	 * Reference to the libgdx game instance.
	 */
	private DodgeTheCarsGame game;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		game = new DodgeTheCarsGame();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGL20 = false;
		initialize(game, config);
	}
}