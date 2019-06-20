package Controller;

import Event.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.*;
import User.*;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class AddEventController {

    public javafx.scene.control.Button closeButton;
    public javafx.scene.control.TextField title;
    public javafx.scene.control.TextField organization;
    public javafx.scene.control.TextField description;
    private User user;
    //closing the current stage
    public void closeButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * set the current login user
      */
    public void setUser(User user){
        this.user=user;
    }

    /**
     * check the vacation input and create new vacation if the inputs are valid
     * @param actionEvent
     */
    public void addEvent(ActionEvent actionEvent) {
        String username=user.getUsername();
        String title=this.title.getText();
        String organiztion=organization.getText();
        String description=this.description.getText();
        if(checkInput(title,description,organiztion)) {
            Event event = new Event(title, description, organiztion);
            if(event!=null){
                Alert result=new Alert( Alert.AlertType.INFORMATION );
                result.setContentText( "Event created succesfuly" );
                result.showAndWait();
            }
            else{
                Alert result=new Alert( Alert.AlertType.INFORMATION );
                result.setContentText( "can't create the event" );
                result.showAndWait();
            }
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }

    }
    private boolean checkInput(String title,String description,String organization){
        Alert result=new Alert( Alert.AlertType.WARNING );
        result.setTitle( "wrong input" );
        if(title.length()<1){
            result.setContentText( "please enter a title" );
            result.showAndWait();
            return false;
        }
        else if(organization.length()<1){
            result.setContentText( "please enter an organization" );
            result.showAndWait();
            return false;
        }
        else if(!organization.equals("police")&&!organization.equals("firefighters")&&!organization.equals("MDA")){
            result.setContentText("organization can be one of those:\n police, firefighters, MDA" );
            result.showAndWait();
            return false;
        }
        else if(description.length()<1){
            result.setContentText( "please enter a description" );
            result.showAndWait();
            return false;
        }
        return true;
    }
    /**
     * @param num
     * @return true if a string is a number
     */
    private boolean isInteger(String num){
        try{
            Integer.parseInt( num );
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
    /**
     * @return true if a string is a time
     */
    private boolean isTime(String time){
        try {
            String number="";
            if(time.length()>5||!time.contains( ":" ))
                return false;
            for (int i = 0; i < time.length(); i++) {
                if(time.charAt( i )!=':')
                    number+=time.charAt( i );
                else{
                    int num=Integer.parseInt( number );
                    if(num<0||num>23)
                        return false;
                    number="";
                }
            }
            int num=Integer.parseInt( number );
            if(num<0||num>59)
                return false;
        }
        catch(Exception e){
            return false;
        }
        return true;
    }
    /**
     * @return true if a string is a double
     */
    private boolean isIDouble(String num){
        try{
            Double.parseDouble( num );
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
