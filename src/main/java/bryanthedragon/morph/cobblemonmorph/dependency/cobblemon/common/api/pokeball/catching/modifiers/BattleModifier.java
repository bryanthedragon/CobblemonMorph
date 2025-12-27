package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier.Behavior
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity

@SourceDebugExtension(["SMAP\nBattleModifier.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleModifier.kt\ncom/cobblemon/mod/common/api/pokeball/catching/modifiers/BattleModifier\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,51:1\n288#2,2:52\n*S KotlinDebug\n*F\n+ 1 BattleModifier.kt\ncom/cobblemon/mod/common/api/pokeball/catching/modifiers/BattleModifier\n*L\n37#1:52,2\n*E\n"])
public open class BattleModifier(calculator: (ServerPlayer, Iterable<ActiveBattlePokemon>, Pokemon) -> Float) : CatchRateModifier {
   private final val calculator: (ServerPlayer, Iterable<ActiveBattlePokemon>, Pokemon) -> Float

   init {
      this.calculator = calculator;
   }

   public override fun isGuaranteed(): Boolean {
      return false;
   }

   public override fun value(thrower: LivingEntity, pokemon: Pokemon): Float {
      var var10000: ServerPlayer = thrower as? ServerPlayer;
      if ((thrower as? ServerPlayer) == null) {
         return 1.0F;
      } else {
         val player: ServerPlayer = var10000;
         val var5: PokemonBattle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(var10000);
         if (var5 != null) {
            val var6: java.lang.Iterable = var5.getActors();
            if (var6 != null) {
               val var10: java.util.Iterator = var6.iterator();

               while (true) {
                  if (!var10.hasNext()) {
                     var10000 = null;
                     break;
                  }

                  val `element$iv`: Any = var10.next();
                  if (`element$iv` as BattleActor is PlayerBattleActor && (`element$iv` as BattleActor).getUuid() == player.m_20148_()) {
                     var10000 = (ServerPlayer)`element$iv`;
                     break;
                  }
               }

               val var7: BattleActor = var10000 as BattleActor;
               if (var10000 as BattleActor != null) {
                  val `$this$firstOrNull$iv`: java.util.List = var7.getActivePokemon();
                  if (`$this$firstOrNull$iv` != null) {
                     return (this.calculator.invoke(player, `$this$firstOrNull$iv`, pokemon) as java.lang.Number).floatValue();
                  }
               }
            }
         }

         return 1.0F;
      }
   }

   public override fun behavior(thrower: LivingEntity, pokemon: Pokemon): Behavior {
      return CatchRateModifier.Behavior.MULTIPLY;
   }

   public override fun isValid(thrower: LivingEntity, pokemon: Pokemon): Boolean {
      return true;
   }

   public override fun modifyCatchRate(currentCatchRate: Float, thrower: LivingEntity, pokemon: Pokemon): Float {
      return (this.behavior(thrower, pokemon).getMutator().invoke(currentCatchRate, this.value(thrower, pokemon)) as java.lang.Number).floatValue();
   }

   public open fun modifyCatchRate(currentCatchRate: Float, player: ServerPlayer, playerPokemon: Iterable<ActiveBattlePokemon>, pokemon: Pokemon): Float {
      return (this.calculator.invoke(player, playerPokemon, pokemon) as java.lang.Number).floatValue();
   }
}
