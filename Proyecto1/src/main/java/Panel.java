import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Panel extends JFrame /*implements ActionListener*/ {
    AnalizadorLexico analizadorLexico = new AnalizadorLexico();
    String contenido ="Grafica Automata";

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
                // Aquí va el código que se ejecutará cuando se presione el botón

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

        // Espacio para mostrar imagen del AFD
        imageLabel = new JLabel("Aquí se mostrará el diagrama del AFD");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setText(contenido);
        centerPanel.add(imageLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Centrar la ventana
        setLocationRelativeTo(null);

        //visible
        setVisible(true);
    }

    public void inicializar(){
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
                    analizadorLexico.analizar(content);
                    textArea.setText(content.toString());

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
