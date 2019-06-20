package Model;

import Controller.Controller;
import User.*;
import Event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import java.util.Observable;

public class Model extends Observable implements ModelInt {
    Controller controller;
    private User user;
    private Event eventToBuy;

    public void setEventToTrade(Event eventToTrade) {
        this.eventToTrade = eventToTrade;
    }

    private Event eventToTrade;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Alert getExitMessage() {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.setTitle("exit");
        exit.setHeaderText("Are you sure you want to exit?");
        ButtonType yes = new ButtonType("yes");
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);
        exit.getButtonTypes().setAll(yes,no);
        return exit;
    }

    @Override
    public boolean createUser(String userName, String password, String email, String organization, String firstName, String lastName) {
        User newUser=UserFactory.createUser( userName,password,email,firstName,lastName,organization );
        return newUser.createNewUser();
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user=user;
    }

    @Override
    public Event getVacation() {
        return eventToBuy;
    }

    @Override
    public void setVacation(Event v) {
        eventToBuy =v;

    }

    @Override
    public User login(String userName, String password) {
        User user=new Dispatcher(userName,password);
        user=UserFactory.createUser(user);
        return user;
    }

    @Override
    public void loginSucces(User user) {
        controller.isConnected=true;
        this.user=user;
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean updateUser(User user,String email, String organization, String firstName, String lastName) {
        boolean success=user.updateUser( email,firstName,lastName,organization );
        if(success) {
            setChanged();
            notifyObservers();
        }
        return success;
    }

    @Override
    public boolean updatePassword(User user, String oldPass, String newPass) {
        return user.updatePW( oldPass,newPass );
    }

    @Override
    public Event getEventToTrade() {
        return this.eventToTrade;
    }

}
