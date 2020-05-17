package pl.kielce.tu.villageSim.util;

import pl.kielce.tu.villageSim.model.entity.map.Unit;

public class UnitUtil {

    public static Integer setUnitRequirementResources(Unit unit) {
        int food = 0;

        switch (unit.getUnitType()) {
            case PEASANT:
                food = 8;
                break;
        }

        unit.setRequiredFood(food);

        return food;
    }
}
