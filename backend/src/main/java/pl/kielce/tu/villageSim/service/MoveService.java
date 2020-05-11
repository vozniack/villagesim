package pl.kielce.tu.villageSim.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.repository.UnitRepository;
import pl.kielce.tu.villageSim.service.aStar.PathNode;

@Service
@Slf4j
@RequiredArgsConstructor
public class MoveService {
    private final UnitRepository unitRepository;

    public void moveUnit(Unit unit, PathNode pathNode) {
        unit.setPositionX(pathNode.getX());
        unit.setPositionY(pathNode.getY());

        unitRepository.save(unit);
    }
}
