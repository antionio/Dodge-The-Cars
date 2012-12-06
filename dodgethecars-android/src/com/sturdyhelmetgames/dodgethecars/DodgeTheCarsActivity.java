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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
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
	 * AdMob publisher code.
	 */
	private static final String ADMOB_PUBLISHER_CODE = "";

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
			} else if (!Swarm.isLoggedIn()) {
				Swarm.showLogin();
			} else {
				Swarm.showLeaderboards();
			}
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
		initializeGame();

		game.eventListenerList.add(new SwarmEventListener() {
			@Override
			public void eventOccurred(SwarmEvent evt) {
				if (evt != null) {
					System.out.println("Open leaderboards event occured lol.");
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

	/**
	 * Initializes the game instance and AdMob and sets the content view.
	 */
	private void initializeGame() {
		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Create the libgdx View
		game = new DodgeTheCarsGame();
		View gameView = initializeForView(game, false);

		// Add the libgdx view
		layout.addView(gameView);

		// Create and setup the AdMob view
		AdView adView = new AdView(this, AdSize.BANNER, ADMOB_PUBLISHER_CODE);
		AdRequest request = new AdRequest();
		// request.addTestDevice(AdRequest.TEST_EMULATOR);
		adView.loadAd(request);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(adView, adParams);

		// Hook it all up
		setContentView(layout);
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