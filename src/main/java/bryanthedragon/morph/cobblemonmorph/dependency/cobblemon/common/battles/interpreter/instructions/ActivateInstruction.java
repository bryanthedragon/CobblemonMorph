/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.UsersProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownInterpreter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.ActionEffectInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.CauserInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResultKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.ActivateInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010'\u001a\u00020&\u00a2\u0006\u0004\b+\u0010,J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007J\u001f\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR&\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\r8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R(\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00148\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001d\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u0017\u0010'\u001a\u00020&8\u0006\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/ActivateInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/ActionEffectInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/CauserInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "postActionEffect", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "preActionEffect", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "runActionEffect", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/bedrockk/molang/runtime/MoLangRuntime;)V", "Ljava/util/concurrent/CompletableFuture;", "future", "Ljava/util/concurrent/CompletableFuture;", "getFuture", "()Ljava/util/concurrent/CompletableFuture;", "setFuture", "(Ljava/util/concurrent/CompletableFuture;)V", "", "", "holds", "Ljava/util/Set;", "getHolds", "()Ljava/util/Set;", "setHolds", "(Ljava/util/Set;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "instructionSet", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "getInstructionSet", "()Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "message", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "<init>", "(Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "common"})
public final class ActivateInstruction
implements ActionEffectInstruction,
CauserInstruction {
    @NotNull
    private final InstructionSet instructionSet;
    @NotNull
    private final BattleMessage message;
    @NotNull
    private CompletableFuture<?> future;
    @NotNull
    private Set<String> holds;
    @NotNull
    private final ResourceLocation id;

    public ActivateInstruction(@NotNull InstructionSet instructionSet, @NotNull BattleMessage message) {
        Intrinsics.checkNotNullParameter((Object)instructionSet, (String)"instructionSet");
        Intrinsics.checkNotNullParameter((Object)message, (String)"message");
        this.instructionSet = instructionSet;
        this.message = message;
        CompletableFuture<Unit> completableFuture = CompletableFuture.completedFuture(Unit.INSTANCE);
        Intrinsics.checkNotNullExpressionValue(completableFuture, (String)"completedFuture(Unit)");
        this.future = completableFuture;
        this.holds = new LinkedHashSet();
        this.id = MiscUtils.cobblemonResource("activate");
    }

    @NotNull
    public final InstructionSet getInstructionSet() {
        return this.instructionSet;
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
        BattlePokemon battlePokemon = this.message.battlePokemon(0, battle2);
        if (battlePokemon == null) {
            return;
        }
        BattlePokemon pokemon = battlePokemon;
        Effect effect = this.message.effectAt(1);
        if (effect == null) {
            return;
        }
        Effect effect2 = effect;
        ShowdownInterpreter.INSTANCE.broadcastOptionalAbility(battle2, effect2, pokemon);
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(battle2, this, pokemon){
            final /* synthetic */ PokemonBattle $battle;
            final /* synthetic */ ActivateInstruction this$0;
            final /* synthetic */ BattlePokemon $pokemon;
            {
                this.$battle = $battle;
                this.this$0 = $receiver;
                this.$pokemon = $pokemon;
                super(0);
            }

            @NotNull
            public final DispatchResult invoke() {
                Map<UUID, BattleMessage> map = ShowdownInterpreter.INSTANCE.getLastCauser();
                UUID uUID = this.$battle.getBattleId();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"battle.battleId");
                UUID uUID2 = uUID;
                BattleMessage battleMessage = this.this$0.getMessage();
                map.put(uUID2, battleMessage);
                ((Map)this.$battle.getMinorBattleActions()).put(this.$pokemon.getUuid(), this.this$0.getMessage());
                return DispatchResultKt.getGO();
            }
        }));
    }

    @Override
    public void runActionEffect(@NotNull PokemonBattle battle2, @NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        battle2.dispatch((Function0<? extends DispatchResult>)((Function0)new Function0<DispatchResult>(this, battle2, runtime2){
            final /* synthetic */ ActivateInstruction this$0;
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
                Effect effect = this.this$0.getMessage().effectAt(1);
                if (effect == null) {
                    return DispatchResultKt.getGO();
                }
                Effect effect2 = effect;
                Status status = Statuses.INSTANCE.getStatus(effect2.getId());
                Object object = status;
                if (object == null || (object = ((Status)object).getActionEffect()) == null) {
                    return DispatchResultKt.getGO();
                }
                Object actionEffect = object;
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
                ActionEffectContext context = new ActionEffectContext((ActionEffectTimeline)actionEffect, null, providers, this.$runtime, false, false, null, 114, null);
                this.this$0.setFuture(((ActionEffectTimeline)actionEffect).run(context));
                this.this$0.setHolds(context.getHolds());
                this.this$0.getFuture().thenApply(arg_0 -> runActionEffect.1.invoke$lambda$1(this.this$0, arg_0));
                return DispatchResultKt.getGO();
            }

            private static final Unit invoke$lambda$1(ActivateInstruction this$0, Object it) {
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
            final /* synthetic */ ActivateInstruction this$0;
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
            public final DispatchResult invoke() {
                MutableComponent mutableComponent;
                BattlePokemon battlePokemon = this.this$0.getMessage().battlePokemon(0, this.$battle);
                if (battlePokemon == null) {
                    return DispatchResultKt.getGO();
                }
                BattlePokemon pokemon = battlePokemon;
                Object object = this.this$0.getMessage().effectAt(2);
                if (object == null || (object = object.getTypelessData()) == null) {
                    object = Component.m_237113_((String)"UNKNOWN");
                }
                Object extraEffect = object;
                Effect effect = this.this$0.getMessage().effectAt(1);
                if (effect == null) {
                    return DispatchResultKt.getGO();
                }
                Effect effect2 = effect;
                MutableComponent pokemonName = pokemon.getName();
                BattlePokemon battlePokemon2 = BattleMessage.battlePokemonFromOptional$default(this.this$0.getMessage(), this.$battle, null, 2, null);
                if (battlePokemon2 == null || (battlePokemon2 = battlePokemon2.getName()) == null) {
                    battlePokemon2 = Component.m_237113_((String)"UNKNOWN");
                }
                BattlePokemon sourceName = battlePokemon2;
                switch (effect2.getId()) {
                    case "magnitude": {
                        Object object2 = this.this$0.getMessage().argumentAt(2);
                        Object[] objectArray = new Object[]{object2 != null && (object2 = StringsKt.toIntOrNull((String)object2)) != null ? (Integer)object2 : 1};
                        mutableComponent = LocalizationUtilsKt.battleLang("activate.magnitude", objectArray);
                        break;
                    }
                    case "eeriespell": 
                    case "spite": {
                        Object[] objectArray = new Object[3];
                        objectArray[0] = pokemonName;
                        Intrinsics.checkNotNullExpressionValue((Object)extraEffect, (String)"extraEffect");
                        objectArray[1] = extraEffect;
                        Intrinsics.checkNotNull((Object)this.this$0.getMessage().argumentAt(3));
                        mutableComponent = LocalizationUtilsKt.battleLang("activate.spite", objectArray);
                        break;
                    }
                    case "toxicdebris": 
                    case "shedskin": {
                        return DispatchResultKt.getGO();
                    }
                    case "destinybond": {
                        void $this$forEach$iv;
                        void $this$mapNotNullTo$iv$iv;
                        Iterable $this$mapNotNull$iv = this.$battle.getActivePokemon();
                        boolean $i$f$mapNotNull = false;
                        Object object3 = $this$mapNotNull$iv;
                        Collection destination$iv$iv = new ArrayList<E>();
                        boolean $i$f$mapNotNullTo = false;
                        void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                        boolean $i$f$forEach = false;
                        Iterator<T> iterator = $this$forEach$iv$iv$iv.iterator();
                        while (iterator.hasNext()) {
                            UUID it$iv$iv;
                            T element$iv$iv$iv;
                            T element$iv$iv = element$iv$iv$iv = iterator.next();
                            boolean bl = false;
                            ActiveBattlePokemon it = (ActiveBattlePokemon)element$iv$iv;
                            boolean bl2 = false;
                            BattlePokemon battlePokemon3 = it.getBattlePokemon();
                            if ((battlePokemon3 != null ? battlePokemon3.getUuid() : null) == null) continue;
                            it$iv$iv = it$iv$iv;
                            boolean bl3 = false;
                            destination$iv$iv.add(it$iv$iv);
                        }
                        $this$mapNotNull$iv = (List)destination$iv$iv;
                        PokemonBattle pokemonBattle = this.$battle;
                        object3 = this.this$0;
                        boolean $i$f$forEach2 = false;
                        for (T element$iv : $this$forEach$iv) {
                            UUID it = (UUID)element$iv;
                            boolean bl = false;
                            ((Map)pokemonBattle.getMinorBattleActions()).put(it, ((ActivateInstruction)object3).getMessage());
                        }
                        Object[] objectArray = new Object[]{pokemonName};
                        mutableComponent = LocalizationUtilsKt.battleLang("activate.destinybond", objectArray);
                        break;
                    }
                    case "focusband": 
                    case "focussash": {
                        Object[] objectArray = new Object[]{pokemonName, effect2.getTypelessData()};
                        mutableComponent = LocalizationUtilsKt.battleLang("activate.focusband", objectArray);
                        break;
                    }
                    case "maxguard": 
                    case "protect": {
                        Object[] objectArray = new Object[]{pokemonName};
                        mutableComponent = LocalizationUtilsKt.battleLang("activate.protect", objectArray);
                        break;
                    }
                    case "shadowforce": 
                    case "hyperspacehole": 
                    case "hyperspacefury": {
                        Object[] objectArray = new Object[]{pokemonName};
                        mutableComponent = LocalizationUtilsKt.battleLang("activate.phantomforce", objectArray);
                        break;
                    }
                    default: {
                        String string = "activate." + effect2.getId();
                        Object[] objectArray = new Object[3];
                        objectArray[0] = pokemonName;
                        Intrinsics.checkNotNullExpressionValue((Object)sourceName, (String)"sourceName");
                        objectArray[1] = sourceName;
                        Intrinsics.checkNotNullExpressionValue((Object)extraEffect, (String)"extraEffect");
                        objectArray[2] = extraEffect;
                        mutableComponent = LocalizationUtilsKt.battleLang(string, objectArray);
                    }
                }
                MutableComponent lang = mutableComponent;
                Intrinsics.checkNotNullExpressionValue((Object)lang, (String)"lang");
                this.$battle.broadcastChatMessage((Component)lang);
                return new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(this.this$0){
                    final /* synthetic */ ActivateInstruction this$0;
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

