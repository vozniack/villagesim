package pl.kielce.tu.villageSim.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.World;
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
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.log.LogType;
import pl.kielce.tu.villageSim.types.resource.ResourceType;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.CommunicationUtil;
import pl.kielce.tu.villageSim.util.ResourceUtil;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BuildTaskManager extends AbstractTaskManager {

    public BuildTaskManager(UnitService unitService, TaskService taskService, CommunicationService communicationService, WorldMapUtil worldMapUtil, PathFindingUtil pathFindingUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, TaskRepository taskRepository, UnitRepository unitRepository) {
        super(unitService, taskService, communicationService, worldMapUtil, pathFindingUtil, structureRepository, buildingRepository, taskRepository, unitRepository);
    }

    @Transactional
    public void prepareTask() {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.UNASSIGNED, TaskType.BUILD);

        tasks.forEach(task -> Optional.ofNullable(unitService.findNearestUnit(task.getBuilding(), UnitState.FREE, UnitType.PEASANT))
                .ifPresent(unit -> {
                    Building building = task.getBuilding();

                    if (World.WOOD >= building.getRequiredWood() && World.ROCK >= building.getRequiredRock()) {
                        List<PathNode> pathNodes = pathFindingUtil.findPathTo(unit, building);

                        if (pathNodes != null) {
                            assignTaskToUnit(task, unit);
                            changeUnitState(task, unit);
                            createTaskPath(task, pathNodes);

                            World.WOOD -= building.getRequiredWood();
                            World.ROCK -= building.getRequiredRock();

                            communicationService.sendResources(ResourceType.WOOD, ResourceUtil.getCurrentResource(ResourceType.WOOD));
                            communicationService.sendResources(ResourceType.ROCK, ResourceUtil.getCurrentResource(ResourceType.ROCK));

                            communicationService.sendLog("Zadanie " + CommunicationUtil.getBuildingTaskDescription(task) + " zostało przypisane do jednostki " + unit.getUnitType().toString(), null, LogType.INFO);
                            log.info("# Task " + task.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
                        } else {
                            buildingRepository.delete(task.getBuilding());

                            task.setBuilding(null);

                            deleteUnfinishedTask(task, unit);
                            communicationService.sendLog("Budynek " + task.getBuilding().getBuildingType().toString() + " nie może być zbudowany - nie udało się udnaleźć trasy", null, LogType.ERROR);
                            log.info("# Task " + task.getTaskType().toString() + " failed - can't find a path");
                        }
                    } else {
                        if (!task.getInformedAboutProblem()) {
                            task.setInformedAboutProblem(true);

                            communicationService.sendLog("Budynek " + task.getBuilding().getBuildingType().toString() + " nie może być zbudowany - za mało surowców", null, LogType.ERROR);
                            log.info("# Task " + task.getTaskType().toString() + " can't be finished - not enough resources");
                        }

                    }
                }));
    }

    @Transactional
    public void finalizeTask(Task task) {
        Building building = task.getBuilding();
        building.setBuildingState(BuildingState.NOT_BROKEN);
        buildingRepository.save(building);

        Unit unit = task.getUnit();
        finalizeUnitState(unit, UnitState.FREE);

        task.setBuilding(null);
        finalizeTaskState(task);

        // #todo delete all structures below

        communicationService.sendWorldState();
        communicationService.sendLog("Budynek " + building.getBuildingType().toString() + " zbudowany", null, LogType.SUCCESS);
    }
}
