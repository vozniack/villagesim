package pl.kielce.tu.villageSim.api.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.api.dto.WorldDto;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.UnitService;

@Component
@RequiredArgsConstructor
public class WorldMapper {
    private final UnitService unitService;
    private final BuildingService buildingService;
    private final StructureService structureService;

    public WorldDto createWorldDto() {
        WorldDto worldDto = new WorldDto();

        worldDto.setSizeWidth(World.sizeWidth);
        worldDto.setSizeHeight(World.sizeHeight);

        worldDto.setUnits(unitService.getAllUnits());
        worldDto.setBuildings(buildingService.getAllBuildings());
        worldDto.setStructures(structureService.getAllStructures());

        return worldDto;
    }
}
