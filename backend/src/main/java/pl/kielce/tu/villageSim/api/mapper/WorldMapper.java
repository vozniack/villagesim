package pl.kielce.tu.villageSim.api.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.api.dto.WorldDto;
import pl.kielce.tu.villageSim.api.dto.WorldParametersDto;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.WorldParameters;
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

        worldDto.setSizeWidth(World.SIZE_WIDTH);
        worldDto.setSizeHeight(World.SIZE_HEIGHT);

        worldDto.setUnits(unitService.getAllUnits());
        worldDto.setBuildings(buildingService.getAllBuildings());
        worldDto.setStructures(structureService.getAllStructures());

        return worldDto;
    }

    public WorldParametersDto mapWorldParametersToDto() {
        WorldParametersDto worldParametersDto = new WorldParametersDto();

        worldParametersDto.setWidth(WorldParameters.SIZE_WIDTH);
        worldParametersDto.setHeight(WorldParameters.SIZE_HEIGHT);

        worldParametersDto.setTreeFactor(WorldParameters.TREE_FACTOR);
        worldParametersDto.setRockFactor(WorldParameters.ROCK_FACTOR);

        worldParametersDto.setWood(WorldParameters.WOOD);
        worldParametersDto.setRock(WorldParameters.ROCK);
        worldParametersDto.setFood(WorldParameters.FOOD);
        worldParametersDto.setGold(WorldParameters.GOLD);

        worldParametersDto.setPeasants(WorldParameters.PEASANTS);
        worldParametersDto.setWorkers(WorldParameters.WORKERS);

        return worldParametersDto;
    }

    public void mapWorldParametersFromDto(WorldParametersDto worldParametersDto) {
        WorldParameters.SIZE_WIDTH = worldParametersDto.getWidth();
        WorldParameters.SIZE_HEIGHT = worldParametersDto.getHeight();

        WorldParameters.TREE_FACTOR = worldParametersDto.getTreeFactor();
        WorldParameters.ROCK_FACTOR = worldParametersDto.getRockFactor();

        World.WOOD = worldParametersDto.getWood();
        World.ROCK = worldParametersDto.getRock();
        World.FOOD = worldParametersDto.getFood();
        World.GOLD = worldParametersDto.getGold();

        WorldParameters.PEASANTS = worldParametersDto.getPeasants();
        WorldParameters.WORKERS = worldParametersDto.getWorkers();
    }
}
