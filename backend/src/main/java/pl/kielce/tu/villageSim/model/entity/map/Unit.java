package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.util.Position;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Unit {

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

    public Unit(UnitType unitType, Position position) {
        this.positionX = position.getX();
        this.positionY = position.getY();
        this.size = position.getSize();

        this.unitType = unitType;
        this.unitState = UnitState.FREE;
    }
}
