import java.util.ArrayList;
import java.util.List;

public class AnalizadorSintactico {
    private List<Token> tokens;
    private List<Error> errores;
    /*HashMap<String, World> mundos; //<IDentificador del mundo, mundo>
    String mundoActual;*/

    public AnalizadorSintactico(List<Token> tokens) {
        this.tokens = tokens;
        this.errores = new ArrayList<>();
        /*this.mundos = new HashMap<>();
        this.mundoActual = "";*/
    }

    public void agregarError (Token token) {
        this.errores.add(new Error(token.getLexema(), "no se esperaba el token " + token.getLexema(), token.getLinea(), token.getColumna(),"Sintatico"));
    }
    public void analizar(){
        this.INICIO();
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

            if(tmp.getTipo().equals("cadena")){
                tmp = this.tokens.removeFirst();

                if(tmp.getTipo().equals("llaveApertura")){
                    this.LOCACIONES();
                    this.CONEXIONES();
                    this.OBJETOS();

                    tmp = this.tokens.removeFirst();

                    if(tmp.getTipo().equals("llaveCierre")){
                        //Continuo
                        return;
                    }else{
                        //Agrego error
                        this.agregarError(tmp);
                    }
                }else{
                    //Agrego error
                    this.agregarError(tmp);
                }
            }else{
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
                                                /*//Crear una nueva locacion
                                                Place place = new Place(identificador, tipo, x, y);
                                                this.mundos.get(mundoActual).agregarPlace(identificador, place);
                                                return;*/

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

    }
    public void OBJETOS(){

    }

}