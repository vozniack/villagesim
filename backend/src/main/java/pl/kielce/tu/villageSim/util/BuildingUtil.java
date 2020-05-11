package pl.kielce.tu.villageSim.util;

import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.types.building.BuildingType;

public class BuildingUtil {

    public static Integer getBuildingSize(BuildingType buildingType) {
        switch (buildingType) {
            case HOUSE:
                return 2;

            case SCHOOL:
            case INN:
                return 3;

            case WAREHOUSE:
                return 4;

            default:
                return 0;
        }
    }

    public static void setBuildingRequiredResources(Building building) {
        int wood = 0;
        int rock = 0;

        switch (building.getBuildingType()) {
            case SCHOOL:
                wood = 18;
                rock = 12;
                break;

            case INN:
                wood = 24;
                rock = 16;
                break;

            case HOUSE:
                wood = 12;
                rock = 6;
                break;

            default:
                break;
        }

        building.setRequiredWood(wood);
        building.setRequiredRock(rock);
    }
}
