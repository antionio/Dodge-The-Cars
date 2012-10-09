package com.sturdyhelmetgames.dodgethecars.events;

import java.util.EventObject;

/**
 * An event to be dispatched to an external application.
 * 
 * @author Antti
 * 
 */
public class SwarmEvent extends EventObject {

	private static final long serialVersionUID = 7145766592451775967L;

	/**
	 * Event type for opening leaderboard.
	 */
	public static final int EVENT_TYPE_OPEN_LEADERBOARD = 0;
	/**
	 * Event type for updating leaderboard.
	 */
	public static final int EVENT_TYPE_UPDATE_LEADERBOARD = 1;

	/**
	 * Event type.
	 * 
	 * @see {@link #EVENT_TYPE_OPEN_LEADERBOARD}
	 *      {@link #EVENT_TYPE_UPDATE_LEADERBOARD}
	 */
	public int type;
	/**
	 * Event value e.g. leaderboard score. Should depend on {@link #type}.
	 */
	public Object eventValue;

	/**
	 * Constructs a {@link SwarmEvent} with type and value.
	 * 
	 * @param source
	 *            Source e.g. object where the {@link SwarmEvent} was created.
	 * @param type
	 *            Event type.
	 * @param eventValue
	 *            Event value e.g. leaderboard score. Should depend on
	 *            {@link #type}.
	 */
	public SwarmEvent(Object source, int type, Object eventValue) {
		super(source);
		this.type = type;
		this.eventValue = eventValue;
	}
}