import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Simulador de conexiones que utiliza la configuración global
 */
public class ConnectionSimulator extends JFrame {
    private ConfigurationManager configManager;
    private JTextArea logArea;
    private JProgressBar progressBar;
    private JButton startButton;
    private JButton closeButton;
    private JLabel statusLabel;
    private JLabel summaryLabel;
    private Random random;
    
    public ConnectionSimulator() {
        this.configManager = ConfigurationManager.getInstance();
        this.random = new Random();
        initializeUI();
    }
    
    /**
     * Inicializa la interfaz de usuario
     */
    private void initializeUI() {
        setTitle("Simulador de Conexiones - Singleton Configuration Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel superior con controles
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        startButton = new JButton("Iniciar Simulación");
        startButton.addActionListener(e -> startSimulation());
        
        closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> dispose());
        
        controlPanel.add(startButton);
        controlPanel.add(closeButton);
        
        // Panel central con área de logs
        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("Logs de Conexiones"));
        
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        logPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Barra de progreso
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setString("Listo para iniciar");
        
        // Panel inferior con estado y resumen
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        statusLabel = new JLabel("Estado: Esperando inicio de simulación");
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        summaryLabel = new JLabel("Resumen: -");
        summaryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        summaryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        infoPanel.add(statusLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(summaryLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(progressBar);
        
        // Agregar componentes al panel principal
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(logPanel, BorderLayout.CENTER);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Inicia la simulación de conexiones
     */
    private void startSimulation() {
        startButton.setEnabled(false);
        logArea.setText("");
        progressBar.setValue(0);
        
        int maxConnections = configManager.getMaxConnections();
        boolean enableLogs = configManager.isEnableLogs();
        
        statusLabel.setText("Estado: Simulando " + maxConnections + " conexiones...");
        
        // Ejecutar simulación en un hilo separado para no bloquear la UI
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                int successfulConnections = 0;
                int failedConnections = 0;
                
                for (int i = 1; i <= maxConnections; i++) {
                    // Simular tiempo de conexión
                    Thread.sleep(random.nextInt(500) + 200);
                    
                    // Simular éxito o fallo (70% éxito)
                    boolean success = random.nextDouble() < 0.7;
                    
                    if (success) {
                        successfulConnections++;
                        if (enableLogs) {
                            logMessage("Conexión " + i + " exitosa");
                        }
                    } else {
                        failedConnections++;
                        if (enableLogs) {
                            logMessage("Fallo en conexión " + i);
                        }
                    }
                    
                    // Actualizar progreso
                    final int progress = (i * 100) / maxConnections;
                    final int finalSuccessful = successfulConnections;
                    final int finalFailed = failedConnections;
                    
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(progress);
                        progressBar.setString(progress + "% completado");
                    });
                }
                
                // Mostrar resumen final
                final String summary = String.format("Resumen: %d exitosas, %d fallidas - Moneda: %s", 
                    successfulConnections, failedConnections, configManager.getDefaultCurrency());
                
                SwingUtilities.invokeLater(() -> {
                    summaryLabel.setText(summary);
                    statusLabel.setText("Estado: Simulación completada");
                    startButton.setEnabled(true);
                });
                
                return null;
            }
        };
        
        worker.execute();
    }
    
    /**
     * Agrega un mensaje al área de logs
     */
    private void logMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            // Auto-scroll al final
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    /**
     * Método principal para probar el simulador de conexiones
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConnectionSimulator simulator = new ConnectionSimulator();
            simulator.setVisible(true);
        });
    }
} 