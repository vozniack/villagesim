package pl.kielce.tu.villageSim.model.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates implements Comparable<Coordinates> {

    private Integer x;

    private Integer y;

    private Integer size;

    @Override
    public String toString() {
        return "[" + x + "][" + y + "] / [size: " + size + "]";
    }

    @Override
    public int compareTo(Coordinates coordinates) {
        return this.getSize().compareTo(coordinates.getSize());
    }
}
