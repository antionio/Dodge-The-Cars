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
package com.sturdyhelmetgames.dodgethecars.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

/**
 * A traffic light that is shown at the start of the game.
 * 
 * @author Antti 28.6.2012
 * 
 */
public class TrafficLight extends BasicEntity {

	/**
	 * Initial position.
	 */
	private static final Vector2 initialPos = new Vector2(35f, -6f);
	/**
	 * Middle position.
	 */
	private final Vector2 middlePos = new Vector2(-3f, -6f);
	/**
	 * End position.
	 */
	private final Vector2 endPos = new Vector2(-35f, -6f);
	/**
	 * Current position.
	 */
	private final Vector2 currentPos = new Vector2(initialPos);

	public TrafficLight() {
		super(initialPos.x, initialPos.y, 6f, 14f, 0f, 0f);
	}

	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);

		if (animationState < 3f) {
			currentPos.lerp(middlePos, 5f * fixedStep);
		} else {
			currentPos.lerp(endPos, 5f * fixedStep);
		}
		x = currentPos.x;
		y = currentPos.y;
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		if (animationState < 1.5f) {
			spriteBatch.draw(Art.trafficLightRed, x, y, width, height);
		} else if (animationState < 2.3f) {
			spriteBatch.draw(Art.trafficLightYellow, x, y, width, height);
		} else {
			spriteBatch.draw(Art.trafficLightGreen, x, y, width, height);
		}
	}

}
