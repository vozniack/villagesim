package pl.kielce.tu.villageSim.model.util;

import lombok.Data;

@Data
public class PathNode {

    /* Coordinates */

    private Integer x;

    private Integer y;

    /* Cost variables */

    private Double hValue;

    private Integer gValue;

    private Double fValue;

    /* Parent node */

    private PathNode parent;

    public PathNode(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}
