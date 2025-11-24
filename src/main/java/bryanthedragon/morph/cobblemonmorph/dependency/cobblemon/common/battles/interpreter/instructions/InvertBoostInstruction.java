/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BasicContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\f\u0010\rJ\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/InvertBoostInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class InvertBoostInstruction
implements InterpreterInstruction {
    @NotNull
    private final BattleMessage message;

    public InvertBoostInstruction(@NotNull BattleMessage message) {
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
        PokemonBattle.dispatchWaiting$default(battle2, 0.0f, (Function0)new Function0<Unit>(this, battle2){
            final /* synthetic */ InvertBoostInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke() {
                BasicContext[] it;
                BasicContext[] basicContextArray;
                BasicContext[] basicContextArray2;
                Iterable<BasicContext> iterable;
                Iterable<BasicContext> destination$iv$iv;
                BattlePokemon battlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return;
                }
                BattlePokemon pokemon = battlePokemon;
                MutableComponent name = pokemon.getName();
                Object[] objectArray = new Object[]{name};
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("invertboost", objectArray);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"invertboost\", name)");
                this.$battle.broadcastChatMessage((Component)mutableComponent);
                BattleContext context = ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), BattleContext.Type.BOOST, this.$battle);
                Collection<BattleContext> collection = pokemon.getContextManager().get(BattleContext.Type.BOOST);
                if (collection != null) {
                    void $this$toTypedArray$iv;
                    void $this$mapTo$iv$iv;
                    Iterable $this$map$iv = collection;
                    boolean $i$f$map = false;
                    Iterable iterable2 = $this$map$iv;
                    destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it2;
                        BattleContext battleContext = (BattleContext)item$iv$iv;
                        iterable = destination$iv$iv;
                        boolean bl = false;
                        iterable.add(new BasicContext(it2.getId(), context.getTurn(), BattleContext.Type.UNBOOST, context.getOrigin()));
                    }
                    $this$map$iv = (List)destination$iv$iv;
                    boolean $i$f$toTypedArray = false;
                    void thisCollection$iv = $this$toTypedArray$iv;
                    basicContextArray2 = thisCollection$iv.toArray(new BasicContext[0]);
                } else {
                    basicContextArray2 = null;
                }
                BasicContext[] newUnboosts = basicContextArray2;
                Collection<BattleContext> collection2 = pokemon.getContextManager().get(BattleContext.Type.UNBOOST);
                if (collection2 != null) {
                    void $this$toTypedArray$iv;
                    void $this$mapTo$iv$iv;
                    Iterable $this$map$iv = collection2;
                    boolean $i$f$map = false;
                    destination$iv$iv = $this$map$iv;
                    Collection destination$iv$iv2 = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                    boolean $i$f$mapTo = false;
                    for (T item$iv$iv : $this$mapTo$iv$iv) {
                        void it3;
                        BattleContext bl = (BattleContext)item$iv$iv;
                        iterable = destination$iv$iv2;
                        boolean bl2 = false;
                        iterable.add((BasicContext)new BasicContext(it3.getId(), context.getTurn(), BattleContext.Type.BOOST, context.getOrigin()));
                    }
                    $this$map$iv = (List)destination$iv$iv2;
                    boolean $i$f$toTypedArray = false;
                    void thisCollection$iv = $this$toTypedArray$iv;
                    basicContextArray = thisCollection$iv.toArray(new BasicContext[0]);
                } else {
                    basicContextArray = null;
                }
                BasicContext[] newBoosts = basicContextArray;
                BattleContext.Type[] typeArray = new BattleContext.Type[]{BattleContext.Type.BOOST, BattleContext.Type.UNBOOST};
                pokemon.getContextManager().clear(typeArray);
                if (newBoosts != null) {
                    it = newBoosts;
                    boolean bl = false;
                    pokemon.getContextManager().add(Arrays.copyOf(it, it.length));
                }
                if (newUnboosts != null) {
                    it = newUnboosts;
                    boolean bl = false;
                    pokemon.getContextManager().add(Arrays.copyOf(it, it.length));
                }
                ((Map)this.$battle.getMinorBattleActions()).put(pokemon.getUuid(), this.this$0.getMessage());
            }
        }, 1, null);
    }
}

