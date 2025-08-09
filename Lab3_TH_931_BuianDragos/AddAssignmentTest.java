import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.TemaXMLRepository;
import service.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddAssignmentTest {

    private TemaXMLRepository temaRepo;
    private Service service;

    @BeforeEach
    void setUp() {
        temaRepo = mock(TemaXMLRepository.class);
        service = new Service(null, temaRepo, null);
    }

    @Test
    void TC1_emptyId_throwsValidationException() {
        Tema tema = new Tema("", "test", 5, 3);
        doThrow(new RuntimeException("Numar tema invalid!")).when(temaRepo).save(tema);

        assertThrows(RuntimeException.class, () -> service.saveTema("", "test", 5, 3));
    }

    @Test
    void TC2_nullId_throwsNullPointerException() {
        Tema tema = new Tema(null, "test", 5, 3);
        doThrow(new NullPointerException("Numar tema invalid!")).when(temaRepo).save(tema);

        assertThrows(NullPointerException.class, () -> service.saveTema(null, "test", 5, 3));
    }

    @Test
    void TC3_emptyDescription_throwsValidationException() {
        Tema tema = new Tema("1", "", 5, 3);
        doThrow(new RuntimeException("Descriere invalida!")).when(temaRepo).save(tema);

        assertThrows(RuntimeException.class, () -> service.saveTema("1", "", 5, 3));
    }

    @Test
    void TC4_invalidDeadline_throwsValidationException() {
        Tema tema = new Tema("1", "test", 20, 3);
        doThrow(new RuntimeException("Data de primire invalida!")).when(temaRepo).save(tema);

        assertThrows(RuntimeException.class, () -> service.saveTema("1", "test", 20, 3));
    }

    @Test
    void TC5_invalidStartline_throwsValidationException() {
        Tema tema = new Tema("1", "test", 5, 20);
        doThrow(new RuntimeException("Data de primire invalida!")).when(temaRepo).save(tema);

        assertThrows(RuntimeException.class, () -> service.saveTema("1", "test", 5, 20));
    }

    @Test
    void TC6_validTema_returns1() {
        when(temaRepo.save(any(Tema.class))).thenReturn(null);
        int result = service.saveTema("1", "test", 5, 3);
        assertEquals(1, result);
    }

    @Test
    void TC7_duplicateTema_returns0() {
        Tema existing = new Tema("1", "test", 5, 3);
        when(temaRepo.save(any(Tema.class))).thenReturn(existing);
        int result = service.saveTema("1", "test", 5, 3);
        assertEquals(0, result);
    }
}
