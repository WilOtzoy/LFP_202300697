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
        this.tokens.clear();
        this.errores.clear();
        this.estado = 0;
        this.i = 0;
        this.buffer = "";

        while(this.i < cadena.length()){
            if(this.estado == 0){
                S0(cadena.charAt(this.i));
            }
            /*else if(this.estado == 1){
                //S1(cadena.charAt(this.i));
            }

            else if(this.estado == 3){
                //S3(cadena.charAt(this.i));
            }
            else if(this.estado == 4){
                //S4(cadena.charAt(this.i));
            }

            i = i+1;*/
        }

    }

    // estados del AFD
    public void S0(char caracter){
        //this.buffer = this.buffer + caracter;
        //this.estado = 1;

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
        }

        /*
        else if(Character.isLetter(caracter)){
            this.buffer+=caracter;
            this.columna+=1;
            this.estado = 1;
        } */



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
            this.buffer+= caracter;
            this.agregarError(String.valueOf(caracter), linea, columna);
            this.buffer ="";
            this.columna+=1;
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
