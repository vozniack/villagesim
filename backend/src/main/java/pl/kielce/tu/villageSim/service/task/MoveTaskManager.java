package pl.kielce.tu.villageSim.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.World;
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
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class MoveTaskManager extends AbstractTaskManager {

    public MoveTaskManager(UnitService unitService, CommunicationService communicationService, WorldMapUtil worldMapUtil, PathFindingUtil pathFindingUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, TaskRepository taskRepository, UnitRepository unitRepository) {
        super(unitService, communicationService, worldMapUtil, pathFindingUtil, structureRepository, buildingRepository, taskRepository, unitRepository);
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
                task.setTaskState(TaskState.ASSIGNED);
                taskRepository.save(task);

                unit.setUnitState(UnitState.BUSY);
                unit.setTask(task);
                unitRepository.save(unit);

                World.unitPaths.put(task.getUnit().getId(), pathNodes);

                log.info("# Task " + task.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
            } else {
                unit.setTask(null);
                unit.setUnitState(UnitState.FREE);
                unitRepository.save(unit);

                task.setTaskState(TaskState.UNFINISHED);
                task.setUnit(null);
                taskRepository.save(task);

                log.info("# Task " + task.getTaskType().toString() + " failed - can't find a path");
            }
        });
    }

    public void finalizeTask(Task task) {
        Unit unit = task.getUnit();

        unit.setTask(null);
        unit.setUnitState(UnitState.FREE);
        unitRepository.save(unit);

        task.setTaskState(TaskState.FINISHED);
        task.setUnit(null);
        taskRepository.save(task);

        communicationService.sendWorldState();
    }
}
