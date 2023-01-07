package uz.pdp.online;

import uz.pdp.online.project.enums.Role;
import uz.pdp.online.project.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Test {
    private int count;
    private static final int default_capacity=10;

    public Test() {
        this(default_capacity);
    }

    public Test(int count)  {
        this.count = count;
    }

    public static void main(String[] args) {

//        User user = new User(5, "admin", "admin", "admin", Role.ADMIN);
//        System.out.println(" admin: " + user);
//
//        User elshod = user;
//
//        System.out.println(" Elshod: " + elshod);
//
//        elshod.setName("elshod");
//
//        System.out.println(" Admin: " + user);
//        System.out.println(" Elshod new : " + elshod);

        ArrayList<String> list=new ArrayList<>(List.of("12","1234","324","234"));
        System.out.println(toString(list));

    }

    public static String toString(ArrayList<String> list) {
        StringJoiner sj=new StringJoiner(" %% ","[","]");
        for (String s : list) {
            sj.add(s);
        }
        return sj.toString();
    }
}
