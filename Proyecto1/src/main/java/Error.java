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

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
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
