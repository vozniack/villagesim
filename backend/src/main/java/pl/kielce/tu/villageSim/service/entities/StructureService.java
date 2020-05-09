package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.util.RandUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StructureService {
    private final StructureRepository structureRepository;

    public void createStructure(StructureType structureType, Integer structureLevel, Coordinates coordinates) {
        log.info("# Creating new structure " + structureType + " at " + coordinates.toString());

        structureRepository.save(new Structure(structureType, structureLevel, coordinates));
    }

    public void updateStructure(Structure structure) {
        structureRepository.save(structure);
    }

    public List<Structure> getAllStructures() {
        return (List<Structure>) structureRepository.findAll();
    }

    public List<Structure> getAllStructuresByType(StructureType structureType) {
        return (List<Structure>) structureRepository.findAllByStructureType(structureType);
    }

    public List<Structure> getAllTreesToGrow() {
        return (List<Structure>) structureRepository.findAllByStructureTypeAndStructureLevelLessThan(StructureType.TREE, 3);
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
