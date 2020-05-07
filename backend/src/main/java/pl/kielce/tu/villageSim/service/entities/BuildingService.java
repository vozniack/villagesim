package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.types.building.BuildingType;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public Building createBuilding(BuildingType buildingType, Coordinates coordinates) {
        log.info("# Creating new building " + buildingType + " at " + coordinates.toString());

        return buildingRepository.save(new Building(buildingType, coordinates));
    }

    public List<Building> getAllBuildings() {
        return (List<Building>) buildingRepository.findAll();
    }

    public List<Building> getBuildingsByType(BuildingType buildingType) {
        return (List<Building>) buildingRepository.getAllByBuildingType(buildingType);
    }

    public void deleteAllBuildings() {
        buildingRepository.deleteAll();
    }
}
