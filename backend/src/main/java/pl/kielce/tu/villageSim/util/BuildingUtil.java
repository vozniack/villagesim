package pl.kielce.tu.villageSim.util;

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
}
