package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.markers.KMappedMarker
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.Tag
import net.minecraft.network.FriendlyByteBuf
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nMoveSet.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveSet.kt\ncom/cobblemon/mod/common/api/moves/MoveSet\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,192:1\n1#2:193\n12744#3,2:194\n1855#4,2:196\n1855#4,2:198\n1549#4:200\n1620#4,3:201\n1855#4,2:204\n1747#4,3:206\n*S KotlinDebug\n*F\n+ 1 MoveSet.kt\ncom/cobblemon/mod/common/api/moves/MoveSet\n*L\n41#1:194,2\n64#1:196,2\n69#1:198,2\n98#1:200\n98#1:201,3\n107#1:204,2\n121#1:206,3\n*E\n"])
public class MoveSet : java.lang.Iterable<Move>, KMappedMarker {
   private final var emit: Boolean = true
   private final val moves: Array<Move?>
   public final val observable: SimpleObservable<MoveSet> = new SimpleObservable()

   public override operator fun iterator(): Iterator<Move> {
      return ArraysKt.filterNotNull(this.moves).iterator();
   }

   public operator fun get(index: Int): Move? {
      val var2: Int = index;
      val it: Int = var2.intValue();
      return if ((if (0 <= it && it < 4) var2 else null) != null) this.moves[(if (0 <= it && it < 4) var2 else null).intValue()] else null;
   }

   public fun getMoves(): List<Move> {
      return ArraysKt.filterNotNull(this.moves);
   }

   public fun hasSpace(): Boolean {
      val `$this$any$iv`: Array<Any> = this.moves;
      var var3: Int = 0;
      val var4: Int = this.moves.length;

      var var10000: Boolean;
      while (true) {
         if (var3 >= var4) {
            var10000 = false;
            break;
         }

         if (`$this$any$iv`[var3] == null) {
            var10000 = true;
            break;
         }

         var3++;
      }

      return var10000;
   }

   public fun setMove(pos: Int, move: Move?) {
      if (0 <= pos && pos < 4) {
         this.moves[pos] = move;
         if (move != null) {
            val var10000: SimpleObservable = move.getObservable();
            if (var10000 != null) {
               Observable.DefaultImpls.subscribe$default(var10000, null, (new Function1<Move, Unit>(this) {
                  {
                     super(1);
                     this.this$0 = `$receiver`;
                  }

                  public final void invoke(@NotNull Move it) {
                     this.this$0.update();
                  }
               }) as Function1, 1, null);
            }
         }

         this.update();
      }
   }

   public fun copyFrom(other: MoveSet) {
      this.doWithoutEmitting((new Function0<Unit>(this, other) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$other = `$other`;
         }

         public final void invoke() {
            this.this$0.clear();
            val `$this$forEach$iv`: java.lang.Iterable = this.$other.getMoves();
            val var2: MoveSet = this.this$0;

            for (Object element$iv : $this$forEach$iv) {
               var2.add(`element$iv` as Move);
            }
         }
      }) as () -> Unit);
      this.update();
   }

   public fun heal() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as Move).setCurrentPp((`element$iv` as Move).getMaxPp());
      }

      this.update();
   }

   public fun partialHeal() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as Move)
            .setCurrentPp(Math.min((`element$iv` as Move).getCurrentPp() + (`element$iv` as Move).getMaxPp() / 2, (`element$iv` as Move).getMaxPp()));
      }

      this.update();
   }

   public fun clear() {
      this.doWithoutEmitting((new Function0<Unit>(this) {
         {
            super(0);
            this.this$0 = `$receiver`;
         }

         public final void invoke() {
            for (int i = 0; i < 4; i++) {
               this.this$0.setMove(i, null);
            }
         }
      }) as () -> Unit);
      this.update();
   }

   public fun swapMove(pos1: Int, pos2: Int) {
      val var3: Move = this.moves[pos2];
      this.moves[pos2] = this.moves[pos1];
      this.moves[pos1] = var3;
      this.update();
   }

   public fun getNBT(): ListTag {
      val listTag: ListTag = new ListTag();
      val `$this$map$iv`: java.lang.Iterable = this.getMoves();
      val `destination$iv$iv`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(`$this$map$iv`, 10));

      for (Object item$iv$iv : $this$map$iv) {
         `destination$iv$iv`.add((`item$iv$iv` as Move).saveToNBT(new CompoundTag()));
      }

      listTag.addAll(`destination$iv$iv` as java.util.List);
      return listTag;
   }

   public fun saveToBuffer(buffer: FriendlyByteBuf) {
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.getMoves().size());

      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as Move).saveToBuffer(buffer);
      }
   }

   public fun saveToJSON(json: JsonObject): JsonObject {
      val var2: java.util.Iterator = ArraysKt.filterNotNull(this.moves).iterator();
      var var3: Int = 0;

      while (var2.hasNext()) {
         json.add("MoveSet${var3++}", (var2.next() as Move).saveToJSON(new JsonObject()) as JsonElement);
      }

      return json;
   }

   public fun add(move: Move) {
      val i: java.lang.Iterable = this;
      var var10000: Boolean;
      if (this is java.util.Collection && (this as java.util.Collection).isEmpty()) {
         var10000 = false;
      } else {
         val var4: java.util.Iterator = i.iterator();

         while (true) {
            if (!var4.hasNext()) {
               var10000 = false;
               break;
            }

            if ((var4.next() as Move).getTemplate() == move.getTemplate()) {
               var10000 = true;
               break;
            }
         }
      }

      if (!var10000) {
         for (int ix = 0; ix < 4; ix++) {
            if (this.moves[ix] == null) {
               this.moves[ix] = move;
               this.update();
               return;
            }
         }
      }
   }

   public fun update() {
      if (this.emit) {
         this.observable.emit(this);
      }
   }

   public fun doWithoutEmitting(action: () -> Unit) {
      val previousEmit: Boolean = this.emit;
      this.emit = false;
      action.invoke();
      this.emit = previousEmit;
   }

   public fun loadFromNBT(nbt: CompoundTag): MoveSet {
      this.doWithoutEmitting((new Function0<Unit>(this, nbt) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$nbt = `$nbt`;
         }

         public final void invoke() {
            this.this$0.clear();
            val var10000: ListTag = this.$nbt.m_128437_("MoveSet", 10);
            val `$this$forEachIndexed$iv`: java.lang.Iterable = var10000 as java.lang.Iterable;
            val var2: MoveSet = this.this$0;
            var `index$iv`: Int = 0;

            for (Object item$iv : $this$forEachIndexed$iv) {
               val var7: Int = `index$iv`++;
               if (var7 < 0) {
                  CollectionsKt.throwIndexOverflow();
               }

               val tag: Tag = `item$iv` as Tag;
               val var10002: Move.Companion = Move.Companion;
               var2.setMove(var7, var10002.loadFromNBT(tag as CompoundTag));
            }
         }
      }) as () -> Unit);
      this.update();
      return this;
   }

   public fun loadFromBuffer(buffer: FriendlyByteBuf): MoveSet {
      this.doWithoutEmitting((new Function0<Unit>(this, buffer) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$buffer = `$buffer`;
         }

         public final void invoke() {
            this.this$0.clear();
            val amountMoves: Int = NetExtensionsKt.readSizedInt(this.$buffer as ByteBuf, IntSize.U_BYTE);

            for (int i = 0; i < amountMoves; i++) {
               this.this$0.setMove(i, Move.Companion.loadFromBuffer(this.$buffer));
            }
         }
      }) as () -> Unit);
      this.update();
      return this;
   }

   public fun loadFromJSON(json: JsonObject): MoveSet {
      this.doWithoutEmitting((new Function0<Unit>(this, json) {
         {
            super(0);
            this.this$0 = `$receiver`;
            this.$json = `$json`;
         }

         public final void invoke() {
            this.this$0.clear();

            for (int i = 0; i < 4; i++) {
               val var10000: JsonElement = this.$json.get("MoveSet$i");
               if (var10000 != null) {
                  val var4: Move.Companion = Move.Companion;
                  val var10001: JsonObject = var10000.getAsJsonObject();
                  this.this$0.add(var4.loadFromJSON(var10001));
               }
            }
         }
      }) as () -> Unit);
      this.update();
      return this;
   }

   public companion object {
      public const val MOVE_COUNT: Int
   }
}
