package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import java.io.File
import java.net.URI
import java.net.URL
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import java.util.ArrayList;
import java.util.Arrays
import net.minecraft.resources.ResourceLocation
import org.jetbrains.annotations.NotNull

public object AssetLoading {
   public fun ResourceLocation.toPath(): Path? {
      val var10000: URI = this.toURL(`$this$toPath`);
      val var2: Path;
      if (var10000 != null) {
         var2 = Paths.get(var10000);
      } else {
         var2 = null;
      }

      return var2;
   }

   public fun ResourceLocation.toURL(): URI? {
      val var3: Array<Any> = new Object[]{`$this$toURL`.m_135827_(), `$this$toURL`.m_135815_()};
      val var10001: java.lang.String = java.lang.String.format("/assets/%s/%s", Arrays.copyOf(var3, var3.length));
      val var10000: URL = Cobblemon.class.getResource(var10001);
      return if (var10000 != null) var10000.toURI() else null;
   }

   public fun fileSearch(dir: Path, filter: (Path) -> Boolean, recursive: Boolean): List<Path> {
      val files: java.util.List = new ArrayList();
      Files.walkFileTree(dir, new SimpleFileVisitor<Path>(filter, files, recursive) {
         {
            this.$filter = `$filter`;
            this.$files = `$files`;
            this.$recursive = `$recursive`;
         }

         @NotNull
         public FileVisitResult visitFile(@NotNull Path file, @NotNull BasicFileAttributes fileAttributes) {
            if (this.$filter.invoke(file) as java.lang.Boolean) {
               this.$files.add(file);
            }

            val var10000: FileVisitResult;
            if (this.$recursive) {
               var10000 = FileVisitResult.CONTINUE;
            } else {
               if (this.$recursive) {
                  throw new NoWhenBranchMatchedException();
               }

               var10000 = FileVisitResult.SKIP_SUBTREE;
            }

            return var10000;
         }
      });
      return files;
   }

   public fun searchFor(dir: String, suffix: String, list: MutableList<File>) {
      val var10000: Array<java.lang.String> = new File(dir).list();
      if (var10000 != null) {
         for (java.lang.String name : var10000) {
            val subFile: File = new File("$dir/$name");
            if (subFile.isFile()) {
               if (StringsKt.endsWith$default(name, suffix, false, 2, null)) {
                  list.add(subFile);
                  continue;
               }
            }

            if (subFile.isDirectory()) {
               this.searchFor("$dir/$name", suffix, list);
            }
         }
      }
   }
}
