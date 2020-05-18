package pl.kielce.tu.villageSim.util.components;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;

@Component
@Slf4j
@RequiredArgsConstructor
public class UnitDeletingUtil {
    private final UnitRepository unitRepository;
    private final TaskRepository taskRepository;
    private final BuildingRepository buildingRepository;

    public void deleteUnit(Unit unit) {
        taskRepository.findAllByUnit(unit).forEach(task -> {
            task.setUnit(null);

            if (task.getTaskType().equals(TaskType.BUILD)) {
                Building building = task.getBuilding();

                task.setBuilding(null);
                taskRepository.save(task);

                buildingRepository.delete(building);
            }

            task.setTaskState(TaskState.UNFINISHED);

            taskRepository.save(task);
        });

        unitRepository.delete(unit);

        log.info("Unit " + unit.getUnitType().toString() + " starved");
    }
}
