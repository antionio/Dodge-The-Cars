package com.sturdyhelmetgames.dodgethecars.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.sturdyhelmetgames.dodgethecars.DodgeTheCarsGame;
import com.sturdyhelmetgames.dodgethecars.assets.Art;

/**
 * Basic stuff needed by all game screens.
 * 
 * @author Antti 18.6.2012
 * 
 */
public abstract class BasicScreen extends InputAdapter implements Screen {

	public DodgeTheCarsGame game;
	protected final OrthographicCamera camera = new OrthographicCamera(48, 32);
	protected final SpriteBatch spriteBatch = new SpriteBatch();
	protected final Matrix4 normalProjection = new Matrix4();
	protected boolean isGL20Available = false;
	protected boolean stepped = false;

	private static final int MAX_FPS = 60;
	private static final int MIN_FPS = 15;
	private static final float TIME_STEP = 1f / MAX_FPS;
	private static final float MAX_STEPS = 1f + MAX_FPS / MIN_FPS;
	private static final float MAX_TIME_PER_FRAME = TIME_STEP * MAX_STEPS;
	private float stepTimeLeft;

	public BasicScreen(DodgeTheCarsGame game) {
		this.game = game;

		// check if gl2 is available
		isGL20Available = Gdx.graphics.isGL20Available();

		// reset camera
		camera.position.set(0, 0, 0);
		camera.update();

		// set a normal projection matrix
		normalProjection.setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/**
	 * Does a fixed timestep independently of framerate.
	 * 
	 * @param delta
	 *            Delta time.
	 * @return True if stepped, false otherwise.
	 */
	private void fixedStep(float delta) {
		stepTimeLeft += delta;
		if (stepTimeLeft > MAX_TIME_PER_FRAME)
			stepTimeLeft = MAX_TIME_PER_FRAME;
		while (stepTimeLeft >= TIME_STEP) {
			updateScreen(TIME_STEP);
			stepTimeLeft -= TIME_STEP;
		}
	}

	/**
	 * Updates the game logic.
	 * 
	 * @param fixedStep
	 *            A fixed timestep.
	 */
	protected abstract void updateScreen(float fixedStep);

	/**
	 * Renders the game graphics.
	 * 
	 * @param delta
	 *            Delta time.
	 */
	public abstract void renderScreen(float delta);

	@Override
	public void render(float delta) {

		fixedStep(delta);

		if (isGL20Available) {
			Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT);
		} else {
			Gdx.graphics.getGL10().glClear(GL10.GL_COLOR_BUFFER_BIT);
		}

		// render screen graphics
		renderScreen(delta);

		// render fps if in debug mode
		if (isDebug()) {
			spriteBatch.getProjectionMatrix().set(normalProjection);
			spriteBatch.begin();
			Art.debugFont.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 0, 20);
			spriteBatch.end();
		}
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
	}

	/**
	 * Is the screen in DEBUG mode?
	 * 
	 * @return If screen is in DEBUG mode returns true, otherwise returns false.
	 */
	protected boolean isDebug() {
		return DodgeTheCarsGame.DEBUG;
	}

	@Override
	public void show() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void resize(int width, int height) {

	}

}
