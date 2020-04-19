package pl.kielce.tu.villageSim.model.abstracts;

import lombok.Data;

@Data
public abstract class Position {

    private Integer[][] positionStart = new Integer[1][1]; // [x][y]

    private Integer[][] positionEnd = new Integer[1][1]; // [x][y]

    /*
     * For 1-cell units and structures positionStart = positionEnd;
     */
}
