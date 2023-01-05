package uz.pdp.online.project.console;

import uz.pdp.online.project.Storage;
import uz.pdp.online.project.enums.Role;
import uz.pdp.online.project.exceptions.WrongAnswer;
import uz.pdp.online.project.impl.AdminOpe;
import uz.pdp.online.project.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.time.LocalDateTime.parse;

public class AdminCon implements AdminOpe {

    static Scanner scannerText = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);

    @Override
    public void startAdmin(AbstractUser abstractUser) {
        System.out.println(" Assalomu alaykum. Hurmatli " + abstractUser.getName());
        label:
        while (true) {
            System.out.println(" # # # # # # # # # # # # # # # # # # # # # # # # #");
            System.out.println("""
                     1-> About Schools;
                     2-> About Teachers;
                     3-> About Pupils;
                     4-> About Subject;
                     5-> About Sinf;
                     6-> About Lesson;
                     0-> Go To Back;
                    """);
            String com = scannerText.nextLine();
            switch (com) {
                case "1" -> aboutSchool();
                case "2" -> aboutTeacher();
                case "3" -> aboutPupil();
                case "4" -> aboutSubject();
                case "5" -> aboutSinf();
                case "6" -> aboutLesson();
                case "0" -> {
                    break label;
                }
                default -> exceptionWrongAnswer();
            }
        }
    }

    private void aboutLesson() {
        label:
        while (true) {
            System.out.println("\n ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ");
            System.out.println("        1-> Add Lesson; 2-> Edit any Lesson; 3-> Show Lessons; 0-> Go To Back; ");
            String com = scannerText.nextLine();
            switch (com) {
                case "1":
                    addLesson();
                    break;
                case "2":
                    editLesson();
                    break;
                case "3":
                    showLesson();
                    break;
                case "0":
                    break label;
                default:
                    exceptionWrongAnswer();
                    break;
            }
        }
    }

    private void showLesson() {
        if (Storage.getStorage().lessonList.size() == 0) {
            System.out.println(" Lesson list is empty ");
        } else {
            Storage.getStorage().lessonList.forEach(lesson -> {
                System.out.println(lesson.toString());
                System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
            });
        }
    }

    private void editLesson() {
        if (Storage.getStorage().lessonList.size() == 0) {
            System.out.println(" Lesson list is empty ");
        } else {
            Lesson lesson = chosenLessonForEdit();
            if (lesson == null) {
                System.out.println(" Lesson is not found ");
            } else {
                editLessonParam(lesson);
            }
        }
    }

    private void editLessonParam(Lesson lesson) {
        label:
        while (true) {
            System.out.println(" ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ++ ");
            System.out.println(" 1-> class; 2-> subject; 3-> teacher; 4-> startTime; 5-> finishTime; 0-> Go To Back; ");
            String com = scannerText.nextLine();
            switch (com) {
                case "1":
                    editLessonSinf(lesson);
                    break;
                case "2":
                    editLessonSubject(lesson);
                    break;
                case "3":
                    editLessonTeacher(lesson);
                    break;
                case "4":
                    editLessonStartTime(lesson);
                    break;
                case "5":
                    editLessonFinishTime(lesson);
                    break;
                case "0":
                    break label;
                default:
                    exceptionWrongAnswer();
                    break;
            }
        }
    }

    private void editLessonFinishTime(Lesson lesson) {
        LocalDateTime finishTime2 = getTime("new finish");

        Duration between = Duration.between(lesson.getStartTime(), finishTime2);
        if (between.getSeconds() <= 3600) {
            System.out.println(" Darsning boshlanish va tushash vaqti orasidagi farq minimum 1 soat bolishi shart.\n Qayta kiriting! ");
        } else {
            lesson.setFinishTime(finishTime2);
            Storage.getStorage().lessonList.set((lesson.getId() - 1), lesson);
            System.out.println("Successfully edit Lesson's finishTime ");
        }
    }

    private void editLessonStartTime(Lesson lesson) {
        LocalDateTime finishTime1 = getTime("new start");

        Duration between = Duration.between(finishTime1, lesson.getFinishTime());
        if (between.getSeconds() <= 3600) {
            System.out.println(" Darsning boshlanish va tushash vaqti orasidagi farq minimum 1 soat bolishi shart.\n Qayta kiriting! ");
        } else {
            lesson.setStartTime(finishTime1);
            Storage.getStorage().lessonList.set((lesson.getId() - 1), lesson);
            System.out.println(" Successfully edit Lesson's startTime ");
        }
    }

    private void editLessonTeacher(Lesson lesson) {
        List<AbstractUser> teacherList = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getRole().equals(Role.TEACHER)).toList();
        User teacher = chosenTeacherForAdmin(teacherList);
        if (teacher == null) {
            System.out.println(" Teacher is not found ");
        } else {
            lesson.setUser(teacher);
            Storage.getStorage().lessonList.set((lesson.getId() - 1), lesson);
            System.out.println(" Successfully edit Lesson's teacher ");
        }
    }

    private void editLessonSubject(Lesson lesson) {
        Subject subject = chosenSubjectForLesson();
        if (subject == null) {
            System.out.println(" sub =ject is not found ");
        } else {
            lesson.setSubject(subject);
            Storage.getStorage().lessonList.set((lesson.getId() - 1), lesson);
            System.out.println(" Successfully edit Lesson's subject ");
        }
    }

    private void editLessonSinf(Lesson lesson) {
        Sinf sinf = chosenSinfForPupil();
        if (sinf == null) {
            System.out.println(" Class is not found ");
        } else {
            lesson.setSinf(sinf);
            Storage.getStorage().lessonList.set((lesson.getId() - 1), lesson);
            System.out.println(" Successfully edit Lesson's Class ");
        }
    }

    private Lesson chosenLessonForEdit() {
        Storage.getStorage().lessonList.forEach(lesson -> {
            System.out.println(lesson.toString());
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        });
        System.out.println(" Enter chosen Lesson's ID: ");
        int chosen = scannerInt.nextInt();
        return Storage.getStorage().lessonList.stream()
                .filter(lesson -> lesson.getId() == chosen)
                .findFirst().orElse(null);
    }

    private void addLesson() {
        while (true) {
            System.out.println(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
            System.out.println("            Let's start add Lesson, Be carefully! ");
            System.out.println("                1-> Continue; 0-> Go To Back; ");
            if (scannerText.nextLine().equals("0")) {
                break;
            }
            System.out.println(" Firstly, you must choose Class --> ");
            if (Storage.getStorage().sinfList.size() == 0) {
                System.out.println(" Class list is empty ");
            } else {
                Sinf sinf = chosenSinfForPupil();
                if (sinf == null) {
                    System.out.println(" Class is not found ");
                } else {
                    System.out.println(" Secondly, you must choose Subject's --> ");
                    if (Storage.getStorage().subjectList.size() == 0) {
                        System.out.println(" Subject list is empty ");
                    } else {
                        Subject subject = chosenSubjectForLesson();
                        if (subject == null) {
                            System.out.println(" Subject is not found ");
                        } else {
                            System.out.println(" Then, you must choose Teacher --> ");
                            List<AbstractUser> teacherList = Storage.getStorage().abstractUsers.stream()
                                    .filter(abstractUser -> abstractUser.getRole().equals(Role.TEACHER)).toList();
                            User teacher = chosenTeacherForAdmin(teacherList);
                            if (teacher == null) {
                                System.out.println(" Teacher is not found ");
                            } else {
                               while(true){
                                   LocalDateTime startTime = getTime("start");

                                   LocalDateTime finishTime = getTime("finish");

                                   Duration between = Duration.between(startTime, finishTime);
                                   if (between.getSeconds() <= 3600) {
                                       System.out.println(" Darsning boshlanish va tushash vaqti orasidagi farq minimum 1 soat bolishi shart.\n Qayta kiriting! ");
                                   } else {
                                       Lesson lesson = new Lesson(Lesson.getCount(), sinf, subject, teacher, startTime, finishTime);
                                       Lesson lesson2 = Storage.getStorage().lessonList.stream()
                                               .filter(lesson1 -> lesson1.getSubject().equals(lesson.getSubject()) && lesson1.getSinf().equals(lesson.getSinf()) && lesson1.getUser().equals(lesson.getUser()))
                                               .findFirst().orElse(null);
                                       if (lesson2 == null) {
                                           Storage.getStorage().lessonList.add(lesson);
                                           System.out.println(" Successfully added this Lesson ");
                                       } else {
                                           System.out.println(" siz yaratgan lesson ning subjecti , sinfi va teacheri avval yaratilingan, Qayta kiriting!  ");
                                       }
                                       break;
                                   }
                               }
                            }
                        }
                    }
                }
            }
        }


    }

    public static LocalDateTime getTime(String cause) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String time;
        while (true) {
            System.out.printf(" Enter Lesson's %s time: (dd.MM.yyyy HH:mm:ss)  --> Example:  31.12.2022 23:59:59 %n", cause);
            time = scannerText.nextLine();
            try {
                return parse(time, formatter);
            } catch (RuntimeException e) {
                System.out.println("Wrong date or time format");
            }
        }
    }

    private Subject chosenSubjectForLesson() {
        Storage.getStorage().subjectList.forEach(subject -> {
            System.out.println(subject.toString());
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        });
        System.out.println(" Enter Subject's ID: ");
        int chosen = scannerInt.nextInt();
        return Storage.getStorage().subjectList.stream()
                .filter(subject -> subject.getId() == chosen)
                .findFirst().orElse(null);
    }

    private void aboutSinf() {
        label:
        while (true) {
            System.out.println("\n ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ");
            System.out.println("        1-> Add Sinf; 2-> Edit any Sinf; 3-> Show Sinf; 0-> Go To Back; ");
            String com = scannerText.nextLine();
            switch (com) {
                case "1":
                    addSinf();
                    break;
                case "2":
                    editSinf();
                    break;
                case "3":
                    showSinf();
                    break;
                case "0":
                    break label;
                default:
                    exceptionWrongAnswer();
                    break;
            }
        }
    }

    private void showSinf() {
        if (Storage.getStorage().sinfList.size() == 0) {
            System.out.println(" Class list is empty ");
        } else {
            Storage.getStorage().sinfList.forEach(sinf -> {
                System.out.println(sinf.toString());
                System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            });
        }
    }

    private void editSinf() {
        if (Storage.getStorage().sinfList.size() == 0) {
            System.out.println(" Class list is empty ");
        } else {
            Sinf sinf = chosenSinfForPupil();
            if (sinf == null) {
                System.out.println(" Class is not found ");
            } else {
                editSinfParam(sinf);
            }
        }
    }

    private void editSinfParam(Sinf sinf) {
        label:
        while (true) {
            System.out.println(" + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + + ");
            System.out.println(" Which parametrs do you edit? Choose that. ");
            System.out.println(" 1-> name; 2-> school; 0-> Go To Back; ");
            String com = scannerText.nextLine();
            switch (com) {
                case "1":
                    editSinfName(sinf);
                    break;
                case "2":
                    editSinfSchool(sinf);
                    break;
                case "0":
                    break label;
                default:
                    exceptionWrongAnswer();
                    break;
            }
        }
    }

    private void editSinfSchool(Sinf sinf) {
        School school = chosenSchoolForSinf();
        if (school == null) {
            System.out.println(" School is not found ");
        } else {
            sinf.setSchool(school);
            Storage.getStorage().sinfList.set((sinf.getId() - 1), sinf);
            System.out.println(" Successfully edit Class's School; ");
        }
    }

    private void editSinfName(Sinf sinf) {
        String name;
        while (true) {
            System.out.println(" Enter Class's name: ");
            name = scannerText.nextLine();
            if (Objects.isNull(name) || name.isBlank()) {
                System.out.println(" Name is null or empty ");
            } else if (isSinfName(name)) {
                System.out.println(" Name is already exists in the Class List");
            } else {
                break;
            }
        }
        String finalName = name;
        sinf.setName(finalName);
        Storage.getStorage().sinfList.set((sinf.getId() - 1), sinf);
        System.out.println(" Successfully edit Class's name; ");
    }

    private void addSinf() {
        while (true) {
            System.out.println(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
            System.out.println("            Let's start add Class, Be carefully! ");
            System.out.println("                1-> Continue; 0-> Go To Back; ");
            if (scannerText.nextLine().equals("0")) {
                break;
            }
            String name;
            while (true) {
                System.out.println(" Enter Class's name: ");
                name = scannerText.nextLine();
                if (Objects.isNull(name) || name.isBlank()) {
                    System.out.println(" Name is null or empty ");
                } else if (isSinfName(name)) {
                    System.out.println(" Name is already exists in the Class List");
                } else {
                    break;
                }
            }
            String finalName = name;
            if (Storage.getStorage().schoolList.size() == 0) {
                System.out.println(" School List is empty ");
            } else {
                School school = chosenSchoolForSinf();
                if (school == null) {
                    System.out.println(" School is not found ");
                } else {
                    Sinf sinf = new Sinf(Sinf.getCount(), finalName, school);
                    Storage.getStorage().sinfList.add(sinf);
                    System.out.println(" Successfully added this Class. ");
                }
            }
        }
    }

    private School chosenSchoolForSinf() {
        Storage.getStorage().schoolList.forEach(school -> {
            System.out.println(school.toString());
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        });
        System.out.println(" Enter School's ID: ");
        int chosen = scannerInt.nextInt();
        return Storage.getStorage().schoolList.stream()
                .filter(school -> school.getId() == chosen)
                .findFirst().orElse(null);
    }

    private boolean isSinfName(String name) {
        Sinf sinf1 = Storage.getStorage().sinfList.stream()
                .filter(sinf -> sinf.getName().equals(name))
                .findFirst().orElse(null);
        return sinf1 != null;
    }

    private void aboutPupil() {
        label:
        while (true) {
            System.out.println("\n ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ");
            System.out.println("        1-> Add Pupil; 2-> Edit any Pupil; 3-> Show Pupils; 0-> Go To Back; ");
            String com = scannerText.nextLine();
            switch (com) {
                case "1":
                    addPupil();
                    break;
                case "2":
                    editPupil();
                    break;
                case "3":
                    showPupil();
                    break;
                case "0":
                    break label;
                default:
                    exceptionWrongAnswer();
                    break;
            }
        }
    }

    private void showPupil() {
        List<AbstractUser> pupilListForAdmin = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getRole().equals(Role.PUPIL)).toList();
        if (pupilListForAdmin.size() == 0) {
            System.out.println(" Pupil List is empty ");
        } else {
            pupilListForAdmin.forEach(abstractUser -> {
                System.out.println(abstractUser.toString());
                System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            });
        }
    }

    private void editPupil() {
        List<AbstractUser> pupilList = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getRole().equals(Role.PUPIL)).toList();
        if (pupilList.size() == 0) {
            System.out.println(" Pupil List is empty ");
        } else {
            Pupil pupil = chosenPupilForEdit(pupilList);
            if (pupil == null) {
                System.out.println(" Pupil is not found ");
            } else {
                editPupilParam(pupil);
            }
        }
    }

    private void editPupilParam(Pupil pupil) {
        System.out.println(" Which parametr do you want to edit ?  Choose param ! ");
        System.out.println(" 1-> name; 2-> login; 3-> password; 4-> age; 5-> sinf; ");
        String com = scannerText.nextLine();
        switch (com) {
            case "1" -> editPupilName(pupil);
            case "2" -> editPupilLogin(pupil);
            case "3" -> editPupilPassword(pupil);
            case "4" -> editPupilAge(pupil);
            case "5" -> editPupilSinf(pupil);
            default -> exceptionWrongAnswer();
        }
    }

    private void editPupilSinf(Pupil pupil) {
        Sinf sinf = chosenSinfForPupil();
        if (sinf == null) {
            System.out.println(" This class is not found, Try again! ");
        } else {
            pupil.setSinf(sinf);
            Storage.getStorage().abstractUsers.set((pupil.getId() - 1), pupil);
            System.out.println(" Successfully edit Pupil's class! ");
        }
    }

    private void editPupilAge(Pupil pupil) {
        int chosen;
        while (true) {
            System.out.println(" Enter Pupil's age (15+): ");
            chosen = scannerInt.nextInt();
            if (chosen < 15) {
                System.out.println(" Age is wrong written, Try again! ");
            } else {
                break;
            }
        }
        int finalAge = chosen;
        pupil.setAge(finalAge);
        Storage.getStorage().abstractUsers.set((pupil.getId() - 1), pupil);
        System.out.println(" Successfully edit Pupil's age! ");
    }

    private void editPupilPassword(Pupil pupil) {
        String password;
        while (true) {
            System.out.print(" Enter Pupil password: ");
            password = scannerText.nextLine();
            if (Objects.isNull(password) || password.isBlank()) {
                System.out.println(" Password is null or empty ");
            } else if (isPupilConPassword(password)) {
                System.out.println(" Password is already exists in List");
            } else {
                break;
            }
        }
        String finalPassword = password;
        pupil.setPassword(finalPassword);
        Storage.getStorage().abstractUsers.set((pupil.getId() - 1), pupil);
        System.out.println(" Successfully edit Pupil's password ");
    }

    private void editPupilLogin(Pupil pupil) {
        String login;
        while (true) {
            System.out.print(" Enter Pupil's login: ");
            login = scannerText.nextLine();
            if (Objects.isNull(login) || login.isBlank()) {
                System.out.println(" Login is null or empty ");
            } else if (isPupilConLogin(login)) {
                System.out.println(" Login is already exists in List");
            } else {
                break;
            }
        }
        String finalLogin = login;
        pupil.setLogin(finalLogin);
        Storage.getStorage().abstractUsers.set((pupil.getId() - 1), pupil);
        System.out.println(" Successfully edit Pupil's login");
    }

    private void editPupilName(Pupil pupil) {
        String name;
        while (true) {
            System.out.print(" Enter Pupil's name: ");
            name = scannerText.nextLine();
            if (Objects.isNull(name) || name.isBlank()) {
                System.out.println(" Name is null or empty ");
            } else if (isPupilConName(name)) {
                System.out.println(" Name is already exists in List");
            } else {
                break;
            }
        }
        String finalName = name;
        pupil.setName(finalName);
        Storage.getStorage().abstractUsers.set((pupil.getId() - 1), pupil);
        System.out.println(" Successfully edit Pupil's name ");
    }

    private Pupil chosenPupilForEdit(List<AbstractUser> pupilList) {
        pupilList.forEach(abstractUser -> {
            System.out.println(abstractUser.toString());
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        });
        System.out.println(" Enter chosen Pupil's ID: ");
        int chosen = scannerInt.nextInt();
        return (Pupil) pupilList.stream().filter(abstractUser -> abstractUser.getId() == chosen)
                .findFirst().orElse(null);
    }

    private void addPupil() {
        while (true) {
            System.out.println(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
            System.out.println("            Let's start add Pupil, Be carefully! ");
            System.out.println("                1-> Continue; 0-> Go To Back; ");
            if (scannerText.nextLine().equals("0")) {
                break;
            }
            System.out.println(" Firstly, You must choose Class for Pupil--> ");
            if (Storage.getStorage().sinfList.size() == 0) {
                System.out.println(" Class List is empty ");
            } else {
                Sinf sinf = chosenSinfForPupil();
                if (sinf == null) {
                    System.out.println(" Class is not found ");
                } else {
                    String name;
                    while (true) {
                        System.out.print(" Enter Pupil's name: ");
                        name = scannerText.nextLine();
                        if (Objects.isNull(name) || name.isBlank()) {
                            System.out.println(" Name is null or empty ");
                        } else if (isPupilConName(name)) {
                            System.out.println(" Name is already exists in List");
                        } else {
                            break;
                        }
                    }
                    String finalName = name;
                    String login;
                    while (true) {
                        System.out.print(" Enter Pupil's login: ");
                        login = scannerText.nextLine();
                        if (Objects.isNull(login) || login.isBlank()) {
                            System.out.println(" Login is null or empty ");
                        } else if (isPupilConLogin(login)) {
                            System.out.println(" Login is already exists in List");
                        } else {
                            break;
                        }
                    }
                    String finalLogin = login;
                    String password;
                    while (true) {
                        System.out.print(" Enter Pupil password: ");
                        password = scannerText.nextLine();
                        if (Objects.isNull(password) || password.isBlank()) {
                            System.out.println(" Password is null or empty ");
                        } else if (isPupilConPassword(password)) {
                            System.out.println(" Password is already exists in List");
                        } else {
                            break;
                        }
                    }
                    String finalPassword = password;
                    int age;
                    while (true) {
                        System.out.print(" Enter Pupil's age (15+): ");
                        age = scannerInt.nextInt();
                        if (age < 15) {
                            System.out.println(" Age is not enough ");
                        } else {
                            break;
                        }
                    }
                    int finalAge = age;
                    Pupil pupil = new Pupil(Pupil.getCount(), finalName, finalLogin, finalPassword, Role.PUPIL, finalAge, sinf);
                    Storage.getStorage().abstractUsers.add(pupil);
                    System.out.println(" Successfully added this Pupil! ");
                }
            }
        }
    }

    private boolean isPupilConPassword(String name) {
        AbstractUser abstractUser1 = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getPassword().equals(name))
                .findFirst().orElse(null);
        return abstractUser1 != null;
    }

    private Sinf chosenSinfForPupil() {
        Storage.getStorage().sinfList.forEach(sinf -> {
            System.out.println(sinf.toString());
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        });
        System.out.println(" Enter Class's ID: ");
        int chosen = scannerInt.nextInt();
        return Storage.getStorage().sinfList.stream()
                .filter(sinf -> sinf.getId() == chosen)
                .findFirst().orElse(null);
    }

    private boolean isPupilConLogin(String login) {
        AbstractUser abstractUser1 = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getLogin().equals(login))
                .findFirst().orElse(null);
        return abstractUser1 != null;
    }

    private boolean isPupilConName(String name) {
        AbstractUser abstractUser1 = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getName().equals(name))
                .findFirst().orElse(null);
        return abstractUser1 != null;
    }

    private void aboutSubject() {
        label:
        while (true) {
            System.out.println("\n ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ");
            System.out.println("        1-> Add Subject; 2-> Edit any Subject; 3-> Show Subject; 0-> Go To Back; ");
            String co = scannerText.nextLine();
            switch (co) {
                case "1":
                    addSubject();
                    break;
                case "2":
                    editSubject();
                    break;
                case "3":
                    showSubject();
                    break;
                case "0":
                    break label;
                default:
                    exceptionWrongAnswer();
                    break;
            }
        }
    }

    private void showSubject() {
        if (Storage.getStorage().subjectList.size() == 0) {
            System.out.println(" Subject List is empty ");
        } else {
            Storage.getStorage().subjectList.forEach(subject -> {
                System.out.println(subject.toString());
                System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            });
        }
    }

    private void editSubject() {
        if (Storage.getStorage().subjectList.size() == 0) {
            System.out.println(" Subject List is empty ");
        } else {
            Storage.getStorage().subjectList.forEach(subject -> {
                System.out.println(subject.toString());
                System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
            });
            System.out.println(" Enter your chosen Subject's ID: ");
            int chosen = scannerInt.nextInt();
            Subject subject1 = Storage.getStorage().subjectList.stream()
                    .filter(subject -> subject.getId() == chosen)
                    .findFirst().orElse(null);
            if (subject1 == null) {
                System.out.println(" Subject is not found ");
            } else {
                String name;
                while (true) {
                    System.out.print(" Enter new Name: ");
                    name = scannerText.nextLine();
                    if (Objects.isNull(name) || name.isBlank()) {
                        System.out.println(" Name is null or empty ");
                    } else {
                        break;
                    }
                }
                String finalName = name;
                subject1.setName(finalName);
                Storage.getStorage().subjectList.set((subject1.getId() - 1), subject1);
                System.out.println(" Successfully edit ");
            }
        }
    }

    private void addSubject() {
        while (true) {
            System.out.println(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
            System.out.println("              Let's start add Subject, Be carefully! ");
            System.out.println("                   1-> Continue; 0-> Go To Back; ");
            if (scannerText.nextLine().equals("0")) {
                break;
            }
            String name;
            while (true) {
                System.out.print(" Enter Subject's name: ");
                name = scannerText.nextLine();
                if (Objects.isNull(name) || name.isBlank()) {
                    System.out.println(" Name is null or empty ");
                } else if (isSubjectConName(name)) {
                    System.out.println(" Name is already exists in List");
                } else {
                    break;
                }
            }
            String finalName = name;
            Subject subject = new Subject(Subject.getCount(), finalName);
            Storage.getStorage().subjectList.add(subject);
            System.out.println(" Successfully added this Subject ");
        }
    }

    private boolean isSubjectConName(String name) {
        Subject subject1 = Storage.getStorage().subjectList.stream()
                .filter(subject -> subject.getName().equals(name))
                .findFirst().orElse(null);
        return subject1 != null;
    }

    private void aboutTeacher() {
        label:
        while (true) {
            System.out.println("\n ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ");
            System.out.println("        1-> Add Teacher; 2-> Edit any Teacher; 3-> Show Teaches; 0-> Go To Back; ");
            String com = scannerText.nextLine();
            switch (com) {
                case "1":
                    addTeacher();
                    break;
                case "2":
                    editTeacher();
                    break;
                case "3":
                    showTeacher();
                    break;
                case "0":
                    break label;
                default:
                    exceptionWrongAnswer();
                    break;
            }
        }
    }

    private void showTeacher() {
        List<AbstractUser> teacherList = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getRole().equals(Role.TEACHER)).toList();
        if (teacherList.size() == 0) {
            System.out.println(" Teacher List is empty ");
        } else {
            teacherList.forEach(abstractUser -> {
                System.out.println(abstractUser.toString());
                System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  - ");
            });
        }
    }

    private void editTeacher() {
        List<AbstractUser> teacherListForAdmin = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getRole().equals(Role.TEACHER)).toList();
        if (teacherListForAdmin.size() == 0) {
            System.out.println(" Teacher List is empty ");
        } else {
            User user = chosenTeacherForAdmin(teacherListForAdmin);
            if (user == null) {
                System.out.println(" Teacher is not found ");
            } else {
                editTeacherParam(user);
            }
        }
    }

    private void editTeacherParam(User user) {
        System.out.println(" Qaysi parametrni o`zgartirmoqchisiz? Uni tanlang ");
        System.out.println(" 1-> name; 2-> login; 3-> password; 0-> Go To Back; ");
        String com = scannerText.nextLine();
        switch (com) {
            case "1" -> editTeacherName(user);
            case "2" -> editTeacherLogin(user);
            case "3" -> editTeacherPassword(user);
            default -> exceptionWrongAnswer();
        }
    }

    private void editTeacherPassword(User user) {
        String password;
        while (true) {
            System.out.print(" Enter Teacher's password: ");
            password = scannerText.nextLine();
            if (Objects.isNull(password) || password.isBlank()) {
                System.out.println(" Password is null or empty ");
            } else if (isTeacherConPassword(password)) {
                System.out.println(" Password is already exists ");
            } else {
                break;
            }
        }
        String finalPassword = password;
        user.setPassword(finalPassword);
        Storage.getStorage().abstractUsers.set((user.getId() - 1), user);
        System.out.println("Successfully edit Teacher's password ");
    }

    private void editTeacherLogin(User user) {
        String login;
        while (true) {
            System.out.print(" Enter Teacher's login: ");
            login = scannerText.nextLine();
            if (Objects.isNull(login) || login.isBlank()) {
                System.out.println(" Login is null or empty ");
            } else if (isTeacherConLogin(login)) {
                System.out.println(" Login is already exists ");
            } else {
                break;
            }
        }
        String finalLogin = login;
        user.setLogin(finalLogin);
        Storage.getStorage().abstractUsers.set((user.getId() - 1), user);
        System.out.println(" Successfully edit Teacher's login! ");
    }

    private void editTeacherName(User user) {
        String name;
        while (true) {
            System.out.print(" Enter Teacher's name: ");
            name = scannerText.nextLine();
            if (Objects.isNull(name) || name.isBlank()) {
                System.out.println(" Name is null or empty ");
            } else if (isTeacherName(name)) {
                System.out.println(" This name is already exists ");
            } else {
                break;
            }
        }
        String finalName = name;
        user.setName(finalName);
        Storage.getStorage().abstractUsers.set((user.getId() - 1), user);
        System.out.println(" Successfully edit Teacher's name! ");
    }

    private User chosenTeacherForAdmin(List<AbstractUser> teacherListForAdmin) {
        teacherListForAdmin.forEach(abstractUser -> {
            System.out.println(abstractUser.toString());
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        });
        System.out.println(" Enter chosen Teacher's ID: ");
        int chosen = scannerInt.nextInt();
        return (User) teacherListForAdmin.stream()
                .filter(abstractUser -> abstractUser.getId() == chosen)
                .findFirst().orElse(null);
    }

    private void addTeacher() {
        while (true) {
            System.out.println(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
            System.out.println("              Let's start add Teacher, Be carefully! ");
            System.out.println("                   1-> Continue; 0-> Go To Back; ");
            if (scannerText.nextLine().equals("0")) {
                break;
            }
            String name;
            while (true) {
                System.out.print(" Enter teacher's name: ");
                name = scannerText.nextLine();
                if (Objects.isNull(name) || name.isBlank()) {
                    System.out.println(" Name is null or empty ");
                } else if (isTeacherName(name)) {
                    System.out.println(" This name is already exists ");
                } else {
                    break;
                }
            }
            String login;
            while (true) {
                System.out.print(" Enter teacher's login: ");
                login = scannerText.nextLine();
                if (Objects.isNull(login) || login.isBlank()) {
                    System.out.println(" Login is null or empty ");
                } else if (isTeacherConLogin(login)) {
                    System.out.println(" Login is already exists ");
                } else {
                    break;
                }
            }
            String password;
            while (true) {
                System.out.print(" Enter teacher's password: ");
                password = scannerText.nextLine();
                if (Objects.isNull(password) || password.isBlank()) {
                    System.out.println(" Password is null or empty ");
                } else if (isTeacherConPassword(password)) {
                    System.out.println(" Password is already exists ");
                } else {
                    break;
                }
            }
            String finalName = name;
            String finalLogin = login;
            String finalPassword = password;
            if (isTeacherLogin(finalLogin)) {
                System.out.println(" This login is already exists ");
            } else {
                User teacher = new User(User.getCount(), finalName, finalLogin, finalPassword, Role.TEACHER);
                Storage.getStorage().abstractUsers.add(teacher);
                System.out.println(" Successfully added teacher, Thank you! ");
            }
        }
    }

    private boolean isTeacherConPassword(String password) {
        AbstractUser abstractUser1 = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getPassword().equals(password))
                .findFirst().orElse(null);
        return abstractUser1 != null;
    }

    public boolean isTeacherConLogin(String login) {
        AbstractUser abstractUser1 = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getLogin().equals(login))
                .findFirst().orElse(null);
        return abstractUser1 != null;
    }

    public boolean isTeacherName(String name) {
        AbstractUser abstractUser1 = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getName().equals(name))
                .findFirst().orElse(null);
        return abstractUser1 != null;
    }

    public boolean isTeacherLogin(String login) {
        AbstractUser abstractUser1 = Storage.getStorage().abstractUsers.stream()
                .filter(abstractUser -> abstractUser.getLogin().equals(login))
                .findFirst().orElse(null);
        return abstractUser1 != null;
    }

    private void aboutSchool() {
        label:
        while (true) {
            System.out.println("\n ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ^ ");
            System.out.println("        1-> Add School; 2-> Edit any School; 3-> Show Schools; 0-> Go To Back; ");
            String com = scannerText.nextLine();
            switch (com) {
                case "1":
                    addSchool();
                    break;
                case "2":
                    editSchool();
                    break;
                case "3":
                    showSchool();
                    break;
                case "0":
                    break label;
                default:
                    exceptionWrongAnswer();
                    break;
            }
        }
    }

    public void exceptionWrongAnswer() {
        try {
            throw new WrongAnswer(" Wrong answer, Try again! ");
        } catch (WrongAnswer a) {
            a.printStackTrace();
        }
    }

    public void exceptionDateAndTime() {
        try {
            throw new WrongAnswer("Wrong date and time format");
        } catch (WrongAnswer a) {
            a.printStackTrace();
        }
    }

    private void showSchool() {
        if (Storage.getStorage().schoolList.size() == 0) {
            System.out.println(" School List is empty ");
        } else {
            Storage.getStorage().schoolList.forEach(school -> {
                System.out.println(school.toString());
                System.out.println(" _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _");
            });
        }
    }

    private void editSchool() {
        while (true) {
            System.out.println(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
            System.out.println(" Let's start Edit School, Be carefully! ");
            System.out.println("    1-> Continue; 0-> Go To Back; ");
            if (scannerText.nextLine().equals("0")) {
                break;
            }
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - ");
            System.out.println(" Firstly, You must choose School --> ");
            if (Storage.getStorage().schoolList.size() == 0) {
                System.out.println(" School List is empty ");
            } else {
                School school = chosenSchool();
                if (school == null) {
                    System.out.println(" This ID is not found ");
                } else {
                    editSchoolParam(school);
                }
            }
        }
    }

    public void editSchoolParam(School school) {
        System.out.println("         Qaysi parametrni o`rgartirmoqchisiz?    ");
        System.out.println("   1-> name; 2-> address; 3-> isActive;  ");
        String com = scannerText.nextLine();
        switch (com) {
            case "1" -> editSchoolName(school);
            case "2" -> editAddress(school);
            case "3" -> editIsActive(school);
            default -> exceptionWrongAnswer();
        }

    }

    private void editIsActive(School school) {
        school.setActive(true);
        Storage.getStorage().schoolList.set((school.getId() - 1), school);
        System.out.println(" Successfully edit School's Active ");
    }

    private void editAddress(School school) {
        String address;
        while (true) {
            System.out.print(" Enter new address: ");
            address = scannerText.nextLine();
            if (Objects.isNull(address) || address.isBlank()) {
                System.out.println(" Address is null or empty ");
            } else {
                break;
            }
        }
        String finalLogin = address;
        school.setAddress(finalLogin);
        Storage.getStorage().schoolList.set((school.getId() - 1), school);
        System.out.println(" Successfully Edit, Tank you! ");
    }

    private void editSchoolName(School school) {
        String name;
        while (true) {
            System.out.print(" Enter new name: ");
            name = scannerText.nextLine();
            if (Objects.isNull(name) || name.isBlank()) {
                System.out.println(" Name is null or empty ");
            } else if (isNameSchool(name)) {
                System.out.println(" Name is already exists ");
            } else {
                break;
            }
        }
        String finalLogin = name;
        school.setName(finalLogin);
        Storage.getStorage().schoolList.set((school.getId() - 1), school);
        System.out.println(" Successfully Edit, Thank you! ");
    }

    private School chosenSchool() {
        Storage.getStorage().schoolList.forEach(school -> {
            System.out.println(school.toString());
            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        });
        System.out.println(" Id ni kiriting: ");
        int chosen = scannerInt.nextInt();
        return Storage.getStorage().schoolList.stream()
                .filter(school -> school.getId() == chosen)
                .findFirst().orElse(null);
    }

    private void addSchool() {
        while (true) {
            System.out.println(" * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * ");
            System.out.println(" Let's start Add School, Be carefully! ");
            System.out.println("    1-> Continue; 0-> Go To Back; ");
            if (scannerText.nextLine().equals("0")) {
                break;
            }
            String name;
            while (true) {
                System.out.print(" Enter School's name: ");
                name = scannerText.nextLine();
                if (Objects.isNull(name) || name.isBlank()) {
                    System.out.println(" Name is null or empty ");
                } else {
                    break;
                }
            }
            String address;
            while (true) {
                System.out.print(" Enter School's address: ");
                address = scannerText.nextLine();
                if (Objects.isNull(address) || address.isBlank()) {
                    System.out.println(" Password is null or empty ");
                } else {
                    break;
                }
            }
            String finalName = name;
            String finalAddress = address;
            if (isNameSchool(finalName)) {
                System.out.println(" This name is already exists ");
            } else {
                School school = new School(School.getCount(), finalName, finalAddress, false);
                Storage.getStorage().schoolList.add(school);
                System.out.println(" Successfully added this School ");
            }
        }
    }

    private boolean isNameSchool(String name) {
        School school1 = Storage.getStorage().schoolList.stream()
                .filter(school -> school.getName().equals(name))
                .findFirst().orElse(null);
        return school1 != null;
    }

    static AdminCon adminCon;

    public static AdminCon getAdminCon() {
        if (adminCon == null) {
            adminCon = new AdminCon();
        }
        return adminCon;
    }
}
