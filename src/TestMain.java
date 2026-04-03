import java.io.File;

public class TestMain {

    public static void main(String[] args) {
        boolean result = Main.parseFiles(new File("test-files"));
        if (!result) {
            throw new AssertionError("parseFiles retornou false para test-files");
        }

        if (Main.getCidades().isEmpty()) {
            throw new AssertionError("A lista de cidades carregada está vazia");
        }

        double populacaoTotal = Main.calcularPopulacaoTotal();
        if (populacaoTotal <= 0) {
            throw new AssertionError("População total inesperada: " + populacaoTotal);
        }

        System.out.println("TestMain passou: " + Main.getCidades().size() + " cidades carregadas, população total = " + populacaoTotal);
    }
}
