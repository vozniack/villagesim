package pl.kielce.tu.villageSim.api.dto;

import lombok.Data;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;

import java.util.ArrayList;
import java.util.List;

@Data
public class WorldDto {

    /* Properties of world */

    private Integer sizeWidth = 64;

    private Integer sizeHeight = 64;


    /* Content of world */

    private List<Unit> units = new ArrayList<>();

    private List<Building> buildings = new ArrayList<>();

    private List<Structure> structures = new ArrayList<>();

    private List<Task> tasks = new ArrayList<>();
}
