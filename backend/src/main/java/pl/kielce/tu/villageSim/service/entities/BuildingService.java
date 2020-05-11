package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.util.BuildingUtil;
import pl.kielce.tu.villageSim.util.components.PositionUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final PositionUtil positionUtil;
    private final TaskService taskService;

    public Building createBuilding(BuildingType buildingType, BuildingState buildingState, Coordinates coordinates, Boolean createTask) {
        if (coordinates == null) {
            coordinates = positionUtil.getNewBuildingCoordinates(buildingType);
        }

        log.info("# Creating new building " + buildingType + " at " + coordinates.toString());

        Building building = buildingRepository.save(new Building(buildingType, buildingState, coordinates));
        BuildingUtil.setBuildingRequiredResources(building);

        if (createTask) {
            taskService.createBuildTask(building);
        }

        return building;
    }

    public void updateBuilding(Building building) {
        buildingRepository.save(building);
    }
}
