package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.types.unit.UnitType;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;

    public Unit createUnit(UnitType unitType) {
        log.info("# Creating new unit: " + unitType);

        // #todo create unit started position (near to buildingType SCHOOL or something like that)

        return unitRepository.save(new Unit(unitType));
    }

    public void deleteAllUnits() {
        unitRepository.deleteAll();
    }
}
