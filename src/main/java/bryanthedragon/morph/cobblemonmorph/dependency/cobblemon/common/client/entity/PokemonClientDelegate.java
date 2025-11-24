/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$ObjectRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.syncher.EntityDataAccessor
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.PokemonSideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.PrimaryAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.animation.StatefulAnimation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.frame.ModelFrame;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokemon.PokemonRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MovingSoundInstance;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Locale;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u0005\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 d2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001dB\u0007\u00a2\u0006\u0004\bc\u0010\nJ\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\r\u0010\t\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u000b\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\u0016\u001a\u00020\u00062\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u0014H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u0013J\u0017\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u001dH\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u000f\u0010!\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b!\u0010\nR\"\u0010#\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\"\u0010)\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\"\u0004\b-\u0010 R\"\u0010.\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010*\u001a\u0004\b/\u0010,\"\u0004\b0\u0010 R\"\u00102\u001a\u0002018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b2\u00103\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\"\u00108\u001a\u0002018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b8\u00103\u001a\u0004\b9\u00105\"\u0004\b:\u00107R\"\u0010<\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0002\b\u0003\u0018\u00010;8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b<\u0010=R\"\u0010>\u001a\u00020\u00028\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b>\u0010?\u001a\u0004\b@\u0010\f\"\u0004\bA\u0010\u0013R\"\u0010B\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bB\u0010*\u001a\u0004\bC\u0010,\"\u0004\bD\u0010 R$\u0010F\u001a\u0004\u0018\u00010E8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bF\u0010G\u001a\u0004\bH\u0010I\"\u0004\b\u001a\u0010JR\"\u0010K\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bK\u0010$\u001a\u0004\bL\u0010&\"\u0004\bM\u0010(R\"\u0010N\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bN\u0010$\u001a\u0004\bO\u0010&\"\u0004\bP\u0010(R\u0014\u0010T\u001a\u00020Q8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bR\u0010SR\u0011\u0010V\u001a\u00020\u001d8F\u00a2\u0006\u0006\u001a\u0004\bU\u0010,R\u0011\u0010X\u001a\u00020\u001d8F\u00a2\u0006\u0006\u001a\u0004\bW\u0010,R$\u0010Z\u001a\u0004\u0018\u00010Y8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bZ\u0010[\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R$\u0010`\u001a\u0004\u0018\u00010Y8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b`\u0010[\u001a\u0004\ba\u0010]\"\u0004\bb\u0010_\u00a8\u0006e"}, d2={"Lcom/cobblemon/mod/common/client/entity/PokemonClientDelegate;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "Lcom/cobblemon/mod/common/api/entity/PokemonSideDelegate;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "changePokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "cry", "()V", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "", "status", "handleStatus", "(B)V", "entity", "initialize", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "Lnet/minecraft/network/syncher/EntityDataAccessor;", "data", "onTrackedDataSet", "(Lnet/minecraft/network/syncher/EntityDataAccessor;)V", "", "targetId", "setPhaseTarget", "(I)V", "tick", "", "partialTicks", "updatePartialTicks", "(F)V", "updatePostDeath", "", "ballDone", "Z", "getBallDone", "()Z", "setBallDone", "(Z)V", "ballOffset", "F", "getBallOffset", "()F", "setBallOffset", "ballRotOffset", "getBallRotOffset", "setBallRotOffset", "", "ballStartTime", "J", "getBallStartTime", "()J", "setBallStartTime", "(J)V", "beamStartTime", "getBeamStartTime", "setBeamStartTime", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "cryAnimation", "Lcom/cobblemon/mod/common/client/render/models/blockbench/animation/StatefulAnimation;", "currentEntity", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getCurrentEntity", "setCurrentEntity", "entityScaleModifier", "getEntityScaleModifier", "setEntityScaleModifier", "Lnet/minecraft/world/entity/Entity;", "phaseTarget", "Lnet/minecraft/world/entity/Entity;", "getPhaseTarget", "()Lnet/minecraft/world/entity/Entity;", "(Lnet/minecraft/world/entity/Entity;)V", "playedSendOutSound", "getPlayedSendOutSound", "setPlayedSendOutSound", "playedThrowingSound", "getPlayedThrowingSound", "setPlayedThrowingSound", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "getSecondsSinceBallThrown", "secondsSinceBallThrown", "getSecondsSinceBeamEffectStarted", "secondsSinceBeamEffectStarted", "Lnet/minecraft/world/phys/Vec3;", "sendOutOffset", "Lnet/minecraft/world/phys/Vec3;", "getSendOutOffset", "()Lnet/minecraft/world/phys/Vec3;", "setSendOutOffset", "(Lnet/minecraft/world/phys/Vec3;)V", "sendOutPosition", "getSendOutPosition", "setSendOutPosition", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonClientDelegate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonClientDelegate.kt\ncom/cobblemon/mod/common/client/entity/PokemonClientDelegate\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,319:1\n1#2:320\n*E\n"})
public final class PokemonClientDelegate
extends PoseableEntityState<PokemonEntity>
implements PokemonSideDelegate {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public PokemonEntity currentEntity;
    @Nullable
    private Entity phaseTarget;
    private float entityScaleModifier = 1.0f;
    private long beamStartTime = System.currentTimeMillis();
    private long ballStartTime = System.currentTimeMillis();
    private boolean ballDone;
    private float ballOffset;
    private float ballRotOffset;
    @Nullable
    private Vec3 sendOutPosition;
    @Nullable
    private Vec3 sendOutOffset;
    private boolean playedSendOutSound;
    private boolean playedThrowingSound;
    @Nullable
    private StatefulAnimation<PokemonEntity, ?> cryAnimation;
    public static final float BEAM_SHRINK_TIME = 0.4f;
    public static final float BEAM_EXTEND_TIME = 0.2f;
    public static final float POKEBALL_AIR_TIME = 0.5f;

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.getCurrentEntity().getSchedulingTracker();
    }

    @NotNull
    public final PokemonEntity getCurrentEntity() {
        PokemonEntity pokemonEntity = this.currentEntity;
        if (pokemonEntity != null) {
            return pokemonEntity;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"currentEntity");
        return null;
    }

    public final void setCurrentEntity(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"<set-?>");
        this.currentEntity = pokemonEntity;
    }

    @Nullable
    public final Entity getPhaseTarget() {
        return this.phaseTarget;
    }

    public final void setPhaseTarget(@Nullable Entity entity2) {
        this.phaseTarget = entity2;
    }

    public final float getEntityScaleModifier() {
        return this.entityScaleModifier;
    }

    public final void setEntityScaleModifier(float f) {
        this.entityScaleModifier = f;
    }

    @Override
    @NotNull
    public PokemonEntity getEntity() {
        return this.getCurrentEntity();
    }

    @Override
    public void updatePartialTicks(float partialTicks) {
        this.setCurrentPartialTicks(partialTicks);
        this.getSchedulingTracker().update(0.0f);
    }

    public final long getBeamStartTime() {
        return this.beamStartTime;
    }

    public final void setBeamStartTime(long l) {
        this.beamStartTime = l;
    }

    public final long getBallStartTime() {
        return this.ballStartTime;
    }

    public final void setBallStartTime(long l) {
        this.ballStartTime = l;
    }

    public final boolean getBallDone() {
        return this.ballDone;
    }

    public final void setBallDone(boolean bl) {
        this.ballDone = bl;
    }

    public final float getBallOffset() {
        return this.ballOffset;
    }

    public final void setBallOffset(float f) {
        this.ballOffset = f;
    }

    public final float getBallRotOffset() {
        return this.ballRotOffset;
    }

    public final void setBallRotOffset(float f) {
        this.ballRotOffset = f;
    }

    @Nullable
    public final Vec3 getSendOutPosition() {
        return this.sendOutPosition;
    }

    public final void setSendOutPosition(@Nullable Vec3 vec3) {
        this.sendOutPosition = vec3;
    }

    @Nullable
    public final Vec3 getSendOutOffset() {
        return this.sendOutOffset;
    }

    public final void setSendOutOffset(@Nullable Vec3 vec3) {
        this.sendOutOffset = vec3;
    }

    public final boolean getPlayedSendOutSound() {
        return this.playedSendOutSound;
    }

    public final void setPlayedSendOutSound(boolean bl) {
        this.playedSendOutSound = bl;
    }

    public final boolean getPlayedThrowingSound() {
        return this.playedThrowingSound;
    }

    public final void setPlayedThrowingSound(boolean bl) {
        this.playedThrowingSound = bl;
    }

    public final float getSecondsSinceBeamEffectStarted() {
        return (float)(System.currentTimeMillis() - this.beamStartTime) / 1000.0f;
    }

    public final float getSecondsSinceBallThrown() {
        return (float)(System.currentTimeMillis() - this.ballStartTime) / 1000.0f;
    }

    @Override
    public void onTrackedDataSet(@NotNull EntityDataAccessor<?> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        PokemonSideDelegate.DefaultImpls.onTrackedDataSet(this, data);
        if (this.currentEntity != null) {
            if (Intrinsics.areEqual(data, PokemonEntity.Companion.getSPECIES())) {
                ResourceLocation identifier = new ResourceLocation((String)this.getCurrentEntity().m_20088_().m_135370_(PokemonEntity.Companion.getSPECIES()));
                this.setCurrentPose(null);
                Pokemon pokemon = this.getCurrentEntity().getPokemon();
                Species species = PokemonSpecies.INSTANCE.getByIdentifier(identifier);
                Intrinsics.checkNotNull((Object)species);
                pokemon.setSpecies(species);
                this.setCurrentModel(PokemonModelRepository.INSTANCE.getPoser(identifier, this.getCurrentEntity().getAspects()));
            } else if (Intrinsics.areEqual(data, PokemonEntity.Companion.getDYING_EFFECTS_STARTED())) {
                Boolean isDying = (Boolean)this.getCurrentEntity().m_20088_().m_135370_(PokemonEntity.Companion.getDYING_EFFECTS_STARTED());
                Intrinsics.checkNotNullExpressionValue((Object)isDying, (String)"isDying");
                if (isDying.booleanValue()) {
                    StatefulAnimation statefulAnimation;
                    PoseableEntityModel poseableEntityModel = this.getCurrentModel();
                    if (poseableEntityModel == null) {
                        return;
                    }
                    PokemonPoseableModel model = (PokemonPoseableModel)poseableEntityModel;
                    try {
                        statefulAnimation = model.getAnimation(this, "faint", this.getRuntime());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        statefulAnimation = null;
                    }
                    StatefulAnimation statefulAnimation2 = statefulAnimation;
                    if (statefulAnimation2 == null) {
                        return;
                    }
                    StatefulAnimation animation = statefulAnimation2;
                    PrimaryAnimation primaryAnimation2 = new PrimaryAnimation(animation, null, null, false, 14, null);
                    this.after(3.0f, (Function0<Unit>)((Function0)new Function0<Unit>(this){
                        final /* synthetic */ PokemonClientDelegate this$0;
                        {
                            this.this$0 = $receiver;
                            super(0);
                        }

                        public final void invoke() {
                            this.this$0.setEntityScaleModifier(0.0f);
                        }
                    }));
                    this.addPrimaryAnimation(primaryAnimation2);
                }
            } else if (Intrinsics.areEqual(data, PokemonEntity.Companion.getBEAM_MODE())) {
                int beamMode = this.getCurrentEntity().getBeamMode();
                switch (beamMode) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        this.playedSendOutSound = false;
                        this.entityScaleModifier = 0.0f;
                        this.beamStartTime = System.currentTimeMillis();
                        this.ballStartTime = System.currentTimeMillis();
                        this.getCurrentEntity().m_6842_(true);
                        this.ballDone = false;
                        Ref.ObjectRef soundPos = new Ref.ObjectRef();
                        soundPos.element = this.getCurrentEntity().m_20182_();
                        UUID uUID = this.getCurrentEntity().getPokemon().getOwnerUUID();
                        if (uUID != null) {
                            UUID it = uUID;
                            boolean bl = false;
                            Player player = this.getCurrentEntity().m_9236_().m_46003_(it);
                            if (player != null) {
                                InteractionHand interactionHand;
                                InteractionHand interactionHand2;
                                Player it2 = player;
                                boolean bl2 = false;
                                Vec3 offset = it2.m_20182_().m_82546_(this.getCurrentEntity().m_20182_().m_82520_(0.0, 2.0 - (double)this.ballOffset / (double)10.0f, 0.0)).m_82541_().m_82490_(-PokemonRenderer.Companion.ease(this.ballOffset));
                                Vec3 $this$onTrackedDataSet_u24lambda_u242_u24lambda_u241_u24lambda_u240 = it2.m_20182_().m_82546_(this.getCurrentEntity().m_20182_());
                                boolean bl3 = false;
                                Vec3 newOffset = offset.m_82490_(2.0);
                                double distance = it2.m_20182_().m_82554_(this.getCurrentEntity().m_20182_());
                                newOffset = newOffset.m_82490_(distance / 10.0 * (double)5);
                                soundPos.element = this.getCurrentEntity().m_20182_().m_82549_(newOffset);
                                InteractionHand interactionHand3 = interactionHand2 = it2.m_7655_();
                                if (interactionHand3 == null) {
                                    interactionHand = InteractionHand.MAIN_HAND;
                                } else {
                                    Intrinsics.checkNotNullExpressionValue((Object)interactionHand3, (String)"it.activeHand ?: Hand.MAIN_HAND");
                                    interactionHand = interactionHand2;
                                }
                                it2.m_6674_(interactionHand);
                            }
                        }
                        Minecraft client = Minecraft.m_91087_();
                        SoundEvent soundEvent = SoundEvent.m_262824_((ResourceLocation)CobblemonSounds.POKE_BALL_TRAIL.m_11660_());
                        Intrinsics.checkNotNullExpressionValue((Object)soundEvent, (String)"of(CobblemonSounds.POKE_BALL_TRAIL.id)");
                        MovingSoundInstance sound2 = new MovingSoundInstance(soundEvent, SoundSource.PLAYERS, (Function0<? extends Vec3>)((Function0)new Function0<Vec3>(this){
                            final /* synthetic */ PokemonClientDelegate this$0;
                            {
                                this.this$0 = $receiver;
                                super(0);
                            }

                            @Nullable
                            public final Vec3 invoke() {
                                Vec3 vec3 = this.this$0.getSendOutPosition();
                                return vec3 != null ? vec3.m_82549_(this.this$0.getSendOutOffset()) : null;
                            }
                        }), 0.1f, 1.0f, false, 20, 0);
                        if (!this.playedThrowingSound) {
                            client.m_91106_().m_120367_((SoundInstance)sound2);
                            this.playedThrowingSound = true;
                        }
                        SchedulingFunctionsKt.lerpOnClient(0.5f, (Function1<? super Float, Unit>)((Function1)new Function1<Float, Unit>(this){
                            final /* synthetic */ PokemonClientDelegate this$0;
                            {
                                this.this$0 = $receiver;
                                super(1);
                            }

                            public final void invoke(float it) {
                                this.this$0.setBallOffset(it);
                            }
                        }));
                        this.ballRotOffset = (float)(Math.random() * (double)this.getCurrentEntity().m_9236_().f_46441_.m_216332_(-15, 15));
                        this.getCurrentEntity().after(0.5f, (Function0<Unit>)((Function0)new Function0<Unit>(this, client, (Ref.ObjectRef<Vec3>)soundPos){
                            final /* synthetic */ PokemonClientDelegate this$0;
                            final /* synthetic */ Minecraft $client;
                            final /* synthetic */ Ref.ObjectRef<Vec3> $soundPos;
                            {
                                this.this$0 = $receiver;
                                this.$client = $client;
                                this.$soundPos = $soundPos;
                                super(0);
                            }

                            public final void invoke() {
                                this.this$0.setBeamStartTime(System.currentTimeMillis());
                                this.this$0.setBallDone(true);
                                if (this.$client.m_91106_().m_120384_(CobblemonSounds.POKE_BALL_SEND_OUT.m_11660_()) != null && !this.this$0.getPlayedSendOutSound()) {
                                    ClientLevel clientLevel = this.$client.f_91073_;
                                    if (clientLevel != null) {
                                        clientLevel.m_6263_((Player)this.$client.f_91074_, ((Vec3)this.$soundPos.element).f_82479_, ((Vec3)this.$soundPos.element).f_82480_, ((Vec3)this.$soundPos.element).f_82481_, SoundEvent.m_262824_((ResourceLocation)CobblemonSounds.POKE_BALL_SEND_OUT.m_11660_()), SoundSource.PLAYERS, 0.6f, 1.0f);
                                    }
                                    this.this$0.setPlayedSendOutSound(true);
                                }
                                Vec3 vec3 = this.this$0.getSendOutPosition();
                                if (vec3 != null) {
                                    BedrockParticleEffect sendflash;
                                    Vec3 vec32 = vec3;
                                    PokemonClientDelegate pokemonClientDelegate = this.this$0;
                                    Vec3 it = vec32;
                                    boolean bl = false;
                                    Vec3 newPos = it.m_82549_(pokemonClientDelegate.getSendOutOffset());
                                    String string = pokemonClientDelegate.getCurrentEntity().getPokemon().getCaughtBall().getName().m_135815_();
                                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"currentEntity.pokemon.caughtBall.name.path");
                                    String string2 = string.toLowerCase(Locale.ROOT);
                                    Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                                    String ballType = StringsKt.replace$default((String)string2, (String)"_", (String)"", (boolean)false, (int)4, null);
                                    String mode = pokemonClientDelegate.getCurrentEntity().isBattling() ? "battle" : "casual";
                                    BedrockParticleEffect bedrockParticleEffect = sendflash = BedrockParticleEffectRepository.INSTANCE.getEffect(MiscUtilsKt.cobblemonResource(ballType + "/" + mode + "/sendflash"));
                                    if (bedrockParticleEffect != null) {
                                        BedrockParticleEffect effect = bedrockParticleEffect;
                                        boolean bl2 = false;
                                        MatrixWrapper wrapper = new MatrixWrapper();
                                        PoseStack matrix = new PoseStack();
                                        matrix.m_85837_(newPos.f_82479_, newPos.f_82480_, newPos.f_82481_);
                                        Matrix4f matrix4f = matrix.m_85850_().m_252922_();
                                        Intrinsics.checkNotNullExpressionValue((Object)matrix4f, (String)"matrix.peek().positionMatrix");
                                        wrapper.updateMatrix(matrix4f);
                                        ClientLevel clientLevel = Minecraft.m_91087_().f_91073_;
                                        if (clientLevel != null) {
                                            Intrinsics.checkNotNullExpressionValue((Object)clientLevel, (String)"MinecraftClient.getInstance().world ?: return@let");
                                            ClientLevel world = clientLevel;
                                            new ParticleStorm(effect, wrapper, world, null, null, null, null, null, null, 504, null).spawn();
                                            BedrockParticleEffect ballsparks = BedrockParticleEffectRepository.INSTANCE.getEffect(MiscUtilsKt.cobblemonResource(ballType + "/" + mode + "/ballsparks"));
                                            BedrockParticleEffect ballsendsparkle = BedrockParticleEffectRepository.INSTANCE.getEffect(MiscUtilsKt.cobblemonResource(ballType + "/" + mode + "/ballsendsparkle"));
                                            SchedulingFunctionsKt.afterOnClient$default(0, 0.01667f, (Function0)new Function0<Unit>(ballsparks, ballsendsparkle, pokemonClientDelegate, wrapper, world, ballType){
                                                final /* synthetic */ BedrockParticleEffect $ballsparks;
                                                final /* synthetic */ BedrockParticleEffect $ballsendsparkle;
                                                final /* synthetic */ PokemonClientDelegate this$0;
                                                final /* synthetic */ MatrixWrapper $wrapper;
                                                final /* synthetic */ ClientLevel $world;
                                                final /* synthetic */ String $ballType;
                                                {
                                                    this.$ballsparks = $ballsparks;
                                                    this.$ballsendsparkle = $ballsendsparkle;
                                                    this.this$0 = $receiver;
                                                    this.$wrapper = $wrapper;
                                                    this.$world = $world;
                                                    this.$ballType = $ballType;
                                                    super(0);
                                                }

                                                public final void invoke() {
                                                    BedrockParticleEffect effect;
                                                    ClientLevel clientLevel;
                                                    MatrixWrapper matrixWrapper;
                                                    BedrockParticleEffect bedrockParticleEffect;
                                                    BedrockParticleEffect bedrockParticleEffect2 = this.$ballsparks;
                                                    if (bedrockParticleEffect2 != null) {
                                                        bedrockParticleEffect = bedrockParticleEffect2;
                                                        matrixWrapper = this.$wrapper;
                                                        clientLevel = this.$world;
                                                        effect = bedrockParticleEffect;
                                                        boolean bl = false;
                                                        new ParticleStorm(effect, matrixWrapper, clientLevel, null, null, null, null, null, null, 504, null).spawn();
                                                    }
                                                    BedrockParticleEffect bedrockParticleEffect3 = this.$ballsendsparkle;
                                                    if (bedrockParticleEffect3 != null) {
                                                        bedrockParticleEffect = bedrockParticleEffect3;
                                                        matrixWrapper = this.$wrapper;
                                                        clientLevel = this.$world;
                                                        effect = bedrockParticleEffect;
                                                        boolean bl = false;
                                                        new ParticleStorm(effect, matrixWrapper, clientLevel, null, null, null, null, null, null, 504, null).spawn();
                                                    }
                                                    this.this$0.getCurrentEntity().after(0.4f, (Function0<Unit>)((Function0)new Function0<Unit>(this.$ballType, this.$wrapper, this.$world){
                                                        final /* synthetic */ String $ballType;
                                                        final /* synthetic */ MatrixWrapper $wrapper;
                                                        final /* synthetic */ ClientLevel $world;
                                                        {
                                                            this.$ballType = $ballType;
                                                            this.$wrapper = $wrapper;
                                                            this.$world = $world;
                                                            super(0);
                                                        }

                                                        public final void invoke() {
                                                            block0: {
                                                                BedrockParticleEffect ballsparkle;
                                                                BedrockParticleEffect bedrockParticleEffect = ballsparkle = BedrockParticleEffectRepository.INSTANCE.getEffect(MiscUtilsKt.cobblemonResource(this.$ballType + "/ballsparkle"));
                                                                if (bedrockParticleEffect == null) break block0;
                                                                BedrockParticleEffect bedrockParticleEffect2 = bedrockParticleEffect;
                                                                MatrixWrapper matrixWrapper = this.$wrapper;
                                                                ClientLevel clientLevel = this.$world;
                                                                BedrockParticleEffect effect = bedrockParticleEffect2;
                                                                boolean bl = false;
                                                                new ParticleStorm(effect, matrixWrapper, clientLevel, null, null, null, null, null, null, 504, null).spawn();
                                                            }
                                                        }
                                                    }));
                                                }
                                            }, 1, null);
                                        }
                                    }
                                }
                                this.this$0.getCurrentEntity().after(0.2f, (Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                                    final /* synthetic */ PokemonClientDelegate this$0;
                                    {
                                        this.this$0 = $receiver;
                                        super(0);
                                    }

                                    public final void invoke() {
                                        SchedulingFunctionsKt.lerpOnClient(0.4f, (Function1<? super Float, Unit>)((Function1)new Function1<Float, Unit>(this.this$0){
                                            final /* synthetic */ PokemonClientDelegate this$0;
                                            {
                                                this.this$0 = $receiver;
                                                super(1);
                                            }

                                            public final void invoke(float it) {
                                                this.this$0.setEntityScaleModifier(it);
                                            }
                                        }));
                                        this.this$0.getCurrentEntity().m_6842_(false);
                                        this.this$0.getCurrentEntity().after(1.0f, (Function0<Unit>)((Function0)new Function0<Unit>(this.this$0){
                                            final /* synthetic */ PokemonClientDelegate this$0;
                                            {
                                                this.this$0 = $receiver;
                                                super(0);
                                            }

                                            public final void invoke() {
                                                this.this$0.setBallOffset(0.0f);
                                                this.this$0.setBallRotOffset(0.0f);
                                                this.this$0.setSendOutPosition(null);
                                            }
                                        }));
                                    }
                                }));
                            }
                        }));
                        break;
                    }
                    case 2: {
                        this.playedSendOutSound = false;
                        this.entityScaleModifier = 0.0f;
                        this.getCurrentEntity().m_6842_(false);
                        Vec3 soundPos = this.getCurrentEntity().m_20182_();
                        Minecraft client = Minecraft.m_91087_();
                        if (client.m_91106_().m_120384_(CobblemonSounds.POKE_BALL_SEND_OUT.m_11660_()) != null && !this.playedSendOutSound) {
                            ClientLevel clientLevel = client.f_91073_;
                            if (clientLevel != null) {
                                clientLevel.m_6263_((Player)client.f_91074_, soundPos.f_82479_, soundPos.f_82480_, soundPos.f_82481_, CobblemonSounds.POKE_BALL_SEND_OUT, SoundSource.PLAYERS, 0.6f, 1.0f);
                            }
                            this.playedSendOutSound = true;
                        }
                        SchedulingFunctionsKt.lerpOnClient(0.4f, (Function1<? super Float, Unit>)((Function1)new Function1<Float, Unit>(this){
                            final /* synthetic */ PokemonClientDelegate this$0;
                            {
                                this.this$0 = $receiver;
                                super(1);
                            }

                            public final void invoke(float it) {
                                this.this$0.setEntityScaleModifier(it);
                            }
                        }));
                        this.getCurrentEntity().after(0.8f, (Function0<Unit>)((Function0)new Function0<Unit>(this){
                            final /* synthetic */ PokemonClientDelegate this$0;
                            {
                                this.this$0 = $receiver;
                                super(0);
                            }

                            public final void invoke() {
                                this.this$0.setBallOffset(0.0f);
                                this.this$0.setBallRotOffset(0.0f);
                                this.this$0.setSendOutPosition(null);
                            }
                        }));
                        break;
                    }
                    case 3: {
                        this.entityScaleModifier = 1.0f;
                        this.beamStartTime = System.currentTimeMillis();
                        this.ballOffset = 0.0f;
                        this.ballRotOffset = 0.0f;
                        this.sendOutPosition = null;
                        SchedulingFunctionsKt.afterOnClient$default(0, 0.2f, (Function0)new Function0<Unit>(this){
                            final /* synthetic */ PokemonClientDelegate this$0;
                            {
                                this.this$0 = $receiver;
                                super(0);
                            }

                            public final void invoke() {
                                SchedulingFunctionsKt.lerpOnClient(0.4f, (Function1<? super Float, Unit>)((Function1)new Function1<Float, Unit>(this.this$0){
                                    final /* synthetic */ PokemonClientDelegate this$0;
                                    {
                                        this.this$0 = $receiver;
                                        super(1);
                                    }

                                    public final void invoke(float it) {
                                        this.this$0.setEntityScaleModifier(1.0f - it);
                                    }
                                }));
                            }
                        }, 1, null);
                    }
                }
            } else if (Intrinsics.areEqual(data, PokemonEntity.Companion.getLABEL_LEVEL())) {
                Object object = this.getCurrentEntity().m_20088_().m_135370_(PokemonEntity.Companion.getLABEL_LEVEL());
                Integer it = (Integer)object;
                boolean bl = false;
                Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                Integer beamMode = (Integer)(it > 0 ? object : null);
                if (beamMode != null) {
                    int it3 = ((Number)beamMode).intValue();
                    boolean bl4 = false;
                    this.getCurrentEntity().getPokemon().setLevel(it3);
                }
            } else if (Intrinsics.areEqual(data, PokemonEntity.Companion.getPHASING_TARGET_ID())) {
                Integer phasingTargetId;
                Integer n = phasingTargetId = (Integer)this.getCurrentEntity().m_20088_().m_135370_(PokemonEntity.Companion.getPHASING_TARGET_ID());
                int n2 = -1;
                if (n == null || n != n2) {
                    Intrinsics.checkNotNullExpressionValue((Object)phasingTargetId, (String)"phasingTargetId");
                    this.setPhaseTarget(phasingTargetId);
                } else {
                    this.phaseTarget = null;
                }
            }
        }
    }

    @Override
    public void changePokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        pokemon.setClient$common(true);
    }

    @Override
    public void initialize(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        this.setCurrentEntity(entity2);
        this.setAge(entity2.f_19797_);
        MoLangEnvironment moLangEnvironment = this.getRuntime().getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"this.runtime.environment");
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"in_battle", arg_0 -> PokemonClientDelegate.initialize$lambda$5(this, arg_0)), TuplesKt.to((Object)"shiny", arg_0 -> PokemonClientDelegate.initialize$lambda$6(this, arg_0)), TuplesKt.to((Object)"form", arg_0 -> PokemonClientDelegate.initialize$lambda$7(this, arg_0)), TuplesKt.to((Object)"width", arg_0 -> PokemonClientDelegate.initialize$lambda$8(this, arg_0)), TuplesKt.to((Object)"height", arg_0 -> PokemonClientDelegate.initialize$lambda$9(this, arg_0)), TuplesKt.to((Object)"weight", arg_0 -> PokemonClientDelegate.initialize$lambda$10(this, arg_0)), TuplesKt.to((Object)"friendship", arg_0 -> PokemonClientDelegate.initialize$lambda$11(this, arg_0))};
        MoLangFunctions.INSTANCE.addFunctions(MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null), MapsKt.mapOf((Pair[])pairArray));
    }

    @Override
    public void tick(@NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Vec3 vec3 = entity2.m_20182_();
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"entity.pos");
        this.updateLocatorPosition(vec3);
        this.incrementAge((Entity)entity2);
    }

    public final void setPhaseTarget(int targetId) {
        this.phaseTarget = this.getCurrentEntity().m_9236_().m_6815_(targetId);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 10) {
            PoseableEntityModel poseableEntityModel = this.getCurrentModel();
            if (poseableEntityModel == null) {
                return;
            }
            PokemonPoseableModel model = (PokemonPoseableModel)poseableEntityModel;
            StatefulAnimation<PokemonEntity, ModelFrame> statefulAnimation = model.getEatAnimation(this.getCurrentEntity(), this);
            if (statefulAnimation == null) {
                return;
            }
            StatefulAnimation<PokemonEntity, ModelFrame> animation = statefulAnimation;
            this.getStatefulAnimations().add(animation);
        }
    }

    @Override
    public void updatePostDeath() {
        PokemonEntity pokemonEntity = this.getCurrentEntity();
        ++pokemonEntity.f_20919_;
        int cfr_ignored_0 = pokemonEntity.f_20919_;
    }

    public final void cry() {
        PoseableEntityModel poseableEntityModel = this.getCurrentModel();
        if (poseableEntityModel == null) {
            return;
        }
        PoseableEntityModel model = poseableEntityModel;
        if (model instanceof PokemonPoseableModel) {
            if (this.cryAnimation != null && (CollectionsKt.contains((Iterable)this.getStatefulAnimations(), this.cryAnimation) || Intrinsics.areEqual(this.cryAnimation, this.getPrimaryAnimation()))) {
                return;
            }
            StatefulAnimation<PokemonEntity, ModelFrame> statefulAnimation = ((PokemonPoseableModel)model).getCryAnimation().invoke(this.getCurrentEntity(), this);
            if (statefulAnimation == null) {
                return;
            }
            StatefulAnimation<PokemonEntity, ModelFrame> animation = statefulAnimation;
            if (animation instanceof PrimaryAnimation) {
                this.addPrimaryAnimation((PrimaryAnimation)animation);
            } else {
                this.getStatefulAnimations().add(animation);
            }
            this.cryAnimation = animation;
        }
    }

    @Override
    public void drop(@Nullable DamageSource source) {
        PokemonSideDelegate.DefaultImpls.drop(this, source);
    }

    private static final Object initialize$lambda$5(PokemonClientDelegate this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return new DoubleValue(this$0.getCurrentEntity().isBattling());
    }

    private static final Object initialize$lambda$6(PokemonClientDelegate this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return new DoubleValue(this$0.getCurrentEntity().getPokemon().getShiny());
    }

    private static final Object initialize$lambda$7(PokemonClientDelegate this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return new StringValue(this$0.getCurrentEntity().getPokemon().getForm().getName());
    }

    private static final Object initialize$lambda$8(PokemonClientDelegate this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return new DoubleValue(this$0.getCurrentEntity().m_20191_().m_82362_());
    }

    private static final Object initialize$lambda$9(PokemonClientDelegate this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return new DoubleValue(this$0.getCurrentEntity().m_20191_().m_82376_());
    }

    private static final Object initialize$lambda$10(PokemonClientDelegate this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return new DoubleValue(this$0.getCurrentEntity().getPokemon().getSpecies().getWeight());
    }

    private static final Object initialize$lambda$11(PokemonClientDelegate this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
        return new DoubleValue(this$0.getCurrentEntity().getPokemon().getFriendship());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/client/entity/PokemonClientDelegate$Companion;", "", "", "BEAM_EXTEND_TIME", "F", "BEAM_SHRINK_TIME", "POKEBALL_AIR_TIME", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

