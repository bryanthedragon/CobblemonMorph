/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/SwapBoostInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class SwapBoostInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public SwapBoostInstruction(@NotNull BattleMessage message) {
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
        battle2.dispatchWaiting(2.0f, (Function0<Unit>)((Function0)new Function0<Unit>(this, battle2){
            final /* synthetic */ SwapBoostInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            /*
             * Unable to fully structure code
             */
            public final void invoke() {
                block10: {
                    v0 = this.this$0.getMessage().battlePokemon(0, this.$battle);
                    if (v0 == null) {
                        return;
                    }
                    pokemon = v0;
                    pokemonName = pokemon.getName();
                    v1 = this.this$0.getMessage().battlePokemon(1, this.$battle);
                    if (v1 == null) {
                        return;
                    }
                    targetPokemon = v1;
                    targetPokemonName = targetPokemon.getName();
                    v2 = BattleMessage.effect$default(this.this$0.getMessage(), null, 1, null);
                    if (v2 == null || (v2 = v2.getId()) == null) {
                        return;
                    }
                    var7_6 = effectID = v2;
                    switch (var7_6.hashCode()) {
                        case -185707080: {
                            if (!var7_6.equals("guardswap")) {
                                break;
                            }
                            ** GOTO lbl26
                        }
                        case 846106648: {
                            if (!var7_6.equals("powerswap")) {
                                break;
                            }
                            ** GOTO lbl26
                        }
                        case 201420505: {
                            if (!var7_6.equals("heartswap")) break;
lbl26:
                            // 3 sources

                            var8_7 = new Object[]{pokemonName};
                            v3 = LocalizationUtilsKt.battleLang("swapboost." + (String)effectID, var8_7);
                            break block10;
                        }
                    }
                    var8_7 = new Object[]{pokemonName, targetPokemonName};
                    v3 = LocalizationUtilsKt.battleLang("swapboost.generic", var8_7);
                }
                lang = v3;
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)lang);
                var7_6 = new BattleContext.Type[]{BattleContext.Type.BOOST};
                pokemon.getContextManager().swap(targetPokemon.getContextManager(), var7_6);
                var7_6 = new BattleContext.Type[]{BattleContext.Type.UNBOOST};
                pokemon.getContextManager().swap(targetPokemon.getContextManager(), var7_6);
                ((Map)this.$battle.getMinorBattleActions()).put(pokemon.getUuid(), this.this$0.getMessage());
            }
        }));
    }
}

