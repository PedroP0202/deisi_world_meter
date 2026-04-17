package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainDropProjectTest {

    private File createDataset(String paises, String cidades, String populacao) throws IOException {
        Path dir = Files.createTempDirectory("deisiworldmeter-test-");
        Files.writeString(dir.resolve("paises.csv"), paises);
        Files.writeString(dir.resolve("cidades.csv"), cidades);
        Files.writeString(dir.resolve("populacao.csv"), populacao);
        return dir.toFile();
    }

    @Test
    public void parseFilesReturnsFalseForMissingFolder() {
        assertFalse(Main.parseFiles(new File("missing-folder-123456")));
    }

    @Test
    public void parseFilesAcceptsAFileAndUsesParentFolder() throws IOException {
        File dir = createDataset(
                "id,alfa2,alfa3,nome\n701,wk,wka,Wakanda\n",
                "alfa2,cidade,regiao,populacao,latitude,longitude\nwk,Birnin Zana,01,50000,0.0,0.0\n",
                "id,ano,populacao masculina,populacao feminina,densidade\n701,2021,5000000,5000000,10.0\n"
        );

        assertTrue(Main.parseFiles(new File(dir, "paises.csv")));
        assertEquals(1, Main.getObjects(TipoEntidade.PAIS).size());
        assertEquals(1, Main.getObjects(TipoEntidade.CIDADE).size());
    }

    @Test
    public void getObjectsPaisKeepsInputOrder() throws IOException {
        File dir = createDataset(
                "id,alfa2,alfa3,nome\n4,af,afg,Afeganistao\n701,wk,wka,Wakanda\n12,dz,dza,Argelia\n",
                "alfa2,cidade,regiao,populacao,latitude,longitude\nwk,Birnin Zana,01,50000,0.0,0.0\naf,Cabul,01,4434550,34.5289,69.1725\nwk,Warrior Falls,02,40000,0.5,0.5\n",
                "id,ano,populacao masculina,populacao feminina,densidade\n4,2021,1,1,1.0\n701,2021,1,1,1.0\n12,2021,1,1,1.0\n"
        );

        Main.parseFiles(dir);

        List<?> paises = Main.getObjects(TipoEntidade.PAIS);
        assertEquals(3, paises.size());
        assertEquals("Afeganistao | 4 | AF | AFG", paises.get(0).toString());
        assertEquals("Wakanda | 701 | WK | WKA | 2", paises.get(1).toString());
        assertEquals("Argelia | 12 | DZ | DZA", paises.get(2).toString());
    }

    @Test
    public void getObjectsCidadeReturnsExpectedToString() throws IOException {
        File dir = createDataset(
                "id,alfa2,alfa3,nome\n999,ll,lll,Latveria\n",
                "alfa2,cidade,regiao,populacao,latitude,longitude\nll,Nowhere,1,500,1.6,2.31\n",
                "id,ano,populacao masculina,populacao feminina,densidade\n999,2021,100,100,2.0\n"
        );

        Main.parseFiles(dir);

        List<?> cidades = Main.getObjects(TipoEntidade.CIDADE);
        assertEquals(1, cidades.size());
        assertEquals("Nowhere | LL | 1 | 500 | (1.6,2.31)", cidades.get(0).toString());
    }

    @Test
    public void invalidCountryRowsAreReportedAndSkipped() throws IOException {
        File dir = createDataset(
                "id,alfa2,alfa3,nome\n4,af,afg,Afeganistao\n4,ax,axx,DuplicadoPorId\n8,al,alb\n12,dz,dza,Argelia\n",
                "alfa2,cidade,regiao,populacao,latitude,longitude\naf,Cabul,01,100,1.0,1.0\n",
                "id,ano,populacao masculina,populacao feminina,densidade\n4,2021,1,1,1.0\n12,2021,1,1,1.0\n"
        );

        Main.parseFiles(dir);

        List<?> paises = Main.getObjects(TipoEntidade.PAIS);
        List<?> invalidos = Main.getObjects(TipoEntidade.INPUT_INVALIDO);

        assertEquals(2, paises.size());
        assertEquals("paises.csv | 3 | 2 | 4", invalidos.get(0));
        assertEquals("paises.csv | 4 | 2 | 3", invalidos.get(1));
    }

    @Test
    public void invalidCityRowsAreReportedInOrderAndNotLoaded() throws IOException {
        File dir = createDataset(
                "id,alfa2,alfa3,nome\n999,ll,lll,Latveria\n",
                "alfa2,cidade,regiao,populacao,latitude,longitude\nll,Nowhere,1,500,1.6,2.31\n,MissingCode,1,300,1.0,1.0\nxx,UnknownCountry,2,250,-1.0,-1.0\nll,NegativePopulation,3,-5,1.0,1.0\n",
                "id,ano,populacao masculina,populacao feminina,densidade\n999,2021,100,100,2.0\n"
        );

        Main.parseFiles(dir);

        List<?> cidades = Main.getObjects(TipoEntidade.CIDADE);
        List<?> invalidos = Main.getObjects(TipoEntidade.INPUT_INVALIDO);

        assertEquals(1, cidades.size());
        assertEquals("cidades.csv | 3 | 1 | 6", invalidos.get(0));
        assertEquals("cidades.csv | 4 | 1 | 6", invalidos.get(1));
        assertEquals("cidades.csv | 5 | 1 | 6", invalidos.get(2));
    }

    @Test
    public void invalidPopulationRowsAreReportedAndSkipped() throws IOException {
        File dir = createDataset(
                "id,alfa2,alfa3,nome\n999,ll,lll,Latveria\n",
                "alfa2,cidade,regiao,populacao,latitude,longitude\nll,Nowhere,1,500,1.6,2.31\n",
                "id,ano,populacao masculina,populacao feminina,densidade\n999,2021,100,100,2.0\nbad-row\n555,2021,10,10,1.0\n999,-2021,10,10,1.0\n"
        );

        Main.parseFiles(dir);

        List<?> invalidos = Main.getObjects(TipoEntidade.INPUT_INVALIDO);

        assertEquals("populacao.csv | 3 | 3 | 1", invalidos.get(0));
        assertEquals("populacao.csv | 4 | 3 | 5", invalidos.get(1));
        assertEquals("populacao.csv | 5 | 3 | 5", invalidos.get(2));
    }

    @Test
    public void calcularPopulacaoTotalSumsOnlyValidCities() throws IOException {
        File dir = createDataset(
                "id,alfa2,alfa3,nome\n4,af,afg,Afeganistao\n999,ll,lll,Latveria\n",
                "alfa2,cidade,regiao,populacao,latitude,longitude\naf,Cabul,01,4434550,34.5289,69.1725\nll,Nowhere,1,500,1.6,2.31\nxx,Ignored,2,250,-1.0,-1.0\n",
                "id,ano,populacao masculina,populacao feminina,densidade\n4,2021,1,1,1.0\n999,2021,1,1,1.0\n"
        );

        Main.parseFiles(dir);

        assertEquals(4435050.0, Main.calcularPopulacaoTotal());
    }

    @Test
    public void duplicateAlfa3IsAlsoConsideredInvalid() throws IOException {
        File dir = createDataset(
                "id,alfa2,alfa3,nome\n4,af,afg,Afeganistao\n8,al,afg,Albania\n",
                "alfa2,cidade,regiao,populacao,latitude,longitude\naf,Cabul,01,4434550,34.5289,69.1725\n",
                "id,ano,populacao masculina,populacao feminina,densidade\n4,2021,1,1,1.0\n"
        );

        Main.parseFiles(dir);

        List<?> paises = Main.getObjects(TipoEntidade.PAIS);
        List<?> invalidos = Main.getObjects(TipoEntidade.INPUT_INVALIDO);

        assertEquals(1, paises.size());
        assertEquals("paises.csv | 3 | 2 | 4", invalidos.get(0));
    }
}
