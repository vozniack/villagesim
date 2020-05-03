package pl.kielce.tu.villageSim.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.service.generator.WorldGenerator;

@RestController
@RequestMapping("/api/world")
@RequiredArgsConstructor
public class WorldController {
    private final WorldGenerator worldGenerator;

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public void generateWorld(@RequestParam(required = false) Integer width, @RequestParam(required = false) Integer height) {
        worldGenerator.generateNewWorld(width, height);
    }

    @PutMapping("/pause")
    public void pauseOrUnpause() {
        World.isWorldReady = !World.isWorldReady;
    }
}
