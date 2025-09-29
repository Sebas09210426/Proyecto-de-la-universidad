import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MascotaTest{
  @Test
  void testAssertEquals(){
    LOG.info("Inicio test assertEquals");
    int result = 2 + 3;
    assertEquals(5, result);
    LOG.info("Fin test assertEquals");
  }
}

// Agregar clase MascotaTest
