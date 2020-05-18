package pl.kielce.tu.villageSim.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class EatTaskManager extends AbstractTaskManager {

    public EatTaskManager(UnitService unitService, TaskService taskService, CommunicationService communicationService, WorldMapUtil worldMapUtil, PathFindingUtil pathFindingUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, TaskRepository taskRepository, UnitRepository unitRepository) {
        super(unitService, taskService, communicationService, worldMapUtil, pathFindingUtil, structureRepository, buildingRepository, taskRepository, unitRepository);
    }

    @Transactional
    public void prepareTask() {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.AWAIT_FOR_PATH, TaskType.EAT);

        tasks.forEach(task -> {
            Unit unit = task.getUnit();

            List<Building> buildings = buildingRepository.getAllByBuildingType(BuildingType.INN);

            if (buildings.size() > 0) {
                task.setBuilding(buildings.get(RandUtil.generateRand(0, buildings.size() - 1)));

                List<PathNode> pathNodes = pathFindingUtil.findPathTo(unit, task.getBuilding());

                if (pathNodes != null) {
                    assignTaskToUnit(task, unit);
                    changeUnitState(task, unit);
                    createTaskPath(task, pathNodes);

                    log.info("# Task " + task.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
                } else {
                    deleteUnfinishedTask(task, unit);
                    log.info("# Task " + task.getTaskType().toString() + " failed - can't find a path");
                }
            } else {
                deleteUnfinishedTask(task, unit);
                log.info("# Task " + task.getTaskType().toString() + " failed - can't find a INN building");
            }

        });
    }

    @Transactional
    public void finalizeTask(Task task) {
        Unit unit = task.getUnit();

        if (World.FOOD >= 3) {
            World.FOOD -= 3;
            unit.setHealth(100);
        }

        finalizeUnitState(unit, UnitState.FREE);
        finalizeTaskState(task);

        communicationService.sendWorldState();
    }
}
