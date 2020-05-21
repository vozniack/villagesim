package pl.kielce.tu.villageSim.api.mapper;

import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.api.dto.ResourceDto;
import pl.kielce.tu.villageSim.types.resource.ResourceType;

@Component
public class ResourcesMapper {

    public ResourceDto createResourceDto(ResourceType resourceType, Integer resourceAmount) {
        ResourceDto resourceDto = new ResourceDto();

        resourceDto.setResourceType(resourceType);
        resourceDto.setResourceAmount(resourceAmount);

        return resourceDto;
    }
}
