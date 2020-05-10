package pl.kielce.tu.villageSim.util.components;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.EntityPosition;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.util.BuildingUtil;
import pl.kielce.tu.villageSim.util.RandUtil;

@Component
public class PositionUtil {
    private final StructureService structureService;
    private final BuildingService buildingService;
    private final UnitService unitService;
    private final WorldMapUtil worldMapUtil;

    public PositionUtil(@Lazy StructureService structureService, @Lazy BuildingService buildingService, @Lazy UnitService unitService, @Lazy WorldMapUtil worldMapUtil) {
        this.structureService = structureService;
        this.buildingService = buildingService;
        this.unitService = unitService;
        this.worldMapUtil = worldMapUtil;
    }

    public Coordinates getNewBuildingCoordinates(BuildingType buildingType) {
        Coordinates coordinates = new Coordinates();

        Integer positionX, positionY;
        Integer size = BuildingUtil.getBuildingSize(buildingType);

        Integer[][] worldMap = worldMapUtil.prepareBuildingsMap();

        boolean areCoordinatesOk = false;
        int counter = 0, buildingCode = 2;

        do {
            positionX = RandUtil.generateRand(0, World.SIZE_WIDTH - 1);
            positionY = RandUtil.generateRand(0, World.SIZE_HEIGHT - 1);

            counter++;

            if (worldMap[positionX][positionY] == buildingCode) {
                areCoordinatesOk = checkNeighbourhood(positionX, positionY, size, worldMap) && checkNeighbourhood(positionX, positionY, size);
            }

            if (counter > 10) {
                buildingCode++;
            }

            if (buildingCode > 5) {
                buildingCode = 1;
            }

        } while (!areCoordinatesOk);

        coordinates.setX(positionX);
        coordinates.setY(positionY);
        coordinates.setSize(size);

        return coordinates;
    }

    private boolean checkNeighbourhood(Integer positionX, Integer positionY, Integer size, Integer[][] worldMap) {
        boolean loopState = true;

        for (int x = positionX; x < positionX + size; x++) {
            for (int y = positionY; y < positionY + size; y++) {
                if (!isInsideMap(x, y)) {
                    loopState = false;
                    break;
                } else if (worldMap[x][y] == 1) {
                    loopState = false;
                }

            }
        }

        return loopState;
    }

    public Coordinates getUnitCoordinatesNearPosition(EntityPosition entityPosition, Integer size, Integer distance) {
        Coordinates coordinates = new Coordinates();

        Integer positionX, positionY;

        boolean areCoordinatesOk;

        do {
            positionX = RandUtil.generateRand(entityPosition.getPositionX() - distance, entityPosition.getPositionX() + entityPosition.getSize() + distance);
            positionY = RandUtil.generateRand(entityPosition.getPositionY() - distance, entityPosition.getPositionY() + entityPosition.getSize() + distance);

            areCoordinatesOk = checkNeighbourhood(positionX, positionY, size);

        } while (!areCoordinatesOk);

        coordinates.setX(positionX);
        coordinates.setY(positionY);
        coordinates.setSize(size);

        return coordinates;
    }

    private boolean checkNeighbourhood(Integer positionX, Integer positionY, Integer size) {
        boolean loopState = true;

        for (int x = positionX; x < positionX + size; x++) {
            for (int y = positionY; y < positionY + size; y++) {
                if (!isCellEmpty(x, y)) {
                    loopState = false;
                }
            }
        }

        return loopState;
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
        Building building = buildingService.getWarehouse();

        return building.getPositionX() - positionX <= distance
                && building.getPositionY() - positionY <= distance
                && positionX - (building.getPositionX() + building.getSize()) <= distance
                && positionY - (building.getPositionY() + building.getSize()) <= distance;
    }

    public boolean isInsideMap(Integer positionX, Integer positionY) {
        return positionX < World.SIZE_WIDTH && positionY < World.SIZE_HEIGHT && positionX > -1 && positionY > -1;
    }
}
