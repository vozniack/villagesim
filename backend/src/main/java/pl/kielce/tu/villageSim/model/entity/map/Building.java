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
    private Integer positionStartX;

    @NotNull
    private Integer positionStartY;

    @NotNull
    private Integer positionEndX;

    @NotNull
    private Integer positionEndY;

    /* Custom constructor */

    public Building(BuildingType buildingType, Position start, Position end) {
        this.positionStartX = start.getX();
        this.positionStartY = start.getY();
        this.positionEndX = end.getX();
        this.positionEndY = end.getY();

        this.buildingType = buildingType;
        this.buildingState = BuildingState.NOT_BROKEN;
    }
}
