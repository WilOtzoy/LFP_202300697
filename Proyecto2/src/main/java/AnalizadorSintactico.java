import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalizadorSintactico {
    private List<Token> tokens;
    private List<Error> erroresSintacticos;
    HashMap<String, World> mundos; //<IDentificador del mundo, mundo>
    String mundoActual;

//    AnalizadorLexico analizadorLexico = new AnalizadorLexico();


    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = tokens;
        this.erroresSintacticos = new ArrayList<>();
        this.mundos = new HashMap<>();
        this.mundoActual = "";
    }

    public void agregarError (Token token) {
        this.erroresSintacticos.add(new Error(token.getLexema(), "no se esperaba el token " + token.getLexema(), token.getLinea(), token.getColumna(),"Sintatico"));
    }

    public List<Error> getErrores(){
        return this.erroresSintacticos;
    }

    public void imprimirErroresSintacticos() {
        System.out.println("ERRORES SINTACTICOS*************");
        for(Error error: this.erroresSintacticos){
            System.out.println(error);
        }
    }

    public void analizar(){
        this.INICIO();

        this.mundos.get(mundoActual).graficar();
    }

    //NO TERMINALES
    public void INICIO(){
        System.out.println("INICIO");
        this.MUNDOS();
    }
    public void MUNDOS(){
        System.out.println("MUNDOS");
        this.MUNDO();
        this.MUNDOSP();
    }

    public void MUNDO(){
        System.out.println("MUNDO");
        Token tmp = this.tokens.removeFirst();

        if(tmp.getTipo().equals("pr_world")){
            //Continuamos
            tmp = this.tokens.removeFirst();
            // aca agregando las comillas

                if (tmp.getTipo().equals("cadena")) {

                    this.mundoActual = tmp.getLexema();
                    this.mundos.put(mundoActual, new World());
                    //aca podria agregar la cadena del mundo para mi combobox

                    tmp = this.tokens.removeFirst();

                    //aca agrego mi otra comillas

                        if (tmp.getTipo().equals("llaveApertura")) {
                            this.LOCACIONES();
                            this.CONEXIONES();
                            this.OBJETOS();

                            tmp = this.tokens.removeFirst();

                            if (tmp.getTipo().equals("llaveCierre")) {
                                //Continuo
                                return;
                            } else {
                                //Agrego error
                                this.agregarError(tmp);
                            }
                        } else {
                            //Agrego error
                            this.agregarError(tmp);
                        }

                } else {
                    //Agregar error
                    this.agregarError(tmp);
                }
        }else{
            //AGregar error
            this.agregarError(tmp);

        }
    }

    public void MUNDOSP(){
        System.out.println("MUNDOSP");
        try{
            if(tokens.get(0).getTipo().equals("coma")){
                System.out.println("segundo mundo ");
                this.tokens.removeFirst();
                this.MUNDO();
                this.MUNDOSP();
            }else{
                agregarError(tokens.get(0));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void LOCACIONES(){
        System.out.println("LOCACIONES");
        this.LOCACION();
        this.LOCACIONESP();
    }

    public void LOCACION(){
        System.out.println("LOCACION");
        String identificador;
        String tipo;
        String x;
        String y;
        Token tmp = this.tokens.removeFirst();

        if(tmp.getTipo().equals("pr_place")){

            tmp = this.tokens.removeFirst();

            if(tmp.getTipo().equals("identificador")){
                //El nombre de la locacion
                identificador = tmp.getLexema();
                tmp = this.tokens.removeFirst();
                if(tmp.getTipo().equals("dosPuntos")){
                    tmp = this.tokens.removeFirst();
                    if(tmp.getTipo().equals("pr_locacion")){
                        //Tipo de locacion
                        tipo = tmp.getLexema();
                        tmp = this.tokens.removeFirst();
                        if(tmp.getTipo().equals("pr_at")){
                            tmp = this.tokens.removeFirst();
                            if(tmp.getTipo().equals("parentesisApertura")){
                                tmp = this.tokens.removeFirst();
                                if(tmp.getTipo().equals("numero")){
                                    //Coordenada x
                                    x = tmp.getLexema();
                                    tmp = this.tokens.removeFirst();
                                    if(tmp.getTipo().equals("coma")){
                                        tmp = this.tokens.removeFirst();
                                        if(tmp.getTipo().equals("numero")){
                                            //Coordenada y
                                            y = tmp.getLexema();
                                            tmp = this.tokens.removeFirst();

                                            if(tmp.getTipo().equals("parentesisCierre")){
                                                //Crear una nueva locacion
                                                Place place = new Place(identificador, tipo, x, y);
                                                this.mundos.get(mundoActual).agregarPlace(identificador, place);
                                                return;

                                            }else{
                                                //Error
                                                agregarError(tmp);
                                            }
                                        }else{
                                            //Error
                                            agregarError(tmp);
                                        }
                                    }else{
                                        //Error
                                        agregarError(tmp);
                                    }
                                }else{
                                    //Error
                                    agregarError(tmp);
                                }
                            }else{
                                //Error
                                agregarError(tmp);
                            }
                        }else{
                            //Error
                            agregarError(tmp);
                        }
                    }else{
                        //Error
                        agregarError(tmp);
                    }

                }else{
                    //Error
                    agregarError(tmp);
                }
            }else{
                //Error
                agregarError(tmp);
            }
        }else{
            //Error
            agregarError(tmp);
            System.out.println("Error de pr_place------------");

            getErrores();
            imprimirErroresSintacticos();

        }
    }

    public void LOCACIONESP(){
        System.out.println("LOCACIONESP");
        if(this.tokens.get(0).getTipo().equals("pr_place")){
            this.LOCACION();
            this.LOCACIONESP();
        }else{
            return;
        }
    }

    public void CONEXIONES(){
        System.out.println("CONEXIONES");
        this.CONEXION();
        this.CONEXIONESP();
    }

    public void CONEXION(){
        System.out.println("CONEXION");
        //String identificador;
        String tipoCamino;
        String placeA;
        String placeB;

        Token tmp = this.tokens.removeFirst();

        if(tmp.getTipo().equals("pr_connect")){
            tmp = this.tokens.removeFirst();
            if(tmp.getTipo().equals("identificador")){
                placeA = tmp.getLexema(); //nombre del place A

                tmp = this.tokens.removeFirst();
                if(tmp.getTipo().equals("pr_to")){
                    tmp = this.tokens.removeFirst();
                    if(tmp.getTipo().equals("identificador")){  //nombre del placeB
                        placeB = tmp.getLexema();
                        tmp= this.tokens.removeFirst();
                        if(tmp.getTipo().equals("pr_with")){
                            tmp = this.tokens.removeFirst();
                            if (tmp.getTipo().equals("comillas")){
                                tmp = this.tokens.removeFirst();
                                if(tmp.getTipo().equals("pr_camino")){
                                    tipoCamino = tmp.getLexema();
                                    tmp = this.tokens.removeFirst();
                                    if (tmp.getTipo().equals("comillas")){
                                        //Crear un nuevo camino
                                        //debeo graficar las conexiones del camino enter locaciones
                                        Conexion conexion = new Conexion(placeA, placeB, tipoCamino);
                                        this.mundos.get(mundoActual).agregarConexion(tipoCamino,conexion);

                                        return;
                                    }else{agregarError(tmp);}
                                }else{agregarError(tmp);}
                            }else{agregarError(tmp);}
                        }else {agregarError(tmp);}
                    }else {agregarError(tmp);}
                }else{agregarError(tmp);}
            }else{agregarError(tmp);}
        }else{agregarError(tmp);}
    }
    public void CONEXIONESP(){
        System.out.println("CONEXIONESP");
        if (this.tokens.get(0).getTipo().equals("pr_connect")){
            this.CONEXION();
            this.CONEXIONESP();
        }else{
            return;
        }
    }


    public void OBJETOS(){
        System.out.println("OBJETOS");
        this.OBJETO();
        this.OBJETOSP();
    }

    public void OBJETO(){
        System.out.println("OBJETO");

        Token tmp = this.tokens.removeFirst();

        if(tmp.getTipo().equals("pr_object")){
            tmp = this.tokens.removeFirst();
            if(tmp.getTipo().equals("cadena")){
                tmp = this.tokens.removeFirst();
                if(tmp.getTipo().equals("dosPuntos")){
                    tmp = this.tokens.removeFirst();
                    if(tmp.getTipo().equals("pr_objectoEspecial")){
                        tmp = this.tokens.removeFirst();
                        if(tmp.getTipo().equals("pt_at")){
                            //puede ser un pace o una coordenada
                            tmp = this.tokens.removeFirst();
                            this.LUGAR();

                        }
                    }
                }
            }

        }

    }
    public void OBJETOSP(){
        System.out.println("OBJETOS");
        if (this.tokens.get(0).getTipo().equals("pr_object")){
            this.OBJETO();
            this.OBJETOSP();
        }else{
            return;
        }
    }

    public void LUGAR(){
        System.out.println("LUGAR  en objetos");
        String x;
        String y;
        String lugar;

        Token tmp = this.tokens.removeFirst();

        //evaluo si es identificador o coordenadas
        if (tmp.getTipo().equals("identificador")){
            //forma 1 de declarar el lugar de los objetos Place

            lugar = tmp.getLexema();

        }else if(tmp.getTipo().equals("parentesisApertura")){
            //forma 2 de declara el lugar de los objetos (X,Y)
            tmp = this.tokens.removeFirst();
            if(tmp.getTipo().equals("numero")){
                x = tmp.getLexema();
                tmp = this.tokens.removeFirst();
                if(tmp.getTipo().equals("coma")){
                    tmp = this.tokens.removeFirst();
                    if(tmp.getTipo().equals("numero")){
                        y = tmp.getLexema();
                        tmp = this.tokens.removeFirst();
                        if(tmp.getTipo().equals("parentesisCierre")){
                            tmp = this.tokens.removeFirst();
                            return;

                        }else{agregarError(tmp);}
                    }else {agregarError(tmp);}
                }else{agregarError(tmp);}
            }else{agregarError(tmp);}
        }else{
            agregarError(tmp);
        }

    }


    public void reporteErroresSintacticos() {
        String contenido = "<html>";
        contenido+="<head> <title>Errores Sintacticos</title><link rel=\"stylesheet\" href=\"myCSS.css\"></head>";
        contenido+="<body>";
        contenido+="<h1>Reporte de errores Sintacticos</h1>";
        contenido+="<table border=\"solid\">";
        contenido+="<tr>";
        contenido+="<th>Caracter</th><th>Descripción</th><th>Linea</th><th>Columna</th><th>Tipo Error</th>";
        contenido+="</tr>";

        for (Error error : this.erroresSintacticos){
            contenido+="<tr>";
            contenido+="<td>"+error.getCaracter()+"</td>";
            contenido+="<td>"+error.getDescripcion()+"</td>";
            contenido+="<td>"+error.getLinea()+"</td>";
            contenido+="<td>"+error.getColumna()+"</td>";
            contenido+="<td>"+error.getTipo()+"</td>";
            contenido+="</tr>";
        }

        contenido+="</table>";
        contenido+="</body>";
        contenido+="</html>";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter("erroresSintacticos.html"))) {
            escritor.write(contenido);
            System.out.println("El reporte de errores sintacticos a sido generado correctamente");
            // Abrir el archivo en el navegador predeterminado
            File archivoHTML = new File("erroresSintacticos.html");
            Desktop.getDesktop().browse(archivoHTML.toURI());
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo html: " + e.getMessage());
        }
    }
}