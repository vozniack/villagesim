package pl.kielce.tu.villageSim.service.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.WorldParameters;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.types.log.LogType;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.BuildingUtil;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.TimeUtil;
import pl.kielce.tu.villageSim.util.components.PositionUtil;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class WorldGenerator {
    private final StructureService structureService;
    private final UnitService unitService;
    private final BuildingService buildingService;
    private final CommunicationService communicationService;
    private final PositionUtil positionUtil;
    private final BuildingRepository buildingRepository;
    private final StructureRepository structureRepository;
    private final UnitRepository unitRepository;
    private final TaskRepository taskRepository;

    public void generateNewWorld() {
        log.info("## Generating new world...");

        World.IS_ACTIVE = false;

        clearWorld();

        setWorldProperties();

        TimeUtil.START_TIME = LocalDateTime.now();

        communicationService.sendLog("Wygenerowano nowy świat!", "00:00", LogType.INFO);

        generateStructures();
        generateBuildings();
        generateUnits();

        World.IS_ACTIVE = true;

        log.info("## New world generated");

        communicationService.sendWorldState();
        communicationService.sendStatistics();
    }

    private void clearWorld() {
        taskRepository.findAll().forEach(task -> {
            task.setStructure(null);
            task.setBuilding(null);
            task.setUnit(null);
            taskRepository.save(task);
        });

        structureRepository.deleteAll();
        buildingRepository.deleteAll();
        unitRepository.deleteAll();

        taskRepository.deleteAll();
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

        for (int i = 0; i < worldSize * WorldParameters.TREE_FACTOR; i++) {
            structureService.createStructure(StructureType.TREE, RandUtil.generateRand(1, 3), new Coordinates(RandUtil.generateRand(0, World.SIZE_WIDTH - 1), RandUtil.generateRand(0, World.SIZE_HEIGHT - 1), 1));
        }

        for (int i = 0; i < worldSize * WorldParameters.ROCK_FACTOR; i++) {
            structureService.createStructure(StructureType.ROCK, RandUtil.generateRand(1, 3), new Coordinates(RandUtil.generateRand(0, World.SIZE_WIDTH - 1), RandUtil.generateRand(0, World.SIZE_HEIGHT - 1), 1));
        }
    }

    private void generateBuildings() {
        Building building = buildingService.createBuilding(BuildingType.WAREHOUSE, BuildingState.NOT_BROKEN, new Coordinates((World.SIZE_WIDTH / 2) - 2, (World.SIZE_HEIGHT / 2) - 2, BuildingUtil.getBuildingSize(BuildingType.WAREHOUSE)), false);

        structureService.clearStructuresNearWarehouse(building);
    }

    private void generateUnits() {
        Building warehouse = buildingRepository.getAllByBuildingType(BuildingType.WAREHOUSE).get(0);

        for (int i = 0; i < WorldParameters.PEASANTS; i++) {
            unitService.createUnit(UnitType.PEASANT, positionUtil.getUnitCoordinatesNearPosition(warehouse, 1, 4));
        }
    }
}
