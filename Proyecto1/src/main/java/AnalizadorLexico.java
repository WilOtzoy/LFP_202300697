import java.util.ArrayList;
import java.util.List;

public class AnalizadorLexico {
    private List<Token> tokens;
    private List<Error> errores;
    private int linea;
    private int columna;
    private String buffer;
    private int estado;
    private int i;

    public AnalizadorLexico(){
        this.tokens = new ArrayList<>();
        this.errores = new ArrayList<>();
    }

    //agrego token a mi lista de tokens
    public void agregarToken(String caracter, String token, int linea, int columna){
        this.tokens.add(new Token(caracter, linea, columna, token));
        this.buffer = "";
    }

    //muestro como error los caracteres no aceptados en mi lenguaje
    public void agregarError(String caracter, int linea, int columna){
        this.errores.add(new Error(caracter, "Caracter "+caracter+" no reconocido en el lenguaje", linea, columna));
        this.buffer = "";
    }

    public void analizar(StringBuilder cadena) {
        //this.buffer = cadena.toString();
        System.out.println("estoy dentro de analizr");
        this.tokens.clear();
        this.errores.clear();
        this.estado = 0;
        this.i = 0;
        this.buffer = "";

        while(this.i < cadena.length()){
            System.out.println("wile cadena");
            if(this.estado == 0){
                System.out.println("estado 0");
                S0(cadena.charAt(this.i));
            }
            else if(this.estado == 1){
                S1(cadena.charAt(this.i));
            }

            else if(this.estado == 3){
                S3(cadena.charAt(this.i));
            }
            else if(this.estado == 4){
                S4(cadena.charAt(this.i));
            }

            i = i+1;
        }

    }

    // estados del AFD
    public void S0(char caracter){
        System.out.println("estoy en estado 0 reconociendo s2 simbolos");
        //Reconocimiento de símbolos (S2)

        if(caracter == '{'){
            this.agregarToken(String.valueOf(caracter), "llaveApertura", this.linea, this.columna);
            this.columna+=1;
        }else if(caracter == '}'){
            this.agregarToken(String.valueOf(caracter), "llaveCierre", this.linea, this.columna);
            this.columna+=1;
        }else if(caracter == ':'){
            this.agregarToken(String.valueOf(caracter), "dosPuntos", this.linea, this.columna);
            this.columna+=1;
        }else if(caracter == ','){
            this.agregarToken(String.valueOf(caracter), "coma", this.linea, this.columna);
            this.columna+=1;
        }else if(caracter == '"'){
            this.buffer+=caracter;
            this.columna+=1;
            this.estado = 3;
        }else if(caracter == '-'){
            this.buffer+=caracter;
            this.columna+=1;
            this.estado = 4;
        }/*else if(Character.isLetter(caracter)){
            //si es una letra concateno
            this.buffer+=caracter;
            this.columna+=1;
            this.estado = 1;
        }*/
        //Zona de caracteres especiales
        else if(caracter == '\n'){
            this.linea+=1;
            this.columna = 0;
        }
        else if(caracter == ' '){
            this.columna+=1;
        }
        else if(caracter == '\t'){
            this.columna += 4;
        }
        //Zona de errores
        else{
            System.out.println("es un error no reconocido en los tokens");
            this.buffer+= caracter;
            this.agregarError(String.valueOf(caracter), linea, columna);
            this.buffer ="";
            this.columna+=1;
        }
    }

    //estado 1
    public void S1(char caracter){
        if(Character.isLetter(caracter) || Character.isDigit(caracter)){
            this.buffer+= caracter;
            this.columna+=1;
        }else{

            if(this.buffer.equals("descripcion") || this.buffer.equals("estados")){ //Todas las palabras reservadas
                this.agregarToken(this.buffer, "palabraReservada",linea, columna);
            }else{
                this.agregarToken(this.buffer, "identificador",linea, columna);
            }

            //this.agregarToken(this.buffer, "identificador",linea, columna);
            this.estado = 0;
            this.columna+=1;
            this.i -= 1; //Regreso el índice pues ya terminé de reconocer el identificador
        }
    }

    //estado 3
    public void S3(char caracter){
        if(caracter != '"'){
            this.buffer+=caracter;
            this.columna+=1;
        }else{
            //Aceptación
            this.buffer+=caracter;
            this.agregarToken(this.buffer,"cadena",linea, columna);
            this.estado = 0;
            this.columna+=1;

        }
    }

    //estado 4
    public void S4(char caracter){
        if(caracter == '>'){
            this.buffer+=caracter;
            this.agregarToken(buffer, "flecha", this.linea, this.columna);
            this.columna+=1;
            this.estado = 0;
        }

        else{
            this.buffer += caracter;
            this.agregarError(String.valueOf(caracter), linea, columna);
            this.columna+=1;
            this.estado = 0;
        }
    }

    //inprimo los tokes encontrados
    public void imprimirTokens() {
        for(Token token: this.tokens){
            System.out.println(token);
        }
    }

    //imprimo los errores encotrados
    public void imprimirErrores() {
        for(Error error: this.errores){
            System.out.println(error);
        }
    }

}
