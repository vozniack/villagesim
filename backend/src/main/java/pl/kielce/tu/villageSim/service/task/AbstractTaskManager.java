package pl.kielce.tu.villageSim.service.task;

import lombok.RequiredArgsConstructor;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractTaskManager {
    protected final UnitService unitService;
    protected final TaskService taskService;
    protected final CommunicationService communicationService;

    protected final WorldMapUtil worldMapUtil;
    protected final PathFindingUtil pathFindingUtil;

    protected final StructureRepository structureRepository;
    protected final BuildingRepository buildingRepository;
    protected final TaskRepository taskRepository;
    protected final UnitRepository unitRepository;

    protected void assignTaskToUnit(Task task, Unit unit) {
        task.setUnit(unit);
        task.setTaskState(TaskState.ASSIGNED);
        taskRepository.save(task);
    }

    protected void changeUnitState(Task task, Unit unit) {
        unit.setUnitState(UnitState.BUSY);
        unit.setTask(task);
        unitRepository.save(unit);
    }

    protected void createTaskPath(Task task, List<PathNode> pathNodes) {
        World.unitPaths.put(task.getUnit().getId(), pathNodes);
    }

    protected void deleteUnfinishedTask(Task task, Unit unit) {
        finalizeUnitState(unit);

        task.setTaskState(TaskState.UNFINISHED);
        task.setUnit(null);
        taskRepository.save(task);
    }

    protected void finalizeUnitState(Unit unit) {
        unit.setTask(null);
        unit.setUnitState(UnitState.FREE);
        unitRepository.save(unit);
    }

    protected void finalizeTaskState(Task task) {
        task.setTaskState(TaskState.FINISHED);
        task.setUnit(null);
        taskRepository.save(task);
    }
}
