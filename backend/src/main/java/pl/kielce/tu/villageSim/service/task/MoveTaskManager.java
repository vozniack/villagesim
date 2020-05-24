package pl.kielce.tu.villageSim.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.Position;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.log.LogType;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.util.CommunicationUtil;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class MoveTaskManager extends AbstractTaskManager {

    public MoveTaskManager(UnitService unitService, TaskService taskService, StructureService structureService, CommunicationService communicationService, WorldMapUtil worldMapUtil, PathFindingUtil pathFindingUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, TaskRepository taskRepository, UnitRepository unitRepository) {
        super(unitService, taskService, structureService, communicationService, worldMapUtil, pathFindingUtil, structureRepository, buildingRepository, taskRepository, unitRepository);
    }

    @Transactional
    public void prepareTask() {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.AWAIT_FOR_PATH, TaskType.MOVE);

        tasks.forEach(task -> {
            Unit unit = task.getUnit();

            Building building = task.getBuilding();
            Structure structure = task.getStructure();

            Position position = building != null ? building : structure;

            List<PathNode> pathNodes = pathFindingUtil.findPathTo(unit, position);

            if (pathNodes != null) {
                assignTaskToUnit(task, unit);
                changeUnitState(task, unit);
                createTaskPath(task, pathNodes);

                communicationService.sendLog(CommunicationUtil.getAssignTaskMessage(task), null, LogType.INFO);
                log.info("# Task " + task.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
            } else {
                deleteUnfinishedTask(task, unit);

                communicationService.sendLog(CommunicationUtil.getCantFindPathMessage(task), null, LogType.ERROR);
                log.info("# Task " + task.getTaskType().toString() + " failed - can't find a path");
            }
        });
    }

    @Transactional
    public void finalizeTask(Task task) {
        Unit unit = task.getUnit();

        finalizeUnitState(unit, UnitState.FREE);
        finalizeTaskState(task);

        communicationService.sendWorldState();
    }
}
