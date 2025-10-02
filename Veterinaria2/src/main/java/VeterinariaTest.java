import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class VeterinariaTest {
    private static final Logger LOG = Logger.getLogger(VeterinariaTest.class.getName());

    private Veterinaria veterinaria;
    private Mascota mascota1,  mascota2, mascota3, mascota4;

    @BeforeEach
    public void setUp() {
        mascota1 = new Mascota("Luna", "perro", "Pitbull", 5, 3, "001", "Luis Martinez", "123456");
        mascota2 = new Mascota("Calo", "perro", "Golden", 4, 6, "002", "Carlos Suarez", "111111");
        mascota3 = new Mascota("Jorge", "mono", "Curioso", 7, 2.7, "003", "Danna Ruiz", "000000");
        mascota4 = new Mascota("Zeus", "gato", "Invernal", 3, 3.4, "004", "Jhon Diaz", "222222");

        veterinaria = new Veterinaria("Veterinaria Uquindio", "12345678", "Patagonia");
        veterinaria.agregarMascota(mascota1);
        veterinaria.agregarMascota(mascota2);
        veterinaria.agregarMascota(mascota3);
        veterinaria.agregarMascota(mascota4);
    }


    @Test
    void testAssertNotNull() {
        LOG.info("Inicio testAssertNotNull");
        assertNotNull(mascota1.getNombre());
        LOG.info("Fin testAssertNotNull");
    }

    @Test
    void testAssertFalse() {
        LOG.info("Inicio testAssertFalse");
        assertFalse(mascota2.getEspecie().isEmpty());
        LOG.info("Fin testAssertFalse");
    }

    @Test
    void testAssertNull() {
        LOG.info("Inicio testAssertNull");
        assertNull(mascota3.getTestNull());
        LOG.info("Fin testAssertNull");
    }

    @Test
    void testAssertEquals(){
        LOG.info("Inicio testAssertEquals");
        assertEquals("004",mascota4.getIdentificacion());
        LOG.info("Fin testAssertEquals");
    }

    @Test
    void testAssertNotEquals(){
        LOG.info("Inicio testAssertNotEquals");
        assertNotEquals("Luna", mascota2.getNombre());
        LOG.info("Fin testAssertNotEquals");
    }

    @Test
    void testAssertTrue(){
        LOG.info("Inicio testAssertTrue");
        assertTrue(mascota1.getEdad() == 5);
        LOG.info("Fin testAssertTrue");
    }

}