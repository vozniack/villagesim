package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.EntityPosition;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.types.building.BuildingState;
import pl.kielce.tu.villageSim.types.building.BuildingType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Building implements EntityPosition {

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

    public Building(BuildingType buildingType, Coordinates coordinates) {
        this.positionX = coordinates.getX();
        this.positionY = coordinates.getY();
        this.size = coordinates.getSize();

        this.buildingType = buildingType;
        this.buildingState = BuildingState.NOT_BROKEN;
    }

    /* Custom setter */

    @Override
    public void setPosition(Integer positionX, Integer positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
