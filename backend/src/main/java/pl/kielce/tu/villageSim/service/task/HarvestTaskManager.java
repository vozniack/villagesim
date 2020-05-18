package pl.kielce.tu.villageSim.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.types.resource.ResourceType;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class HarvestTaskManager extends AbstractTaskManager {

    public HarvestTaskManager(UnitService unitService, TaskService taskService, CommunicationService communicationService, WorldMapUtil worldMapUtil, PathFindingUtil pathFindingUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, TaskRepository taskRepository, UnitRepository unitRepository) {
        super(unitService, taskService, communicationService, worldMapUtil, pathFindingUtil, structureRepository, buildingRepository, taskRepository, unitRepository);
    }

    @Transactional
    public void prepareTask() {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.UNASSIGNED, TaskType.HARVEST);

        tasks.forEach(task -> Optional.ofNullable(unitService.findNearestUnit(task.getBuilding(), UnitState.FREE, UnitType.PEASANT)).ifPresent(unit -> {
            Building farm = task.getBuilding();

            List<PathNode> pathNodes = pathFindingUtil.findPathTo(unit, farm);

            if (pathNodes != null) {
                assignTaskToUnit(task, unit);
                changeUnitState(task, unit);
                createTaskPath(task, pathNodes);

                log.info("# Task " + task.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
            } else {
                deleteUnfinishedTask(task, unit);
                log.info("# Task " + task.getTaskType().toString() + " failed - can't find a path");

                taskService.createHarvestTask(farm);
            }
        }));
    }

    @Transactional
    public void finalizeTask(Task task) {
        Building farm = task.getBuilding();

        farm.setIsAction(false);
        farm.setActionCounter(0);
        buildingRepository.save(farm);

        Unit unit = task.getUnit();

        unit.setResourceAmount(8);
        unit.setResourceType(ResourceType.FOOD);

        finalizeUnitState(unit);
        finalizeTaskState(task);

        communicationService.sendWorldState();

        List<Building> buildings = buildingRepository.getAllByBuildingType(BuildingType.INN);

        if (buildings.size() > 0) {
            taskService.createTransportTask(unit, buildings.get(RandUtil.generateRand(0, buildings.size() - 1)));
        }
    }
}
