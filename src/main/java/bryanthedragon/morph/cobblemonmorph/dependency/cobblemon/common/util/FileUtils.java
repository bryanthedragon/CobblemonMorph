package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import java.io.Closeable
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

public object FileUtils {
   public fun unzipFile(source: Path, target: Path) {
      label39: {
         val var3: Closeable = new ZipInputStream(new FileInputStream(source.toFile()));
         var var4: java.lang.Throwable = null;

         try {
            try {
               val zis: ZipInputStream = var3 as ZipInputStream;

               for (ZipEntry zipEntry = ((ZipInputStream)var3).getNextEntry(); zipEntry != null; zipEntry = zis.getNextEntry()) {
                  val newPath: Path = INSTANCE.checkPath(zipEntry, target);
                  if (!zipEntry.isDirectory()) {
                     if (newPath.getParent() != null && Files.notExists(newPath.getParent())) {
                        Files.createDirectories(newPath.getParent());
                     }

                     Files.copy(zis, newPath, StandardCopyOption.REPLACE_EXISTING);
                  } else {
                     Files.createDirectories(newPath);
                  }
               }

               zis.closeEntry();
            } catch (var10: java.lang.Throwable) {
               var4 = var10;
               throw var10;
            }
         } catch (var11: java.lang.Throwable) {
            CloseableKt.closeFinally(var3, var4);
         }

         CloseableKt.closeFinally(var3, null);
      }
   }

   private fun checkPath(zipEntry: ZipEntry, targetDir: Path): Path {
      val normalizePath: Path = targetDir.resolve(zipEntry.getName()).normalize().toAbsolutePath();
      if (!normalizePath.startsWith(targetDir.normalize().toAbsolutePath())) {
         throw new IOException("Bad zip entry: ${zipEntry.getName()}");
      } else {
         return normalizePath;
      }
   }
}
