package pl.kielce.tu.villageSim.util;

import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.types.resource.ResourceType;

public class ResourceUtil {

    public static Integer getCurrentResource(ResourceType resourceType) {

        switch (resourceType) {
            case WOOD:
                return World.WOOD;

            case ROCK:
                return World.ROCK;

            case FOOD:
                return World.FOOD;

            default:
                return null;
        }
    }
}
