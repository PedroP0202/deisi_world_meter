import java.util.*;

public class scratch4 {
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
        String linha = "wk,Warrior Falls,02,40000.0,0.5,0.5";
        String[] partes = splitCsvLine(linha);
        for(int i=0; i<partes.length; i++) {
            System.out.println(i + ": " + partes[i]);
        }
    }
}
