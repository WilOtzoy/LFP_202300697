import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {
        //System.out.println("holamundo");
        AnalizadorLexico analizadorLexico = new AnalizadorLexico();

        try {
            File archivo = new File("C:\\Users\\wilso\\Downloads\\Entrada1.lfp");
            StringBuilder content = new StringBuilder();
            if (archivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(archivo));
                String linea;
                while ((linea = br.readLine()) != null) {
                    content.append(linea).append("\n");
                }

                System.out.println(content);
                analizadorLexico.analizar(content);

                analizadorLexico.imprimirTokens();
                System.out.println("");

                analizadorLexico.imprimirErrores();
            } else {
                System.out.println("El archivo no existe.");
            }
        }
        catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

    }
}
