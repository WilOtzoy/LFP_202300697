import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Panel extends JFrame {
     public  Map<String, AFD> automatas;

    AnalizadorLexico analizadorLexico = new AnalizadorLexico();

    String contenido ="";

    JLabel imageLabel;
    JTextArea textArea ;

    public Panel() {

        // Configurar la ventana principal
        setTitle("AFD Graph");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con botones
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Botón "Análizar Archivo"
        JButton analyzeButton = new JButton("Análizar Archivo");
        topPanel.add(analyzeButton);

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // evento de analizar
                inicializar();
            }
        });

        // Botón "Generar Reporte"
        JButton reportButton = new JButton("Generar Reporte");
        topPanel.add(reportButton);

        // Agregar un ActionListener al botón
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // evento
                System.out.println("*** T O K E N S *** ");
                //analizadorLexico.imprimirTokens();
                analizadorLexico.reporteTokens();
                System.out.println(" ");
                System.out.println("*** E R R O R E S  * L E X I C O S *** ");
                //analizadorLexico.imprimirErrores();
                analizadorLexico.reporteErrores();
            }
        });

        // ComboBox para seleccionar AFD
        JComboBox<String> selctBox = new JComboBox<>(new String[]{"Selecciona un AFD", "AFD1", "AFD2", "Numero"});
        topPanel.add(selctBox);

        // Botón "Graficar"
        JButton graphButton = new JButton("Graficar");
        graphButton.setBackground(Color.BLUE);
        graphButton.setForeground(Color.WHITE);
        topPanel.add(graphButton);

        add(topPanel, BorderLayout.NORTH);

        // Panel central para mostrar texto y diagramas
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));

        // Área de texto para mostrar descripción del AFD
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        centerPanel.add(scrollPane);

        // Cargargo una imagen del grafo en el JLabel
        imageLabel = new JLabel("GRAFO AFD");
        ImageIcon grafo = new ImageIcon("G1.jpg");
        imageLabel.setIcon(grafo);
        centerPanel.add(imageLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Centrar la ventana
        setLocationRelativeTo(null);

        //visible
        setVisible(true);
    }

    public void inicializar(){
        automatas = new HashMap<>();

        //Crear el selector de archivos
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showOpenDialog(null);

        //Si el usuario seleccionó algun archivo
        if (selection == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String ruta = archivoSeleccionado.getAbsolutePath();

            //muevo a aca mi path
            try {
                //File archivo = new File("C:\\Users\\wilso\\Downloads\\Entrada1.lfp");
                File archivo = new File(ruta);
                StringBuilder content = new StringBuilder();
                if (archivo.exists()) {
                    BufferedReader br = new BufferedReader(new FileReader(archivo));
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        content.append(linea).append("\n");
                    }

                    //System.out.println(content);
                    //Analizo el contenido
                    analizadorLexico.analizar(content);
                    textArea.setText(content.toString());

                    List<Token> tokens = analizadorLexico.getTokens();
                    //**************************************
                    int i = 0;
                    while(i < tokens.size()){
                        if(tokens.get(i).getTipo().equals("identificador") && tokens.get(i+1).getTipo().equals("dosPuntos")){
                            String nombre = tokens.get(i).getLexema();
                            AFD afd = new AFD(nombre);

                            i+=2;//Avanzo al token después de dosPuntos

                            if(tokens.get(i).getTipo().equals("palabraReservada") && tokens.get(i).getLexema().equals("inicial")){
                                //Avanzo hasta después de dosPuntos
                                i+=2;

                                afd.setEstadoInicial(tokens.get(i).getLexema());
                                i+=2;
                            }

                            if(tokens.get(i).getTipo().equals("palabraReservada") && tokens.get(i).getLexema().equals("finales")){
                                i+=3;
                                while(!tokens.get(i).getTipo().equals("parentesisCierra")){
                                    afd.agregarEstadoFinal(tokens.get(i).getLexema());

                                    if(tokens.get(i+1).getTipo().equals("coma")){
                                        i+=2;
                                    }else{
                                        i+=1;
                                    }
                                }

                            }

                            i+=2;

                            if(tokens.get(i).getTipo().equals("palabraReservada") && tokens.get(i).getLexema().equals("transiciones")){
                                i+=1;

                                while(!tokens.get(i+1).getTipo().equals("llaveCierre")){
                                    i+=2;

                                    String estadoActual = tokens.get(i).getLexema();

                                    i+=3;

                                    while(!tokens.get(i).getTipo().equals("parentesisCierra")){
                                        String entrada = tokens.get(i).getLexema();

                                        i+=2;
                                        String estadoDestino = tokens.get(i).getLexema();

                                        afd.agregarTransicion(estadoActual, entrada, estadoDestino);

                                        if(tokens.get(i+1).getTipo().equals("coma")){
                                            i+=2;
                                        } else{
                                            i+=1;
                                        }
                                    }
                                }
                            }

                            automatas.put(nombre, afd);

                        }

                        i++;
                    }

                    //**************************************

                } else {
                    System.out.println("El archivo no existe.");
                }
            }
            catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }

        }

    }

}
