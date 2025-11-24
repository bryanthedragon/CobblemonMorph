/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.CharsKt
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/StartInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class StartInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public StartInstruction(@NotNull BattleMessage message) {
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
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this, battle2){
            final /* synthetic */ StartInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            /*
             * Unable to fully structure code
             */
            @NotNull
            public final DispatchResult invoke() {
                block20: {
                    block22: {
                        block23: {
                            block24: {
                                block21: {
                                    v0 = this.this$0.getMessage().battlePokemon(0, this.$battle);
                                    if (v0 == null) {
                                        return DispatchResultKt.getGO();
                                    }
                                    pokemon = v0;
                                    v1 = this.this$0.getMessage().effectAt(1);
                                    if (v1 == null || (v1 = v1.getId()) == null) {
                                        return DispatchResultKt.getGO();
                                    }
                                    effectID = v1;
                                    optionalEffect = BattleMessage.effect$default(this.this$0.getMessage(), null, 1, null);
                                    v2 = optionalPokemon = BattleMessage.battlePokemonFromOptional$default(this.this$0.getMessage(), this.$battle, null, 2, null);
                                    optionalPokemonName = v2 != null ? v2.getName() : null;
                                    v3 = this.this$0.getMessage().effectAt(2);
                                    if (v3 == null || (v3 = v3.getTypelessData()) == null) {
                                        v3 = extraEffect = Component.m_237113_((String)"UNKOWN");
                                    }
                                    if (!StringsKt.contains$default((CharSequence)((CharSequence)effectID), (CharSequence)"perish", (boolean)false, (int)2, null)) {
                                        var7_7 = new BattleContext[]{ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.VOLATILE, this.$battle)};
                                        pokemon.getContextManager().add(var7_7);
                                    }
                                    ((Map)this.$battle.getMinorBattleActions()).put(pokemon.getUuid(), this.this$0.getMessage());
                                    if (this.this$0.getMessage().hasOptionalArgument("silent")) break block20;
                                    v4 = optionalEffect;
                                    if (!Intrinsics.areEqual((Object)(v4 != null ? v4.getId() : null), (Object)"reflecttype") || optionalPokemonName == null) break block21;
                                    var8_8 = new Object[]{pokemon.getName(), optionalPokemonName};
                                    v5 = LocalizationUtilsKt.battleLang("start.reflecttype", var8_8);
                                    break block22;
                                }
                                var8_8 = effectID;
                                switch (var8_8.hashCode()) {
                                    case -678735345: {
                                        if (var8_8.equals("perish0")) break;
                                        ** break;
                                    }
                                    case 681421801: {
                                        if (var8_8.equals("stockpile1")) break;
                                        ** break;
                                    }
                                    case 681421803: {
                                        if (var8_8.equals("stockpile3")) break;
                                        ** break;
                                    }
                                    case 681421802: {
                                        if (var8_8.equals("stockpile2")) break;
                                        ** break;
                                    }
                                    case 95027346: {
                                        if (!var8_8.equals("curse")) {
                                            ** break;
                                        }
                                        break block23;
                                    }
                                    case -678735342: {
                                        if (!var8_8.equals("perish3")) {
                                            ** break;
                                        }
                                        ** GOTO lbl61
                                    }
                                    case 2124767068: {
                                        if (!var8_8.equals("dynamax")) {
                                            ** break;
                                        }
                                        break block24;
                                    }
                                    case -678735343: {
                                        if (var8_8.equals("perish2")) break;
                                        ** break;
                                    }
                                    case -678735344: {
                                        if (var8_8.equals("perish1")) break;
                                        ** break;
                                    }
                                    case -793000954: {
                                        if (!var8_8.equals("confusion")) ** break;
lbl61:
                                        // 2 sources

                                        return DispatchResultKt.getGO();
                                    }
                                }
                                var9_9 = new Object[]{pokemon.getName(), CharsKt.digitToInt((char)StringsKt.last((CharSequence)((CharSequence)effectID)))};
                                v5 = LocalizationUtilsKt.battleLang("start." + StringsKt.dropLast((String)effectID, (int)1), var9_9);
                                break block22;
                            }
                            v6 = this.this$0.getMessage().effectAt(2);
                            if (v6 == null || (v6 = v6.getId()) == null) {
                                v6 = effectID;
                            }
                            var10_10 = new Object[]{pokemon.getName()};
                            v7 = LocalizationUtilsKt.battleLang("start." + (String)v6, var10_10);
                            Intrinsics.checkNotNullExpressionValue((Object)v7, (String)"battleLang(\"start.${mess\u2026tID}\", pokemon.getName())");
                            v5 = TextKt.yellow(v7);
                            break block22;
                        }
                        var9_9 = new Object[2];
                        v8 = BattleMessage.battlePokemonFromOptional$default(this.this$0.getMessage(), this.$battle, null, 2, null);
                        Intrinsics.checkNotNull((Object)v8);
                        var9_9[0] = v8.getName();
                        var9_9[1] = pokemon.getName();
                        v5 = LocalizationUtilsKt.battleLang("start.curse", var9_9);
                        break block22;
lbl82:
                        // 11 sources

                        v9 = "start." + (String)effectID;
                        var9_9 = new Object[2];
                        var9_9[0] = pokemon.getName();
                        Intrinsics.checkNotNullExpressionValue((Object)extraEffect, (String)"extraEffect");
                        var9_9[1] = extraEffect;
                        v5 = LocalizationUtilsKt.battleLang(v9, var9_9);
                    }
                    lang = v5;
                    Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                    this.$battle.broadcastChatMessage((Component)lang);
                }
                return new WaitDispatch(1.0f);
            }
        }));
    }
}

