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

    /* Position */

    @NotNull
    private Integer positionX;

    @NotNull
    private Integer positionY;

    @NotNull
    private Integer size;

    /* Custom constructor */

    public Structure(StructureType structureType, Integer structureLevel, Position position) {
        this.positionX = position.getX();
        this.positionY = position.getY();
        this.size = position.getSize();

        this.structureType = structureType;
        this.structureLevel = structureLevel;
    }
}
