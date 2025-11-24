/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/TerastallizeInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
@SourceDebugExtension(value={"SMAP\nTerastallizeInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TerastallizeInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/TerastallizeInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1#2:37\n*E\n"})
public final class TerastallizeInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public TerastallizeInstruction(@NotNull BattleMessage message) {
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
                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                BattlePokemon battlePokemon2 = this.message.battlePokemon(0, battle2);
                if (battlePokemon2 == null) {
                    return;
                }
                battlePokemon = battlePokemon2;
                object = this.message.effectAt(1);
                if (object == null) break block4;
                Effect it = object;
                boolean bl = false;
                ElementalType elementalType = ElementalTypes.INSTANCE.get(it.getId());
                object = elementalType;
                if (elementalType != null) break block5;
            }
            return;
        }
        Object type = object;
        PokemonBattle.dispatchWaiting$default(battle2, 0.0f, (Function0)new Function0<Unit>(battlePokemon, battle2, (ElementalType)type, this){
            final /* synthetic */ BattlePokemon $battlePokemon;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ ElementalType $type;
            final /* synthetic */ TerastallizeInstruction this$0;
            {
                this.$battlePokemon = $battlePokemon;
                this.$battle = $battle;
                this.$type = $type;
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                MutableComponent pokemonName = this.$battlePokemon.getName();
                Object[] objectArray = new Object[]{pokemonName, this.$type.getDisplayName()};
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("terastallize", objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"terastallize\u2026onName, type.displayName)");
                this.$battle.broadcastChatMessage((Component)TextKt.yellow(mutableComponent));
                ((Map)this.$battle.getMinorBattleActions()).put(this.$battlePokemon.getUuid(), this.this$0.getMessage());
            }
        }, 1, null);
    }
}

