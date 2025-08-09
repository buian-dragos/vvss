import domain.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.StudentXMLRepository;
import service.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddStudentTest {

    private StudentXMLRepository studentRepo;
    private Service service;

    @BeforeEach
    void setUp() {
        studentRepo = mock(StudentXMLRepository.class);
        service = new Service(studentRepo, null, null);
    }

    @Test
    void TC1_validInput_returns1() {
        when(studentRepo.save(any(Student.class))).thenReturn(null);
        int result = service.saveStudent("s1", "Ana", 935);
        assertEquals(1, result);
    }

    @Test
    void TC2_emptyId_returns0() {
        when(studentRepo.save(any(Student.class))).thenReturn(new Student("", "Ana", 935));
        int result = service.saveStudent("", "Ana", 935);
        assertEquals(0, result);
    }

    @Test
    void TC3_emptyName_returns0() {
        when(studentRepo.save(any(Student.class))).thenReturn(new Student("s2", "", 935));
        int result = service.saveStudent("s2", "", 935);
        assertEquals(0, result);
    }

    @Test
    void TC4_validLowerGroup_returns1() {
        when(studentRepo.save(any(Student.class))).thenReturn(null);
        int result = service.saveStudent("s3", "Ion", 231);
        assertEquals(1, result);
    }

    @Test
    void TC5_groupTooLow_returns0() {
        when(studentRepo.save(any(Student.class))).thenReturn(new Student("s4", "Ana", 109));
        int result = service.saveStudent("s4", "Ana", 109);
        assertEquals(0, result);
    }

    @Test
    void TC6_groupLowerBoundary_returns1() {
        when(studentRepo.save(any(Student.class))).thenReturn(null);
        int result = service.saveStudent("s5", "Ana", 110);
        assertEquals(1, result);
    }

    @Test
    void TC7_groupNearLower_returns1() {
        when(studentRepo.save(any(Student.class))).thenReturn(null);
        int result = service.saveStudent("s6", "Ana", 111);
        assertEquals(1, result);
    }

    @Test
    void TC8_groupNearUpper_returns1() {
        when(studentRepo.save(any(Student.class))).thenReturn(null);
        int result = service.saveStudent("s7", "Ana", 937);
        assertEquals(1, result);
    }

    @Test
    void TC9_groupUpperBoundary_returns1() {
        when(studentRepo.save(any(Student.class))).thenReturn(null);
        int result = service.saveStudent("s8", "Ana", 938);
        assertEquals(1, result);
    }

    @Test
    void TC10_groupTooHigh_returns0() {
        when(studentRepo.save(any(Student.class))).thenReturn(new Student("s6", "Ana", 939));
        int result = service.saveStudent("s9", "Ana", 939);
        assertEquals(0, result);
    }
}
