import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    //mi lista donde alacenare los valores de mi archivo .lfp
    public static ArrayList<Personaje> personajeList = new ArrayList();

    public static void main(String[] args) {
        try {
            boolean fin = false;
            Scanner scanner = new Scanner(System.in);
            int opt;

            //cargarArchivo();
            while (!fin) {
                System.out.println("***C H A P I N * G A M E S***");
                System.out.println("========================");
                System.out.println("[1] - Cargar Archivo");
                System.out.println("[2] - Jugar");
                System.out.println("[3] - Generar Reporte de mayor ataque");
                System.out.println("[4] - Generar Reporte con mayor defensa");
                System.out.println("[5] - Mostrar informacion del desarrolador");
                System.out.println("[6] - Salir");

                opt = scanner.nextInt();

                switch (opt) {
                    case 1:
                        cargarArchivo();
                        break;
                    case 2:
                        // Jugar
                        //escrituraArchivoHtml();
                        break;
                    case 3:
                        //reporte con mayor ataque
                        reporteMayorAtaque();
                        break;
                    case 4:
                        //reporte con mayor defensa
                        reporteMayorDefensa();
                        break;
                    case 5:
                        System.out.println("***Desarrollador***");
                        System.out.println("==================");
                        System.out.println("Nombre: Wilson Wilfredo Pérez Otzoy");
                        System.out.println("Carnet: 202300697");
                        break;
                    case 6:
                        System.exit(0);
                    default:
                        System.out.println("Opción Inválida");
                }
            }
        }catch (Exception e){e.printStackTrace();}

    }

    public static void  cargarArchivo(){
        //Crear el selector de archivos
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showOpenDialog(null);

        //Si el usuario seleccionó algun archivo
        if (selection == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String ruta = archivoSeleccionado.getAbsolutePath();

            try {
                File archivo = new File(ruta);

                if (archivo.exists()) {
                    BufferedReader br = new BufferedReader(new FileReader(archivo));
                    String linea;
                    br.readLine(); //Saltar la primera linea el encabezado del archivo

                    while ((linea = br.readLine()) != null) {
                        System.out.println(linea);
                        String[] datos = linea.split("\\|");

                        //Encontrando los datos
                        String name = datos[0].trim();
                        int health = Integer.parseInt(datos[1].trim());
                        int attack = Integer.parseInt(datos[2].trim());
                        int defense = Integer.parseInt(datos[3].trim());

                        //lo agrego a mi lista previamiente creada
                        Personaje character = new Personaje(name,health,attack,defense);
                        personajeList.add(character);
                    }
                } else {
                    System.out.println("Error: el archivo no existe :(");
                }
            } catch (IOException e) {
                System.out.println("Error al leer el archivo " + e.getMessage());
            }

        } else {
            System.out.println("No se seleccionó ningún archivo");
        }
    }

    //mayor ataque
    public static void reporteMayorAtaque(){

        // Ordenar la lista de estudiantes de mayor a menor según la edad
        /*Collections.sort(personajeList, new Comparator<Personaje>() {

            @Override
            public int compare(Personaje p1, Personaje p2) {
                return Integer.compare(p2.getAtaque(), p1.getAtaque()); // Ordenar de mayor a menor
            }
        });
*/
        //mio
        Collections.sort(personajeList, Collections.reverseOrder());


        for (Personaje personaje : personajeList){
            System.out.println(personaje);
        }

    }

    //mayor defensa
    public static void reporteMayorDefensa(){

    }



}
