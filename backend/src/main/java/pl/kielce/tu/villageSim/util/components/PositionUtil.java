package pl.kielce.tu.villageSim.util.components;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.EntityPosition;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.util.RandUtil;

@Component
@AllArgsConstructor
public class PositionUtil {
    private final StructureService structureService;
    private final BuildingService buildingService;
    private final UnitService unitService;

    public Coordinates getCoordinatesNear(EntityPosition entityPosition, Integer size, Integer distance) {
        Coordinates coordinates = new Coordinates();

        boolean areCoordinatesOk;

        Integer positionX, positionY;

        do {
            positionX = RandUtil.generateRand(entityPosition.getPositionX() - distance, entityPosition.getPositionX() + entityPosition.getSize() + distance);
            positionY = RandUtil.generateRand(entityPosition.getPositionY() - distance, entityPosition.getPositionY() + entityPosition.getSize() + distance);

            boolean loopState = true;

            for (int x = positionX; x < positionX + size; x++) {
                for (int y = positionY; y < positionY + size; y++) {
                    if (!isCellEmpty(x, y)) {
                        loopState = false;
                    }
                }
            }

            areCoordinatesOk = loopState;

        } while (!areCoordinatesOk);

        coordinates.setX(positionX);
        coordinates.setY(positionY);
        coordinates.setSize(size);

        return coordinates;
    }

    public boolean isCellEmpty(Integer positionX, Integer positionY) {
        for (Structure structure : structureService.getAllStructures()) {
            if (isOccupied(positionX, positionY, structure)) {
                return false;
            }
        }

        for (Building building : buildingService.getAllBuildings()) {
            if (isOccupied(positionX, positionY, building)) {
                return false;
            }
        }

        for (Unit unit : unitService.getAllUnits()) {
            if (isOccupied(positionX, positionY, unit)) {
                return false;
            }
        }

        return true;
    }

    private boolean isOccupied(Integer positionX, Integer positionY, EntityPosition entityPosition) {
        return positionX >= entityPosition.getPositionX() && positionX <= entityPosition.getPositionX() + entityPosition.getSize() - 1
                && positionY >= entityPosition.getPositionY() && positionY <= entityPosition.getPositionY() + entityPosition.getSize() - 1;
    }

    public boolean isNearWarehouse(Integer positionX, Integer positionY, Integer distance) {
        Building building = buildingService.getBuildingsByType(BuildingType.WAREHOUSE).get(0);

        return building.getPositionX() - positionX <= distance
                && building.getPositionY() - positionY <= distance
                && positionX - (building.getPositionX() + building.getSize()) <= distance
                && positionY - (building.getPositionY() + building.getSize()) <= distance;
    }
}
