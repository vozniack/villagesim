package pl.kielce.tu.villageSim.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.types.structure.StructureType;

@Repository
public interface StructureRepository extends CrudRepository<Structure, Long> {

    Iterable<Structure> findAllByStructureType(StructureType structureType);

    Iterable<Structure> findAllByStructureTypeAndStructureLevelLessThan(StructureType structureType, Integer structureLevel);
}
