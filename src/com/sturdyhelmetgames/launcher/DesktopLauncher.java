package com.sturdyhelmetgames.launcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sturdyhelmetgames.dodgethecars.DodgeTheCarsGame;

/**
 * Main class for launching the game.
 * 
 * @author Antti 18.6.2012
 * 
 */
public class DesktopLauncher {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Dodge the Cars";
		cfg.useGL20 = false;
		cfg.vSyncEnabled = true;
		cfg.width = 800;
		cfg.height = 400;

		new LwjglApplication(new DodgeTheCarsGame(), cfg);
	}
}
