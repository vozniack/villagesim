package pl.kielce.tu.villageSim.model.abstracts;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the position on the board. For 1-cell units and structures start is equal to end.
 */

@Data
@NoArgsConstructor
public abstract class Coordinates {

    protected Integer positionStartX;

    protected Integer positionEndX;

    protected Integer positionStartY;

    protected Integer positionEndY;

    public Coordinates(Integer positionStartX, Integer positionEndX, Integer positionStartY, Integer positionEndY) {
        this.positionStartX = positionStartX;
        this.positionEndX = positionEndX;
        this.positionStartY = positionStartY;
        this.positionEndY = positionEndY;
    }
}
