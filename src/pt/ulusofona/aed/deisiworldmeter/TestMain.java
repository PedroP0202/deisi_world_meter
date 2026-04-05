package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;

public class TestMain {

    public static void main(String[] args) {
        // Test 1: parseFiles success
        boolean result = Main.parseFiles(new File("test-files"));
        if (!result) {
            throw new AssertionError("Test 1 falhou: parseFiles retornou false para test-files");
        }

        // Test 2: getCidades not empty
        if (Main.getCidades().isEmpty()) {
            throw new AssertionError("Test 2 falhou: A lista de cidades carregada está vazia");
        }

        // Test 3: calcularPopulacaoTotal positive
        double populacaoTotal = Main.calcularPopulacaoTotal();
        if (populacaoTotal <= 0) {
            throw new AssertionError("Test 3 falhou: População total inesperada: " + populacaoTotal);
        }

        // Test 4: TipoEntidade exists
        TipoEntidade cidade = TipoEntidade.CIDADE;
        TipoEntidade pais = TipoEntidade.PAIS;
        if (cidade == null || pais == null) {
            throw new AssertionError("Test 4 falhou: TipoEntidade não definido corretamente");
        }

        // Test 5: parseFiles invalid folder
        boolean resultInvalid = Main.parseFiles(new File("nonexistent"));
        if (resultInvalid) {
            throw new AssertionError("Test 5 falhou: parseFiles retornou true para pasta inexistente");
        }

        System.out.println("Todos os testes passaram: " + Main.getCidades().size() + " cidades carregadas, população total = " + populacaoTotal);
    }
}
