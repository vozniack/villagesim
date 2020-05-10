package pl.kielce.tu.villageSim.util;

import pl.kielce.tu.villageSim.model.World;

public class SchedulerUtil {

    public static boolean canPerform() {
        return World.IS_ACTIVE;
    }
}
