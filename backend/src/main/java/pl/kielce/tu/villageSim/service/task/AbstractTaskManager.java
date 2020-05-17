package pl.kielce.tu.villageSim.service.task;

import lombok.RequiredArgsConstructor;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.TaskRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;
import pl.kielce.tu.villageSim.util.components.WorldMapUtil;

@RequiredArgsConstructor
public abstract class AbstractTaskManager<Entity> implements TaskManager<Entity> {
    protected final UnitService unitService;
    protected final CommunicationService communicationService;

    protected final WorldMapUtil worldMapUtil;
    protected final PathFindingUtil pathFindingUtil;

    protected final StructureRepository structureRepository;
    protected final BuildingRepository buildingRepository;
    protected final TaskRepository taskRepository;
    protected final UnitRepository unitRepository;
}
