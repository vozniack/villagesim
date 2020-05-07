package pl.kielce.tu.villageSim.service.aStar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.util.components.PathFindingUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class PathFindingService {
    private final PathFindingUtil pathFindingUtil;
    private final Boolean diagonalMove = false;
    private Integer posXStart, posYStart;
    private Integer posXEnd, posYEnd;

    private Integer[][] world;

    private List<PathNode> openNodes;
    private List<PathNode> closedNodes;
    private List<PathNode> pathNodes;

    private PathNode currentNode;

    @Autowired
    public PathFindingService(PathFindingUtil pathFindingUtil) {
        this.pathFindingUtil = pathFindingUtil;
    }

    public List<PathNode> findPathTo(Unit unit, Integer posXEnd, Integer posYEnd) {
        System.out.println("## Starting A* algorithm");

        initAlgorithm(unit.getPositionX(), unit.getPositionY(), posXEnd, posYEnd);

        addNeighborsToOpenNodesList();

        while (!currentNode.getX().equals(posXEnd) || !currentNode.getY().equals(posYEnd)) {
            if (openNodes.isEmpty()) {
                return null;
            }

            currentNode = openNodes.get(0);
            openNodes.remove(0);
            closedNodes.add(currentNode);

            addNeighborsToOpenNodesList();
        }

        pathNodes.add(0, currentNode);

        while (!currentNode.getX().equals(posXStart) || !currentNode.getY().equals(posYStart)) {
            currentNode = currentNode.getParent();
            pathNodes.add(0, currentNode);
        }

        System.out.println("## Ending A* algorithm");

        return pathNodes;
    }

    private void initAlgorithm(Integer posXStart, Integer posYStart, Integer posXEnd, Integer posYEnd) {
        this.posXStart = posXStart;
        this.posYStart = posYStart;
        this.posXEnd = posXEnd;
        this.posYEnd = posYEnd;

        this.world = pathFindingUtil.prepareWorldArray();

        this.openNodes = new ArrayList<>();
        this.closedNodes = new ArrayList<>();
        this.pathNodes = new ArrayList<>();

        this.currentNode = new PathNode(this.posXStart, this.posYStart, 0d, 0d, null);

        this.closedNodes.add(this.currentNode);

        System.out.println("## Init A* algorithm");
    }

    private void addNeighborsToOpenNodesList() {
        PathNode newPathNode;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (!diagonalMove && x != 0 && y != 0) {
                    continue;
                }

                newPathNode = new PathNode(currentNode.getX() + x, currentNode.getY() + y, currentNode.getG(), countDistanceRelativeToCurrentNode(x, y), currentNode);

                if ((x != 0 || y != 0)
                        && currentNode.getX() + x >= 0 && currentNode.getX() + x < world.length
                        && currentNode.getY() + y >= 0 && currentNode.getY() + y < world[0].length
                        && world[currentNode.getX() + x][currentNode.getY() + y] != -1
                        && !findNeighborInList(openNodes, newPathNode) && !findNeighborInList(closedNodes, newPathNode)) {
                    newPathNode.setG(newPathNode.getParent().getG() + 1d);
                    newPathNode.setG(newPathNode.getG() + (double) (world[currentNode.getX() + x][currentNode.getY() + y]));

                    openNodes.add(newPathNode);
                }
            }
        }
    }

    private Double countDistanceRelativeToCurrentNode(int posX, int posY) {
        return diagonalMove
                ? Math.hypot(currentNode.getX() + posX - posXEnd, currentNode.getY() + posY - posYEnd)
                : (double) (Math.abs(currentNode.getX() + posX - posXEnd) + Math.abs(currentNode.getY() + posY - posYEnd));
    }

    private Boolean findNeighborInList(List<PathNode> pathNodes, PathNode pathNode) {
        return pathNodes.stream().anyMatch((neighborNode) -> (neighborNode.getX().equals(pathNode.getX())) && neighborNode.getY().equals(pathNode.getY()));
    }

}
