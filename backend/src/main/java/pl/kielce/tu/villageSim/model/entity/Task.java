package pl.kielce.tu.villageSim.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.types.task.TaskState;
import pl.kielce.tu.villageSim.types.task.TaskType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskType taskType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TaskState taskState;
}
