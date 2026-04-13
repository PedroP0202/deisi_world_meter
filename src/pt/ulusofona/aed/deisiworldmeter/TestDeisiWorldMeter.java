package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

public class TestDeisiWorldMeter {

    @Test
    public void testParseFolderIsInvalid() {
        boolean result = Main.parseFiles(new File("non_existent_folder_12345"));
        assertFalse(result, "Deveria retornar false para uma pasta inexistente");
    }
    
    @Test
    public void testGetObjectsExists() {
        List<?> list = Main.getObjects(TipoEntidade.CIDADE);
        assertNotNull(list, "O retorno de getObjects não deve ser nulo");
    }
    
    @Test
    public void testInputInvalidoExiste() {
        List<?> invalidos = Main.getObjects(TipoEntidade.INPUT_INVALIDO);
        assertNotNull(invalidos, "A lista de invalidos não pode ser nula");
    }

    @Test
    public void testPaisClass() {
        Pais pais = new Pais(10, "PT", "PRT", "Portugal");
        assertEquals("Portugal", pais.getNome());
        assertEquals("PT", pais.getAlfa2());
        assertEquals("PRT", pais.getAlfa3());
        assertEquals(10, pais.getId());
    }

    @Test
    public void testPopulacaoClass() {
        Populacao pop = new Populacao(5, 2023, 5000, 5500, 25.5);
        assertEquals(5, pop.getId());
        assertEquals(2023, pop.getAno());
        assertEquals(5000, pop.getPopulacaoMasculina());
        assertEquals(5500, pop.getPopulacaoFeminina());
        assertEquals(25.5, pop.getDensidade(), 0.01);
    }
    
    @Test
    public void testCidadeClass() {
        Cidade cidade = new Cidade("PT", "Lisboa", "Lisboa e Vale do Tejo", 500000, 38.7, -9.1);
        assertEquals("PT", cidade.getAlfa2());
        assertEquals("Lisboa", cidade.getNome());
        assertEquals("Lisboa e Vale do Tejo", cidade.getRegiao());
        assertEquals(500000, cidade.getPopulacao());
        assertEquals(38.7, cidade.getLatitude(), 0.01);
        assertEquals(-9.1, cidade.getLongitude(), 0.01);
    }
}
