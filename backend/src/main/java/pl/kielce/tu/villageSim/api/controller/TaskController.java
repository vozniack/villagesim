package pl.kielce.tu.villageSim.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.types.task.TaskType;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public void createTask(@RequestParam TaskType taskType) {
        switch (taskType) {
            case CUT_TREE:
                taskService.createLumberjackTask();
                break;

            case BREAK_STONE:
                taskService.createStonemasonTask();
                break;
        }
    }
}
