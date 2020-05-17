package pl.kielce.tu.villageSim.model.entity.map.interfaces;

public interface Position {

    Integer getPositionX();

    Integer getPositionY();

    Integer getSize();

    void setPosition(Integer positionX, Integer positionY);
}
