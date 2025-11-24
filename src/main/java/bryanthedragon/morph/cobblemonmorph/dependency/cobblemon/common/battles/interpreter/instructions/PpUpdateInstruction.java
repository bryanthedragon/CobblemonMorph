/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/PpUpdateInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class PpUpdateInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public PpUpdateInstruction(@NotNull BattleMessage message) {
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
            final /* synthetic */ PpUpdateInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                Object object;
                BattlePokemon battlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return DispatchResultKt.getGO();
                }
                BattlePokemon pokemon = battlePokemon;
                Object object2 = this.this$0.getMessage().argumentAt(1);
                if (object2 == null || (object2 = StringsKt.split$default((CharSequence)((CharSequence)object2), (String[])(object = new String[]{", "}), (boolean)false, (int)0, (int)6, null)) == null) {
                    return DispatchResultKt.getGO();
                }
                Object moveDatum = object2;
                Iterable $this$forEach$iv = (Iterable)moveDatum;
                boolean $i$f$forEach = false;
                for (E element$iv : $this$forEach$iv) {
                    Move move;
                    Object v2;
                    String movePp;
                    block5: {
                        String moveData = (String)element$iv;
                        boolean bl = false;
                        String[] stringArray = new String[]{": "};
                        List moveIdAndPp = StringsKt.split$default((CharSequence)moveData, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
                        String moveId = (String)moveIdAndPp.get(0);
                        movePp = (String)moveIdAndPp.get(1);
                        Iterable $this$firstOrNull$iv = pokemon.getEffectedPokemon().getMoveSet();
                        boolean $i$f$firstOrNull = false;
                        for (T element$iv2 : $this$firstOrNull$iv) {
                            Move move2 = (Move)element$iv2;
                            boolean bl2 = false;
                            if (!StringsKt.equals((String)move2.getName(), (String)moveId, (boolean)true)) continue;
                            v2 = element$iv2;
                            break block5;
                        }
                        v2 = null;
                    }
                    if ((Move)v2 == null) {
                        return DispatchResultKt.getGO();
                    }
                    move.setCurrentPp(Integer.parseInt(movePp));
                }
                return DispatchResultKt.getGO();
            }
        }));
    }
}

