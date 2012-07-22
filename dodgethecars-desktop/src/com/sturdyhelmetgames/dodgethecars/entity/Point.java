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
 * Represents a point that bounces from a collected item.
 * 
 * @author Antti 29.6.2012
 * 
 */
public class Point extends BasicEntity {

	/**
	 * Point type for rendering different kinds of {@link PineCone}s.
	 * 
	 * @author Antti 29.6.2012
	 * 
	 */
	public enum PointType {
		POINT_500, POINT_1000, POINT_1500;
	}

	private PointType pointType;

	private static final float ALIVE_TIME_MAX = 1.5f;
	private float aliveTick;

	/**
	 * Constructs a {@link Point} with position and {@link PointType}.
	 * 
	 * @param x
	 * @param y
	 * @param pointType
	 */
	public Point(float x, float y, PointType pointType) {
		super(x, y, 3f, 2f, 3f, 1f);
		this.pointType = pointType;
	}

	@Override
	public void update(float fixedStep) {
		super.update(fixedStep);
		// tick alive time
		aliveTick += fixedStep;
	}

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		final float scale = getScale();

		if (pointType == PointType.POINT_500) {
			spriteBatch.draw(Art.points500, x, y, 0f, 0f, width - 0.5f, height, scale, scale, 0f);
		} else if (pointType == PointType.POINT_1000) {
			spriteBatch.draw(Art.points1000, x, y, 0f, 0f, width, height, scale, scale, 0f);
		} else if (pointType == PointType.POINT_1500) {
			spriteBatch.draw(Art.points1500, x, y, 0f, 0f, width, height, scale, scale, 0f);
		}

	}

	/**
	 * Checks if the {@link Point} is alive or not.
	 * 
	 * @return True if {@link Point} is alive, false otherwise.
	 */
	public boolean isAlive() {
		if (aliveTick >= ALIVE_TIME_MAX) {
			return false;
		}
		return true;
	}

}
