package Update;

import DataBase.UpdateTable;

import java.util.ArrayList;

public class Update {
    int eventID;
    String description;
    public Update(int eventID, String text) {
        this.eventID=eventID;
        description=text;
    }
    public Update(int eventID){
        this.eventID=eventID;
    }

    public void insertToDB() {
        UpdateTable ut=new UpdateTable();
        ut.InsertCommand(this);
    }

    public int getEventID() {
        return eventID;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Update> getAllUpdates() {
        UpdateTable ut=new UpdateTable();
        return ut.getAllEventUpdates(eventID);
    }

    @Override
    public String toString() {
        return "event ID: "+ this.eventID +"\n" + "description: " +this.description;
    }
}
