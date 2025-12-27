package org.graalvm.home.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.DirectoryStream.Filter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import org.graalvm.home.HomeFinder;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.nativeimage.ProcessProperties;

public final class DefaultHomeFinder extends HomeFinder {
   private static final boolean STATIC_VERBOSE = Boolean.getBoolean("com.oracle.graalvm.locator.verbose");
   private static final Path FORCE_GRAAL_HOME;
   private static final Path GRAAL_HOME_RELATIVE_PATH;
   private static final Map<String, Path> LANGUAGE_RELATIVE_HOMES = new HashMap<>();
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
         Object result = this.searchHomeFolder();
         home = result != null ? result : HOME_NOT_FOUND;
         if (!ImageInfo.inImageBuildtimeCode()) {
            this.graalVMHome = home;
         }
      }

      if (home instanceof Path) {
         return (Path)home;
      } else {
         assert home == HOME_NOT_FOUND;

         return null;
      }
   }

   private Path searchHomeFolder() {
      if (this.isVerbose()) {
         System.err.println("FORCE_GRAAL_HOME: " + FORCE_GRAAL_HOME);
         System.err.println("GRAAL_HOME_RELATIVE_PATH: " + GRAAL_HOME_RELATIVE_PATH);

         for (Entry<String, Path> entry : LANGUAGE_RELATIVE_HOMES.entrySet()) {
            System.err.println("relative home of " + entry.getKey() + " from the launcher's directory: " + entry.getValue());
         }
      }

      if (FORCE_GRAAL_HOME != null) {
         this.verbose("GraalVM home forced to: ", FORCE_GRAAL_HOME);
         return FORCE_GRAAL_HOME;
      } else if (ImageInfo.inImageRuntimeCode()) {
         String graalvmHomeValue = System.getProperty("org.graalvm.home");
         Path home;
         if (graalvmHomeValue != null) {
            this.verbose("GraalVM home already set to: ", graalvmHomeValue);
            home = Paths.get(graalvmHomeValue);
         } else {
            home = this.getGraalVmHomeNative();
            this.verbose("Found GraalVM home: ", home);
            if (home == null) {
               return null;
            }
         }

         if (!Files.exists(home)) {
            throw new AssertionError("GraalVM home is not reachable.");
         } else {
            return home;
         }
      } else {
         String javaHomeProperty = System.getProperty("java.home");
         if (javaHomeProperty == null) {
            throw new AssertionError("The java.home system property is not set");
         } else {
            Path javaHome = Paths.get(javaHomeProperty);
            if (!Files.exists(javaHome)) {
               throw new AssertionError("Java home is not reachable.");
            } else if (Files.exists(javaHome.resolve(Paths.get("lib", "modules")))) {
               this.verbose("GraalVM home found by java.home property as: ", javaHome);
               return javaHome;
            } else {
               throw new AssertionError("Missing jimage in java.home: " + javaHome);
            }
         }
      }
   }

   @Override
   public String getVersion() {
      String res = this.version;
      if (res == null) {
         if (GRAALVM_VERSION != null) {
            res = GRAALVM_VERSION;
         } else {
            res = "snapshot";
            Path home = this.getHomeFolder();
            if (home != null) {
               Path releaseFile = home.resolve("release");
               if (Files.exists(releaseFile)) {
                  try (InputStream in = new BufferedInputStream(Files.newInputStream(releaseFile, StandardOpenOption.READ))) {
                     Properties properties = new Properties();
                     properties.load(in);
                     Object loadedVersion = properties.get("GRAALVM_VERSION");
                     if (loadedVersion != null) {
                        res = loadedVersion.toString();
                        if (res.startsWith("\"")) {
                           res = res.substring(1, res.length());
                        }

                        if (res.endsWith("\"")) {
                           res = res.substring(0, res.length() - 1);
                        }
                     }
                  } catch (IOException var9) {
                  }
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
            res = collectHomes(home.resolve(Paths.get("languages")));

            for (Object property : System.getProperties().keySet()) {
               if (property instanceof String) {
                  String name = (String)property;
                  if (name.startsWith("org.graalvm.language.") && name.endsWith(".home")) {
                     String after = name.substring("org.graalvm.language.".length());
                     if (after.length() > ".home".length()) {
                        String languageId = after.substring(0, after.length() - ".home".length());
                        if (!languageId.contains(".")) {
                           res.put(languageId, Paths.get(System.getProperty(name)));
                        }
                     }
                  }
               }
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
         if (home == null) {
            res = Collections.emptyMap();
         } else {
            res = Collections.unmodifiableMap(collectHomes(home.resolve(Paths.get("tools"))));
         }

         if (!ImageInfo.inImageBuildtimeCode()) {
            this.toolHomes = res;
         }
      }

      return res;
   }

   private static Map<String, Path> collectHomes(Path folder) {
      Map<String, Path> res = new HashMap<>();
      if (Files.exists(folder)) {
         try (DirectoryStream<Path> dirContent = Files.newDirectoryStream(folder, new Filter<Path>() {
               public boolean accept(Path entry) throws IOException {
                  Path fileName = entry.getFileName();
                  return fileName == null ? false : !fileName.toString().startsWith(".");
               }
            })) {
            for (Path home : dirContent) {
               Path filename = home.getFileName();
               if (filename != null) {
                  res.put(filename.toString(), home);
               }
            }
         } catch (IOException var8) {
            throw new RuntimeException(var8);
         }
      }

      return res;
   }

   private Map<String, Path> collectStandaloneHomes() {
      Map<String, Path> res = new HashMap<>();
      Path executableOrObjFile = null;
      if (ImageInfo.isExecutable()) {
         executableOrObjFile = getCurrentExecutablePath();
      } else if (ImageInfo.isSharedLibrary()) {
         executableOrObjFile = getCurrentObjectFilePath();
      }

      if (executableOrObjFile != null) {
         Path launcherDir = executableOrObjFile.getParent();

         for (Entry<String, Path> entry : LANGUAGE_RELATIVE_HOMES.entrySet()) {
            Path langHome = launcherDir.resolve(entry.getValue()).normalize();
            String langId = entry.getKey();
            res.put(langId, langHome);
            this.verbose("Resolved the ", langId, " home as ", langHome);
         }
      }

      return res;
   }

   private Path getGraalVmHomeNative() {
      Path executable = getCurrentExecutablePath();
      if (executable != null) {
         Path result = getGraalVmHomeFromRelativeLauncherPath(executable);
         if (result != null) {
            this.verbose("GraalVM home found by executable as: ", result);
            return result;
         }
      }

      Path objectFile = getCurrentObjectFilePath();
      if (objectFile != null) {
         Path result = getGraalVmHomeFromRelativeLauncherPath(objectFile);
         if (result == null) {
            result = getGraalVmHomeLibPolyglotFallBack(objectFile);
         }

         if (result != null) {
            this.verbose("GraalVM home found by object file as: ", result);
            return result;
         }
      }

      return null;
   }

   private static Path getGraalVmHomeFromRelativeLauncherPath(Path executableOrObjFile) {
      if (GRAAL_HOME_RELATIVE_PATH != null) {
         Path result = trimAbsolutePath(executableOrObjFile, GRAAL_HOME_RELATIVE_PATH);
         if (result != null) {
            return result;
         }
      }

      return null;
   }

   private static Path getGraalVmHomeLibPolyglotFallBack(Path objectFile) {
      Path parent = objectFile.getParent();
      if (parent != null && "polyglot".equals(getFileName(parent))) {
         parent = parent.getParent();
         if (parent != null && "lib".equals(getFileName(parent))) {
            Path home = null;
            Path jdk = parent.getParent();
            if (jdk != null) {
               home = jdk;
            }

            return home != null && isJdkHome(home) ? home : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static boolean isJdkHome(Path path) {
      Path javac = path.resolve(Paths.get("bin", "javac"));
      return isJreHome(path) && Files.isRegularFile(javac) && Files.isExecutable(javac);
   }

   private static boolean isJreHome(Path path) {
      Path java = path.resolve(Paths.get("bin", "java"));
      return Files.isRegularFile(java) && Files.isExecutable(java);
   }

   private static Path trimAbsolutePath(Path absolute, Path expectedRelative) {
      Path p = expectedRelative;

      Path result;
      for (result = absolute; p != null; p = p.getParent()) {
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
      return path == null ? null : Paths.get(path);
   }

   private static Path getCurrentExecutablePath() {
      String path = ProcessProperties.getExecutableName();
      return path == null ? null : Paths.get(path);
   }

   private static String getFileName(Path path) {
      Path fileName = path.getFileName();
      return fileName == null ? null : fileName.toString();
   }

   private boolean isVerbose() {
      if (ImageInfo.inImageBuildtimeCode()) {
         return STATIC_VERBOSE;
      } else {
         Boolean res = this.verbose;
         if (res == null) {
            res = STATIC_VERBOSE || Boolean.parseBoolean(System.getenv("VERBOSE_GRAALVM_LOCATOR"));
            this.verbose = res;
         }

         return res;
      }
   }

   private void verbose(Object... args) {
      if (this.isVerbose()) {
         StringBuilder builder = new StringBuilder();

         for (Object arg : args) {
            builder.append(arg);
         }

         System.err.println(builder.toString());
      }
   }

   static {
      String forcedHome = System.getProperty("org.graalvm.launcher.home");
      if (forcedHome != null && forcedHome.length() > 0) {
         FORCE_GRAAL_HOME = Paths.get(forcedHome);
      } else {
         FORCE_GRAAL_HOME = null;
      }

      String relativeHome = System.getProperty("org.graalvm.launcher.relative.home");
      if (relativeHome != null && relativeHome.length() > 0) {
         GRAAL_HOME_RELATIVE_PATH = Paths.get(relativeHome);
      } else {
         GRAAL_HOME_RELATIVE_PATH = null;
      }

      for (Object property : System.getProperties().keySet()) {
         if (property instanceof String) {
            String name = (String)property;
            if (name.startsWith("org.graalvm.launcher.relative.") && name.endsWith(".home")) {
               String after = name.substring("org.graalvm.launcher.relative.".length());
               if (after.length() > ".home".length()) {
                  String languageId = after.substring(0, after.length() - ".home".length());
                  LANGUAGE_RELATIVE_HOMES.put(languageId, Paths.get(System.getProperty(name)));
               }
            }
         }
      }

      GRAALVM_VERSION = System.getProperty("org.graalvm.version");
      HOME_NOT_FOUND = new Object();
   }
}
