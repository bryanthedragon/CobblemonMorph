package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.EntityEffect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import java.util.concurrent.CompletableFuture

public abstract class BattleEffect : EntityEffect {
   public open val battleOnly: Boolean

   public override fun start(entity: PokemonEntity): CompletableFuture<PokemonEntity>? {
      val var10000: CompletableFuture = entity.getEffects().getProgress();
      val progress: java.lang.Boolean = if (var10000 != null) var10000.isDone() else null;
      if (!(progress == true) && progress != null) {
         return null;
      } else {
         val future: CompletableFuture = new CompletableFuture();
         entity.getEffects().setProgress(future);
         this.apply(entity, future);
         return future;
      }
   }

   public override fun end(entity: PokemonEntity): CompletableFuture<PokemonEntity>? {
      val var10000: CompletableFuture = entity.getEffects().getProgress();
      val progress: java.lang.Boolean = if (var10000 != null) var10000.isDone() else null;
      if (!(progress == true) && progress != null) {
         return null;
      } else {
         val future: CompletableFuture = new CompletableFuture();
         entity.getEffects().setProgress(future);
         this.revert(entity, future);
         return future;
      }
   }

   protected abstract fun apply(entity: PokemonEntity, future: CompletableFuture<PokemonEntity>) {
   }

   protected abstract fun revert(entity: PokemonEntity, future: CompletableFuture<PokemonEntity>) {
   }
}
