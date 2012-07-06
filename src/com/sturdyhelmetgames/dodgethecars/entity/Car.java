package com.sturdyhelmetgames.dodgethecars.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

/**
 * A car that drives around the stage and might hit the player.
 * 
 * @author Antti 30.6.2012
 * 
 */
public class Car extends BasicEntity {

	/**
	 * Car color RED.
	 */
	private static final int CAR_COLOR_RED = 0;
	/**
	 * Car color CYAN.
	 */
	private static final int CAR_COLOR_CYAN = 1;
	/**
	 * Car color GREEN.
	 */
	private static final int CAR_COLOR_GREEN = 2;

	/**
	 * {@link Car}'s color.
	 */
	public int color;
	/**
	 * Car's maximum velocity.
	 */
	private float maxVelocity;

	/**
	 * Offset for drawing car.
	 */
	private static final float RENDER_X_OFFSET = 2f;

	/**
	 * Constructs a {@link Car} with position, direction, color and maximum velocity.
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @param color
	 * @param maxVelocity
	 */
	public Car(float x, float y, Direction direction, int color, float maxVelocity) {
		super(x, y, 10f, 10f, 8f, 2f);
		this.direction = direction;
		this.color = color;
		this.maxVelocity = maxVelocity;
	}

	/**
	 * Resets {@link Car}'s state.
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 * @param color
	 * @param maxVelocity
	 */
	public void reset(float x, float y, Direction direction, int color, float maxVelocity) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.color = color;
		this.maxVelocity = maxVelocity;

		animationState = 0f;
	}

	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);
		if (direction == Direction.LEFT) {
			acceleration.x = -ACCELERATION_MAX;
		} else if (direction == Direction.RIGHT) {
			acceleration.x = ACCELERATION_MAX;
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		super.render(spriteBatch, delta);
		final float scale = getScale();

		// render animation based on direction
		if (direction == Direction.LEFT) {
			Animation animation = null;
			if (color == CAR_COLOR_RED) {
				animation = Art.carRedDriveLeftAnimation;
			} else if (color == CAR_COLOR_CYAN) {
				animation = Art.carCyanDriveLeftAnimation;
			} else if (color == CAR_COLOR_GREEN) {
				animation = Art.carGreenDriveLeftAnimation;
			}
			spriteBatch
					.draw(animation.getKeyFrame(animationState, true), x, y, 0f, 0f, width, height, scale, scale, 0f);
		} else if (direction == Direction.RIGHT) {
			// x offset is calculated because of car's shadow behaves slightly differently
			final float x = this.x - RENDER_X_OFFSET;
			Animation animation = null;
			if (color == CAR_COLOR_RED) {
				animation = Art.carRedDriveRightAnimation;
			} else if (color == CAR_COLOR_CYAN) {
				animation = Art.carCyanDriveRightAnimation;
			} else if (color == CAR_COLOR_GREEN) {
				animation = Art.carGreenDriveRightAnimation;
			}
			spriteBatch
					.draw(animation.getKeyFrame(animationState, true), x, y, 0f, 0f, width, height, scale, scale, 0f);
		}
	}

	@Override
	public float getMaxVelocity() {
		return maxVelocity;
	}

}
