import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final List<Cidade> cidades = new ArrayList<>();

    public static void main(String[] args) {
        boolean result = parseFiles(new File("."));
        if (result) {
            printCidades();
            System.out.println("População total das cidades lidas: " + calcularPopulacaoTotal());
        } else {
            System.err.println("Falha ao carregar os ficheiros.");
        }
    }

    public static boolean parseFiles(File folder) {
        cidades.clear();
        File ficheiroCidades = new File(folder, "cidades.csv");

        if (!ficheiroCidades.exists()) {
            System.err.println("Arquivo não encontrado: " + ficheiroCidades.getPath());
            return false;
        }

        try (Scanner scanner = new Scanner(ficheiroCidades)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(",");

                if (partes.length != 6) {
                    System.err.println("Linha inválida: " + linha);
                    continue;
                }

                try {
                    String alfa2 = partes[0].trim();
                    String nome = partes[1].trim();
                    String regiao = partes[2].trim();
                    double populacao = partes[3].trim().isEmpty() ? 0.0 : Double.parseDouble(partes[3].trim());
                    double latitude = Double.parseDouble(partes[4].trim());
                    double longitude = Double.parseDouble(partes[5].trim());

                    Cidade cidade = new Cidade(alfa2, nome, regiao, populacao, latitude, longitude);
                    cidades.add(cidade);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao parsear números na linha: " + linha);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
            return false;
        }

        return !cidades.isEmpty();
    }

    public static List<Cidade> getCidades() {
        return new ArrayList<>(cidades);
    }

    public static double calcularPopulacaoTotal() {
        double total = 0;
        for (Cidade cidade : cidades) {
            total += cidade.getPopulacao();
        }
        return total;
    }

    public static void printCidades() {
        for (Cidade cidade : cidades) {
            System.out.println(cidade);
        }
    }
}
