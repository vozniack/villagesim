package pl.kielce.tu.villageSim.service.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.util.Position;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.RandUtil;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorldGenerator {
    private final StructureService structureService;
    private final UnitService unitService;
    private final BuildingService buildingService;
    private final TaskService taskService;

    public void generateNewWorld(Integer width, Integer height) {
        log.info("## Generating new world...");

        World.isWorldReady = false;

        clearWorld();

        World.sizeWidth = (width != null) ? width : World.sizeWidth;
        World.sizeHeight = (height != null) ? height : World.sizeHeight;

        generateStructures();
        generateBuildings();
        generateUnits();

        World.isWorldReady = true;

        log.info("## New world generated");
    }

    private void clearWorld() {
        taskService.deleteAllTasks();
        buildingService.deleteAllBuildings();
        unitService.deleteAllUnits();
        structureService.deleteAllStructures();
    }

    private void generateStructures() {
        int worldSize = World.sizeWidth * World.sizeHeight;

        for (int i = 0; i < worldSize * 0.1; i++) {
            structureService.createStructure(StructureType.TREE, RandUtil.generateRand(1, 3), new Position(RandUtil.generateRand(0, World.sizeWidth), RandUtil.generateRand(0, World.sizeHeight), 1));
        }

        for (int i = 0; i < worldSize * 0.05; i++) {
            structureService.createStructure(StructureType.ROCK, RandUtil.generateRand(1, 3), new Position(RandUtil.generateRand(0, World.sizeWidth), RandUtil.generateRand(0, World.sizeHeight), 1));
        }
    }

    private void generateBuildings() {
        buildingService.createBuilding(BuildingType.WAREHOUSE, new Position((World.sizeWidth / 2) - 2, (World.sizeHeight / 2) - 2, 3));

        Building warehouse = buildingService.getBuildingsByType(BuildingType.WAREHOUSE).get(0);

        buildingService.createBuilding(BuildingType.HOUSE, new Position(warehouse.getPositionX() - 4, warehouse.getPositionY() - 4, 2));
        buildingService.createBuilding(BuildingType.HOUSE, new Position(warehouse.getPositionX() + warehouse.getSize() + 1, warehouse.getPositionY() + warehouse.getSize() + 3, 2));
        buildingService.createBuilding(BuildingType.HOUSE, new Position(warehouse.getPositionX() + warehouse.getSize() + 3, warehouse.getPositionY() - 2, 2));

        // #todo random house coordinates

        structureService.clearStructuresNearWarehouse(warehouse);
    }

    private void generateUnits() {
        Building warehouse = buildingService.getBuildingsByType(BuildingType.WAREHOUSE).get(0);

        unitService.createUnit(UnitType.PEASANT, new Position(warehouse.getPositionX() - 2, warehouse.getPositionY() - 2, 1));
        unitService.createUnit(UnitType.PEASANT, new Position(warehouse.getPositionX() - 4, warehouse.getPositionY() - 1, 1));
        unitService.createUnit(UnitType.PEASANT, new Position(warehouse.getPositionX() - 3, warehouse.getPositionY(), 1));
        unitService.createUnit(UnitType.PEASANT, new Position(warehouse.getPositionX() - 1, warehouse.getPositionY() + 2, 1));
        unitService.createUnit(UnitType.PEASANT, new Position(warehouse.getPositionX() - 4, warehouse.getPositionY() + 2, 1));

        // #todo generate few units next to warehouse
    }
}
