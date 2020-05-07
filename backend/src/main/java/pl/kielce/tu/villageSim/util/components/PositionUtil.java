package pl.kielce.tu.villageSim.util.components;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.model.entity.map.Structure;
import pl.kielce.tu.villageSim.model.entity.map.Unit;
import pl.kielce.tu.villageSim.model.entity.map.interfaces.EntityPosition;
import pl.kielce.tu.villageSim.service.entities.BuildingService;
import pl.kielce.tu.villageSim.service.entities.StructureService;
import pl.kielce.tu.villageSim.service.entities.UnitService;

@Component
@AllArgsConstructor
public class PositionUtil {
    private final StructureService structureService;
    private final BuildingService buildingService;
    private final UnitService unitService;

    public boolean isCellEmpty(Integer positionX, Integer positionY) {
        for (Structure structure : structureService.getAllStructures()) {
            if (isOccupied(positionX, positionY, structure)) {
                return false;
            }
        }

        for (Building building : buildingService.getAllBuildings()) {
            if (isOccupied(positionX, positionY, building)) {
                return false;
            }
        }

        for (Unit unit : unitService.getAllUnits()) {
            if (isOccupied(positionX, positionY, unit)) {
                return false;
            }
        }

        return true;
    }

    private boolean isOccupied(Integer positionX, Integer positionY, EntityPosition entityPosition) {
        return positionX >= entityPosition.getPositionX() && positionX <= entityPosition.getPositionX() + entityPosition.getSize() - 1
                && positionY >= entityPosition.getPositionY() && positionY <= entityPosition.getPositionY() + entityPosition.getSize() - 1;
    }
}
