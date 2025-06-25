import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Pantalla de bienvenida que muestra información basada en la configuración
 */
public class WelcomeScreen extends JFrame {
    private ConfigurationManager configManager;
    private JLabel welcomeLabel;
    private JLabel timeLabel;
    private JLabel themeLabel;
    private Timer timer;
    
    public WelcomeScreen() {
        this.configManager = ConfigurationManager.getInstance();
        initializeUI();
        startTimeUpdate();
    }
    
    /**
     * Inicializa la interfaz de usuario
     */
    private void initializeUI() {
        setTitle("Panel de Bienvenida - Singleton Configuration Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        
        // Aplicar tema
        applyTheme();
        
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        // Panel superior con mensaje de bienvenida
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        
        welcomeLabel = new JLabel(getWelcomeMessage());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel subtitleLabel = new JLabel("Sistema de Gestión de Configuración Global");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createVerticalStrut(10));
        welcomePanel.add(subtitleLabel);
        
        // Panel central con información de tiempo
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        
        JLabel timeTitleLabel = new JLabel("Hora Actual:");
        timeTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timeTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateTime();
        
        timePanel.add(timeTitleLabel);
        timePanel.add(Box.createVerticalStrut(10));
        timePanel.add(timeLabel);
        
        // Panel inferior con información del tema
        JPanel themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));
        
        themeLabel = new JLabel("Tema actual: " + configManager.getTheme());
        themeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        themeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel regionLabel = new JLabel("Región: " + configManager.getRegion());
        regionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        regionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        themePanel.add(themeLabel);
        themePanel.add(Box.createVerticalStrut(5));
        themePanel.add(regionLabel);
        
        // Botón de cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dispose());
        
        // Agregar componentes al panel principal
        mainPanel.add(welcomePanel, BorderLayout.NORTH);
        mainPanel.add(timePanel, BorderLayout.CENTER);
        mainPanel.add(themePanel, BorderLayout.SOUTH);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
        
        add(mainPanel);
    }
    
    /**
     * Aplica el tema de colores según la configuración
     */
    private void applyTheme() {
        String theme = configManager.getTheme();
        if ("dark".equals(theme)) {
            // Tema oscuro
            UIManager.put("Panel.background", new Color(50, 50, 50));
            UIManager.put("Label.foreground", Color.WHITE);
            UIManager.put("Button.background", new Color(70, 70, 70));
            UIManager.put("Button.foreground", Color.WHITE);
            getContentPane().setBackground(new Color(50, 50, 50));
        } else {
            // Tema claro (por defecto)
            UIManager.put("Panel.background", new Color(240, 240, 240));
            UIManager.put("Label.foreground", Color.BLACK);
            UIManager.put("Button.background", new Color(220, 220, 220));
            UIManager.put("Button.foreground", Color.BLACK);
            getContentPane().setBackground(new Color(240, 240, 240));
        }
    }
    
    /**
     * Obtiene el mensaje de bienvenida según el idioma configurado
     */
    private String getWelcomeMessage() {
        String language = configManager.getLanguage();
        switch (language) {
            case "ES":
                return "¡Bienvenido al Sistema!";
            case "EN":
                return "Welcome to the System!";
            case "FR":
                return "Bienvenue dans le Système!";
            case "DE":
                return "Willkommen im System!";
            default:
                return "Welcome to the System!";
        }
    }
    
    /**
     * Actualiza la hora mostrada según el formato configurado
     */
    private void updateTime() {
        LocalDateTime now = LocalDateTime.now();
        String timeFormat = configManager.getTimeFormat();
        
        DateTimeFormatter formatter;
        if ("24H".equals(timeFormat)) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        } else {
            formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        }
        
        String formattedTime = now.format(formatter);
        timeLabel.setText(formattedTime);
    }
    
    /**
     * Inicia la actualización automática del tiempo
     */
    private void startTimeUpdate() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> updateTime());
            }
        }, 0, 1000); // Actualizar cada segundo
    }
    
    /**
     * Detiene la actualización del tiempo al cerrar la ventana
     */
    @Override
    public void dispose() {
        if (timer != null) {
            timer.cancel();
        }
        super.dispose();
    }
    
    /**
     * Método principal para probar la pantalla de bienvenida
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.setVisible(true);
        });
    }
} 