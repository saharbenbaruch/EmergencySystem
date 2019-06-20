package Model;

import User.User;
import Event.Event;
import javafx.scene.control.Alert;

public interface ModelInt {
    User user=null;
    Alert getExitMessage();

    /**
     * create a new user
     * @param userName
     * @param password
     * @param email
     * @param organization users birthdate
     * @param firstName
     * @param lastName
     * @return true if user created
     */
    boolean createUser(String userName, String password, String email, String organization, String firstName, String lastName);
    User getUser();
    void setUser(User user);
    Event getVacation();
    void setVacation(Event v);
    /**
     * login to a user with username and password
     */
    User login(String userName, String password);

    /**
     * notify the controller about succesful login
     * @param user the logged user
     */
    public void loginSucces(User user);

    /**
     * update user information
     * @param user
     * @param email
     * @param organization
     * @param firstName
     * @param lastName
     * @return
     */
    boolean updateUser(User user,String email, String organization, String firstName, String lastName);

    /**
     * update the user password
     * @param user
     * @param oldPass
     * @param newPass
     * @return
     */
    boolean updatePassword(User user, String oldPass, String newPass);
    //get the wanted vacation to trade
    Event getEventToTrade();
    //set a vacation to trade
    void setEventToTrade(Event trade);
}
