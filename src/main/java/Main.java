import java.util.Scanner;
import java.util.Map;

/**
 * Clase principal que implementa el menú de consola para navegar entre los diferentes modos
 */
public class Main {
    private static ConfigurationManager configManager;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        configManager = ConfigurationManager.getInstance();
        scanner = new Scanner(System.in);
        
        System.out.println("=== Singleton Configuration Manager ===");
        System.out.println("Sistema de gestión de configuracion global");
        System.out.println();
        
        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = getIntInput("Seleccione una opcion: ");
            
            switch (choice) {
                case 1:
                    showCurrentConfiguration();
                    break;
                case 2:
                    changeConfiguration();
                    break;
                case 3:
                    openWelcomeScreen();
                    break;
                case 4:
                    runConnectionSimulator();
                    break;
                case 5:
                    running = false;
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opcion invalida. Por favor, intente de nuevo.");
            }
            
            if (running) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Muestra el menú principal
     */
    private static void showMainMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Ver configuración actual");
        System.out.println("2. Cambiar configuración");
        System.out.println("3. Ir al Panel de Bienvenida");
        System.out.println("4. Ejecutar el Simulador de Conexiones");
        System.out.println("5. Salir");
        System.out.println("=====================");
    }
    
    /**
     * Muestra la configuración actual
     */
    private static void showCurrentConfiguration() {
        System.out.println("\n=== CONFIGURACIÓN ACTUAL ===");
        Map<String, Object> config = configManager.getAllConfiguration();
        
        for (Map.Entry<String, Object> entry : config.entrySet()) {
            System.out.printf("%-20s: %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println("=============================");
    }
    
    /**
     * Permite cambiar la configuración
     */
    private static void changeConfiguration() {
        System.out.println("\n=== CAMBIAR CONFIGURACIÓN ===");
        System.out.println("Parámetros disponibles:");
        System.out.println("1. defaultCurrency (ej: CRC, USD)");
        System.out.println("2. timeFormat (24H o AM/PM)");
        System.out.println("3. maxConnections (número entero)");
        System.out.println("4. language (ES, EN, FR, DE)");
        System.out.println("5. autoSaveInterval (minutos)");
        System.out.println("6. enableLogs (true/false)");
        System.out.println("7. theme (light/dark)");
        System.out.println("8. region (ej: LATAM, GLOBAL)");
        System.out.println("9. backupEnabled (true/false)");
        System.out.println("10. backupDirectory (ruta)");
        System.out.println("0. Cancelar");
        
        int choice = getIntInput("Seleccione el parámetro a cambiar (0-10): ");
        
        if (choice == 0) {
            return;
        }
        
        String[] parameters = {
            "defaultCurrency", "timeFormat", "maxConnections", "language", 
            "autoSaveInterval", "enableLogs", "theme", "region", 
            "backupEnabled", "backupDirectory"
        };
        
        if (choice < 1 || choice > parameters.length) {
            System.out.println("Opción inválida.");
            return;
        }
        
        String parameter = parameters[choice - 1];
        System.out.println("Parámetro seleccionado: " + parameter);
        System.out.println("Valor actual: " + configManager.getConfiguration(parameter));
        
        System.out.print("Nuevo valor: ");
        String newValue = scanner.nextLine().trim();
        
        // Validar y convertir el valor según el tipo
        try {
            Object convertedValue = convertValue(parameter, newValue);
            configManager.setConfiguration(parameter, convertedValue);
            configManager.save();
            System.out.println("Configuración actualizada exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Convierte el valor de entrada al tipo correcto según el parámetro
     */
    private static Object convertValue(String parameter, String value) {
        switch (parameter) {
            case "maxConnections":
            case "autoSaveInterval":
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Debe ser un número entero.");
                }
            case "enableLogs":
            case "backupEnabled":
                if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
                    return Boolean.parseBoolean(value);
                } else {
                    throw new IllegalArgumentException("Debe ser 'true' o 'false'.");
                }
            case "timeFormat":
                if ("24H".equals(value) || "AM/PM".equals(value)) {
                    return value;
                } else {
                    throw new IllegalArgumentException("Debe ser '24H' o 'AM/PM'.");
                }
            case "theme":
                if ("light".equals(value) || "dark".equals(value)) {
                    return value;
                } else {
                    throw new IllegalArgumentException("Debe ser 'light' o 'dark'.");
                }
            case "language":
                if ("ES".equals(value) || "EN".equals(value) || "FR".equals(value) || "DE".equals(value)) {
                    return value;
                } else {
                    throw new IllegalArgumentException("Debe ser 'ES', 'EN', 'FR' o 'DE'.");
                }
            default:
                return value; // Para strings como defaultCurrency, region, backupDirectory
        }
    }
    
    /**
     * Abre la pantalla de bienvenida
     */
    private static void openWelcomeScreen() {
        System.out.println("Abriendo Panel de Bienvenida...");
        try {
            // Ejecutar en un hilo separado para no bloquear la consola
            new Thread(() -> {
                try {
                    WelcomeScreen welcomeScreen = new WelcomeScreen();
                    welcomeScreen.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Error al abrir la pantalla de bienvenida: " + e.getMessage());
                }
            }).start();
        } catch (Exception e) {
            System.err.println("Error al abrir la pantalla de bienvenida: " + e.getMessage());
        }
    }
    
    /**
     * Ejecuta el simulador de conexiones
     */
    private static void runConnectionSimulator() {
        System.out.println("Abriendo Simulador de Conexiones...");
        try {
            // Ejecutar en un hilo separado para no bloquear la consola
            new Thread(() -> {
                try {
                    ConnectionSimulator simulator = new ConnectionSimulator();
                    simulator.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Error al abrir el simulador de conexiones: " + e.getMessage());
                }
            }).start();
        } catch (Exception e) {
            System.err.println("Error al abrir el simulador de conexiones: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene entrada de tipo entero del usuario
     */
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }
} 