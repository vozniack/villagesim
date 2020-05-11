package pl.kielce.tu.villageSim.util.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.EntityPosition;
import pl.kielce.tu.villageSim.service.aStar.PathFindingService;
import pl.kielce.tu.villageSim.service.aStar.PathNode;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PathFindingUtil {
    private final PathFindingService pathFindingService;
    private final PositionUtil positionUtil;

    public List<PathNode> findPathNodes(Unit unit, EntityPosition entityPosition) {
        for (int i = entityPosition.getPositionX() - 1; i < entityPosition.getPositionX() + entityPosition.getSize() + 1; i++) {
            for (int j = entityPosition.getPositionY() - 1; j < entityPosition.getPositionY() + entityPosition.getSize() + 1; j++) {

                if (!positionUtil.isOccupied(i, j, entityPosition)) {
                    List<PathNode> pathNodes = pathFindingService.findPathTo(unit, i, j);

                    if (pathNodes != null) {
                        return pathNodes;
                    }
                }

            }
        }

        return null;
    }
}
