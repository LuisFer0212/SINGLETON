# Singleton Configuration Manager

Este proyecto implementa el patrón de diseño **Singleton** para centralizar la lectura, acceso y modificación de una configuración global en Java.

## 📋 Descripción

El sistema permite gestionar una configuración global que se almacena en un archivo JSON (`config.json`) y se accede a través de una única instancia de `ConfigurationManager`. El proyecto incluye:

- **Patrón Singleton puro** para gestión centralizada de configuración
- **Interfaz gráfica** con Swing para visualizar la configuración
- **Simulador de conexiones** que utiliza los parámetros de configuración
- **Menú de consola** para navegar entre diferentes modos
- **Persistencia automática** de cambios en el archivo JSON

## 🏗️ Estructura del Proyecto

```
Singleton/
├── config.json                    # Archivo de configuración
├── pom.xml                       # Configuración de Maven
├── compile.bat                   # Script de compilación para Windows
├── run.bat                       # Script de ejecución para Windows
├── README.md                     # Este archivo
└── src/main/java/
    ├── Main.java                 # Clase principal con menú de consola
    ├── ConfigurationManager.java # Implementación del patrón Singleton
    ├── ConfigurationLoader.java  # Lógica de lectura/escritura JSON
    ├── WelcomeScreen.java        # GUI del panel de bienvenida
    └── ConnectionSimulator.java  # GUI del simulador de conexiones
```

## ⚙️ Parámetros de Configuración

El archivo `config.json` contiene los siguientes parámetros:

| Parámetro          | Tipo    | Descripción                          | Valores Válidos         |
| ------------------ | ------- | ------------------------------------ | ----------------------- |
| `defaultCurrency`  | String  | Moneda por defecto                   | "CRC", "USD", etc.      |
| `timeFormat`       | String  | Formato de hora                      | "24H", "AM/PM"          |
| `maxConnections`   | Integer | Máximo de conexiones                 | Número entero           |
| `language`         | String  | Idioma del sistema                   | "ES", "EN", "FR", "DE"  |
| `autoSaveInterval` | Integer | Intervalo de auto-guardado (minutos) | Número entero           |
| `enableLogs`       | Boolean | Habilitar logs                       | true/false              |
| `theme`            | String  | Tema de la interfaz                  | "light", "dark"         |
| `region`           | String  | Región del sistema                   | "LATAM", "GLOBAL", etc. |
| `backupEnabled`    | Boolean | Habilitar respaldo                   | true/false              |
| `backupDirectory`  | String  | Directorio de respaldo               | Ruta de carpeta         |

## 🚀 Requisitos

- **Java 11** o superior
- **Maven 3.6** o superior (opcional, solo para compilación con Maven)
- **Sistema operativo**: Windows, macOS, Linux

## 📦 Instalación de Java

### Windows

1. Descargue Java desde [Eclipse Temurin](https://adoptium.net/)
2. Ejecute el instalador y siga las instrucciones
3. Agregue Java al PATH del sistema
4. Verifique la instalación: `java -version`

## 🔧 Instalación y Compilación

### Opción 1: Compilación Manual (Recomendada para principiantes)

#### Windows

```bash
# Ejecutar el script de compilación
compile.bat

# Ejecutar la aplicación
run.bat
```

#### 3. Crear el JAR ejecutable

```bash
mvn clean package
```

#### 4. Ejecutar

```bash
# Usando Maven
mvn exec:java

# Usando el JAR generado
java -jar target/configuration-manager-1.0.0.jar
```

## 🎯 Ejecución

### Primera vez

1. Asegúrese de tener Java instalado
2. Ejecute `compile.bat` (Windows) o los comandos de compilación manual
3. Ejecute `run.bat` (Windows) o `java -cp "target/classes:lib/json-simple-1.1.1.jar" Main`

### Ejecuciones posteriores

- Solo ejecute `run.bat` (Windows) o el comando de ejecución

## 🎮 Uso del Sistema

### Menú Principal

Al ejecutar la aplicación, se mostrará un menú de consola con las siguientes opciones:

1. **Ver configuración actual** - Muestra todos los parámetros y sus valores
2. **Cambiar configuración** - Permite modificar cualquier parámetro
3. **Ir al Panel de Bienvenida** - Abre una ventana GUI con información del sistema
4. **Ejecutar el Simulador de Conexiones** - Abre una ventana GUI para simular conexiones
5. **Salir** - Cierra la aplicación

### Panel de Bienvenida

La pantalla de bienvenida muestra:

- Mensaje de bienvenida en el idioma configurado
- Hora actual en el formato especificado (24H o AM/PM)
- Información del tema y región actuales
- Los colores se adaptan según el tema configurado (claro/oscuro)

### Simulador de Conexiones

El simulador:

- Abre conexiones hasta el límite configurado en `maxConnections`
- Muestra logs en tiempo real si `enableLogs` está habilitado
- Proporciona un resumen final con la moneda por defecto
- Incluye una barra de progreso para seguimiento visual

## 🔧 Modificación de Configuración

### Desde el Menú de Consola

1. Seleccione la opción "2. Cambiar configuración"
2. Elija el parámetro a modificar (1-10)
3. Ingrese el nuevo valor
4. La configuración se guarda automáticamente en `config.json`

### Ejemplos de Valores

```bash
# Cambiar moneda
defaultCurrency: USD

# Cambiar formato de hora
timeFormat: AM/PM

# Cambiar número máximo de conexiones
maxConnections: 10

# Cambiar idioma
language: EN

# Habilitar logs
enableLogs: true

# Cambiar tema
theme: dark
```

## 🛠️ Desarrollo

### Estructura de Clases

- **`ConfigurationManager`**: Implementa el patrón Singleton

  - Constructor privado
  - Instancia estática
  - Método público `getInstance()`
  - Métodos para obtener y modificar configuración

- **`ConfigurationLoader`**: Maneja la persistencia JSON

  - Lectura del archivo `config.json`
  - Escritura de cambios
  - Manejo de errores y valores por defecto

- **`WelcomeScreen`**: Interfaz gráfica de bienvenida

  - Muestra información basada en la configuración
  - Actualización en tiempo real del reloj
  - Aplicación de temas

- **`ConnectionSimulator`**: Simulador de conexiones

  - Utiliza `maxConnections` y `enableLogs`
  - Interfaz gráfica con logs y progreso
  - Simulación asíncrona

- **`Main`**: Punto de entrada y menú de consola
  - Navegación entre modos
  - Validación de entrada de usuario
  - Gestión de cambios de configuración

### Patrón Singleton Implementado

```java
public class ConfigurationManager {
    private static ConfigurationManager instance;
    private Map<String, Object> configuration;

    // Constructor privado
    private ConfigurationManager() {
        // Inicialización
    }

    // Método público para obtener la instancia
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
}
```




