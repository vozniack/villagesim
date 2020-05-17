package pl.kielce.tu.villageSim.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
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

    private Boolean informedAboutProblem;

    /* Relations */

    @OneToOne
    private Building building;

    @OneToOne
    private Structure structure;

    @OneToOne
    private Unit unit;

    public Task(TaskType taskType) {
        this.taskType = taskType;
        this.taskState = TaskState.UNASSIGNED;
        this.informedAboutProblem = false;
    }
}
