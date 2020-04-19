package pl.kielce.tu.villageSim.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.types.task.TaskState;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByTaskState(TaskState taskState);
}
