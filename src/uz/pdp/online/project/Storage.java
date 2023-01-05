package uz.pdp.online.project;

import uz.pdp.online.project.model.*;

import java.util.ArrayList;

public class Storage {

    public ArrayList<AbstractUser> abstractUsers = new ArrayList<>();
    public ArrayList<Exercise> exerciseList = new ArrayList<>();
    public ArrayList<ExercisePupil> exercisesPupilList = new ArrayList<>();
    public ArrayList<Lesson> lessonList = new ArrayList<>();
    public ArrayList<School> schoolList = new ArrayList<>();
    public ArrayList<Sinf> sinfList = new ArrayList<>();
    public ArrayList<Subject> subjectList = new ArrayList<>();



    private static Storage storage;

    public static Storage getStorage() {
        if (storage == null){
            storage = new Storage();
        }
        return storage;
    }
}
