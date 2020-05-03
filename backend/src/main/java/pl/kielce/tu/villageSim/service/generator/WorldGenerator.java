package pl.kielce.tu.villageSim.service.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.util.Position;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.structure.StructureType;
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

        clearWorld();

        World.sizeWidth = (width != null) ? width : World.sizeWidth;
        World.sizeHeight = (height != null) ? height : World.sizeHeight;

        generateStructures();
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
        for (int i = 0; i < 100; i++) {
            structureService.createStructure(StructureType.TREE, RandUtil.generateRand(1, 3), new Position(RandUtil.generateRand(0, World.sizeWidth), RandUtil.generateRand(0, World.sizeHeight), 1));
        }

        for (int i = 0; i < 100; i++) {
            structureService.createStructure(StructureType.ROCK, 3, new Position(RandUtil.generateRand(0, World.sizeWidth), RandUtil.generateRand(0, World.sizeHeight), 1));
        }
    }

    public void generateUnits() {
        // #todo generate few units in a center of map or next to main building
    }
}
