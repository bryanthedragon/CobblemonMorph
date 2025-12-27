package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.modifiers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CatchRateModifier
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer
import kotlin.jvm.functions.Function2
import kotlin.jvm.functions.Function3
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.LivingEntity
import org.jetbrains.annotations.NotNull

public object CatchRateModifiers {
   public final val LEVEL: BattleModifier = new BattleModifier(<unrepresentable>.INSTANCE)
   public final val LIGHT_LEVEL: WorldStateModifier = new WorldStateModifier(<unrepresentable>.INSTANCE)
   public final val LOVE: BattleModifier = new BattleModifier(<unrepresentable>.INSTANCE)
   public final val MOON_PHASES: CatchRateModifier = (new WorldStateModifier(<unrepresentable>.INSTANCE)) as CatchRateModifier
   public final val NEST: CatchRateModifier = (new DynamicMultiplierModifier(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE)) as CatchRateModifier
   public final val PARK: WorldStateModifier = new WorldStateModifier(<unrepresentable>.INSTANCE)
   public final val SAFARI: WorldStateModifier = new WorldStateModifier(<unrepresentable>.INSTANCE)
   public final val SUBMERGED_IN_WATER: WorldStateModifier = new WorldStateModifier(<unrepresentable>.INSTANCE)
   public final val WEIGHT_BASED: CatchRateModifier =
      (new DynamicMultiplierModifier(<unrepresentable>.INSTANCE, <unrepresentable>.INSTANCE)) as CatchRateModifier

   public fun typeBoosting(multiplier: Float, vararg types: ElementalType): CatchRateModifier {
      return new MultiplierModifier(multiplier, (new Function2<LivingEntity, Pokemon, java.lang.Boolean>(types) {
         {
            super(2);
            this.$types = `$types`;
         }

         @NotNull
         public final java.lang.Boolean invoke(@NotNull LivingEntity var1, @NotNull Pokemon pokemon) {
            val `$this$any$iv`: java.lang.Iterable = pokemon.getTypes();
            val var4: Array<ElementalType> = this.$types;
            var var10000: Boolean;
            if (`$this$any$iv` is java.util.Collection && (`$this$any$iv` as java.util.Collection).isEmpty()) {
               var10000 = false;
            } else {
               val var6: java.util.Iterator = `$this$any$iv`.iterator();

               while (true) {
                  if (!var6.hasNext()) {
                     var10000 = false;
                     break;
                  }

                  if (ArraysKt.contains(var4, var6.next() as ElementalType)) {
                     var10000 = true;
                     break;
                  }
               }
            }

            return var10000;
         }
      }) as (LivingEntity?, Pokemon?) -> java.lang.Boolean);
   }

   public fun statusBoosting(multiplier: Float, vararg status: Status): CatchRateModifier {
      return new MultiplierModifier(multiplier, (new Function2<LivingEntity, Pokemon, java.lang.Boolean>(status) {
         {
            super(2);
            this.$status = `$status`;
         }

         @NotNull
         public final java.lang.Boolean invoke(@NotNull LivingEntity var1, @NotNull Pokemon pokemon) {
            val var10000: Array<Status> = this.$status;
            val var10001: PersistentStatusContainer = pokemon.getStatus();
            if (var10001 != null) {
               val var3: PersistentStatus = var10001.getStatus();
               if (var3 != null) {
                  return ArraysKt.contains(var10000, var3);
               }
            }

            return false;
         }
      }) as (LivingEntity?, Pokemon?) -> java.lang.Boolean);
   }

   public fun turnBased(multiplierCalculator: (Int) -> Float): BattleModifier {
      return new BattleModifier(
         (new Function3<ServerPlayer, java.lang.Iterable<? extends ActiveBattlePokemon>, Pokemon, java.lang.Float>(multiplierCalculator) {
            {
               super(3);
               this.$multiplierCalculator = `$multiplierCalculator`;
            }

            @NotNull
            public final java.lang.Float invoke(@NotNull ServerPlayer player, @NotNull java.lang.Iterable<ActiveBattlePokemon> var2, @NotNull Pokemon var3) {
               val var10000: PokemonBattle = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player);
               return if (var10000 == null) 1.0F else this.$multiplierCalculator.invoke(var10000.getTurn()) as java.lang.Float;
            }
         }) as (ServerPlayer?, MutableIterable<ActiveBattlePokemon>?, Pokemon?) -> java.lang.Float
      );
   }
}
