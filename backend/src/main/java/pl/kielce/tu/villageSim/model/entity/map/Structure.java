package pl.kielce.tu.villageSim.model.entity.map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.kielce.tu.villageSim.model.abstracts.Position;
import pl.kielce.tu.villageSim.types.structure.StructureType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Structure extends Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StructureType structureType;

    @NotNull
    private Integer structureLevel;
}
