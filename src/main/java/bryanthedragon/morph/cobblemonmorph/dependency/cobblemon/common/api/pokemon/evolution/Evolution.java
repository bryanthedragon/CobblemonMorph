package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionTestedEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMove
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import java.util.Arrays
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.sounds.SoundSource
import net.minecraft.world.item.ItemStack

public interface Evolution : EvolutionLike {
   public var consumeHeldItem: Boolean
   public val learnableMoves: MutableSet<MoveTemplate>
   public var optional: Boolean
   public val requirements: MutableSet<EvolutionRequirement>
   public val result: PokemonProperties

   public open fun test(pokemon: Pokemon): Boolean {
   }

   public open fun evolve(pokemon: Pokemon): Boolean {
   }

   public open fun forceEvolve(pokemon: Pokemon) {
   }

   public open fun evolutionMethod(pokemon: Pokemon) {
   }

   public open fun applyTo(pokemon: Pokemon) {
   }

   // $VF: Class flags could not be determined
   @SourceDebugExtension(["SMAP\nEvolution.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Evolution.kt\ncom/cobblemon/mod/common/api/pokemon/evolution/Evolution$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 4 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,164:1\n1726#2,3:165\n1855#2,2:178\n800#2,11:180\n1855#2,2:191\n14#3,5:168\n19#3:176\n14#3,5:193\n19#3:201\n13579#4:173\n13580#4:175\n13579#4:198\n13580#4:200\n14#5:174\n14#5:199\n1#6:177\n*S KotlinDebug\n*F\n+ 1 Evolution.kt\ncom/cobblemon/mod/common/api/pokemon/evolution/Evolution$DefaultImpls\n*L\n74#1:165,3\n147#1:178,2\n156#1:180,11\n156#1:191,2\n76#1:168,5\n76#1:176\n158#1:193,5\n158#1:201\n76#1:173\n76#1:175\n158#1:198\n158#1:200\n76#1:174\n158#1:199\n*E\n"])
   internal class DefaultImpls {
      @JvmStatic
      fun test(`$this`: Evolution, pokemon: Pokemon): Boolean {
         val event: java.lang.Iterable = `$this`.getRequirements();
         var var10000: Boolean;
         if (event is java.util.Collection && (event as java.util.Collection).isEmpty()) {
            var10000 = true;
         } else {
            val `events$iv`: java.util.Iterator = event.iterator();

            while (true) {
               if (!`events$iv`.hasNext()) {
                  var10000 = true;
                  break;
               }

               if (!(`events$iv`.next() as EvolutionRequirement).check(pokemon)) {
                  var10000 = false;
                  break;
               }
            }
         }

         val var15: EvolutionTestedEvent = new EvolutionTestedEvent(pokemon, `$this`, var10000, var10000);
         val var16: EventObservable = CobblemonEvents.EVOLUTION_TESTED;
         val var17: Array<EvolutionTestedEvent> = new EvolutionTestedEvent[]{var15};
         var16.emit(Arrays.copyOf(var17, var17.length));

         for (Object element$iv$iv : var17) {
            ;
         }

         return var15.getResult();
      }

      @JvmStatic
      fun evolve(`$this`: Evolution, pokemon: Pokemon): Boolean {
         if (`$this`.getConsumeHeldItem()) {
            val var10001: ItemStack = ItemStack.f_41583_;
            Pokemon.swapHeldItem$default(pokemon, var10001, false, 2, null);
         }

         if (`$this`.getOptional()) {
            return pokemon.getEvolutionProxy().server().add(`$this`);
         } else {
            `$this`.forceEvolve(pokemon);
            return true;
         }
      }

      @JvmStatic
      fun forceEvolve(`$this`: Evolution, pokemon: Pokemon) {
         if (pokemon.getState() is ShoulderedState) {
            pokemon.tryRecallWithAnimation();
         }

         if (pokemon.getEntity() != null) {
         }

         `$this`.evolutionMethod(pokemon);
      }

      @JvmStatic
      fun evolutionMethod(`$this`: Evolution, pokemon: Pokemon) {
         `$this`.getResult().apply(pokemon);

         val `$this$iv`: java.lang.Iterable;
         for (Object element$iv : $this$filterIsInstance$iv) {
            val `$this$forEach$iv$iv`: MoveTemplate = `$i$f$post` as MoveTemplate;
            if (pokemon.getMoveSet().hasSpace()) {
               pokemon.getMoveSet().add(`$this$forEach$iv$iv`.create());
            } else {
               pokemon.getBenchedMoves().add(new BenchedMove(`$this$forEach$iv$iv`, 0));
            }

            val var10000: ServerPlayer = pokemon.getOwnerPlayer();
            if (var10000 != null) {
               var10000.m_213846_(
                  LocalizationUtilsKt.lang("experience.learned_move", pokemon.getDisplayName(), `$this$forEach$iv$iv`.getDisplayName()) as Component
               );
            }
         }

         `$this$iv` = pokemon.getLockedEvolutions();
         val var20: java.util.Collection = new ArrayList();

         for (Object element$iv$iv : $this$filterIsInstance$iv) {
            if (var29 is PassiveEvolution) {
               var20.add(var29);
            }
         }

         for (Object element$iv : $this$filterIsInstance$iv) {
            (var21 as PassiveEvolution).attemptEvolution(pokemon);
         }

         val var31: ServerPlayer = pokemon.getOwnerPlayer();
         if (var31 != null) {
            var31.m_6330_(CobblemonSounds.EVOLVING, SoundSource.NEUTRAL, 1.0F, 1.0F);
         }

         val var15: EventObservable = CobblemonEvents.EVOLUTION_COMPLETE;
         val var18: Array<EvolutionCompleteEvent> = new EvolutionCompleteEvent[]{new EvolutionCompleteEvent(pokemon, `$this`)};
         var15.emit(Arrays.copyOf(var18, var18.length));

         for (Object element$iv$ivx : var18) {
            ;
         }
      }

      @JvmStatic
      fun applyTo(`$this`: Evolution, pokemon: Pokemon) {
         `$this`.getResult().apply(pokemon);
      }
   }
}
