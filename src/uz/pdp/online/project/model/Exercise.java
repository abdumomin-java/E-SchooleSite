package uz.pdp.online.project.model;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

public class Exercise {

    static int count=1;
    {
        count++;
    }

    private int id;
    private Lesson lesson;
    private String task;
    private LocalDateTime deadline;

    public Exercise() {
    }

    public Exercise(int id, Lesson lesson, String task, LocalDateTime deadline) {
        this.id = id;
        this.lesson = lesson;
        this.task = task;
        this.deadline = deadline;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Exercise.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return id == exercise.id && Objects.equals(lesson, exercise.lesson) && Objects.equals(task, exercise.task) && Objects.equals(deadline, exercise.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lesson, task, deadline);
    }

    @Override
    public String toString() {
        return " Exercise{ " +
                "id=" + id +
                " , task='" + task + '\'' +
                ", deadline=" + deadline +
                ",\n            lesson= " + lesson +
                '}';
    }
}
