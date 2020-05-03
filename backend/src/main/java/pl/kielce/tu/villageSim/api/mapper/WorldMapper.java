package pl.kielce.tu.villageSim.api.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.api.dto.WorldDto;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.service.entities.UnitService;

@Component
@RequiredArgsConstructor
public class WorldMapper {
    private final UnitService unitService;

    public WorldDto createWorldDto() {
        WorldDto worldDto = new WorldDto();

        worldDto.setSizeWidth(World.sizeWidth);
        worldDto.setSizeHeight(World.sizeHeight);

        worldDto.setUnits(unitService.getAllUnits());

        return worldDto;
    }
}
