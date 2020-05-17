package pl.kielce.tu.villageSim.service.scheduled;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.SchedulerUtil;

@Service
@Slf4j
@AllArgsConstructor
public class UnitScheduledService {
    private final CommunicationService communicationService;
    private final UnitRepository unitRepository;

    @Scheduled(fixedDelay = 2048)
    public void manageUnitHealth() {
        if (SchedulerUtil.canPerform()) {
            unitRepository.findAll().forEach(unit -> {
                unit.setHealth(unit.getHealth() - RandUtil.generateRand(12, 24));

                if (unit.getHealth() < 75) {
                    if (World.FOOD >= 2) {
                        World.FOOD -= 2;
                        unit.setHealth(unit.getHealth() + RandUtil.generateRand(8, 16));
                    }
                }

                if (unit.getHealth() < 0 && unit.getTask() == null) {
                    if (World.FOOD >= 2) {
                        World.FOOD -= 2;
                        unit.setHealth(unit.getHealth() + RandUtil.generateRand(12, 18));
                    } else {
                        unitRepository.delete(unit);
                        log.info("Unit " + unit.getUnitType().toString() + " starved");
                    }
                } else {
                    unitRepository.save(unit);
                }

                communicationService.sendWorldState();

            });
        }
    }
}
