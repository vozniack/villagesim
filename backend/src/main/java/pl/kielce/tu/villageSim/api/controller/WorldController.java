package pl.kielce.tu.villageSim.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.villageSim.service.generator.WorldGenerator;

@RestController
@RequestMapping("/api/world")
@RequiredArgsConstructor
public class WorldController {
    private final WorldGenerator worldGenerator;

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public void generateWorld() {
        worldGenerator.generateNewWorld();
    }
}
