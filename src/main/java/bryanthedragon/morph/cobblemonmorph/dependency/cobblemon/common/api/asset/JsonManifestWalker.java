package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.asset

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintWriter
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nJsonManifestWalker.kt\nKotlin\n*S Kotlin\n*F\n+ 1 JsonManifestWalker.kt\ncom/cobblemon/mod/common/api/asset/JsonManifestWalker\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 3 GsonExtensions.kt\ncom/cobblemon/mod/common/util/GsonExtensionsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,82:1\n13579#2,2:83\n17#3:85\n1855#4,2:86\n*S KotlinDebug\n*F\n+ 1 JsonManifestWalker.kt\ncom/cobblemon/mod/common/api/asset/JsonManifestWalker\n*L\n48#1:83,2\n65#1:85\n66#1:86,2\n*E\n"])
public object JsonManifestWalker {
   internal fun build(manifestPath: String) {
      val file: File = new File(manifestPath);
      file.createNewFile();
      val folder: File = file.getParentFile();
      folder.mkdir();
      val members: Array<File> = folder.listFiles(JsonManifestWalker::build$lambda$0);
      val jsonArray: JsonArray = new JsonArray();

      for (Object element$iv : members) {
         jsonArray.add(FilesKt.relativeTo((File)`element$iv`, folder).toString());
      }

      val var13: PrintWriter = new PrintWriter(file);
      new GsonBuilder().setPrettyPrinting().create().toJson(jsonArray as JsonElement, var13);
      var13.flush();
      var13.close();
   }

   public fun <T> load(clazz: Class<Any>, folder: String, gson: Gson): List<Any> {
      val manifestPath: java.lang.String = "/assets/cobblemon/$folder/_MANIFEST.json";
      val var10000: InputStream = Cobblemon.class.getResourceAsStream(manifestPath);
      val folderPath: java.lang.String = StringsKt.substringBeforeLast$default(manifestPath, "/", null, 2, null);
      val list: java.util.List = new ArrayList();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val path: java.lang.String = (`element$iv` as JsonElement).getAsString();
         val exception: InputStream = Cobblemon.INSTANCE.getClass().getResourceAsStream("$folderPath/$path");
         if (exception == null) {
            val `$this$load_u24lambda_u243_u24lambda_u242`: JsonManifestWalker = INSTANCE;
            Cobblemon.INSTANCE.getLOGGER().error("manifest contains element $path which was not found.");
         } else {
            val stream: InputStream = exception;

            try {
               list.add(gson.fromJson(new InputStreamReader(stream), clazz));
            } catch (var20: Exception) {
               Cobblemon.INSTANCE.getLOGGER().error("Issue loading manifest component: $path");
               var20.printStackTrace();
            }
         }
      }

      return list;
   }

   @JvmStatic
   fun `build$lambda$0`(f: File): Boolean {
      return FilesKt.getExtension(f) == "json" && !(FilesKt.getNameWithoutExtension(f) == "_MANIFEST");
   }
}
