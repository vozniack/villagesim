package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.MoveService;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.SchedulerUtil;
import pl.kielce.tu.villageSim.util.components.PositionUtil;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MoveScheduledService {
    private final UnitService unitService;
    private final MoveService moveService;
    private final TaskService taskService;
    private final PositionUtil positionUtil;
    private final UnitRepository unitRepository;
    private final BuildingRepository buildingRepository;

    @Scheduled(fixedRate = 512)
    public void moveFreeUnit() {
        if (SchedulerUtil.canPerform()) {
            unitRepository.findAllByUnitState(UnitState.FREE).forEach(unit -> {

                /* #todo return near to warehouse

                if (!positionUtil.isNearWarehouse(unit.getPositionX(), unit.getPositionY(), 4)) {
                    taskService.createMoveTask(unit, buildingRepository.getAllByBuildingType(BuildingType.WAREHOUSE).get(0), null);
                }
                */

                if (RandUtil.generateChance(0.1)) {
                    Integer positionX = unit.getPositionX() + RandUtil.generateRand(-1, 1);
                    Integer positionY = unit.getPositionY() + RandUtil.generateRand(-1, 1);

                    if (positionUtil.isCellEmpty(positionX, positionY, true) && positionUtil.isNearWarehouse(positionX, positionY, 4)) {
                        unit.setPosition(positionX, positionY);
                        unitService.updateUnit(unit);
                    }
                }

            });
        }
    }

    @Scheduled(fixedDelay = 512)
    public void moveBusyUnit() {
        if (SchedulerUtil.canPerform()) {
            unitRepository.findAllByUnitState(UnitState.BUSY).forEach(unit -> {

                List<PathNode> path = World.unitPaths.get(unit.getId());

                if (path != null) {

                    if (path.size() > 0) {
                        moveService.moveUnit(unit, path.get(0));
                        path.remove(0);

                        World.unitPaths.replace(unit.getId(), path);
                    } else {
                        World.unitPaths.remove(unit.getId());

                        taskService.finalizeTask(unit.getTask());
                    }
                }
            });
        }
    }


}
