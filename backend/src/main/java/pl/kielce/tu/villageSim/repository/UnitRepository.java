package pl.kielce.tu.villageSim.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;

import java.util.List;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Long> {

    List<Unit> findAll();

    List<Unit> findAllByUnitState(UnitState unitState);

    List<Unit> findAllByUnitStateAndUnitType(UnitState unitState, UnitType unitType);
}
