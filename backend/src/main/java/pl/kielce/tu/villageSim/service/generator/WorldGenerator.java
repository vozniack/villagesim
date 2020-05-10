package pl.kielce.tu.villageSim.service.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.WorldParameters;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.BuildingUtil;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.components.PositionUtil;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorldGenerator {
    private final StructureService structureService;
    private final UnitService unitService;
    private final BuildingService buildingService;
    private final TaskService taskService;
    private final PositionUtil positionUtil;

    public void generateNewWorld() {
        log.info("## Generating new world...");

        World.IS_ACTIVE = false;

        clearWorld();

        setWorldProperties();

        generateStructures();
        generateBuildings();
        generateUnits();

        World.IS_ACTIVE = true;

        log.info("## New world generated");
    }

    private void clearWorld() {
        taskService.deleteAllTasks();
        buildingService.deleteAllBuildings();
        unitService.deleteAllUnits();
        structureService.deleteAllStructures();
    }

    private void setWorldProperties() {
        World.SIZE_WIDTH = WorldParameters.SIZE_WIDTH;
        World.SIZE_HEIGHT = WorldParameters.SIZE_HEIGHT;

        World.WOOD = WorldParameters.WOOD;
        World.ROCK = WorldParameters.ROCK;
        World.FOOD = WorldParameters.FOOD;
        World.GOLD = WorldParameters.GOLD;
    }

    private void generateStructures() {
        int worldSize = World.SIZE_WIDTH * World.SIZE_HEIGHT;

        // #todo check if cell is empty

        for (int i = 0; i < worldSize * WorldParameters.TREE_FACTOR; i++) {
            structureService.createStructure(StructureType.TREE, RandUtil.generateRand(1, 3), new Coordinates(RandUtil.generateRand(0, World.SIZE_WIDTH - 1), RandUtil.generateRand(0, World.SIZE_HEIGHT - 1), 1));
        }

        for (int i = 0; i < worldSize * WorldParameters.ROCK_FACTOR; i++) {
            structureService.createStructure(StructureType.ROCK, RandUtil.generateRand(1, 3), new Coordinates(RandUtil.generateRand(0, World.SIZE_WIDTH - 1), RandUtil.generateRand(0, World.SIZE_HEIGHT - 1), 1));
        }
    }

    private void generateBuildings() {
        buildingService.createBuilding(BuildingType.WAREHOUSE, BuildingState.NOT_BROKEN, new Coordinates((World.SIZE_WIDTH / 2) - 2, (World.SIZE_HEIGHT / 2) - 2, BuildingUtil.getBuildingSize(BuildingType.WAREHOUSE)));

        Building warehouse = buildingService.getWarehouse();

        structureService.clearStructuresNearWarehouse(warehouse);
    }

    private void generateUnits() {
        Building warehouse = buildingService.getWarehouse();

        for (int i = 0; i < WorldParameters.PEASANTS; i++) {
            unitService.createUnit(UnitType.PEASANT, positionUtil.getUnitCoordinatesNearPosition(warehouse, 1, 4));
        }

        for (int i = 0; i < WorldParameters.WORKERS; i++) {
            unitService.createUnit(UnitType.WORKER, positionUtil.getUnitCoordinatesNearPosition(warehouse, 1, 4));
        }
    }
}
