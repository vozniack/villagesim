package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.types.structure.StructureType;

@Service
@Slf4j
@RequiredArgsConstructor
public class StructureService {
    private final StructureRepository structureRepository;

    public void createStructure(StructureType structureType, Integer structureLevel, Coordinates coordinates) {
        log.info("# Creating new structure " + structureType + " at " + coordinates.toString());

        structureRepository.save(new Structure(structureType, structureLevel, coordinates));
    }

    /* Support methods */

    public void clearStructuresNearWarehouse(Building building) {
        structureRepository.findAll().forEach(structure -> {
            if (!isFarFromWarehouse(structure, building)) {
                structureRepository.delete(structure);
            }
        });
    }

    private boolean isFarFromWarehouse(Structure structure, Building building) {
        int buildingPosX = building.getPositionX(), buildingPosY = building.getPositionY();
        int structurePosX = structure.getPositionX(), structurePosY = structure.getPositionY();
        int offsetSize = building.getSize();

        return buildingPosY - structurePosY > 4 + offsetSize || structurePosY - buildingPosY > 5 + offsetSize
                || buildingPosX - structurePosX > 4 + offsetSize || structurePosX - buildingPosX > 5 + offsetSize;
    }
}
