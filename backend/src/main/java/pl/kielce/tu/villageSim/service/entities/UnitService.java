package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.abstracts.Position;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.types.unit.UnitType;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public Unit createUnit(UnitType unitType, Position start, Position end) {
        log.info("# Creating new unit " + unitType + " at " + start.toString() + " / " + end.toString());

        return unitRepository.save(new Unit(unitType, start, end));
    }

    public void deleteAllUnits() {
        unitRepository.deleteAll();
    }
}
