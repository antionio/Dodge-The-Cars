package com.sturdyhelmetgames.dodgethecars.events;

import java.util.EventListener;

public interface SwarmEventListener extends EventListener {

	public void eventOccurred(SwarmEvent evt);

}