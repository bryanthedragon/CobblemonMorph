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
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/ClearNegativeBoostInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class ClearNegativeBoostInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public ClearNegativeBoostInstruction(@NotNull BattleMessage message) {
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
        BattlePokemon battlePokemon = this.message.battlePokemon(0, battle2);
        if (battlePokemon == null) {
            return;
        }
        BattlePokemon battlePokemon2 = battlePokemon;
        battle2.dispatchWaiting(1.5f, (Function0<Unit>)((Function0)new Function0<Unit>(battlePokemon2, this, battle2){
            final /* synthetic */ BattlePokemon $battlePokemon;
            final /* synthetic */ ClearNegativeBoostInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.$battlePokemon = $battlePokemon;
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            public final void invoke() {
                MutableComponent lang;
                Object[] objectArray;
                MutableComponent pokemonName = this.$battlePokemon.getName();
                if (this.this$0.getMessage().hasOptionalArgument("zeffect")) {
                    objectArray = new Object[]{pokemonName};
                    v0 = LocalizationUtilsKt.battleLang("clearallnegativeboost.zeffect", objectArray);
                } else {
                    objectArray = new Object[]{pokemonName};
                    v0 = lang = LocalizationUtilsKt.battleLang("clearallnegativeboost", objectArray);
                }
                if (!this.this$0.getMessage().hasOptionalArgument("silent")) {
                    Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                    this.$battle.broadcastChatMessage((Component)lang);
                }
                objectArray = new BattleContext.Type[]{BattleContext.Type.UNBOOST};
                this.$battlePokemon.getContextManager().clear((BattleContext.Type[])objectArray);
                ((Map)this.$battle.getMinorBattleActions()).put(this.$battlePokemon.getUuid(), this.this$0.getMessage());
            }
        }));
    }
}

