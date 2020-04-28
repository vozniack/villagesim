package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.types.building.BuildingType;

@Service
@Slf4j
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public Building createBuilding(BuildingType buildingType) {
        log.info("# Creating new building: " + buildingType);

        // #todo handle building position in parameter

        return buildingRepository.save(new Building(buildingType));
    }

    public void deleteAllBuildings() {
        buildingRepository.deleteAll();
    }
}
