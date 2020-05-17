package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.Position;
import pl.kielce.tu.villageSim.model.util.Coordinates;
import pl.kielce.tu.villageSim.types.structure.StructureType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Structure implements Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* Properties */

    @Enumerated(EnumType.STRING)
    @NotNull
    private StructureType structureType;

    @NotNull
    private Integer structureLevel;

    /* Position */

    /* Position */

    @NotNull
    private Integer positionX;

    @NotNull
    private Integer positionY;

    @NotNull
    private Integer size;

    /* Custom constructor */

    public Structure(StructureType structureType, Integer structureLevel, Coordinates coordinates) {
        this.positionX = coordinates.getX();
        this.positionY = coordinates.getY();
        this.size = coordinates.getSize();

        this.structureType = structureType;
        this.structureLevel = structureLevel;
    }

    /* Custom setter */

    @Override
    public void setPosition(Integer positionX, Integer positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }
}
