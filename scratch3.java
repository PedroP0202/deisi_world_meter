import java.util.*;

public class scratch3 {
    private static String[] splitCsvLine(String linha) {
        List<String> partes = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < linha.length() && linha.charAt(i + 1) == '"') {
                    buffer.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                partes.add(buffer.toString());
                buffer.setLength(0);
            } else {
                buffer.append(c);
            }
        }
        partes.add(buffer.toString());
        return partes.toArray(new String[0]);
    }

    public static void main(String[] args) {
        String[] linhas = {
            "wk,Birnin Zana,01,50000.0,0.0,0.0",
            "wk,Warrior Falls,02,40000.0,0.5,0.5",
            "wk,Szaa,03,30000.0,-0.5,-0.5",
            "wk,Pembroke,04,25000.0,1.0,1.0",
            "wk,Golden City,05,45000.0,-1.0,-1.0"
        };
        for (String linha : linhas) {
            String[] partes = splitCsvLine(linha);
            System.out.println("Line: " + linha + " -> Parts: " + partes.length);
        }
    }
}
