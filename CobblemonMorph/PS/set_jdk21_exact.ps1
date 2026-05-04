$jdk='C:\Program Files\Java\jdk-21.0.11'
setx JAVA_HOME "$jdk" | Out-Null
$Env:JAVA_HOME = $jdk
$Env:PATH = "$Env:JAVA_HOME\bin;" + $Env:PATH
$gradleProps = Join-Path $Env:USERPROFILE '.gradle\gradle.properties'
$dir = Split-Path $gradleProps
if (-not (Test-Path $dir)) { New-Item -ItemType Directory -Path $dir -Force | Out-Null }
if (Test-Path $gradleProps) {
  (Get-Content $gradleProps) -replace '^(org\.gradle\.java\.home\s*=).*$', 'org.gradle.java.home=C:\\Program Files\\Java\\jdk-21.0.11' | Set-Content $gradleProps
} else {
  'org.gradle.java.home=C:\\Program Files\\Java\\jdk-21.0.11' | Out-File -FilePath $gradleProps -Encoding utf8
}
Write-Output "JAVA_HOME=$Env:JAVA_HOME"
Write-Output "Gradle user properties: $gradleProps"
Get-Content $gradleProps | Write-Output
.\gradlew.bat -v
