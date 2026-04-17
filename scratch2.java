import java.util.*;

public class scratch2 {
    public static void main(String[] args) {
        String[] linhas = {
            "wk,Birnin Zana,01,50000.0,0.0,0.0",
            "wk,Warrior Falls,02,40000.0,0.5,0.5",
            "wk,Szaa,03,30000.0,-0.5,-0.5",
            "wk,Pembroke,04,25000.0,1.0,1.0",
            "wk,Golden City,05,45000.0,-1.0,-1.0"
        };
        for (String linha : linhas) {
            System.out.println("Line: " + linha);
            String[] partes = linha.split(",");
            if (partes.length != 6) {
                System.out.println("  Rejeitada: length != 6"); continue;
            }
            try {
                String alfa2 = partes[0].trim();
                String popStr = partes[3].trim();
                if (popStr.isEmpty()) { } else { Double.parseDouble(popStr); }
                int pop = popStr.isEmpty() ? 0 : (int)Double.parseDouble(popStr);
                if (pop < 0) { System.out.println("  Rejeitada pop"); continue; }
                System.out.println("  Valid!");
            } catch (Exception e) {
                System.out.println("  Exception: " + e.getMessage());
            }
        }
    }
}
