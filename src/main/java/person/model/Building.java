package person.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Building
{
    private int id;
    private String name;
    private int totalUnits;
    private int availableUnits;
    private String planetOrSatellite;

    public Building(int id, String name, int totalUnits, int availableUnits, String planetOrSatellite)
    {
        this.id = id;
        this.name = name;
        this.totalUnits = totalUnits;
        this.availableUnits = availableUnits;
        this.planetOrSatellite = planetOrSatellite;
    }

}