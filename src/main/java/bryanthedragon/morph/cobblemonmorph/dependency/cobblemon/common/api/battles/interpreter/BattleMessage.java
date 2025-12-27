package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import java.util.ArrayList;
import java.util.HashMap
import java.util.Locale
import java.util.UUID
import kotlin.jvm.internal.SourceDebugExtension

@SourceDebugExtension(["SMAP\nBattleMessage.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleMessage.kt\ncom/cobblemon/mod/common/api/battles/interpreter/BattleMessage\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,262:1\n1#2:263\n1360#3:264\n1446#3,5:265\n*S KotlinDebug\n*F\n+ 1 BattleMessage.kt\ncom/cobblemon/mod/common/api/battles/interpreter/BattleMessage\n*L\n123#1:264\n123#1:265,5\n*E\n"])
public class BattleMessage(rawMessage: String) {
   private final val args: ArrayList<String>

   public final var id: String = ""
      private set

   private final val optionalArgumentMatcher: Regex
   private final val optionalArguments: HashMap<String, String>

   public final var rawMessage: String
      private set

   init {
      this.rawMessage = rawMessage;
      this.args = new ArrayList<>();
      this.optionalArguments = new HashMap<>();
      this.optionalArgumentMatcher = new Regex("^\\[([^]]+)]");
      this.parse(rawMessage);
   }

   public fun argumentAt(index: Int): String? {
      return CollectionsKt.getOrNull(this.args, index) as java.lang.String;
   }

   public fun optionalArgument(name: String): String? {
      val var10000: HashMap = this.optionalArguments;
      val var10001: java.lang.String = name.toLowerCase(Locale.ROOT);
      return var10000.get(var10001) as java.lang.String;
   }

   public fun hasOptionalArgument(name: String): Boolean {
      return this.optionalArgument(name) != null;
   }

   public fun parse(rawMessage: String): BattleMessage {
      var message: java.lang.String = StringsKt.trim(rawMessage).toString();
      this.id = "";
      this.args.clear();
      this.optionalArguments.clear();
      this.rawMessage = message;
      if (StringsKt.startsWith$default(message, "|", false, 2, null) && !(message == "|")) {
         message = this.push(message);
         this.id = StringsKt.substringBefore$default(message, "|", null, 2, null);

         for (java.lang.String var8 = this.push(message); !StringsKt.isBlank(var8); var8 = this.push(var8)) {
            val currentData: java.lang.String = StringsKt.substringBefore$default(var8, "|", null, 2, null);
            val optionalArgumentID: MatchResult = Regex.find$default(this.optionalArgumentMatcher, currentData, 0, 2, null);
            if (optionalArgumentID != null) {
               val var10000: java.lang.String = StringsKt.removeSuffix(StringsKt.removePrefix(optionalArgumentID.getValue(), "["), "]")
                  .toLowerCase(Locale.ROOT);
               this.optionalArguments
                  .put(var10000, StringsKt.trim(StringsKt.substringAfter$default(currentData, optionalArgumentID.getValue(), null, 2, null)).toString());
            } else {
               this.args.add(currentData);
            }
         }

         return this;
      } else {
         return this;
      }
   }

   public fun pokemonByUuid(index: Int, battle: PokemonBattle): BattlePokemon? {
      var var10000: java.lang.String = this.argumentAt(index);
      if (var10000 != null) {
         val var21: UUID = UUID.fromString(var10000);
         if (var21 != null) {
            val var14: UUID = var21;
            val `$this$flatMap$iv`: java.lang.Iterable = battle.getActors();
            var `destination$iv$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$flatMap$iv) {
               CollectionsKt.addAll(`destination$iv$iv`, (`element$iv$iv` as BattleActor).getPokemonList());
            }

            val `$this$flatMapTo$iv$iv`: java.util.Iterator = (`destination$iv$iv` as java.util.List).iterator();

            while (true) {
               if (!`$this$flatMapTo$iv$iv`.hasNext()) {
                  var10000 = null;
                  break;
               }

               `destination$iv$iv` = (java.util.Collection)`$this$flatMapTo$iv$iv`.next();
               if ((`destination$iv$iv` as BattlePokemon).getUuid() == var14) {
                  var10000 = `destination$iv$iv`;
                  break;
               }
            }

            return var10000 as BattlePokemon;
         }
      }

      return null;
   }

   public fun actorAndActivePokemon(index: Int, battle: PokemonBattle): Pair<BattleActor, ActiveBattlePokemon>? {
      val var10000: Pair = this.pnxAndUuid(index);
      return if (var10000 == null) null else this.actorAndActivePokemon(var10000.component1() as java.lang.String, battle);
   }

   public fun battlePokemon(index: Int, battle: PokemonBattle): BattlePokemon? {
      val var10000: Pair = this.pnxAndUuid(index);
      return if (var10000 == null) null else this.battlePokemon(var10000.component1() as java.lang.String, var10000.component2() as java.lang.String, battle);
   }

   public fun battlePokemonFromOptional(battle: PokemonBattle, optionalArg: String = "of"): BattlePokemon? {
      var var10000: java.lang.String = this.optionalArguments.get(optionalArg);
      if (var10000 == null) {
         return null;
      } else {
         var10000 = if (var10000.length() >= 2) var10000 else null;
         if (var10000 != null) {
            val var16: java.util.List = StringsKt.split$default(var10000, new java.lang.String[]{":"}, false, 0, 6, null);
            if (var16 != null) {
               val var17: java.util.List = if (var16.size() == 2) var16 else null;
               if (var17 != null) {
                  val var11: Any = var17.get(0);
                  var10000 = (if (PNX_MATCHER.matches(var11 as java.lang.String) || PN_MATCHER.matches(var11 as java.lang.String)) var11 else null) as java.lang.String;
                  if (var10000 == null) {
                     return null;
                  }

                  return this.battlePokemon(var10000, StringsKt.trim(var17.get(1) as java.lang.String).toString(), battle);
               }
            }
         }

         return null;
      }
   }

   public fun pnxAndUuid(index: Int): Pair<String, String>? {
      var var10000: java.lang.String = this.argumentAt(index);
      if (var10000 != null) {
         var10000 = if (var10000.length() >= 2) var10000 else null;
         if (var10000 != null) {
            val var16: java.util.List = StringsKt.split$default(var10000, new java.lang.String[]{":"}, false, 0, 6, null);
            if (var16 != null) {
               val var17: java.util.List = if (var16.size() == 2) var16 else null;
               if (var17 != null) {
                  val var10: Any = var17.get(0);
                  var10000 = (if (PNX_MATCHER.matches(var10 as java.lang.String) || PN_MATCHER.matches(var10 as java.lang.String)) var10 else null) as java.lang.String;
                  if (var10000 == null) {
                     return null;
                  }

                  return TuplesKt.to(var10000, StringsKt.trim(var17.get(1) as java.lang.String).toString());
               }
            }
         }
      }

      return null;
   }

   public fun effectAt(index: Int): Effect? {
      val var10000: java.lang.String = this.argumentAt(index);
      return if (var10000 == null) null else Effect.Companion.parse(var10000);
   }

   public fun effect(argumentName: String = "from"): Effect? {
      val var10000: java.lang.String = this.optionalArgument(argumentName);
      return if (var10000 == null) null else Effect.Companion.parse(var10000);
   }

   public fun moveAt(index: Int): MoveTemplate? {
      var var10000: java.lang.String = this.argumentAt(index);
      if (var10000 != null) {
         var10000 = var10000.toLowerCase(Locale.ROOT);
         if (var10000 != null) {
            var10000 = new Regex("[^a-z0-9]").replace(var10000, "");
            if (var10000 != null) {
               return Moves.INSTANCE.getByName(var10000);
            }
         }
      }

      return null;
   }

   public fun actorAndActivePokemonFromOptional(battle: PokemonBattle, argumentName: String = "of"): Pair<BattleActor, ActiveBattlePokemon>? {
      var var10000: java.lang.String = this.optionalArgument(argumentName);
      if (var10000 != null) {
         var10000 = if (var10000.length() >= 3) var10000 else null;
         if (var10000 != null) {
            var10000 = var10000.substring(0, 3);
            if (var10000 != null) {
               return this.actorAndActivePokemon(var10000, battle);
            }
         }
      }

      return null;
   }

   private fun push(message: String): String {
      return StringsKt.substringAfter(message, "|", "");
   }

   private fun actorAndActivePokemon(pnx: String, battle: PokemonBattle): Pair<BattleActor, ActiveBattlePokemon>? {
      var var3: Pair;
      try {
         var3 = battle.getActorAndActiveSlotFromPNX(pnx);
      } catch (var5: Exception) {
         var3 = null;
      }

      return var3;
   }

   private fun battlePokemon(pnx: String, pokemonID: String, battle: PokemonBattle): BattlePokemon? {
      var var4: BattlePokemon;
      try {
         var4 = battle.getBattlePokemon(pnx, pokemonID);
      } catch (var6: Exception) {
         var4 = null;
      }

      return var4;
   }

   public companion object {
      private const val OPTIONAL_ARG_END: String
      private const val OPTIONAL_ARG_START: String
      public final val PNX_MATCHER: Regex
      public final val PN_MATCHER: Regex
      private const val SEPARATOR: String
   }
}
