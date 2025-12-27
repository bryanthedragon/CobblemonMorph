package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.CobblemonEffect
import java.util.Locale

public interface Effect {
   public val id: String
   public val rawData: String
   public val type: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect.Type

   public open val typelessData: String
      public open get() {
      }


   public companion object {
      private fun of(id: String, type: bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect.Type, rawData: String): Effect {
         return new CobblemonEffect(id, type, rawData);
      }

      public fun ability(id: String, rawData: String): Effect {
         if (Abilities.INSTANCE.get(id) == null) {
            throw new IllegalArgumentException("Cannot instance ability effect with ID $id");
         } else {
            return this.of(id, Effect.Type.ABILITY, rawData);
         }
      }

      public fun item(id: String, rawData: String): Effect {
         return this.of(id, Effect.Type.ITEM, rawData);
      }

      public fun move(id: String, rawData: String): Effect {
         if (Moves.INSTANCE.getByName(id) == null) {
            throw new IllegalArgumentException("Cannot instance move effect with ID $id");
         } else {
            return this.of(id, Effect.Type.MOVE, rawData);
         }
      }

      public fun pure(id: String, rawData: String): Effect {
         return this.of(id, Effect.Type.PURE, rawData);
      }

      public fun parse(rawData: String): Effect? {
         if (StringsKt.isBlank(rawData)) {
            return null;
         } else {
            var var2: Effect;
            try {
               val var10000: Effect;
               if (StringsKt.startsWith$default(rawData, Effect.Type.ABILITY.getPrefix(), false, 2, null)) {
                  val var10001: java.lang.String = rawData.toLowerCase(Locale.ROOT);
                  var10000 = this.ability(
                     ShowdownIdentifiable.Companion
                        .getREGEX$common()
                        .replace(StringsKt.substringAfter$default(var10001, Effect.Type.ABILITY.getPrefix(), null, 2, null), ""),
                     rawData
                  );
               } else if (StringsKt.startsWith$default(rawData, Effect.Type.ITEM.getPrefix(), false, 2, null)) {
                  val var16: java.lang.String = rawData.toLowerCase(Locale.ROOT);
                  var10000 = this.item(
                     ShowdownIdentifiable.Companion
                        .getREGEX$common()
                        .replace(StringsKt.substringAfter$default(var16, Effect.Type.ITEM.getPrefix(), null, 2, null), ""),
                     rawData
                  );
               } else if (StringsKt.startsWith$default(rawData, Effect.Type.MOVE.getPrefix(), false, 2, null)) {
                  val var17: java.lang.String = rawData.toLowerCase(Locale.ROOT);
                  var10000 = this.move(
                     ShowdownIdentifiable.Companion
                        .getREGEX$common()
                        .replace(StringsKt.substringAfter$default(var17, Effect.Type.MOVE.getPrefix(), null, 2, null), ""),
                     rawData
                  );
               } else {
                  val var18: java.lang.String = rawData.toLowerCase(Locale.ROOT);
                  var10000 = this.pure(ShowdownIdentifiable.Companion.getREGEX$common().replace(var18, ""), rawData);
               }

               var2 = var10000;
            } catch (var5: Exception) {
               var2 = null;
            }

            return var2;
         }
      }
   }

   // $VF: Class flags could not be determined
   internal class DefaultImpls {
      @JvmStatic
      fun getTypelessData(`$this`: Effect): java.lang.String {
         return StringsKt.trim(StringsKt.substringAfter$default(`$this`.getRawData(), `$this`.getType().getPrefix(), null, 2, null)).toString();
      }
   }

   public enum Type(prefix: String) {
      ABILITY("ability:"),
      ITEM("item:"),
      MOVE("move:"),
      BAGITEM("bagitem:"),
      PURE("")
      public final val prefix: String

      init {
         this.prefix = prefix;
      }
   }
}
