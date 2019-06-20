package User;

import DataBase.EventTable;
import Event.Event;

import java.util.ArrayList;

public class OrganizationUser extends User {
    public OrganizationUser(String username, String pass, String email, String name, String lastname, String organization) {
        super(username, pass, email, name, lastname, organization);
    }
    public OrganizationUser(User user){
        super(user);
    }

    @Override
    public ArrayList<Event> selectEvents() {
        EventTable et=new EventTable();
        return et.getAllOrganizationEvents(this.getOrganization());
    }
}
