package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class TestMain {

    @Test
    public void testParse() {
        assertTrue(Main.parseFiles(new File(".")));
    }

    @Test
    public void testPaises() {
        Main.parseFiles(new File("."));
        assertNotNull(Main.getObjects(TipoEntidade.PAIS));
    }

    @Test
    public void testCidades() {
        Main.parseFiles(new File("."));
        assertNotNull(Main.getObjects(TipoEntidade.CIDADE));
    }

    @Test
    public void testInvalidos() {
        Main.parseFiles(new File("."));
        assertNotNull(Main.getObjects(TipoEntidade.INPUT_INVALIDO));
    }

    @Test
    public void testPopulacao() {
        Main.parseFiles(new File("."));
        assertTrue(Main.calcularPopulacaoTotal() >= 0);
    }
}