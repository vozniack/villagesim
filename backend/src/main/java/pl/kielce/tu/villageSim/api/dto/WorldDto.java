package pl.kielce.tu.villageSim.api.dto;

import lombok.Data;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;

import java.util.List;

@Data
public class WorldDto {

    /* Properties of world */

    private Integer sizeWidth;

    private Integer sizeHeight;


    /* Content of world */

    private List<Unit> units;

    private List<Building> buildings;

    private List<Structure> structures;

    private List<Task> tasks;

    public WorldDto() {
        this.sizeWidth = World.sizeWidth;
        this.sizeHeight = World.sizeHeight;

        this.units = World.units;
        this.buildings = World.buildings;
        this.structures = World.structures;
        this.tasks = World.tasks;
    }
}
