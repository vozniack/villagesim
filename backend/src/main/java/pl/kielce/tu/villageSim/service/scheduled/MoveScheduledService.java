package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.MoveService;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.SchedulerUtil;
import pl.kielce.tu.villageSim.util.components.PositionUtil;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MoveScheduledService {
    private final MoveService moveService;
    private final TaskService taskService;
    private final CommunicationService communicationService;
    private final PositionUtil positionUtil;
    private final UnitRepository unitRepository;

    @Scheduled(fixedRate = 512)
    public void moveFreeUnit() {
        if (SchedulerUtil.canPerform()) {
            unitRepository.findAllByUnitState(UnitState.FREE).forEach(unit -> {

                if (RandUtil.generateChance(0.1)) {
                    Integer positionX = unit.getPositionX() + RandUtil.generateRand(-1, 1);
                    Integer positionY = unit.getPositionY() + RandUtil.generateRand(-1, 1);

                    if (positionUtil.isCellEmpty(positionX, positionY, true) && positionUtil.isNearWarehouse(positionX, positionY, 4)) {
                        unit.setPosition(positionX, positionY);
                        unitRepository.save(unit);
                        communicationService.sendWorldState();
                    }
                }

            });
        }
    }

    @Scheduled(fixedDelay = 256)
    public void moveBusyUnit() {
        if (SchedulerUtil.canPerform()) {
            unitRepository.findAllByUnitState(UnitState.BUSY).forEach(unit -> {
                List<PathNode> path = World.unitPaths.get(unit.getId());

                if (path != null) {
                    if (path.size() > 0) {
                        moveService.moveUnit(unit, path.get(0));
                        communicationService.sendWorldState();

                        path.remove(0);

                        World.unitPaths.replace(unit.getId(), path);
                    } else {
                        World.unitPaths.remove(unit.getId());

                        if (unit.getTask() != null) {
                            taskService.finalizeTask(unit.getTask());
                        }
                    }
                }
            });
        }
    }


}
