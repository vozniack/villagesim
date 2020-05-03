package pl.kielce.tu.villageSim.model.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {

    private Integer x;

    private Integer y;

    private Integer size;

    @Override
    public String toString() {
        return "[" + x + "][" + y + "] / [size: " + size + "]";
    }
}
