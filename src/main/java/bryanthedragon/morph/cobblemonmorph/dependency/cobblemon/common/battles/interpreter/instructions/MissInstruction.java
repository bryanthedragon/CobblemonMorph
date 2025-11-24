/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u0010\u001a\u0004\u0018\u00010\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/MissInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "target", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getTarget", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class MissInstruction
implements InterpreterInstruction {
    @NotNull
    private final PokemonBattle battle;
    @NotNull
    private final BattleMessage message;
    @Nullable
    private final BattlePokemon target;

    public MissInstruction(@NotNull PokemonBattle battle2, @NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.battle = battle2;
        this.message = message;
        this.target = this.message.battlePokemon(1, this.battle);
    }

    @NotNull
    public final PokemonBattle getBattle() {
        return this.battle;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @Nullable
    public final BattlePokemon getTarget() {
        return this.target;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        battle2.dispatchGo((Function0<Unit>)((Function0)new Function0<Unit>(this, battle2){
            final /* synthetic */ MissInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            public final void invoke() {
                BattlePokemon battlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return;
                }
                BattlePokemon pokemon = battlePokemon;
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("missed", new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"missed\")");
                this.$battle.broadcastChatMessage((Component)TextKt.red(mutableComponent));
                ((Map)this.$battle.getMinorBattleActions()).put(pokemon.getUuid(), this.this$0.getMessage());
            }
        }));
    }
}

