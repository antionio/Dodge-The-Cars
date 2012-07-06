package com.sturdyhelmetgames.dodgethecars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.sturdyhelmetgames.dodgethecars.DodgeTheCarsGame;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

/**
 * A splash screen that shows the Sturdy Helmet Games' logo.
 * 
 * @author Antti 18.6.2012
 * 
 */
public class SplashScreen extends TransitionScreen {

	public SplashScreen(DodgeTheCarsGame game) {
		super(game);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	protected void updateScreen(float fixedStep) {
		super.updateScreen(fixedStep);
		if (screenTime > 4.99f) {
			game.setScreen(new TitleScreen(game));
		}
	}

	@Override
	public void renderScreen(float delta) {
		spriteBatch.getProjectionMatrix().set(camera.combined);
		spriteBatch.begin();
		// draw logo and background
		spriteBatch.draw(Art.splashBackgroundTex, -25f, -25f, 50f, 50f);
		spriteBatch.draw(Art.sturdyHelmetLogoTex, -12.5f, -20f, 25f, 35f);
		spriteBatch.end();
		super.renderScreen(delta);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.ENTER) {
			if (screenTime < 4f) {
				screenTime = 4f;
			}
			return true;
		}
		return false;
	}

}
