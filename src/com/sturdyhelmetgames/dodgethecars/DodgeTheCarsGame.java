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
