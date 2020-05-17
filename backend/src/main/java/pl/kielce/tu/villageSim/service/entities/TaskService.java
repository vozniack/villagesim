package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.service.task.TaskManager;
import pl.kielce.tu.villageSim.types.task.TaskType;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskManager<Building> buildingTaskManager;
    private final TaskRepository taskRepository;

    public void createBuildTask(Building building) {
        Task task = new Task(TaskType.BUILD);
        task.setBuilding(building);

        log.info("# Creating new building task with building ID: " + building.getId());

        taskRepository.save(task);
    }

    public void createLumberjackTask() {

    }

    public void createStonemasonTask() {

    }

    public void finalizeTask(Task task) {
        switch (task.getTaskType()) {
            case BUILD:
                buildingTaskManager.finalizeTask(task);
                break;
        }
    }
}
