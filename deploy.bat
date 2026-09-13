@echo off
setlocal

set "APP_DIR=C:\apps\GestionSalleDeClasse"
set "JAR_NAME=GestionSalleDeClasse-0.0.1-SNAPSHOT.jar"
set "JAR_SOURCE=target\%JAR_NAME%"
set "PID_FILE=%APP_DIR%\app.pid"
set "LOG_FILE=%APP_DIR%\app.log"
set "ERROR_LOG_FILE=%APP_DIR%\app-error.log"
set "JAVA_EXE=C:\Program Files\Java\jdk-25.0.4\bin\java.exe"

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

    echo PID precedent : %OLD_PID%

    tasklist /FI "PID eq %OLD_PID%" | findstr "%OLD_PID%" >nul

    if not errorlevel 1 (
        echo Arret du processus PID %OLD_PID%...
        taskkill /PID %OLD_PID% /T /F >nul 2>&1
        timeout /t 2 /nobreak >nul
    ) else (
        echo Ancienne instance deja arretee.
    )

    del "%PID_FILE%" >nul 2>&1

) else (
    echo Aucune ancienne instance trouvee.
)

echo.
echo [3/5] Demarrage de l'application...

REM Important pour Jenkins :
REM empeche Jenkins de tuer l'application a la fin du build
set "JENKINS_NODE_COOKIE=dontKillMe"

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "$p = Start-Process -FilePath '%JAVA_EXE%' -ArgumentList '-jar','%APP_DIR%\%JAR_NAME%' -WorkingDirectory '%APP_DIR%' -RedirectStandardOutput '%LOG_FILE%' -RedirectStandardError '%ERROR_LOG_FILE%' -PassThru; Set-Content -Path '%PID_FILE%' -Value $p.Id; Write-Host ('Application demarree avec PID : ' + $p.Id)"

if errorlevel 1 (
    echo ERREUR : impossible de demarrer l'application.
    exit /b 1
)

echo.
echo [4/5] Attente du demarrage...

timeout /t 10 /nobreak >nul

echo.
echo [5/5] Verification de l'application...

powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "try { $r = Invoke-WebRequest -Uri 'http://localhost:8081/api/profs' -UseBasicParsing -TimeoutSec 5; if ($r.StatusCode -eq 200) { exit 0 } else { exit 1 } } catch { exit 1 }"

if errorlevel 1 (
    echo.
    echo ERREUR : l'API ne repond pas sur le port 8081.
    echo.
    echo ===== LOG APPLICATION =====
    powershell -NoProfile -Command "Get-Content '%LOG_FILE%' -Tail 30"
    echo.
    echo ===== LOG ERREUR =====
    if exist "%ERROR_LOG_FILE%" powershell -NoProfile -Command "Get-Content '%ERROR_LOG_FILE%' -Tail 30"
    echo.
    exit /b 1
)

echo.
echo ========================================
echo   DEPLOIEMENT REUSSI
echo ========================================
echo.
echo API  : http://localhost:8081
echo PID  : 
type "%PID_FILE%"
echo Logs : %LOG_FILE%
echo.

endlocal
exit /b 0