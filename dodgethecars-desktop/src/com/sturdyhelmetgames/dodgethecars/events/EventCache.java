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
package com.sturdyhelmetgames.dodgethecars.events;

/**
 * Contains static references to various events that are dispatched to an
 * external application.
 * 
 * @author Antti
 * 
 */
public final class EventCache {
	public static SwarmEvent openLeaderboard;
	public static SwarmEvent updateLeaderboard;

	private EventCache() {
		// method hidden
	}

	public static final void load(Object loader) {
		openLeaderboard = new SwarmEvent(loader,
				SwarmEvent.EVENT_TYPE_OPEN_LEADERBOARD, null);
		updateLeaderboard = new SwarmEvent(loader,
				SwarmEvent.EVENT_TYPE_UPDATE_LEADERBOARD, null);
	}

}