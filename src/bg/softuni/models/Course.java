package bg.softuni.models;

import bg.softuni.exceptions.DuplicateEntryInStructureException;
import bg.softuni.exceptions.InvalidStringException;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Course {
    public static final int NUMBER_OF_TASKS_ON_EXAM = 5;
    public static final int MAX_SCORE_ON_EXAM_TASK = 100;

    private String name;
    private LinkedHashMap<String, Student> studentByName;

    public Course(String name) {
        this.name = name;
        this.studentByName = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.equals("")) {
            throw new InvalidStringException();
        }

        this.name = name;
    }

    public Map<String, Student> getStudentByName() {
        return Collections.unmodifiableMap(this.studentByName);
    }

    public void enrollStudent(Student student) {
        if (this.studentByName.containsKey(student.getUserName())) {
            throw new DuplicateEntryInStructureException(
                    student.getUserName(), this.name);
        }

        this.studentByName.put(student.getUserName(), student);
    }
}
