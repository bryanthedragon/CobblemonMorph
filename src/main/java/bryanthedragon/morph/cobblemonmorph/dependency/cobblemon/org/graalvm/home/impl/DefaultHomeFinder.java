
package org.graalvm.home.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.graalvm.home.HomeFinder;
import org.graalvm.home.impl.VmLocatorSymbol;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.nativeimage.ProcessProperties;

public final class DefaultHomeFinder
extends HomeFinder {
    private static final boolean STATIC_VERBOSE = Boolean.getBoolean("com.oracle.graalvm.locator.verbose");
    private static final Path FORCE_GRAAL_HOME;
    private static final Path GRAAL_HOME_RELATIVE_PATH;
    private static final Map<String, Path> LANGUAGE_RELATIVE_HOMES;
    private static final String GRAALVM_VERSION_PROPERTY = "org.graalvm.version";
    private static final String GRAALVM_VERSION;
    private static final Object HOME_NOT_FOUND;
    private volatile Boolean verbose;
    private volatile String version;
    private volatile Object graalVMHome;
    private volatile Map<String, Path> languageHomes;
    private volatile Map<String, Path> toolHomes;

    @Override
    public Path getHomeFolder() {
        Object home = this.graalVMHome;
        if (home == null) {
            Path result = this.searchHomeFolder();
            Object object = home = result != null ? result : HOME_NOT_FOUND;
            if (!ImageInfo.inImageBuildtimeCode()) {
                this.graalVMHome = home;
            }
        }
        if (home instanceof Path) {
            return (Path)home;
        }
        assert (home == HOME_NOT_FOUND);
        return null;
    }

    private Path searchHomeFolder() {
        Path home;
        if (this.isVerbose()) {
            System.err.println("FORCE_GRAAL_HOME: " + FORCE_GRAAL_HOME);
            System.err.println("GRAAL_HOME_RELATIVE_PATH: " + GRAAL_HOME_RELATIVE_PATH);
            for (Map.Entry<String, Path> entry : LANGUAGE_RELATIVE_HOMES.entrySet()) {
                System.err.println("relative home of " + entry.getKey() + " from the launcher's directory: " + entry.getValue());
            }
        }
        if (FORCE_GRAAL_HOME != null) {
            this.verbose("GraalVM home forced to: ", FORCE_GRAAL_HOME);
            return FORCE_GRAAL_HOME;
        }
        if (ImageInfo.inImageRuntimeCode()) {
            String graalvmHomeValue = System.getProperty("org.graalvm.home");
            if (graalvmHomeValue != null) {
                this.verbose("GraalVM home already set to: ", graalvmHomeValue);
                home = Paths.get(graalvmHomeValue, new String[0]);
            } else {
                home = this.getGraalVmHomeNative();
                this.verbose("Found GraalVM home: ", home);
                if (home == null) {
                    return null;
                }
            }
            if (!Files.exists(home, new LinkOption[0])) {
                throw new AssertionError((Object)"GraalVM home is not reachable.");
            }
            return home;
        }
        String javaHomeProperty = System.getProperty("java.home");
        if (javaHomeProperty == null) {
            throw new AssertionError((Object)"The java.home system property is not set");
        }
        Path javaHome = Paths.get(javaHomeProperty, new String[0]);
        if (!Files.exists(javaHome, new LinkOption[0])) {
            throw new AssertionError((Object)"Java home is not reachable.");
        }
        if (!Files.exists(javaHome.resolve(Paths.get("lib", "modules")), new LinkOption[0])) {
            throw new AssertionError((Object)("Missing jimage in java.home: " + javaHome));
        }
        home = javaHome;
        this.verbose("GraalVM home found by java.home property as: ", home);
        return home;
    }

    @Override
    public String getVersion() {
        String res = this.version;
        if (res == null) {
            if (GRAALVM_VERSION != null) {
                res = GRAALVM_VERSION;
            } else {
                Path releaseFile;
                res = "snapshot";
                Path home = this.getHomeFolder();
                if (home != null && Files.exists(releaseFile = home.resolve("release"), new LinkOption[0])) {
                    try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(releaseFile, StandardOpenOption.READ));){
                        Properties properties2 = new Properties();
                        properties2.load(in);
                        Object loadedVersion = properties2.get("GRAALVM_VERSION");
                        if (loadedVersion != null) {
                            res = loadedVersion.toString();
                            if (res.startsWith("\"")) {
                                res = res.substring(1, res.length());
                            }
                            if (res.endsWith("\"")) {
                                res = res.substring(0, res.length() - 1);
                            }
                        }
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            }
            if (!ImageInfo.inImageBuildtimeCode()) {
                this.version = res;
            }
        }
        return res;
    }

    @Override
    public Map<String, Path> getLanguageHomes() {
        Map<String, Path> res = this.languageHomes;
        if (res == null) {
            Path home = this.getHomeFolder();
            if (home == null) {
                res = Collections.unmodifiableMap(this.collectStandaloneHomes());
            } else {
                res = DefaultHomeFinder.collectHomes(home.resolve(Paths.get("languages", new String[0])));
                for (Object property : System.getProperties().keySet()) {
                    String languageId;
                    String after2;
                    String name;
                    if (!(property instanceof String) || !(name = (String)property).startsWith("org.graalvm.language.") || !name.endsWith(".home") || (after2 = name.substring("org.graalvm.language.".length())).length() <= ".home".length() || (languageId = after2.substring(0, after2.length() - ".home".length())).contains(".")) continue;
                    res.put(languageId, Paths.get(System.getProperty(name), new String[0]));
                }
                res = Collections.unmodifiableMap(res);
            }
            if (!ImageInfo.inImageBuildtimeCode()) {
                this.languageHomes = res;
            }
        }
        return res;
    }

    @Override
    public Map<String, Path> getToolHomes() {
        Map<String, Path> res = this.toolHomes;
        if (res == null) {
            Path home = this.getHomeFolder();
            res = home == null ? Collections.emptyMap() : Collections.unmodifiableMap(DefaultHomeFinder.collectHomes(home.resolve(Paths.get("tools", new String[0]))));
            if (!ImageInfo.inImageBuildtimeCode()) {
                this.toolHomes = res;
            }
        }
        return res;
    }

    private static Map<String, Path> collectHomes(Path folder) {
        HashMap<String, Path> res = new HashMap<String, Path>();
        if (Files.exists(folder, new LinkOption[0])) {
            try (DirectoryStream<Path> dirContent = Files.newDirectoryStream(folder, (DirectoryStream.Filter<? super Path>)new DirectoryStream.Filter<Path>(){

                @Override
                public boolean accept(Path entry) throws IOException {
                    Path fileName = entry.getFileName();
                    if (fileName == null) {
                        return false;
                    }
                    return !fileName.toString().startsWith(".");
                }
            });){
                for (Path home : dirContent) {
                    Path filename = home.getFileName();
                    if (filename == null) continue;
                    res.put(filename.toString(), home);
                }
            }
            catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
        return res;
    }

    private Map<String, Path> collectStandaloneHomes() {
        HashMap<String, Path> res = new HashMap<String, Path>();
        Path executableOrObjFile = null;
        if (ImageInfo.isExecutable()) {
            executableOrObjFile = DefaultHomeFinder.getCurrentExecutablePath();
        } else if (ImageInfo.isSharedLibrary()) {
            executableOrObjFile = DefaultHomeFinder.getCurrentObjectFilePath();
        }
        if (executableOrObjFile != null) {
            Path launcherDir = executableOrObjFile.getParent();
            for (Map.Entry<String, Path> entry : LANGUAGE_RELATIVE_HOMES.entrySet()) {
                Path langHome = launcherDir.resolve(entry.getValue()).normalize();
                String langId = entry.getKey();
                res.put(langId, langHome);
                this.verbose("Resolved the ", langId, " home as ", langHome);
            }
        }
        return res;
    }

    private Path getGraalVmHomeNative() {
        Path result;
        Path executable = DefaultHomeFinder.getCurrentExecutablePath();
        if (executable != null && (result = DefaultHomeFinder.getGraalVmHomeFromRelativeLauncherPath(executable)) != null) {
            this.verbose("GraalVM home found by executable as: ", result);
            return result;
        }
        Path objectFile = DefaultHomeFinder.getCurrentObjectFilePath();
        if (objectFile != null) {
            Path result2 = DefaultHomeFinder.getGraalVmHomeFromRelativeLauncherPath(objectFile);
            if (result2 == null) {
                result2 = DefaultHomeFinder.getGraalVmHomeLibPolyglotFallBack(objectFile);
            }
            if (result2 != null) {
                this.verbose("GraalVM home found by object file as: ", result2);
                return result2;
            }
        }
        return null;
    }

    private static Path getGraalVmHomeFromRelativeLauncherPath(Path executableOrObjFile) {
        Path result;
        if (GRAAL_HOME_RELATIVE_PATH != null && (result = DefaultHomeFinder.trimAbsolutePath(executableOrObjFile, GRAAL_HOME_RELATIVE_PATH)) != null) {
            return result;
        }
        return null;
    }

    private static Path getGraalVmHomeLibPolyglotFallBack(Path objectFile) {
        Path parent = objectFile.getParent();
        if (parent == null || !"polyglot".equals(DefaultHomeFinder.getFileName(parent))) {
            return null;
        }
        if ((parent = parent.getParent()) == null || !"lib".equals(DefaultHomeFinder.getFileName(parent))) {
            return null;
        }
        Path home = null;
        Path jdk = parent.getParent();
        if (jdk != null) {
            home = jdk;
        }
        return home != null && DefaultHomeFinder.isJdkHome(home) ? home : null;
    }

    private static boolean isJdkHome(Path path) {
        Path javac = path.resolve(Paths.get("bin", "javac"));
        return DefaultHomeFinder.isJreHome(path) && Files.isRegularFile(javac, new LinkOption[0]) && Files.isExecutable(javac);
    }

    private static boolean isJreHome(Path path) {
        Path java = path.resolve(Paths.get("bin", "java"));
        return Files.isRegularFile(java, new LinkOption[0]) && Files.isExecutable(java);
    }

    private static Path trimAbsolutePath(Path absolute, Path expectedRelative) {
        Path result = absolute;
        for (Path p = expectedRelative; p != null; p = p.getParent()) {
            if (result == null) {
                return null;
            }
            Path filename = result.getFileName();
            if (filename == null || !filename.equals(p.getFileName())) {
                return null;
            }
            result = result.getParent();
        }
        return result;
    }

    private static Path getCurrentObjectFilePath() {
        String path = ProcessProperties.getObjectFile(VmLocatorSymbol.SYMBOL);
        return path == null ? null : Paths.get(path, new String[0]);
    }

    private static Path getCurrentExecutablePath() {
        String path = ProcessProperties.getExecutableName();
        return path == null ? null : Paths.get(path, new String[0]);
    }

    private static String getFileName(Path path) {
        Path fileName = path.getFileName();
        return fileName == null ? null : fileName.toString();
    }

    private boolean isVerbose() {
        if (ImageInfo.inImageBuildtimeCode()) {
            return STATIC_VERBOSE;
        }
        Boolean res = this.verbose;
        if (res == null) {
            this.verbose = res = Boolean.valueOf(STATIC_VERBOSE || Boolean.parseBoolean(System.getenv("VERBOSE_GRAALVM_LOCATOR")));
        }
        return res;
    }

    private void verbose(Object ... args) {
        if (this.isVerbose()) {
            StringBuilder builder = new StringBuilder();
            for (Object arg : args) {
                builder.append(arg);
            }
            System.err.println(builder.toString());
        }
    }

    static {
        LANGUAGE_RELATIVE_HOMES = new HashMap<String, Path>();
        String forcedHome = System.getProperty("org.graalvm.launcher.home");
        FORCE_GRAAL_HOME = forcedHome != null && forcedHome.length() > 0 ? Paths.get(forcedHome, new String[0]) : null;
        String relativeHome = System.getProperty("org.graalvm.launcher.relative.home");
        GRAAL_HOME_RELATIVE_PATH = relativeHome != null && relativeHome.length() > 0 ? Paths.get(relativeHome, new String[0]) : null;
        for (Object property : System.getProperties().keySet()) {
            String after2;
            String name;
            if (!(property instanceof String) || !(name = (String)property).startsWith("org.graalvm.launcher.relative.") || !name.endsWith(".home") || (after2 = name.substring("org.graalvm.launcher.relative.".length())).length() <= ".home".length()) continue;
            String languageId = after2.substring(0, after2.length() - ".home".length());
            LANGUAGE_RELATIVE_HOMES.put(languageId, Paths.get(System.getProperty(name), new String[0]));
        }
        GRAALVM_VERSION = System.getProperty(GRAALVM_VERSION_PROPERTY);
        HOME_NOT_FOUND = new Object();
    }
}

