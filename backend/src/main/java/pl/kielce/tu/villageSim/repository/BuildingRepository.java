package pl.kielce.tu.villageSim.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.types.building.BuildingType;

@Repository
public interface BuildingRepository extends CrudRepository<Building, Long> {

    Iterable<Building> getAllByBuildingType(BuildingType buildingType);
}
