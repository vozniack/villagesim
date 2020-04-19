package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.task.TaskState;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskScheduledService {
    private final TaskService taskService;
    private final UnitService unitService;

    @Scheduled(fixedDelay = 1000) // 1 second
    public void assignNewTasks() {
        List<Task> tasks = taskService.findTasksByState(TaskState.UNASSIGNED);

        if (tasks.isEmpty()) {
            log.debug("No unassigned tasks");
        }

        // #todo find all task with type UNASSIGNED, find nearest unit (basically without type, just an unit), assign task to unit
    }

    @Scheduled(fixedDelay = 1000) // 1 second
    public void doTasks() {
        List<Task> tasks = taskService.findTasksByState(TaskState.ASSIGNED);

        if (tasks.isEmpty()) {
            log.debug("No assigned tasks");
        }

        // #todo find unit with assigned task, do task - move to objective target or do what is needed
    }
}
