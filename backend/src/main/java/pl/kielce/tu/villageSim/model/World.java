package pl.kielce.tu.villageSim.model;

import lombok.Data;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;

import java.util.ArrayList;
import java.util.List;

@Data
public class World {

    /* Properties of world */
    private static Integer sizeWidth = 128;

    private static Integer sizeHeight = 128;


    /* Content of world */

    private static List<Unit> units = new ArrayList<>();

    private static List<Building> buildings = new ArrayList<>();

    private static List<Structure> structures = new ArrayList<>();

    private static List<Task> tasks = new ArrayList<>();
}
