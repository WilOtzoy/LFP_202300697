import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class GUIApp extends JFrame {

    // Componentes
    private JTextArea textArea;
    private JLabel imageLabel;
    private JComboBox<String> reportComboBox;

    private JButton loadButton;
    private JButton clearButton;
    private JButton analyzeButton;

    private JButton aboutButton;
    private JButton tokensReportButton;
    private JButton errorReportButton;

    //StringBuilder content = new StringBuilder();

    AnalizadorLexico analizadorLexico = new AnalizadorLexico();
    AnalizadorSintactico analizadorSintactico = new AnalizadorSintactico(analizadorLexico.getTokens());


    public GUIApp() {
        // Configuración de la ventana
        setTitle("Mapa narrativo ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 550);
        setLocationRelativeTo(null);

        // Diseño de la interfaz
        setLayout(new BorderLayout());

        // Panel izquierdo
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Área de texto
        textArea = new JTextArea("Área de texto");
        textArea.setEditable(true);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        // Botones inferiores
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        loadButton = new JButton("Cargar archivo");
        clearButton = new JButton("Limpiar área");
        analyzeButton = new JButton("Analizar archivo");

        buttonPanel.add(loadButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(analyzeButton);

        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Panel derecho
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Área de imagen
        imageLabel = new JLabel("Área de imagen");
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(400, 400));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        rightPanel.add(imageLabel, BorderLayout.CENTER);

        // Botones inferiores panel derecho
        JPanel buttonPanelRight = new JPanel();
        buttonPanelRight.setLayout(new FlowLayout(FlowLayout.LEFT));
        tokensReportButton = new JButton("Reporte de tokens");
        errorReportButton = new JButton("Reporte de errores");
        aboutButton = new JButton("Acerca de");

        buttonPanelRight.add(tokensReportButton);
        buttonPanelRight.add(errorReportButton);
        buttonPanelRight.add(aboutButton);

        rightPanel.add(buttonPanelRight, BorderLayout.SOUTH);

        // Menú desplegable
        String[] mundosList = {"Isla del místico", "Valle de las brujas", "Montaña de dragones"};
        reportComboBox = new JComboBox<>(mundosList);
        reportComboBox.setSelectedIndex(0);
        rightPanel.add(reportComboBox, BorderLayout.NORTH);

        // Agregar paneles al JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400);

        add(splitPane, BorderLayout.CENTER);

        // Eventos de los botones
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarArchivo();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                analizadorLexico.getTokens().clear();
                analizadorLexico.getErrores().clear();
                analizadorSintactico.getErrores().clear();


                //content.setLength(0);

                textArea.setText("");
                JOptionPane.showMessageDialog(null,"Datos borrados satisfactoriamente!");

            }
        });

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //textArea.setText("Análisis realizado exitosamente.");
                //analizadorLexico.analizar();


                    StringBuilder contenido = new StringBuilder(textArea.getText());
                    /*content = new StringBuilder();
                    content = new StringBuilder(textArea.getText());
                    //contenido = textArea.getText().toString();
                    analizadorLexico.analizar(content);*/

                    analizadorLexico.analizar(contenido);

                    analizadorLexico.imprimirTokens();
                    analizadorLexico.imprimirErrores();

                    analizadorSintactico.analizar();

                    JOptionPane.showMessageDialog(null,"Archivo Analizado correctamente");

            }
        });

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Nombre: Wilson Wilfredo Pérez Otzoy \nCarnet: 202300697");
            }
        });

        errorReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analizadorLexico.imprimirErrores();
                analizadorSintactico.imprimirErroresSintacticos();

                analizadorLexico.reporteErroresLexicos();
                analizadorSintactico.reporteErroresSintacticos();

            }
        });

        tokensReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                analizadorLexico.imprimirTokens();

                analizadorLexico.reporteTokens();

            }
        });

        reportComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                analizadorSintactico.analizar();
            }
        });
        // Mostrar la ventana
        setVisible(true);
    }

    public void cargarArchivo(){
        //Crear el selector de archivos
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showOpenDialog(null);

        //Si el usuario seleccionó algun archivo
        if (selection == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String ruta = archivoSeleccionado.getAbsolutePath();

            //muevo a aca mi path
            try {
                File archivo = new File(ruta);
                //aca el string builder
                StringBuilder content = new StringBuilder();

                if (archivo.exists()) {
                    BufferedReader br = new BufferedReader(new FileReader(archivo));
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        content.append(linea).append("\n");
                    }

                    System.out.println(content);
                    //Analizo el contenido
                    //analizadorLexico.analizar(content);

                    textArea.setText(content.toString());

                    //analizadorLexico.imprimirTokens();
                    //analizadorLexico.imprimirErrores();

                    //List<Token> tokens = analizadorLexico.getTokens();
                } else {
                    System.out.println("El archivo no existe.");
                }
            }
            catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }

        }

                }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUIApp();
        });
    }
    }
