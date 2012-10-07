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
		cfg.height = 480;
		cfg.resizable = false;

		new LwjglApplication(new DodgeTheCarsGame(), cfg);
	}
}
