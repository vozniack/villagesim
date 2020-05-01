package pl.kielce.tu.villageSim.model.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position {

    private Integer x;

    private Integer y;

    @Override
    public String toString() {
        return "[" + x + "][" + y + "]";
    }
}
