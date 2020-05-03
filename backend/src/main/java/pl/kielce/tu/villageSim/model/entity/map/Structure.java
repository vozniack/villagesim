package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.util.Position;
import pl.kielce.tu.villageSim.types.structure.StructureType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Structure {

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

    @NotNull
    private Integer positionStartX;

    @NotNull
    private Integer positionStartY;

    @NotNull
    private Integer positionEndX;

    @NotNull
    private Integer positionEndY;

    /* Custom constructor */

    public Structure(StructureType structureType, Position start, Position end) {
        this.positionStartX = start.getX();
        this.positionStartY = start.getY();
        this.positionEndX = end.getX();
        this.positionEndY = end.getY();

        this.structureType = structureType;
        this.structureLevel = 3;
    }
}
