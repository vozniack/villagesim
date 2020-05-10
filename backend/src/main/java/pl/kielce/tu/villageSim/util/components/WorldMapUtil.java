package pl.kielce.tu.villageSim.util.components;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.building.BuildingType;

@Component
public class WorldMapUtil {
    private final StructureService structureService;
    private final BuildingService buildingService;
    private final UnitService unitService;
    private final PositionUtil positionUtil;

    public WorldMapUtil(@Lazy StructureService structureService, @Lazy BuildingService buildingService, @Lazy UnitService unitService, @Lazy PositionUtil positionUtil) {
        this.structureService = structureService;
        this.buildingService = buildingService;
        this.unitService = unitService;
        this.positionUtil = positionUtil;
    }

    public Integer[][] prepareBuildingsMap() {
        Integer[][] array = new Integer[World.SIZE_WIDTH][World.SIZE_HEIGHT];

        fillEmptyArray(array, 0);

        fillBuildings(array, 5, 3);
        fillBuildingsByType(array, 4, 3, BuildingType.INN);
        fillBuildingsByType(array, 3, 3, BuildingType.SCHOOL);
        fillBuildingsByType(array, 2, 3, BuildingType.WAREHOUSE);

        fillBuildings(array, 1, 1);

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

    private void fillEmptyArray(Integer[][] array, Integer emptyCellValue) {
        for (int i = 0; i < World.SIZE_WIDTH; i++) {
            for (int j = 0; j < World.SIZE_HEIGHT; j++) {
                array[i][j] = emptyCellValue;
            }
        }
    }

    private void fillStructures(Integer[][] array, Integer blockedCellValue) {
        for (Structure structure : structureService.getAllStructures()) {
            array[structure.getPositionX()][structure.getPositionY()] = blockedCellValue;
        }
    }

    private void fillUnits(Integer[][] array, Integer blockedCellValue) {
        for (Unit unit : unitService.getAllUnits()) {
            array[unit.getPositionX()][unit.getPositionY()] = blockedCellValue;
        }
    }

    private void fillBuildings(Integer[][] array, Integer blockedCellValue, Integer border) {
        for (Building building : buildingService.getAllBuildings()) {
            fillAllBuildingCells(array, building, blockedCellValue, border);
        }
    }

    private void fillBuildingsByType(Integer[][] array, Integer blockedCellValue, Integer border, BuildingType buildingType) {
        for (Building building : buildingService.getBuildingsByType(buildingType)) {
            fillAllBuildingCells(array, building, blockedCellValue, border);
        }
    }

    private void fillAllBuildingCells(Integer[][] array, Building building, Integer blockedCellValue, Integer border) {
        for (int i = -border; i < building.getSize() + border; i++) {
            for (int j = -border; j < building.getSize() + border; j++) {
                if (positionUtil.isInsideMap(building.getPositionX() + i, building.getPositionY() + j)) {
                    array[building.getPositionX() + i][building.getPositionY() + j] = blockedCellValue;
                }
            }
        }
    }

}
