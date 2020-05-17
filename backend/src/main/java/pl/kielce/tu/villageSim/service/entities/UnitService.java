package pl.kielce.tu.villageSim.service.entities;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.Position;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.communication.CommunicationService;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.MathUtil;
import pl.kielce.tu.villageSim.util.UnitUtil;
import pl.kielce.tu.villageSim.util.components.PositionUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnitService {
    private final CommunicationService communicationService;
    private final PositionUtil positionUtil;
    private final UnitRepository unitRepository;
    private final BuildingRepository buildingRepository;

    public Unit createUnit(UnitType unitType, Coordinates coordinates) {
        if (coordinates == null) {
            Building building = buildingRepository.getAllByBuildingType(BuildingType.SCHOOL).get(0);
            coordinates = positionUtil.getUnitCoordinatesNearPosition(building, 1, 1);
        }

        log.info("# Creating new unit " + unitType + " at " + coordinates.toString());

        Unit unit = new Unit(unitType, coordinates);
        UnitUtil.setUnitRequirementResources(unit);

        unitRepository.save(unit);

        communicationService.sendWorldState();

        return unit;
    }

    /* Support methods */

    public Unit findNearestUnit(Position position, UnitState unitState, UnitType unitType) {
        Unit selectedUnit = null;

        double distance = 0d;

        for (Unit unit : unitRepository.findAllByUnitStateAndUnitType(unitState, unitType)) {
            if (distance == 0d) {
                distance = MathUtil.countDistance(position, unit);
                selectedUnit = unit;
            }

            if (MathUtil.countDistance(position, unit) < distance) {
                selectedUnit = unit;
            }
        }

        return selectedUnit;
    }
}
