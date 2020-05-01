package pl.kielce.tu.villageSim.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.villageSim.service.generator.WorldGenerator;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WorldController {
    private final WorldGenerator worldGenerator;

    @PostMapping("/api/world/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public void generateWorld(@RequestParam(required = false) Integer width, @RequestParam(required = false) Integer height) {
        worldGenerator.generateNewWorld(width, height);
    }
}
