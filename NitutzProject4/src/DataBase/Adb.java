package DataBase;

import java.sql.*;
import java.util.ArrayList;

public abstract class Adb {
    /**
     * @return a database connection
     */
    public Connection connect() {
        // SQLite connection string
        Connection conn = null;
        try {
            conn = DriverManager.getConnection( "jdbc:sqlite:Nituz.db" );
        } catch (SQLException e) {
            System.out.println( e.getMessage() );
        }
        return conn;
    }

    /**
     * implements a generic insert command with all inputs that are string
     * @param query
     * @param fields
     * @return
     */
    public boolean InsertComand(String query,String[] fields ){
        boolean success = false;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (int i = 1; i <=fields.length ; i++) {
                pstmt.setString(i, fields[i-1]);
            }
            pstmt.executeUpdate();
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    /**
     * implements a generic select command where all fields are string type
     * @param query
     * @param wantedColumn
     * @return
     */
    public ArrayList<String> selectCommand(String query, String[] wantedColumn){
        String record = null;
        ArrayList<String> records=new ArrayList<>(  );
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            // loop through the result set
            while (rs.next()) {
                record="";
                for (int i = 0; i <wantedColumn.length ; i++) {
                    record+=rs.getString( wantedColumn[i] ) + "\t";
                }
                records.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return records;
    }

    /**
     * implement a generic integer select commands where all fileds are integers
     * @param wantedColumn
     * @return
     */
    public int selectIntCommand(String wantedColumn){
        String query="SELECT vacationID,messageID FROM ids";
        String record = "";
        ArrayList<String> records=new ArrayList<>(  );
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(query)){
            // loop through the result set
            while (rs.next()) {
                record=rs.getInt( wantedColumn ) + "\t";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Integer.parseInt(record);
    }

    /**
     *
     * @param sql the sql command
     * @param columns the wanted columns in table
     * @param values the where values
     * @return
     */
    public ArrayList<String> selectWhereCommand(String sql, String[] columns,String[] values ) {
        String record = null;
        ArrayList<String> records=new ArrayList<>(  );
        try (Connection conn = this.connect();
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // set the value
            for (int i = 1; i <=values.length ; i++) {
                pstmt.setString(i,values[i-1]);
            }
            //
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                record="";
                for (int i = 0; i <columns.length ; i++) {
                    record+=rs.getString( columns[i] ) + "\t";
                }
                records.add(record);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return records;
    }

    /**
     * updating a generic table where all fields are integers
     * @param update the sql query
     * @param newValues the new values
     */
    public boolean update(String update, int[] newValues) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(update)) {

            // set the corresponding param
            for (int i = 1; i <=newValues.length ; i++) {
                pstmt.setInt(i, newValues[i-1]);
            }
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    /**
     * updating a generic table where all fields are strings
     * @param update the sql query
     * @param newValues the new values
     */
    public boolean update(String update, String[] newValues) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(update)) {

            // set the corresponding param
            for (int i = 1; i <=newValues.length ; i++) {
                pstmt.setString(i, newValues[i-1]);
            }
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * implement a generic update command where all inputs are string
     * @param update
     * @param newValue
     * @return
     */
    public boolean updateInt(String update, int newValue) {
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(update)) {

            // set the corresponding param
            pstmt.setInt(1, newValue);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

}
