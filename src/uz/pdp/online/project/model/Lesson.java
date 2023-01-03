package uz.pdp.online.project.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Lesson {

    static int count = 1;
    {
        count++;
    }

    private int id;
    private Sinf sinf;
    private Subject subject;
    private User user;      // teacher
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    public Lesson() {
    }

    public Lesson(int id, Sinf sinf, Subject subject, User user, LocalDateTime startTime, LocalDateTime finishTime) {
        this.id = id;
        this.sinf = sinf;
        this.subject = subject;
        this.user = user;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Lesson.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sinf getSinf() {
        return sinf;
    }

    public void setSinf(Sinf sinf) {
        this.sinf = sinf;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id && Objects.equals(sinf, lesson.sinf) && Objects.equals(subject, lesson.subject) && Objects.equals(user, lesson.user) && Objects.equals(startTime, lesson.startTime) && Objects.equals(finishTime, lesson.finishTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sinf, subject, user, startTime, finishTime);
    }

    @Override
    public String toString() {
        return " Lesson{ " +
                "id=" + id +
                ", sinf=" + sinf +
                ", subject=" + subject +
                ",\n           teacher=" + user +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                '}';
    }
}
