package pl.kielce.tu.villageSim.model.entity.map.interfaces;

public interface EntityPosition {

    Integer getPositionX();

    Integer getPositionY();

    Integer getSize();

    void setPosition(Integer positionX, Integer positionY);
}
