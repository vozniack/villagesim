package pl.kielce.tu.villageSim.model;

import lombok.Getter;

public class World {

    @Getter
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static World INSTANCE = new World();

    /* Properties of world */

    public static Boolean IS_ACTIVE = false;

    public static Integer SIZE_WIDTH = 0;

    public static Integer SIZE_HEIGHT = 0;

    /* Resources */

    public static Integer WOOD = 0;

    public static Integer ROCK = 0;

    public static Integer FOOD = 0;

    public static Integer GOLD = 0;

}
