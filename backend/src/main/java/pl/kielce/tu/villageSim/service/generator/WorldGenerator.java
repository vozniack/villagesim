package pl.kielce.tu.villageSim.service.generator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorldGenerator {
    private final StructureService structureService;
    private final UnitService unitService;
    private final BuildingService buildingService;
    private final TaskService taskService;

    public void generateNewWorld() {
        log.info("## Generating new world...");

        clearWorld();
        generateStructures();
        generateUnits();

        log.info("## New world generated");
    }

    private void clearWorld() {
        taskService.deleteAllTasks();
        buildingService.deleteAllBuildings();
        unitService.deleteAllUnits();
        structureService.deleteAllStructures();
    }

    private void generateStructures() {

        // #todo trees and rocks using noise
    }

    public void generateUnits() {

        // #todo generate few units in a center of map
    }
}
