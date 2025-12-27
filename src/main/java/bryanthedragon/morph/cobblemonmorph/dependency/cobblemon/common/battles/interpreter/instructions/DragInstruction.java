package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.BattleDispatch
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import kotlin.jvm.functions.Function0
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.world.entity.LivingEntity
import org.jetbrains.annotations.NotNull

public class DragInstruction(instructionSet: InstructionSet, battleActor: BattleActor, publicMessage: BattleMessage, privateMessage: BattleMessage) :
   InterpreterInstruction {
   public final val battleActor: BattleActor
   public final val instructionSet: InstructionSet
   public final val privateMessage: BattleMessage
   public final val publicMessage: BattleMessage

   init {
      this.instructionSet = instructionSet;
      this.battleActor = battleActor;
      this.publicMessage = publicMessage;
      this.privateMessage = privateMessage;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      battle.dispatchInsert(
         (
            new Function0<java.lang.Iterable<? extends BattleDispatch>>(this, battle) {
               {
                  super(0);
                  this.this$0 = `$receiver`;
                  this.$battle = `$battle`;
               }

               @NotNull
               public final java.lang.Iterable<BattleDispatch> invoke() {
                  val var10000: Pair = this.this$0.getPublicMessage().pnxAndUuid(0);
                  val pnx: java.lang.String = var10000.component1() as java.lang.String;
                  val activePokemon: ActiveBattlePokemon = this.$battle.getActorAndActiveSlotFromPNX(pnx).component2() as ActiveBattlePokemon;
                  val pokemon: InstructionSet = this.this$0.getInstructionSet();
                  val entity: InterpreterInstruction = this.this$0;
                  val `index$iv`: Int = pokemon.getInstructions().indexOf(entity);
                  var var29: Any;
                  if (CollectionsKt.last(pokemon.getInstructions()) == entity) {
                     var29 = null;
                  } else {
                     val oldPokemon: java.lang.Iterable = pokemon.getInstructions().subList(`index$iv` + 1, pokemon.getInstructions().size());
                     val `element$iv$iv`: java.util.Collection = new ArrayList();

                     for (Object element$iv$iv$iv : $this$filterIsInstance$iv$iv) {
                        if (`element$iv$iv$iv` is TransformInstruction) {
                           `element$iv$iv`.add(`element$iv$iv$iv`);
                        }
                     }

                     val `$this$filterIsInstanceTo$iv$iv$iv`: java.util.Iterator = (`element$iv$iv` as java.util.List).iterator();

                     while (true) {
                        if (!`$this$filterIsInstanceTo$iv$iv$iv`.hasNext()) {
                           var29 = null;
                           break;
                        }

                        val var28: Any = `$this$filterIsInstanceTo$iv$iv$iv`.next();
                        if (true) {
                           var29 = var28;
                           break;
                        }
                     }
                  }

                  val imposter: Boolean = (if (var29 as TransformInstruction != null) (var29 as TransformInstruction).getExpectedTarget() else null) != null;
                  val illusion: BattlePokemon = this.this$0.getPublicMessage().battlePokemonFromOptional(this.$battle, "is");
                  var29 = this.this$0.getPublicMessage().battlePokemon(0, this.$battle);
                  if (var29 == null) {
                     return SetsKt.emptySet();
                  } else {
                     val var31: PokemonBattle = this.$battle;
                     val var10001: MutableComponent = LocalizationUtilsKt.battleLang("dragged_out", ((BattlePokemon)var29).getName());
                     var31.broadcastChatMessage(var10001 as Component);
                     var29 = activePokemon.getBattlePokemon();
                     if (var29 != null) {
                        val var22: PokemonBattle = this.$battle;
                        val var23: DragInstruction = this.this$0;
                        ((BattlePokemon)var29).getContextManager().clear(BattleContext.Type.VOLATILE, BattleContext.Type.BOOST, BattleContext.Type.UNBOOST);
                        var22.getMajorBattleActions().put(((BattlePokemon)var29).getUuid(), var23.getPublicMessage());
                     }

                     this.$battle.getMajorBattleActions().put(((BattlePokemon)var29).getUuid(), this.this$0.getPublicMessage());
                     return SetsKt.setOf(<unrepresentable>::invoke$lambda$1);
                  }
               }

               private static final DispatchResult invoke$lambda$1(
                  LivingEntity $entity,
                  PokemonBattle $battle,
                  DragInstruction this$0,
                  java.lang.String $pnx,
                  ActiveBattlePokemon $activePokemon,
                  BattlePokemon $pokemon,
                  BattlePokemon $illusion,
                  boolean $imposter,
                  PokemonBattle it
               ) {
                  return if (`$entity` != null)
                     SwitchInstruction.Companion
                        .createEntitySwitch(`$battle`, `this$0`.getBattleActor(), `$entity`, `$pnx`, `$activePokemon`, `$pokemon`, `$illusion`, `$imposter`)
                     else
                     SwitchInstruction.Companion.createNonEntitySwitch(`$battle`, `this$0`.getBattleActor(), `$pnx`, `$activePokemon`, `$pokemon`, `$illusion`);
               }
            }
         ) as () -> MutableIterable<BattleDispatch>
      );
   }
}
