package pl.kielce.tu.villageSim.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.kielce.tu.villageSim.service.aStar.PathNode;

import java.util.List;

@Data
@AllArgsConstructor
public class MoveDto {

    private Integer x;

    private Integer y;

    private List<PathNode> path;
}
