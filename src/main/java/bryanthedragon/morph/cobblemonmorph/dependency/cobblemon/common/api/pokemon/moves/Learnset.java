package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ClientDataSynchronizer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import com.google.gson.JsonElement
import io.netty.buffer.ByteBuf
import java.util.ArrayList;
import java.util.LinkedHashMap
import java.util.LinkedHashSet
import java.util.Map.Entry
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.FriendlyByteBuf
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nLearnset.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Learnset.kt\ncom/cobblemon/mod/common/api/pokemon/moves/Learnset\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,121:1\n766#2:122\n857#2,2:123\n1045#2:125\n1360#2:126\n1446#2,5:127\n1#3:132\n*S KotlinDebug\n*F\n+ 1 Learnset.kt\ncom/cobblemon/mod/common/api/pokemon/moves/Learnset\n*L\n91#1:122\n91#1:123,2\n92#1:125\n93#1:126\n93#1:127,5\n*E\n"])
public open class Learnset : ClientDataSynchronizer<Learnset> {
   public final val eggMoves: MutableList<MoveTemplate> = (new ArrayList()) as java.util.List
   public final val evolutionMoves: MutableSet<MoveTemplate> = (new LinkedHashSet()) as java.util.Set
   public final val formChangeMoves: MutableList<MoveTemplate> = (new ArrayList()) as java.util.List
   public final val levelUpMoves: MutableMap<Int, MutableList<MoveTemplate>> = (new LinkedHashMap()) as java.util.Map
   public final val tmMoves: MutableList<MoveTemplate> = (new ArrayList()) as java.util.List
   public final val tutorMoves: MutableList<MoveTemplate> = (new ArrayList()) as java.util.List

   public fun getLevelUpMovesUpTo(level: Int): Set<MoveTemplate> {
      var `$this$flatMap$iv`: java.lang.Iterable = this.levelUpMoves.entrySet();
      var `destination$iv$iv`: java.util.Collection = new ArrayList();

      for (Object element$iv$iv : $this$flatMap$iv) {
         if (((`element$iv$iv` as Entry).getKey() as java.lang.Number).intValue() <= level) {
            `destination$iv$iv`.add(`element$iv$iv`);
         }
      }

      `$this$flatMap$iv` = CollectionsKt.sortedWith(`destination$iv$iv` as java.util.List, new Learnset$getLevelUpMovesUpTo$$inlined$sortedBy$1());
      `destination$iv$iv` = new ArrayList();

      for (Object element$iv$ivx : $this$flatMap$iv) {
         CollectionsKt.addAll(`destination$iv$iv`, (`element$iv$ivx` as Entry).getValue() as java.util.List);
      }

      return CollectionsKt.toSet(`destination$iv$iv` as java.util.List);
   }

   public open fun shouldSynchronize(other: Learnset): Boolean {
      return !(other.levelUpMoves == this.levelUpMoves);
   }

   public override fun decode(buffer: FriendlyByteBuf) {
      this.levelUpMoves.clear();
      val var2: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_BYTE);

      for (int var3 = 0; var3 < var2; var3++) {
         val level: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_SHORT);
         val moves: java.util.List = new ArrayList();
         val var8: Int = NetExtensionsKt.readSizedInt(buffer as ByteBuf, IntSize.U_SHORT);

         for (int var9 = 0; var9 < var8; var9++) {
            val var10000: MoveTemplate = Moves.INSTANCE.getByNumericalId(buffer.readInt());
            if (var10000 != null) {
               moves.add(var10000);
            }
         }

         this.levelUpMoves.put(level, moves);
      }
   }

   public override fun encode(buffer: FriendlyByteBuf) {
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.levelUpMoves.size());

      for (Entry var3 : this.levelUpMoves.entrySet()) {
         val level: Int = (var3.getKey() as java.lang.Number).intValue();
         val moves: java.util.List = var3.getValue() as java.util.List;
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_SHORT, level);
         NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_SHORT, moves.size());

         for (MoveTemplate move : moves) {
            buffer.writeInt(move.getNum());
         }
      }
   }

   public companion object {
      public final val eggInterpreter: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset.Interpreter
      public final val formChangeInterpreter: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset.Interpreter
      public final val interpreters: MutableList<bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset.Interpreter>
      public final val levelUpInterpreter: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset.Interpreter
      public final val tmInterpreter: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset.Interpreter
      public final val tutorInterpreter: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset.Interpreter
   }

   public class Interpreter(loadMove: (JsonElement, Learnset) -> Boolean) {
      public final val loadMove: (JsonElement, Learnset) -> Boolean

      init {
         this.loadMove = loadMove;
      }

      public companion object {
         public fun parseFromPrefixIntoList(prefix: String, list: (Learnset) -> MutableList<MoveTemplate>): bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset.Interpreter {
            return new Learnset.Interpreter((new Function2<JsonElement, Learnset, java.lang.Boolean>(prefix, list) {
               {
                  super(2);
                  this.$prefix = `$prefix`;
                  this.$list = `$list`;
               }

               @NotNull
               public final java.lang.Boolean invoke(@NotNull JsonElement element, @NotNull Learnset learnset) {
                  val var10000: JsonElement = if (element.isJsonPrimitive()) element else null;
                  val var10: java.lang.String = if (var10000 != null) var10000.getAsString() else null;
                  if (var10 == null) {
                     return false;
                  } else {
                     if (StringsKt.startsWith$default(var10, this.$prefix, false, 2, null)) {
                        val var4: MoveTemplate = Moves.INSTANCE.getByName(StringsKt.substringAfter$default(var10, ":", null, 2, null));
                        if (var4 != null) {
                           (this.$list.invoke(learnset) as java.util.List).add(var4);
                           return true;
                        }
                     }

                     return false;
                  }
               }
            }) as (JsonElement?, Learnset?) -> java.lang.Boolean);
         }
      }
   }
}
