package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.util.Position;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.types.structure.StructureType;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StructureService {
    private final StructureRepository structureRepository;

    public Structure createStructure(StructureType structureType, Position start, Position end) {
        log.info("# Creating new structure " + structureType + " at " + start.toString() + " / " + end.toString());

        return structureRepository.save(new Structure(structureType, start, end));
    }

    public List<Structure> getAllStructures() {
        return (List<Structure>) structureRepository.findAll();
    }

    public void deleteAllStructures() {
        structureRepository.deleteAll();
    }
}
