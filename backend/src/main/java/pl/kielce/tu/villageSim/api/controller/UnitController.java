package pl.kielce.tu.villageSim.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.service.entities.UnitService;
import pl.kielce.tu.villageSim.types.unit.UnitType;
import pl.kielce.tu.villageSim.util.RandUtil;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Unit createUnit(@RequestParam UnitType unitType) {
        return unitService.createUnit(unitType, new Coordinates(RandUtil.generateRand(0, World.sizeWidth), RandUtil.generateRand(0, World.sizeHeight), 1));
    }
}
