package pl.kielce.tu.villageSim.service.scheduled;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.util.SchedulerUtil;

@Service
@AllArgsConstructor
public class BuildingScheduledService {
    private final BuildingRepository buildingRepository;

    @Scheduled(fixedDelay = 2048)
    public void drainUnitHealth() {
        if (SchedulerUtil.canPerform()) {
            buildingRepository.getAllByBuildingType(BuildingType.FARM).forEach(farm -> World.FOOD += 3);
        }
    }
}
