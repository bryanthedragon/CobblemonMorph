/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.CauserInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0018\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0096\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/AbilityInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/CauserInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "instructionSet", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "getInstructionSet", "()Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class AbilityInstruction
implements InterpreterInstruction,
CauserInstruction {
    @NotNull
    private final InstructionSet instructionSet;
    @NotNull
    private final BattleMessage message;

    public AbilityInstruction(@NotNull InstructionSet instructionSet, @NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)instructionSet, (String)"instructionSet");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.instructionSet = instructionSet;
        this.message = message;
    }

    @NotNull
    public final InstructionSet getInstructionSet() {
        return this.instructionSet;
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
        BattlePokemon pokemon = battlePokemon;
        Effect effect = this.message.effectAt(1);
        if (effect == null) {
            return;
        }
        Effect effect2 = effect;
        Effect optionalEffect = BattleMessage.effect$default(this.message, null, 1, null);
        BattlePokemon optionalPokemon = BattleMessage.battlePokemonFromOptional$default(this.message, battle2, null, 2, null);
        Effect effect3 = optionalEffect;
        if (effect3 == null) {
            effect3 = effect2;
        }
        ShowdownInterpreter.INSTANCE.broadcastAbility(battle2, effect3, pokemon);
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(pokemon, optionalPokemon, battle2, this, optionalEffect, effect2){
            final /* synthetic */ BattlePokemon $pokemon;
            final /* synthetic */ BattlePokemon $optionalPokemon;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ AbilityInstruction this$0;
            final /* synthetic */ Effect $optionalEffect;
            final /* synthetic */ Effect $effect;
            {
                this.$pokemon = $pokemon;
                this.$optionalPokemon = $optionalPokemon;
                this.$battle = $battle;
                this.this$0 = $receiver;
                this.$optionalEffect = $optionalEffect;
                this.$effect = $effect;
                super(0);
            }

            /*
             * Unable to fully structure code
             */
            @NotNull
            public final DispatchResult invoke() {
                pokemonName = this.$pokemon.getName();
                v0 = this.$optionalPokemon;
                optionalPokemonName = v0 != null ? v0.getName() : null;
                var3_3 = ShowdownInterpreter.INSTANCE.getLastCauser();
                v1 = this.$battle.getBattleId();
                Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"battle.battleId");
                var4_4 = v1;
                var5_5 = this.this$0.getMessage();
                var3_3.put((UUID)var4_4, (BattleMessage)var5_5);
                v2 = this.$optionalEffect;
                v3 = var4_4 = v2 != null ? v2.getId() : null;
                if (var4_4 == null) ** GOTO lbl-1000
                tmp = -1;
                switch (var4_4.hashCode()) {
                    case 110620997: {
                        if (var4_4.equals("trace")) {
                            tmp = 1;
                        }
                        break;
                    }
                    case -808719889: {
                        if (var4_4.equals("receiver")) {
                            tmp = 2;
                        }
                        break;
                    }
                    case -857043995: {
                        if (var4_4.equals("powerofalchemy")) {
                            tmp = 2;
                        }
                        break;
                    }
                }
                block5 : switch (tmp) {
                    case 1: {
                        v4 = optionalPokemonName;
                        if (v4 != null) {
                            var5_5 = v4;
                            var6_6 = this.$effect;
                            it = var5_5;
                            $i$a$-let-AbilityInstruction$invoke$1$lang$1 = false;
                            var9_9 = new Object[]{pokemonName, it, var6_6.getTypelessData()};
                            v5 = LocalizationUtilsKt.battleLang("ability.trace", var9_9);
                            break;
                        }
                        v5 = null;
                        break;
                    }
                    case 2: {
                        v6 = optionalPokemonName;
                        if (v6 != null) {
                            var5_5 = v6;
                            var6_6 = this.$effect;
                            it = var5_5;
                            $i$a$-let-AbilityInstruction$invoke$1$lang$2 = false;
                            var9_9 = new Object[]{it, var6_6.getTypelessData()};
                            v5 = LocalizationUtilsKt.battleLang("ability.receiver", var9_9);
                            break;
                        }
                        v5 = null;
                        break;
                    }
                    default: lbl-1000:
                    // 2 sources

                    {
                        var5_5 = this.$effect.getId();
                        switch (var5_5.hashCode()) {
                            case -425372569: {
                                if (var5_5.equals("cloudnine")) break;
                                ** break;
                            }
                            case -891888173: {
                                if (!var5_5.equals("sturdy")) {
                                    ** break;
                                }
                                ** GOTO lbl77
                            }
                            case 152824269: {
                                if (!var5_5.equals("anticipation")) {
                                    ** break;
                                }
                                ** GOTO lbl77
                            }
                            case -991786635: {
                                if (var5_5.equals("airlock")) break;
                                ** break;
                            }
                            case -282335599: {
                                if (!var5_5.equals("unnerve")) ** break;
lbl77:
                                // 3 sources

                                var6_6 = new Object[]{pokemonName};
                                v5 = LocalizationUtilsKt.battleLang("ability." + this.$effect.getId(), var6_6);
                                break block5;
                            }
                        }
                        v5 = LocalizationUtilsKt.battleLang("ability.airlock", new Object[0]);
                        break;
lbl82:
                        // 6 sources

                        v5 = null;
                    }
                }
                lang = v5;
                ((Map)this.$battle.getMinorBattleActions()).put(this.$pokemon.getUuid(), this.this$0.getMessage());
                if (lang != null) {
                    this.$battle.broadcastChatMessage((Component)lang);
                    return new WaitDispatch(1.0f);
                }
                return DispatchResultKt.getGO();
            }
        }));
    }
}

