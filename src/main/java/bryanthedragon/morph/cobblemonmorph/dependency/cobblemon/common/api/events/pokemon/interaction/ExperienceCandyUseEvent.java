package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.CandyItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.AddExperienceResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import net.minecraft.server.level.ServerPlayer

public interface ExperienceCandyUseEvent {
   public val item: CandyItem
   public val player: ServerPlayer
   public val pokemon: Pokemon

   public class Post(player: ServerPlayer, pokemon: Pokemon, item: CandyItem, experienceResult: AddExperienceResult) : ExperienceCandyUseEvent {
      public final val experienceResult: AddExperienceResult
      public open val item: CandyItem
      public open val player: ServerPlayer
      public open val pokemon: Pokemon

      init {
         this.player = player;
         this.pokemon = pokemon;
         this.item = item;
         this.experienceResult = experienceResult;
      }

      public fun wasExperienceGiven(): Boolean {
         return this.experienceResult.getExperienceAdded() > 0;
      }

      public fun wasCandyConsumed(): Boolean {
         return this.experienceResult.getExperienceAdded() > 0 && !this.getPlayer().m_7500_();
      }
   }

   public class Pre(player: ServerPlayer, pokemon: Pokemon, item: CandyItem, baseExperienceYield: Int, experienceYield: Int)
      : Cancelable,
      ExperienceCandyUseEvent {
      public final val baseExperienceYield: Int
      public final var experienceYield: Int
      public open val item: CandyItem
      public open val player: ServerPlayer
      public open val pokemon: Pokemon

      init {
         this.player = player;
         this.pokemon = pokemon;
         this.item = item;
         this.baseExperienceYield = baseExperienceYield;
         this.experienceYield = experienceYield;
      }
   }
}
