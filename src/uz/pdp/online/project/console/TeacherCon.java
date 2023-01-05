package uz.pdp.online.project.console;

import uz.pdp.online.project.Storage;
import uz.pdp.online.project.enums.Grade;
import uz.pdp.online.project.enums.Role;
import uz.pdp.online.project.enums.Task_Status;
import uz.pdp.online.project.impl.TeacherOpe;
import uz.pdp.online.project.model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.time.LocalDateTime.parse;

public class TeacherCon implements TeacherOpe {

    static Scanner scannerText = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);

    @Override
    public void startTeacher(AbstractUser teacher) {
        System.out.println(" Assalomu alaykum. Hurmatli " + teacher.getName() + " mentor!");
        label:
        while (true) {
            System.out.println(" @@@ @@@ @@@ @@@ @@@ @@@ @@@ @@@ @@@ @@@ @@@ @@@ @@@  ");
            System.out.println("""
                        1-> show my Lessons and Students;
                        2-> posting an assignment to my any lesson;
                        3-> assessment of assignments ;
                        4-> show Exercise list;
                        5-> show Exercise Pupil list;
                        0-> Go To Back;
                    """);
            String com = scannerText.nextLine();
            switch (com) {
                case "1":
                    showMyLessons(teacher);
                    break;
                case "2":
                    postingAssignment(teacher);
                    break;
                case "3":
                    assessmentOfAssignment(teacher);
                    break;
                case "4":
                    showExerciseList(teacher);
                    break;
                case "5":
                    showExercisePupilList(teacher);
                    break;
                case "0":
                    break label;
                default:
                    AdminCon.getAdminCon().exceptionWrongAnswer();
                    break;
            }
        }
    }

    private void assessmentOfAssignment(AbstractUser teacher) {
        System.out.println(" Firstly, you must choose Lesson's ID: --> ");
        List<Lesson> myLessons = Storage.getStorage().lessonList.stream()
                .filter(lesson -> lesson.getUser().equals(teacher)).toList();
        if (myLessons.size() == 0) {
            System.out.println(" My lesson list is empty ");
        } else {
            Lesson lesson = chosenLessonForTeacher(myLessons);
            if (lesson == null) {
                System.out.println(" chosen Lesson is not found ");
            } else {
                List<ExercisePupil> checkingForPupils = Storage.getStorage().exercisesPupilList.stream()
                        .filter(exercisePupil -> exercisePupil.getTaskStatus().equals(Task_Status.CHECKING)).toList();
                if (checkingForPupils.size() == 0) {
                    System.out.println(" Talabalar topshiriqlarni bajarmagan, Keyinroq unirib ko`ring ");
                } else {
                    while (true) {
                        System.out.println(" Studentlarni baholash boshlandi; ");
                        System.out.println("  1-> Continue; 0-> Go To Back; ");
                        if (scannerText.nextLine().equals("0")) {
                            break;
                        }
                        ExercisePupil exercisePupil = chosenExerciseForLessonForTeacher(lesson);

                        System.out.println(" Student's answer: " + exercisePupil.getAnswer());
                        System.out.println(" Studentni baholaysizmi?   1-> ha; 0-> yoq, ortga; ");
                        if (scannerText.nextLine().equals("1")) {
                            System.out.println("""
                                      Bahoni to`g`ri tanlang:
                                      2-> two; 3-> three; 4-> four; 5-> five;
                                    """);
                            String com = scannerText.nextLine();
                            switch (com) {
                                case "2" -> exercisePupil.setGrade(Grade.TWO);
                                case "3" -> exercisePupil.setGrade(Grade.THREE);
                                case "4" -> exercisePupil.setGrade(Grade.FOUR);
                                case "5" -> exercisePupil.setGrade(Grade.FIVE);
                                default -> exercisePupil.setGrade(Grade.ONE);
                            }
                            exercisePupil.setTaskStatus(Task_Status.COMPLETED);
                            Storage.getStorage().exercisesPupilList.set((exercisePupil.getId() - 1), exercisePupil);
                            System.out.println(" Baho yangilandi, Thank you! ");
                        }
                        System.out.println(" Baho o`zgartirish tugadi; ");
                    }
                }
            }
        }
    }

    public ExercisePupil chosenExerciseForLessonForTeacher(Lesson lesson) {
        Storage.getStorage().exercisesPupilList.forEach(exercisePupil -> {
            if (exercisePupil.getExercise().getLesson().equals(lesson)) {
                System.out.println(" ExercisePupil{ " +
                        "id=" + exercisePupil.getId() +
                        ", exercise= " + exercisePupil.getExercise() +
                        ",\n            pupil= " + exercisePupil.getPupil() +
                        ", answer='" + exercisePupil.getAnswer() + '\'' +
                        ", grade=" + exercisePupil.getGrade() +
                        ", taskStatus=" + exercisePupil.getTaskStatus() +
                        '}');
            }
        });
        System.out.println(" Enter chosen ExercisePupil's ID: ");
        int chosen = scannerInt.nextInt();
        return Storage.getStorage().exercisesPupilList.stream()
                .filter(exercisePupil -> exercisePupil.getId() == chosen)
                .findFirst().orElse(null);
    }

    private void showExercisePupilList(AbstractUser teacher) {
        if (Storage.getStorage().exercisesPupilList.size() == 0) {
            System.out.println(" Exercise pupil list is empty ");
        } else {
            Storage.getStorage().exercisesPupilList.forEach(exercisePupil -> {
                if (exercisePupil.getExercise().getLesson().getUser().equals(teacher)) {
                    System.out.println(" ExercisePupil{ " +
                            "id=" + exercisePupil.getId() +
                            ", exercise= " + exercisePupil.getExercise() +
                            ",\n              pupil= " + exercisePupil.getPupil() +
                            ", answer='" + exercisePupil.getAnswer() + '\'' +
                            ", grade=" + exercisePupil.getGrade() +
                            ", taskStatus=" + exercisePupil.getTaskStatus() +
                            '}');
                    System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
                }
            });
        }
    }

    private void showExerciseList(AbstractUser teacher) {
        if (Storage.getStorage().exerciseList.size() == 0) {
            System.out.println(" Exercise list is empty ");
        } else {
            Storage.getStorage().exerciseList.forEach(exercise -> {
                if (exercise.getLesson().getUser().equals(teacher)) {
                    System.out.println(" Exercise{ " +
                            "id=" + exercise.getId() +
                            ", lesson= " + exercise.getLesson() +
                            ", \n           task='" + exercise.getTask() + '\'' +
                            ", deadline=" + exercise.getDeadline() +
                            '}');
                    System.out.println(" -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ");
                }
            });
        }
    }

    private void postingAssignment(AbstractUser teacher) {
        System.out.println(" Firstly, you must choose Lesson --> ");
        List<Lesson> myLessons = Storage.getStorage().lessonList.stream()
                .filter(lesson -> lesson.getUser().equals(teacher)).toList();
        if (myLessons.size() == 0) {
            System.out.println(" My lesson list is empty ");
        } else {
            Lesson lesson = chosenLessonForTeacher(myLessons);
            if (lesson == null) {
                System.out.println(" chosen Lesson is not found ");
            } else {
                String task;
                while (true) {
                    System.out.println(" Enter Task's name: ");
                    task = scannerText.nextLine();
                    if (Objects.isNull(task) || task.isBlank()) {
                        System.out.println(" Name is null or empty ");
                    } else {
                        break;
                    }
                }
                String finalTask = task;

                String finishTime;
                while (true) {
                    System.out.println(" Enter Exercise's deadline: (dd.MM.yyyy HH:mm:ss) --> Example:  31.12.2022 23:59:59 ");
                    finishTime = scannerText.nextLine();
                    if (!finishTime.matches("[0-9]{2}.[0-9]{2}.[0-9]{4} [0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                        AdminCon.getAdminCon().exceptionDateAndTime();
                    } else {
                        break;
                    }
                }
                String finalFinishTime = finishTime;
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime deadline = parse(finalFinishTime, formatter1);

                Exercise exercise = new Exercise(Exercise.getCount(), lesson, finalTask, deadline);
                Storage.getStorage().exerciseList.add(exercise);
                System.out.println(" Successfully add this Exercise, Thank you! ");

                int p = 0;
                List<AbstractUser> pupilList = Storage.getStorage().abstractUsers.stream()
                        .filter(abstractUser -> abstractUser.getRole().equals(Role.PUPIL)).toList();
                for (AbstractUser abstractUser : pupilList) {
                    Pupil pupil = (Pupil) abstractUser;
                    p++;
                    ExercisePupil exercisePupil = new ExercisePupil(ExercisePupil.getCount(), exercise, pupil, "0", Grade.ZERO, Task_Status.IN_PROGRESS);
                    Storage.getStorage().exercisesPupilList.add(exercisePupil);
                }
                System.out.println(" Successfully add this exercise for " + p + " students ");
            }
        }
    }

    private void showMyLessons(AbstractUser teacher) {
        System.out.println(" Firstly, you must choose Lesson --> ");
        List<Lesson> teacherLessons = Storage.getStorage().lessonList.stream()
                .filter(lesson -> lesson.getUser().equals(teacher)).toList();
        if (teacherLessons.size() == 0) {
            System.out.println(" My lesson List is empty ");
        } else {
            Lesson lesson = chosenLessonForTeacher(teacherLessons);
            if (lesson == null) {
                System.out.println(" Chosen lesson is not found ");
            } else {
                Storage.getStorage().abstractUsers.forEach(abstractUser -> {
                    if (abstractUser.getRole().equals(Role.PUPIL)) {
                        Pupil pupil = (Pupil) abstractUser;
                        if (pupil.getSinf().equals(lesson.getSinf())) {
                            System.out.println(" Pupil{ id=" + pupil.getId() + ", name=" + pupil.getName() + ", login=" + pupil.getLogin() + ", password=" + pupil.getPassword() + ", age=" + pupil.getAge() + ",\n         sinf=" + pupil.getSinf() + '}');
                            System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
                        }
                    }
                });
            }
        }
    }

    private Lesson chosenLessonForTeacher(List<Lesson> myLessons) {
        myLessons.forEach(lesson -> {
            System.out.println(lesson.toString());
            System.out.println(" -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ");
        });
        System.out.println(" Enter chosen Lesson's ID: ");
        int chosen = scannerInt.nextInt();
        return myLessons.stream().filter(lesson -> lesson.getId() == chosen)
                .findFirst().orElse(null);
    }

    static TeacherCon teacherCon;

    public static TeacherCon getTeacherCon() {
        if (teacherCon == null) {
            teacherCon = new TeacherCon();
        }
        return teacherCon;
    }
}
