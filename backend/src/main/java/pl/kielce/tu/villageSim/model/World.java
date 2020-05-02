package pl.kielce.tu.villageSim.model;

import lombok.Getter;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;

import java.util.ArrayList;
import java.util.List;

public class World {

    @Getter
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static World INSTANCE = new World();

    /* Properties of world */

    public static Boolean isWorldReady = false;

    public static Integer sizeWidth = 64;

    public static Integer sizeHeight = 64;


    /* Content of world */

    public static List<Unit> units = new ArrayList<>();

    public static List<Building> buildings = new ArrayList<>();

    public static List<Structure> structures = new ArrayList<>();

    public static List<Task> tasks = new ArrayList<>();


}
