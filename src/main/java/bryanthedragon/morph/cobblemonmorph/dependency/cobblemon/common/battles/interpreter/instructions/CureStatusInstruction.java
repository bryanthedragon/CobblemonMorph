package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import kotlin.jvm.functions.Function0
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nCureStatusInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CureStatusInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/CureStatusInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,58:1\n1#2:59\n*E\n"])
public class CureStatusInstruction(message: BattleMessage) : InterpreterInstruction {
   public final val message: BattleMessage

   init {
      this.message = message;
   }

   public override operator fun invoke(battle: PokemonBattle) {
      var var12: BattlePokemon;
      label24: {
         val var10000: Pair = this.message.actorAndActivePokemon(0, battle);
         if (var10000 != null) {
            val var11: ActiveBattlePokemon = var10000.getSecond() as ActiveBattlePokemon;
            if (var11 != null) {
               var12 = var11.getBattlePokemon();
               break label24;
            }
         }

         var12 = null;
      }

      val maybePartyPokemon: BattlePokemon = this.message.battlePokemon(0, battle);
      var12 = var12;
      if (var12 == null) {
         var12 = maybePartyPokemon;
         if (maybePartyPokemon == null) {
            return;
         }
      }

      val var14: java.lang.String = this.message.argumentAt(1);
      if (var14 != null) {
         val var15: Status = Statuses.INSTANCE.getStatus(var14);
         if (var15 != null) {
            val status: Status = var15;
            val effect: Effect = BattleMessage.effect$default(this.message, null, 1, null);
            ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle, effect, var12);
            PokemonBattle.dispatchWaiting$default(
               battle,
               0.0F,
               (
                  new Function0<Unit>(var12, var12, this, effect, status, battle) {
                     {
                        super(0);
                        this.$pokemon = `$pokemon`;
                        this.$maybeActivePokemon = `$maybeActivePokemon`;
                        this.this$0 = `$receiver`;
                        this.$effect = `$effect`;
                        this.$status = `$status`;
                        this.$battle = `$battle`;
                     }

                     public final void invoke() {
                        val pokemonName: MutableComponent = this.$pokemon.getName();
                        this.$pokemon.getEffectedPokemon().setStatus(null);
                        this.$pokemon.sendUpdate();
                        if (this.$maybeActivePokemon != null) {
                           val var10000: Pair = this.this$0.getMessage().pnxAndUuid(0);
                           if (var10000 != null) {
                              this.$battle.sendUpdate(new BattlePersistentStatusPacket(var10000.getFirst() as java.lang.String, null));
                           }
                        }

                        val var8: Effect.Type = if (this.$effect != null) this.$effect.getType() else null;
                        val var10: MutableComponent = if ((if (var8 == null) -1 else WhenMappings.$EnumSwitchMapping$0[var8.ordinal()]) == 1)
                           LocalizationUtilsKt.battleLang("curestatus.${this.$effect.getId()}", pokemonName)
                           else
                           MiscUtilsKt.asTranslated(this.$status.getRemoveMessage(), pokemonName);
                        val var12: PokemonBattle = this.$battle;
                        var12.broadcastChatMessage(var10 as Component);
                        this.$pokemon.getContextManager().remove(this.$status.getShowdownName(), BattleContext.Type.STATUS);
                        this.$battle.getMinorBattleActions().put(this.$pokemon.getUuid(), this.this$0.getMessage());
                     }
                  }
               ) as Function0,
               1,
               null
            );
            return;
         }
      }
   }
}
