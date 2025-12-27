package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition
import java.io.File
import java.util.UUID
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtIo

public open class NBTStoreAdapter(rootFolder: String, useNestedFolders: Boolean, folderPerClass: Boolean) : OneToOneFileStoreAdapter(
      rootFolder, useNestedFolders, folderPerClass, "dat"
   ) {
   public open fun <E : StorePosition, T : PokemonStore<Any>> serialize(store: Any): CompoundTag {
      return store.saveToNBT(new CompoundTag());
   }

   public open fun save(file: File, serialized: CompoundTag) {
      NbtIo.m_128944_(serialized, file);
   }

   public override fun <E, T : PokemonStore<Any>> load(file: File, storeClass: Class<out Any>, uuid: UUID): Any? {
      var nbt: PokemonStore;
      try {
         nbt = storeClass.getConstructor(UUID.class).newInstance(uuid) as PokemonStore;
      } catch (var9: NoSuchMethodException) {
         nbt = storeClass.getConstructor(UUID.class).newInstance(uuid) as PokemonStore;
      }

      val store: PokemonStore = nbt;

      try {
         val var12: CompoundTag = NbtIo.m_128937_(file);
         store.loadFromNBT(var12);
         return (T)store;
      } catch (var8: Exception) {
         var8.printStackTrace();
         return null;
      }
   }
}
