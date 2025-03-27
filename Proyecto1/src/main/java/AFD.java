import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class AFD {
    private String nombre;
    private String estadoInicial;
    private List<String> estadosFinales;
    private Map<String, Map<String, String>> transiciones;

    public AFD(String nombre) {
        this.nombre = nombre;
        this.estadosFinales = new ArrayList<>();
        this.transiciones = new HashMap<>();
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public void agregarEstadoFinal(String estado){
        this.estadosFinales.add(estado);
    }

    public void agregarTransicion(String estadoActual, String entrada, String estadoDestino){
        transiciones.putIfAbsent(estadoActual, new HashMap<>());
        transiciones.get(estadoActual).put(entrada, estadoDestino);

    }



}
