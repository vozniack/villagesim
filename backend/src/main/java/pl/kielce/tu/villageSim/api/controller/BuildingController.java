package pl.kielce.tu.villageSim.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.types.building.BuildingType;
import pl.kielce.tu.villageSim.util.RandUtil;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Building createBuilding(@RequestParam BuildingType buildingType) {
        return buildingService.createBuilding(buildingType, new Coordinates(RandUtil.generateRand(0, World.sizeWidth), RandUtil.generateRand(0, World.sizeHeight), 2));
    }
}
