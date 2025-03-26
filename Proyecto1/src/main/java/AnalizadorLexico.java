import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        //System.out.println("estoy dentro de analizr");
        this.tokens.clear();
        this.errores.clear();
        this.estado = 0;
        this.i = 0;
        this.buffer = "";

        while(this.i < cadena.length()){
            //System.out.println("wile cadena");
            if(this.estado == 0){
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
        //System.out.println("estoy en estado 0 reconociendo s2 simbolos");
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
        }else if(caracter == '('){
            this.agregarToken(String.valueOf(caracter), "parentesisApertura", this.linea, this.columna);
            this.columna+=1;
        }else if(caracter == ')'){
            this.agregarToken(String.valueOf(caracter), "parentesisCierre", this.linea, this.columna);
            this.columna+=1;
        }else if(caracter == '['){
            this.agregarToken(String.valueOf(caracter), "corcheteApertura", this.linea, this.columna);
            this.columna+=1;
        }else if(caracter == ']'){
            this.agregarToken(String.valueOf(caracter), "corcheteCierre", this.linea, this.columna);
            this.columna+=1;
        }else if(caracter == '='){
            this.agregarToken(String.valueOf(caracter), "signoIgual", this.linea, this.columna);
            this.columna+=1;
        }else if(caracter == '"'){
            this.buffer+=caracter;
            this.columna+=1;
            this.estado = 3;
        }else if(caracter == '-'){
            this.buffer+=caracter;
            this.columna+=1;
            this.estado = 4;
        }else if(Character.isLetter(caracter)){
            //si es una letra concateno
            this.buffer+=caracter;
            this.columna+=1;
            this.estado = 1;
        }
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
            //System.out.println("es un error no reconocido en los tokens");
            this.buffer+= caracter;
            this.agregarError(String.valueOf(caracter), linea, columna);
            this.buffer ="";
            this.columna+=1;
        }
    }

    //estado 1
    public void S1(char caracter){
        //System.out.println("Estoy dentro de estado 1 ---------------");
        if(Character.isLetter(caracter) || Character.isDigit(caracter)){
            this.buffer+= caracter;
            this.columna+=1;
        }else{
            // evaluado las palabrase reservadas en el automata
            if(this.buffer.equals("descripcion") || this.buffer.equals("estados") || this.buffer.equals("alfabeto") || this.buffer.equals("inicial") || this.buffer.equals("finales") || this.buffer.equals("transiciones")){
                this.agregarToken(this.buffer, "palabraReservada",linea, columna);
            }else{
                this.agregarToken(this.buffer, "identificador",linea, columna);
            }

            this.estado = 0;
            this.columna+=1;
            this.i -= 1; //Regreso el índice pues ya terminé de reconocer el identificador
        }
    }

    //estado 3
    public void S3(char caracter){
        //System.out.println("Estoy dentro de estado 3 ---------------");
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
       // System.out.println("Estoy dentro de estado 4 ---------------");
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
    /*public void imprimirTokens() {
        for(Token token: this.tokens){
            System.out.println(token);
        }
    }*/

    //imprimo los errores encotrados
    public void imprimirErrores() {
        for(Error error: this.errores){
            System.out.println(error);
        }
    }



    //metodos de html
    public void reporteTokens() {
        String contenido = "<html>";
        contenido+="<head> <title>Reporte tokens</title><link rel=\"stylesheet\" href=\"myCSS.css\"></head>";
        contenido+="<body>";
        contenido+="<h1>Reporte de tokens</h1>";
        contenido+="<table border=\"solid\">";
        contenido+="<tr>";
        contenido+="<th>Token</th><th>Lexema</th><th>Linea</th><th>Columna</th>";
        contenido+="</tr>";

        for (Token token : this.tokens){
            contenido+="<tr>";
            contenido+="<td>"+token.getTipo()+"</td>";
            contenido+="<td>"+token.getLexema()+"</td>";
            contenido+="<td>"+token.getLinea()+"</td>";
            contenido+="<td>"+token.getColumna()+"</td>";
            contenido+="</tr>";
        }

        contenido+="</table>";
        contenido+="</body>";
        contenido+="</html>";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("tokens.html"))) {
            escritor.write(contenido);
            System.out.println("El reporte de tokens a sido generado correctamente");
            // Abrir el archivo en el navegador predeterminado
            File archivoHTML = new File("tokens.html");
            Desktop.getDesktop().browse(archivoHTML.toURI());
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo html: " + e.getMessage());
        }

    }

    public void reporteErrores() {
        String contenido = "<html>";
        contenido+="<head> <title>Errores lexicos</title><link rel=\"stylesheet\" href=\"myCSS.css\"></head>";
        contenido+="<body>";
        contenido+="<h1>Reporte de errores lexicos</h1>";
        contenido+="<table border=\"solid\">";
        contenido+="<tr>";
        contenido+="<th>Caracter</th><th>Descripción</th><th>Linea</th><th>Columna</th>";
        contenido+="</tr>";

        for (Error error : this.errores){
            contenido+="<tr>";
            contenido+="<td>"+error.getCaracter()+"</td>";
            contenido+="<td>"+error.getDescripcion()+"</td>";
            contenido+="<td>"+error.getLinea()+"</td>";
            contenido+="<td>"+error.getColumna()+"</td>";
            contenido+="</tr>";
        }

        contenido+="</table>";
        contenido+="</body>";
        contenido+="</html>";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("errores.html"))) {
            escritor.write(contenido);
            System.out.println("El reporte de errores lexicos a sido generado correctamente");
            // Abrir el archivo en el navegador predeterminado
            File archivoHTML = new File("errores.html");
            Desktop.getDesktop().browse(archivoHTML.toURI());
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo html: " + e.getMessage());
        }

    }
}
