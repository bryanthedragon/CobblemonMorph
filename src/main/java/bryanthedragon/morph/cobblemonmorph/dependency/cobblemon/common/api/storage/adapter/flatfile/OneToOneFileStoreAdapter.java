package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.CobblemonAdapterParent
import java.io.File
import java.util.Locale
import java.util.UUID

public abstract class OneToOneFileStoreAdapter<S> : CobblemonAdapterParent<S>, FileStoreAdapter<S> {
   private final val fileExtension: String
   private final val folderPerClass: Boolean
   private final val rootFolder: String
   private final val useNestedFolders: Boolean

   open fun OneToOneFileStoreAdapter(rootFolder: java.lang.String, useNestedFolders: Boolean, folderPerClass: Boolean, fileExtension: java.lang.String) {
      this.rootFolder = rootFolder;
      this.useNestedFolders = useNestedFolders;
      this.folderPerClass = folderPerClass;
      this.fileExtension = fileExtension;
   }

   public abstract fun save(file: File, serialized: Any) {
   }

   public abstract fun <E, T : PokemonStore<Any>> load(file: File, storeClass: Class<out Any>, uuid: UUID): Any? {
   }

   public fun getFile(storeClass: Class<out PokemonStore<*>>, uuid: UUID): File {
      var var10000: java.lang.String = storeClass.getSimpleName();
      var10000 = var10000.toLowerCase(Locale.ROOT);
      val subfolder1: java.lang.String = if (this.folderPerClass) "$var10000/" else "";
      if (this.useNestedFolders) {
         var10000 = uuid.toString();
         var10000 = var10000.substring(0, 2);
         var10000 = "$var10000/";
      } else {
         var10000 = "";
      }

      val var11: File = new File(
         "${if (!StringsKt.endsWith$default(this.rootFolder, "/", false, 2, null)) "${this.rootFolder}/" else this.rootFolder}$subfolder1$var10000",
         if (this.folderPerClass) "$uuid.${this.fileExtension}" else "$uuid-$var10000.${this.fileExtension}"
      );
      var11.getParentFile().mkdirs();
      return var11;
   }

   public override fun save(storeClass: Class<out PokemonStore<*>>, uuid: UUID, serialized: Any) {
      val file: File = this.getFile(storeClass, uuid);
      val tempFile: File = new File("${file.getAbsolutePath()}.temp");
      tempFile.createNewFile();
      this.save(tempFile, (S)serialized);
      FilesKt.copyTo$default(tempFile, file, true, 0, 4, null);
      tempFile.delete();
   }

   public override fun <E : StorePosition, T : PokemonStore<Any>> provide(storeClass: Class<Any>, uuid: UUID): Any? {
      label32: {
         var tempFile: File;
         label49: {
            val file: File = this.getFile(storeClass, uuid);
            tempFile = new File("${file.getAbsolutePath()}.temp");
            if (tempFile.exists()) {
               try {
                  val tempLoaded: PokemonStore = this.load(tempFile, storeClass, uuid);
                  if (tempLoaded != null) {
                     this.save(file, this.serialize(tempLoaded));
                     break label49;
                  }
               } catch (var10: java.lang.Throwable) {
                  tempFile.delete();
               }

               tempFile.delete();
            }

            var var10000: PokemonStore;
            if (file.exists()) {
               var10000 = this.load(file, storeClass, uuid);
               if (var10000 == null) {
                  val it: OneToOneFileStoreAdapter = this;
                  Cobblemon.INSTANCE
                     .getLOGGER()
                     .error("Pokémon save file for ${storeClass.getSimpleName()} ($uuid) was corrupted. A fresh file will be created.");
                  var10000 = storeClass.getConstructor(UUID.class).newInstance(uuid) as PokemonStore;
               }
            } else {
               var10000 = null;
            }

            return (T)var10000;
         }

         tempFile.delete();
      }
   }
}
