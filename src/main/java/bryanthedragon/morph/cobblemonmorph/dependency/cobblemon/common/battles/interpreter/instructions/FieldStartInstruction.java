/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/FieldStartInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
@SourceDebugExtension(value={"SMAP\nFieldStartInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FieldStartInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/FieldStartInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"})
public final class FieldStartInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public FieldStartInstruction(@NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.message = message;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        BattlePokemon source;
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Effect effect = this.message.effectAt(0);
        if (effect == null) {
            return;
        }
        Effect effect2 = effect;
        BattlePokemon battlePokemon = source = BattleMessage.battlePokemonFromOptional$default(this.message, battle2, null, 2, null);
        if (battlePokemon != null) {
            BattlePokemon it = battlePokemon;
            boolean bl = false;
            ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle2, BattleMessage.effect$default(this.message, null, 1, null), source);
        }
        battle2.dispatchWaiting(1.5f, (Function0<Unit>)((Function0)new Function0<Unit>(effect2, source, battle2, this){
            final /* synthetic */ Effect $effect;
            final /* synthetic */ BattlePokemon $source;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ FieldStartInstruction this$0;
            {
                this.$effect = $effect;
                this.$source = $source;
                this.$battle = $battle;
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                String string = "fieldstart." + this.$effect.getId();
                Object[] objectArray = new Object[1];
                BattlePokemon battlePokemon = this.$source;
                if (battlePokemon == null || (battlePokemon = battlePokemon.getName()) == null) {
                    battlePokemon = Component.m_237113_((String)"UNKNOWN");
                }
                Intrinsics.checkNotNullExpressionValue((Object)battlePokemon, (String)"source?.getName() ?: Text.literal(\"UNKNOWN\")");
                objectArray[0] = battlePokemon;
                MutableComponent lang = LocalizationUtilsKt.battleLang(string, objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)lang);
                String string2 = StringsKt.substringAfterLast$default((String)this.$effect.getRawData(), (String)" ", null, (int)2, null).toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toUpperCase(Locale.ROOT)");
                BattleContext.Type type = BattleContext.Type.valueOf(string2);
                BattleContext[] battleContextArray = new BattleContext[]{ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), type, this.$battle)};
                this.$battle.getContextManager().add(battleContextArray);
            }
        }));
    }
}

