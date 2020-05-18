package pl.kielce.tu.villageSim.service.scheduled;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.SchedulerUtil;
import pl.kielce.tu.villageSim.util.components.UnitDeletingUtil;

@Service
@Slf4j
@AllArgsConstructor
public class UnitScheduledService {
    private final CommunicationService communicationService;
    private final TaskService taskService;
    private final UnitDeletingUtil unitDeletingUtil;
    private final UnitRepository unitRepository;

    @Scheduled(fixedDelay = 8192)
    public void manageUnitHealth() {
        if (SchedulerUtil.canPerform()) {
            unitRepository.findAll().forEach(unit -> {
                unit.setHealth(unit.getHealth() - RandUtil.generateRand(6, 12));
                unitRepository.save(unit);

                if (unit.getHealth() < 50 && unit.getUnitState().equals(UnitState.FREE)) {
                    taskService.createEatTask(unit);
                }

                if (unit.getHealth() < 0) {
                    unitDeletingUtil.deleteUnit(unit);
                }

                communicationService.sendWorldState();

            });
        }
    }
}
