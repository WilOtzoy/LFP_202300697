import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class World {
    HashMap<String, Place> locaciones; //<IDentificador del lugar, Lugar>
    HashMap<String, PlaceVisual> locacionesVisuales;

    HashMap<String, Conexion> conexines;
    HashMap<String, Camino> caminos;

    public World() {
        locaciones = new HashMap<>();
        locacionesVisuales = new HashMap<>();
        this.locacionesVisuales.put("playa", new PlaceVisual("ellipse", "lightblue"));
        this.locacionesVisuales.put("cueva", new PlaceVisual("box", "gray"));
        this.locacionesVisuales.put("templo", new PlaceVisual("octagon", "gold"));
        this.locacionesVisuales.put("jungla", new PlaceVisual("parallelogram", "forestgreen"));
        this.locacionesVisuales.put("montana", new PlaceVisual("triangle", "sienna"));
        this.locacionesVisuales.put("pueblo", new PlaceVisual("house", "burlywood"));
        this.locacionesVisuales.put("isla", new PlaceVisual("invtriangle", "lightgoldenrod"));
        this.locacionesVisuales.put("rio", new PlaceVisual("hexagon", "deepskyblue"));
        this.locacionesVisuales.put("volcan", new PlaceVisual("doublecircle", "orangered"));
        this.locacionesVisuales.put("pantano", new PlaceVisual("trapezium", "darkseagreen"));

        caminos = new HashMap<>();
        conexines = new HashMap<>();
        this.caminos.put("camino", new Camino("solid","black"));
        this.caminos.put("puente", new Camino("dotted","gray"));
        this.caminos.put("sendero", new Camino("dashed","saddlebrown"));
        this.caminos.put("carretera", new Camino("solid","darkgray"));
        this.caminos.put("nado", new Camino("dashed","deepskyblue"));
        this.caminos.put("lancha", new Camino("solid","blue"));
        this.caminos.put("teleferico", new Camino("dotted","purple"));

    }


    public void agregarPlace(String identificador, Place place) {
        this.locaciones.put(identificador, place);
    }

    public void agregarConexion(String identificador, Conexion conexion){
        //this.caminos.put(identificador,camino);
        this.conexines.put(identificador,conexion);
    }

    public void graficarConexion() {
        String dotFilePath = "grafo.dot";
        String outputImagePath = "grafo.png";

        /*String dotContent = "digraph G {\n";
        dotContent += "node [style=filled fontname=\"Times-Roman\"];\n";
        dotContent += "graph [layout=neato, splines=true, overlap=false];\n";

        //NODOS
        for (Map.Entry<String, Place> entry : locaciones.entrySet()) {
            String identificador = entry.getKey();
            Place lugar = entry.getValue();

            PlaceVisual lugarVisual = this.locacionesVisuales.get(lugar.tipo);

            dotContent += identificador + "[label=\"" + identificador + "\", shape=" + lugarVisual.getFigura()
                    + ", fillcolor= " + lugarVisual.getColor() + ", pos=\"" + lugar.x + "," + lugar.y + "!\"];";
        }*/
        // Inicio del archivo DOT
        StringBuilder dotContent = new StringBuilder();
        dotContent.append("digraph G {\n");
        dotContent.append("node [style=filled fontname=\"Times-Roman\"];\n");
        dotContent.append("graph [layout=neato, splines=true, overlap=false];\n");

        // Agregar nodos (locaciones)
        for (Map.Entry<String, Place> entry : locaciones.entrySet()) {
            String identificador = entry.getKey();
            Place lugar = entry.getValue();

            PlaceVisual lugarVisual = this.locacionesVisuales.get(lugar.tipo);

            dotContent.append(identificador).append("[label=\"").append(identificador)
                    .append("\", shape=").append(lugarVisual.getFigura())
                    .append(", fillcolor=").append(lugarVisual.getColor())
                    .append(", pos=\"").append(lugar.x).append(",").append(lugar.y).append("!\"];\n");
        }

        //Aristas conexiones
        for (Map.Entry<String, Conexion> entry : conexines.entrySet()) {
            Conexion conexion = entry.getValue();
            String lugarA = conexion.placeA;
            String lugarB = conexion.placeB;
            String tipoCamino = conexion.camino;

            // Obtener el estilo y color del camino
            Camino camino = this.caminos.get(tipoCamino);
            if (camino != null) {
                String estilo = camino.tipocamino;
                String color = camino.color;

                // Agregar la conexión al archivo DOT
                dotContent.append(lugarA).append(" -> ").append(lugarB)
                        .append(" [label=\"").append(tipoCamino)
                        .append("\", style=").append(estilo)
                        .append(", color=").append(color).append("];\n");
            }
        }

        // Cerrar el archivo DOT
        dotContent.append("}");

        //dotContent += "}";

        try {
            FileWriter writer = new FileWriter(dotFilePath);
            writer.write(dotContent.toString());
            writer.close(); // Muy importante
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo.");
            e.printStackTrace();
        }

        String[] comando = {"dot", "-Tpng", dotFilePath, "-o", outputImagePath};
        try {
            ProcessBuilder builder = new ProcessBuilder(comando);
            builder.inheritIO();
            Process proceso = builder.start();
            int exitCode = proceso.waitFor();

            if (exitCode == 0) {
                System.out.println("Conversion completada");
            } else {
                System.err.println("Error en la conversion de codigo");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void graficar(){
        String dotFilePath = "grafo.dot";
        String outputImagePath = "grafo.png";

        /*String dotContent = "digraph G {\n";
        dotContent += "node [style=filled fontname=\"Times-Roman\"];\n";
        dotContent += "graph [layout=neato, splines=true, overlap=false];\n";

        for (Map.Entry<String, Place> entry : locaciones.entrySet()) {
            String identificador = entry.getKey();
            Place lugar = entry.getValue();

            PlaceVisual lugarVisual = this.locacionesVisuales.get(lugar.tipo);

            dotContent += identificador + "[label=\"" + identificador + "\", shape=" + lugarVisual.getFigura()
                    + ", fillcolor= " + lugarVisual.getColor() + ", pos=\"" + lugar.x + "," + lugar.y + "!\"];";
        }
        dotContent += "}";

        try {
            FileWriter writer = new FileWriter(dotFilePath);
            writer.write(dotContent);
            writer.close(); // Muy importante
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo.");
            e.printStackTrace();
        }

        String[] comando = {"dot", "-Tpng", dotFilePath, "-o", outputImagePath};
        try {
            ProcessBuilder builder = new ProcessBuilder(comando);
            builder.inheritIO();
            Process proceso = builder.start();
            int exitCode = proceso.waitFor();

            if (exitCode == 0) {
                System.out.println("Conversion completada");
            } else {
                System.err.println("Error en la conversion de codigo");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }*/

        //-------------------------------
        String dotContent = "digraph G {\n";
        dotContent += "node [style=filled fontname=\"Noto Color Emoji\"];\n";
        dotContent += "graph [layout=neato, splines=true, overlap=false];\n";

        for (Map.Entry<String, Place> entry: locaciones.entrySet()){
            String identificador = entry.getKey();
            Place lugar = entry.getValue();

            PlaceVisual lugarVisual = this.locacionesVisuales.get(lugar.tipo);

            dotContent+=identificador+"[label=\""+identificador+"\", shape="+lugarVisual.getFigura()
                    +", fillcolor= "+lugarVisual.getColor()+", pos=\""+lugar.x+","+lugar.y+"!\"];";
        }

        dotContent+="}";


        try{
            FileWriter writer = new FileWriter(dotFilePath);
            writer.write(dotContent);
            writer.close(); // Muy importante
        }catch (IOException e) {
            System.out.println("Error al escribir el archivo.");
            e.printStackTrace();
        }

        String[] comando = {"dot", "-Tpng", dotFilePath, "-o", outputImagePath};
        try{
            ProcessBuilder builder = new ProcessBuilder(comando);
            builder.inheritIO();
            Process proceso = builder.start();
            int exitCode = proceso.waitFor();

            if(exitCode == 0){
                System.out.println("Conversion completada");
            }else{
                System.err.println("Error en la conversion de codigo");
            }
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
