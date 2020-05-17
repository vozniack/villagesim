package pl.kielce.tu.villageSim.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.Task;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.structure.StructureType;
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
public class StructureTaskManager extends AbstractTaskManager {

    public StructureTaskManager(UnitService unitService, CommunicationService communicationService, WorldMapUtil worldMapUtil, PathFindingUtil pathFindingUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, TaskRepository taskRepository, UnitRepository unitRepository) {
        super(unitService, communicationService, worldMapUtil, pathFindingUtil, structureRepository, buildingRepository, taskRepository, unitRepository);
    }

    @Transactional
    public void prepareTask(TaskType taskType) {
        List<Task> tasks = taskRepository.findAllByTaskStateAndTaskType(TaskState.UNASSIGNED, taskType);

        tasks.forEach(task -> Optional.ofNullable(unitService.findNearestUnit(task.getStructure(), UnitState.FREE, UnitType.PEASANT))
                .ifPresent(unit -> {
                    Structure structure = task.getStructure();

                    List<PathNode> pathNodes = pathFindingUtil.findPathTo(unit, structure);

                    if (pathNodes != null) {
                        task.setUnit(unit);
                        task.setTaskState(TaskState.ASSIGNED);
                        taskRepository.save(task);

                        unit.setUnitState(UnitState.BUSY);
                        unit.setTask(task);
                        unitRepository.save(unit);

                        World.unitPaths.put(task.getUnit().getId(), pathNodes);

                        log.info("# Task " + task.getTaskType().toString() + " assigned to unit " + unit.getUnitType().toString());
                    } else {
                        unit.setTask(null);
                        unit.setUnitState(UnitState.FREE);
                        unitRepository.save(unit);

                        task.setTaskState(TaskState.UNFINISHED);
                        task.setUnit(null);
                        taskRepository.save(task);

                        log.info("# Task " + task.getTaskType().toString() + " failed - can't find a path");
                    }
                }));
    }

    @Transactional
    public void finalizeTask(Task task) {
        Structure structure = task.getStructure();

        structure.setStructureLevel(structure.getStructureLevel() - 1);

        if (structure.getStructureType().equals(StructureType.TREE)) {
            World.WOOD += 8;
        } else if (structure.getStructureType().equals(StructureType.ROCK)) {
            World.ROCK += 8;
        }

        if (structure.getStructureLevel() < 1) {
            structureRepository.delete(structure);
        }

        structureRepository.save(structure);

        Unit unit = task.getUnit();
        unit.setTask(null);
        unit.setUnitState(UnitState.FREE);
        unitRepository.save(unit);

        task.setStructure(null);
        task.setTaskState(TaskState.FINISHED);
        task.setUnit(null);
        taskRepository.save(task);

        communicationService.sendWorldState();
    }

    public Structure findNearestStructure(StructureType structureType) {
        List<Structure> structures = structureRepository.findAllByStructureType(structureType);

        return structures.get(RandUtil.generateRand(0, structures.size() - 1));
    }
}
