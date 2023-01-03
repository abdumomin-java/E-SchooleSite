package uz.pdp.online.project;

import uz.pdp.online.project.console.ApplicationCon;
import uz.pdp.online.project.model.*;

public class Main {

    public static void main(String[] args) {

        User admin = new User(User.getCount(), "admin", "admin", "admin", Role.ADMIN);
        Storage.getStorage().abstractUsers.add(admin);

        User teacher = new User(User.getCount(), "Kozimjon", "kozimjon", "kozimjon", Role.TEACHER);
        Storage.getStorage().abstractUsers.add(teacher);

        School school = new School(School.getCount(), "21-maktab", "Uchqo`rg`on", false);
        Storage.getStorage().schoolList.add(school);

        Sinf sinf = new Sinf(Sinf.getCount(), "9-A sinf", school);
        Storage.getStorage().sinfList.add(sinf);

        Pupil pupil = new Pupil(Pupil.getCount(), "Alisher", "alisher", "alisher", Role.PUPIL, 23, sinf);
        Storage.getStorage().abstractUsers.add(pupil);

        Subject subject = new Subject(Subject.getCount(), "English");
        Storage.getStorage().subjectList.add(subject);

        ApplicationCon.getApplicationCon().startApp();

    }

}
