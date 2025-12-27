package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMusicPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleQueueRequestPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSetTeamPokemonPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import java.util.ArrayList;
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nTurnInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TurnInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/TurnInstruction\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,68:1\n800#2,11:69\n1855#2,2:80\n1855#2:82\n1549#2:83\n1620#2,3:84\n1856#2:87\n*S KotlinDebug\n*F\n+ 1 TurnInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/TurnInstruction\n*L\n40#1:69,11\n40#1:80,2\n45#1:82\n46#1:83\n46#1:84,3\n45#1:87\n*E\n"])
public class TurnInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      val var10000: java.lang.String = this.message.argumentAt(0);
      if (var10000 != null) {
         val turnNumber: Int = Integer.parseInt(var10000);
         if (!battle.getStarted()) {
            battle.setStarted(true);
            val `$this$forEach$iv`: java.lang.Iterable = battle.getActors();
            val `element$iv`: java.util.Collection = new ArrayList();

            for (Object element$iv$iv : $this$filterIsInstance$iv) {
               if (req is PlayerBattleActor) {
                  `element$iv`.add(req);
               }
            }

            for (Object element$ivx : $this$filterIsInstance$iv) {
               val var28: PlayerBattleActor = `element$ivx` as PlayerBattleActor;
               (`element$ivx` as PlayerBattleActor).sendUpdate(new BattleInitializePacket(battle, (`element$ivx` as PlayerBattleActor).getSide()));
               var28.sendUpdate(new BattleMusicPacket(var28.getBattleTheme(), 0.0F, 0.0F, 6, null));
            }

            for (Object element$ivx : $this$filterIsInstance$iv) {
               val var29: BattleActor = `element$ivx` as BattleActor;
               val var33: java.lang.Iterable = (`element$ivx` as BattleActor).getPokemonList();
               val `destination$iv$ivx`: java.util.Collection = new ArrayList(CollectionsKt.collectionSizeOrDefault(var33, 10));

               for (Object item$iv$iv : var33) {
                  `destination$iv$ivx`.add((`item$iv$iv` as BattlePokemon).getEffectedPokemon());
               }

               var29.sendUpdate(new BattleSetTeamPokemonPacket(`destination$iv$ivx`));
               val var35: ShowdownActionRequest = var29.getRequest();
               if (var35 != null) {
                  var29.sendUpdate(new BattleQueueRequestPacket(var35));
               }
            }

            battle.dispatch((new Function0<DispatchResult>(battle) {
               {
                  super(0);
                  this.$battle = `$battle`;
               }

               @NotNull
               public final DispatchResult invoke() {
                  return <unrepresentable>::invoke$lambda$0;
               }

               private static final boolean invoke$lambda$0(PokemonBattle $battle) {
                  return !`$battle`.getSide1().stillSendingOut() && !`$battle`.getSide2().stillSendingOut();
               }
            }) as () -> DispatchResult);
            battle.dispatchGo((new Function0<Unit>(battle) {
               {
                  super(0);
                  this.$battle = `$battle`;
               }

               public final void invoke() {
                  this.$battle.getSide1().playCries();
                  SchedulingFunctionsKt.afterOnServer$default(0, 1.0F, (new Function0<Unit>(this.$battle) {
                     {
                        super(0);
                        this.$battle = `$battle`;
                     }

                     public final void invoke() {
                        this.$battle.getSide2().playCries();
                     }
                  }) as Function0, 1, null);
               }
            }) as () -> Unit);
         }

         battle.dispatch((new Function0<DispatchResult>(battle, turnNumber) {
            {
               super(0);
               this.$battle = `$battle`;
               this.$turnNumber = `$turnNumber`;
            }

            @NotNull
            public final DispatchResult invoke() {
               this.$battle.sendToActors(new BattleMakeChoicePacket());
               val var10000: PokemonBattle = this.$battle;
               val var10001: MutableComponent = LocalizationUtilsKt.battleLang("turn", this.$turnNumber);
               var10000.broadcastChatMessage(TextKt.aqua(var10001) as Component);
               this.$battle.turn(this.$turnNumber);
               return DispatchResultKt.getGO();
            }
         }) as () -> DispatchResult);
      }
   }
}
