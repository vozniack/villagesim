package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.EntityPosition;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Unit implements EntityPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* Properties */

    @Enumerated(EnumType.STRING)
    @NotNull
    private UnitType unitType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UnitState unitState;

    /* Position */

    /* Position */

    @NotNull
    private Integer positionX;

    @NotNull
    private Integer positionY;

    @NotNull
    private Integer size;

    /* Custom constructor */

    public Unit(UnitType unitType, Coordinates coordinates) {
        this.positionX = coordinates.getX();
        this.positionY = coordinates.getY();
        this.size = coordinates.getSize();

        this.unitType = unitType;
        this.unitState = UnitState.FREE;
    }

    /* Custom setter */

    @Override
    public void setPosition(Integer positionX, Integer positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
