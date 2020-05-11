package pl.kielce.tu.villageSim.util;

import pl.kielce.tu.villageSim.model.entity.map.interfaces.EntityPosition;

public class MathUtil {

    public static double countDistance(EntityPosition position1, EntityPosition position2) {
        int dx = Math.abs(position2.getPositionX() - position1.getPositionX());
        int dy = Math.abs(position2.getPositionY() - position1.getPositionY());

        return Math.sqrt(2) * Math.min(dx, dy) + (Math.max(dx, dy) - Math.min(dx, dy));
    }
}
