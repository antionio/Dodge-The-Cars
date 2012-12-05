package com.sturdyhelmetgames.dodgethecars.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

public class PauseIndicator extends GameOverIndicator {

	@Override
	public void render(SpriteBatch spriteBatch, float delta) {
		spriteBatch.draw(Art.pauseScreen, x - width / 2, y - height / 2, width, height);
	}

}
