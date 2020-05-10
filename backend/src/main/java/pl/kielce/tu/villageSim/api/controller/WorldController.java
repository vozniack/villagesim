package pl.kielce.tu.villageSim.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.villageSim.api.dto.WorldParametersDto;
import pl.kielce.tu.villageSim.api.mapper.WorldMapper;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.service.generator.WorldGenerator;

@RestController
@RequestMapping("/api/world")
@RequiredArgsConstructor
public class WorldController {
    private final WorldMapper worldMapper;
    private final WorldGenerator worldGenerator;

    @GetMapping("/parameters")
    public WorldParametersDto getCurrentWorldParameters() {
        return worldMapper.mapWorldParametersToDto();
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public void generateWorld(@RequestBody WorldParametersDto worldParametersDto) {
        worldMapper.mapWorldParametersFromDto(worldParametersDto);
        worldGenerator.generateNewWorld();
    }

    @PutMapping("/pause")
    public void pauseOrUnpause() {
        World.IS_ACTIVE = !World.IS_ACTIVE;
    }
}
