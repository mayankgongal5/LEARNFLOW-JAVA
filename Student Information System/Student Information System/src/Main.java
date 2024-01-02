import java.util.*;

class Marks {
    private final Map<String, Integer> subjectMarks;

    public Marks() {
        this.subjectMarks = new HashMap<>();
    }

    public void addSubjectMarks(String subject, int marks) {
        subjectMarks.put(subject, marks);
    }

    public Map<String, Integer> getSubjectMarks() {
        return subjectMarks;
    }
}

record Enrollment(Course course, Marks marks) {

    public double calculatePercentage() {
        if (marks.getSubjectMarks().isEmpty()) {
            return 0.0;
        }

        int totalMarks = 0;
        int totalSubjects = marks.getSubjectMarks().size();

        for (int marksValue : marks.getSubjectMarks().values()) {
            totalMarks += marksValue;
        }

        return (double) totalMarks / totalSubjects;
    }
}

record Course(String courseCode, String courseName) {
}

class Student {
    private final String studentId;
    private final String name;
    private final List<Enrollment> enrollments;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.enrollments = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void enroll(Course course, Marks marks) {
        enrollments.add(new Enrollment(course, marks));
    }
}

class StudentInformationSystem {
    private final List<Student> students;
    private final List<Course> courses;

    public StudentInformationSystem() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
    }

    public void addStudent(String studentId, String name) {
        students.add(new Student(studentId, name));
    }

    public void addCourse(String courseCode, String courseName) {
        courses.add(new Course(courseCode, courseName));
    }

    public void enrollStudent(String studentId, String courseCode, Marks marks) {
        Student student = findStudent(studentId);
        Course course = findCourse(courseCode);

        if (student != null && course != null) {
            student.enroll(course, marks);
            System.out.println("Enrollment successful for " + student.getName() + " in " + course.courseName());
        } else {
            System.out.println("Student or Course not found!");
        }
    }

    public void generateTranscript(String studentId) {
        Student student = findStudent(studentId);

        if (student != null) {
            System.out.println("Transcript for " + student.getName() + " (ID: " + student.getStudentId() + ")");
            List<Enrollment> enrollments = student.getEnrollments();

            if (enrollments.isEmpty()) {
                System.out.println("No courses enrolled.");
            } else {
                System.out.println("Courses Enrolled:");
                for (Enrollment enrollment : enrollments) {
                    Course course = enrollment.course();
                    Marks marks = enrollment.marks();
                    double percentage = enrollment.calculatePercentage();

                    System.out.println("Course: " + course.courseCode() + " - " + course.courseName());
                    System.out.println("Marks:");
                    for (Map.Entry<String, Integer> entry : marks.getSubjectMarks().entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    System.out.println("Percentage: " + percentage + "%");
                    System.out.println();
                }
            }
        } else {
            System.out.println("Student not found!");
        }
    }

    private Student findStudent(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    private Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.courseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        StudentInformationSystem sis = new StudentInformationSystem();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. Generate Transcript");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.next();
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.next();
                    sis.addStudent(studentId, studentName);
                    System.out.println("Student added successfully!");
                    break;
                case 2:
                    System.out.print("Enter Course Code: ");
                    String courseCode = scanner.next();
                    System.out.print("Enter Course Name: ");
                    String courseName = scanner.next();
                    sis.addCourse(courseCode, courseName);
                    System.out.println("Course added successfully!");
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    String enrollStudentId = scanner.next();
                    System.out.print("Enter Course Code: ");
                    String enrollCourseCode = scanner.next();

                    Marks marks = new Marks();
                    System.out.print("Enter the number of subjects for course " + enrollCourseCode + ": ");
                    int numSubjects = scanner.nextInt();

                    for (int i = 0; i < numSubjects; i++) {
                        System.out.print("Enter subject name: ");
                        String subject = scanner.next();
                        System.out.print("Enter marks for " + subject + ": ");
                        int subjectMarks = scanner.nextInt();
                        marks.addSubjectMarks(subject, subjectMarks);
                    }

                    sis.enrollStudent(enrollStudentId, enrollCourseCode, marks);
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    String transcriptStudentId = scanner.next();
                    sis.generateTranscript(transcriptStudentId);
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);
    }
}
