package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.service.task.BuildTaskManager;
import pl.kielce.tu.villageSim.service.task.MoveTaskManager;
import pl.kielce.tu.villageSim.service.task.StructureTaskManager;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final BuildTaskManager buildTaskManager;
    private final StructureTaskManager structureTaskManager;
    private final MoveTaskManager moveTaskManager;
    private final TaskRepository taskRepository;

    public void createBuildTask(Building building) {
        Task task = new Task(TaskType.BUILD);
        task.setBuilding(building);

        log.info("# Creating new building task with building ID: " + building.getId());

        taskRepository.save(task);
    }

    public void createLumberjackTask() {
        Task task = new Task(TaskType.CUT_TREE);

        Structure tree = structureTaskManager.findNearestStructure(StructureType.TREE);
        task.setStructure(tree);

        log.info("# Creating new lumber jack task with tree id: " + tree.getId());

        taskRepository.save(task);
    }

    public void createStonemasonTask() {
        Task task = new Task(TaskType.BREAK_STONE);

        Structure rock = structureTaskManager.findNearestStructure(StructureType.ROCK);
        task.setStructure(rock);

        log.info("# Creating new breaking rock task with rock id: " + rock.getId());

        taskRepository.save(task);
    }

    public void createMoveTask(Unit unit, Building building, Structure structure) {
        Task task = new Task(TaskType.MOVE);

        task.setUnit(unit);
        task.setTaskState(TaskState.AWAIT_FOR_PATH);

        if (building != null) {
            task.setBuilding(building);
        }

        if (structure != null) {
            task.setStructure(structure);
        }

        taskRepository.save(task);
    }

    public void finalizeTask(Task task) {
        switch (task.getTaskType()) {
            case BUILD:
                buildTaskManager.finalizeTask(task);
                break;

            case CUT_TREE:
            case BREAK_STONE:
                structureTaskManager.finalizeTask(task);
                break;

            case MOVE:
                moveTaskManager.finalizeTask(task);
                break;
        }
    }
}
