package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.util.ArrayList;
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf

@SourceDebugExtension(["SMAP\nBenchedMove.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BenchedMove.kt\ncom/cobblemon/mod/common/api/moves/BenchedMoves\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,138:1\n1549#2:139\n1620#2,3:140\n1549#2:143\n1620#2,3:144\n1855#2,2:147\n1855#2,2:149\n*S KotlinDebug\n*F\n+ 1 BenchedMove.kt\ncom/cobblemon/mod/common/api/moves/BenchedMoves\n*L\n52#1:139\n52#1:140,3\n57#1:143\n57#1:144,3\n58#1:147,2\n64#1:149,2\n*E\n"])
public class BenchedMoves : java.lang.Iterable<BenchedMove>, KMappedMarker {
   private final val benchedMoves: MutableList<BenchedMove> = (new ArrayList()) as java.util.List
   private final var emit: Boolean = true
   public final val observable: SimpleObservable<BenchedMoves> = new SimpleObservable()

   public fun doWithoutEmitting(action: () -> Unit) {
      val previousEmit: Boolean = this.emit;
      this.emit = false;
      action.invoke();
      this.emit = previousEmit;
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

   public fun add(benchedMove: BenchedMove) {
      this.doThenEmit((new Function0<Unit>(this, benchedMove) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$benchedMove = `$benchedMove`;
         }

         public final void invoke() {
            BenchedMoves.access$getBenchedMoves$p(this.this$0).add(this.$benchedMove);
         }
      }) as () -> Unit);
   }

   public fun addAll(benchedMoves: Iterable<BenchedMove>) {
      this.doThenEmit((new Function0<Unit>(this, benchedMoves) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$benchedMoves = `$benchedMoves`;
         }

         public final void invoke() {
            CollectionsKt.addAll(BenchedMoves.access$getBenchedMoves$p(this.this$0), this.$benchedMoves);
         }
      }) as () -> Unit);
   }

   public fun clear() {
      this.doThenEmit((new Function0<Unit>(this) {
         {
            super(0);
            this.this$0 = `$receiver`;
         }

         public final void invoke() {
            BenchedMoves.access$getBenchedMoves$p(this.this$0).clear();
         }
      }) as () -> Unit);
   }

   public fun remove(benchedMove: BenchedMove) {
      this.doThenEmit((new Function0<Unit>(this, benchedMove) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$benchedMove = `$benchedMove`;
         }

         public final void invoke() {
            BenchedMoves.access$getBenchedMoves$p(this.this$0).remove(this.$benchedMove);
         }
      }) as () -> Unit);
   }

   public fun remove(moveTemplate: MoveTemplate) {
      this.doThenEmit((new Function0<Unit>(this, moveTemplate) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$moveTemplate = `$moveTemplate`;
         }

         public final void invoke() {
            BenchedMoves.access$getBenchedMoves$p(this.this$0).removeIf(<unrepresentable>::invoke$lambda$0);
         }

         private static final boolean invoke$lambda$0(Function1 $tmp0, Object p0) {
            return `$tmp0`.invoke(p0) as java.lang.Boolean;
         }
      }) as () -> Unit);
   }

   public override operator fun iterator(): MutableIterator<BenchedMove> {
      return this.benchedMoves.iterator();
   }

   public fun saveToNBT(nbt: ListTag): ListTag {
      val `$this$map$iv`: java.lang.Iterable = this.benchedMoves;
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.benchedMoves, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add((`item$iv$iv` as BenchedMove).saveToNBT(new CompoundTag()));
      }

      nbt.addAll(`destination$iv$iv` as java.util.List);
      return nbt;
   }

   public fun saveToJSON(json: JsonArray): JsonArray {
      val `$this$forEach$iv`: java.lang.Iterable = this.benchedMoves;
      val `element$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(this.benchedMoves, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `element$iv`.add((`item$iv$iv` as BenchedMove).saveToJSON(new JsonObject()));
      }

      for (Object element$ivx : $this$map$iv) {
         json.add((`element$ivx` as JsonObject) as JsonElement);
      }

      return json;
   }

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      buffer.writeShort(this.benchedMoves.size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as BenchedMove).saveToBuffer(buffer);
      }
   }

   public fun loadFromNBT(nbt: ListTag): BenchedMoves {
      this.doThenEmit((new Function0<Unit>(this, nbt) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$nbt = `$nbt`;
         }

         public final void invoke() {
            this.this$0.clear();
            val `$this$forEach$iv`: java.lang.Iterable = this.$nbt as java.lang.Iterable;
            val var2: BenchedMoves = this.this$0;

            for (Object element$iv : $this$forEach$iv) {
               val it: Tag = `element$iv` as Tag;
               val var10000: java.util.List = BenchedMoves.access$getBenchedMoves$p(var2);
               val var10001: BenchedMove.Companion = BenchedMove.Companion;
               var10000.add(var10001.loadFromNBT(it as CompoundTag));
            }
         }
      }) as () -> Unit);
      return this;
   }

   public fun loadFromJSON(json: JsonArray): BenchedMoves {
      this.doThenEmit((new Function0<Unit>(this, json) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$json = `$json`;
         }

         public final void invoke() {
            this.this$0.clear();
            val `$this$forEach$iv`: java.lang.Iterable = this.$json as java.lang.Iterable;
            val var2: BenchedMoves = this.this$0;

            for (Object element$iv : $this$forEach$iv) {
               val it: JsonElement = `element$iv` as JsonElement;
               val var10000: java.util.List = BenchedMoves.access$getBenchedMoves$p(var2);
               val var10001: BenchedMove.Companion = BenchedMove.Companion;
               val var10002: JsonObject = it.getAsJsonObject();
               var10000.add(var10001.loadFromJSON(var10002));
            }
         }
      }) as () -> Unit);
      return this;
   }

   public fun loadFromBuffer(buffer: FriendlyByteBuf): BenchedMoves {
      this.doThenEmit((new Function0<Unit>(this, buffer) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$buffer = `$buffer`;
         }

         public final void invoke() {
            this.this$0.clear();
            val var1: Short = this.$buffer.readShort();
            val var2: BenchedMoves = this.this$0;
            val var3: FriendlyByteBuf = this.$buffer;

            for (int var4 = 0; var4 < var1; var4++) {
               BenchedMoves.access$getBenchedMoves$p(var2).add(BenchedMove.Companion.loadFromBuffer(var3));
            }
         }
      }) as () -> Unit);
      return this;
   }
}
