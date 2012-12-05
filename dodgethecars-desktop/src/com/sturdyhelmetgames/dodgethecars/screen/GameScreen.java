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
package com.sturdyhelmetgames.dodgethecars.screen;

import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.utils.Array;
import com.sturdyhelmetgames.dodgethecars.DodgeTheCarsGame;
import com.sturdyhelmetgames.dodgethecars.RandomUtil;
import com.sturdyhelmetgames.dodgethecars.assets.Art;
import com.sturdyhelmetgames.dodgethecars.assets.Sound;
import com.sturdyhelmetgames.dodgethecars.entity.BasicEntity;
import com.sturdyhelmetgames.dodgethecars.entity.BasicEntity.Direction;
import com.sturdyhelmetgames.dodgethecars.entity.Car;
import com.sturdyhelmetgames.dodgethecars.entity.GameOverIndicator;
import com.sturdyhelmetgames.dodgethecars.entity.Heart;
import com.sturdyhelmetgames.dodgethecars.entity.PauseIndicator;
import com.sturdyhelmetgames.dodgethecars.entity.PineCone;
import com.sturdyhelmetgames.dodgethecars.entity.Point;
import com.sturdyhelmetgames.dodgethecars.entity.Point.PointType;
import com.sturdyhelmetgames.dodgethecars.entity.Squirrel;
import com.sturdyhelmetgames.dodgethecars.entity.TrafficLight;
import com.sturdyhelmetgames.dodgethecars.events.EventCache;

/**
 * Main game screen.
 * 
 * @author Antti 19.6.2012
 * 
 */
public class GameScreen extends TransitionScreen {

	private static final float TIME_PER_ROUND = 5f;

	/**
	 * Number of car "waves"
	 */
	private long wave;

	/**
	 * How long the game session has been running.
	 */
	private float gameTime;

	/**
	 * First time attribute that is used to control the speed of {@link Car}
	 * spawns.
	 */
	private float carSpeedUpTotalTime;

	/**
	 * Second time attribute that is used to control the speed of {@link Car}
	 * spawns.
	 */
	private float carSpeedUpTime;

	/**
	 * Time that has passed since last {@link Car} spawn.
	 */
	private float carTime;

	/**
	 * Thime that has passed since last {@link PineCone} spawn.
	 */
	private float pineConeTime;

	/**
	 * Tells if the game is paused or not.
	 */
	private boolean paused = false;

	/**
	 * Tells if the game is over or not.
	 */
	private boolean gameOver = false;

	/**
	 * Tells how long game over lasts.
	 */
	private float gameOverTime = 0f;

	/**
	 * List for game objects.
	 */
	private final Array<BasicEntity> gameEntities = new Array<BasicEntity>(
			false, 30);

	/**
	 * Pool for cars.
	 */
	private final Array<Car> carPool = new Array<Car>(false, 20);

	/**
	 * Pool for pinecones.
	 */
	private final Array<PineCone> pineConePool = new Array<PineCone>(false, 20);

	public static final float LEVEL_BOUNDARY_X_RIGHT = 21f;
	public static final float LEVEL_BOUNDARY_X_LEFT = -23f;
	public static final float LEVEL_BOUNDARY_Y_TOP = 14f;
	public static final float LEVEL_BOUNDARY_Y_BOTTOM = -15f;

	/**
	 * The player character.
	 */
	private Squirrel player;

	/**
	 * Traffic light.
	 */
	private TrafficLight trafficLight;

	/**
	 * Game over indicator.
	 */
	private final GameOverIndicator gameOverIndicator = new GameOverIndicator();

	/**
	 * Pause indicator.
	 */
	private final PauseIndicator pauseIndicator = new PauseIndicator();

	/**
	 * Holder for score text position.
	 */
	private final Vector3 scoreTextPosition = new Vector3(
			LEVEL_BOUNDARY_X_LEFT, LEVEL_BOUNDARY_Y_TOP - 1f, 0f);

	/**
	 * Stage for {@link Touchpad}.
	 */
	private final Stage stage;

	/**
	 * {@link Touchpad} for touch events.
	 */
	private final Touchpad touchpad;

	/**
	 * Indicates if touch controls are active or not.
	 */
	private boolean isTouchActive = false;

	public GameScreen(final DodgeTheCarsGame game) {
		super(game);

		Gdx.input.setCatchBackKey(true);

		player = new Squirrel();
		gameEntities.add(player);

		trafficLight = new TrafficLight();

		camera.project(scoreTextPosition);

		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
				true) {
			@Override
			public boolean keyDown(int keyCode) {
				if (gameTime > 3.5f) {
					if (!paused && !gameOver) {
						if (keyCode == Keys.P || keyCode == Keys.ESCAPE
								|| keyCode == Keys.BACK || keyCode == Keys.HOME) {
							pause();
							return true;
						}
					} else if (paused) {
						if (keyCode == Keys.P || keyCode == Keys.ESCAPE
								|| keyCode == Keys.BACK || keyCode == Keys.C) {
							resumeGame();
							return true;
						} else if (keyCode == Keys.Q) {
							updateLeaderboard();
							game.setScreen(new TitleScreen(game));
							return true;
						}
					}

					if (gameOver) {
						if (keyCode == Keys.R) {
							game.setScreen(new GameScreen(game));
							return true;
						} else if (keyCode == Keys.Q) {
							game.setScreen(new TitleScreen(game));
							return true;
						}
					}
				}
				return super.keyDown(keyCode);
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				final Vector3 position = new Vector3(screenX, screenY, 0f);
				camera.unproject(position);

				if (gameTime > 3.5f) {
					if (paused) {
						// very dirty code, sorry :)
						if (position.x > -7f && position.x < 7f
								&& position.y > 3f && position.y < 6f) {
							resumeGame();
							return true;
						} else if (position.x > -7f && position.x < 7f
								&& position.y < -4f && position.y > -7f) {
							updateLeaderboard();
							game.setScreen(new TitleScreen(game));
							return true;
						}
					}
					if (gameOver) {
						// very dirty code, sorry :)
						if (position.x > -7f && position.x < 7f
								&& position.y > 4f && position.y < 7f) {
							game.setScreen(new GameScreen(game));
							return true;
						} else if (position.x > -7f && position.x < 7f
								&& position.y < -4f && position.y > -7f) {
							game.setScreen(new TitleScreen(game));
							return true;
						}
					}
				}

				return super.touchUp(screenX, screenY, pointer, button);
			}

		};
		Gdx.input.setInputProcessor(stage);
		touchpad = new Touchpad(2.0f, Art.skin.get("touchpad",
				TouchpadStyle.class));
		touchpad.setBounds(15, 15, 200, 200);
		stage.addActor(touchpad);

		// if touch-based device, activate touch controls
		if (ApplicationType.Android.equals(Gdx.app.getType())
				|| ApplicationType.iOS.equals(Gdx.app.getType())) {
			isTouchActive = true;
		}
	}

	/**
	 * Activates game over screen.
	 */
	private void activateGameOver() {
		gameOver = true;

		if (Sound.musicHappyGoLucky.isPlaying()) {
			Sound.musicHappyGoLucky.stop();
		}
		Sound.soundSquirrelDie.play();
		updateLeaderboard();
	}

	/**
	 * Update leaderboard with player score.
	 */
	private void updateLeaderboard() {
		EventCache.updateLeaderboard.eventValue = player.score;
		game.fireEvent(EventCache.updateLeaderboard);
	}

	@Override
	protected void updateScreen(float fixedStep) {
		super.updateScreen(fixedStep);

		// check input and after that update entities
		checkInput(fixedStep);

		if (paused) {
			pauseIndicator.update(fixedStep);
		} else {
			// touch controls if on android
			if (isTouchActive && player.isAlive()) {
				stage.act(fixedStep);
				final float knobX = touchpad.getKnobX() - 100;
				final float knobY = touchpad.getKnobY() - 100;
				player.acceleration.x = knobX;
				player.acceleration.y = knobY;
			}

			// cumulate timers
			gameTime += fixedStep;
			carSpeedUpTotalTime += fixedStep;

			trafficLight.update(fixedStep);

			if (gameOver) {
				gameOverTime += fixedStep;
				if (gameOverTime > 2f) {
					gameOverIndicator.update(fixedStep);
				}
			}

			if (gameTime > 3f) {
				if (!Sound.musicHappyGoLucky.isPlaying() && player.isAlive()) {
					Sound.musicHappyGoLucky.play();
				}

				carSpeedUpTime += fixedStep;
				carTime += fixedStep;
				pineConeTime += fixedStep;

				// add some score when player is alive
				if (player.isAlive()) {
					player.score += (fixedStep * 90);
				}

				wave = Math.round(gameTime / TIME_PER_ROUND) + 1;
				carSpeedUpTime = wave % 3 == 0 ? (carSpeedUpTotalTime * wave) / 5000 + 3.5f
						: 2f;
				if (carSpeedUpTime > 4.2f) {
					carSpeedUpTotalTime = 2f;
				}

				// randomly spawn cars
				if (carTime > 4f + MathUtils.random(5f) - carSpeedUpTime) {
					carTime = 0f;

					// from which side the car drives
					final boolean side = getRandom().nextBoolean();
					final float carX = side ? 35f : -35f;
					final float carY = getRandom().nextInt(32) - 16;
					final Direction carDirection = side ? Direction.LEFT
							: Direction.RIGHT;
					final int carColor = getRandom().nextInt(3);
					final float maxVelocity = RandomUtil.oneOfFive() ? 20f
							: BasicEntity.VELOCITY_MAX;

					Car car = null;
					if (carPool.size < 1) {
						// pool didn't have spare cars, create a new one
						car = new Car(carX, carY, carDirection, carColor,
								maxVelocity);
					} else {
						// pool had cars, fetch one and reset
						car = carPool.get(0);
						car.reset(carX, carY, carDirection, carColor,
								maxVelocity);
						carPool.removeIndex(0);
					}
					gameEntities.add(car);
				}

				// randomly spawn pinecones and hearts
				if (pineConeTime > 2f + MathUtils.random(5f)) {
					pineConeTime = 0f;

					// randomize new powerup position
					final float powerupX = getRandom().nextInt(44) - 22;
					final float powerupY = getRandom().nextInt(28) - 14;

					if (RandomUtil.oneOfSeven()) {
						// TODO: pool hearts for less GC
						Heart heart = new Heart(powerupX, powerupY);
						gameEntities.add(heart);
					} else {
						// randomize new pinecone color
						int color = PineCone.PINECONE_COLOR_BRONZE;
						if (RandomUtil.oneOfThree()) {
							color = PineCone.PINECONE_COLOR_SILVER;
						} else if (RandomUtil.oneOfFive()) {
							color = PineCone.PINECONE_COLOR_GOLD;
						}

						PineCone pineCone = null;
						if (pineConePool.size < 1) {
							// pool didn't have spare pinecones, create a new
							// one
							pineCone = new PineCone(powerupX, powerupY, color);
						} else {
							// pineConePool.sort();
							// pool had pinecones, fetch one and reset
							pineCone = pineConePool.get(0);
							pineCone.reset(powerupX, powerupY, color);
							pineConePool.removeIndex(0);
						}

						gameEntities.add(pineCone);
					}
				}

				// update entities
				for (Iterator<BasicEntity> i = gameEntities.iterator(); i
						.hasNext();) {
					BasicEntity entity = i.next();
					entity.update(fixedStep);

					if (entity instanceof Car) {
						Car car = (Car) entity;

						// if car hits player, squirrel takes damage
						if (car.hit(player)) {
							if (!player.isDamaged && player.health > 1) {
								Sound.soundSquirrelHit.play();
							}
							if (player.takeDamage()) {
								activateGameOver();
							}
						}

						// if car is out of the limits send it to pool
						if (car.x < -36f || car.x > 36f) {
							carPool.add(car);
							i.remove();
						}
					}
					if (entity instanceof PineCone) {
						PineCone pineCone = (PineCone) entity;

						// if pine cone hits player, "collect" it
						if (player.isAlive() && pineCone.hit(player)) {
							pineCone.collected = true;
							player.score += (500 * (pineCone.color + 1));
							// TODO: pool point objects
							if (pineCone.color == PineCone.PINECONE_COLOR_BRONZE) {
								gameEntities.add(new Point(pineCone.x,
										pineCone.y, PointType.POINT_500));
							} else if (pineCone.color == PineCone.PINECONE_COLOR_SILVER) {
								gameEntities.add(new Point(pineCone.x,
										pineCone.y, PointType.POINT_1000));
							} else if (pineCone.color == PineCone.PINECONE_COLOR_GOLD) {
								gameEntities.add(new Point(pineCone.x,
										pineCone.y, PointType.POINT_1500));
							}
						}

						// if pine cone is collected send it to pool
						if (!pineCone.isAlive() || pineCone.collected) {
							if (pineCone.collected) {
								Sound.soundCollect.play();
							}
							pineCone.collected = false;
							pineConePool.add(pineCone);
							i.remove();
						}
					}
					if (entity instanceof Heart) {
						Heart heart = (Heart) entity;
						// if heart hits player, "collect" it and get score and
						// health
						if (player.isAlive() && heart.hit(player)) {
							heart.collected = true;
							player.score += 500;
							player.replenishHealth();
							// TODO: pool points
							gameEntities.add(new Point(heart.x, heart.y,
									PointType.POINT_500));
						}

						if (!heart.isAlive() || heart.collected) {
							if (heart.collected) {
								Sound.soundCollect.play();
							}
							i.remove();
						}
					}
					if (entity instanceof Point) {
						Point point = (Point) entity;
						if (!point.isAlive()) {
							i.remove();
						}
					}
				}

			}
			// sort order of entities for drawing, pretty lame solution
			gameEntities.sort();
		}
	}

	@Override
	public void renderScreen(float delta) {
		spriteBatch.getProjectionMatrix().set(camera.combined);
		spriteBatch.begin();

		// render the background
		spriteBatch.draw(Art.backgroundTexture, -24, -46.5f, 48, 64);

		// if player is dead, render it underneath everything
		if (!player.isAlive()) {
			player.render(spriteBatch, delta);
		}

		// render the entities except squirrel when it's dead
		for (BasicEntity entity : gameEntities) {
			if (!(entity instanceof Squirrel) || ((Squirrel) entity).isAlive()) {
				entity.render(spriteBatch, delta);
			}
		}

		// render hud
		for (int i = 0; i < Squirrel.HEALTH_MAX; i++) {
			if (i < player.health) {
				spriteBatch.draw(Art.heartTex, LEVEL_BOUNDARY_X_LEFT + i * 2f,
						LEVEL_BOUNDARY_Y_TOP - 1f, 2f, 2f);
			} else {
				spriteBatch.draw(Art.heartBlackTex, LEVEL_BOUNDARY_X_LEFT + i
						* 2f, LEVEL_BOUNDARY_Y_TOP - 1f, 2f, 2f);
			}
		}

		trafficLight.render(spriteBatch, delta);

		if (gameOver) {
			gameOverIndicator.render(spriteBatch, delta);
		}
		if (paused) {
			pauseIndicator.render(spriteBatch, delta);
		}

		spriteBatch.end();

		if (isTouchActive) {
			stage.draw();
		}

		// render fps and additional stuff for debugging
		spriteBatch.getProjectionMatrix().set(normalProjection);
		spriteBatch.begin();

		Art.scoreFont.draw(spriteBatch, String.valueOf(player.score),
				scoreTextPosition.x, scoreTextPosition.y);

		if (isDebug()) {
			Art.debugFont.draw(spriteBatch, "Score: " + player.score, 0, 40);

			int numOfCarsInPlay = 0;
			int numOfCarsInPool = 0;
			for (BasicEntity entity : gameEntities) {
				if (entity instanceof Car) {
					numOfCarsInPlay++;
				}
			}
			for (Car car : carPool) {
				if (car != null) {
					numOfCarsInPool++;
				}
			}
			Art.debugFont.draw(spriteBatch, "Number of cars in play: "
					+ numOfCarsInPlay, 0, 60);
			Art.debugFont.draw(spriteBatch, "Number of cars in pool: "
					+ numOfCarsInPool, 0, 80);

			int numOfPineConesInPlay = 0;
			int numOfPineConesInPool = 0;
			for (BasicEntity entity : gameEntities) {
				if (entity instanceof PineCone) {
					numOfPineConesInPlay++;
				}
			}
			for (PineCone pineCone : pineConePool) {
				if (pineCone != null) {
					numOfPineConesInPool++;
				}
			}

			Art.debugFont.draw(spriteBatch, "Number of pine cones in play: "
					+ numOfPineConesInPlay, 0, 100);
			Art.debugFont.draw(spriteBatch, "Number of pine cones in pool: "
					+ numOfPineConesInPool, 0, 120);
			Art.debugFont.draw(spriteBatch, "Game speed up time: "
					+ carSpeedUpTime, 0, 140);
			Art.debugFont.draw(spriteBatch, "Round: " + wave, 0, 160);

		}

		spriteBatch.end();

		renderFadeIn(delta);
	}

	/**
	 * Processes player movement keys.
	 * 
	 * @param fixedStep
	 */
	private void checkInput(float fixedStep) {
		if (!paused) {
			if (player.isAlive()) {
				// process player movement keys
				if (Gdx.input.isKeyPressed(Keys.UP)) {
					player.direction = Direction.UP;
					player.acceleration.y = BasicEntity.ACCELERATION_MAX;
				}
				if (Gdx.input.isKeyPressed(Keys.DOWN)) {
					player.direction = Direction.DOWN;
					player.acceleration.y = -BasicEntity.ACCELERATION_MAX;
				}
				if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
					player.direction = Direction.RIGHT;
					player.acceleration.x = BasicEntity.ACCELERATION_MAX;
				}
				if (Gdx.input.isKeyPressed(Keys.LEFT)) {
					player.direction = Direction.LEFT;
					player.acceleration.x = -BasicEntity.ACCELERATION_MAX;
				}
			}
		}

	}

	@Override
	public void pause() {
		super.pause();
		paused = true;
		pauseIndicator.reset();
	}

	public void resumeGame() {
		paused = false;
	}

	/**
	 * Return a {@link Random} instance.
	 * 
	 * @return Random instance.
	 */
	private Random getRandom() {
		return RandomUtil.getRandom();
	}

	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
	}

}
