package pl.kielce.tu.villageSim.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.types.unit.UnitState;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Long> {

    Iterable<Unit> findAllByUnitState(UnitState unitState);
}
