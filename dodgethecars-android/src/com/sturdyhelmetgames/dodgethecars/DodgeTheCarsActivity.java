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

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sturdyhelmetgames.dodgethecars.events.SwarmEvent;
import com.sturdyhelmetgames.dodgethecars.events.SwarmEventListener;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActiveUser;
import com.swarmconnect.SwarmLeaderboard;
import com.swarmconnect.SwarmLeaderboard.GotLeaderboardCB;
import com.swarmconnect.delegates.SwarmLoginListener;

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

	// Need handler for callbacks to the UI thread
	private final Handler mHandler = new Handler();

	private final Runnable mUpdateResults = new Runnable() {
		@Override
		public void run() {
			if (!Swarm.isInitialized()) {
				Swarm.init(DodgeTheCarsActivity.this,
						SwarmConstants.App.APP_ID, SwarmConstants.App.APP_AUTH,
						mySwarmLoginListener);
			}
			Swarm.showLeaderboards();
		}
	};

	/**
	 * Swarm Global leaderboard.
	 */
	private static SwarmLeaderboard globalLeaderboard;

	GotLeaderboardCB globalLBCallback = new GotLeaderboardCB() {
		@Override
		public void gotLeaderboard(SwarmLeaderboard leaderboard) {
			if (leaderboard != null) {
				// Save the leaderboard for later use
				DodgeTheCarsActivity.globalLeaderboard = leaderboard;
			}
		}
	};

	private SwarmLoginListener mySwarmLoginListener = new SwarmLoginListener() {

		@Override
		public void loginCanceled() {
			// TODO Auto-generated method stub

		}

		@Override
		public void loginStarted() {
			// TODO Auto-generated method stub

		}

		@Override
		public void userLoggedIn(SwarmActiveUser arg0) {
			SwarmLeaderboard.getLeaderboardById(
					SwarmConstants.Leaderboard.GLOBAL_ID, globalLBCallback);
		}

		@Override
		public void userLoggedOut() {
			// TODO Auto-generated method stub

		}

	};

	/**
	 * Updates leaderboard with given score.
	 * 
	 * @param score
	 *            Player score.
	 */
	private void updateLeaderboard(int score) {
		if (Swarm.isLoggedIn()) {
			if (globalLeaderboard != null) {
				globalLeaderboard.submitScore(score);
			}
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		game = new DodgeTheCarsGame();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGL20 = false;
		initialize(game, config);

		game.eventListenerList.add(new SwarmEventListener() {
			@Override
			public void eventOccurred(SwarmEvent evt) {
				if (evt != null) {
					if (evt.type == SwarmEvent.EVENT_TYPE_OPEN_LEADERBOARD) {
						mHandler.post(mUpdateResults);
					} else if (evt.type == SwarmEvent.EVENT_TYPE_UPDATE_LEADERBOARD) {
						if (evt.eventValue != null) {
							Integer value = (Integer) evt.eventValue;
							updateLeaderboard(value.intValue());
						}
					}
				}
			}
		});

		// If the user has logged in at least once before, then
		// automatically login the user without showing the home screen.
		Swarm.setActive(this);
		if (Swarm.isEnabled()) {
			Swarm.init(this, SwarmConstants.App.APP_ID,
					SwarmConstants.App.APP_AUTH, mySwarmLoginListener);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Swarm.setActive(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		Swarm.setInactive(this);
	}
}