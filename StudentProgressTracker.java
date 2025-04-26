import java.util.*;

// Base class
class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}

// Aggregated class
class Course {
    String courseName;

    public Course(String courseName) {
        this.courseName = courseName;
    }
}

// Composed class
class Progress {
    int marks;
    String grade;

    public Progress() {
        this.marks = 0;
        this.grade = "Not Assigned";
    }

    public void updateProgress(int marks) {
        this.marks = marks;
        if (marks >= 90) grade = "A";
        else if (marks >= 75) grade = "B";
        else if (marks >= 60) grade = "C";
        else grade = "F";
    }
}

// Student class
class Student extends Person {
    Progress progress;
    Course course;

    public Student(String name, int age, Course course) {
        super(name, age);
        this.course = course;
        this.progress = new Progress();
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Course: " + course.courseName);
        System.out.println("Marks: " + progress.marks + ", Grade: " + progress.grade);
        System.out.println("-----------------------------------");
    }
}

// Teacher class (age removed)
class Teacher {
    String name;
    Course course;

    public Teacher(String name, Course course) {
        this.name = name;
        this.course = course;
    }

    public void displayInfo() {
        System.out.println("Teacher Name: " + name);
        System.out.println("Teaches Course: " + course.courseName);
        System.out.println("-----------------------------------");
    }

    public void trackStudents(List<Student> students) {
        System.out.println("Tracking students for course: " + course.courseName);
        for (Student s : students) {
            if (s.course.courseName.equals(this.course.courseName)) {
                System.out.println("Student: " + s.name + ", Marks: " + s.progress.marks + ", Grade: " + s.progress.grade);
            }
        }
        System.out.println("-----------------------------------");
    }
}

// Thread class to update progress
class ProgressUpdater extends Thread {
    Student student;
    int marks;

    public ProgressUpdater(Student student, int marks) {
        this.student = student;
        this.marks = marks;
    }

    public void run() {
        System.out.println("Updating progress for " + student.name + "...");
        student.progress.updateProgress(marks);
        System.out.println("Progress updated for " + student.name + ".");
    }
}

// Main class
public class StudentProgressTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Course options
        Course java = new Course("Java Programming");
        Course python = new Course("Python Programming");

        List<Student> students = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();

        // Add Teachers
        System.out.println("Enter Teacher Details:");
        System.out.print("Teacher 1 Name (Java): ");
        String t1Name = sc.nextLine();
        teachers.add(new Teacher(t1Name, java));

        System.out.print("Teacher 2 Name (Python): ");
        String t2Name = sc.nextLine();
        teachers.add(new Teacher(t2Name, python));

        // Add Students
        System.out.print("\nEnter number of students: ");
        int n = sc.nextInt();
        sc.nextLine(); // Consume newline

        for (int i = 0; i < n; i++) {
            System.out.println("\nEnter details for student " + (i + 1));
            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = sc.nextInt();
            sc.nextLine(); // Consume newline

            System.out.print("Course (Java/Python): ");
            String courseInput = sc.nextLine();
            Course course = courseInput.equalsIgnoreCase("Java") ? java : python;

            students.add(new Student(name, age, course));
        }

        // Display initial info
        System.out.println("\nInitial Student Info:");
        for (Student s : students) {
            s.displayInfo();
        }

        // Display teacher info
        System.out.println("\nTeacher Info:");
        for (Teacher t : teachers) {
            t.displayInfo();
        }

        // Progress update
        System.out.println("\nEnter marks for each student:");
        List<ProgressUpdater> updaters = new ArrayList<>();
        for (Student s : students) {
            System.out.print("Enter marks for " + s.name + ": ");
            int marks = sc.nextInt();
            ProgressUpdater updater = new ProgressUpdater(s, marks);
            updaters.add(updater);
            updater.start();
        }

        // Wait for all threads to complete
        for (ProgressUpdater updater : updaters) {
            try {
                updater.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Display updated info
        System.out.println("\nAfter Progress Update:");
        for (Student s : students) {
            s.displayInfo();
        }

        // Teachers tracking students
        System.out.println("\nTeachers Tracking Students:");
        for (Teacher t : teachers) {
            t.trackStudents(students);
        }

        sc.close();
    }
}