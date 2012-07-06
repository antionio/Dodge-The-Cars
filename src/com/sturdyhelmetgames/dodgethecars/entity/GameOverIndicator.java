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
	private static final Vector2 initialPos = new Vector2(35f, -6f);
	/**
	 * Target position.
	 */
	private final Vector2 targetPos = new Vector2(-15f, -6f);
	/**
	 * Current position.
	 */
	private final Vector2 currentPos = new Vector2(initialPos);

	/**
	 * Constructs a {@link GameOverIndicator}.
	 */
	public GameOverIndicator() {
		super(initialPos.x, initialPos.y, 30f, 12f, 0f, 0f);
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
		spriteBatch.draw(Art.gameOver, x, y, width, height);
	}

}
