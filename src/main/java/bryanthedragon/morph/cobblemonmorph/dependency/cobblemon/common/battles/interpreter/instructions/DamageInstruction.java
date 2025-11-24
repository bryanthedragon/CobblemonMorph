/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$BooleanRef
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.ActionEffectInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.CauserInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.DamageInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.MoveInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.animation.PlayPoseableAnimationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleHealthChangePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.effect.RunPosableMoLangPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.DamageTakenEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress.RecoilEvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent.PoisonStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010.\u001a\u00020-\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u00107\u001a\u000202\u0012\u0006\u00103\u001a\u000202\u00a2\u0006\u0004\b9\u0010:J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000b\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000b\u0010\nJ\u001f\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0019\u0010\u0015\u001a\u0004\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R&\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u00198\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR(\u0010\"\u001a\b\u0012\u0004\u0012\u00020!0 8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010)\u001a\u00020(8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,R\u0017\u0010.\u001a\u00020-8\u0006\u00a2\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b0\u00101R\u0017\u00103\u001a\u0002028\u0006\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106R\u0017\u00107\u001a\u0002028\u0006\u00a2\u0006\f\n\u0004\b7\u00104\u001a\u0004\b8\u00106\u00a8\u0006;"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/DamageInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/ActionEffectInstruction;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "doRecoilEvoChecks", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "postActionEffect", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "preActionEffect", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "runActionEffect", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/bedrockk/molang/runtime/MoLangRuntime;)V", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getActor", "()Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "expectedTarget", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getExpectedTarget", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "Ljava/util/concurrent/CompletableFuture;", "future", "Ljava/util/concurrent/CompletableFuture;", "getFuture", "()Ljava/util/concurrent/CompletableFuture;", "setFuture", "(Ljava/util/concurrent/CompletableFuture;)V", "", "", "holds", "Ljava/util/Set;", "getHolds", "()Ljava/util/Set;", "setHolds", "(Ljava/util/Set;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "instructionSet", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "getInstructionSet", "()Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "privateMessage", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getPrivateMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "publicMessage", "getPublicMessage", "<init>", "(Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
@SourceDebugExtension(value={"SMAP\nDamageInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DamageInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/DamageInstruction\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,215:1\n1#2:216\n*E\n"})
public final class DamageInstruction
implements ActionEffectInstruction {
    @NotNull
    private final InstructionSet instructionSet;
    @NotNull
    private final BattleActor actor;
    @NotNull
    private final BattleMessage publicMessage;
    @NotNull
    private final BattleMessage privateMessage;
    @Nullable
    private final BattlePokemon expectedTarget;
    @NotNull
    private CompletableFuture<?> future;
    @NotNull
    private Set<String> holds;
    @NotNull
    private final ResourceLocation id;

    public DamageInstruction(@NotNull InstructionSet instructionSet, @NotNull BattleActor actor, @NotNull BattleMessage publicMessage, @NotNull BattleMessage privateMessage) {
        Intrinsics.checkNotNullParameter((Object)instructionSet, (String)"instructionSet");
        Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
        Intrinsics.checkNotNullParameter((Object)publicMessage, (String)"publicMessage");
        Intrinsics.checkNotNullParameter((Object)privateMessage, (String)"privateMessage");
        this.instructionSet = instructionSet;
        this.actor = actor;
        this.publicMessage = publicMessage;
        this.privateMessage = privateMessage;
        this.expectedTarget = this.publicMessage.battlePokemon(0, this.actor.getBattle());
        CompletableFuture<Unit> completableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(completableFuture, (String)"completedFuture(Unit)");
        this.future = completableFuture;
        this.holds = new LinkedHashSet();
        this.id = MiscUtilsKt.cobblemonResource("damage");
    }

    @NotNull
    public final InstructionSet getInstructionSet() {
        return this.instructionSet;
    }

    @NotNull
    public final BattleActor getActor() {
        return this.actor;
    }

    @NotNull
    public final BattleMessage getPublicMessage() {
        return this.publicMessage;
    }

    @NotNull
    public final BattleMessage getPrivateMessage() {
        return this.privateMessage;
    }

    @Nullable
    public final BattlePokemon getExpectedTarget() {
        return this.expectedTarget;
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
        block4: {
            BattlePokemon source;
            String[] stringArray;
            Object object;
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            BattlePokemon battlePokemon = this.publicMessage.battlePokemon(0, this.actor.getBattle());
            if (battlePokemon == null) {
                return;
            }
            BattlePokemon battlePokemon2 = battlePokemon;
            String string = this.privateMessage.optionalArgument("from");
            boolean recoiling = string != null ? StringsKt.equals((String)string, (String)"recoil", (boolean)true) : false;
            CauserInstruction lastCauser = this.instructionSet.getMostRecentCauser(this);
            if (recoiling) {
                this.doRecoilEvoChecks(battlePokemon2);
                if (lastCauser instanceof MoveInstruction) {
                    battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(lastCauser){
                        final /* synthetic */ CauserInstruction $lastCauser;
                        {
                            this.$lastCauser = $lastCauser;
                            super(0);
                        }

                        @NotNull
                        public final DispatchResult invoke() {
                            return new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(this.$lastCauser){
                                final /* synthetic */ CauserInstruction $lastCauser;
                                {
                                    this.$lastCauser = $lastCauser;
                                    super(0);
                                }

                                @NotNull
                                public final Boolean invoke() {
                                    return !((MoveInstruction)this.$lastCauser).getHolds().contains("recoil");
                                }
                            }));
                        }
                    }));
                }
            }
            if ((object = this.privateMessage.argumentAt(1)) == null || (object = StringsKt.split$default((CharSequence)((CharSequence)object), (String[])(stringArray = new String[]{" "}), (boolean)false, (int)0, (int)6, null)) == null || (object = (String)object.get(0)) == null) {
                return;
            }
            Object newHealth = object;
            Effect effect = BattleMessage.effect$default(this.privateMessage, null, 1, null);
            BattlePokemon battlePokemon3 = source = BattleMessage.battlePokemonFromOptional$default(this.privateMessage, battle2, null, 2, null);
            if (battlePokemon3 == null) break block4;
            BattlePokemon it = battlePokemon3;
            boolean bl = false;
            ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle2, effect, it);
        }
    }

    private final void doRecoilEvoChecks(BattlePokemon battlePokemon) {
        Pokemon pokemon = battlePokemon.getEffectedPokemon();
        boolean bl = false;
        if (RecoilEvolutionProgress.Companion.supports(pokemon)) {
            String string = this.privateMessage.argumentAt(1);
            if (string == null) {
                throw new UnsupportedOperationException("Cant get recoil string");
            }
            String healthStr = string;
            Object object = Regex.find$default((Regex)new Regex("([0-9]+).*"), (CharSequence)healthStr, (int)0, (int)2, null);
            if (object == null || (object = object.getGroups()) == null || (object = object.get(1)) == null || (object = object.getValue()) == null || (object = StringsKt.toIntOrNull((String)object)) == null) {
                throw new UnsupportedOperationException("Cant get recoil string");
            }
            int newHealth = (Integer)object;
            int difference = pokemon.getCurrentHealth() - newHealth;
            if (difference > 0) {
                RecoilEvolutionProgress progress2 = (RecoilEvolutionProgress)pokemon.getEvolutionProxy().current().progressFirstOrCreate(doRecoilEvoChecks.1.progress.1.INSTANCE, doRecoilEvoChecks.1.progress.2.INSTANCE);
                progress2.updateProgress(new RecoilEvolutionProgress.Progress(progress2.currentProgress().getRecoil() + difference));
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void runActionEffect(@NotNull PokemonBattle battle2, @NotNull MoLangRuntime runtime2) {
        Status status;
        Ref.ObjectRef status2;
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Effect effect = BattleMessage.effect$default(this.privateMessage, null, 1, null);
        BattlePokemon battlePokemon = this.publicMessage.battlePokemon(0, this.actor.getBattle());
        if (battlePokemon == null) {
            return;
        }
        BattlePokemon battlePokemon2 = battlePokemon;
        Ref.ObjectRef objectRef = status2 = new Ref.ObjectRef();
        Object object = effect;
        if (object != null && (object = object.getId()) != null) {
            void it;
            Object object2 = object;
            Ref.ObjectRef objectRef2 = objectRef;
            boolean bl = false;
            status = Statuses.INSTANCE.getStatus((String)it);
            objectRef = objectRef2;
        } else {
            status = null;
        }
        objectRef.element = status;
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this, battle2, (Ref.ObjectRef<Status>)status2, battlePokemon2, runtime2){
            final /* synthetic */ DamageInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ Ref.ObjectRef<Status> $status;
            final /* synthetic */ BattlePokemon $battlePokemon;
            final /* synthetic */ MoLangRuntime $runtime;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                this.$status = $status;
                this.$battlePokemon = $battlePokemon;
                this.$runtime = $runtime;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                Object object;
                BattlePokemon battlePokemon = this.this$0.getPrivateMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return DispatchResultKt.getGO();
                }
                BattlePokemon pokemon = battlePokemon;
                if (this.$status.element instanceof PoisonStatus) {
                    Object object2 = pokemon.getEffectedPokemon().getStatus();
                    Object object3 = this.$status.element = object2 != null && (object2 = ((PersistentStatusContainer)object2).getStatus()) != null ? (Status)object2 : (Status)this.$status.element;
                }
                if ((object = (Status)this.$status.element) == null || (object = ((Status)object).getActionEffect()) == null) {
                    return DispatchResultKt.getGO();
                }
                Object actionEffect = object;
                Object[] objectArray = new Object[]{this.$battle};
                List providers = CollectionsKt.mutableListOf((Object[])objectArray);
                PokemonEntity pokemonEntity = this.$battlePokemon.getEffectedPokemon().getEntity();
                if (pokemonEntity != null) {
                    PokemonEntity it = pokemonEntity;
                    boolean bl = false;
                    Entity[] entityArray = new Entity[]{it};
                    UsersProvider p0 = new UsersProvider(entityArray);
                    boolean bl2 = false;
                    providers.add(p0);
                }
                ActionEffectContext context = new ActionEffectContext((ActionEffectTimeline)actionEffect, null, providers, this.$runtime, false, false, null, 114, null);
                this.this$0.setFuture(((ActionEffectTimeline)actionEffect).run(context));
                this.this$0.setHolds(context.getHolds());
                this.this$0.getFuture().thenApply(arg_0 -> runActionEffect.1.invoke$lambda$1(this.this$0, arg_0));
                return DispatchResultKt.getGO();
            }

            private static final Unit invoke$lambda$1(DamageInstruction this$0, Object it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                this$0.getHolds().clear();
                return Unit.INSTANCE;
            }
        }));
    }

    @Override
    public void postActionEffect(@NotNull PokemonBattle battle2) {
        String[] stringArray;
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Object object = this.privateMessage.argumentAt(1);
        if (object == null || (object = StringsKt.split$default((CharSequence)((CharSequence)object), (String[])(stringArray = new String[]{" "}), (boolean)false, (int)0, (int)6, null)) == null || (object = (String)object.get(0)) == null) {
            return;
        }
        Object newHealth = object;
        BattlePokemon battlePokemon = this.publicMessage.battlePokemon(0, this.actor.getBattle());
        if (battlePokemon == null) {
            return;
        }
        BattlePokemon battlePokemon2 = battlePokemon;
        Ref.BooleanRef causedFaint = new Ref.BooleanRef();
        causedFaint.element = Intrinsics.areEqual((Object)newHealth, (Object)"0");
        Effect effect = BattleMessage.effect$default(this.privateMessage, null, 1, null);
        BattlePokemon source = BattleMessage.battlePokemonFromOptional$default(this.privateMessage, battle2, null, 2, null);
        CauserInstruction lastCauser = this.instructionSet.getMostRecentCauser(this);
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(battlePokemon2, causedFaint, (String)newHealth, effect, source, battle2, this, lastCauser){
            final /* synthetic */ BattlePokemon $battlePokemon;
            final /* synthetic */ Ref.BooleanRef $causedFaint;
            final /* synthetic */ String $newHealth;
            final /* synthetic */ Effect $effect;
            final /* synthetic */ BattlePokemon $source;
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ DamageInstruction this$0;
            final /* synthetic */ CauserInstruction $lastCauser;
            {
                this.$battlePokemon = $battlePokemon;
                this.$causedFaint = $causedFaint;
                this.$newHealth = $newHealth;
                this.$effect = $effect;
                this.$source = $source;
                this.$battle = $battle;
                this.this$0 = $receiver;
                this.$lastCauser = $lastCauser;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                Object[] objectArray;
                int remainingHealth;
                float newHealthRatio;
                block34: {
                    MutableComponent mutableComponent;
                    ResourceKey resourceKey;
                    double d;
                    double d2;
                    double d3;
                    NetworkPacket<RunPosableMoLangPacket> pkt;
                    MutableComponent pokemonName = this.$battlePokemon.getName();
                    PokemonEntity pokemonEntity = this.$battlePokemon.getEntity();
                    if (!this.$causedFaint.element && pokemonEntity != null) {
                        pkt = new PlayPoseableAnimationPacket(pokemonEntity.m_19879_(), SetsKt.setOf((Object)"recoil"), SetsKt.emptySet());
                        d3 = pokemonEntity.m_20185_();
                        d2 = pokemonEntity.m_20186_();
                        d = pokemonEntity.m_20189_();
                        resourceKey = pokemonEntity.m_9236_().m_46472_();
                        NetworkPacket networkPacket = pkt;
                        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"registryKey");
                        NetworkPacket.DefaultImpls.sendToPlayersAround$default(networkPacket, d3, d2, d, 50.0, resourceKey, null, 32, null);
                    }
                    if (pokemonEntity != null) {
                        pkt = new RunPosableMoLangPacket(pokemonEntity.m_19879_(), SetsKt.setOf((Object)"q.particle('cobblemon:hit', 'target')"));
                        d3 = pokemonEntity.m_20185_();
                        d2 = pokemonEntity.m_20186_();
                        d = pokemonEntity.m_20189_();
                        resourceKey = pokemonEntity.m_9236_().m_46472_();
                        NetworkPacket networkPacket = pkt;
                        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"registryKey");
                        NetworkPacket.DefaultImpls.sendToPlayersAround$default(networkPacket, d3, d2, d, 50.0, resourceKey, null, 32, null);
                    }
                    newHealthRatio = 0.0f;
                    String[] stringArray = new String[]{"/"};
                    remainingHealth = Integer.parseInt((String)StringsKt.split$default((CharSequence)this.$newHealth, (String[])stringArray, (boolean)false, (int)0, (int)6, null).get(0));
                    if (this.$effect == null) break block34;
                    switch (this.$effect.getId()) {
                        case "stickybarb": 
                        case "blacksludge": {
                            objectArray = new Object[]{pokemonName, this.$effect.getTypelessData()};
                            mutableComponent = LocalizationUtilsKt.battleLang("damage.item", objectArray);
                            break;
                        }
                        case "psn": 
                        case "tox": 
                        case "brn": {
                            Status status = Statuses.INSTANCE.getStatus(this.$effect.getId());
                            String string = status != null && (status = status.getName()) != null ? status.m_135815_() : null;
                            if (string == null) {
                                return DispatchResultKt.getGO();
                            }
                            String status2 = string;
                            Object[] objectArray2 = new Object[]{pokemonName};
                            mutableComponent = LocalizationUtilsKt.lang("status." + status2 + ".hurt", objectArray2);
                            break;
                        }
                        case "aftermath": {
                            objectArray = new Object[]{pokemonName};
                            mutableComponent = LocalizationUtilsKt.battleLang("damage.generic", objectArray);
                            break;
                        }
                        case "chloroblast": 
                        case "steelbeam": {
                            objectArray = new Object[]{pokemonName};
                            mutableComponent = LocalizationUtilsKt.battleLang("damage.mindblown", objectArray);
                            break;
                        }
                        case "jumpkick": {
                            objectArray = new Object[]{pokemonName};
                            mutableComponent = LocalizationUtilsKt.battleLang("damage.highjumpkick", objectArray);
                            break;
                        }
                        default: {
                            String string = "damage." + this.$effect.getId();
                            objectArray = new Object[2];
                            objectArray[0] = pokemonName;
                            BattlePokemon battlePokemon = this.$source;
                            if (battlePokemon == null || (battlePokemon = battlePokemon.getName()) == null) {
                                battlePokemon = Component.m_237113_((String)"UNKOWN");
                            }
                            Intrinsics.checkNotNullExpressionValue((Object)battlePokemon, (String)"source?.getName() ?: Text.literal(\"UNKOWN\")");
                            objectArray[1] = battlePokemon;
                            mutableComponent = LocalizationUtilsKt.battleLang(string, objectArray);
                        }
                    }
                    MutableComponent lang = mutableComponent;
                    Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                    this.$battle.broadcastChatMessage((Component)TextKt.red(lang));
                }
                if (this.$causedFaint.element) {
                    newHealthRatio = 0.0f;
                    this.$battle.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this.$battlePokemon){
                        final /* synthetic */ BattlePokemon $battlePokemon;
                        {
                            this.$battlePokemon = $battlePokemon;
                            super(0);
                        }

                        @NotNull
                        public final DispatchResult invoke() {
                            this.$battlePokemon.getEffectedPokemon().setCurrentHealth(0);
                            this.$battlePokemon.sendUpdate();
                            return DispatchResultKt.getGO();
                        }
                    }));
                    this.$causedFaint.element = true;
                } else {
                    String[] stringArray = new String[]{"/"};
                    int maxHealth = Integer.parseInt((String)StringsKt.split$default((CharSequence)this.$newHealth, (String[])stringArray, (boolean)false, (int)0, (int)6, null).get(1));
                    int difference = maxHealth - remainingHealth;
                    newHealthRatio = (float)remainingHealth / (float)maxHealth;
                    this.$battle.dispatchToFront((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this.$battlePokemon, remainingHealth, difference){
                        final /* synthetic */ BattlePokemon $battlePokemon;
                        final /* synthetic */ int $remainingHealth;
                        final /* synthetic */ int $difference;
                        {
                            this.$battlePokemon = $battlePokemon;
                            this.$remainingHealth = $remainingHealth;
                            this.$difference = $difference;
                            super(0);
                        }

                        @NotNull
                        public final DispatchResult invoke() {
                            this.$battlePokemon.getEffectedPokemon().setCurrentHealth(this.$remainingHealth);
                            if (this.$difference > 0) {
                                Pokemon pokemon = this.$battlePokemon.getEffectedPokemon();
                                int n = this.$difference;
                                Pokemon pokemon2 = pokemon;
                                boolean bl = false;
                                if (DamageTakenEvolutionProgress.Companion.supports(pokemon2)) {
                                    DamageTakenEvolutionProgress progress2 = (DamageTakenEvolutionProgress)pokemon2.getEvolutionProxy().current().progressFirstOrCreate(postActionEffect.1.progress.1.INSTANCE, postActionEffect.1.progress.2.INSTANCE);
                                    progress2.updateProgress(new DamageTakenEvolutionProgress.Progress(progress2.currentProgress().getAmount() + n));
                                }
                            }
                            this.$battlePokemon.sendUpdate();
                            return DispatchResultKt.getGO();
                        }
                    }));
                }
                Pair<String, String> pair = this.this$0.getPrivateMessage().pnxAndUuid(0);
                if (pair != null) {
                    Pair<String, String> pair2 = pair;
                    objectArray = this.$battle;
                    DamageInstruction damageInstruction = this.this$0;
                    Pair<String, String> pair3 = pair2;
                    boolean bl = false;
                    String pnx = (String)pair3.component1();
                    PokemonBattle.sendSidedUpdate$default((PokemonBattle)objectArray, damageInstruction.getActor(), new BattleHealthChangePacket(pnx, remainingHealth, null, 4, null), new BattleHealthChangePacket(pnx, newHealthRatio, null, 4, null), false, 8, null);
                }
                ((Map)this.$battle.getMinorBattleActions()).put(this.$battlePokemon.getUuid(), this.this$0.getPrivateMessage());
                return this.$lastCauser instanceof MoveInstruction && ((MoveInstruction)this.$lastCauser).getActionEffect() != null && !this.$causedFaint.element ? (DispatchResult)new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(this.$lastCauser){
                    final /* synthetic */ CauserInstruction $lastCauser;
                    {
                        this.$lastCauser = $lastCauser;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return ((MoveInstruction)this.$lastCauser).getFuture().isDone();
                    }
                })) : (this.$causedFaint.element ? DispatchResultKt.getGO() : (DispatchResult)new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(this.this$0){
                    final /* synthetic */ DamageInstruction this$0;
                    {
                        this.this$0 = $receiver;
                        super(0);
                    }

                    @NotNull
                    public final Boolean invoke() {
                        return !this.this$0.getHolds().contains("effects");
                    }
                })));
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
}

