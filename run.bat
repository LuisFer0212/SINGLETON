@echo off
echo Ejecutando Singleton Configuration Manager...
echo.

REM Verificar si los archivos compilados existen
if not exist "target\classes\Main.class" (
    echo ERROR: Los archivos no están compilados
    echo Ejecute primero: compile.bat
    pause
    exit /b 1
)

REM Verificar si la librería existe
if not exist "lib\json-simple-1.1.1.jar" (
    echo ERROR: No se encontró json-simple-1.1.1.jar
    echo Ejecute primero: compile.bat
    pause
    exit /b 1
)

REM Ejecutar la aplicación
echo Iniciando aplicación...
java -cp "target\classes;lib\json-simple-1.1.1.jar" Main

echo.
echo Aplicación terminada.
pause 