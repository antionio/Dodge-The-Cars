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

/**
 * Represents a collectable entity that drops to the screen from above.
 * 
 * @author Antti 30.6.2012
 * 
 */
public abstract class CollectableEntity extends BasicEntity {

	/**
	 * Tells if the {@link CollectableEntity} is collected or not.
	 */
	public boolean collected = false;

	protected static final float ALIVE_TIME_MAX = 5f;
	protected static final float DYING_TIME_MIN = 3.5f;
	protected static final float DYING_TICK_MAX = 0.05f;
	protected float aliveTick;
	protected float dyingTick;

	/**
	 * Constructs a {@link CollectableEntity} with position, size and hitbox size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param hitBoxWidth
	 * @param hitBoxHeight
	 */
	public CollectableEntity(float x, float y, float width, float height, float hitBoxWidth, float hitBoxHeight) {
		super(x, y, width, height, hitBoxWidth, hitBoxHeight);
		zz = 30f;
		za = 0f;
	}

	/**
	 * Resets the {@link CollectableEntity} state.
	 * 
	 * @param x
	 * @param y
	 */
	protected void reset(float x, float y) {
		this.x = x;
		this.y = y;
		aliveTick = 0f;
		dyingTick = 0f;
		animationState = 0f;
		zz = 30f;
		za = 0f;
	}

	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);

		// bounce the entity
		zz += za;
		if (zz < 0f) {
			zz = 0f;
			za *= -0.3f;
		}
		za -= 0.2f;

		// tick dying
		if (dyingTick > DYING_TICK_MAX) {
			dyingTick = 0f;
		}

		// tick alive & dying times
		aliveTick += fixedStep;
		if (aliveTick >= DYING_TIME_MIN) {
			dyingTick += fixedStep;
		}
	}

	/**
	 * Checks if the {@link CollectableEntity} is alive or not.
	 * 
	 * @return True if {@link CollectableEntity} is alive, false otherwise.
	 */
	public boolean isAlive() {
		if (aliveTick >= ALIVE_TIME_MAX) {
			return false;
		}
		return true;
	}

}
