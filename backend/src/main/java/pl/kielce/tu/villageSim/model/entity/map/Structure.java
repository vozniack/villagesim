package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.abstracts.Coordinates;
import pl.kielce.tu.villageSim.model.abstracts.Position;
import pl.kielce.tu.villageSim.types.structure.StructureType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Structure extends Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StructureType structureType;

    @NotNull
    private Integer structureLevel;

    public Structure(StructureType structureType, Position start, Position end) {
        super(start, end);

        this.structureType = structureType;
        this.structureLevel = 3;
    }
}
