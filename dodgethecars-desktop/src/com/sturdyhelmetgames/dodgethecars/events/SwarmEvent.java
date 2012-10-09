package com.sturdyhelmetgames.dodgethecars.events;

import java.util.EventObject;

public class SwarmEvent extends EventObject {

	private static final long serialVersionUID = 7145766592451775967L;
	public static final int EVENT_TYPE_OPEN_LEADERBOARD = 0;
	public static final int EVENT_TYPE_UPDATE_LEADERBOARD = 1;

	public int type;
	public Object eventValue;

	public SwarmEvent(Object source, int type, Object eventValue) {
		super(source);
		this.type = type;
		this.eventValue = eventValue;
	}
}