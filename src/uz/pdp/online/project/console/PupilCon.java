package uz.pdp.online.project.console;

import uz.pdp.online.project.Storage;
import uz.pdp.online.project.impl.PupilOpe;
import uz.pdp.online.project.model.ExercisePupil;
import uz.pdp.online.project.model.Lesson;
import uz.pdp.online.project.model.Pupil;
import uz.pdp.online.project.model.Task_Status;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PupilCon implements PupilOpe {

    static Scanner scannerText = new Scanner(System.in);
    static Scanner scannerInt = new Scanner(System.in);

    @Override
    public void startPupil(Pupil pupil) {
        System.out.println("\n Salom, Talaba " + pupil.getName());
        label:
        while (true) {
            System.out.println(" ### ### ### ### ### ### ### ### ### ### ### ### ### ");
            System.out.println("""
                        1-> mening darslarim va topshiriqlarim ro`yxati;
                        2-> Topshiriq uchun javob yuborish;
                        3-> Topshiriq uchun qoyilgan ballarim;
                        0-> Go To Back;
                    """);
            String com = scannerText.nextLine();
            switch (com) {
                case "1":
                    showMyLesson(pupil);
                    break;
                case "2":
                    sentMyExerciseAnswer(pupil);
                    break;
                case "3":
                    showMyAnswerResult(pupil);
                    break;
                case "0":
                    break label;
                default:
                    AdminCon.getAdminCon().exceptionWrongAnswer();
                    break;
            }
        }
    }

    private void showMyAnswerResult(Pupil pupil) {
        List<ExercisePupil> answerList = Storage.getStorage().exercisesPupilList.stream()
                .filter(exercisePupil -> exercisePupil.getPupil().equals(pupil)).toList();
        if (answerList.size() == 0) {
            System.out.println(" List is empty ");
        } else {
            answerList.forEach(exercisePupil -> {
                System.out.println(" Exercise: " + exercisePupil.getExercise().toString());
                System.out.println(" My answer: " + exercisePupil.getAnswer());
                System.out.println(" Answer status: " + exercisePupil.getTaskStatus());
                System.out.println(" My grade: " + exercisePupil.getGrade());
                System.out.println(" -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- ");
            });

        }
    }

    private void sentMyExerciseAnswer(Pupil pupil) {
        while (true) {
            System.out.println(" +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ +++ ");
            System.out.println("         Let's start sent answer for exercise, Be carefully! ");
            System.out.println("                   1-> Continue; 0-> Go To Back; ");
            if (scannerText.nextLine().equals("0")) {
                break;
            }

            System.out.println(" Firstly, you must choose your lesson --> ");
            if (Storage.getStorage().lessonList.size() == 0) {
                System.out.println(" Lesson list is empty ");
            } else {
                Lesson lesson = chosenLessonForPupil(pupil);
                if (lesson == null) {
                    System.out.println(" chosen Lesson is not found ");
                } else {
                    List<ExercisePupil> newMyExercise = Storage.getStorage().exercisesPupilList.stream()
                            .filter(exercisePupil -> exercisePupil.getPupil().equals(pupil) && !exercisePupil.getTaskStatus().equals(Task_Status.COMPLETED)).toList();

                    if (newMyExercise.size() == 0) {
                        System.out.println(" My exercise list is empty ");
                    } else {
                        ExercisePupil exercisePupil1 = chosenExerciseForLesson(lesson, newMyExercise);

                        if (exercisePupil1 == null) {
                            System.out.println(" chosen ExercisePupil is not found ");
                        } else {
                            String answer;
                            while (true) {
                                System.out.println(" Enter answer for ExercisePupil: ");
                                answer = scannerText.nextLine();
                                if (Objects.isNull(answer) || answer.isBlank()) {
                                    System.out.println(" Answer is null or empty, Try again! ");
                                } else {
                                    break;
                                }
                            }
                            String finalAnswer = answer;
                            exercisePupil1.setAnswer(finalAnswer);
                            exercisePupil1.setTaskStatus(Task_Status.CHECKING);
                            Storage.getStorage().exercisesPupilList.set((exercisePupil1.getId() - 1), exercisePupil1);
                            System.out.println(" Successfully added this Answer, Thank you Student! ");
                        }
                    }
                }
            }
        }
    }

    public ExercisePupil chosenExerciseForLesson(Lesson lesson, List<ExercisePupil> newMyExercise) {
        newMyExercise.forEach(exercisePupil -> {
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
        System.out.println(" Enter chosen my ExercisePupil's ID: ");
        int chosen = scannerInt.nextInt();
        return newMyExercise.stream()
                .filter(exercisePupil -> exercisePupil.getId() == chosen)
                .findFirst().orElse(null);
    }

    private void showMyLesson(Pupil pupil) {
        System.out.println(" Firstly, you must choose your lesson --> ");
        if (Storage.getStorage().lessonList.size() == 0) {
            System.out.println(" Lesson list is empty ");
        } else {
            Lesson lesson = chosenLessonForPupil(pupil);
            if (lesson == null) {
                System.out.println(" chosen Lesson is not found ");
            } else {
                if (Storage.getStorage().exercisesPupilList.size() == 0) {
                    System.out.println(" Exercise Pupil list is empty ");
                } else {
                    Storage.getStorage().exercisesPupilList.forEach(exercisePupil -> {
                        if (exercisePupil.getExercise().getLesson().equals(lesson) && exercisePupil.getPupil().equals(pupil)) {
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
                }
            }
        }
    }

    private Lesson chosenLessonForPupil(Pupil pupil) {
        Storage.getStorage().lessonList.forEach(lesson -> {
            if (lesson.getSinf().equals(pupil.getSinf())) {
                System.out.println(" Lesson{ " +
                        "id=" + lesson.getId() +
                        ", sinf=" + lesson.getSinf() +
                        ", subject=" + lesson.getSubject() +
                        ",\n        teacher=" + lesson.getUser() +
                        ", startTime=" + lesson.getStartTime() +
                        ", finishTime=" + lesson.getFinishTime() +
                        '}');
            }
        });
        System.out.println(" Enter chosen Lesson's ID: ");
        int chosen = scannerInt.nextInt();
        return Storage.getStorage().lessonList.stream()
                .filter(lesson -> lesson.getId() == chosen)
                .findFirst().orElse(null);
    }

    static PupilCon pupilCon;

    public static PupilCon getPupilCon() {
        if (pupilCon == null) {
            pupilCon = new PupilCon();
        }
        return pupilCon;
    }
}
