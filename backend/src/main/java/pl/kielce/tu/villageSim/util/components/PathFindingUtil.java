package pl.kielce.tu.villageSim.util.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.World;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.Position;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.service.aStar.PathFindingService;
import pl.kielce.tu.villageSim.service.aStar.PathNode;
import pl.kielce.tu.villageSim.util.MathUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PathFindingUtil {
    private final PathFindingService pathFindingService;
    private final PositionUtil positionUtil;
    private final WorldMapUtil worldMapUtil;

    public List<PathNode> findPathTo(Unit unit, Position position) {
        Integer[][] array = worldMapUtil.preparePositionBorderMap(position);

        Coordinates coordinates = findNearestCoordinates(unit, array);

        if (!positionUtil.isOccupied(coordinates.getX(), coordinates.getY(), position)) {
            return pathFindingService.findPathTo(unit, coordinates.getX(), coordinates.getY());
        }

        return null;
    }

    private Coordinates findNearestCoordinates(Unit unit, Integer[][] worldMap) {
        List<Coordinates> coordinates = new ArrayList<>();

        for (int x = 0; x < World.SIZE_WIDTH; x++) {
            for (int y = 0; y < World.SIZE_HEIGHT; y++) {
                if (worldMap[x][y] == 1) {
                    coordinates.add(new Coordinates(x, y, 0));
                }
            }
        }

        coordinates.forEach(coordinate -> coordinate.setSize((int) MathUtil.countDistance(unit.getPositionX(), unit.getPositionY(), coordinate.getX(), coordinate.getY())));

        Collections.sort(coordinates);

        return coordinates.get(0);
    }
}
