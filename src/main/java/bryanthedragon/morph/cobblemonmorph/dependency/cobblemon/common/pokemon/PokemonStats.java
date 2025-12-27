package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.netty.buffer.ByteBuf
import java.util.LinkedHashMap
import java.util.Map.Entry
import kotlin.collections.MutableMap.MutableEntry
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation

@SourceDebugExtension(["SMAP\nPokemonStats.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonStats.kt\ncom/cobblemon/mod/common/pokemon/PokemonStats\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,127:1\n215#2,2:128\n215#2,2:132\n1855#3,2:130\n1855#3,2:134\n*S KotlinDebug\n*F\n+ 1 PokemonStats.kt\ncom/cobblemon/mod/common/pokemon/PokemonStats\n*L\n66#1:128,2\n85#1:132,2\n77#1:130,2\n97#1:134,2\n*E\n"])
public abstract class PokemonStats : java.lang.Iterable<Entry<? extends Stat, ? extends Integer>>, KMappedMarker {
   public abstract val acceptableRange: IntRange
   public abstract val defaultValue: Int
   private final var emit: Boolean = true
   public final val observable: SimpleObservable<PokemonStats> = new SimpleObservable()
   private final val stats: MutableMap<Stat, Int> = (new LinkedHashMap()) as java.util.Map

   public override operator fun iterator(): MutableIterator<MutableEntry<Stat, Int>> {
      return this.stats.entrySet().iterator();
   }

   public fun doWithoutEmitting(action: () -> Unit) {
      this.emit = false;
      action.invoke();
      this.emit = true;
   }

   public fun doThenEmit(action: () -> Unit) {
      this.doWithoutEmitting(action);
      this.update();
   }

   public fun update() {
      if (this.emit) {
         this.observable.emit(this);
      }
   }

   public operator fun get(key: Stat): Int? {
      return this.stats.get(key);
   }

   public open operator fun set(key: Stat, value: Int) {
      if (this.canSet(key, value)) {
         this.stats.put(key, value);
         this.update();
      }
   }

   protected open fun canSet(stat: Stat, value: Int): Boolean {
      val var3: IntRange = this.getAcceptableRange();
      return value <= var3.getLast() && var3.getFirst() <= value;
   }

   public fun saveToNBT(nbt: CompoundTag): CompoundTag {
      for (Entry element$iv : this.stats.entrySet()) {
         val stat: Stat = `element$iv`.getKey() as Stat;
         val value: Int = (`element$iv`.getValue() as java.lang.Number).intValue();
         if (value != this.getDefaultValue()) {
            nbt.m_128376_(this.cleanStatIdentifier(stat.getIdentifier()), (short)value);
         }
      }

      return nbt;
   }

   public fun loadFromNBT(nbt: CompoundTag): PokemonStats {
      this.stats.clear();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         this.set(
            `element$iv` as Stat,
            RangesKt.coerceIn(nbt.m_128448_(this.cleanStatIdentifier((`element$iv` as Stat).getIdentifier())), this.getAcceptableRange() as ClosedRange)
         );
      }

      return this;
   }

   public fun saveToJSON(json: JsonObject): JsonObject {
      for (Entry element$iv : this.stats.entrySet()) {
         val stat: Stat = `element$iv`.getKey() as Stat;
         val value: Int = (`element$iv`.getValue() as java.lang.Number).intValue();
         if (value != this.getDefaultValue()) {
            json.addProperty(this.cleanStatIdentifier(stat.getIdentifier()), value);
         }
      }

      return json;
   }

   public fun loadFromJSON(json: JsonObject): PokemonStats {
      this.stats.clear();

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val var10002: JsonElement = json.get(this.cleanStatIdentifier((`element$iv` as Stat).getIdentifier()));
         this.set(
            `element$iv` as Stat,
            if (var10002 != null) RangesKt.coerceIn(var10002.getAsInt(), this.getAcceptableRange() as ClosedRange) else this.getDefaultValue()
         );
      }

      return this;
   }

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.stats.size());

      for (Entry var3 : this.stats.entrySet()) {
         val stat: Stat = var3.getKey() as Stat;
         val value: Int = (var3.getValue() as java.lang.Number).intValue();
         Cobblemon.INSTANCE.getStatProvider().encode(buffer, stat);
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_SHORT, value);
      }
   }

   public fun loadFromBuffer(buffer: FriendlyByteBuf) {
      this.stats.clear();
      val var2: Short = buffer.readUnsignedByte();

      for (int var3 = 0; var3 < var2; var3++) {
         this.stats.put(Cobblemon.INSTANCE.getStatProvider().decode(buffer), buffer.readUnsignedShort());
      }
   }

   public fun getOrDefault(stat: Stat): Int {
      val var10000: Int = this.get(stat);
      return var10000 ?: this.getDefaultValue();
   }

   private fun cleanStatIdentifier(identifier: ResourceLocation): String {
      val var10000: java.lang.String = identifier.toString();
      return StringsKt.substringAfter$default(var10000, "cobblemon:", null, 2, null);
   }
}
