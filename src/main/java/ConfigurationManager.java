import java.util.HashMap;
import java.util.Map;

/**
 * Clase que implementa el patrón Singleton para gestionar la configuración global
 * de la aplicación.
 */
public class ConfigurationManager {
    private static ConfigurationManager instance;
    private Map<String, Object> configuration;
    private ConfigurationLoader loader;
    
    // Constructor privado para implementar Singleton
    private ConfigurationManager() {
        this.loader = new ConfigurationLoader();
        this.configuration = new HashMap<>();
        loadConfiguration();
    }
    
    /**
     * Obtiene la única instancia de ConfigurationManager (Singleton)
     * @return La instancia única de ConfigurationManager
     */
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    /**
     * Carga la configuración desde el archivo config.json
     */
    private void loadConfiguration() {
        try {
            this.configuration = loader.loadConfiguration();
        } catch (Exception e) {
            System.err.println("Error al cargar configuración: " + e.getMessage());
            loadDefaultConfiguration();
        }
    }
    
    /**
     * Carga la configuración por defecto si hay errores
     */
    private void loadDefaultConfiguration() {
        configuration.put("defaultCurrency", "USD");
        configuration.put("timeFormat", "24H");
        configuration.put("maxConnections", 10);
        configuration.put("language", "EN");
        configuration.put("autoSaveInterval", 5);
        configuration.put("enableLogs", true);
        configuration.put("theme", "light");
        configuration.put("region", "GLOBAL");
        configuration.put("backupEnabled", true);
        configuration.put("backupDirectory", "./backup");
    }
    
    /**
     * Guarda la configuración actual en el archivo config.json
     */
    public void save() {
        try {
            loader.saveConfiguration(configuration);
            System.out.println("Configuración guardada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al guardar configuración: " + e.getMessage());
        }
    }
    
    // Métodos para obtener valores de configuración
    public String getDefaultCurrency() {
        return (String) configuration.get("defaultCurrency");
    }
    
    public String getTimeFormat() {
        return (String) configuration.get("timeFormat");
    }
    
    public int getMaxConnections() {
        Object value = configuration.get("maxConnections");
        if (value instanceof Long) {
            return ((Long) value).intValue();
        }
        return (Integer) value;
    }
    
    public String getLanguage() {
        return (String) configuration.get("language");
    }
    
    public int getAutoSaveInterval() {
        Object value = configuration.get("autoSaveInterval");
        if (value instanceof Long) {
            return ((Long) value).intValue();
        }
        return (Integer) value;
    }
    
    public boolean isEnableLogs() {
        return (Boolean) configuration.get("enableLogs");
    }
    
    public String getTheme() {
        return (String) configuration.get("theme");
    }
    
    public String getRegion() {
        return (String) configuration.get("region");
    }
    
    public boolean isBackupEnabled() {
        return (Boolean) configuration.get("backupEnabled");
    }
    
    public String getBackupDirectory() {
        return (String) configuration.get("backupDirectory");
    }
    
    // Métodos para modificar valores de configuración
    public void setDefaultCurrency(String defaultCurrency) {
        configuration.put("defaultCurrency", defaultCurrency);
    }
    
    public void setTimeFormat(String timeFormat) {
        configuration.put("timeFormat", timeFormat);
    }
    
    public void setMaxConnections(int maxConnections) {
        configuration.put("maxConnections", maxConnections);
    }
    
    public void setLanguage(String language) {
        configuration.put("language", language);
    }
    
    public void setAutoSaveInterval(int autoSaveInterval) {
        configuration.put("autoSaveInterval", autoSaveInterval);
    }
    
    public void setEnableLogs(boolean enableLogs) {
        configuration.put("enableLogs", enableLogs);
    }
    
    public void setTheme(String theme) {
        configuration.put("theme", theme);
    }
    
    public void setRegion(String region) {
        configuration.put("region", region);
    }
    
    public void setBackupEnabled(boolean backupEnabled) {
        configuration.put("backupEnabled", backupEnabled);
    }
    
    public void setBackupDirectory(String backupDirectory) {
        configuration.put("backupDirectory", backupDirectory);
    }
    
    /**
     * Obtiene toda la configuración como un mapa
     * @return Mapa con toda la configuración
     */
    public Map<String, Object> getAllConfiguration() {
        return new HashMap<>(configuration);
    }
    
    /**
     * Establece un valor de configuración genérico
     * @param key Clave de configuración
     * @param value Valor a establecer
     */
    public void setConfiguration(String key, Object value) {
        configuration.put(key, value);
    }
    
    /**
     * Obtiene un valor de configuración genérico
     * @param key Clave de configuración
     * @return Valor de la configuración
     */
    public Object getConfiguration(String key) {
        return configuration.get(key);
    }
} 