package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.SchedulerUtil;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskScheduledService {
    private final UnitService unitService;
    private final PathFindingUtil pathFindingUtil;
    private final TaskRepository taskRepository;
    private final UnitRepository unitRepository;

    @Scheduled(fixedDelay = 1000)
    public void assignNewTasks() {
        if (SchedulerUtil.canPerform()) {
            List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.UNASSIGNED, TaskType.BUILD);

            tasks.forEach(buildTask -> Optional.ofNullable(unitService.findNearestUnit(buildTask.getBuilding(), UnitState.FREE, UnitType.PEASANT))
                    .ifPresent(unit -> {
                        buildTask.setUnit(unitService.findNearestUnit(buildTask.getBuilding(), UnitState.FREE, UnitType.PEASANT));
                        buildTask.setTaskState(TaskState.WAIT_FOR_PATH);
                        taskRepository.save(buildTask);

                        unit.setUnitState(UnitState.BUSY);
                        unit.setTask(buildTask);
                        unitRepository.save(unit);

                        log.info("# Task " + buildTask.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
                    }));
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void setPath() {
        if (SchedulerUtil.canPerform()) {
            setPathForMoveTasks();
            setPathForBuildingTasks();
        }
    }

    private void setPathForMoveTasks() {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.WAIT_FOR_PATH, TaskType.MOVE);

        tasks.forEach(task -> {
            List<PathNode> pathNodes = pathFindingUtil.findPathNodes(task.getUnit(), task.getBuilding());
            finalizePathSetting(task, pathNodes);
        });

    }

    private void setPathForBuildingTasks() {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.WAIT_FOR_PATH, TaskType.BUILD);

        tasks.forEach(buildTask -> {
            if (buildTask.getUnit() != null) {
                Unit unit = buildTask.getUnit();
                Building building = buildTask.getBuilding();

                if (World.WOOD >= building.getRequiredWood() && World.ROCK >= building.getRequiredRock()) {
                    List<PathNode> pathNodes = pathFindingUtil.findPathNodes(unit, building);
                    finalizePathSetting(buildTask, pathNodes);
                }
            }
        });
    }

    private void finalizePathSetting(Task task, List<PathNode> pathNodes) {
        if (pathNodes != null) {
            World.unitPaths.put(task.getUnit().getId(), pathNodes);

            task.setTaskState(TaskState.ASSIGNED);
        } else {
            task.setTaskState(TaskState.DELAYED);
        }

        taskRepository.save(task);
    }
}
