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
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleMessage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.Effect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.EntityBackedBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.BattleDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.DispatchResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InstructionSet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.InterpreterInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.UntilDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.dispatch.WaitDispatch;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.SwitchInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.interpreter.instructions.TransformInstruction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.effects.IllusionEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleSwitchPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CollectionUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB'\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0016\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0013\u001a\u0004\b\u0017\u0010\u0015\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/SwitchInstruction;", "Lcom/cobblemon/mod/common/battles/dispatch/InterpreterInstruction;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "invoke", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "battleActor", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getBattleActor", "()Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "instructionSet", "Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "getInstructionSet", "()Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "privateMessage", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "getPrivateMessage", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;", "publicMessage", "getPublicMessage", "<init>", "(Lcom/cobblemon/mod/common/battles/dispatch/InstructionSet;Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleMessage;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSwitchInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SwitchInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/SwitchInstruction\n+ 2 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 InstructionSet.kt\ncom/cobblemon/mod/common/battles/dispatch/InstructionSet$getNextInstruction$1\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,156:1\n42#2,6:157\n800#3,11:163\n288#3:174\n289#3:176\n42#4:175\n4098#5,11:177\n1#6:188\n*S KotlinDebug\n*F\n+ 1 SwitchInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/SwitchInstruction\n*L\n43#1:157,6\n43#1:163,11\n43#1:174\n43#1:176\n43#1:175\n52#1:177,11\n*E\n"})
public final class SwitchInstruction
implements InterpreterInstruction {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final InstructionSet instructionSet;
    @NotNull
    private final BattleActor battleActor;
    @NotNull
    private final BattleMessage publicMessage;
    @NotNull
    private final BattleMessage privateMessage;

    public SwitchInstruction(@NotNull InstructionSet instructionSet, @NotNull BattleActor battleActor, @NotNull BattleMessage publicMessage, @NotNull BattleMessage privateMessage) {
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

    /*
     * WARNING - void declaration
     */
    @Override
    public void invoke(@NotNull PokemonBattle battle2) {
        block22: {
            BattlePokemon pokemon;
            BattlePokemon illusion;
            boolean imposter;
            LivingEntity entity2;
            ActiveBattlePokemon activePokemon;
            BattleActor actor;
            String pnx;
            block18: {
                PokemonEntity pokemonEntity;
                block19: {
                    IllusionEffect illusionEffect;
                    boolean bl;
                    EntityBackedBattleActor entityBackedBattleActor;
                    Object $this$filterIsInstance$iv;
                    Iterable iterable;
                    int index$iv232;
                    block21: {
                        block20: {
                            Vec3 idealPos;
                            void $this$filterIsInstanceTo$iv$iv;
                            BattleActor[] $this$filterIsInstanceTo$iv$iv$iv;
                            boolean $i$f$filterIsInstanceTo;
                            boolean $i$f$filterIsInstance;
                            Object v1;
                            block17: {
                                Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
                                Pair<String, String> pair = this.publicMessage.pnxAndUuid(0);
                                if (pair == null) {
                                    return;
                                }
                                pnx = (String)pair.component1();
                                Pair<BattleActor, ActiveBattlePokemon> pair2 = battle2.getActorAndActiveSlotFromPNX(pnx);
                                actor = (BattleActor)pair2.component1();
                                activePokemon = (ActiveBattlePokemon)pair2.component2();
                                entity2 = actor instanceof EntityBackedBattleActor ? (LivingEntity)((EntityBackedBattleActor)((Object)actor)).getEntity() : null;
                                InstructionSet $this$iv = this.instructionSet;
                                boolean $i$f$getNextInstruction = false;
                                index$iv232 = $this$iv.getInstructions().indexOf(this);
                                if (Intrinsics.areEqual((Object)CollectionsKt.last($this$iv.getInstructions()), (Object)this)) {
                                    v1 = null;
                                } else {
                                    void $this$firstOrNull$iv$iv;
                                    Iterable $this$filterIsInstance$iv$iv = $this$iv.getInstructions().subList(index$iv232 + 1, $this$iv.getInstructions().size());
                                    $i$f$filterIsInstance = false;
                                    iterable = $this$filterIsInstance$iv$iv;
                                    Collection destination$iv$iv$iv = new ArrayList();
                                    $i$f$filterIsInstanceTo = false;
                                    for (Object t : $this$filterIsInstanceTo$iv$iv$iv) {
                                        if (!(t instanceof TransformInstruction)) continue;
                                        destination$iv$iv$iv.add(t);
                                    }
                                    $this$filterIsInstance$iv$iv = (List)destination$iv$iv$iv;
                                    boolean $i$f$firstOrNull = false;
                                    $this$filterIsInstanceTo$iv$iv$iv = $this$firstOrNull$iv$iv.iterator();
                                    while ($this$filterIsInstanceTo$iv$iv$iv.hasNext()) {
                                        Object element$iv$iv;
                                        Object t = element$iv$iv = $this$filterIsInstanceTo$iv$iv$iv.next();
                                        boolean bl2 = false;
                                        Object it = t;
                                        if (!true) continue;
                                        v1 = element$iv$iv;
                                        break block17;
                                    }
                                    v1 = null;
                                }
                            }
                            TransformInstruction transformInstruction = v1;
                            imposter = (transformInstruction != null ? transformInstruction.getExpectedTarget() : null) != null;
                            illusion = this.publicMessage.battlePokemonFromOptional(battle2, "is");
                            BattlePokemon battlePokemon = this.publicMessage.battlePokemon(0, battle2);
                            if (battlePokemon == null) {
                                return;
                            }
                            pokemon = battlePokemon;
                            if (battle2.getStarted()) break block18;
                            activePokemon.setBattlePokemon(pokemon);
                            activePokemon.setIllusion(illusion);
                            pokemonEntity = pokemon.getEntity();
                            if (pokemonEntity != null || entity2 == null) break block19;
                            $this$filterIsInstance$iv = this.battleActor.getSide().getOppositeSide().getActors();
                            $i$f$filterIsInstance = false;
                            $this$filterIsInstanceTo$iv$iv$iv = $this$filterIsInstance$iv;
                            Collection destination$iv$iv = new ArrayList();
                            $i$f$filterIsInstanceTo = false;
                            for (void element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                                if (!(element$iv$iv instanceof EntityBackedBattleActor)) continue;
                                destination$iv$iv.add(element$iv$iv);
                            }
                            entityBackedBattleActor = (EntityBackedBattleActor)CollectionsKt.firstOrNull((List)((List)destination$iv$iv));
                            if (entityBackedBattleActor == null || (entityBackedBattleActor = entityBackedBattleActor.getEntity()) == null || (entityBackedBattleActor = entityBackedBattleActor.m_20182_()) == null) break block20;
                            EntityBackedBattleActor pos = entityBackedBattleActor;
                            bl = false;
                            Vec3 vec3 = pos.m_82546_(entity2.m_20182_());
                            Vec3 vec32 = idealPos = entity2.m_20182_().m_82549_(vec3.m_82490_(0.33));
                            entityBackedBattleActor = vec32;
                            if (vec32 != null) break block21;
                        }
                        entityBackedBattleActor = entity2.m_20182_();
                    }
                    EntityBackedBattleActor targetPos = entityBackedBattleActor;
                    index$iv232 = actor.getStillSendingOutCount();
                    actor.setStillSendingOutCount(index$iv232 + 1);
                    Pokemon index$iv232 = pokemon.getEffectedPokemon();
                    $this$filterIsInstance$iv = battle2.getBattleId();
                    Level level = entity2.m_9236_();
                    Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
                    ServerLevel serverLevel = (ServerLevel)level;
                    BattlePokemon battlePokemon = illusion;
                    if (battlePokemon != null) {
                        BattlePokemon it = battlePokemon;
                        bl = false;
                        illusionEffect = new IllusionEffect(it.getEffectedPokemon());
                    } else {
                        illusionEffect = null;
                    }
                    iterable = illusionEffect;
                    Intrinsics.checkNotNullExpressionValue((Object)targetPos, (String)"targetPos");
                    Pokemon.sendOutWithAnimation$default(index$iv232, entity2, serverLevel, (Vec3)targetPos, (UUID)$this$filterIsInstance$iv, false, (IllusionEffect)((Object)iterable), null, 64, null).thenApply(arg_0 -> SwitchInstruction.invoke$lambda$2((Function1)new Function1<PokemonEntity, Integer>(actor){
                        final /* synthetic */ BattleActor $actor;
                        {
                            this.$actor = $actor;
                            super(1);
                        }

                        public final Integer invoke(PokemonEntity it) {
                            BattleActor battleActor = this.$actor;
                            int n = battleActor.getStillSendingOutCount();
                            battleActor.setStillSendingOutCount(n + -1);
                            return n;
                        }
                    }, arg_0));
                    break block22;
                }
                if (pokemonEntity != null) {
                    BattlePokemon battlePokemon = illusion;
                    if (battlePokemon != null) {
                        BattlePokemon it = battlePokemon;
                        boolean bl = false;
                        new IllusionEffect(it.getEffectedPokemon()).start(pokemonEntity);
                    }
                }
                break block22;
            }
            battle2.dispatchInsert((Function0<? extends Iterable<? extends BattleDispatch>>)((Function0)new Function0<Iterable<? extends BattleDispatch>>(pokemon, activePokemon, battle2, this, entity2, actor, pnx, illusion, imposter){
                final /* synthetic */ BattlePokemon $pokemon;
                final /* synthetic */ ActiveBattlePokemon $activePokemon;
                final /* synthetic */ PokemonBattle $battle;
                final /* synthetic */ SwitchInstruction this$0;
                final /* synthetic */ LivingEntity $entity;
                final /* synthetic */ BattleActor $actor;
                final /* synthetic */ String $pnx;
                final /* synthetic */ BattlePokemon $illusion;
                final /* synthetic */ boolean $imposter;
                {
                    this.$pokemon = $pokemon;
                    this.$activePokemon = $activePokemon;
                    this.$battle = $battle;
                    this.this$0 = $receiver;
                    this.$entity = $entity;
                    this.$actor = $actor;
                    this.$pnx = $pnx;
                    this.$illusion = $illusion;
                    this.$imposter = $imposter;
                    super(0);
                }

                @NotNull
                public final Iterable<BattleDispatch> invoke() {
                    this.$pokemon.sendUpdate();
                    if (Intrinsics.areEqual((Object)this.$activePokemon.getBattlePokemon(), (Object)this.$pokemon)) {
                        return SetsKt.emptySet();
                    }
                    BattlePokemon battlePokemon = this.$activePokemon.getBattlePokemon();
                    if (battlePokemon != null) {
                        BattleContext.Type[] typeArray;
                        BattlePokemon battlePokemon2 = battlePokemon;
                        SwitchInstruction switchInstruction = this.this$0;
                        BattlePokemon battlePokemon3 = this.$pokemon;
                        PokemonBattle pokemonBattle = this.$battle;
                        BattlePokemon oldPokemon = battlePokemon2;
                        boolean bl = false;
                        Effect effect = BattleMessage.effect$default(switchInstruction.getPublicMessage(), null, 1, null);
                        if (Intrinsics.areEqual((Object)(effect != null ? effect.getId() : null), (Object)"batonpass")) {
                            typeArray = new BattleContext.Type[]{BattleContext.Type.BOOST, BattleContext.Type.UNBOOST};
                            oldPokemon.getContextManager().swap(battlePokemon3.getContextManager(), typeArray);
                        }
                        typeArray = new BattleContext.Type[]{BattleContext.Type.VOLATILE, BattleContext.Type.BOOST, BattleContext.Type.UNBOOST};
                        oldPokemon.getContextManager().clear(typeArray);
                        ((Map)pokemonBattle.getMajorBattleActions()).put(oldPokemon.getUuid(), switchInstruction.getPublicMessage());
                    }
                    ((Map)this.$battle.getMajorBattleActions()).put(this.$pokemon.getUuid(), this.this$0.getPublicMessage());
                    return SetsKt.setOf(arg_0 -> invoke.4.invoke$lambda$1(this.$entity, this.$battle, this.$actor, this.$pnx, this.$activePokemon, this.$pokemon, this.$illusion, this.$imposter, arg_0));
                }

                private static final DispatchResult invoke$lambda$1(LivingEntity $entity, PokemonBattle $battle, BattleActor $actor, String $pnx, ActiveBattlePokemon $activePokemon, BattlePokemon $pokemon, BattlePokemon $illusion, boolean $imposter, PokemonBattle it) {
                    Intrinsics.checkNotNullParameter((Object)$battle, (String)"$battle");
                    Intrinsics.checkNotNullParameter((Object)$actor, (String)"$actor");
                    Intrinsics.checkNotNullParameter((Object)$pnx, (String)"$pnx");
                    Intrinsics.checkNotNullParameter((Object)$activePokemon, (String)"$activePokemon");
                    Intrinsics.checkNotNullParameter((Object)$pokemon, (String)"$pokemon");
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    return $entity != null ? SwitchInstruction.Companion.createEntitySwitch($battle, $actor, $entity, $pnx, $activePokemon, $pokemon, $illusion, $imposter) : SwitchInstruction.Companion.createNonEntitySwitch($battle, $actor, $pnx, $activePokemon, $pokemon, $illusion);
                }
            }));
        }
    }

    private static final Integer invoke$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Integer)$tmp0.invoke(p0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017JS\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013JA\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/battles/interpreter/instructions/SwitchInstruction$Companion;", "", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "actor", "Lnet/minecraft/world/entity/LivingEntity;", "entity", "", "pnx", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "activePokemon", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "newPokemon", "illusion", "", "imposter", "Lcom/cobblemon/mod/common/battles/dispatch/DispatchResult;", "createEntitySwitch", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Lnet/minecraft/world/entity/LivingEntity;Ljava/lang/String;Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Z)Lcom/cobblemon/mod/common/battles/dispatch/DispatchResult;", "createNonEntitySwitch", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;Ljava/lang/String;Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Lcom/cobblemon/mod/common/battles/dispatch/DispatchResult;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nSwitchInstruction.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SwitchInstruction.kt\ncom/cobblemon/mod/common/battles/interpreter/instructions/SwitchInstruction$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,156:1\n1#2:157\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final DispatchResult createEntitySwitch(@NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull LivingEntity entity2, @NotNull String pnx, @NotNull ActiveBattlePokemon activePokemon, @NotNull BattlePokemon newPokemon, @Nullable BattlePokemon illusion, boolean imposter) {
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            Intrinsics.checkNotNullParameter((Object)pnx, (String)"pnx");
            Intrinsics.checkNotNullParameter((Object)activePokemon, (String)"activePokemon");
            Intrinsics.checkNotNullParameter((Object)newPokemon, (String)"newPokemon");
            BattlePokemon battlePokemon = activePokemon.getBattlePokemon();
            PokemonEntity pokemonEntity = battlePokemon != null ? battlePokemon.getEntity() : null;
            CompletableFuture<Unit> sendOutFuture = new CompletableFuture<Unit>();
            boolean doCry = illusion == null && !imposter;
            Object object = pokemonEntity;
            if (object == null || (object = ((PokemonEntity)object).recallWithAnimation()) == null) {
                object = CompletableFuture.completedFuture(Unit.INSTANCE);
            }
            ((CompletableFuture)object).thenApply(arg_0 -> Companion.createEntitySwitch$lambda$3(actor, activePokemon, newPokemon, illusion, battle2, pnx, doCry, sendOutFuture, entity2, arg_0));
            return new UntilDispatch((Function0<Boolean>)((Function0)new Function0<Boolean>(sendOutFuture){
                final /* synthetic */ CompletableFuture<Unit> $sendOutFuture;
                {
                    this.$sendOutFuture = $sendOutFuture;
                    super(0);
                }

                @NotNull
                public final Boolean invoke() {
                    return this.$sendOutFuture.isDone();
                }
            }));
        }

        public static /* synthetic */ DispatchResult createEntitySwitch$default(Companion companion, PokemonBattle pokemonBattle, BattleActor battleActor, LivingEntity livingEntity, String string, ActiveBattlePokemon activeBattlePokemon, BattlePokemon battlePokemon, BattlePokemon battlePokemon2, boolean bl, int n, Object object) {
            if ((n & 0x40) != 0) {
                battlePokemon2 = null;
            }
            if ((n & 0x80) != 0) {
                bl = false;
            }
            return companion.createEntitySwitch(pokemonBattle, battleActor, livingEntity, string, activeBattlePokemon, battlePokemon, battlePokemon2, bl);
        }

        @NotNull
        public final DispatchResult createNonEntitySwitch(@NotNull PokemonBattle battle2, @NotNull BattleActor actor, @NotNull String pnx, @NotNull ActiveBattlePokemon activePokemon, @NotNull BattlePokemon newPokemon, @Nullable BattlePokemon illusion) {
            Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)pnx, (String)"pnx");
            Intrinsics.checkNotNullParameter((Object)activePokemon, (String)"activePokemon");
            Intrinsics.checkNotNullParameter((Object)newPokemon, (String)"newPokemon");
            CollectionUtilsKt.swap(actor.getPokemonList(), actor.getActivePokemon().indexOf(activePokemon), actor.getPokemonList().indexOf(newPokemon));
            activePokemon.setBattlePokemon(newPokemon);
            activePokemon.setIllusion(illusion);
            PokemonBattle.sendSidedUpdate$default(battle2, actor, new BattleSwitchPokemonPacket(pnx, newPokemon, true, illusion), new BattleSwitchPokemonPacket(pnx, newPokemon, false, illusion), false, 8, null);
            return new WaitDispatch(1.5f);
        }

        public static /* synthetic */ DispatchResult createNonEntitySwitch$default(Companion companion, PokemonBattle pokemonBattle, BattleActor battleActor, String string, ActiveBattlePokemon activeBattlePokemon, BattlePokemon battlePokemon, BattlePokemon battlePokemon2, int n, Object object) {
            if ((n & 0x20) != 0) {
                battlePokemon2 = null;
            }
            return companion.createNonEntitySwitch(pokemonBattle, battleActor, string, activeBattlePokemon, battlePokemon, battlePokemon2);
        }

        private static final void createEntitySwitch$lambda$3$lambda$2(Function1 $tmp0, Object p0) {
            Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
            $tmp0.invoke(p0);
        }

        private static final Object createEntitySwitch$lambda$3(BattleActor $actor, ActiveBattlePokemon $activePokemon, BattlePokemon $newPokemon, BattlePokemon $illusion, PokemonBattle $battle, String $pnx, boolean $doCry, CompletableFuture $sendOutFuture, LivingEntity $entity, Object it) {
            Object object;
            Intrinsics.checkNotNullParameter((Object)$actor, (String)"$actor");
            Intrinsics.checkNotNullParameter((Object)$activePokemon, (String)"$activePokemon");
            Intrinsics.checkNotNullParameter((Object)$newPokemon, (String)"$newPokemon");
            Intrinsics.checkNotNullParameter((Object)$battle, (String)"$battle");
            Intrinsics.checkNotNullParameter((Object)$pnx, (String)"$pnx");
            Intrinsics.checkNotNullParameter((Object)$sendOutFuture, (String)"$sendOutFuture");
            Intrinsics.checkNotNullParameter((Object)$entity, (String)"$entity");
            CollectionUtilsKt.swap($actor.getPokemonList(), $actor.getActivePokemon().indexOf($activePokemon), $actor.getPokemonList().indexOf($newPokemon));
            $activePokemon.setBattlePokemon($newPokemon);
            $activePokemon.setIllusion($illusion);
            PokemonBattle.sendSidedUpdate$default($battle, $actor, new BattleSwitchPokemonPacket($pnx, $newPokemon, true, $illusion), new BattleSwitchPokemonPacket($pnx, $newPokemon, false, $illusion), false, 8, null);
            if ($newPokemon.getEntity() != null) {
                BattlePokemon battlePokemon = $illusion;
                if (battlePokemon != null) {
                    BattlePokemon it2 = battlePokemon;
                    boolean bl = false;
                    IllusionEffect illusionEffect = new IllusionEffect(it2.getEffectedPokemon());
                    PokemonEntity pokemonEntity = $newPokemon.getEntity();
                    Intrinsics.checkNotNull((Object)pokemonEntity);
                    illusionEffect.start(pokemonEntity);
                }
                if ($doCry) {
                    PokemonEntity pokemonEntity = $newPokemon.getEntity();
                    if (pokemonEntity != null) {
                        pokemonEntity.cry();
                    }
                }
                object = $sendOutFuture.complete(Unit.INSTANCE);
            } else {
                IllusionEffect illusionEffect;
                ServerLevel serverLevel;
                ServerLevel world;
                ServerLevel lastPosition = $activePokemon.getPosition();
                ServerLevel serverLevel2 = lastPosition;
                if (serverLevel2 == null || (serverLevel2 = (ServerLevel)serverLevel2.getFirst()) == null) {
                    Level level = $entity.m_9236_();
                    Intrinsics.checkNotNull((Object)level, (String)"null cannot be cast to non-null type net.minecraft.server.world.ServerWorld");
                    serverLevel2 = world = (ServerLevel)level;
                }
                if ((serverLevel = lastPosition) == null || (serverLevel = (Vec3)serverLevel.getSecond()) == null) {
                    serverLevel = $entity.m_20182_();
                }
                ServerLevel pos = serverLevel;
                Pokemon pokemon = $newPokemon.getEffectedPokemon();
                UUID uUID = $battle.getBattleId();
                BattlePokemon battlePokemon = $illusion;
                if (battlePokemon != null) {
                    BattlePokemon it3 = battlePokemon;
                    boolean bl = false;
                    illusionEffect = new IllusionEffect(it3.getEffectedPokemon());
                } else {
                    illusionEffect = null;
                }
                IllusionEffect illusionEffect2 = illusionEffect;
                Intrinsics.checkNotNullExpressionValue((Object)pos, (String)"pos");
                object = Pokemon.sendOutWithAnimation$default(pokemon, $entity, world, (Vec3)pos, uUID, $doCry, illusionEffect2, null, 64, null).thenAccept(arg_0 -> Companion.createEntitySwitch$lambda$3$lambda$2((Function1)new Function1<PokemonEntity, Unit>((CompletableFuture<Unit>)$sendOutFuture){
                    final /* synthetic */ CompletableFuture<Unit> $sendOutFuture;
                    {
                        this.$sendOutFuture = $sendOutFuture;
                        super(1);
                    }

                    public final void invoke(PokemonEntity it) {
                        this.$sendOutFuture.complete(Unit.INSTANCE);
                    }
                }, arg_0));
            }
            return object;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

