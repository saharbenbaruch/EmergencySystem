package DataBase;

import Event.Event;

import java.sql.*;
import java.util.ArrayList;

public class EventTable extends Adb {

    /**
     * insert a new event to the db
     * @param eventToDB
     * @return
     */
    public boolean InsertCommand(Event eventToDB) {
        boolean success = false;
        String sqlCommand = "INSERT INTO events(id,title,isFinish,organization,description)" +
                "VALUES (?,?,?,?,?);";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlCommand)) {
            pstmt.setInt(1, eventToDB.getEventID());
            pstmt.setString(2, eventToDB.getTitle());
            pstmt.setInt(3, eventToDB.isFinish());
            pstmt.setString(4, eventToDB.getOrganziation());
            pstmt.setString(5, eventToDB.getDescription());
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    /**
     * get all available events from the db
     * @return
     */
    public ArrayList<Event> getAllEvents(){
        ArrayList<Event>  res = new ArrayList<Event>();
        String sql="SELECT id,title,start_time,isFinish,organization,description FROM events;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println("here");
                Event currRecord = new Event(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("isFinish"),
                        rs.getString("organization"),
                        rs.getString("description"));
                System.out.println(currRecord.toString());
                res.add(currRecord);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }
    /**
     * get all available events from the db
     * @return
     */
    public ArrayList<Event> getAllOrganizationEvents(String organization){
        ArrayList<Event>  res = new ArrayList<Event>();
        String sql="SELECT id,title,start_time,isFinish,organization,description FROM events WHERE organization=?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,organization);
            // set the value
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                Event currRecord = new Event(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("isFinish"),
                        rs.getString("organization"),
                        rs.getString("description"));
                System.out.println(currRecord.toString());
                res.add(currRecord);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }


}