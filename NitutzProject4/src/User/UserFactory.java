package User;

public class UserFactory {
    public static User createUser(String username, String pass, String email, String name, String lastname, String organization){
        if(organization.equals("dispatcher"))
            return new Dispatcher(username, pass, email, name, lastname, organization);
        else
            return new OrganizationUser(username, pass, email, name, lastname, organization);
    }

    public static User createUser(User user) {
        if(user.getOrganization().equals("dispatcher"))
            return new Dispatcher(user);
        else
            return new OrganizationUser(user);
    }
}
