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
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.ActionEffectInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.CantInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010!\u001a\u00020 \u00a2\u0006\u0004\b%\u0010&J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\u0006J\u001f\u0010\n\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bR&\u0010\r\u001a\u0006\u0012\u0002\b\u00030\f8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R(\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/CantInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/ActionEffectInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "postActionEffect", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "preActionEffect", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "runActionEffect", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/bedrockk/molang/runtime/MoLangRuntime;)V", "Ljava/util/concurrent/CompletableFuture;", "future", "Ljava/util/concurrent/CompletableFuture;", "getFuture", "()Ljava/util/concurrent/CompletableFuture;", "setFuture", "(Ljava/util/concurrent/CompletableFuture;)V", "", "", "holds", "Ljava/util/Set;", "getHolds", "()Ljava/util/Set;", "setHolds", "(Ljava/util/Set;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class CantInstruction
implements ActionEffectInstruction {
    @NotNull
    private final BattleMessage message;
    @NotNull
    private CompletableFuture<?> future;
    @NotNull
    private Set<String> holds;
    @NotNull
    private final ResourceLocation id;

    public CantInstruction(@NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.message = message;
        CompletableFuture<Unit> completableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(completableFuture, (String)"completedFuture(Unit)");
        this.future = completableFuture;
        this.holds = new LinkedHashSet();
        this.id = MiscUtilsKt.cobblemonResource("cant");
    }

    @NotNull
    public final BattleMessage getMessage() {
        return this.message;
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
            final /* synthetic */ CantInstruction this$0;
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
                BattlePokemon battlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return DispatchResultKt.getGO();
                }
                BattlePokemon pokemon = battlePokemon;
                Object object = this.this$0.getMessage().effectAt(1);
                if (object == null || (object = object.getId()) == null) {
                    return DispatchResultKt.getGO();
                }
                Object effectID = object;
                MutableComponent name = pokemon.getName();
                Status status = Statuses.INSTANCE.getStatus((String)effectID);
                Object object2 = status;
                if (object2 == null || (object2 = ((Status)object2).getActionEffect()) == null) {
                    return DispatchResultKt.getGO();
                }
                Object actionEffect = object2;
                Object[] objectArray = new Object[]{this.$battle};
                List providers = CollectionsKt.mutableListOf((Object[])objectArray);
                PokemonEntity pokemonEntity = pokemon.getEffectedPokemon().getEntity();
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

            private static final Unit invoke$lambda$1(CantInstruction this$0, Object it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                this$0.getHolds().clear();
                return Unit.INSTANCE;
            }
        }));
    }

    @Override
    public void postActionEffect(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this, battle2){
            final /* synthetic */ CantInstruction this$0;
            final /* synthetic */ PokemonBattle $battle;
            {
                this.this$0 = $receiver;
                this.$battle = $battle;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                MutableComponent mutableComponent;
                BattlePokemon battlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return DispatchResultKt.getGO();
                }
                BattlePokemon pokemon = battlePokemon;
                Object object = this.this$0.getMessage().effectAt(1);
                if (object == null || (object = object.getId()) == null) {
                    return DispatchResultKt.getGO();
                }
                Object effectID = object;
                MutableComponent name = pokemon.getName();
                MoveTemplate moveTemplate = this.this$0.getMessage().moveAt(2);
                if (moveTemplate == null || (moveTemplate = moveTemplate.getDisplayName()) == null) {
                    CantInstruction $this$invoke_u24lambda_u240 = this.this$0;
                    boolean bl = false;
                    moveTemplate = TextKt.text("(Unrecognized: " + $this$invoke_u24lambda_u240.getMessage().argumentAt(2) + ")");
                }
                MoveTemplate moveName = moveTemplate;
                switch (effectID) {
                    case "queenlymajesty": 
                    case "dazzling": 
                    case "armortail": 
                    case "damp": {
                        Object[] objectArray = new Object[]{name, moveName};
                        mutableComponent = LocalizationUtilsKt.battleLang("cant.generic", objectArray);
                        break;
                    }
                    case "par": 
                    case "slp": 
                    case "frz": {
                        Status status = Statuses.INSTANCE.getStatus((String)effectID);
                        String string = status != null && (status = status.getName()) != null ? status.m_135815_() : null;
                        if (string == null) {
                            return DispatchResultKt.getGO();
                        }
                        String status2 = string;
                        Object[] objectArray = new Object[]{name};
                        mutableComponent = LocalizationUtilsKt.lang("status." + status2 + ".is", objectArray);
                        break;
                    }
                    default: {
                        Object[] objectArray = new Object[]{name, moveName};
                        mutableComponent = LocalizationUtilsKt.battleLang("cant." + (String)effectID, objectArray);
                    }
                }
                MutableComponent lang = mutableComponent;
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)TextKt.red(lang));
                ((Map)this.$battle.getMinorBattleActions()).put(pokemon.getUuid(), this.this$0.getMessage());
                return new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(this.this$0){
                    final /* synthetic */ CantInstruction this$0;
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
}

