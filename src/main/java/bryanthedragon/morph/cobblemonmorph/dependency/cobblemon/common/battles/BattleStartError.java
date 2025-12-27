package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.UUID
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity

public interface BattleStartError {
   public abstract fun getMessageFor(entity: Entity): MutableComponent {
   }

   public companion object {
      public fun alreadyInBattle(player: ServerPlayer): AlreadyInBattleError {
         val var10002: UUID = player.m_20148_();
         val var10003: Component = player.m_5446_();
         return new AlreadyInBattleError(var10002, var10003);
      }

      public fun alreadyInBattle(pokemonEntity: PokemonEntity): AlreadyInBattleError {
         val var10002: UUID = pokemonEntity.m_20148_();
         val var10003: Component = pokemonEntity.m_5446_();
         return new AlreadyInBattleError(var10002, var10003);
      }

      public fun alreadyInBattle(actor: BattleActor): AlreadyInBattleError {
         return new AlreadyInBattleError(actor.getUuid(), actor.getName() as Component);
      }

      public fun targetIsBusy(targetName: MutableComponent): BusyError {
         return new BusyError(targetName);
      }

      public fun insufficientPokemon(player: ServerPlayer, requiredCount: Int, hadCount: Int): InsufficientPokemonError {
         return new InsufficientPokemonError(player, requiredCount, hadCount);
      }

      public fun canceledByEvent(reason: MutableComponent?): CanceledError {
         return new CanceledError(reason);
      }
   }
}
