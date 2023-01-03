package uz.pdp.online.project.model;

import java.util.Objects;

public class School {

    static int count=1;
    {
        count++;
    }

    private int id;
    private String name;
    private String address;
    private Boolean isActive;

    public School(int id, String name, String address, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.isActive = isActive;
    }

    public School() {
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        School.count = count;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return id == school.id && Objects.equals(name, school.name) && Objects.equals(address, school.address) && Objects.equals(isActive, school.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, isActive);
    }

    @Override
    public String toString() {
        return "School{ " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
