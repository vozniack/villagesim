package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.Position;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.MathUtil;

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

    /* Support methods */

    public Unit findNearestUnit(Position position, UnitState unitState, UnitType unitType) {
        Unit selectedUnit = null;

        double distance = 0d;

        for (Unit unit : unitRepository.findAllByUnitStateAndUnitType(unitState, unitType)) {
            if (distance == 0d) {
                distance = MathUtil.countDistance(position, unit);
                selectedUnit = unit;
            }

            if (MathUtil.countDistance(position, unit) < distance) {
                selectedUnit = unit;
            }
        }

        return selectedUnit;
    }
}
