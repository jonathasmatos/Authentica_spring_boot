@REM ----------------------------------------------------------------------------
@REM Maven Wrapper Start Up Batch script for Windows
@REM ----------------------------------------------------------------------------

@setlocal

@set ERROR_CODE=0
@set MAVEN_PROJECTBASEDIR=%~dp0

@set WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar

@if not exist "%WRAPPER_JAR%" (
    echo [ERROR] Maven Wrapper JAR not found: %WRAPPER_JAR%
    goto error
)

@set MAVEN_CMD_LINE_ARGS=%*

@REM Estabiliza o contexto do diretório raiz para o Maven
@set "MAVEN_OPTS=-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%"

java %MAVEN_OPTS% -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %MAVEN_CMD_LINE_ARGS%

@if "%ERRORLEVEL%" neq "0" goto error
@goto end

:error
@set ERROR_CODE=1

:end
@exit /B %ERROR_CODE%
