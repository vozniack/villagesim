package pl.kielce.tu.villageSim.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.villageSim.api.dto.MoveDto;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.aStar.PathFindingService;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class MoveController {
    private final PathFindingService pathFindingService;
    private final UnitRepository unitRepository;

    @GetMapping("move")
    public MoveDto getMovePath(@RequestParam Integer posX, @RequestParam Integer posY) {
        return new MoveDto(posX, posY, pathFindingService.findPathTo(unitRepository.findAll().get(0), posX, posY));
    }
}
