package pl.kielce.tu.villageSim.api.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.api.dto.StatisticsDto;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.types.resource.ResourceType;
import pl.kielce.tu.villageSim.util.ResourceUtil;

@Component
@AllArgsConstructor
public class StatisticsMapper {
    private final UnitRepository unitRepository;
    private final BuildingRepository buildingRepository;

    public StatisticsDto createStatisticsDto() {
        StatisticsDto statisticsDto = new StatisticsDto();

        statisticsDto.setWood(ResourceUtil.getCurrentResource(ResourceType.WOOD));
        statisticsDto.setRock(ResourceUtil.getCurrentResource(ResourceType.ROCK));
        statisticsDto.setFood(ResourceUtil.getCurrentResource(ResourceType.FOOD));

        statisticsDto.setUnits((int) unitRepository.count());
        statisticsDto.setBuildings((int) buildingRepository.count());

        return statisticsDto;
    }
}
