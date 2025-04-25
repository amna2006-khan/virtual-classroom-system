import java.util.*;

// Base Class
class Person {
    protected String name;

    public Person(String name) {
        this.name = name;
    }

    public void display() {
        System.out.println("Name: " + name);
    }
}

// Inheritance: Student and Teacher from Person
class Student extends Person {
    public Student(String name) {
        super(name);
    }
}

class Teacher extends Person {
    public Teacher(String name) {
        super(name);
    }
}

// Aggregation: Course has a reference to Teacher (not owned)
class Course {
    private String courseName;
    private Teacher teacher;

    public Course(String courseName, Teacher teacher) {
        this.courseName = courseName;
        this.teacher = teacher;
    }

    public String getCourseName() {
        return courseName;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void showCourseInfo() {
        System.out.println("Course: " + courseName);
        System.out.println("Taught by: " + teacher.name);
    }
}

// Composition: Classroom has Course and a list of Students
class Classroom {
    private Course course;
    private List<Student> students;

    public Classroom(Course course) {
        this.course = course;
        this.students = new ArrayList<>();
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void startClass() {
        course.showCourseInfo();
        System.out.println("Students Present:");
        for (Student s : students) {
            System.out.println("- " + s.name);
        }
        System.out.println("Class is now in session...");
    }
}

// Threading: Simulate a live class
class LiveClass extends Thread {
    private Classroom classroom;

    public LiveClass(Classroom classroom) {
        this.classroom = classroom;
    }

    public void run() {
        classroom.startClass();
        try {
            Thread.sleep(5000); // Simulate class running for 5 seconds
        } catch (InterruptedException e) {
            System.out.println("Class interrupted!");
        }
        System.out.println("Class has ended.\n");
    }
}

// Main Class to test everything
public class VirtualClassroomSystem {
    public static void main(String[] args) {
        Teacher teacher = new Teacher("Ms. Johnson");
        Course course = new Course("Physics", teacher);
        Classroom classroom = new Classroom(course);

        // Adding students
        classroom.addStudent(new Student("Alice"));
        classroom.addStudent(new Student("Bob"));

        // Start class using thread
        LiveClass liveClass = new LiveClass(classroom);
        liveClass.start();

        System.out.println("Main thread is free to do other tasks...");
    }
}