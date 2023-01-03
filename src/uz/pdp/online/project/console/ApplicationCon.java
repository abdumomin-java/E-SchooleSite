package uz.pdp.online.project.console;

import uz.pdp.online.project.Storage;
import uz.pdp.online.project.impl.ApplicationOpe;
import uz.pdp.online.project.model.AbstractUser;
import uz.pdp.online.project.model.Pupil;
import uz.pdp.online.project.model.Role;

import java.util.Objects;
import java.util.Scanner;

public class ApplicationCon implements ApplicationOpe {

    static Scanner scannerText = new Scanner(System.in);

    @Override
    public void startApp() {

        System.out.println("\n @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ ");
        System.out.println(" @ @ @ @ @                          Lms.Online.tuit.uz                           @ @ @ @ @ ");
        System.out.println(" @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ ");

        while (true) {
            System.out.println("\n      # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # ");
            System.out.println("                       1-> Log in;  0-> Stop Program           ");
            String com = scannerText.nextLine();
            if (com.equals("1")) {
                signIN();
            } else if (com.equals("0")) {
                break;
            } else {
                AdminCon.getAdminCon().exceptionWrongAnswer();
            }
        }
    }

    private void signIN() {
        String login;
        while (true) {
            System.out.print(" Enter your login: ");
            login = scannerText.nextLine();
            if (Objects.isNull(login) || login.isBlank()) {
                System.out.println(" Login is null or empty ");
            } else {
                break;
            }
        }
        String password;
        while (true) {
            System.out.print(" Enter your password: ");
            password = scannerText.nextLine();
            if (Objects.isNull(password) || password.isBlank()) {
                System.out.println(" Password is null or empty ");
            } else {
                break;
            }
        }
        String finalLogin = login;
        String finalPassword = password;
        AbstractUser loginAndPassword = isLoginAndPassword(finalLogin, finalPassword);
        if (loginAndPassword == null) {
            System.out.println(" This User is not found ");
        } else {
            if (loginAndPassword.getRole().equals(Role.ADMIN)) {
                AdminCon.getAdminCon().startAdmin(loginAndPassword);
            } else if (loginAndPassword.getRole().equals(Role.TEACHER)) {
                TeacherCon.getTeacherCon().startTeacher(loginAndPassword);
            } else {
                Pupil pupil = (Pupil) Storage.getStorage().abstractUsers.stream()
                        .filter(abstractUser -> abstractUser.getLogin().equals(finalLogin)
                                && abstractUser.getPassword().equals(finalPassword))
                        .findFirst().orElse(null);
                if (pupil != null) {
                    PupilCon.getPupilCon().startPupil(pupil);
                } else {
                    System.out.println(" This Pupil is not found");
                }
            }
        }
    }

    public static AbstractUser isLoginAndPassword(String login, String password) {
        return Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getLogin().equals(login) &&
                        abstractUser.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    static ApplicationCon applicationCon;

    public static ApplicationCon getApplicationCon() {
        if (applicationCon == null) {
            applicationCon = new ApplicationCon();
        }
        return applicationCon;
    }
}
