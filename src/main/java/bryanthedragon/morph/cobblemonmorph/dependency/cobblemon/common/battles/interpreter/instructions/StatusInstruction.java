/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattlePersistentStatusPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/StatusInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class StatusInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public StatusInstruction(@NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.message = message;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Pair<String, String> pair = this.message.pnxAndUuid(0);
        if (pair == null) {
            return;
        }
        String pnx = (String)pair.component1();
        BattlePokemon battlePokemon = this.message.battlePokemon(0, battle2);
        if (battlePokemon == null) {
            return;
        }
        BattlePokemon pokemon = battlePokemon;
        Object object = this.message.actorAndActivePokemonFromOptional(battle2, "of");
        BattlePokemon otherPokemon = object != null && (object = (ActiveBattlePokemon)object.getSecond()) != null ? ((ActiveBattlePokemon)object).getBattlePokemon() : null;
        String string = this.message.argumentAt(1);
        if (string == null) {
            return;
        }
        String statusLabel = string;
        Status status = Statuses.INSTANCE.getStatus(statusLabel);
        if (status == null) {
            Cobblemon.INSTANCE.getLOGGER().error("Unrecognized status: " + statusLabel);
            return;
        }
        Status status2 = status;
        Effect effect = BattleMessage.effect$default(this.message, null, 1, null);
        BattlePokemon battlePokemon2 = otherPokemon;
        if (battlePokemon2 == null) {
            battlePokemon2 = pokemon;
        }
        ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle2, effect, battlePokemon2);
        PokemonBattle.dispatchWaiting$default(battle2, 0.0f, (Function0)new Function0<Unit>(status2, pokemon, battle2, pnx, this){
            final /* synthetic */ Status $status;
            final /* synthetic */ BattlePokemon $pokemon;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ String $pnx;
            final /* synthetic */ StatusInstruction this$0;
            {
                this.$status = $status;
                this.$pokemon = $pokemon;
                this.$battle = $battle;
                this.$pnx = $pnx;
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                if (this.$status instanceof PersistentStatus) {
                    this.$pokemon.getEffectedPokemon().applyStatus((PersistentStatus)this.$status);
                    this.$battle.sendUpdate(new BattlePersistentStatusPacket(this.$pnx, (PersistentStatus)this.$status));
                }
                Object[] objectArray = new Object[]{this.$pokemon.getName()};
                MutableComponent mutableComponent = MiscUtils.asTranslated(this.$status.getApplyMessage(), objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"status.applyMessage.asTr\u2026slated(pokemon.getName())");
                this.$battle.broadcastChatMessage((Component)mutableComponent);
                BattleContext[] battleContextArray = new BattleContext[]{ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.STATUS, this.$battle)};
                this.$pokemon.getContextManager().add(battleContextArray);
                ((Map)this.$battle.getMinorBattleActions()).put(this.$pokemon.getUuid(), this.this$0.getMessage());
            }
        }, 1, null);
    }
}

