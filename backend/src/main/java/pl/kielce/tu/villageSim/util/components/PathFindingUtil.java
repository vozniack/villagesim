package pl.kielce.tu.villageSim.util.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.Position;
import pl.kielce.tu.villageSim.service.aStar.PathFindingService;
import pl.kielce.tu.villageSim.service.aStar.PathNode;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PathFindingUtil {
    private final PathFindingService pathFindingService;
    private final PositionUtil positionUtil;

    public List<PathNode> findPathTo(Unit unit, Position position) {

        for (int i = position.getPositionX() - 1; i < position.getPositionX() + position.getSize() + 1; i++) {
            for (int j = position.getPositionY() - 1; j < position.getPositionY() + position.getSize() + 1; j++) {

                if (!positionUtil.isOccupied(i, j, position) && isNotInCorner(i, j, position)) {
                    List<PathNode> pathNodes = pathFindingService.findPathTo(unit, i, j);

                    if (pathNodes != null) {
                        return pathNodes;
                    }
                }

            }
        }

        return null;
    }

    private boolean isNotInCorner(Integer positionX, Integer positionY, Position position) {
        return (positionX != position.getPositionX() - 1 && positionY != position.getPositionY() - 1)
                && (positionX != position.getPositionX() + 1 && positionY != position.getPositionY() - 1)
                && (positionX != position.getPositionX() - 1 && positionY != position.getPositionY() + 1)
                && (positionX != position.getPositionX() + 1 && positionY != position.getPositionY() + 1);
    }
}
