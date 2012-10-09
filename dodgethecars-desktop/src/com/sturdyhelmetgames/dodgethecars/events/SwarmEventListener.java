package com.sturdyhelmetgames.dodgethecars.events;

import java.util.EventListener;

/**
 * {@link EventListener} based interface for listening events that are
 * dispatched to external/parent applications.
 * 
 * @author Antti
 * 
 */
public interface SwarmEventListener extends EventListener {

	/**
	 * Handles event occurences.
	 * 
	 * @param evt
	 *            Occured event.
	 */
	void eventOccurred(SwarmEvent evt);

}