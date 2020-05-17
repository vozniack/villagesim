package pl.kielce.tu.villageSim.api.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.api.dto.WorldDto;
import pl.kielce.tu.villageSim.api.dto.WorldParametersDto;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.WorldParameters;
import pl.kielce.tu.villageSim.repository.BuildingRepository;
import pl.kielce.tu.villageSim.repository.StructureRepository;
import pl.kielce.tu.villageSim.repository.UnitRepository;

@Component
@RequiredArgsConstructor
public class WorldMapper {
    private final BuildingRepository buildingRepository;
    private final StructureRepository structureRepository;
    private final UnitRepository unitRepository;

    public WorldDto createWorldDto() {
        WorldDto worldDto = new WorldDto();

        worldDto.setSizeWidth(World.SIZE_WIDTH);
        worldDto.setSizeHeight(World.SIZE_HEIGHT);

        worldDto.setUnits(unitRepository.findAll());
        worldDto.setBuildings(buildingRepository.findAll());
        worldDto.setStructures(structureRepository.findAll());

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

        WorldParameters.WOOD = worldParametersDto.getWood();
        WorldParameters.ROCK = worldParametersDto.getRock();
        WorldParameters.FOOD = worldParametersDto.getFood();
        WorldParameters.GOLD = worldParametersDto.getGold();

        WorldParameters.PEASANTS = worldParametersDto.getPeasants();
        WorldParameters.WORKERS = worldParametersDto.getWorkers();
    }
}
