package uz.pdp.online.project.model;

import java.util.Objects;

public class Sinf {

    static int count=1;
    {
        count++;
    }

    private int id;
    private String name;
    private School school;

    public Sinf() {
    }

    public Sinf(int id, String name, School school) {
        this.id = id;
        this.name = name;
        this.school = school;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Sinf.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sinf sinf = (Sinf) o;
        return id == sinf.id && Objects.equals(name, sinf.name) && Objects.equals(school, sinf.school);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, school);
    }

    @Override
    public String toString() {
        return "Sinf{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", school=" + school +
                '}';
    }
}
