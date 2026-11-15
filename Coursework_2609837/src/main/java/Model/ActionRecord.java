package Model;

//This is the object constructor class
public class ActionRecord {
    //These are the variables created and defaulted since they are class variables
    private String actionRecord;
    private Player player;
    private int station;

    //This is the constructor method that creates a ActionRecord object according to the given parameters
    public ActionRecord(String actionRecord, Player player, int station) {
        this.actionRecord = actionRecord;
        this.player = player;
        this.station = station;
    }

    //These are getter methods in order to access the object fields since they are set to private
    public String getActionRecord() { return this.actionRecord; }
    public Player getPlayer() { return this.player; }
    public int getStation() { return this.station; }
}
