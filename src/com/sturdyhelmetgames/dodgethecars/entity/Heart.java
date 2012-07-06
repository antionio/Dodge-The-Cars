package com.sturdyhelmetgames.dodgethecars.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

/**
 * A heart that replenishes one health.
 * 
 * @author Antti 29.6.2012
 * 
 */
public class Heart extends CollectableEntity {

	public Heart(float x, float y) {
		super(x, y, 2f, 2f, 2f, 1f);
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		super.render(spriteBatch, delta);

		// calculate scale
		final float scale = getScale();

		if (aliveTick < DYING_TICK_MAX || dyingTick < DYING_TICK_MAX) {
			spriteBatch.draw(Art.heartTex, x, y + zz, 0f, 0f, width, height, scale, scale, 0f);
		}
	}

}
