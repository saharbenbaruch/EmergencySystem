package User;
import DataBase.*;
import Event.Event;

import java.util.ArrayList;

public abstract class User {
    private String username="";
    private String pass="";
    private String email="";
    private String firstname="";
    private String lastname="";
    private String organization="";
    private dbTableUsers db=new dbTableUsers();
    String[] columns={"username","password","email","firstname","lastname","organization"};

    /**
     * a constructor that get a user from the db by his username and password
     * @param userName
     * @param password
     */
    public User(String userName,String password){
        String[] values= {userName,password};
        ArrayList<String> user=db.selectWhereCommand( "SELECT username,password,email,firstname,lastname,organization FROM users WHERE username= ? AND password=?",columns,values );
        String selectedUser;
        if(user.size()>=1){
            selectedUser=user.get( 0 );
            addValues(selectedUser);
        }
    }

    /**
     * a constructor that get a user from the db by his username
     * @param userName
     */
    public User(String userName){
        String[] values={userName};
        ArrayList<String> user=db.selectWhereCommand( "SELECT username,password,email,firstname,lastname,organization FROM users WHERE username= ?",columns,values);
        String selectedUser;
        if(user.size()>=1){
            selectedUser=user.get( 0 );
            addValues(selectedUser);
        }
        //this.mailbox = Mailbox.recreateMailBox(this);
    }
    public User(User user){
        if(user!=null){
            this.username=user.getUsername();
            this.pass=user.getPass();
            this.firstname=user.firstname;
            this.lastname=user.lastname;
            this.organization=user.getOrganization();
            this.email=user.getEmail();
        }
    }
    public abstract ArrayList<Event> selectEvents();
    /**
     * a constructor that create a new user in the db
     * @param username
     * @param pass
     * @param email
     * @param name
     * @param lastname
     * @param organization
     */
    public User(String username, String pass, String email, String name, String lastname, String organization) {
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.firstname = name;
        this.lastname = lastname;
        this.organization = organization;
    }

    /**
     * @return the user username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @return the user password
     */
    public String getPass() {
        return pass;
    }
    /**
     * @return the user email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @return the user first name
     */
    public String getName() {
        return firstname;
    }

    /**
     * get a user lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @return  a user organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * cahnge a values after an update
     * @param selectedUser
     */
    private void addValues(String selectedUser) {
        String[] values=selectedUser.split( "\t" );
        username=values[0];
        pass=values[1];
        email=values[2];
        firstname=values[3];
        lastname=values[4];
        organization=values[5];
    }

    /**
     * update a user information
     * @param email
     * @param name
     * @param lastname
     * @param organization
     * @return
     */
    public boolean updateUser(String email,String name,String lastname,String organization){
        String[] newValues={email,name,lastname,organization,username};
         if(db.update( "UPDATE users SET email = ?, firstname = ?, lastname= ?, organization = ? WHERE username = ?", newValues)){
             this.email=email;
             this.firstname=name;
             this.lastname=lastname;
             this.organization=organization;
             return true;
         }
         return false;
    }
    //create a new user in the DB
    public boolean createNewUser(){
        String[] insert={username,pass,email,firstname,lastname,organization};
        return db.InsertComand( "INSERT INTO users(username,password,email,firstname,lastname,organization) VALUES(?,?,?,?,?,?)",insert );
    }
    //select a users frim the users by some fields
    public ArrayList<String> selectUser(String query, String[] fields){
        return db.selectCommand( query,fields );
    }

    /**
     * check if all user fields are ok
     */
    public boolean checkUser(){
        return username.length()>=1&&pass.length()>=1&&email.length()>=1&&firstname.length()>=1&&lastname.length()>=1&&organization.length()>=1;
    }

    /**
     * update a username passwrord
     * @param oldPass the user old password
     * @param newPass the user new password
     * @return true if the password succesfuly changed
     */
    public boolean updatePW(String oldPass, String newPass) {
        if(pass.equals( oldPass )){
            String[] newVals={newPass,username};
            if(db.update( "UPDATE users SET password = ? WHERE username = ?",newVals )){
                pass=newPass;
                return true;
            }
        }
        return false;
    }

}
