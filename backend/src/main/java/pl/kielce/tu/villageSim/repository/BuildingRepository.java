package pl.kielce.tu.villageSim.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kielce.tu.villageSim.model.entity.map.Building;
import pl.kielce.tu.villageSim.types.building.BuildingType;

import java.util.List;

@Repository
public interface BuildingRepository extends CrudRepository<Building, Long> {

    List<Building> findAll();

    List<Building> getAllByBuildingType(BuildingType buildingType);
}
