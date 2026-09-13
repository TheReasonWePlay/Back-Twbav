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

if not exist "%APP_DIR%" mkdir "%APP_DIR%"

echo.
echo [1/5] Copie du JAR...
copy /Y "%JAR_SOURCE%" "%APP_DIR%\%JAR_NAME%"

if errorlevel 1 (
    echo ERREUR : impossible de copier le JAR.
    exit /b 1
)

echo.
echo [2/5] Arret de l'ancienne application...

if exist "%PID_FILE%" (
    set /p OLD_PID=<"%PID_FILE%"

    tasklist /FI "PID eq %OLD_PID%" | findstr "%OLD_PID%" >nul

    if not errorlevel 1 (
        echo Arret du processus PID %OLD_PID%...
        taskkill /PID %OLD_PID% /T /F
    ) else (
        echo Ancienne instance deja arretee.
    )

    del "%PID_FILE%"
) else (
    echo Aucune ancienne instance trouvee.
)

echo.
echo [3/5] Demarrage de l'application...

cd /d "%APP_DIR%"

start "GestionSalleDeClasse" /MIN ^
    "C:\Program Files\Java\jdk-25.0.4\bin\java.exe" ^
    -jar "%APP_DIR%\%JAR_NAME%" ^
    >> "%LOG_FILE%" 2>&1

echo.
echo [4/5] Attente du demarrage...

timeout /t 10 /nobreak >nul

echo.
echo [5/5] Verification de l'application...

powershell -NoProfile -Command "try { $r = Invoke-WebRequest -Uri 'http://localhost:8081/api/profs' -UseBasicParsing -TimeoutSec 5; if ($r.StatusCode -eq 200) { exit 0 } else { exit 1 } } catch { exit 1 }"

if errorlevel 1 (
    echo.
    echo ERREUR : l'API ne repond pas sur le port 8081.
    echo Consultez :
    echo %LOG_FILE%
    exit /b 1
)

echo.
echo ========================================
echo   DEPLOIEMENT REUSSI
echo ========================================
echo.
echo API : http://localhost:8081
echo Logs : %LOG_FILE%

endlocal
exit /b 0