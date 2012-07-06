package com.sturdyhelmetgames.dodgethecars.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

/**
 * A pine cone that can be collected for extra points.
 * 
 * @author Antti 30.6.2012
 * 
 */
public class PineCone extends CollectableEntity {

	public static final int PINECONE_COLOR_BRONZE = 0;
	public static final int PINECONE_COLOR_SILVER = 1;
	public static final int PINECONE_COLOR_GOLD = 2;

	public int color;

	public PineCone(float x, float y, int color) {
		super(x, y, 2f, 3f, 2f, 1f);
		this.color = color;
	}

	/**
	 * Resets the {@link PineCone} state.
	 * 
	 * @param x
	 * @param y
	 * @param color
	 */
	public void reset(float x, float y, int color) {
		reset(x, y);
		this.color = color;
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		super.render(spriteBatch, delta);

		// calculate scale
		final float scale = getScale();

		if (aliveTick < DYING_TICK_MAX || dyingTick < DYING_TICK_MAX) {
			if (PINECONE_COLOR_BRONZE == color) {
				spriteBatch.draw(Art.pineconeBronzeTex, x, y + zz, 0f, 0f, width, height, scale, scale, 0f);
			} else if (PINECONE_COLOR_SILVER == color) {
				spriteBatch.draw(Art.pineconeSilverTex, x, y + zz, 0f, 0f, width, height, scale, scale, 0f);
			} else if (PINECONE_COLOR_GOLD == color) {
				spriteBatch.draw(Art.pineconeGoldTex, x, y + zz, 0f, 0f, width, height, scale, scale, 0f);
			}
		}
	}

}
