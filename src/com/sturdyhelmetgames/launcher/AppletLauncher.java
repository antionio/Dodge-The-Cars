package com.sturdyhelmetgames.launcher;

import com.badlogic.gdx.backends.lwjgl.LwjglApplet;
import com.sturdyhelmetgames.dodgethecars.DodgeTheCarsGame;

/**
 * Applet container for game.
 * 
 * @author Antti 6.7.2012
 * 
 */
public class AppletLauncher extends LwjglApplet {

	private static final long serialVersionUID = 1642320891549169406L;

	public AppletLauncher() {
		super(new DodgeTheCarsGame(), false);
	}

}
