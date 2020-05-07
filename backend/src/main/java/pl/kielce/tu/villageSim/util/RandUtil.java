package pl.kielce.tu.villageSim.util;

import java.util.Random;

public class RandUtil {
    private static final Random random = new Random();

    public static Integer generateRand(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static boolean generateChance(Double possibility) {
        return random.nextDouble() <= possibility;
    }
}
