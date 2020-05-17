package pl.kielce.tu.villageSim.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.types.structure.StructureType;

import java.util.List;

@Repository
public interface StructureRepository extends CrudRepository<Structure, Long> {

    List<Structure> findAll();

    List<Structure> findAllByStructureType(StructureType structureType);

    List<Structure> findAllByStructureTypeAndStructureLevelLessThan(StructureType structureType, Integer structureLevel);

    List<Structure> findAllByStructureTypeAndStructureLevelGreaterThan(StructureType structureType, Integer structureLevel);
}
