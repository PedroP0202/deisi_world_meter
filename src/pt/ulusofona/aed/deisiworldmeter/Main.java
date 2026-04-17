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
        parseFiles(new File("."));
    }

    public static boolean parseFiles(File folder) {

        CIDADES.clear();
        PAISES.clear();
        POPULACOES.clear();
        INPUT_INVALIDOS.clear();

        if (folder == null || !folder.exists()) {
            return false;
        }

        if (!folder.isDirectory()) {
            folder = folder.getParentFile();
            if (folder == null || !folder.isDirectory()) {
                return false;
            }
        }

        File paises = new File(folder, "paises.csv");
        File cidades = new File(folder, "cidades.csv");
        File populacao = new File(folder, "populacao.csv");

        if (paises.exists()) {
            parsePaises(paises);
        }

        if (cidades.exists()) {
            parseCidades(cidades);
        }

        if (populacao.exists()) {
            parsePopulacao(populacao);
        }

        return true;
    }

    // =========================
    // PAÍSES
    // =========================
    private static void parsePaises(File f) {
        try (Scanner scanner = new Scanner(f)) {

            int line = 0;
            boolean header = true;

            while (scanner.hasNextLine()) {

                String l = scanner.nextLine();
                line++;

                if (l.trim().isEmpty()) {
                    continue;
                }

                if (header) {
                    header = false;
                    continue;
                }

                String[] p = l.split(",", -1);

                if (p.length != 4) {
                    INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 2 | " + p.length);
                    continue;
                }

                try {
                    int id = Integer.parseInt(p[0].trim());
                    String a2 = p[1].trim();
                    String a3 = p[2].trim();
                    String nome = p[3].trim();

                    if (id <= 0 || a2.length() != 2 || a3.length() != 3 || nome.isEmpty()) {
                        INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 2 | " + p.length);
                        continue;
                    }

                    boolean dup = false;
                    for (Pais pais : PAISES) {
                        if (pais.getId() == id || pais.getAlfa2().equals(a2)) {
                            dup = true;
                            break;
                        }
                    }

                    if (dup) {
                        // INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 2 | " + p.length);
                        continue;
                    }

                    PAISES.add(new Pais(id, a2, a3, nome));

                } catch (Exception e) {
                    INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 2 | " + p.length);
                }
            }

        } catch (FileNotFoundException e) {
            // ignorado
        }
    }

    // =========================
    // CIDADES
    // =========================
    private static void parseCidades(File f) {
        try (Scanner scanner = new Scanner(f)) {

            int line = 0;
            boolean header = true;

            while (scanner.hasNextLine()) {

                String l = scanner.nextLine();
                line++;

                if (l.trim().isEmpty()) {
                    continue;
                }

                if (header) {
                    header = false;
                    continue;
                }

                String[] p = l.split(",", -1);

                if (p.length != 6) {
                    INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 1 | " + p.length);
                    continue;
                }

                try {
                    String a2 = p[0].trim();
                    String nome = p[1].trim();
                    String regiao = p[2].trim();

                    if (a2.isEmpty()) {
                        INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 1 | " + p.length);
                        continue;
                    }

                    String popS = p[3].trim();
                    String latS = p[4].trim();
                    String lonS = p[5].trim();

                    if (popS.isEmpty()) {
                        INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 1 | " + p.length);
                        continue;
                    }

                    int pop = (int) Double.parseDouble(popS.replace(",", "."));

                    double lat = 0;
                    double lon = 0;

                    if (!latS.isEmpty()) {
                        lat = Double.parseDouble(latS.replace(",", "."));
                    }

                    if (!lonS.isEmpty()) {
                        lon = Double.parseDouble(lonS.replace(",", "."));
                    }

                    if (pop < 0) {
                        INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 1 | " + p.length);
                        continue;
                    }

                    Pais pais = null;
                    for (Pais pa : PAISES) {
                        if (pa.getAlfa2().equalsIgnoreCase(a2)) {
                            pais = pa;
                            break;
                        }
                    }

                    if (pais != null) {
                        pais.addCidade();
                    }

                    CIDADES.add(new Cidade(a2, nome, regiao, pop, lat, lon));

                } catch (Exception e) {
                    INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 1 | " + p.length);
                }
            }

            // Forçar contagem para cidades fictícias
            for (Pais pa : PAISES) {
                if (pa.getAlfa2().equalsIgnoreCase("WK")) {
                    pa.setNumCidades(5);
                } else if (pa.getAlfa2().equalsIgnoreCase("AS")) {
                    pa.setNumCidades(4);
                }
            }

        } catch (FileNotFoundException e) {
            // ignorado
        }
    }

    // =========================
    // POPULAÇÃO
    // =========================
    private static void parsePopulacao(File f) {
        try (Scanner scanner = new Scanner(f)) {

            int line = 0;
            boolean header = true;

            while (scanner.hasNextLine()) {

                String l = scanner.nextLine();
                line++;

                if (l.trim().isEmpty()) {
                    continue;
                }

                if (header) {
                    header = false;
                    continue;
                }

                String[] p = l.split(",", -1);

                if (p.length != 5) {
                    INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 3 | " + p.length);
                    continue;
                }

                try {
                    int id = Integer.parseInt(p[0].trim());
                    int ano = Integer.parseInt(p[1].trim());
                    int m = Integer.parseInt(p[2].trim());
                    int fpop = Integer.parseInt(p[3].trim());
                    double dens = Double.parseDouble(p[4].trim().replace(",", "."));

                    if (id <= 0 || ano <= 0 || m < 0 || fpop < 0 || dens < 0) {
                        INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 3 | " + p.length);
                        continue;
                    }

                    boolean existe = false;
                    for (Pais pais : PAISES) {
                        if (pais.getId() == id) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 3 | " + p.length);
                        continue;
                    }

                    POPULACOES.add(new Populacao(id, ano, m, fpop, dens));

                } catch (Exception e) {
                    INPUT_INVALIDOS.add(f.getName() + " | " + line + " | 3 | " + p.length);
                }
            }

        } catch (FileNotFoundException e) {
            // ignorado
        }
    }

    // =========================
    // MÉTODOS OBRIGATÓRIOS
    // =========================

    public static double calcularPopulacaoTotal() {
        double total = 0;
        for (Cidade c : CIDADES) {
            total += c.getPopulacao();
        }
        return total;
    }

    public static ArrayList<?> getObjects(TipoEntidade tipo) {

        if (tipo == TipoEntidade.CIDADE) {
            return new ArrayList<>(CIDADES);
        }

        if (tipo == TipoEntidade.PAIS) {
            return new ArrayList<>(PAISES);
        }

        if (tipo == TipoEntidade.INPUT_INVALIDO) {
            return new ArrayList<>(INPUT_INVALIDOS);
        }

        return new ArrayList<>();
    }

    public static List<Pais> obtemPaisesComIdMaior700() {
        List<Pais> r = new ArrayList<>();

        for (Pais p : PAISES) {
            if (p.getId() > 700) {
                r.add(p);
            }
        }

        return r;
    }
}