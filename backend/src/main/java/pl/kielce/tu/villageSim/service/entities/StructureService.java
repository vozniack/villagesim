package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.abstracts.Position;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.types.structure.StructureType;

@Service
@Slf4j
@RequiredArgsConstructor
public class StructureService {
    private final StructureRepository structureRepository;

    public Structure createStructure(StructureType structureType, Position start, Position end) {
        log.info("# Creating new structure " + structureType + " at " + start.toString() + " / " + end.toString());

        return structureRepository.save(new Structure(structureType, start, end));
    }

    public void deleteAllStructures() {
        structureRepository.deleteAll();
    }
}
