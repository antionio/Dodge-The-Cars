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

import static com.sturdyhelmetgames.dodgethecars.screen.GameScreen.LEVEL_BOUNDARY_X_LEFT;
import static com.sturdyhelmetgames.dodgethecars.screen.GameScreen.LEVEL_BOUNDARY_X_RIGHT;
import static com.sturdyhelmetgames.dodgethecars.screen.GameScreen.LEVEL_BOUNDARY_Y_BOTTOM;
import static com.sturdyhelmetgames.dodgethecars.screen.GameScreen.LEVEL_BOUNDARY_Y_TOP;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

/**
 * Squirrel is the player character.
 * 
 * @author Antti 30.6.2012
 * 
 */
public class Squirrel extends BasicEntity {

	/**
	 * Maximum health.
	 */
	public static final int HEALTH_MAX = 3;
	/**
	 * Current health.
	 */
	public int health = HEALTH_MAX;
	/**
	 * Current score.
	 */
	public long score = 0;

	/**
	 * Maximum velocity.
	 */
	public static final float VELOCITY_MAX = 17f;
	/**
	 * Maximum damaged time.
	 */
	public static final float SQUIRREL_DAMAGED_TIME_MAX = 3f;
	/**
	 * Maximum damaged tick.
	 */
	public static final float SQUIRREL_DAMAGED_TICK_MAX = 0.05f;
	/**
	 * Tells if the {@link Squirrel} is damaged or not.
	 */
	public boolean isDamaged = false;
	/**
	 * Time of being damaged.
	 */
	public float damagedTime = 0f;
	/**
	 * Damaged tick.
	 */
	public float damagedTick = 0f;

	/**
	 * Constructs a new {@link Squirrel}.
	 */
	public Squirrel() {
		super(-1.5f, -4f, 3f, 8f, 3f, 2f);
	}

	@Override
	protected void tryMove() {
		// do not let squirrel move outside level boundaries
		final float newX = x + velocity.x;
		final float newY = y + velocity.y;

		if (newX < LEVEL_BOUNDARY_X_LEFT) {
			x = LEVEL_BOUNDARY_X_LEFT;
		} else if (newX > LEVEL_BOUNDARY_X_RIGHT) {
			x = LEVEL_BOUNDARY_X_RIGHT;
		} else {
			x = newX;
		}

		if (newY < LEVEL_BOUNDARY_Y_BOTTOM) {
			y = LEVEL_BOUNDARY_Y_BOTTOM;
		} else if (newY > LEVEL_BOUNDARY_Y_TOP) {
			y = LEVEL_BOUNDARY_Y_TOP;
		} else {
			y = newY;
		}

	}

	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);
		if (isNotMoving()) {
			animationState -= fixedStep;
		}

		// update damage tick stuff
		if (isDamaged) {
			if (damagedTime <= 0f) {
				damagedTime = 0f;
				damagedTick = 0f;
				isDamaged = false;
			} else {
				damagedTime -= fixedStep;
				damagedTick += fixedStep;
				if (damagedTick > SQUIRREL_DAMAGED_TICK_MAX) {
					damagedTick = 0f;
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		// don't render shadow if dead
		if (isAlive()) {
			super.render(spriteBatch, delta);
		}

		// calculate scale
		final float scale = getScale();

		if (damagedTick <= 0f) {
			if (!isAlive()) {
				// if squirrel is dead, draw it flattened
				spriteBatch.draw(Art.squirrelStandTex, x, y, 0f, 0f, width * 2, height / 2, scale, scale, 0f);
			} else if (isNotMoving()) {
				// if squirrel is not moving, draw it standing
				spriteBatch.draw(Art.squirrelStandTex, x, y, 0f, 0f, width, height, scale, scale, 0f);
			} else {
				// if squirrel is moving, draw walking animation
				spriteBatch.draw(Art.squirrelWalkDownAnimation.getKeyFrame(animationState, true), x, y, 0f, 0f, width,
						height, scale, scale, 0f);
			}
		}

	}

	/**
	 * Checks if the entity is alive or not.
	 * 
	 * @return True if the entity is alive, false otherwise.
	 */
	public boolean isAlive() {
		if (health > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Reduce squirrel health by one and if not dead, start blinking.
	 * 
	 * @returns True if squirrel died, false otherwise.s
	 */
	public boolean takeDamage() {
		if (isAlive() && !isDamaged) {
			health--;
			if (isAlive()) {
				// if did not die, start blinking
				isDamaged = true;
				damagedTime = SQUIRREL_DAMAGED_TIME_MAX;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * Replenish squirrel health by one.
	 */
	public void replenishHealth() {
		if (isAlive() && health < HEALTH_MAX) {
			health++;
		}
	}

	@Override
	public float getMaxVelocity() {
		return VELOCITY_MAX;
	}

}
