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
 * An indicator that appears at the end of the game.
 * 
 * @author Antti 30.6.2012
 * 
 */
public class GameOverIndicator extends BasicEntity {

	/**
	 * Initial position.
	 */
	protected static final Vector2 initialPos = new Vector2(0f, -30f);
	/**
	 * Target position.
	 */
	protected final Vector2 targetPos = new Vector2(0f, 0f);
	/**
	 * Current position.
	 */
	protected final Vector2 currentPos = new Vector2(initialPos);

	/**
	 * Constructs a {@link GameOverIndicator}.
	 */
	public GameOverIndicator() {
		super(initialPos.x, initialPos.y, 20f, 20f, 0f, 0f);
	}

	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);

		// move the entity to the target position using linear interpolation
		currentPos.lerp(targetPos, 5f * fixedStep);
		x = currentPos.x;
		y = currentPos.y;
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		spriteBatch.draw(Art.gameOverScreen, x - width / 2, y - height / 2,
				width, height);
	}

	/**
	 * Resets indicator state.
	 */
	public void reset() {
		currentPos.set(initialPos);
	}

}
