/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.ActionEffectInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.BoostInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010(\n\u0002\b\b\u0018\u0000 52\u00020\u0001:\u00015B/\u0012\u0006\u0010!\u001a\u00020 \u0012\u0006\u0010*\u001a\u00020)\u0012\f\u0010/\u001a\b\u0012\u0004\u0012\u00020)0.\u0012\b\b\u0002\u0010&\u001a\u00020%\u00a2\u0006\u0004\b3\u00104J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\u0006J\u001f\u0010\n\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR&\u0010\r\u001a\u0006\u0012\u0002\b\u00030\f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R(\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R\u0017\u0010&\u001a\u00020%8\u0006\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b&\u0010(R\u0017\u0010*\u001a\u00020)8\u0006\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-R\u001d\u0010/\u001a\b\u0012\u0004\u0012\u00020)0.8\u0006\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102\u00a8\u00066"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/BoostInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/ActionEffectInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "postActionEffect", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "preActionEffect", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "runActionEffect", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/bedrockk/molang/runtime/MoLangRuntime;)V", "Ljava/util/concurrent/CompletableFuture;", "future", "Ljava/util/concurrent/CompletableFuture;", "getFuture", "()Ljava/util/concurrent/CompletableFuture;", "setFuture", "(Ljava/util/concurrent/CompletableFuture;)V", "", "", "holds", "Ljava/util/Set;", "getHolds", "()Ljava/util/Set;", "setHolds", "(Ljava/util/Set;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "instructionSet", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "getInstructionSet", "()Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "", "isBoost", "Z", "()Z", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "", "remainingLines", "Ljava/util/Iterator;", "getRemainingLines", "()Ljava/util/Iterator;", "<init>", "(Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;Ljava/util/Iterator;Z)V", "Companion", "common"})
public final class BoostInstruction
implements ActionEffectInstruction {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final InstructionSet instructionSet;
    @NotNull
    private final BattleMessage message;
    @NotNull
    private final Iterator<BattleMessage> remainingLines;
    private final boolean isBoost;
    @NotNull
    private CompletableFuture<?> future;
    @NotNull
    private Set<String> holds;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ActionEffectTimeline BOOST_EFFECT;
    @NotNull
    private static final ActionEffectTimeline UNBOOST_EFFECT;

    public BoostInstruction(@NotNull InstructionSet instructionSet, @NotNull BattleMessage message, @NotNull Iterator<BattleMessage> remainingLines, boolean isBoost) {
        Intrinsics.checkNotNullParameter((Object)instructionSet, (String)"instructionSet");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        Intrinsics.checkNotNullParameter(remainingLines, (String)"remainingLines");
        this.instructionSet = instructionSet;
        this.message = message;
        this.remainingLines = remainingLines;
        this.isBoost = isBoost;
        CompletableFuture<Unit> completableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(completableFuture, (String)"completedFuture(Unit)");
        this.future = completableFuture;
        this.holds = new LinkedHashSet();
        this.id = MiscUtils.cobblemonResource("boost");
    }

    public /* synthetic */ BoostInstruction(InstructionSet instructionSet, BattleMessage battleMessage, Iterator iterator, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            bl = true;
        }
        this(instructionSet, battleMessage, iterator, bl);
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
    public final Iterator<BattleMessage> getRemainingLines() {
        return this.remainingLines;
    }

    public final boolean isBoost() {
        return this.isBoost;
    }

    @Override
    @NotNull
    public CompletableFuture<?> getFuture() {
        return this.future;
    }

    @Override
    public void setFuture(@NotNull CompletableFuture<?> completableFuture) {
        Intrinsics.checkNotNullParameter(completableFuture, (String)"<set-?>");
        this.future = completableFuture;
    }

    @Override
    @NotNull
    public Set<String> getHolds() {
        return this.holds;
    }

    @Override
    public void setHolds(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.holds = set2;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void preActionEffect(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
    }

    @Override
    public void runActionEffect(@NotNull PokemonBattle battle2, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this, battle2, runtime2){
            final /* synthetic */ BoostInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ MoLangRuntime $runtime;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                this.$runtime = $runtime;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                ActionEffectTimeline actionEffect = this.this$0.isBoost() ? BoostInstruction.Companion.getBOOST_EFFECT() : BoostInstruction.Companion.getUNBOOST_EFFECT();
                Object[] objectArray = new Object[]{this.$battle};
                List providers = CollectionsKt.mutableListOf((Object[])objectArray);
                BattlePokemon battlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return DispatchResultKt.getGO();
                }
                BattlePokemon pokemon = battlePokemon;
                PokemonEntity pokemonEntity = pokemon.getEffectedPokemon().getEntity();
                if (pokemonEntity != null) {
                    PokemonEntity it = pokemonEntity;
                    boolean bl = false;
                    Entity[] entityArray = new Entity[]{it};
                    UsersProvider p0 = new UsersProvider(entityArray);
                    boolean bl2 = false;
                    providers.add(p0);
                }
                ActionEffectContext context = new ActionEffectContext(actionEffect, null, providers, this.$runtime, false, false, null, 114, null);
                this.this$0.setFuture(actionEffect.run(context));
                this.this$0.setHolds(context.getHolds());
                this.this$0.getFuture().thenApply(arg_0 -> runActionEffect.1.invoke$lambda$1(this.this$0, arg_0));
                return DispatchResultKt.getGO();
            }

            private static final Unit invoke$lambda$1(BoostInstruction this$0, Object it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                this$0.getHolds().clear();
                return Unit.INSTANCE;
            }
        }));
    }

    @Override
    public void postActionEffect(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        BattlePokemon battlePokemon = this.message.battlePokemon(0, battle2);
        if (battlePokemon == null) {
            return;
        }
        BattlePokemon pokemon = battlePokemon;
        String string = this.message.argumentAt(1);
        if (string == null) {
            return;
        }
        String statKey = string;
        String string2 = this.message.argumentAt(2);
        if (string2 == null) {
            return;
        }
        int stages = Integer.parseInt(string2);
        Component stat = Stats.Companion.getStat(statKey).getDisplayName();
        String severity = Stats.Companion.getSeverity(stages);
        String rootKey = this.isBoost ? "boost" : "unboost";
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this, rootKey, severity, pokemon, stat, battle2, stages){
            final /* synthetic */ BoostInstruction this$0;
            final /* synthetic */ String $rootKey;
            final /* synthetic */ String $severity;
            final /* synthetic */ BattlePokemon $pokemon;
            final /* synthetic */ Component $stat;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ int $stages;
            {
                this.this$0 = $receiver;
                this.$rootKey = $rootKey;
                this.$severity = $severity;
                this.$pokemon = $pokemon;
                this.$stat = $stat;
                this.$battle = $battle;
                this.$stages = $stages;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                MutableComponent mutableComponent;
                if (this.this$0.getMessage().hasOptionalArgument("zeffect")) {
                    var2_1 = new Object[]{this.$pokemon.getName(), this.$stat};
                    mutableComponent = LocalizationUtilsKt.battleLang(this.$rootKey + "." + this.$severity + ".zeffect", var2_1);
                } else {
                    var2_1 = new Object[]{this.$pokemon.getName(), this.$stat};
                    mutableComponent = LocalizationUtilsKt.battleLang(this.$rootKey + "." + this.$severity, var2_1);
                }
                MutableComponent lang = mutableComponent;
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)lang);
                BattleContext.Type boostBucket = this.this$0.isBoost() ? BattleContext.Type.BOOST : BattleContext.Type.UNBOOST;
                BattleContext context = ShowdownInterpreter.INSTANCE.getContextFromAction(this.this$0.getMessage(), boostBucket, this.$battle);
                int n = this.$stages;
                BattlePokemon battlePokemon = this.$pokemon;
                int n2 = 0;
                while (n2 < n) {
                    int it = n2++;
                    boolean bl = false;
                    BattleContext[] battleContextArray = new BattleContext[]{context};
                    battlePokemon.getContextManager().add(battleContextArray);
                }
                ((Map)this.$battle.getMinorBattleActions()).put(this.$pokemon.getUuid(), this.this$0.getMessage());
                return new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(this.this$0){
                    final /* synthetic */ BoostInstruction this$0;
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
        }));
    }

    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        ActionEffectInstruction.DefaultImpls.invoke(this, battle2);
    }

    @Override
    public void addMolangQueries(@NotNull MoLangRuntime runtime2) {
        ActionEffectInstruction.DefaultImpls.addMolangQueries(this, runtime2);
    }

    static {
        ActionEffectTimeline actionEffectTimeline = ActionEffects.INSTANCE.getActionEffects().get(MiscUtils.cobblemonResource("boost"));
        Intrinsics.checkNotNull((Object)actionEffectTimeline);
        BOOST_EFFECT = actionEffectTimeline;
        ActionEffectTimeline actionEffectTimeline2 = ActionEffects.INSTANCE.getActionEffects().get(MiscUtils.cobblemonResource("unboost"));
        Intrinsics.checkNotNull((Object)actionEffectTimeline2);
        UNBOOST_EFFECT = actionEffectTimeline2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/BoostInstruction$Companion;", "", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "BOOST_EFFECT", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "getBOOST_EFFECT", "()Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "UNBOOST_EFFECT", "getUNBOOST_EFFECT", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ActionEffectTimeline getBOOST_EFFECT() {
            return BOOST_EFFECT;
        }

        @NotNull
        public final ActionEffectTimeline getUNBOOST_EFFECT() {
            return UNBOOST_EFFECT;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

