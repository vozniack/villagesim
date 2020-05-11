package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.types.unit.UnitState;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UnitRepository unitRepository;
    private final BuildingRepository buildingRepository;
    private final StructureRepository structureRepository;

    public void createMoveTask(Unit unit, Building building, Structure structure) {
        Task task = new Task(TaskType.MOVE);

        task.setTaskState(TaskState.WAIT_FOR_PATH);

        if (building != null) {
            task.setBuilding(building);
        } else if (structure != null) {
            task.setStructure(structure);
        }

        task.setUnit(unit);
        taskRepository.save(task);

        unit.setUnitState(UnitState.BUSY);
        unitRepository.save(unit);
    }

    public void createBuildTask(Building building) {
        Task task = new Task(TaskType.BUILD);
        task.setBuilding(building);

        log.info("# Creating new building task with building ID: " + building.getId());

        taskRepository.save(task);
    }

    public void finalizeTask(Task task) {
        Unit unit = task.getUnit();

        unit.setUnitState(UnitState.FREE);
        unit.setTask(null);
        unitRepository.save(unit);

        finalizeTaskByType(task);

        taskRepository.delete(task);
    }

    private void finalizeTaskByType(Task task) {
        switch (task.getTaskType()) {
            case MOVE:
                break;

            case BUILD:
                Building building = task.getBuilding();

                building.setBuildingState(BuildingState.NOT_BROKEN);

                // #todo clear structures below building

                buildingRepository.save(building);

                break;

            default:
                break;
        }
    }

}
