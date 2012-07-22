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
