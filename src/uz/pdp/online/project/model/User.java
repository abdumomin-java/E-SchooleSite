package uz.pdp.online.project.model;

import uz.pdp.online.project.enums.Role;

public class User extends AbstractUser{

    static int count=1;
    {
        count++;
    }

    public User() {
    }

    public User(int id, String name, String login, String password, Role role) {
        super(id, name, login, password, role);
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        User.count = count;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
