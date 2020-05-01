package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.abstracts.Coordinates;
import pl.kielce.tu.villageSim.model.abstracts.Position;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.building.BuildingType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Building extends Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BuildingType buildingType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BuildingState buildingState;

    public Building(BuildingType buildingType, Position start, Position end) {
        super(start, end);

        this.buildingType = buildingType;
        this.buildingState = BuildingState.NOT_BROKEN;
    }
}
