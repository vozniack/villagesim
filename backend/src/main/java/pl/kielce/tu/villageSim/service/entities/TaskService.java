package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.types.task.TaskState;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> findTasksByState(TaskState taskState) {
        return taskRepository.findAllByTaskState(taskState);
    }

    public void deleteAllTasks() {
        taskRepository.deleteAll();
    }
}
