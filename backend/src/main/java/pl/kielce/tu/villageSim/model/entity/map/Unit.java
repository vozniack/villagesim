package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.abstracts.Coordinates;
import pl.kielce.tu.villageSim.model.abstracts.Position;
import pl.kielce.tu.villageSim.types.unit.UnitState;
import pl.kielce.tu.villageSim.types.unit.UnitType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Unit extends Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UnitType unitType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UnitState unitState;

    public Unit(UnitType unitType, Position start, Position end) {
        super(start, end);

        this.unitType = unitType;
        this.unitState = UnitState.FREE;
    }
}
