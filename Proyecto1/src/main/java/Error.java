public class Error {
    private String caracter;
    private String descripcion;
    private int linea;
    private int columna;

    public Error(String caracter, String descripcion, int linea, int columna) {
        this.caracter = caracter;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
    }

    @Override
    public String toString() {
        return "Error{" +
                "caracter='" + caracter + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                '}';
    }
}
