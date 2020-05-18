package pl.kielce.tu.villageSim.service.scheduled;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.service.entities.TaskService;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.util.SchedulerUtil;

@Service
@AllArgsConstructor
public class BuildingScheduledService {
    private final BuildingRepository buildingRepository;
    private final TaskService taskService;

    @Scheduled(fixedDelay = 4096)
    public void farmScheduler() {
        if (SchedulerUtil.canPerform()) {
            buildingRepository.getAllByBuildingTypeAndIsAction(BuildingType.FARM, false).forEach(farm -> {
                if (farm.getBuildingState().equals(BuildingState.NOT_BROKEN)) {
                    farm.setActionCounter(farm.getActionCounter() + 1);

                    if (farm.getActionCounter() > 10) {
                        farm.setIsAction(false);
                        farm.setActionCounter(0);
                    }

                    if (farm.getActionCounter() > 3) {
                        farm.setIsAction(true);
                        farm.setActionCounter(0);

                        taskService.createHarvestTask(farm);
                    }

                    buildingRepository.save(farm);
                }
            });
        }
    }
}
