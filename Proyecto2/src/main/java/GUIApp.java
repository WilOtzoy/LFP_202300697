//public class GUIApp {
//}
import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIApp extends JFrame {

    // Componentes
    private JTextArea textArea;
    private JLabel imageLabel;
    private JComboBox<String> reportComboBox;

    private JButton loadButton;
    private JButton clearButton;
    private JButton analyzeButton;

    private JButton about;
    private JButton tokensReport;
    private JButton errorReport;

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
        textArea.setEditable(false);
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
        analyzeButton = new JButton("Analisar archivo");

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
        tokensReport = new JButton("Reporte de tokens");
        errorReport = new JButton("Reporte de errores");
        about = new JButton("Acerca de");

        buttonPanelRight.add(tokensReport);
        buttonPanelRight.add(errorReport);
        buttonPanelRight.add(about);

        rightPanel.add(buttonPanelRight, BorderLayout.SOUTH);

        // Menú desplegable
        String[] reportOptions = {"Isla del místico", "Valle de las brujas", "Montaña de dragones"};
        reportComboBox = new JComboBox<>(reportOptions);
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
                textArea.setText("Archivo cargado exitosamente.");
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                imageLabel.setText("Área de imagen");
            }
        });

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("Análisis realizado exitosamente.");
            }
        });

        // Mostrar la ventana
        setVisible(true);
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUIApp();
        });
    }*/
}