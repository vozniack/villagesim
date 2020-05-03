package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.util.Position;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.building.BuildingType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* Properties */

    @Enumerated(EnumType.STRING)
    @NotNull
    private BuildingType buildingType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private BuildingState buildingState;

    /* Position */

    @NotNull
    private Integer positionX;

    @NotNull
    private Integer positionY;

    @NotNull
    private Integer size;

    /* Custom constructor */

    public Building(BuildingType buildingType, Position position) {
        this.positionX = position.getX();
        this.positionY = position.getY();
        this.size = position.getSize();

        this.buildingType = buildingType;
        this.buildingState = BuildingState.NOT_BROKEN;
    }
}
