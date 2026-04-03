import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        File ficheiroCidades = new File("cidades.csv"); 
        Scanner scanner  = new Scanner(ficheiroCidades);

        while (scanner.hasNext()) {
            String linha = scanner.nextLine();
            System.out.println("linha =" + linha);

           String[] partes = linha.split(";");

           int alfa2 = Integer.parseInt(partes[0]);
           int cidade = Integer.parseInt(partes[1]);
           int regiao = Integer.parseInt(partes[2]);

           int populacao = Integer.parseInt(partes[3]);
           int latitude = Integer.parseInt(partes[4]);
           int longitude = Integer.parseInt(partes[5]);
        }
    }


    
}
