package ru.nsu.shirokov;

import java.util.*;

public class StudentGradebook {
    private final String studentName;
    private final boolean isBudget; // true - бюджет, false - платно
    private boolean diplomaWorkPassed; // true, если квалификационная работа сдана
    private final List<Semester> semesters;

    public StudentGradebook(String studentName, boolean isBudget) {
        this.studentName = studentName;
        this.isBudget = isBudget;
        this.semesters = new ArrayList<>();
        this.diplomaWorkPassed = false;
    }

    // Добавить семестр
    public void addSemester(Semester semester) {
        semesters.add(semester);
    }
    public List<Semester> getSemesters() {
        return new ArrayList<>(semesters); // Возвращаем копию списка, чтобы избежать мутаций
    }


    // Установить оценку за диплом
    public void setDiplomaWorkPassed(boolean passed) {
        this.diplomaWorkPassed = passed;
    }

    // 1. Текущий средний балл
    public double calculateGPA() {
        return semesters.stream()
                .flatMap(s -> s.getSubjects().stream())
                .mapToDouble(Subject::getNumericGrade)
                .average()
                .orElse(0.0);
    }

    // 2. Возможность перевода на бюджет
    public boolean canTransferToBudget() {
        if (isBudget) return false;
        int sessionCount = semesters.size();
        if (sessionCount < 2) return false;

        // Берём последние две сессии
        List<Semester> lastTwoSemesters = semesters.subList(sessionCount - 2, sessionCount);

        // Проверяем отсутствие неудовлетворительных оценок
        return lastTwoSemesters.stream()
                .flatMap(s -> s.getSubjects().stream())
                .noneMatch(sub -> sub.getGrade() == Grade.SATISFACTORY || sub.getGrade() == Grade.FAILED);
    }

    // 3. Возможность получения красного диплома
    public boolean canGetHonorsDiploma() {
        long totalSubjects = semesters.stream()
                .flatMap(s -> s.getSubjects().stream())
                .count();

        long excellentCount = semesters.stream()
                .flatMap(s -> s.getSubjects().stream())
                .filter(sub -> sub.getGrade() == Grade.EXCELLENT)
                .count();

        boolean hasNoSatisfactory = semesters.stream()
                .flatMap(s -> s.getSubjects().stream())
                .noneMatch(sub -> sub.getGrade() == Grade.SATISFACTORY || sub.getGrade() == Grade.FAILED);

        return diplomaWorkPassed &&
                (double) excellentCount / totalSubjects >= 0.75 &&
                hasNoSatisfactory;
    }

    // 4. Возможность получения повышенной стипендии
    public boolean canGetIncreasedScholarship() {
        if (!isBudget || semesters.isEmpty()) return false;

        // Берём последний семестр
        Semester lastSemester = semesters.get(semesters.size() - 1);

        // Проверяем отсутствие неудовлетворительных оценок
        return lastSemester.getSubjects().stream()
                .noneMatch(sub -> sub.getGrade() == Grade.SATISFACTORY || sub.getGrade() == Grade.FAILED) &&
                lastSemester.getSubjects().stream()
                        .filter(sub -> sub.isExam())
                        .allMatch(sub -> sub.getGrade() == Grade.EXCELLENT);
    }

    @Override
    public String toString() {
        return "StudentGradebook{" +
                "studentName='" + studentName + '\'' +
                ", isBudget=" + isBudget +
                ", semesters=" + semesters +
                '}';
    }
}
class Semester {
    private final int semesterNumber;
    private final List<Subject> subjects;

    public Semester(int semesterNumber) {
        this.semesterNumber = semesterNumber;
        this.subjects = new ArrayList<>();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "semesterNumber=" + semesterNumber +
                ", subjects=" + subjects +
                '}';
    }
}
class Subject {
    private final String name;
    private final Grade grade;
    private final boolean isExam;

    public Subject(String name, Grade grade, boolean isExam) {
        this.name = name;
        this.grade = grade;
        this.isExam = isExam;
    }

    public String getName() {
        return name;
    }

    public Grade getGrade() {
        return grade;
    }

    public boolean isExam() {
        return isExam;
    }

    public double getNumericGrade() {
        return grade.getNumericValue();
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", grade=" + grade +
                ", isExam=" + isExam +
                '}';
    }
}

enum Grade {
    EXCELLENT(5.0),
    GOOD(4.0),
    SATISFACTORY(3.0),
    FAILED(2.0);

    private final double numericValue;

    Grade(double numericValue) {
        this.numericValue = numericValue;
    }

    public double getNumericValue() {
        return numericValue;
    }
}

class Main {
    public static void main(String[] args) {
        StudentGradebook gradebook = new StudentGradebook("John Doe", false);

        Semester semester1 = new Semester(1);
        semester1.addSubject(new Subject("Math", Grade.EXCELLENT, true));
        semester1.addSubject(new Subject("Physics", Grade.GOOD, true));
        gradebook.addSemester(semester1);

        Semester semester2 = new Semester(2);
        semester2.addSubject(new Subject("Chemistry", Grade.EXCELLENT, true));
        semester2.addSubject(new Subject("English", Grade.GOOD, false));
        gradebook.addSemester(semester2);

        System.out.println("GPA: " + gradebook.calculateGPA());
        System.out.println("Can transfer to budget: " + gradebook.canTransferToBudget());
        gradebook.setDiplomaWorkPassed(true);
        System.out.println("Can get honors diploma: " + gradebook.canGetHonorsDiploma());
        System.out.println("Can get increased scholarship: " + gradebook.canGetIncreasedScholarship());
    }
}

