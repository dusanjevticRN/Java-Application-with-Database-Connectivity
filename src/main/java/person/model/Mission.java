package person.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mission {
    private int id;
    private String startDate;
    private String endDate;
    private String missionStatus;
    private String missionName;
    private String missionLocation;

    public Mission(int id, String startDate, String endDate, String missionStatus, String missionName, String missionLocation) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.missionStatus = missionStatus;
        this.missionName = missionName;
        this.missionLocation = missionLocation;
    }
}
