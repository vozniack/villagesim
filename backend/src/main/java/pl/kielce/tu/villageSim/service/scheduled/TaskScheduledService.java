package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.service.task.TaskManager;
import pl.kielce.tu.villageSim.util.SchedulerUtil;

@Component
@RequiredArgsConstructor
public class TaskScheduledService {
    private final TaskManager<Building> buildingTaskManager;

    @Scheduled(fixedDelay = 512)
    public void assignNewTasks() {
        if (SchedulerUtil.canPerform()) {
            buildingTaskManager.prepareTask();
        }
    }
}
