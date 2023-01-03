package uz.pdp.online.project.model;

import java.util.Objects;

public class ExercisePupil {

    static int count=1;
    {
        count++;
    }

    private int id;
    private Exercise exercise;
    private Pupil pupil;
    private String answer;
    private Grade grade;
    private Task_Status taskStatus;

    public ExercisePupil(int id, Exercise exercise, Pupil pupil, String answer, Grade grade, Task_Status taskStatus) {
        this.id = id;
        this.exercise = exercise;
        this.pupil = pupil;
        this.answer = answer;
        this.grade = grade;
        this.taskStatus = taskStatus;
    }

    public ExercisePupil() {
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        ExercisePupil.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Task_Status getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Task_Status taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExercisePupil that = (ExercisePupil) o;
        return id == that.id && Objects.equals(exercise, that.exercise) && Objects.equals(pupil, that.pupil) && Objects.equals(answer, that.answer) && grade == that.grade && taskStatus == that.taskStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exercise, pupil, answer, grade, taskStatus);
    }

    @Override
    public String toString() {
        return "ExercisePupil{ " +
                "id=" + id +
                ", exercise= " + exercise +
                ",\n            pupil= " + pupil +
                ", answer='" + answer + '\'' +
                ", grade=" + grade +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
