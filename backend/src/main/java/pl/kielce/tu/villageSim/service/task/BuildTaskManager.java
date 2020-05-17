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
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BuildTaskManager extends AbstractTaskManager<Building> {

    public BuildTaskManager(UnitService unitService, CommunicationService communicationService, WorldMapUtil worldMapUtil, PathFindingUtil pathFindingUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, TaskRepository taskRepository, UnitRepository unitRepository) {
        super(unitService, communicationService, worldMapUtil, pathFindingUtil, structureRepository, buildingRepository, taskRepository, unitRepository);
    }

    @Override
    @Transactional
    public void prepareTask() {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.UNASSIGNED, TaskType.BUILD);

        tasks.forEach(task -> Optional.ofNullable(unitService.findNearestUnit(task.getBuilding(), UnitState.FREE, UnitType.PEASANT))
                .ifPresent(unit -> {
                    Building building = task.getBuilding();

                    if (World.WOOD >= building.getRequiredWood() && World.ROCK >= building.getRequiredRock()) {
                        List<PathNode> pathNodes = pathFindingUtil.findPathTo(unit, building);

                        if (pathNodes != null) {
                            task.setUnit(unit);
                            task.setTaskState(TaskState.ASSIGNED);

                            taskRepository.save(task);

                            unit.setUnitState(UnitState.BUSY);
                            unit.setTask(task);
                            unitRepository.save(unit);

                            World.unitPaths.put(task.getUnit().getId(), pathNodes);

                            World.WOOD -= building.getRequiredWood();
                            World.ROCK -= building.getRequiredRock();

                            log.info("# Task " + task.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
                        } else {
                            log.info("# Task " + task.getTaskType().toString() + " can't be accessed - no path");
                        }
                    } else {
                        if (!task.getInformedAboutProblem()) {
                            task.setInformedAboutProblem(true);
                            log.info("# Task " + task.getTaskType().toString() + " can't be finished - not enough resources");
                        }

                    }
                }));
    }

    @Override
    @Transactional
    public void finalizeTask(Task task) {
        Building building = task.getBuilding();
        building.setBuildingState(BuildingState.NOT_BROKEN);
        buildingRepository.save(building);

        Unit unit = task.getUnit();
        unit.setTask(null);
        unit.setUnitState(UnitState.FREE);
        unitRepository.save(unit);

        task.setTaskState(TaskState.FINISHED);
        task.setUnit(null);
        taskRepository.save(task);

        communicationService.sendWorldState();
    }
}
