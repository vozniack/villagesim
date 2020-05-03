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

    @NotNull
    private Integer positionStartX;

    @NotNull
    private Integer positionStartY;

    @NotNull
    private Integer positionEndX;

    @NotNull
    private Integer positionEndY;

    /* Custom constructor */

    public Unit(UnitType unitType, Position start, Position end) {
        this.positionStartX = start.getX();
        this.positionStartY = start.getY();
        this.positionEndX = end.getX();
        this.positionEndY = end.getY();

        this.unitType = unitType;
        this.unitState = UnitState.FREE;
    }
}
