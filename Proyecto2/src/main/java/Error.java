public class Error {
    private String caracter;
    private String descripcion;
    private int linea;
    private int columna;
    private String tipo; //Léxico o sintáctico

    public Error(String caracter, String descripcion, int linea, int columna, String tipo) {
        this.caracter = caracter;
        this.descripcion = descripcion;
        this.linea = linea;
        this.columna = columna;
        this.tipo = tipo;
    }

    public String getCaracter() {
        return caracter;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Error{" +
                "caracter='" + caracter + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
