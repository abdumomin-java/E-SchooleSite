package uz.pdp.online.project.model;

import uz.pdp.online.project.enums.Role;

import java.util.Objects;

public class Pupil extends AbstractUser{

    static int count=1;
    {
        count++;
    }

    private int age;
    private Sinf sinf;

    public Pupil() {
    }

    public Pupil(int id, String name, String login, String password, Role role, int age, Sinf sinf) {
        super(id, name, login, password, role);
        this.age = age;
        this.sinf = sinf;
    }
    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Pupil.count = count;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sinf getSinf() {
        return sinf;
    }

    public void setSinf(Sinf sinf) {
        this.sinf = sinf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Pupil pupil = (Pupil) o;
        return age == pupil.age && Objects.equals(sinf, pupil.sinf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), age, sinf);
    }

    @Override
    public String toString() {
        return "Pupil{ id=" + getId() + ", age=" + age + ", sinf=" + sinf + " " + super.toString() + '}';
    }
}
