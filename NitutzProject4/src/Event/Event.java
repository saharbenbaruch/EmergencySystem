package Event;


import DataBase.EventTable;

import java.sql.*;

public class Event {
    private int eventID;
    private String title;
    private String description;
    public boolean isFinish;
    private Date startTime;
    private String organziation; //"YYYY-MM-DD HH:MM"


    /**
     * create a new Event on the DB with the user inputs
     * @param title
     * @param description
     * @param organization
     */
    public Event(String title, String description, String organization) {
        EventTable vte = new EventTable();
        this.eventID = (Math.random()*Integer.MAX_VALUE+ "").hashCode();
        if(this.eventID<0)
            this.eventID=this.eventID*-1;
        this.title = title;
        this.description = description;
        this.organziation = organization;
        isFinish = false;

        //add to dataBase
        vte.InsertCommand(this);
    }


    /**
     * get a vacation from the db by the vacation id
     * @param eventID
     */
    public Event(int eventID) {
        EventTable eventTableEntry = new EventTable();
        String querry = "SELECT id,title,startTime,isFinish,organization FROM events WHERE  id = ?;";
        try (Connection conn = eventTableEntry.connect();
             PreparedStatement pstmt  = conn.prepareStatement(querry)){
            pstmt.setInt(1,eventID);
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                this.eventID= rs.getInt("id");
                this.title=rs.getString("title");
                this.startTime=rs.getDate("startTime");
                this.isFinish=(rs.getInt("isFinish")!=0);
                this.organziation=rs.getString("organization");
                this.description=rs.getString("description");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Event(int id, String title, int isFinish, String organization, String description) {
        this.eventID=id;
        this.title=title;
        //this.startTime=startTime;
        this.isFinish=(isFinish!=0);
        this.organziation=organization;
        this.description=description;
    }

    /*getters*/
    public int getEventID() {
        return eventID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int isFinish() {
        if(isFinish)
            return 1;
        return 0;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getOrganziation() {
        return organziation;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "eventID: " );
        sb.append( eventID );
        sb.append(", ");
        sb.append("title: ");
        sb.append(title);
        sb.append(", is closed: ");
        sb.append(isFinish);
        sb.append("\n");
        sb.append("Description: ");
        sb.append(description);
        return sb.toString();
    }


    public void closeEvent() {
        EventTable et=new EventTable();
        int[] arr=new int[1];
        arr[0]=eventID;
        et.update("UPDATE events SET isFinish=1 WHERE id=?",arr);
    }
}
