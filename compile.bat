@echo off
echo Compilando Singleton Configuration Manager...
echo.

REM Verificar si Java está instalado
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java no está instalado o no está en el PATH
    echo Por favor, instale Java 11 o superior
    echo Visite: https://adoptium.net/
    pause
    exit /b 1
)

REM Crear directorio de salida
if not exist "target\classes" mkdir "target\classes"

REM Descargar json-simple si no existe
if not exist "lib\json-simple-1.1.1.jar" (
    echo Descargando json-simple-1.1.1.jar...
    if not exist "lib" mkdir "lib"
    powershell -Command "Invoke-WebRequest -Uri 'https://repo1.maven.org/maven2/com/googlecode/json-simple/json-simple/1.1.1/json-simple-1.1.1.jar' -OutFile 'lib\json-simple-1.1.1.jar'"
    if %errorlevel% neq 0 (
        echo ERROR: No se pudo descargar json-simple-1.1.1.jar
        echo Por favor, descárguelo manualmente de: https://repo1.maven.org/maven2/com/googlecode/json-simple/json-simple/1.1.1/
        pause
        exit /b 1
    )
)

REM Compilar archivos Java
echo Compilando archivos Java...
javac -cp "lib\json-simple-1.1.1.jar" -d "target\classes" src\main\java\*.java

if %errorlevel% neq 0 (
    echo ERROR: Error durante la compilación
    pause
    exit /b 1
)

echo.
echo Compilación exitosa!
echo Los archivos compilados están en: target\classes\
echo.
echo Para ejecutar la aplicación:
echo java -cp "target\classes;lib\json-simple-1.1.1.jar" Main
echo.
pause 