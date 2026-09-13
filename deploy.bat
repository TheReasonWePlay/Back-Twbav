@echo off
setlocal

set APP_DIR=C:\apps\GestionSalleDeClasse
set JAR_NAME=GestionSalleDeClasse-0.0.1-SNAPSHOT.jar
set JAR_SOURCE=target\%JAR_NAME%
set PID_FILE=%APP_DIR%\app.pid
set LOG_FILE=%APP_DIR%\app.log

echo ========================================
echo   DEPLOIEMENT GestionSalleDeClasse
echo ========================================

if not exist "%APP_DIR%" (
    echo Creation du dossier %APP_DIR%
    mkdir "%APP_DIR%"
)

echo.
echo [1/4] Copie du JAR...
copy /Y "%JAR_SOURCE%" "%APP_DIR%\%JAR_NAME%"

if errorlevel 1 (
    echo ERREUR : impossible de copier le JAR.
    exit /b 1
)

echo.
echo [2/4] Arret de l'ancienne application...

if exist "%PID_FILE%" (
    set /p OLD_PID=<"%PID_FILE%"

    tasklist /FI "PID eq %OLD_PID%" | findstr "%OLD_PID%" >nul

    if not errorlevel 1 (
        echo Arret du processus PID %OLD_PID%...
        taskkill /PID %OLD_PID% /T /F
    ) else (
        echo Ancien processus deja arrete.
    )

    del "%PID_FILE%"
) else (
    echo Aucune ancienne instance trouvee.
)

echo.
echo [3/4] Demarrage de l'application...

start "GestionSalleDeClasse" /B ^
    "C:\Program Files\Java\jdk-25.0.4\bin\java.exe" ^
    -jar "%APP_DIR%\%JAR_NAME%" ^
    >> "%LOG_FILE%" 2>&1

if errorlevel 1 (
    echo ERREUR : impossible de demarrer l'application.
    exit /b 1
)

echo.
echo [4/4] Deploiement termine.
echo.
echo Application :
echo http://localhost:8081
echo.
echo Logs :
echo %LOG_FILE%

endlocal