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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleFaintedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleFaintPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Arrays;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\"\u0010\u0012\u001a\u00020\u00118\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/FaintInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "faintingPokemon", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getFaintingPokemon", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "", "waitTime", "F", "getWaitTime", "()F", "setWaitTime", "(F)V", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class FaintInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;
    private float waitTime;
    @NotNull
    private final BattlePokemon faintingPokemon;

    public FaintInstruction(@NotNull PokemonBattle battle2, @NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.message = message;
        this.waitTime = 2.5f;
        BattlePokemon battlePokemon = this.message.battlePokemon(0, battle2);
        Intrinsics.checkNotNull((Object)battlePokemon);
        this.faintingPokemon = battlePokemon;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    public final float getWaitTime() {
        return this.waitTime;
    }

    public final void setWaitTime(float f) {
        this.waitTime = f;
    }

    @NotNull
    public final BattlePokemon getFaintingPokemon() {
        return this.faintingPokemon;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        battle2.dispatchWaiting(this.waitTime, (Function0<Unit>)((Function0)new Function0<Unit>(this, battle2){
            final /* synthetic */ FaintInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke() {
                void $this$iv;
                Pair<String, String> pair = this.this$0.getMessage().pnxAndUuid(0);
                if (pair == null) {
                    return;
                }
                String pnx = (String)pair.component1();
                this.$battle.sendUpdate(new BattleFaintPacket(pnx));
                this.this$0.getFaintingPokemon().getEffectedPokemon().setCurrentHealth(0);
                this.this$0.getFaintingPokemon().sendUpdate();
                BattleContext context = ShowdownInterpreter.INSTANCE.getContextFromFaint(this.this$0.getFaintingPokemon(), this.$battle);
                Object[] objectArray = CobblemonEvents.BATTLE_FAINTED;
                BattleFaintedEvent[] battleFaintedEventArray = new BattleFaintedEvent[]{new BattleFaintedEvent(this.$battle, this.this$0.getFaintingPokemon(), context)};
                BattleFaintedEvent[] events$iv = battleFaintedEventArray;
                boolean $i$f$post = false;
                $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
                BattleFaintedEvent[] $this$forEach$iv$iv = events$iv;
                boolean $i$f$forEach = false;
                int n = $this$forEach$iv$iv.length;
                for (int i = 0; i < n; ++i) {
                    BattleFaintedEvent element$iv$iv;
                    BattleFaintedEvent battleFaintedEvent = element$iv$iv = $this$forEach$iv$iv[i];
                    boolean bl = false;
                    BattleFaintedEvent it = battleFaintedEvent;
                }
                ((ActiveBattlePokemon)this.$battle.getActorAndActiveSlotFromPNX(pnx).getSecond()).setBattlePokemon(null);
                objectArray = new BattleContext[]{context};
                this.this$0.getFaintingPokemon().getContextManager().add((BattleContext[])objectArray);
                objectArray = new BattleContext.Type[]{BattleContext.Type.STATUS, BattleContext.Type.VOLATILE, BattleContext.Type.BOOST, BattleContext.Type.UNBOOST};
                this.this$0.getFaintingPokemon().getContextManager().clear((BattleContext.Type[])objectArray);
                ((Map)this.$battle.getMajorBattleActions()).put(this.this$0.getFaintingPokemon().getUuid(), this.this$0.getMessage());
            }
        }));
        battle2.dispatchWaiting(0.5f, (Function0<Unit>)((Function0)new Function0<Unit>(this, battle2){
            final /* synthetic */ FaintInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            public final void invoke() {
                Object[] objectArray = new Object[]{this.this$0.getFaintingPokemon().getName()};
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("fainted", objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"fainted\", faintingPokemon.getName())");
                MutableComponent faintMessage = TextKt.red(mutableComponent);
                this.$battle.broadcastChatMessage((Component)faintMessage);
            }
        }));
    }
}

