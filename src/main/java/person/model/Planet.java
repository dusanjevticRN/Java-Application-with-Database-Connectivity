package person.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Planet
{
    private int id;
    private String name;
    private String type;
    private float distanceFromStar;
    private float minTemperature;
    private float maxTemperature;
    private float oxygenLevel;
    private float otherGasLevel;
    private float gravityHeight;
    private float orbitSpeed;
    private int deathCount;
    private int yearsSpent;

    public Planet(int id, String name, String type, float distanceFromStar, float minTemperature, float maxTemperature,
                  float oxygenLevel, float otherGasLevel, float gravityHeight, float orbitSpeed,
                  int deathCount, int yearsSpent) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.distanceFromStar = distanceFromStar;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.oxygenLevel = oxygenLevel;
        this.otherGasLevel = otherGasLevel;
        this.gravityHeight = gravityHeight;
        this.orbitSpeed = orbitSpeed;
        this.deathCount = deathCount;
        this.yearsSpent = yearsSpent;
    }
}
