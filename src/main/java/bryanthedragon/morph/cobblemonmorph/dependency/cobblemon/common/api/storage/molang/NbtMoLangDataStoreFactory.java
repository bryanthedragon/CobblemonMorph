package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.molang

import com.bedrockk.molang.runtime.struct.VariableStruct
import com.bedrockk.molang.runtime.value.MoValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import java.io.File
import java.nio.file.Path
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtIo
import net.minecraft.nbt.Tag

@SourceDebugExtension(["SMAP\nNbtMoLangDataStoreFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NbtMoLangDataStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/molang/NbtMoLangDataStoreFactory\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,89:1\n1855#2,2:90\n*S KotlinDebug\n*F\n+ 1 NbtMoLangDataStoreFactory.kt\ncom/cobblemon/mod/common/api/storage/molang/NbtMoLangDataStoreFactory\n*L\n52#1:90,2\n*E\n"])
public object NbtMoLangDataStoreFactory : MoLangDataStoreFactory {
   public final val cache: MutableMap<UUID, VariableStruct> = (new LinkedHashMap()) as java.util.Map
   public final val dirty: MutableList<UUID> = (new ArrayList()) as java.util.List
   public final lateinit var savePath: Path
   public final var saveTicks: Int = 100
   public final var ticker: Int

   public fun saveAll() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         this.save(`element$iv` as UUID);
      }
   }

   public override fun markDirty(uuid: UUID) {
      dirty.add(uuid);
   }

   public override fun load(uuid: UUID): VariableStruct {
      val var6: VariableStruct;
      if (cache.containsKey(uuid)) {
         val var10000: Any = cache.get(uuid);
         var6 = var10000 as VariableStruct;
      } else {
         if (!this.file(uuid).exists()) {
            val var5: VariableStruct = new VariableStruct();
            cache.put(uuid, var5);
            return var5;
         }

         val nbt: CompoundTag = NbtIo.m_128937_(this.file(uuid));
         val var7: MoLangFunctions = MoLangFunctions.INSTANCE;
         val var8: MoValue = var7.readMoValueFromNBT(nbt as Tag);
         val data: VariableStruct = var8 as VariableStruct;
         cache.put(uuid, var8 as VariableStruct);
         var6 = data;
      }

      return var6;
   }

   public fun save(uuid: UUID) {
      val file: File = this.file(uuid);
      val var10000: VariableStruct = cache.get(uuid);
      if (var10000 != null) {
         val var5: Tag = MoLangFunctions.INSTANCE.writeMoValueToNBT(var10000);
         val nbt: CompoundTag = var5 as CompoundTag;
         file.getParentFile().mkdirs();
         NbtIo.m_128944_(nbt, file);
         dirty.remove(uuid);
      }
   }

   private fun file(uuid: UUID): File {
      val var10000: Path = this.getSavePath();
      var var10001: java.lang.String = uuid.toString();
      var10001 = var10001.substring(0, 2);
      return var10000.resolve("playermolangdata/$var10001/$uuid.dat").toFile();
   }

   @JvmStatic
   fun {
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STARTED, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGOUT, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STOPPING, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_POST, null, <unrepresentable>.INSTANCE, 1, null);
   }
}
