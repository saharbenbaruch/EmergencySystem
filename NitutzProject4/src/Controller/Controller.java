package Controller;

import Model.Model;
import User.Dispatcher;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import Model.*;


public class Controller implements Observer {
    public javafx.scene.control.Button closeButton;
    public boolean isConnected;
    private Button logout;
    private Button showEvents;
    private Button addEvents;
    private Button profile;
    private Button register;
    private Button login;
    private Stage primaryStage;
    private Parent root;
    private ModelInt myModel;
    private ImageView image;
    public Controller(){
        createButtons();
    }

    /**
     * create the new buttons if a user is logged in
     */
    private void createButtons() {
        logout = new Button("Logout");
        logout.setOnAction( e -> logout(  ) );
        logout.setLayoutX(630);
        logout.setLayoutY(20);
        logout.setPrefWidth(150);
        showEvents =new Button( "show Events" );
        showEvents.setOnAction(e-> showEvents() );
        showEvents.setLayoutX(280);
        showEvents.setLayoutY(330);
        showEvents.setPrefWidth(250);
        showEvents.setPrefHeight( 50 );
        addEvents =new Button( "add new Event" );
        addEvents.setOnAction(e -> addEvent() );
        addEvents.setLayoutX(280);
        addEvents.setLayoutY(170);
        addEvents.setPrefWidth(250);
        addEvents.setPrefHeight( 50 );
        profile=new Button( "my profile" );
        profile.setOnAction( e -> updateUser(  ) );
        profile.setLayoutX(480);
        profile.setLayoutY(20);
    }


    /**
     * open the add vacation view
     */
    private void addEvent() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/AddEvent.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality( Modality.APPLICATION_MODAL);
            stage.initStyle( StageStyle.UNDECORATED);
            stage.setTitle("Add vacation");
            AddEventController view = fxmlLoader.getController();
            view.setUser( myModel.getUser( ));
            //view.setModel(myModel);
            stage.setScene(new Scene(root1));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent windowEvent) {
                    windowEvent.consume();
                    stage.close();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * log out a user
     */
    private void logout() {
        isConnected=false;
        myModel.setUser(null);
        Group group=new Group( image,root,register,login);
        Scene scene = new Scene(group, 800, 700);
        scene.getStylesheets().add("/View/MyStyle.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * resize the window
     * @param scene
     */
    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if(isConnected){
            Label label = new Label("Hello " + myModel.getUser().getName());
            label.setLayoutX(50);
            label.setLayoutY(20);
            Group group;
            if(myModel.getUser() instanceof Dispatcher)
                group=new Group(image, root,label,logout, addEvents, showEvents,profile );
            else
                group=new Group(image, root,label,logout, showEvents,profile );
            Scene scene = new Scene(group, 800, 700);
            scene.getStylesheets().add("/View/MyStyle.css");
            primaryStage.setScene(scene);
            primaryStage.show();

        }

    }

    /**
     * open the login view
     */
    public void login(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality( Modality.APPLICATION_MODAL);
            stage.initStyle( StageStyle.UNDECORATED);
            stage.setTitle("Login");
            LoginUserController view = fxmlLoader.getController();
            view.setModel(myModel);
            stage.setScene(new Scene(root1));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent windowEvent) {
                    windowEvent.consume();
                    stage.close();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * open the create user view
     */
    public void register(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/CreateUser.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality( Modality.APPLICATION_MODAL);
            stage.initStyle( StageStyle.UNDECORATED);
            stage.setTitle("Register");
            stage.setScene(new Scene(root1));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent windowEvent) {
                    windowEvent.consume();
                    stage.close();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * open the update user view for a logged in user
     */
    public void updateUser(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/UpdateUser.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            UpdateUserController update=fxmlLoader.getController();
            update.setText( myModel.getUser( ));
            update.setModel( myModel );
            Stage stage = new Stage();
            stage.initModality( Modality.APPLICATION_MODAL);
            stage.initStyle( StageStyle.UNDECORATED);
            stage.setTitle("update profile");
            stage.setScene(new Scene(root1));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent windowEvent) {
                    windowEvent.consume();
                    stage.close();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * open the show events view
     */
    public void showEvents() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/ShowEvents.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            ShowEventsController eventsController=fxmlLoader.getController();
            if(isConnected)
                eventsController.setUser(myModel.getUser());
            eventsController.setMyModel( myModel );
            Stage stage = new Stage();
            stage.initModality( Modality.APPLICATION_MODAL);
            stage.initStyle( StageStyle.UNDECORATED);
            stage.setTitle("show events");
            eventsController.setStage(stage,root1);
            eventsController.setEvents();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent windowEvent) {
                    windowEvent.consume();
                    stage.close();
                }
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * set the fxml objects from the main
     * @param image
     * @param register
     * @param login
     * @param root
     */
    public void setUIObjects(ImageView image,Button register, Button login, Parent root) {
        this.image=image;
        this.register=register;
        this.login=login;
        this.root=root;
    }

    /**
     * set the controller model
     * @param model
     */
    public void setModel(Model model) {
        myModel=model;
    }

    /**
     * get the current stage
     * @param primaryStage
     */
    public void setStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }

    /**
     * show an exit message
     * @param actionEvent
     */
    public void exit(ActionEvent actionEvent) {
        showExitMessage();
    }
    private void showExitMessage(){
        Alert exit = myModel.getExitMessage();
        Optional<ButtonType> result = exit.showAndWait();

    }

    /**
     * close the stage if the close button pressed
     * @param actionEvent
     */
    public void closeButtonAction(ActionEvent actionEvent) {
        //Stage stage = (Stage) closeButton.getScene().getWindow();
        primaryStage.close();
    }


}
