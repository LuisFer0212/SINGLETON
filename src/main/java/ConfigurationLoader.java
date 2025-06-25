import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Clase responsable de cargar y guardar la configuración desde/hacia archivos JSON
 */
public class ConfigurationLoader {
    private static final String CONFIG_FILE = "config.json";
    
    /**
     * Carga la configuración desde el archivo config.json
     * @return Mapa con la configuración cargada
     * @throws Exception Si hay error al leer el archivo
     */
    public Map<String, Object> loadConfiguration() throws Exception {
        Map<String, Object> config = new HashMap<>();
        
        if (!Files.exists(Paths.get(CONFIG_FILE))) {
            throw new FileNotFoundException("Archivo de configuración no encontrado: " + CONFIG_FILE);
        }
        
        try (Reader reader = new FileReader(CONFIG_FILE)) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            
            // Convertir JSONObject a Map
            for (Object key : jsonObject.keySet()) {
                String keyStr = (String) key;
                Object value = jsonObject.get(keyStr);
                config.put(keyStr, value);
            }
        } catch (Exception e) {
            throw new Exception("Error al parsear el archivo de configuración: " + e.getMessage());
        }
        
        return config;
    }
    
    /**
     * Guarda la configuración en el archivo config.json
     * @param configuration Mapa con la configuración a guardar
     * @throws Exception Si hay error al escribir el archivo
     */
    public void saveConfiguration(Map<String, Object> configuration) throws Exception {
        JSONObject jsonObject = new JSONObject();
        
        // Convertir Map a JSONObject
        for (Map.Entry<String, Object> entry : configuration.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            writer.write(jsonObject.toJSONString());
            writer.flush();
        } catch (Exception e) {
            throw new Exception("Error al escribir el archivo de configuración: " + e.getMessage());
        }
    }
    
    /**
     * Verifica si existe el archivo de configuración
     * @return true si existe, false en caso contrario
     */
    public boolean configFileExists() {
        return Files.exists(Paths.get(CONFIG_FILE));
    }
    
    /**
     * Crea un archivo de configuración por defecto
     * @throws Exception Si hay error al crear el archivo
     */
    public void createDefaultConfig() throws Exception {
        Map<String, Object> defaultConfig = new HashMap<>();
        defaultConfig.put("defaultCurrency", "USD");
        defaultConfig.put("timeFormat", "24H");
        defaultConfig.put("maxConnections", 10);
        defaultConfig.put("language", "EN");
        defaultConfig.put("autoSaveInterval", 5);
        defaultConfig.put("enableLogs", true);
        defaultConfig.put("theme", "light");
        defaultConfig.put("region", "GLOBAL");
        defaultConfig.put("backupEnabled", true);
        defaultConfig.put("backupDirectory", "./backup");
        
        saveConfiguration(defaultConfig);
    }
} 