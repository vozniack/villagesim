package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public Unit createUnit(UnitType unitType, Coordinates coordinates) {
        log.info("# Creating new unit " + unitType + " at " + coordinates.toString());

        return unitRepository.save(new Unit(unitType, coordinates));
    }

    public void updateUnit(Unit unit) {
        unitRepository.save(unit);
    }

    public List<Unit> getAllUnits() {
        return (List<Unit>) unitRepository.findAll();
    }

    public List<Unit> getAllUnitsByUnitState(UnitState unitState) {
        return (List<Unit>) unitRepository.findAllByUnitState(unitState);
    }

    public void deleteAllUnits() {
        unitRepository.deleteAll();
    }
}
