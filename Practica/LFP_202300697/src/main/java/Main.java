import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
                        if (personajeList != null){
                            personajeList.clear();
                            cargarArchivo();
                        }else{
                            cargarArchivo();
                        }
                        break;
                    case 2:
                        if (personajeList != null){
                            //el torneo comienza
                            jugar(personajeList);
                        }else{
                            System.out.println("No hay personajes.");
                        }
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

    //jugar
    public static void jugar(List<Personaje> personajes){
        List<Personaje> sobrevivientes = new ArrayList<>(personajes);
        int ronda = 1;
        //que si exista un combate de pareja
        while (sobrevivientes.size() > 1) {
            System.out.println("\n* * *  R O N D A  " + ronda + " * * *");
            List<Personaje> ganadores = new ArrayList<>();

            for (int i = 0; i < sobrevivientes.size(); i += 2) {
                if (i + 1 < sobrevivientes.size()) {
                    Personaje ganador = combate(sobrevivientes.get(i), sobrevivientes.get(i + 1));
                    ganadores.add(ganador);
                } else {
                    //indica q el numero es impar de sobrevivientes por lo que pasa el primero en atomaticamente a la sig, ronda
                    System.out.println(sobrevivientes.get(i).nombre + " avanza automáticamente a la siguiente ronda.");
                    ganadores.add(sobrevivientes.get(i));
                }
            }
            sobrevivientes = ganadores;
            ronda++;
        }
        //el tornero finaliza la lista se reducira en 1 por lo que estaria en la pos 0
        System.out.println("\nEl campeón del torneo es: " + "'" +sobrevivientes.get(0).nombre + "'");
    }
    public static Personaje combate(Personaje p1, Personaje p2) {
        System.out.println("\nBatalla entre: " + p1.nombre + " vs " + p2.nombre);

        //evaluar que sus vidas no sean 0
        while (p1.estaVivo() && p2.estaVivo()) {
            atacar(p1, p2);
            if (p2.estaVivo()) {
                atacar(p2, p1);
            }
        }
        Personaje ganador = p1.estaVivo() ? p1 : p2;
        System.out.println("Ganador: " + ganador.nombre);
        return ganador;
    }

    public static void atacar(Personaje jugador1, Personaje jugador2) {
        int danio = jugador1.ataque - jugador2.defensa;
        //evaluo si el daño>0  osea si hubo daño  procedo a restarlo en la vida del jugador2
        if (danio > 0) {
            jugador2.vida -= danio;
            System.out.println(jugador1.nombre + " atacando  " +  jugador2.nombre + " causando un daño de: "  + danio + "pts. " + jugador2.nombre + " le queda " + jugador2.vida  + " de vida.");
        } else {
            //el jugador 1 no le causo daño a jugador 2
            System.out.println(jugador1.nombre + " no pudo hacer daño a " + jugador2.nombre);

        }
    }

    //mayor ataque
    public static void reporteMayorAtaque(){

        // Ordenar la lista de personajes de mayor a menor según el ataque
        Collections.sort(personajeList, new Comparator<Personaje>() {

            @Override
            public int compare(Personaje p1, Personaje p2) {
                // Ordenar de mayor a menor
                return Integer.compare(p2.getAtaque(), p1.getAtaque());
            }
        });
            //una vez ya lo tengo ordenado porcedo a crear mi html para mostrarlo en tabla
        String contenido = "<html>";
        contenido+="<head> <title>Personajes de combate</title><link rel=\"stylesheet\" href=\"myCSS.css\"></head>";
        contenido+="<body>";
        contenido+="<h1>Personajes con mayor ataque</h1>";
        contenido+="<table border=\"solid\">";
        contenido+="<tr>";
        contenido+="<th>Nombre Personaje</th><th>Salud</th><th>Ataque</th><th>Defensa</th>";
        contenido+="</tr>";

            //aca creo otra lista donde solo halla los top 5  como ya esta ordenado solo paso lso prieros 5
            List<Personaje> top5MayorAtaque = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
            top5MayorAtaque.add(personajeList.get(i));
            }

        //la lista ya esta ordenada, procedo a imprimirlos en tabla
        for (Personaje personaje : top5MayorAtaque){
            contenido+="<tr>";
            contenido+="<td>"+personaje.nombre+"</td>";
            contenido+="<td>"+personaje.salud+"</td>";
            contenido+="<td>"+personaje.ataque+"</td>";
            contenido+="<td>"+personaje.defensa+"</td>";
            contenido+="</tr>";
        }

        contenido+="</table>";
        contenido+="</body>";
        contenido+="</html>";
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("reporteMayorAtaque.html"))) {
            escritor.write(contenido);

            System.out.println("El reporte de personajes con mayor ataque a sido generado correctamente");
            // Abrir el archivo en el navegador predeterminado
            File archivoHTML = new File("reporteMayorAtaque.html");
            Desktop.getDesktop().browse(archivoHTML.toURI());
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo html: " + e.getMessage());
        }

        //contenido.concat("<html>");
    }

    //mayor defensa
    public static void reporteMayorDefensa(){
        // Ordenar la lista de personajes de mayor a menor según la defensa
        Collections.sort(personajeList, new Comparator<Personaje>() {

            @Override
            public int compare(Personaje p1, Personaje p2) {
                // Ordenar de mayor a menor
                return Integer.compare(p2.getDefensa(), p1.getDefensa());
            }
        });
        //una vez ya lo tengo ordenado porcedo a crear mi html para mostrarlo en tabla
        String contenido = "<html>";
        contenido+="<head> <title>Personajes de combate</title><link rel=\"stylesheet\" href=\"myCSS.css\"></head>";
        contenido+="<body>";
        contenido+="<h1>Personajes con mayor defensa</h1>";
        contenido+="<table border=\"solid\">";
        contenido+="<tr>";
        contenido+="<th>Nombre Personaje</th><th>Salud</th><th>Ataque</th><th>Defensa</th>";
        contenido+="</tr>";

        //aca creo otra lista donde solo halla los top 5  como ya esta ordenado solo paso lso prieros 5
        List<Personaje> top5MayorDefensa = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            top5MayorDefensa.add(personajeList.get(i));
        }

        //la lista ya esta ordenada, procedo a imprimirlos en tabla
        for (Personaje personaje : top5MayorDefensa){
            contenido+="<tr>";
            contenido+="<td>"+personaje.nombre+"</td>";
            contenido+="<td>"+personaje.salud+"</td>";
            contenido+="<td>"+personaje.ataque+"</td>";
            contenido+="<td>"+personaje.defensa+"</td>";
            contenido+="</tr>";
        }

        contenido+="</table>";
        contenido+="</body>";
        contenido+="</html>";
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("reporteMayorDefensa.html"))) {
            escritor.write(contenido);

            System.out.println("El reporte de personajes con mayor defensa a sido generado correctamente");
            // Abrir el archivo en el navegador predeterminado
            File archivoHTML = new File("reporteMayorDefensa.html");
            Desktop.getDesktop().browse(archivoHTML.toURI());
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo html: " + e.getMessage());
        }

    }



}
