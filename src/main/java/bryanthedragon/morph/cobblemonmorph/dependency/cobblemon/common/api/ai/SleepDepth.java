package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.ai

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.StringIdentifiedObjectAdapter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.targeting.TargetingConditions
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.AABB
import org.jetbrains.annotations.NotNull

public interface SleepDepth {
   public abstract fun canSleep(pokemonEntity: PokemonEntity): Boolean {
   }

   public abstract fun shouldWake(pokemonEntity: PokemonEntity): Boolean {
   }

   public companion object {
      public final val adapter: StringIdentifiedObjectAdapter<SleepDepth> = new StringIdentifiedObjectAdapter(<unrepresentable>.INSTANCE)

      public final val comatose: SleepDepth = (new SleepDepth() {
         @Override
         public boolean canSleep(@NotNull PokemonEntity pokemonEntity) {
            return true;
         }

         @Override
         public boolean shouldWake(@NotNull PokemonEntity pokemonEntity) {
            return true;
         }
      }) as SleepDepth

      public final val depths: MutableMap<String, SleepDepth> =
         MapsKt.mutableMapOf(new Pair[]{TuplesKt.to("comatose", comatose), TuplesKt.to("normal", normal)})

      public final val normal: SleepDepth =
         (
            new SleepDepth() {
               @Override
               public boolean canSleep(@NotNull PokemonEntity pokemonEntity) {
                  return pokemonEntity.m_9236_()
                     .m_45955_(TargetingConditions.m_148353_(), pokemonEntity as LivingEntity, AABB.m_165882_(pokemonEntity.m_20182_(), 16.0, 16.0, 16.0))
                     .isEmpty();
               }

               @Override
               public boolean shouldWake(@NotNull PokemonEntity pokemonEntity) {
                  val nearbyPlayers: java.util.List = pokemonEntity.m_9236_()
                     .m_45955_(TargetingConditions.m_148353_(), pokemonEntity as LivingEntity, AABB.m_165882_(pokemonEntity.m_20182_(), 16.0, 16.0, 16.0));
                  val `$this$any$iv`: java.lang.Iterable = nearbyPlayers;
                  val var10000: Boolean;
                  if (nearbyPlayers is java.util.Collection && (nearbyPlayers as java.util.Collection).isEmpty()) {
                     var10000 = false;
                  } else {
                     for (Object element$iv : $this$any$iv) {
                        if (!(`element$iv` as Player).m_6144_()) {
                           return true;
                        }
                     }

                     var10000 = false;
                  }

                  return var10000;
               }
            }
         ) as SleepDepth
      }
}
