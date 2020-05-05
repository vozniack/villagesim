package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.util.Position;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.util.RandUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StructureService {
    private final StructureRepository structureRepository;

    public void createStructure(StructureType structureType, Integer structureLevel, Position position) {
        log.info("# Creating new structure " + structureType + " at " + position.toString());

        structureRepository.save(new Structure(structureType, RandUtil.generateRand(1, 3), position));
    }

    public List<Structure> getAllStructures() {
        return (List<Structure>) structureRepository.findAll();
    }

    public void deleteAllStructures() {
        structureRepository.deleteAll();
    }

    /* Support methods */

    public void clearStructuresNearWarehouse(Building building) {
        getAllStructures().forEach(structure -> {
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
