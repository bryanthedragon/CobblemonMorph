# Detect an installed JDK 21 directory and use it for Gradle.
# This is useful when the JDK is installed as jdk-21.0.11 or similar.
$searchPaths = @(
    'C:\Program Files\Java',
    'C:\Program Files (x86)\Java',
    'C:\Program Files\Zulu',
    'C:\Program Files\AdoptOpenJDK',
    'C:\Program Files\Amazon Corretto',
    'C:\Program Files\BellSoft'
)
$jdkDirs = @()
foreach ($path in $searchPaths) {
    if (Test-Path $path) {
        $jdkDirs += Get-ChildItem -Path $path -Directory -ErrorAction SilentlyContinue | Where-Object { $_.Name -match '^jdk-21(\.|$)' }
    }
}
if ($jdkDirs.Count -eq 0) {
    Write-Error 'No JDK 21 installation found in common locations. Please install JDK 21 or update this script with the correct path.'
    exit 1
}
$jdk = $jdkDirs | Sort-Object Name -Descending | Select-Object -First 1 | Select-Object -ExpandProperty FullName
# Persist for future terminals
setx JAVA_HOME "$jdk" | Out-Null
# Set for this session
$Env:JAVA_HOME = $jdk
$Env:PATH = "$Env:JAVA_HOME\bin;" + $Env:PATH
# Ensure Gradle user properties exists and set org.gradle.java.home
$gradleProps = Join-Path $Env:USERPROFILE '.gradle\gradle.properties'
$dir = Split-Path $gradleProps
if (-not (Test-Path $dir)) { New-Item -ItemType Directory -Path $dir -Force | Out-Null }
$javaHomeValue = $jdk -replace '\\', '\\\\'
if (Test-Path $gradleProps) {
  (Get-Content $gradleProps) -replace '^(org\.gradle\.java\.home\s*=).*$', "org.gradle.java.home=$javaHomeValue" | Set-Content $gradleProps
} else {
  "org.gradle.java.home=$javaHomeValue" | Out-File -FilePath $gradleProps -Encoding utf8
}
Write-Output "JAVA_HOME=$Env:JAVA_HOME"
Write-Output "Gradle user properties: $gradleProps"
Get-Content $gradleProps | Write-Output
# Run gradle to show Java version used
.\gradlew.bat -v
