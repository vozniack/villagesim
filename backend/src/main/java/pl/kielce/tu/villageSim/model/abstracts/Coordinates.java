package pl.kielce.tu.villageSim.model.abstracts;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Represents the position on the board. For 1-cell units and structures start is equal to end.
 */

@AllArgsConstructor
@NoArgsConstructor
public abstract class Coordinates {

    protected Position start;

    protected Position end;
}
