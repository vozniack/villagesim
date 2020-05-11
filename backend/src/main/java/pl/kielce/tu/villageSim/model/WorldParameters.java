package pl.kielce.tu.villageSim.model;

import lombok.Getter;

public class WorldParameters {

    @Getter
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static WorldParameters INSTANCE = new WorldParameters();

    /* World size */

    public static Integer SIZE_WIDTH = 96;

    public static Integer SIZE_HEIGHT = 32;

    /* Generating factors */

    public static Double TREE_FACTOR = 0.1;

    public static Double ROCK_FACTOR = 0.05;

    /* Resources */

    public static Integer WOOD = 64;

    public static Integer ROCK = 32;

    public static Integer FOOD = 16;

    public static Integer GOLD = 0;

    /* Units */

    public static Integer PEASANTS = 4;

    public static Integer WORKERS = 0;
}
