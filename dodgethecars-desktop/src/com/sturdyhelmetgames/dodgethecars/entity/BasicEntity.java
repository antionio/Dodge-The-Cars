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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

/**
 * A basic moving entity in the game.
 * 
 * @author Antti 19.6.2012
 * 
 */
public abstract class BasicEntity implements Comparable<BasicEntity> {

	/**
	 * Represents a direction the {@link BasicEntity} might be facing.
	 * 
	 * @author Antti 30.6.2012
	 * 
	 */
	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	/**
	 * State for drawing animation.
	 */
	public float animationState = 0f;
	public float width, height, hitBoxWidth, hitBoxHeight, x, y;
	/**
	 * Variable for controlling the drawing of "droppable" items.
	 */
	protected float zz, za;
	/**
	 * The entity's direction.
	 */
	public Direction direction = Direction.DOWN;

	/**
	 * Maximum acceleration.
	 */
	public static final float ACCELERATION_MAX = 25f;
	/**
	 * Maximum velocity.
	 */
	public static final float VELOCITY_MAX = 13f;
	/**
	 * Minimum moving velocity.
	 */
	private static final float MOVING_VELOCITY_MIN = 0.01f;
	/**
	 * Damping for velocity.
	 */
	public static final float DAMP = 0.7f;
	/**
	 * Default entity scale.
	 */
	private static final float SCALE_AMOUNT_DEFAULT = 1f;
	/**
	 * Entity scaling amoung variable.
	 */
	private static final float SCALE_AMOUNT_VERTICAL = 80f;

	/**
	 * Entity's current acceleration.
	 */
	public final Vector2 acceleration = new Vector2();
	/**
	 * Entity's current velocity.
	 */
	public final Vector2 velocity = new Vector2();
	/**
	 * Entity's hitbox.
	 */
	protected final Rectangle hitBox = new Rectangle();

	/**
	 * Constructs a {@link BasicEntity} with position, size and hitbox size.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param hitBoxWidth
	 * @param hitBoxHeight
	 */
	public BasicEntity(float x, float y, float width, float height,
			float hitBoxWidth, float hitBoxHeight) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.hitBoxWidth = hitBoxWidth;
		this.hitBoxHeight = hitBoxHeight;
		this.hitBox.setWidth(hitBoxWidth);
		this.hitBox.setHeight(hitBoxHeight);
	}

	/**
	 * Updates the entity state.
	 * 
	 * @param fixedStep
	 */
	public void update(float fixedStep) {
		animationState += fixedStep;
		tryMove();
		velocity.add(acceleration.x, acceleration.y);
		if (velocity.x > getMaxVelocity()) {
			velocity.x = getMaxVelocity();
		}
		if (velocity.x < -getMaxVelocity()) {
			velocity.x = -getMaxVelocity();
		}
		if (velocity.y > getMaxVelocity()) {
			velocity.y = getMaxVelocity();
		}
		if (velocity.y < -getMaxVelocity()) {
			velocity.y = -getMaxVelocity();
		}
		if (acceleration.x != 0) {
			acceleration.x *= DAMP;
			velocity.x *= DAMP;
		}
		if (acceleration.y != 0) {
			acceleration.y *= DAMP;
			velocity.y *= DAMP;
		}
		velocity.mul(fixedStep);
	}

	/**
	 * Renders the entity. The default is to draw the entity's shadow. The
	 * shadow is drawn to indicate the entity's hitbox.
	 * 
	 * @param spriteBatch
	 * @param delta
	 */
	public void render(SpriteBatch spriteBatch, float delta) {
		final float shadowZZ = zz * 0.1f;
		final float shadowOffsetX = shadowZZ / 2;
		// draw shadow
		spriteBatch.draw(Art.shadowTex, x + shadowOffsetX, y - 0.2f, 0f, 01f,
				hitBoxWidth - shadowZZ, hitBoxHeight - shadowOffsetX,
				getScale(), getScale(), 0f);
	}

	/**
	 * Try to move the entity according to velocity.
	 */
	protected void tryMove() {
		x += velocity.x;
		y += velocity.y;
	}

	/**
	 * Checks if given entity collides with this one.
	 * 
	 * @param entity
	 * @return True if hit, false otherwise.
	 */
	public boolean hit(BasicEntity entity) {
		if (getHitbox().overlaps(entity.getHitbox())) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the entity is actually moving or not.
	 * 
	 * @return True if not moving, false if is.
	 */
	public boolean isNotMoving() {
		if (velocity.x > -MOVING_VELOCITY_MIN
				&& velocity.x < MOVING_VELOCITY_MIN
				&& velocity.y > -MOVING_VELOCITY_MIN
				&& velocity.y < MOVING_VELOCITY_MIN) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(BasicEntity o) {
		if (y < o.y) {
			return 1;
		} else if (y > o.y) {
			return -1;
		}
		return 0;
	}

	/**
	 * Returns a hitbox for this entity.
	 * 
	 * @return Entity hitbox.
	 */
	public Rectangle getHitbox() {
		hitBox.setX(x);
		hitBox.setY(y);
		return hitBox;
	}

	/**
	 * Returns the entity's maximum velocity.
	 * 
	 * @return Maximum velocity.
	 */
	public float getMaxVelocity() {
		return VELOCITY_MAX;
	}

	/**
	 * Returns the current scale for the entity. Scale calculation is based on
	 * the position of the {@link BasicEntity} on the y-axis.
	 * 
	 * @return Scale
	 */
	protected float getScale() {
		return SCALE_AMOUNT_DEFAULT - y / SCALE_AMOUNT_VERTICAL;
	}

}
