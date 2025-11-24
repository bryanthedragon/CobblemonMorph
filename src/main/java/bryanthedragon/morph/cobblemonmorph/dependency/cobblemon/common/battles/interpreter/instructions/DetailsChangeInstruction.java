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
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/DetailsChangeInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class DetailsChangeInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public DetailsChangeInstruction(@NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.message = message;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Object object;
        BattlePokemon battlePokemon;
        block5: {
            block4: {
                char[] cArray;
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                BattlePokemon battlePokemon2 = this.message.battlePokemon(0, battle2);
                if (battlePokemon2 == null) {
                    return;
                }
                battlePokemon = battlePokemon2;
                object = this.message.argumentAt(1);
                if (object == null || (object = StringsKt.split$default((CharSequence)((CharSequence)object), (char[])(cArray = new char[]{','}), (boolean)false, (int)0, (int)6, null)) == null || (object = (String)object.get(0)) == null || (object = StringsKt.substringAfter$default((String)object, (char)'-', null, (int)2, null)) == null) break block4;
                String string = ((String)object).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                object = string;
                if (string != null) break block5;
            }
            return;
        }
        Object formName = object;
        PokemonBattle.dispatchWaiting$default(battle2, 0.0f, (Function0)new Function0<Unit>(battlePokemon, battle2, (String)formName, this){
            final /* synthetic */ BattlePokemon $battlePokemon;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ String $formName;
            final /* synthetic */ DetailsChangeInstruction this$0;
            {
                this.$battlePokemon = $battlePokemon;
                this.$battle = $battle;
                this.$formName = $formName;
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                MutableComponent pokemonName = this.$battlePokemon.getName();
                Object[] objectArray = new Object[]{pokemonName};
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("detailschange." + this.$formName, objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"detailschange.$formName\", pokemonName)");
                this.$battle.broadcastChatMessage((Component)mutableComponent);
                ((Map)this.$battle.getMajorBattleActions()).put(this.$battlePokemon.getUuid(), this.this$0.getMessage());
            }
        }, 1, null);
    }
}

