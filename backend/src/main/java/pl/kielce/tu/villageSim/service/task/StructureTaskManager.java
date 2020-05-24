package pl.kielce.tu.villageSim.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.types.log.LogType;
import pl.kielce.tu.villageSim.types.resource.ResourceType;
import pl.kielce.tu.villageSim.types.structure.StructureType;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.CommunicationUtil;
import pl.kielce.tu.villageSim.util.RandUtil;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StructureTaskManager extends AbstractTaskManager {

    public StructureTaskManager(UnitService unitService, TaskService taskService, StructureService structureService, CommunicationService communicationService, WorldMapUtil worldMapUtil, PathFindingUtil pathFindingUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, TaskRepository taskRepository, UnitRepository unitRepository) {
        super(unitService, taskService, structureService, communicationService, worldMapUtil, pathFindingUtil, structureRepository, buildingRepository, taskRepository, unitRepository);
    }

    @Transactional
    public void prepareTask(TaskType taskType) {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.UNASSIGNED, taskType);

        tasks.forEach(task -> Optional.ofNullable(unitService.findNearestUnit(task.getStructure(), UnitState.FREE, UnitType.PEASANT))
                .ifPresent(unit -> {
                    Structure structure = task.getStructure();

                    List<PathNode> pathNodes = pathFindingUtil.findPathTo(unit, structure);

                    if (pathNodes != null) {
                        assignTaskToUnit(task, unit);
                        changeUnitState(task, unit);
                        createTaskPath(task, pathNodes);

                        communicationService.sendLog(CommunicationUtil.getAssignTaskMessage(task), null, LogType.INFO);
                        log.info("# Task " + task.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
                    } else {
                        deleteUnfinishedTask(task, unit);

                        communicationService.sendLog(CommunicationUtil.getCantFindPathMessage(task), null, LogType.ERROR);
                        log.info("# Task " + task.getTaskType().toString() + " failed - can't find a path");
                    }
                }));
    }

    @Transactional
    public void finalizeTask(Task task) {
        Structure structure = task.getStructure();

        structure.setStructureLevel(structure.getStructureLevel() - 1);

        if (structure.getStructureLevel() < 1) {
            structureRepository.delete(structure);
        }

        structureRepository.save(structure);

        Unit unit = task.getUnit();

        unit.setResourceAmount(8);
        unit.setResourceType(getResourceTypeByStructureType(task.getStructure().getStructureType()));

        communicationService.sendLog("Zadanie " + task.getTaskType().toString() + " zakończone powodzeniem - zebrano " + unit.getResourceAmount().toString() + " " + unit.getResourceType().toString(), null, LogType.SUCCESS);

        finalizeUnitState(unit, UnitState.BUSY);

        task.setStructure(null);
        finalizeTaskState(task);

        taskService.createTransportTask(unit, buildingRepository.getAllByBuildingType(BuildingType.WAREHOUSE).get(0));

        communicationService.sendWorldState();
    }

    public Structure findNearestStructure(StructureType structureType) {
        List<Structure> structures = structureRepository.findAllByStructureType(structureType);

        return structures.get(RandUtil.generateRand(0, structures.size() - 1));
    }

    private ResourceType getResourceTypeByStructureType(StructureType structureType) {
        switch (structureType) {
            case TREE:
                return ResourceType.WOOD;

            case ROCK:
                return ResourceType.ROCK;

            default:
                return null;
        }
    }
}
