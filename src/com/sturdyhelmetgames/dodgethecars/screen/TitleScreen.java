package com.sturdyhelmetgames.dodgethecars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.sturdyhelmetgames.dodgethecars.DodgeTheCarsGame;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

/**
 * Title screen for game instructions.
 * 
 * @author Antti 26.6.2012
 * 
 */
public class TitleScreen extends TransitionScreen {

	private boolean keyPressed = false;

	public TitleScreen(DodgeTheCarsGame game) {
		super(game);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	protected void updateScreen(float fixedStep) {
		super.updateScreen(fixedStep);

		if (keyPressed && screenTime > 4.99f) {
			game.setScreen(new GameScreen(game));
		}
	}

	@Override
	public void renderScreen(float delta) {
		spriteBatch.getProjectionMatrix().set(camera.combined);
		spriteBatch.begin();
		// draw title screen
		spriteBatch.draw(Art.titleScreenTexture, -24f, -39f, 48f, 55f);
		spriteBatch.end();

		renderFadeIn();
		if (keyPressed) {
			renderFadeOut(4f);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.ENTER) {
			keyPressed = true;
			screenTime = 4f;
			return true;
		}
		return false;
	}

}
