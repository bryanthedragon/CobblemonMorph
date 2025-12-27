package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.FileUtils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.file.Path
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nGraalShowdownUnbundler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GraalShowdownUnbundler.kt\ncom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler\n+ 2 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n*L\n1#1,96:1\n17#2:97\n17#2:98\n*S KotlinDebug\n*F\n+ 1 GraalShowdownUnbundler.kt\ncom/cobblemon/mod/common/battles/runner/graal/GraalShowdownUnbundler\n*L\n76#1:97\n86#1:98\n*E\n"])
public class GraalShowdownUnbundler {
   private final val gson: Gson = new GsonBuilder().disableHtmlEscaping().create()

   public fun attemptUnbundle() {
      val showdownDir: File = new File("showdown");
      val metadata: GraalShowdownUnbundler.ShowdownMetadata = this.loadShowdownMetadata();
      if (!showdownDir.exists() || Cobblemon.INSTANCE.getConfig().getAutoUpdateShowdown()) {
         showdownDir.mkdirs();
         val showdownZip: File = new File(showdownDir, "showdown.zip");
         val showdownMetadataFile: File = new File(showdownDir, "showdown.json");
         var extract: Boolean = true;
         if (showdownMetadataFile.exists()) {
            val current: GraalShowdownUnbundler.ShowdownMetadata = this.readShowdownMetadata(showdownMetadataFile);
            val var10000: Double = metadata.getShowdownVersion();
            if (var10000 == current.getShowdownVersion()) {
               extract = false;
            } else {
               Cobblemon.INSTANCE
                  .getLOGGER()
                  .info("Updating showdown service to version ${metadata.getShowdownVersion()}, from version ${current.getShowdownVersion()}...");
               val backupDir: File = new File("showdown-backup");
               if (backupDir.exists() && backupDir.isDirectory()) {
                  FilesKt.deleteRecursively(backupDir);
               }

               FilesKt.copyTo$default(showdownDir, backupDir, false, 0, 6, null);
            }
         }

         if (extract) {
            ResourceLocationExtensionsKt.extractTo(new ResourceLocation("cobblemon", "showdown.zip"), showdownZip);
            ResourceLocationExtensionsKt.extractTo(new ResourceLocation("cobblemon", "showdown.json"), showdownMetadataFile);
            val var8: FileUtils = FileUtils.INSTANCE;
            val var10001: Path = showdownZip.toPath();
            val var10002: Path = showdownDir.toPath();
            var8.unzipFile(var10001, var10002);
            showdownZip.delete();
         }
      }
   }

   private fun loadShowdownMetadata(): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal.GraalShowdownUnbundler.ShowdownMetadata? {
      try {
         val var10000: InputStream = this.getClass().getResourceAsStream("/assets/cobblemon/showdown.json");
         val var6: Gson = this.gson;
         return var6.fromJson(new InputStreamReader(var10000), GraalShowdownUnbundler.ShowdownMetadata.class) as GraalShowdownUnbundler.ShowdownMetadata;
      } catch (var5: Exception) {
         var5.printStackTrace();
         return null;
      }
   }

   private fun readShowdownMetadata(target: File): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal.GraalShowdownUnbundler.ShowdownMetadata? {
      try {
         label21: {
            val exception: Closeable = new InputStreamReader(new FileInputStream(target));
            var var3: java.lang.Throwable = null;

            try {
               try {
                  val it: InputStreamReader = exception as InputStreamReader;
                  val var10000: Gson = this.gson;
                  val var8: GraalShowdownUnbundler.ShowdownMetadata = var10000.fromJson(it, GraalShowdownUnbundler.ShowdownMetadata.class) as GraalShowdownUnbundler.ShowdownMetadata;
               } catch (var9: java.lang.Throwable) {
                  var3 = var9;
                  throw var9;
               }
            } catch (var10: java.lang.Throwable) {
               CloseableKt.closeFinally(exception, var3);
            }

            CloseableKt.closeFinally(exception, null);
         }
      } catch (var11: Exception) {
         var11.printStackTrace();
         return null;
      }
   }

   private data class ShowdownMetadata(showdownVersion: Double) {
      public final val showdownVersion: Double

      init {
         this.showdownVersion = showdownVersion;
      }

      public operator fun component1(): Double {
         return this.showdownVersion;
      }

      public fun copy(showdownVersion: Double = this.showdownVersion): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal.GraalShowdownUnbundler.ShowdownMetadata {
         return new GraalShowdownUnbundler.ShowdownMetadata(showdownVersion);
      }

      public override fun toString(): String {
         return "ShowdownMetadata(showdownVersion=${this.showdownVersion})";
      }

      public override fun hashCode(): Int {
         return java.lang.Double.hashCode(this.showdownVersion);
      }

      public override operator fun equals(other: Any?): Boolean {
         if (this === other) {
            return true;
         } else if (other !is GraalShowdownUnbundler.ShowdownMetadata) {
            return false;
         } else {
            return java.lang.Double.compare(this.showdownVersion, (other as GraalShowdownUnbundler.ShowdownMetadata).showdownVersion) == 0;
         }
      }
   }
}
