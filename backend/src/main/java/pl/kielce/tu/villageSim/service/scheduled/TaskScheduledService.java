package pl.kielce.tu.villageSim.service.scheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.service.task.BuildTaskManager;
import pl.kielce.tu.villageSim.service.task.MoveTaskManager;
import pl.kielce.tu.villageSim.service.task.StructureTaskManager;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.util.SchedulerUtil;

@Component
@RequiredArgsConstructor
public class TaskScheduledService {
    private final BuildTaskManager buildTaskManager;
    private final StructureTaskManager structureTaskManager;
    private final MoveTaskManager moveTaskManager;

    @Scheduled(fixedDelay = 512)
    public void assignNewTasks() {
        if (SchedulerUtil.canPerform()) {
            buildTaskManager.prepareTask();

            structureTaskManager.prepareTask(TaskType.CUT_TREE);
            structureTaskManager.prepareTask(TaskType.BREAK_STONE);

            moveTaskManager.prepareTask();
        }
    }
}
