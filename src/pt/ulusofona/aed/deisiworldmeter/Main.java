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
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linha.split(",", -1);
                if (partes.length != 4) {
                    INPUT_INVALIDOS.add(linha);
                    continue;
                }
                try {
                    String idStr = partes[0].trim();
                    String alfa2 = partes[1].trim();
                    String alfa3 = partes[2].trim();
                    String nome = partes[3].trim();

                    if (idStr.isEmpty() || alfa2.isEmpty() || alfa3.isEmpty() || nome.isEmpty()) {
                        INPUT_INVALIDOS.add(linha);
                        continue;
                    }

                    int id = Integer.parseInt(idStr);
                    if (id <= 0 || !alfa2.matches("[a-zA-Z]{2}") || !alfa3.matches("[a-zA-Z]{3}")) {
                        INPUT_INVALIDOS.add(linha);
                        continue;
                    }

                    boolean duplicate = false;
                    for (Pais pais : PAISES) {
                        if (pais.getId() == id || pais.getAlfa2().equalsIgnoreCase(alfa2)) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (duplicate) {
                        INPUT_INVALIDOS.add(linha);
                        continue;
                    }

                    Pais novoPais = new Pais(id, alfa2, alfa3, nome);
                    PAISES.add(novoPais);
                } catch (Exception e) {
                    INPUT_INVALIDOS.add(linha);
                }
            }
        } catch (FileNotFoundException e) {
            
        }
    }

    private static void parseCidades(File f) {
        try (Scanner scanner = new Scanner(f)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linha.split(",", -1);
                if (partes.length != 6) {
                    INPUT_INVALIDOS.add(linha);
                    continue;
                }
                try {
                    String alfa2 = partes[0].trim();
                    String nome = partes[1].trim();
                    String regiao = partes[2].trim();
                    String popStr = partes[3].trim();
                    String latStr = partes[4].trim();
                    String lonStr = partes[5].trim();

                    if (alfa2.isEmpty() || nome.isEmpty() || regiao.isEmpty() || popStr.isEmpty() || latStr.isEmpty() || lonStr.isEmpty()) {
                        INPUT_INVALIDOS.add(linha);
                        continue;
                    }

                    int populacao = (int) Double.parseDouble(popStr);
                    double latitude = Double.parseDouble(latStr);
                    double longitude = Double.parseDouble(lonStr);
                    if (populacao < 0 || latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
                        INPUT_INVALIDOS.add(linha);
                        continue;
                    }

                    Pais paisDaCidade = null;
                    for (Pais p : PAISES) {
                        if (p.getAlfa2().equalsIgnoreCase(alfa2)) {
                            paisDaCidade = p;
                            break;
                        }
                    }
                    if (paisDaCidade == null) {
                        INPUT_INVALIDOS.add(linha);
                        continue;
                    }

                    CIDADES.add(new Cidade(alfa2, nome, regiao, populacao, latitude, longitude));
                    paisDaCidade.addCidade();
                } catch (Exception e) {
                    INPUT_INVALIDOS.add(linha);
                }
            }
        } catch (FileNotFoundException e) {
            
        }
    }

    private static void parsePopulacao(File f) {
        try (Scanner scanner = new Scanner(f)) {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linha.split(",", -1);
                if (partes.length != 5) {
                    INPUT_INVALIDOS.add(linha);
                    continue;
                }
                try {
                    String idStr = partes[0].trim();
                    String anoStr = partes[1].trim();
                    String popMStr = partes[2].trim();
                    String popFStr = partes[3].trim();
                    String densidadeStr = partes[4].trim();

                    if (idStr.isEmpty() || anoStr.isEmpty() || popMStr.isEmpty() || popFStr.isEmpty() || densidadeStr.isEmpty()) {
                        INPUT_INVALIDOS.add(linha);
                        continue;
                    }

                    int id = Integer.parseInt(idStr);
                    int ano = Integer.parseInt(anoStr);
                    int popM = Integer.parseInt(popMStr);
                    int popF = Integer.parseInt(popFStr);
                    double densidade = Double.parseDouble(densidadeStr);

                    if (id <= 0 || ano <= 0 || popM < 0 || popF < 0 || densidade < 0) {
                        INPUT_INVALIDOS.add(linha);
                        continue;
                    }

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
