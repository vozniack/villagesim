package pl.kielce.tu.villageSim.util.components;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.Position;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.util.BuildingUtil;
import pl.kielce.tu.villageSim.util.RandUtil;

@Component
public class PositionUtil {
    private final WorldMapUtil worldMapUtil;
    private final BuildingRepository buildingRepository;
    private final StructureRepository structureRepository;
    private final UnitRepository unitRepository;

    public PositionUtil(@Lazy WorldMapUtil worldMapUtil, BuildingRepository buildingRepository, StructureRepository structureRepository, UnitRepository unitRepository) {
        this.worldMapUtil = worldMapUtil;
        this.buildingRepository = buildingRepository;
        this.structureRepository = structureRepository;
        this.unitRepository = unitRepository;
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
                areCoordinatesOk = checkNeighbourhood(positionX, positionY, size, worldMap) && checkNeighbourhood(positionX, positionY, size, false);
            }

            if (counter > 32) {
                buildingCode++;
                counter = 0;
            }

            if (buildingCode > 4) {
                buildingCode = 2;
                counter = 0;
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

    public Coordinates getUnitCoordinatesNearPosition(Position position, Integer size, Integer distance) {
        Coordinates coordinates = new Coordinates();

        Integer positionX, positionY;

        boolean areCoordinatesOk;

        do {
            positionX = RandUtil.generateRand(position.getPositionX() - distance, position.getPositionX() + position.getSize() + distance);
            positionY = RandUtil.generateRand(position.getPositionY() - distance, position.getPositionY() + position.getSize() + distance);

            areCoordinatesOk = checkNeighbourhood(positionX, positionY, size, true);

        } while (!areCoordinatesOk);

        coordinates.setX(positionX);
        coordinates.setY(positionY);
        coordinates.setSize(size);

        return coordinates;
    }

    private boolean checkNeighbourhood(Integer positionX, Integer positionY, Integer size, Boolean structures) {
        boolean loopState = true;

        for (int x = positionX; x < positionX + size; x++) {
            for (int y = positionY; y < positionY + size; y++) {
                if (!isCellEmpty(x, y, structures)) {
                    loopState = false;
                }
            }
        }

        return loopState;
    }

    public boolean isCellEmpty(Integer positionX, Integer positionY, Boolean structures) {
        for (Building building : buildingRepository.findAll()) {
            if (isOccupied(positionX, positionY, building)) {
                return false;
            }
        }

        if (structures) {
            for (Structure structure : structureRepository.findAll()) {
                if (isOccupied(positionX, positionY, structure)) {
                    return false;
                }
            }
        }

        for (Unit unit : unitRepository.findAll()) {
            if (isOccupied(positionX, positionY, unit)) {
                return false;
            }
        }

        return true;
    }

    public boolean isOccupied(Integer positionX, Integer positionY, Position position) {
        return positionX >= position.getPositionX() && positionX <= position.getPositionX() + position.getSize() - 1
                && positionY >= position.getPositionY() && positionY <= position.getPositionY() + position.getSize() - 1;
    }

    public boolean isNearWarehouse(Integer positionX, Integer positionY, Integer distance) {
        Building building = buildingRepository.getAllByBuildingType(BuildingType.WAREHOUSE).get(0);

        return building.getPositionX() - positionX <= distance
                && building.getPositionY() - positionY <= distance
                && positionX - (building.getPositionX() + building.getSize()) <= distance
                && positionY - (building.getPositionY() + building.getSize()) <= distance;
    }

    public boolean isInsideMap(Integer positionX, Integer positionY) {
        return positionX < World.SIZE_WIDTH && positionY < World.SIZE_HEIGHT && positionX > -1 && positionY > -1;
    }
}
