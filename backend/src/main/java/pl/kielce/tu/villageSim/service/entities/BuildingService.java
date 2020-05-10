package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.util.components.PositionUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final PositionUtil positionUtil;

    public Building createBuilding(BuildingType buildingType, BuildingState buildingState, Coordinates coordinates) {
        if (coordinates == null) {
            coordinates = positionUtil.getNewBuildingCoordinates(buildingType);
        }

        log.info("# Creating new building " + buildingType + " at " + coordinates.toString());

        return buildingRepository.save(new Building(buildingType, buildingState, coordinates));
    }

    public void updateBuilding(Building building) {
        buildingRepository.save(building);
    }

    public List<Building> getAllBuildings() {
        return (List<Building>) buildingRepository.findAll();
    }

    public List<Building> getBuildingsByType(BuildingType buildingType) {
        return (List<Building>) buildingRepository.getAllByBuildingType(buildingType);
    }

    public Building getWarehouse() {
        return ((List<Building>) buildingRepository.getAllByBuildingType(BuildingType.WAREHOUSE)).get(0);
    }

    public void deleteAllBuildings() {
        buildingRepository.deleteAll();
    }
}
