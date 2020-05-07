package pl.kielce.tu.villageSim.util.components;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.UnitService;

@Component
@AllArgsConstructor
public class PathFindingUtil {
    private final StructureService structureService;
    private final BuildingService buildingService;
    private final UnitService unitService;

    public Integer[][] prepareWorldArray() {
        Integer[][] array = new Integer[World.sizeWidth][World.sizeHeight];

        for (int i = 0; i < World.sizeWidth; i++) {
            for (int j = 0; j < World.sizeHeight; j++) {
                array[i][j] = 0;
            }
        }

        for (Structure structure : structureService.getAllStructures()) {
            array[structure.getPositionX()][structure.getPositionY()] = -1;
        }

        for (Building building : buildingService.getAllBuildings()) {
            fillAllBuildingCells(array, building);
        }

        for (Unit unit : unitService.getAllUnits()) {
            array[unit.getPositionX()][unit.getPositionY()] = -1;
        }

        return array;
    }

    private void fillAllBuildingCells(Integer[][] array, Building building) {
        for (int i = 0; i < building.getSize(); i++) {
            for (int j = 0; j < building.getSize(); j++) {
                array[building.getPositionX() + i][building.getPositionY() + j] = -1;
            }
        }
    }

    // #todo to remove
    private void printArray(Integer[][] array) {
        for (int i = 0; i < World.sizeWidth; i++) {
            for (int j = 0; j < World.sizeHeight; j++) {
                System.out.print("[  " + array[i][j] + "  ]");
            }

            System.out.println();
        }
    }
}
