import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class World {
    HashMap<String, Place> locaciones; //<IDentificador del lugar, Lugar>
    HashMap<String, PlaceVisual> locacionesVisuales;

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
    }

    public void agregarPlace(String identificador, Place place) {
        this.locaciones.put(identificador, place);
    }

    public void graficar() {
        String dotFilePath = "grafo.dot";
        String outputImagePath = "grafo.png";

        String dotContent = "digraph G {\n";
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
        }
    }
}
