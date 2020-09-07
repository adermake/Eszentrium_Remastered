package esze.utils;

import io.netty.util.internal.ThreadLocalRandom;

public class MathUtils {
	
	public static int randInt(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
}
