import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MascotaTest{
  @Test
  void testAssertEquals(){
 Mascota mascota1 = new Mascota("Canela", "Perro","husky","8","1N2K");
    assertEquals("1N2K", mascota1.getId());
  }
}

// Agregado clase MascotaTest
