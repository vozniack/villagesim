package pl.kielce.tu.villageSim.service.task;

import pl.kielce.tu.villageSim.model.entity.Task;

public interface TaskManager<Entity> {

    void prepareTask();

    void finalizeTask(Task task);
}
