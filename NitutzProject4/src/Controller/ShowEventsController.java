package Controller;


import Model.ModelInt;
import User.User;
import Event.Event;
import Update.Update;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.geometry.Insets;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.beans.value.*;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class ShowEventsController {
    private Parent root;
    public javafx.scene.control.Button closeButton;
    public javafx.scene.control.TextField txtfld_update;
    public javafx.scene.control.CheckBox close;
    @FXML
    private TextField user_name;
    @FXML
    private TextField pass;
    public String userName="";
    public String password="";
    private Group group=null;
    private int height=70;
    private User user;
    private ShowEventsController controller;
    private boolean filt;
    private Event event;

    // set the controler model
    public void setMyModel(ModelInt myModel) {
        this.myModel = myModel;
    }

    private ModelInt myModel;

    public Stage getStage() {
        return stage;
    }

    private Stage stage;


    /**
     * set the controler who created this controle
      */
    private void setController(ShowEventsController svc) {
        controller=svc;
    }
    // close the specific window
    public void closeButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * show all the choosed events
     * @param events
     */
    public void setEvents(Collection<Event> events){
        if(filt) {
            height=70;
            group = null;
        }
        for (Event v: events) {
            addevents(v);
        }

        ScrollBar sc = new ScrollBar();
        sc.setLayoutX(900 - sc.getWidth());
        sc.setMin(0);
        sc.setLayoutY( 70 );
        sc.setLayoutX( 960 );
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(750);
        sc.setMax(750 );
        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                group.setLayoutY(-new_val.doubleValue());
            }
        });

        if(group!=null) {
            group.getChildren().add( sc );
            Scene scene = new Scene( group );
            stage.setScene( scene );
            stage.setMaxHeight( 800 );
        }
        else if(!filt){
            Scene scene=new Scene( root );
            stage.setScene( scene );
        }
        if(filt){
            filt=false;
            stage.show();
        }

    }

    /**
     * get all the available events
     */
    public void setEvents() {
        ArrayList<Event> events =user.selectEvents();
        setEvents(events);
    }

    /**
     * add a specific event to the events group
     * @param event event
     */
    private void addevents(Event event) {
        Label parameters=new Label( event.toString() );
        Button events = new Button("show updates");
        Button update = new Button("update event");
        events.setOnAction( e->{
            events.setDisable( true );
            update.setDisable( true );
            showInformation(event,events,update);
        } );
        update.setOnAction( e->{
            events.setDisable( true );
            update.setDisable( true );
            updateEvent(event,events,update);
        } );
        HBox hb= new HBox(  );
        hb.setSpacing( 10 );
        hb.setMargin( parameters, new Insets(20, 20, 20, 20) );
        hb.setMargin( events, new Insets(0, 0, 0, 0) );
        hb.setMargin( update, new Insets(0, 0, 0, 0) );
        hb.setLayoutX( 40 );
        hb.setLayoutY( height );
        hb.setPrefWidth( 900 );
        ObservableList list = hb.getChildren();
        list.addAll(parameters,events,update  );
        height+=120;
        if(group==null){
            group=new Group(root, hb );

        }
        else
            group=new Group( group,hb );
        group.getStylesheets().add("/View/MyStyle.css");

    }

    private void updateEvent(Event event, Button events, Button update) {
        openXml(event,"/View/updateEvent.fxml",false);
        events.setDisable(false);
        update.setDisable(false);
    }

    private void setEvent(Event event) {
        this.event=event;
    }

    private void setModel(ModelInt myModel) {
        this.myModel=myModel;
    }

    private void showInformation(Event event, Button events, Button update) {
        openXml(event,"/View/ShowUpdates.fxml",true);
        events.setDisable(false);
        update.setDisable(false);
    }
    private void openXml(Event event,String name,boolean showUpdates){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
            Parent root1 = (Parent) fxmlLoader.load();
            ShowEventsController update=fxmlLoader.getController();
            update.setUser( user );
            update.setModel( myModel );
            update.setEvent( event );
            Stage stage = new Stage();
            stage.initModality( Modality.APPLICATION_MODAL);
            stage.initStyle( StageStyle.UNDECORATED);
            stage.setTitle("show updates");
            boolean toShow=true;
            if(showUpdates) {
                update.setStage(stage, root1);
                toShow=update.setUpdates();
            }
            else{
                stage.setScene(new Scene(root1));
            }
            if(!toShow){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No updates for the event");
                alert.setContentText("there is no updates to show");
                alert.showAndWait();
            }
            else {
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    public void handle(WindowEvent windowEvent) {
                        windowEvent.consume();
                        stage.close();
                    }
                });
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean setUpdates() {
        Update update=new Update(event.getEventID());
        ArrayList<Update> updates=update.getAllUpdates();
        if(updates.size()==0)
            return false;
        setUpdates(updates);
        return true;

    }

    private void setUpdates(ArrayList<Update> updates) {
        if(filt) {
            height=70;
            group = null;
        }
        for (Update update: updates) {
            addUpdates(update);
        }
        ScrollBar sc = new ScrollBar();
        sc.setLayoutX(900 - sc.getWidth());
        sc.setMin(0);
        sc.setLayoutY( 70 );
        sc.setLayoutX( 960 );
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(750);
        sc.setMax(750 );
        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                group.setLayoutY(-new_val.doubleValue());
            }
        });
        if(group!=null) {
            group.getChildren().add( sc );
            Scene scene = new Scene( group );
            stage.setScene( scene );
            stage.setMaxHeight( 800 );
        }
        else if(!filt){
            Scene scene=new Scene( root );
            stage.setScene( scene );
        }
        if(filt){
            filt=false;
            stage.show();
        }

    }

    private void addUpdates(Update update) {
        Label parameters=new Label( update.toString() );
        HBox hb= new HBox(  );
        hb.setSpacing( 10 );
        hb.setMargin( parameters, new Insets(20, 20, 20, 20) );
        hb.setLayoutX( 40 );
        hb.setLayoutY( height );
        hb.setPrefWidth( 900 );
        ObservableList list = hb.getChildren();
        list.addAll(parameters);
        height+=120;
        if(group==null){
            group=new Group(root, hb );

        }
        else
            group=new Group( group,hb );
        group.getStylesheets().add("/View/MyStyle.css");

    }

    public void setStage(Stage stage, Parent root) {
        this.stage=stage;
        this.root=root;
    }

    /**
     * set the current loged in user
     * @param user
     */
    public void setUser(User user) {
        this.user=user;
    }

    public void newUpdate(ActionEvent actionEvent) {
        if(!event.isFinish&&close.isSelected())
            event.closeEvent();
        Update update=new Update(event.getEventID(),txtfld_update.getText());
        update.insertToDB();
        if(update!=null){
            Alert result=new Alert( Alert.AlertType.INFORMATION );
            result.setTitle( "Update added" );
            result.setContentText( "update added succesfuly" );
            result.showAndWait();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
        else{
            Alert result=new Alert( Alert.AlertType.WARNING );
            result.setTitle( "a problem occurred" );
            result.setContentText( "Failed to update the event" );
        }

    }
}
