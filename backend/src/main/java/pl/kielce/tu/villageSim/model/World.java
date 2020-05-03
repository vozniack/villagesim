package pl.kielce.tu.villageSim.model;

import lombok.Getter;

public class World {

    @Getter
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static World INSTANCE = new World();

    /* Properties of world */

    public static Boolean isWorldReady = false;

    public static Integer sizeWidth = 64;

    public static Integer sizeHeight = 16;

}
