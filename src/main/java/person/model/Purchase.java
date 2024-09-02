package person.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Purchase {
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String buildingName;
    private String vehicle;
    private String purchaseDate;
    private String travelDate;
    private String travelTime;

    public Purchase(int id, String firstName, String lastName, String username, String buildingName, String vehicle, String purchaseDate, String travelDate, String travelTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.buildingName = buildingName;
        this.vehicle = vehicle;
        this.purchaseDate = purchaseDate;
        this.travelDate = travelDate;
        this.travelTime = travelTime;
    }
}
