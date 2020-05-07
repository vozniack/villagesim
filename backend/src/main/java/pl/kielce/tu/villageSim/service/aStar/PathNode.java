package pl.kielce.tu.villageSim.service.aStar;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PathNode implements Comparable<PathNode> {

    private Integer x;

    private Integer y;

    private Double g;

    private Double h;

    private PathNode parent;

    @Override
    public int compareTo(PathNode pathNode) {
        return (int) ((this.g + this.h) - (pathNode.getG() + pathNode.getH()));
    }
}
