package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final List<Cidade> CIDADES = new ArrayList<>();
    private static final List<Pais> PAISES = new ArrayList<>();
    private static final List<Populacao> POPULACOES = new ArrayList<>();
    private static final List<String> INPUT_INVALIDOS = new ArrayList<>();

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
        CIDADES.clear();
        PAISES.clear();
        POPULACOES.clear();
        INPUT_INVALIDOS.clear();
        
        File ficheiroPaises = new File(folder, "paises.csv");
        if (ficheiroPaises.exists()) {
            parsePaises(ficheiroPaises);
        } else {
            System.err.println("Arquivo não encontrado: " + ficheiroPaises.getPath());
        }

        File ficheiroCidades = new File(folder, "cidades.csv");
        if (ficheiroCidades.exists()) {
            parseCidades(ficheiroCidades);
        } else {
            System.err.println("Arquivo não encontrado: " + ficheiroCidades.getPath());
        }

        File ficheiroPopulacao = new File(folder, "populacao.csv");
        if (ficheiroPopulacao.exists()) {
            parsePopulacao(ficheiroPopulacao);
        } else {
            System.err.println("Arquivo não encontrado: " + ficheiroPopulacao.getPath());
        }
        
        return !CIDADES.isEmpty() || !PAISES.isEmpty() || !POPULACOES.isEmpty();
    }

    private static void parsePaises(File f) {
        try (Scanner scanner = new Scanner(f)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(",");
                if (partes.length != 4) {
                    INPUT_INVALIDOS.add(linha);
                    continue;
                }
                try {
                    int id = Integer.parseInt(partes[0].trim());
                    String alfa2 = partes[1].trim();
                    String alfa3 = partes[2].trim();
                    String nome = partes[3].trim();
                    PAISES.add(new Pais(id, alfa2, alfa3, nome));
                } catch (Exception e) {
                    INPUT_INVALIDOS.add(linha);
                }
            }
        } catch (FileNotFoundException e) {
            // Ignorar ficheiro nao encontrado
        }
    }

    private static void parseCidades(File f) {
        try (Scanner scanner = new Scanner(f)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(",");
                if (partes.length != 6) {
                    INPUT_INVALIDOS.add(linha);
                    continue;
                }
                try {
                    String alfa2 = partes[0].trim();
                    String nome = partes[1].trim();
                    String regiao = partes[2].trim();
                    String popStr = partes[3].trim();
                    double populacao = popStr.isEmpty() ? 0.0 : Double.parseDouble(popStr);
                    double latitude = Double.parseDouble(partes[4].trim());
                    double longitude = Double.parseDouble(partes[5].trim());
                    CIDADES.add(new Cidade(alfa2, nome, regiao, populacao, latitude, longitude));
                } catch (Exception e) {
                    INPUT_INVALIDOS.add(linha);
                }
            }
        } catch (FileNotFoundException e) {
            // Ignorar ficheiro nao encontrado
        }
    }

    private static void parsePopulacao(File f) {
        try (Scanner scanner = new Scanner(f)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(",");
                if (partes.length != 5) {
                    INPUT_INVALIDOS.add(linha);
                    continue;
                }
                try {
                    int id = Integer.parseInt(partes[0].trim());
                    int ano = Integer.parseInt(partes[1].trim());
                    int popM = Integer.parseInt(partes[2].trim());
                    int popF = Integer.parseInt(partes[3].trim());
                    double densidade = Double.parseDouble(partes[4].trim());
                    POPULACOES.add(new Populacao(id, ano, popM, popF, densidade));
                } catch (Exception e) {
                    INPUT_INVALIDOS.add(linha);
                }
            }
        } catch (FileNotFoundException e) {
            // Ignorar ficheiro nao encontrado
        }
    }

    public static ArrayList<?> getObjects(TipoEntidade tipo) {
        if (tipo == TipoEntidade.CIDADE) {
            return new ArrayList<>(CIDADES);
        } else if (tipo == TipoEntidade.PAIS) {
            return new ArrayList<>(PAISES);
        } else if (tipo == TipoEntidade.INPUT_INVALIDO) {
            return new ArrayList<>(INPUT_INVALIDOS);
        }
        return new ArrayList<>();
    }

    public static List<Cidade> getCidades() {
        return new ArrayList<>(CIDADES);
    }

    public static double calcularPopulacaoTotal() {
        double total = 0;
        for (Cidade cidade : CIDADES) {
            total += cidade.getPopulacao();
        }
        return total;
    }

    public static void printCidades() {
        for (Cidade cidade : CIDADES) {
            System.out.println(cidade);
        }
    }
}
