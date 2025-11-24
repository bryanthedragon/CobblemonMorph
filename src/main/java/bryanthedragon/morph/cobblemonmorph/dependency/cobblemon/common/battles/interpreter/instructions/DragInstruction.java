/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.BattleDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.DragInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.SwitchInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.TransformInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0016\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0013\u001a\u0004\b\u0017\u0010\u0015\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/DragInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "battleActor", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getBattleActor", "()Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "instructionSet", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "getInstructionSet", "()Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "privateMessage", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getPrivateMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "publicMessage", "getPublicMessage", "<init>", "(Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class DragInstruction
implements InterpreterInstruction {
    @NotNull
    private final InstructionSet instructionSet;
    @NotNull
    private final BattleActor battleActor;
    @NotNull
    private final BattleMessage publicMessage;
    @NotNull
    private final BattleMessage privateMessage;

    public DragInstruction(@NotNull InstructionSet instructionSet, @NotNull BattleActor battleActor, @NotNull BattleMessage publicMessage, @NotNull BattleMessage privateMessage) {
        Intrinsics.checkNotNullParameter((Object)instructionSet, (String)"instructionSet");
        Intrinsics.checkNotNullParameter((Object)battleActor, (String)"battleActor");
        Intrinsics.checkNotNullParameter((Object)publicMessage, (String)"publicMessage");
        Intrinsics.checkNotNullParameter((Object)privateMessage, (String)"privateMessage");
        this.instructionSet = instructionSet;
        this.battleActor = battleActor;
        this.publicMessage = publicMessage;
        this.privateMessage = privateMessage;
    }

    @NotNull
    public final InstructionSet getInstructionSet() {
        return this.instructionSet;
    }

    @NotNull
    public final BattleActor getBattleActor() {
        return this.battleActor;
    }

    @NotNull
    public final BattleMessage getPublicMessage() {
        return this.publicMessage;
    }

    @NotNull
    public final BattleMessage getPrivateMessage() {
        return this.privateMessage;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        battle2.dispatchInsert((Function0<? extends Iterable<? extends BattleDispatch>>)((Function0)new Function0<Iterable<? extends BattleDispatch>>(this, battle2){
            final /* synthetic */ DragInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Iterable<BattleDispatch> invoke() {
                BattleContext.Type[] typeArray;
                Object v1;
                ActiveBattlePokemon activePokemon;
                String pnx;
                block6: {
                    void $this$iv;
                    Pair<String, String> pair = this.this$0.getPublicMessage().pnxAndUuid(0);
                    Intrinsics.checkNotNull(pair);
                    pnx = (String)pair.component1();
                    activePokemon = (ActiveBattlePokemon)this.$battle.getActorAndActiveSlotFromPNX(pnx).component2();
                    InstructionSet instructionSet = this.this$0.getInstructionSet();
                    InterpreterInstruction comparedTo$iv = this.this$0;
                    boolean $i$f$getNextInstruction = false;
                    int index$iv = $this$iv.getInstructions().indexOf(comparedTo$iv);
                    if (Intrinsics.areEqual((Object)CollectionsKt.last($this$iv.getInstructions()), (Object)comparedTo$iv)) {
                        v1 = null;
                    } else {
                        void $this$firstOrNull$iv$iv;
                        void $this$filterIsInstanceTo$iv$iv$iv;
                        Iterable $this$filterIsInstance$iv$iv = $this$iv.getInstructions().subList(index$iv + 1, $this$iv.getInstructions().size());
                        boolean $i$f$filterIsInstance = false;
                        typeArray = $this$filterIsInstance$iv$iv;
                        Collection destination$iv$iv$iv = new ArrayList<E>();
                        boolean $i$f$filterIsInstanceTo = false;
                        for (T element$iv$iv$iv : $this$filterIsInstanceTo$iv$iv$iv) {
                            if (!(element$iv$iv$iv instanceof TransformInstruction)) continue;
                            destination$iv$iv$iv.add(element$iv$iv$iv);
                        }
                        $this$filterIsInstance$iv$iv = (List)destination$iv$iv$iv;
                        boolean $i$f$firstOrNull = false;
                        typeArray = $this$firstOrNull$iv$iv.iterator();
                        while (typeArray.hasNext()) {
                            E element$iv$iv;
                            E e = element$iv$iv = typeArray.next();
                            boolean bl = false;
                            E it = e;
                            if (!true) continue;
                            v1 = element$iv$iv;
                            break block6;
                        }
                        v1 = null;
                    }
                }
                TransformInstruction transformInstruction = v1;
                boolean imposter = (transformInstruction != null ? transformInstruction.getExpectedTarget() : null) != null;
                BattlePokemon illusion = this.this$0.getPublicMessage().battlePokemonFromOptional(this.$battle, "is");
                BattlePokemon battlePokemon = this.this$0.getPublicMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return SetsKt.emptySet();
                }
                BattlePokemon pokemon = battlePokemon;
                Object object = new Object[]{pokemon.getName()};
                MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("dragged_out", (Object[])object);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"dragged_out\", pokemon.getName())");
                this.$battle.broadcastChatMessage((Component)mutableComponent);
                BattlePokemon battlePokemon2 = activePokemon.getBattlePokemon();
                if (battlePokemon2 != null) {
                    object = battlePokemon2;
                    PokemonBattle pokemonBattle = this.$battle;
                    DragInstruction dragInstruction = this.this$0;
                    Object oldPokemon = object;
                    boolean bl = false;
                    typeArray = new BattleContext.Type[]{BattleContext.Type.VOLATILE, BattleContext.Type.BOOST, BattleContext.Type.UNBOOST};
                    ((BattlePokemon)oldPokemon).getContextManager().clear(typeArray);
                    ((Map)pokemonBattle.getMajorBattleActions()).put(((BattlePokemon)oldPokemon).getUuid(), dragInstruction.getPublicMessage());
                }
                ((Map)this.$battle.getMajorBattleActions()).put(pokemon.getUuid(), this.this$0.getPublicMessage());
                LivingEntity entity2 = this.this$0.getBattleActor() instanceof EntityBackedBattleActor ? (LivingEntity)((EntityBackedBattleActor)((Object)this.this$0.getBattleActor())).getEntity() : null;
                return SetsKt.setOf(arg_0 -> invoke.1.invoke$lambda$1(entity2, this.$battle, this.this$0, pnx, activePokemon, pokemon, illusion, imposter, arg_0));
            }

            private static final DispatchResult invoke$lambda$1(LivingEntity $entity, PokemonBattle $battle, DragInstruction this$0, String $pnx, ActiveBattlePokemon $activePokemon, BattlePokemon $pokemon, BattlePokemon $illusion, boolean $imposter, PokemonBattle it) {
                Intrinsics.checkNotNullParameter((Object)$battle, (String)"$battle");
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                Intrinsics.checkNotNullParameter((Object)$pnx, (String)"$pnx");
                Intrinsics.checkNotNullParameter((Object)$activePokemon, (String)"$activePokemon");
                Intrinsics.checkNotNullParameter((Object)$pokemon, (String)"$pokemon");
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return $entity != null ? SwitchInstruction.Companion.createEntitySwitch($battle, this$0.getBattleActor(), $entity, $pnx, $activePokemon, $pokemon, $illusion, $imposter) : SwitchInstruction.Companion.createNonEntitySwitch($battle, this$0.getBattleActor(), $pnx, $activePokemon, $pokemon, $illusion);
            }
        }));
    }
}

