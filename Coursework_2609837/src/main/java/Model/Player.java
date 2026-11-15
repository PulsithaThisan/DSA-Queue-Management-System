package Model;

//This is the object constructor class
public class Player {
    //These are the variables created and defaulted since they are class variables
    private final String id;
    private String name;
    private String level;
    private int stationAssigned;
    private boolean isActive;

    //This is the constructor method that creates a Player object according to the given parameters
    public Player (String id, String name, String level, int stationAssigned, boolean isActive) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.stationAssigned = stationAssigned;
        this.isActive = isActive;
    }

    //These are getter methods in order to access the object fields since they are set to private
    public String getId() { return id; }
    public String getName() { return name; }
    public String getLevel() { return level; }
    public int getStationAssigned() { return stationAssigned; }
    public Boolean getIsActive() { return isActive; }

    //These are setter methods in order to access the object fields since they are set to private
    public void setStationAssigned(int statingAssigned) { this.stationAssigned = statingAssigned; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
