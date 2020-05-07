package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.SchedulerUtil;
import pl.kielce.tu.villageSim.util.components.PositionUtil;

@Component
@RequiredArgsConstructor
public class MoveScheduledService {
    private final UnitService unitService;
    private final PositionUtil positionUtil;

    @Scheduled(fixedDelay = 2048) // 2 seconds
    public void moveFreeUnit() {
        if (SchedulerUtil.canPerform()) {
            unitService.getAllUnitsByUnitState(UnitState.FREE).forEach(unit -> {
                if (RandUtil.generateChance(0.25)) {
                    Integer positionX = unit.getPositionX() + RandUtil.generateRand(-1, 1);
                    Integer positionY = unit.getPositionY() + RandUtil.generateRand(-1, 1);

                    if (positionUtil.isCellEmpty(positionX, positionY)) {
                        unit.setPosition(positionX, positionY);
                        unitService.updateUnit(unit);
                    }
                }
            });
        }
    }
}
