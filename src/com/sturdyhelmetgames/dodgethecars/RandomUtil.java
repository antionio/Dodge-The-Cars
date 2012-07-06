package com.sturdyhelmetgames.dodgethecars;

import java.util.Random;

import com.badlogic.gdx.math.MathUtils;

/**
 * Utility class for getting stuff randomized.
 * 
 * @author Antti 25.6.2012
 * 
 */
public final class RandomUtil extends MathUtils {

	/**
	 * Return the random instance.
	 * 
	 * @return Random
	 */
	public static final Random getRandom() {
		return random;
	}

	/**
	 * Randomizes a one-of-three value.
	 * 
	 * @return true if randomized value was one of three, false otherwise.
	 */
	public static boolean oneOfThree() {
		int selector = random(2);
		if (selector == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Randomizes one-of-five value.
	 * 
	 * @return returns true 20% of the time, false 80% of the time.
	 */
	public static boolean oneOfFive() {
		int selector = random(4);
		if (selector == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Randomizes one-of-seven value.
	 * 
	 * @return
	 */
	public static boolean oneOfSeven() {
		int selector = random(6);
		if (selector == 0) {
			return true;
		}
		return false;
	}

}
