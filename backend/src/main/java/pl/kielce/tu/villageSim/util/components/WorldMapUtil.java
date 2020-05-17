package pl.kielce.tu.villageSim.util.components;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.Position;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.types.building.BuildingType;

@Component
public class WorldMapUtil {
    private final PositionUtil positionUtil;
    private final StructureRepository structureRepository;
    private final BuildingRepository buildingRepository;
    private final UnitRepository unitRepository;

    public WorldMapUtil(@Lazy PositionUtil positionUtil, StructureRepository structureRepository, BuildingRepository buildingRepository, UnitRepository unitRepository) {
        this.positionUtil = positionUtil;
        this.structureRepository = structureRepository;
        this.buildingRepository = buildingRepository;
        this.unitRepository = unitRepository;
    }

    public Integer[][] prepareBuildingsMap() {
        Integer[][] array = new Integer[World.SIZE_WIDTH][World.SIZE_HEIGHT];

        fillEmptyArray(array, 0);

        fillBuildings(array, 4, 3);

        fillBuildingsByType(array, 3, BuildingType.INN);
        fillBuildingsByType(array, 3, BuildingType.SCHOOL);
        fillBuildingsByType(array, 2, BuildingType.WAREHOUSE);

        fillBuildings(array, 1, 2);

        return array;
    }

    public Integer[][] prepareWorldArray(Integer emptyCellValue, Integer blockedCellValue) {
        Integer[][] array = new Integer[World.SIZE_WIDTH][World.SIZE_HEIGHT];

        fillEmptyArray(array, emptyCellValue);

        fillStructures(array, blockedCellValue);
        fillUnits(array, blockedCellValue);
        fillBuildings(array, blockedCellValue, 0);

        return array;
    }

    public Integer[][] preparePositionBorderMap(Position position) {
        Integer[][] array = new Integer[World.SIZE_WIDTH][World.SIZE_HEIGHT];

        fillEmptyArray(array, 0);
        fillAllBuildingCells(array, (Building) position, 1, 1, true);
        fillAllBuildingCells(array, (Building) position, 0, 0, false);

        return array;
    }

    private void fillEmptyArray(Integer[][] array, Integer emptyCellValue) {
        for (int i = 0; i < World.SIZE_WIDTH; i++) {
            for (int j = 0; j < World.SIZE_HEIGHT; j++) {
                array[i][j] = emptyCellValue;
            }
        }
    }

    private void fillStructures(Integer[][] array, Integer blockedCellValue) {
        for (Structure structure : structureRepository.findAll()) {
            array[structure.getPositionX()][structure.getPositionY()] = blockedCellValue;
        }
    }

    private void fillUnits(Integer[][] array, Integer blockedCellValue) {
        for (Unit unit : unitRepository.findAll()) {
            array[unit.getPositionX()][unit.getPositionY()] = blockedCellValue;
        }
    }

    private void fillBuildings(Integer[][] array, Integer blockedCellValue, Integer border) {
        for (Building building : buildingRepository.findAll()) {
            fillAllBuildingCells(array, building, blockedCellValue, border, false);
        }
    }

    private void fillBuildingsByType(Integer[][] array, Integer blockedCellValue, BuildingType buildingType) {
        for (Building building : buildingRepository.getAllByBuildingType(buildingType)) {
            fillAllBuildingCells(array, building, blockedCellValue, 3, false);
        }
    }

    private void fillAllBuildingCells(Integer[][] array, Building building, Integer blockedCellValue, Integer border, Boolean checkCorners) {
        for (int i = -border; i < building.getSize() + border; i++) {
            for (int j = -border; j < building.getSize() + border; j++) {
                if (positionUtil.isInsideMap(building.getPositionX() + i, building.getPositionY() + j)) {
                    if (checkCorners) {
                        if (isNotInCorner(i, j, building)) {
                            array[building.getPositionX() + i][building.getPositionY() + j] = blockedCellValue;
                        }
                    } else {
                        array[building.getPositionX() + i][building.getPositionY() + j] = blockedCellValue;
                    }

                }
            }
        }
    }

    private boolean isNotInCorner(Integer positionX, Integer positionY, Position position) {
        return (positionX != position.getPositionX() - 1 && positionY != position.getPositionY() - 1)
                && (positionX != position.getPositionX() + 1 && positionY != position.getPositionY() - 1)
                && (positionX != position.getPositionX() - 1 && positionY != position.getPositionY() + 1)
                && (positionX != position.getPositionX() + 1 && positionY != position.getPositionY() + 1);
    }
}
