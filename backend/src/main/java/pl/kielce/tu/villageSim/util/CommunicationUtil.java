package pl.kielce.tu.villageSim.util;

import pl.kielce.tu.villageSim.model.entity.Task;

public class CommunicationUtil {

    public static String getBuildingTaskDescription(Task task) {
        return task.getTaskType().toString() + " " + task.getBuilding().getBuildingType().toString();
    }

    /* Default messages */

    public static String getAssignTaskMessage(Task task) {
        return "Zadanie " + task.getTaskType().toString() + " zostało przypisane do jednostki " + task.getUnit().getUnitType().toString();
    }

    public static String getCantFindPathMessage(Task task) {
        return "Zadanie " + task.getTaskType().toString() + " nie może zostać zakończone - nie znaleziono trasy";
    }
}
