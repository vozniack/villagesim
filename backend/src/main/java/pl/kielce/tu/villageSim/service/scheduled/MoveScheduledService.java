package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.util.RandUtil;

@Component
@RequiredArgsConstructor
public class MoveScheduledService {
    private final UnitService unitService;

    @Scheduled(fixedDelay = 1000) // 1 second
    public void moveFreeUnit() {
        unitService.getAllUnitsByUnitState(UnitState.FREE).forEach(unit -> {
            unit.setPositionX(unit.getPositionX() + RandUtil.generateRand(-1, 1));
            unit.setPositionY(unit.getPositionY() + RandUtil.generateRand(-1, 1));
            unitService.updateUnit(unit);
        });
    }
}
