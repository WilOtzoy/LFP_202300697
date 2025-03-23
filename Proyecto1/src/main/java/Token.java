public class Token {
    private String lexema;
    private int linea;
    private int columna;
    private String tipo;

    //mi cosntructor
    public Token(String lexema, int linea, int columna, String tipo) {
        this.lexema = lexema;
        this.linea = linea;
        this.columna = columna;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Token{" +
                "lexema='" + lexema + '\'' +
                ", linea=" + linea +
                ", columna=" + columna +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
