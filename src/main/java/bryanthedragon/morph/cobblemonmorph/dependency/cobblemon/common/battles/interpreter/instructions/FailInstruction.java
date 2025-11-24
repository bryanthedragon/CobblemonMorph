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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/FailInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class FailInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public FailInstruction(@NotNull BattleMessage message) {
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
        battle2.dispatchWaiting(1.5f, (Function0<Unit>)((Function0)new Function0<Unit>(this, battle2){
            final /* synthetic */ FailInstruction this$0;
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
                v0 = this.this$0.getMessage().battlePokemon(0, this.$battle);
                if (v0 == null) {
                    return;
                }
                pokemon = v0;
                pokemonName = pokemon.getName();
                v1 = this.this$0.getMessage().effectAt(1);
                effectID = v1 != null ? v1.getId() : null;
                cause = this.this$0.getMessage().effect("from");
                of = BattleMessage.battlePokemonFromOptional$default(this.this$0.getMessage(), this.$battle, null, 2, null);
                var7_6 = effectID;
                if (var7_6 == null) ** GOTO lbl-1000
                tmp = -1;
                switch (var7_6.hashCode()) {
                    case -641723388: {
                        if (var7_6.equals("shedtail")) {
                            tmp = 1;
                        }
                        break;
                    }
                    case -537625199: {
                        if (var7_6.equals("corrosivegas")) {
                            tmp = 2;
                        }
                        break;
                    }
                    case -1599618703: {
                        if (var7_6.equals("doubleshock")) {
                            tmp = 3;
                        }
                        break;
                    }
                    case -492715048: {
                        if (var7_6.equals("aurawheel")) {
                            tmp = 4;
                        }
                        break;
                    }
                    case 2124767068: {
                        if (var7_6.equals("dynamax")) {
                            tmp = 5;
                        }
                        break;
                    }
                    case -1377752918: {
                        if (var7_6.equals("burnup")) {
                            tmp = 3;
                        }
                        break;
                    }
                    case 702307440: {
                        if (var7_6.equals("hyperspacefury")) {
                            tmp = 4;
                        }
                        break;
                    }
                    case -293122902: {
                        if (var7_6.equals("unboost")) {
                            tmp = 6;
                        }
                        break;
                    }
                }
                switch (tmp) {
                    case 3: lbl-1000:
                    // 2 sources

                    {
                        v2 = LocalizationUtilsKt.battleLang("fail", new Object[0]);
                        break;
                    }
                    case 1: {
                        var8_7 = new Object[]{pokemonName};
                        v2 = LocalizationUtilsKt.battleLang("fail.substitute", var8_7);
                        break;
                    }
                    case 4: {
                        var8_7 = new Object[]{pokemonName};
                        v2 = LocalizationUtilsKt.battleLang("fail.darkvoid", var8_7);
                        break;
                    }
                    case 2: {
                        var8_7 = new Object[]{pokemonName};
                        v2 = LocalizationUtilsKt.battleLang("fail.healblock", var8_7);
                        break;
                    }
                    case 5: {
                        var8_7 = new Object[]{pokemonName};
                        v2 = LocalizationUtilsKt.battleLang("fail.grassknot", var8_7);
                        break;
                    }
                    case 6: {
                        v3 = statKey = this.this$0.getMessage().argumentAt(2);
                        if (v3 != null) {
                            it = v3;
                            $i$a$-let-FailInstruction$invoke$1$lang$stat$1 = false;
                            v4 = Stats.Companion.getStat(it).getDisplayName();
                        } else {
                            v4 = stat = null;
                        }
                        if (stat != null) {
                            var10_11 = new Object[]{pokemonName, stat};
                            v2 = LocalizationUtilsKt.battleLang("fail." + effectID + ".single", var10_11);
                            break;
                        }
                        var10_11 = new Object[]{pokemonName};
                        v2 = LocalizationUtilsKt.battleLang("fail." + effectID, var10_11);
                        break;
                    }
                    default: {
                        var8_7 = new Object[]{pokemonName};
                        v2 = LocalizationUtilsKt.battleLang("fail." + effectID, var8_7);
                    }
                }
                lang = v2;
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)TextKt.red(lang));
                ((Map)this.$battle.getMinorBattleActions()).put(pokemon.getUuid(), this.this$0.getMessage());
            }
        }));
    }
}

