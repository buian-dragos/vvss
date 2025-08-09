import domain.Student;
import domain.Tema;
import domain.Nota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class IntegrationTest {

    private StudentXMLRepository studentRepo;
    private TemaXMLRepository temaRepo;
    private NotaXMLRepository notaRepo;
    private Service service;

    @BeforeEach
    void setUp() {
        studentRepo = mock(StudentXMLRepository.class);
        temaRepo = mock(TemaXMLRepository.class);
        notaRepo = mock(NotaXMLRepository.class);
        service = new Service(studentRepo, temaRepo, notaRepo);
    }

    @Test
    void addStudentInvalidID() {
        when(studentRepo.save(any(Student.class))).thenReturn(new Student("", "Ana", 935));
        int result = service.saveStudent("", "Ana", 935);
        assertEquals(0, result);
    }

    @Test
    void addAssignmentValid() {
        when(temaRepo.save(any(Tema.class))).thenReturn(null);
        int result = service.saveTema("1", "test", 5, 3);
        assertEquals(1, result);
    }

    @Test
    void addGradeValid() {
        Student s = new Student("s1", "Ana", 935);
        Tema t = new Tema("1", "desc", 5, 3);

        when(studentRepo.findOne("s1")).thenReturn(s);
        when(temaRepo.findOne("1")).thenReturn(t);
        when(notaRepo.save(any())).thenReturn(null); // save successful

        int result = service.saveNota("s1", "1", 9.5, 5, "good job");
        assertEquals(1, result);
    }

    @Test
    void fullIntegrationTest() {
        // Add valid student
        Student s = new Student("s1", "Ana", 935);
        when(studentRepo.save(any(Student.class))).thenReturn(null);
        int studentResult = service.saveStudent("s1", "Ana", 935);
        assertEquals(1, studentResult);

        // Add valid assignment
        Tema t = new Tema("1", "test", 5, 3);
        when(temaRepo.save(any(Tema.class))).thenReturn(null);
        int assignmentResult = service.saveTema("1", "test", 5, 3);
        assertEquals(1, assignmentResult);

        // Add valid grade
        when(studentRepo.findOne("s1")).thenReturn(s);
        when(temaRepo.findOne("1")).thenReturn(t);
        when(notaRepo.save(any())).thenReturn(null);
        int gradeResult = service.saveNota("s1", "1", 9.5, 5, "good job");
        assertEquals(1, gradeResult);
    }


}
