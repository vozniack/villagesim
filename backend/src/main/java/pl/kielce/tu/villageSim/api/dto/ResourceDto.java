package pl.kielce.tu.villageSim.api.dto;

import lombok.Data;
import pl.kielce.tu.villageSim.types.resource.ResourceType;

@Data
public class ResourceDto {

    private ResourceType resourceType;

    private Integer resourceAmount;
}
