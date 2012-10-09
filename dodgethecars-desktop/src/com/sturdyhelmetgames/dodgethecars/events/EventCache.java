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