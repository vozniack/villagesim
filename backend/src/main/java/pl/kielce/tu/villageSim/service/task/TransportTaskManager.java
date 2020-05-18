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
import pl.kielce.tu.villageSim.types.resource.ResourceType;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class TransportTaskManager extends AbstractTaskManager {

    public TransportTaskManager(UnitService unitService, TaskService taskService, CommunicationService communicationService, WorldMapUtil worldMapUtil, PathFindingUtil pathFindingUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, TaskRepository taskRepository, UnitRepository unitRepository) {
        super(unitService, taskService, communicationService, worldMapUtil, pathFindingUtil, structureRepository, buildingRepository, taskRepository, unitRepository);
    }

    @Transactional
    public void prepareTask() {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.AWAIT_FOR_PATH, TaskType.TRANSPORT);

        tasks.forEach(task -> {
            Unit unit = task.getUnit();
            Building building = task.getBuilding();

            List<PathNode> pathNodes = pathFindingUtil.findPathTo(unit, building);

            if (pathNodes != null) {
                assignTaskToUnit(task, unit);
                changeUnitState(task, unit);
                createTaskPath(task, pathNodes);

                log.info("# Task " + task.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
            } else {
                deleteUnfinishedTask(task, unit);

                unit.setResourceType(null);
                unit.setResourceAmount(0);
                unitRepository.save(unit);

                log.info("# Task " + task.getTaskType().toString() + " failed - can't find a path");
            }
        });
    }

    @Transactional
    public void finalizeTask(Task task) {
        Unit unit = task.getUnit();

        finalizeTransport(unit.getResourceType(), unit.getResourceAmount());

        unit.setResourceType(null);
        unit.setResourceAmount(0);
        finalizeUnitState(unit);

        finalizeTaskState(task);

        communicationService.sendWorldState();
    }

    private void finalizeTransport(ResourceType resourceType, Integer resourceAmount) {
        switch (resourceType) {
            case WOOD:
                World.WOOD += resourceAmount;
                break;

            case ROCK:
                World.ROCK += resourceAmount;
                break;

            case FOOD:
                World.FOOD += resourceAmount;
                break;
        }
    }
}
