package ru.nsu.shirokov;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class StudentGradebookTest {
    private StudentGradebook gradebook;

    @BeforeEach
    void setUp() {
        gradebook = new StudentGradebook("John Doe", false);

        Semester semester1 = new Semester(1);
        semester1.addSubject(new Subject("Math", Grade.EXCELLENT, true));
        semester1.addSubject(new Subject("Physics", Grade.GOOD, true));
        gradebook.addSemester(semester1);

        Semester semester2 = new Semester(2);
        semester2.addSubject(new Subject("Chemistry", Grade.EXCELLENT, true));
        semester2.addSubject(new Subject("English", Grade.SATISFACTORY, false));
        gradebook.addSemester(semester2);
    }

    @Test
    void testCalculateGPA() {
        double expectedGPA = (Grade.EXCELLENT.getNumericValue()
                + Grade.GOOD.getNumericValue()
                + Grade.EXCELLENT.getNumericValue()
                + Grade.SATISFACTORY.getNumericValue()) / 4.0;

        assertEquals(expectedGPA, gradebook.calculateGPA(), 0.01);
    }

    @Test
    void testCanTransferToBudget_FalseDueToSatisfactoryGrades() {
        assertFalse(gradebook.canTransferToBudget());
    }

    @Test
    void testCanTransferToBudget_TrueWhenNoSatisfactoryGrades() {
        gradebook = new StudentGradebook("John Doe", false);

        Semester semester1 = new Semester(1);
        semester1.addSubject(new Subject("Math", Grade.EXCELLENT, true));
        semester1.addSubject(new Subject("Physics", Grade.EXCELLENT, true));
        gradebook.addSemester(semester1);

        Semester semester2 = new Semester(2);
        semester2.addSubject(new Subject("Chemistry", Grade.GOOD, true));
        semester2.addSubject(new Subject("English", Grade.GOOD, false));
        gradebook.addSemester(semester2);

        assertTrue(gradebook.canTransferToBudget());
    }

    @Test
    void testCanGetHonorsDiploma_FalseDueToSatisfactoryGrade() {
        gradebook.setDiplomaWorkPassed(true);
        assertFalse(gradebook.canGetHonorsDiploma());
    }

    @Test
    void testCanGetHonorsDiploma_TrueWhenAllConditionsMet() {
        gradebook = new StudentGradebook("Jane Smith", true);

        Semester semester1 = new Semester(1);
        semester1.addSubject(new Subject("Math", Grade.EXCELLENT, true));
        semester1.addSubject(new Subject("Physics", Grade.EXCELLENT, true));
        gradebook.addSemester(semester1);

        Semester semester2 = new Semester(2);
        semester2.addSubject(new Subject("Chemistry", Grade.EXCELLENT, true));
        semester2.addSubject(new Subject("English", Grade.EXCELLENT, false));
        gradebook.addSemester(semester2);

        gradebook.setDiplomaWorkPassed(true);
        assertTrue(gradebook.canGetHonorsDiploma());
    }

    @Test
    void testCanGetIncreasedScholarship_FalseWhenNotBudget() {
        assertFalse(gradebook.canGetIncreasedScholarship());
    }

    @Test
    void testCanGetIncreasedScholarship_TrueForBudgetAndExcellentGrades() {
        gradebook = new StudentGradebook("Jane Smith", true);

        Semester semester1 = new Semester(1);
        semester1.addSubject(new Subject("Math", Grade.EXCELLENT, true));
        semester1.addSubject(new Subject("Physics", Grade.EXCELLENT, true));
        gradebook.addSemester(semester1);

        Semester semester2 = new Semester(2);
        semester2.addSubject(new Subject("Chemistry", Grade.EXCELLENT, true));
        semester2.addSubject(new Subject("English", Grade.EXCELLENT, false));
        gradebook.addSemester(semester2);

        assertTrue(gradebook.canGetIncreasedScholarship());
    }

    @Test
    void testAddSemester() {
        Semester newSemester = new Semester(3);
        newSemester.addSubject(new Subject("History", Grade.GOOD, true));
        gradebook.addSemester(newSemester);

        List<Semester> semesters = gradebook.getSemesters(); // Теперь метод доступен
        assertEquals(3, semesters.size());
        assertEquals("History", semesters.get(2).getSubjects().get(0).getName());
    }


    @Test
    void testToString() {
        String result = gradebook.toString();
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("Math"));
        assertTrue(result.contains("Chemistry"));
    }
}
