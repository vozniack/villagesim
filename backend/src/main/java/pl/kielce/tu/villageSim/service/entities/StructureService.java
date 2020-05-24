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

    public void clearStructuresUnderBuilding(Building building) {
        structureRepository.findAll().forEach(structure -> {
            if (isFarFromBuilding(structure, building, 0)) {
                structureRepository.delete(structure);
            }
        });
    }

    public void clearStructuresNearWarehouse(Building building) {
        structureRepository.findAll().forEach(structure -> {
            if (isFarFromBuilding(structure, building, 4)) {
                structureRepository.delete(structure);
            }
        });
    }

    private boolean isFarFromBuilding(Structure structure, Building building, Integer offsetSize) {
        int buildingPosX = building.getPositionX(), buildingPosY = building.getPositionY();
        int structurePosX = structure.getPositionX(), structurePosY = structure.getPositionY();
        int buildingSize = building.getSize();

        return buildingPosY - structurePosY <= offsetSize + buildingSize && structurePosY - buildingPosY <= offsetSize + buildingSize
                && buildingPosX - structurePosX <= offsetSize + buildingSize && structurePosX - buildingPosX <= offsetSize + buildingSize;
    }
}
