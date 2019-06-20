package DataBase;

import Event.Event;
import Update.Update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UpdateTable extends Adb {
    /**
     * insert a new update to the db
     * @param update
     * @return
     */
    public boolean InsertCommand(Update update) {
        boolean success = false;
        String sqlCommand = "INSERT INTO updates(event_id,description)" +
                "VALUES (?,?);";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sqlCommand)) {
            pstmt.setInt(1, update.getEventID());
            pstmt.setString(2, update.getDescription());
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }
    /**
     * get all event updates from the db
     * @return
     */
    public ArrayList<Update> getAllEventUpdates(int eventID){
        ArrayList<Update>  res = new ArrayList<Update>();
        String sql="SELECT event_id,description FROM updates WHERE event_id=?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,eventID);
            // set the value
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                Update currRecord = new Update(rs.getInt("event_id"),
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
