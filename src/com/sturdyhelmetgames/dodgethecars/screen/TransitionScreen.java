package com.sturdyhelmetgames.dodgethecars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.sturdyhelmetgames.dodgethecars.DodgeTheCarsGame;

/**
 * Screen that supports simple transitions.
 * 
 * @author Antti 28.6.2012
 * 
 */
public abstract class TransitionScreen extends BasicScreen {

	protected float screenTime;
	protected Color transitionColor = new Color();
	private final ShapeRenderer renderer = new ShapeRenderer();

	public TransitionScreen(DodgeTheCarsGame game) {
		super(game);
	}

	@Override
	protected void updateScreen(float fixedStep) {
		screenTime += fixedStep;
	}

	@Override
	public void renderScreen(float delta) {
		renderFadeIn();
		renderFadeOut(4f);
	}

	/**
	 * Renders fade in transition.
	 */
	protected void renderFadeIn() {
		// a simple fade in transition
		if (screenTime > -1f && screenTime < 0.99f) {
			transitionColor.set(0f, 0f, 0f, 1f - screenTime);

			Gdx.gl.glEnable(GL20.GL_BLEND);
			renderer.begin(ShapeType.FilledRectangle);
			renderer.filledRect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), transitionColor,
					transitionColor, transitionColor, transitionColor);
			renderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}

	/**
	 * Renders fade out transition.
	 */
	protected void renderFadeOut(float fadeOutTime) {
		if (screenTime > fadeOutTime) {
			transitionColor.set(0f, 0f, 0f, screenTime - fadeOutTime);

			Gdx.gl.glEnable(GL20.GL_BLEND);
			renderer.begin(ShapeType.FilledRectangle);
			renderer.filledRect(0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), transitionColor,
					transitionColor, transitionColor, transitionColor);
			renderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		renderer.dispose();
	}

}
