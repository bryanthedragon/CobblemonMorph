/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/SideStartInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class SideStartInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public SideStartInstruction(@NotNull BattleMessage message) {
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
            final /* synthetic */ SideStartInstruction this$0;
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
                block15: {
                    block14: {
                        v0 = this.this$0.getMessage().argumentAt(0);
                        side = (v0 != null ? v0.charAt(1) == '1' : false) != false ? this.$battle.getSide1() : this.$battle.getSide2();
                        v1 = this.this$0.getMessage().effectAt(1);
                        if (v1 == null) {
                            return;
                        }
                        effect = v1;
                        $this$forEach$iv = this.$battle.getSides();
                        $i$f$forEach = false;
                        var5_6 = $this$forEach$iv.iterator();
                        while (var5_6.hasNext()) {
                            it = element$iv = var5_6.next();
                            $i$a$-forEach-SideStartInstruction$invoke$1$1 = false;
                            subject = Intrinsics.areEqual((Object)it, (Object)side) != false ? LocalizationUtilsKt.battleLang("side_subject.ally", new Object[0]) : LocalizationUtilsKt.battleLang("side_subject.opponent", new Object[0]);
                            v2 = "sidestart." + effect.getId();
                            var10_11 = new Object[1];
                            Intrinsics.checkNotNullExpressionValue((Object)subject, (String)"subject");
                            var10_11[0] = subject;
                            lang = LocalizationUtilsKt.battleLang(v2, var10_11);
                            Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                            it.broadcastChatMessage((Component)lang);
                        }
                        v3 = StringsKt.substringAfterLast$default((String)effect.getRawData(), (String)" ", null, (int)2, null).toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue((Object)v3, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                        var4_5 = v3;
                        switch (var4_5.hashCode()) {
                            case 3506021: {
                                if (var4_5.equals("rock")) break;
                                ** break;
                            }
                            case 1085265597: {
                                if (!var4_5.equals("reflect")) {
                                    ** break;
                                }
                                ** GOTO lbl51
                            }
                            case -694469544: {
                                if (!var4_5.equals("tailwind")) {
                                    ** break;
                                }
                                break block14;
                            }
                            case 3615762: {
                                if (!var4_5.equals("veil")) {
                                    ** break;
                                }
                                ** GOTO lbl51
                            }
                            case -895946451: {
                                if (var4_5.equals("spikes")) break;
                                ** break;
                            }
                            case 117588: {
                                if (var4_5.equals("web")) break;
                                ** break;
                            }
                            case -907689876: {
                                if (!var4_5.equals("screen")) ** break;
lbl51:
                                // 3 sources

                                v4 = BattleContext.Type.SCREEN;
                                break block15;
                            }
                        }
                        v4 = BattleContext.Type.HAZARD;
                        break block15;
                    }
                    v4 = BattleContext.Type.TAILWIND;
                    break block15;
lbl58:
                    // 8 sources

                    v4 = BattleContext.Type.MISC;
                }
                bucket = v4;
                var4_5 = new BattleContext[]{ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), bucket, this.$battle)};
                side.getContextManager().add(var4_5);
            }
        }));
    }
}

