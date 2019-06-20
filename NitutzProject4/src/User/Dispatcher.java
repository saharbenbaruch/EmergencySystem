package User;

import DataBase.EventTable;
import Event.Event;

import java.util.ArrayList;

public class Dispatcher extends User {
    public Dispatcher(String username, String pass, String email, String name, String lastname, String organization) {
        super(username, pass, email, name, lastname, organization);
    }
    public Dispatcher(User user){
        super(user);
    }

    public Dispatcher(String userName, String password) {
        super(userName,password);
    }

    @Override
    public ArrayList<Event> selectEvents() {
        EventTable et=new EventTable();
        return et.getAllEvents();
    }
}
