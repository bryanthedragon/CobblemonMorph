/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.TargetsProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.CauserInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.DamageInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.MissInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.MoveInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.UseMoveEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010#\u001a\u00020\"\u0012\u0006\u0010(\u001a\u00020'\u00a2\u0006\u0004\b;\u0010<J\u0018\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0096\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0019\u0010\t\u001a\u0004\u0018\u00010\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011RF\u0010\u0014\u001a&\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u00050\u0005 \u0013*\u0012\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00120\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R(\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0017\u0010#\u001a\u00020\"8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\u0017\u0010(\u001a\u00020'8\u0006\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+R\u0017\u0010-\u001a\u00020,8\u0006\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R$\u00102\u001a\u0004\u0018\u0001018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b2\u00103\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\"\u00108\u001a\u0002018\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b8\u00103\u001a\u0004\b9\u00105\"\u0004\b:\u00107\u00a8\u0006="}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/MoveInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/CauserInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "actionEffect", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "getActionEffect", "()Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "effect", "Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "getEffect", "()Lcom/cobblemon/mod/common/api/battles/interpreter/Effect;", "Ljava/util/concurrent/CompletableFuture;", "kotlin.jvm.PlatformType", "future", "Ljava/util/concurrent/CompletableFuture;", "getFuture", "()Ljava/util/concurrent/CompletableFuture;", "setFuture", "(Ljava/util/concurrent/CompletableFuture;)V", "", "", "holds", "Ljava/util/Set;", "getHolds", "()Ljava/util/Set;", "setHolds", "(Ljava/util/Set;)V", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "instructionSet", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "getInstructionSet", "()Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "move", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getMove", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "targetPokemon", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getTargetPokemon", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "setTargetPokemon", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "userPokemon", "getUserPokemon", "setUserPokemon", "<init>", "(Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class MoveInstruction
implements InterpreterInstruction,
CauserInstruction {
    @NotNull
    private final InstructionSet instructionSet;
    @NotNull
    private final BattleMessage message;
    @NotNull
    private final Effect effect;
    @NotNull
    private final MoveTemplate move;
    @Nullable
    private final ActionEffectTimeline actionEffect;
    private CompletableFuture<Unit> future;
    @NotNull
    private Set<String> holds;
    public BattlePokemon userPokemon;
    @Nullable
    private BattlePokemon targetPokemon;

    public MoveInstruction(@NotNull InstructionSet instructionSet, @NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)instructionSet, (String)"instructionSet");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.instructionSet = instructionSet;
        this.message = message;
        Effect effect = this.message.effectAt(1);
        if (effect == null) {
            effect = Effect.Companion.pure("", "");
        }
        this.effect = effect;
        this.move = Moves.INSTANCE.getByNameOrDummy(this.effect.getId());
        this.actionEffect = this.move.getActionEffect();
        this.future = CompletableFuture.completedFuture(Unit.INSTANCE);
        this.holds = new LinkedHashSet();
    }

    @NotNull
    public final InstructionSet getInstructionSet() {
        return this.instructionSet;
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
    }

    @NotNull
    public final Effect getEffect() {
        return this.effect;
    }

    @NotNull
    public final MoveTemplate getMove() {
        return this.move;
    }

    @Nullable
    public final ActionEffectTimeline getActionEffect() {
        return this.actionEffect;
    }

    public final CompletableFuture<Unit> getFuture() {
        return this.future;
    }

    public final void setFuture(CompletableFuture<Unit> completableFuture) {
        this.future = completableFuture;
    }

    @NotNull
    public final Set<String> getHolds() {
        return this.holds;
    }

    public final void setHolds(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.holds = set2;
    }

    @NotNull
    public final BattlePokemon getUserPokemon() {
        BattlePokemon battlePokemon = this.userPokemon;
        if (battlePokemon != null) {
            return battlePokemon;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"userPokemon");
        return null;
    }

    public final void setUserPokemon(@NotNull BattlePokemon battlePokemon) {
        Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"<set-?>");
        this.userPokemon = battlePokemon;
    }

    @Nullable
    public final BattlePokemon getTargetPokemon() {
        return this.targetPokemon;
    }

    public final void setTargetPokemon(@Nullable BattlePokemon battlePokemon) {
        this.targetPokemon = battlePokemon;
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        BattlePokemon battlePokemon = this.message.battlePokemon(0, battle2);
        Intrinsics.checkNotNull((Object)battlePokemon);
        this.setUserPokemon(battlePokemon);
        BattlePokemon targetPokemon = this.targetPokemon = this.message.battlePokemon(2, battle2);
        Effect optionalEffect = BattleMessage.effect$default(this.message, null, 1, null);
        ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle2, optionalEffect, this.getUserPokemon());
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this){
            final /* synthetic */ MoveInstruction this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                return new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(this.this$0){
                    final /* synthetic */ MoveInstruction this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    /*
                     * WARNING - void declaration
                     */
                    @NotNull
                    public final Boolean invoke() {
                        Object object;
                        Object v0;
                        block2: {
                            void $this$lastOrNull$iv$iv;
                            void $this$filterIsInstanceTo$iv$iv$iv;
                            void $this$iv;
                            InstructionSet instructionSet = this.this$0.getInstructionSet();
                            InterpreterInstruction comparedTo$iv = this.this$0;
                            boolean $i$f$getMostRecentInstruction = false;
                            int index$iv = $this$iv.getInstructions().indexOf(comparedTo$iv);
                            Iterable $this$filterIsInstance$iv$iv = $this$iv.getInstructions().subList(0, index$iv);
                            boolean $i$f$filterIsInstance = false;
                            Iterable iterable = $this$filterIsInstance$iv$iv;
                            Collection destination$iv$iv$iv = new ArrayList<E>();
                            boolean $i$f$filterIsInstanceTo = false;
                            for (T element$iv$iv$iv : $this$filterIsInstanceTo$iv$iv$iv) {
                                if (!(element$iv$iv$iv instanceof MoveInstruction)) continue;
                                destination$iv$iv$iv.add(element$iv$iv$iv);
                            }
                            $this$filterIsInstance$iv$iv = (List)destination$iv$iv$iv;
                            boolean $i$f$lastOrNull = false;
                            ListIterator<E> iterator$iv$iv = $this$lastOrNull$iv$iv.listIterator($this$lastOrNull$iv$iv.size());
                            while (iterator$iv$iv.hasPrevious()) {
                                E element$iv$iv;
                                E e = element$iv$iv = iterator$iv$iv.previous();
                                boolean bl = false;
                                E it = e;
                                if (!true) continue;
                                v0 = element$iv$iv;
                                break block2;
                            }
                            v0 = null;
                        }
                        return !((object = (MoveInstruction)v0) != null && (object = ((MoveInstruction)object).getFuture()) != null ? !((CompletableFuture)object).isDone() : false);
                    }
                }));
            }
        }));
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this, battle2, optionalEffect, targetPokemon){
            final /* synthetic */ MoveInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ Effect $optionalEffect;
            final /* synthetic */ BattlePokemon $targetPokemon;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                this.$optionalEffect = $optionalEffect;
                this.$targetPokemon = $targetPokemon;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final DispatchResult invoke() {
                void $this$mapNotNullTo$iv$iv;
                void $this$mapNotNull$iv;
                void $this$filterIsInstanceTo$iv$iv;
                void $this$mapNotNullTo$iv$iv2;
                void $this$mapNotNull$iv2;
                void $this$filterIsInstanceTo$iv$iv2;
                Object object;
                Object it;
                MutableComponent mutableComponent;
                Object progress2;
                MutableComponent pokemonName = this.this$0.getUserPokemon().getName();
                Object[] objectArray = ShowdownInterpreter.INSTANCE.getLastCauser();
                UUID uUID = this.$battle.getBattleId();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"battle.battleId");
                Object[] objectArray2 = uUID;
                BattleMessage battleMessage = this.this$0.getMessage();
                objectArray.put((UUID)objectArray2, (BattleMessage)battleMessage);
                objectArray = this.this$0.getUserPokemon().getEffectedPokemon();
                objectArray2 = this.this$0;
                Object[] pokemon = objectArray;
                boolean $i$a$-let-MoveInstruction$invoke$2$62 = false;
                if (UseMoveEvolutionProgress.Companion.supports((Pokemon)pokemon, objectArray2.getMove())) {
                    progress2 = (UseMoveEvolutionProgress)pokemon.getEvolutionProxy().current().progressFirstOrCreate((Function1)new Function1<EvolutionProgress<?>, Boolean>((MoveInstruction)objectArray2){
                        final /* synthetic */ MoveInstruction this$0;
                        {
                            this.this$0 = $receiver;
                            super(1);
                        }

                        @NotNull
                        public final Boolean invoke(@NotNull EvolutionProgress<?> it) {
                            Intrinsics.checkNotNullParameter(it, (String)"it");
                            return it instanceof UseMoveEvolutionProgress && Intrinsics.areEqual((Object)((UseMoveEvolutionProgress)it).currentProgress().getMove(), (Object)this.this$0.getMove());
                        }
                    }, invoke.1.progress.2.INSTANCE);
                    ((UseMoveEvolutionProgress)progress2).updateProgress(new UseMoveEvolutionProgress.Progress(objectArray2.getMove(), ((UseMoveEvolutionProgress)progress2).currentProgress().getAmount() + 1));
                }
                Effect effect = this.$optionalEffect;
                if (Intrinsics.areEqual((Object)(effect != null ? effect.getId() : null), (Object)"magicbounce")) {
                    objectArray2 = new Object[]{pokemonName, this.this$0.getMove().getDisplayName()};
                    mutableComponent = LocalizationUtilsKt.battleLang("ability.magicbounce", objectArray2);
                } else if (!Intrinsics.areEqual((Object)this.this$0.getMove().getName(), (Object)"struggle") && this.$targetPokemon != null && !Intrinsics.areEqual((Object)this.$targetPokemon, (Object)this.this$0.getUserPokemon())) {
                    objectArray2 = new Object[]{pokemonName, this.this$0.getMove().getDisplayName(), this.$targetPokemon.getName()};
                    mutableComponent = LocalizationUtilsKt.battleLang("used_move_on", objectArray2);
                } else {
                    objectArray2 = new Object[]{pokemonName, this.this$0.getMove().getDisplayName()};
                    mutableComponent = LocalizationUtilsKt.battleLang("used_move", objectArray2);
                }
                MutableComponent lang = mutableComponent;
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)lang);
                ((Map)this.$battle.getMajorBattleActions()).put(this.this$0.getUserPokemon().getUuid(), this.this$0.getMessage());
                pokemon = new Object[]{this.$battle};
                List providers = CollectionsKt.mutableListOf((Object[])pokemon);
                PokemonEntity pokemonEntity = this.this$0.getUserPokemon().getEffectedPokemon().getEntity();
                if (pokemonEntity != null) {
                    it = pokemonEntity;
                    boolean bl = false;
                    Entity[] entityArray = new Entity[]{it};
                    UsersProvider p0 = new UsersProvider(entityArray);
                    boolean bl2 = false;
                    providers.add(p0);
                }
                if ((object = this.$targetPokemon) != null && (object = ((BattlePokemon)object).getEffectedPokemon()) != null && (object = ((Pokemon)object).getEntity()) != null) {
                    Object it2 = object;
                    boolean bl = false;
                    Entity[] entityArray = new Entity[]{it2};
                    TargetsProvider p0 = new TargetsProvider(entityArray);
                    boolean bl3 = false;
                    providers.add(p0);
                }
                MoLangRuntime $i$a$-let-MoveInstruction$invoke$2$62 = new MoLangRuntime();
                progress2 = this.$battle;
                it = $i$a$-let-MoveInstruction$invoke$2$62;
                boolean bl = false;
                MoLangEnvironment moLangEnvironment = ((MoLangRuntime)it).getEnvironment();
                Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"it.environment");
                MoLangFunctions.INSTANCE.addStandardFunctions(((PokemonBattle)progress2).addQueryFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null)));
                MoLangRuntime runtime2 = $i$a$-let-MoveInstruction$invoke$2$62;
                if (this.this$0.getActionEffect() == null) {
                    return DispatchResultKt.getGO();
                }
                progress2 = this.this$0.getActionEffect();
                ActionEffectContext context = new ActionEffectContext((ActionEffectTimeline)progress2, null, providers, runtime2, false, false, null, 114, null);
                List<InterpreterInstruction> subsequentInstructions = this.this$0.getInstructionSet().findInstructionsCausedBy(this.this$0);
                Iterable $this$filterIsInstance$iv = subsequentInstructions;
                boolean $i$f$filterIsInstance = false;
                Iterable bl3 = $this$filterIsInstance$iv;
                Iterable<E> destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterIsInstanceTo = false;
                for (T element$iv$iv : $this$filterIsInstanceTo$iv$iv2) {
                    if (!(element$iv$iv instanceof MissInstruction)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filterIsInstance$iv = (List)destination$iv$iv;
                boolean $i$f$mapNotNull = false;
                $this$filterIsInstanceTo$iv$iv2 = $this$mapNotNull$iv2;
                destination$iv$iv = new ArrayList<E>();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv2;
                boolean $i$f$forEach = false;
                Iterator<T> iterator = $this$forEach$iv$iv$iv.iterator();
                while (iterator.hasNext()) {
                    BattlePokemon it$iv$iv;
                    T element$iv$iv$iv;
                    T element$iv$iv = element$iv$iv$iv = iterator.next();
                    boolean bl4 = false;
                    MissInstruction it3 = (MissInstruction)element$iv$iv;
                    boolean bl5 = false;
                    if (it3.getTarget() == null) continue;
                    boolean bl6 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                List missedTargets = (List)destination$iv$iv;
                MoLangEnvironment moLangEnvironment2 = runtime2.getEnvironment();
                Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment2, (String)"runtime.environment");
                MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment2, null, 1, null).addFunction("missed", arg_0 -> invoke.2.invoke$lambda$7(missedTargets, arg_0));
                Iterable $this$filterIsInstance$iv2 = subsequentInstructions;
                boolean $i$f$filterIsInstance2 = false;
                destination$iv$iv = $this$filterIsInstance$iv2;
                Collection destination$iv$iv2 = new ArrayList<E>();
                boolean $i$f$filterIsInstanceTo2 = false;
                for (T element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                    if (!(element$iv$iv instanceof DamageInstruction)) continue;
                    destination$iv$iv2.add(element$iv$iv);
                }
                $this$filterIsInstance$iv2 = (List)destination$iv$iv2;
                boolean $i$f$mapNotNull2 = false;
                $this$filterIsInstanceTo$iv$iv = $this$mapNotNull$iv;
                destination$iv$iv2 = new ArrayList<E>();
                boolean $i$f$mapNotNullTo2 = false;
                void $this$forEach$iv$iv$iv2 = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach2 = false;
                Iterator<T> iterator2 = $this$forEach$iv$iv$iv2.iterator();
                while (iterator2.hasNext()) {
                    BattlePokemon it$iv$iv;
                    T element$iv$iv$iv;
                    T element$iv$iv = element$iv$iv$iv = iterator2.next();
                    boolean bl7 = false;
                    DamageInstruction it4 = (DamageInstruction)element$iv$iv;
                    boolean bl8 = false;
                    if (it4.getExpectedTarget() == null) continue;
                    boolean bl9 = false;
                    destination$iv$iv2.add(it$iv$iv);
                }
                List hurtTargets = (List)destination$iv$iv2;
                MoLangEnvironment moLangEnvironment3 = runtime2.getEnvironment();
                Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment3, (String)"runtime.environment");
                MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment3, null, 1, null).addFunction("hurt", arg_0 -> invoke.2.invoke$lambda$10(hurtTargets, arg_0));
                MoLangEnvironment moLangEnvironment4 = runtime2.getEnvironment();
                Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment4, (String)"runtime.environment");
                MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment4, null, 1, null).addFunction("move", arg_0 -> invoke.2.invoke$lambda$11(this.this$0, arg_0));
                MoLangEnvironment moLangEnvironment5 = runtime2.getEnvironment();
                Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment5, (String)"runtime.environment");
                MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment5, null, 1, null).addFunction("instruction_id", invoke.2::invoke$lambda$12);
                this.this$0.setFuture(this.this$0.getActionEffect().run(context));
                this.this$0.setHolds(context.getHolds());
                this.this$0.getFuture().thenApply(arg_0 -> invoke.2.invoke$lambda$13((Function1)new Function1<Unit, Unit>(this.this$0){
                    final /* synthetic */ MoveInstruction this$0;
                    {
                        this.this$0 = $receiver;
                        super(1);
                    }

                    public final void invoke(Unit it) {
                        this.this$0.getHolds().clear();
                    }
                }, arg_0));
                return new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(this.this$0){
                    final /* synthetic */ MoveInstruction this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return !this.this$0.getHolds().contains("effects");
                    }
                }));
            }

            private static final Object invoke$lambda$7(List $missedTargets, MoParams params) {
                boolean bl;
                block4: {
                    Intrinsics.checkNotNullParameter((Object)$missedTargets, (String)"$missedTargets");
                    if (params.getParams().size() == 0) {
                        return new DoubleValue(!((Collection)$missedTargets).isEmpty());
                    }
                    String entityUUID = params.getString(0);
                    Iterable $this$any$iv = $missedTargets;
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            BattlePokemon it = (BattlePokemon)element$iv;
                            boolean bl2 = false;
                            PokemonEntity pokemonEntity = it.getEntity();
                            if (!Intrinsics.areEqual((Object)(pokemonEntity != null ? pokemonEntity.m_20149_() : null), (Object)entityUUID)) continue;
                            bl = true;
                            break block4;
                        }
                        bl = false;
                    }
                }
                Boolean bl3 = bl;
                return new DoubleValue(bl3);
            }

            private static final Object invoke$lambda$10(List $hurtTargets, MoParams params) {
                boolean bl;
                block4: {
                    Intrinsics.checkNotNullParameter((Object)$hurtTargets, (String)"$hurtTargets");
                    if (params.getParams().size() == 0) {
                        return new DoubleValue(!((Collection)$hurtTargets).isEmpty());
                    }
                    String entityUUID = params.getString(0);
                    Iterable $this$any$iv = $hurtTargets;
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            BattlePokemon it = (BattlePokemon)element$iv;
                            boolean bl2 = false;
                            PokemonEntity pokemonEntity = it.getEntity();
                            if (!Intrinsics.areEqual((Object)(pokemonEntity != null ? pokemonEntity.m_20149_() : null), (Object)entityUUID)) continue;
                            bl = true;
                            break block4;
                        }
                        bl = false;
                    }
                }
                Boolean bl3 = bl;
                return new DoubleValue(bl3);
            }

            private static final Object invoke$lambda$11(MoveInstruction this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return this$0.getMove().getStruct();
            }

            private static final Object invoke$lambda$12(MoParams it) {
                return new StringValue(MiscUtils.cobblemonResource("move").toString());
            }

            private static final Unit invoke$lambda$13(Function1 $tmp0, Object p0) {
                Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
                return (Unit)$tmp0.invoke(p0);
            }
        }));
    }
}

