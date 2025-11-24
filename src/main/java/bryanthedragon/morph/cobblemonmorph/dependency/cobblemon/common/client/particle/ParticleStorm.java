/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.NoRenderParticle
 *  net.minecraft.client.particle.Particle
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ShowdownIdentifiable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BedrockParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LoopingTravelDistanceEventTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.SimpleEventTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.MatrixWrapper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.SnowstormParticleEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.Matrix4fExtensionsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 e2\u00020\u0001:\u0001eBu\u0012\u0006\u0010,\u001a\u00020+\u0012\u0006\u00109\u001a\u000208\u0012\u0006\u0010_\u001a\u00020^\u0012\u000e\b\u0002\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00050=\u0012\u000e\b\u0002\u0010R\u001a\b\u0012\u0004\u0012\u00020\u001d0=\u0012\u000e\b\u0002\u0010V\u001a\b\u0012\u0004\u0012\u00020\u001d0=\u0012\u000e\b\u0002\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00130=\u0012\b\b\u0002\u0010N\u001a\u00020M\u0012\n\b\u0002\u00101\u001a\u0004\u0018\u000100\u00a2\u0006\u0004\bc\u0010dJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\t\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\r\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\u0004\b\u000e\u0010\rJ\r\u0010\u000f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u000f\u0010\rJ\r\u0010\u0010\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0010\u0010\rJ\r\u0010\u0011\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0011\u0010\rJ\r\u0010\u0012\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0012\u0010\rJ\u000f\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\r\u0010\u0016\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0016\u0010\u0015J\r\u0010\u0017\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0017\u0010\u0015J\u000f\u0010\u0018\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0015J\u0015\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001a\u0010\nJ\u0015\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001c\u0010\nR\"\u0010\u001e\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\"\u0010%\u001a\u00020$8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u0017\u0010,\u001a\u00020+8\u0006\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u0019\u00101\u001a\u0004\u0018\u0001008\u0006\u00a2\u0006\f\n\u0004\b1\u00102\u001a\u0004\b3\u00104R\"\u00105\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b5\u0010\u001f\u001a\u0004\b6\u0010!\"\u0004\b7\u0010#R\u0017\u00109\u001a\u0002088\u0006\u00a2\u0006\f\n\u0004\b9\u0010:\u001a\u0004\b;\u0010<R\u001d\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00130=8\u0006\u00a2\u0006\f\n\u0004\b>\u0010?\u001a\u0004\b@\u0010AR\u0017\u0010C\u001a\u00020B8\u0006\u00a2\u0006\f\n\u0004\bC\u0010D\u001a\u0004\bE\u0010FR\u001d\u0010I\u001a\b\u0012\u0004\u0012\u00020H0G8\u0006\u00a2\u0006\f\n\u0004\bI\u0010J\u001a\u0004\bK\u0010LR\u0017\u0010N\u001a\u00020M8\u0006\u00a2\u0006\f\n\u0004\bN\u0010O\u001a\u0004\bP\u0010QR\u001d\u0010R\u001a\b\u0012\u0004\u0012\u00020\u001d0=8\u0006\u00a2\u0006\f\n\u0004\bR\u0010?\u001a\u0004\bS\u0010AR\u001d\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00050=8\u0006\u00a2\u0006\f\n\u0004\bT\u0010?\u001a\u0004\bU\u0010AR\u001d\u0010V\u001a\b\u0012\u0004\u0012\u00020\u001d0=8\u0006\u00a2\u0006\f\n\u0004\bV\u0010?\u001a\u0004\bW\u0010AR\"\u0010X\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bX\u0010\u001f\u001a\u0004\bY\u0010!\"\u0004\bZ\u0010#R\"\u0010[\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b[\u0010\u001f\u001a\u0004\b\\\u0010!\"\u0004\b]\u0010#R\u0017\u0010_\u001a\u00020^8\u0006\u00a2\u0006\f\n\u0004\b_\u0010`\u001a\u0004\ba\u0010b\u00a8\u0006f"}, d2={"Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "Lnet/minecraft/client/particle/NoRenderParticle;", "", "getMaxAge", "()I", "Lnet/minecraft/world/phys/Vec3;", "getNextParticleSpawnPosition", "()Lnet/minecraft/world/phys/Vec3;", "nextParticlePosition", "getNextParticleVelocity", "(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "", "getPrevX", "()D", "getPrevY", "getPrevZ", "getX", "getY", "getZ", "", "markDead", "()V", "spawn", "spawnParticle", "tick", "direction", "transformDirection", "position", "transformPosition", "", "despawned", "Z", "getDespawned", "()Z", "setDespawned", "(Z)V", "", "distanceTravelled", "F", "getDistanceTravelled", "()F", "setDistanceTravelled", "(F)V", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "effect", "Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "getEffect", "()Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/world/entity/Entity;", "getEntity", "()Lnet/minecraft/world/entity/Entity;", "hasPlayedOnce", "getHasPlayedOnce", "setHasPlayedOnce", "Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "matrixWrapper", "Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "getMatrixWrapper", "()Lcom/cobblemon/mod/common/client/render/MatrixWrapper;", "Lkotlin/Function0;", "onDespawn", "Lkotlin/jvm/functions/Function0;", "getOnDespawn", "()Lkotlin/jvm/functions/Function0;", "Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect;", "particleEffect", "Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect;", "getParticleEffect", "()Lcom/cobblemon/mod/common/particle/SnowstormParticleEffect;", "", "Lcom/cobblemon/mod/common/client/render/SnowstormParticle;", "particles", "Ljava/util/List;", "getParticles", "()Ljava/util/List;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "sourceAlive", "getSourceAlive", "sourceVelocity", "getSourceVelocity", "sourceVisible", "getSourceVisible", "started", "getStarted", "setStarted", "stopped", "getStopped", "setStopped", "Lnet/minecraft/client/multiplayer/ClientLevel;", "world", "Lnet/minecraft/client/multiplayer/ClientLevel;", "getWorld", "()Lnet/minecraft/client/multiplayer/ClientLevel;", "<init>", "(Lcom/cobblemon/mod/common/api/snowstorm/BedrockParticleEffect;Lcom/cobblemon/mod/common/client/render/MatrixWrapper;Lnet/minecraft/client/multiplayer/ClientLevel;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;Lcom/bedrockk/molang/runtime/MoLangRuntime;Lnet/minecraft/world/entity/Entity;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nParticleStorm.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleStorm.kt\ncom/cobblemon/mod/common/client/particle/ParticleStorm\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,200:1\n1855#2,2:201\n1855#2,2:204\n1855#2,2:206\n1855#2,2:208\n1#3:203\n*S KotlinDebug\n*F\n+ 1 ParticleStorm.kt\ncom/cobblemon/mod/common/client/particle/ParticleStorm\n*L\n100#1:201,2\n110#1:204,2\n147#1:206,2\n159#1:208,2\n*E\n"})
public final class ParticleStorm
extends NoRenderParticle {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BedrockParticleEffect effect;
    @NotNull
    private final MatrixWrapper matrixWrapper;
    @NotNull
    private final ClientLevel world;
    @NotNull
    private final Function0<Vec3> sourceVelocity;
    @NotNull
    private final Function0<Boolean> sourceAlive;
    @NotNull
    private final Function0<Boolean> sourceVisible;
    @NotNull
    private final Function0<Unit> onDespawn;
    @NotNull
    private final MoLangRuntime runtime;
    @Nullable
    private final Entity entity;
    @NotNull
    private final List<SnowstormParticle> particles;
    private boolean started;
    private boolean stopped;
    private boolean despawned;
    private boolean hasPlayedOnce;
    private float distanceTravelled;
    @NotNull
    private final SnowstormParticleEffect particleEffect;
    @Nullable
    private static ParticleStorm contextStorm;

    public ParticleStorm(@NotNull BedrockParticleEffect effect, @NotNull MatrixWrapper matrixWrapper, @NotNull ClientLevel world, @NotNull Function0<? extends Vec3> sourceVelocity2, @NotNull Function0<Boolean> sourceAlive, @NotNull Function0<Boolean> sourceVisible, @NotNull Function0<Unit> onDespawn, @NotNull MoLangRuntime runtime2, @Nullable Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)effect, (String)"effect");
        Intrinsics.checkNotNullParameter((Object)matrixWrapper, (String)"matrixWrapper");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter(sourceVelocity2, (String)"sourceVelocity");
        Intrinsics.checkNotNullParameter(sourceAlive, (String)"sourceAlive");
        Intrinsics.checkNotNullParameter(sourceVisible, (String)"sourceVisible");
        Intrinsics.checkNotNullParameter(onDespawn, (String)"onDespawn");
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        super(world, matrixWrapper.getOrigin().f_82479_, matrixWrapper.getOrigin().f_82480_, matrixWrapper.getOrigin().f_82481_);
        this.effect = effect;
        this.matrixWrapper = matrixWrapper;
        this.world = world;
        this.sourceVelocity = sourceVelocity2;
        this.sourceAlive = sourceAlive;
        this.sourceVisible = sourceVisible;
        this.onDespawn = onDespawn;
        this.runtime = runtime2;
        this.entity = entity2;
        this.particles = new ArrayList();
        this.particleEffect = new SnowstormParticleEffect(this.effect);
        this.runtime.execute(this.effect.getEmitter().getStartExpressions());
        Iterable $this$forEach$iv = this.effect.getEmitter().getCreationEvents();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            SimpleEventTrigger it = (SimpleEventTrigger)element$iv;
            boolean bl = false;
            it.trigger(this, null);
        }
    }

    public /* synthetic */ ParticleStorm(BedrockParticleEffect bedrockParticleEffect, MatrixWrapper matrixWrapper, ClientLevel clientLevel, Function0 function0, Function0 function02, Function0 function03, Function0 function04, MoLangRuntime moLangRuntime, Entity entity2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            function0 = 1.INSTANCE;
        }
        if ((n & 0x10) != 0) {
            function02 = 2.INSTANCE;
        }
        if ((n & 0x20) != 0) {
            function03 = 3.INSTANCE;
        }
        if ((n & 0x40) != 0) {
            function04 = 4.INSTANCE;
        }
        if ((n & 0x80) != 0) {
            moLangRuntime = new MoLangRuntime();
        }
        if ((n & 0x100) != 0) {
            entity2 = null;
        }
        this(bedrockParticleEffect, matrixWrapper, clientLevel, (Function0<? extends Vec3>)function0, (Function0<Boolean>)function02, (Function0<Boolean>)function03, (Function0<Unit>)function04, moLangRuntime, entity2);
    }

    @NotNull
    public final BedrockParticleEffect getEffect() {
        return this.effect;
    }

    @NotNull
    public final MatrixWrapper getMatrixWrapper() {
        return this.matrixWrapper;
    }

    @NotNull
    public final ClientLevel getWorld() {
        return this.world;
    }

    @NotNull
    public final Function0<Vec3> getSourceVelocity() {
        return this.sourceVelocity;
    }

    @NotNull
    public final Function0<Boolean> getSourceAlive() {
        return this.sourceAlive;
    }

    @NotNull
    public final Function0<Boolean> getSourceVisible() {
        return this.sourceVisible;
    }

    @NotNull
    public final Function0<Unit> getOnDespawn() {
        return this.onDespawn;
    }

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    @Nullable
    public final Entity getEntity() {
        return this.entity;
    }

    public final void spawn() {
        if (this.entity != null) {
            MoLangEnvironment moLangEnvironment = this.runtime.getEnvironment();
            Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"runtime.environment");
            MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null).addFunction("entity_width", arg_0 -> ParticleStorm.spawn$lambda$0(this, arg_0)).addFunction("entity_height", arg_0 -> ParticleStorm.spawn$lambda$1(this, arg_0)).addFunction("entity_size", arg_0 -> ParticleStorm.spawn$lambda$3(this, arg_0)).addFunction("entity_radius", arg_0 -> ParticleStorm.spawn$lambda$5(this, arg_0)).addFunction("entity_scale", arg_0 -> ParticleStorm.spawn$lambda$6(this, arg_0));
            this.runtime.getEnvironment().setSimpleVariable("entity_width", new DoubleValue(this.entity.m_20191_().m_82362_()));
            this.runtime.getEnvironment().setSimpleVariable("entity_height", new DoubleValue(this.entity.m_20191_().m_82376_()));
            AABB $this$spawn_u24lambda_u247 = this.entity.m_20191_();
            boolean bl = false;
            double longerDiameter = $this$spawn_u24lambda_u247.m_82362_() > $this$spawn_u24lambda_u247.m_82376_() ? $this$spawn_u24lambda_u247.m_82362_() : $this$spawn_u24lambda_u247.m_82376_();
            this.runtime.getEnvironment().setSimpleVariable("entity_size", new DoubleValue(longerDiameter));
            this.runtime.getEnvironment().setSimpleVariable("entity_radius", new DoubleValue(longerDiameter / (double)2));
            Entity entity2 = this.entity;
            PokemonEntity pokemonEntity = entity2 instanceof PokemonEntity ? (PokemonEntity)entity2 : null;
            this.runtime.getEnvironment().setSimpleVariable("entity_scale", new DoubleValue(pokemonEntity != null ? (Number)Float.valueOf(pokemonEntity.m_6134_()) : (Number)1.0));
        }
        Minecraft.m_91087_().f_91061_.m_107344_((Particle)this);
    }

    public final double getX() {
        return this.f_107212_;
    }

    public final double getY() {
        return this.f_107213_;
    }

    public final double getZ() {
        return this.f_107214_;
    }

    public final double getPrevX() {
        return this.f_107209_;
    }

    public final double getPrevY() {
        return this.f_107210_;
    }

    public final double getPrevZ() {
        return this.f_107211_;
    }

    @NotNull
    public final List<SnowstormParticle> getParticles() {
        return this.particles;
    }

    public final boolean getStarted() {
        return this.started;
    }

    public final void setStarted(boolean bl) {
        this.started = bl;
    }

    public final boolean getStopped() {
        return this.stopped;
    }

    public final void setStopped(boolean bl) {
        this.stopped = bl;
    }

    public final boolean getDespawned() {
        return this.despawned;
    }

    public final void setDespawned(boolean bl) {
        this.despawned = bl;
    }

    public final boolean getHasPlayedOnce() {
        return this.hasPlayedOnce;
    }

    public final void setHasPlayedOnce(boolean bl) {
        this.hasPlayedOnce = bl;
    }

    public final float getDistanceTravelled() {
        return this.distanceTravelled;
    }

    public final void setDistanceTravelled(float f) {
        this.distanceTravelled = f;
    }

    @NotNull
    public final SnowstormParticleEffect getParticleEffect() {
        return this.particleEffect;
    }

    public int m_107273_() {
        return this.stopped ? 0 : Integer.MAX_VALUE;
    }

    public void m_107274_() {
        super.m_107274_();
        if (!this.despawned) {
            Iterable $this$forEach$iv = this.effect.getEmitter().getExpirationEvents();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                SimpleEventTrigger it = (SimpleEventTrigger)element$iv;
                boolean bl = false;
                it.trigger(this, null);
            }
            this.despawned = true;
            this.onDespawn.invoke();
        }
    }

    public void m_5989_() {
        this.m_107257_(this.m_107273_());
        super.m_5989_();
        if (!this.hasPlayedOnce) {
            this.f_107224_ = 0;
            this.hasPlayedOnce = true;
        }
        if (!((Boolean)this.sourceAlive.invoke()).booleanValue() && !this.stopped) {
            this.stopped = true;
            this.m_107274_();
        }
        if (this.stopped || !((Boolean)this.sourceVisible.invoke()).booleanValue()) {
            return;
        }
        Vec3 pos = this.matrixWrapper.getOrigin();
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        this.f_107212_ = pos.f_82479_;
        this.f_107213_ = pos.f_82480_;
        this.f_107214_ = pos.f_82481_;
        float oldDistanceTravelled = this.distanceTravelled;
        this.distanceTravelled += (float)new Vec3(this.f_107212_ - this.f_107209_, this.f_107213_ - this.f_107210_, this.f_107214_ - this.f_107211_).m_82553_();
        this.effect.getEmitter().getTravelDistanceEvents().check(this, null, oldDistanceTravelled, this.distanceTravelled);
        Iterable $this$forEach$iv = this.effect.getEmitter().getLoopingTravelDistanceEvents();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            LoopingTravelDistanceEventTrigger it = (LoopingTravelDistanceEventTrigger)element$iv;
            boolean bl = false;
            it.check(this, null, oldDistanceTravelled, this.distanceTravelled);
        }
        this.effect.getEmitter().getEventTimeline().check(this, null, (double)(this.f_107224_ - 1) / 20.0, (double)this.f_107224_ / 20.0);
        this.runtime.getEnvironment().setSimpleVariable("emitter_random_1", new DoubleValue(Random.Default.nextDouble()));
        this.runtime.getEnvironment().setSimpleVariable("emitter_random_2", new DoubleValue(Random.Default.nextDouble()));
        this.runtime.getEnvironment().setSimpleVariable("emitter_random_3", new DoubleValue(Random.Default.nextDouble()));
        this.runtime.getEnvironment().setSimpleVariable("emitter_random_4", new DoubleValue(Random.Default.nextDouble()));
        this.runtime.getEnvironment().setSimpleVariable("emitter_age", new DoubleValue((double)this.f_107224_ / 20.0));
        this.runtime.execute(this.effect.getEmitter().getUpdateExpressions());
        switch (WhenMappings.$EnumSwitchMapping$0[this.effect.getEmitter().getLifetime().getAction(this.runtime, this.started, (double)this.f_107224_ / 20.0).ordinal()]) {
            case 1: {
                Iterable $this$forEach$iv2 = this.effect.getCurves();
                boolean $i$f$forEach2 = false;
                for (Object element$iv : $this$forEach$iv2) {
                    MoLangCurve it = (MoLangCurve)element$iv;
                    boolean bl = false;
                    it.apply(this.runtime);
                }
                int toEmit = this.effect.getEmitter().getRate().getEmitCount(this.runtime, this.started, this.particles.size());
                this.started = true;
                int n = 0;
                while (n < toEmit) {
                    int it = n++;
                    boolean bl = false;
                    this.spawnParticle();
                }
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                this.stopped = true;
                break;
            }
            case 4: {
                this.started = false;
            }
        }
    }

    @NotNull
    public final Vec3 getNextParticleSpawnPosition() {
        this.runtime.getEnvironment().setSimpleVariable("particle_random_1", new DoubleValue(Random.Default.nextDouble()));
        this.runtime.getEnvironment().setSimpleVariable("particle_random_2", new DoubleValue(Random.Default.nextDouble()));
        this.runtime.getEnvironment().setSimpleVariable("particle_random_3", new DoubleValue(Random.Default.nextDouble()));
        this.runtime.getEnvironment().setSimpleVariable("particle_random_4", new DoubleValue(Random.Default.nextDouble()));
        Vec3 newPosition = this.transformPosition(this.effect.getEmitter().getShape().getNewParticlePosition(this.runtime, this.entity));
        return newPosition;
    }

    @NotNull
    public final Vec3 getNextParticleVelocity(@NotNull Vec3 nextParticlePosition) {
        Intrinsics.checkNotNullParameter((Object)nextParticlePosition, (String)"nextParticlePosition");
        Vec3 center = this.transformPosition(this.effect.getEmitter().getShape().getCenter(this.runtime, this.entity));
        Vec3 initialVelocity = this.effect.getParticle().getMotion().getInitialVelocity(this.runtime, this, nextParticlePosition, center);
        Vec3 vec3 = initialVelocity.m_82490_(0.05).m_82549_(this.effect.getSpace().getLocalVelocity() ? (Vec3)this.sourceVelocity.invoke() : Vec3.f_82478_);
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"initialVelocity\n        \u2026locity() else Vec3d.ZERO)");
        return vec3;
    }

    public final void spawnParticle() {
        Vec3 newPosition = this.getNextParticleSpawnPosition();
        Vec3 velocity = this.getNextParticleVelocity(newPosition);
        contextStorm = this;
        this.world.m_7106_((ParticleOptions)this.particleEffect, newPosition.f_82479_, newPosition.f_82480_, newPosition.f_82481_, velocity.f_82479_, velocity.f_82480_, velocity.f_82481_);
        contextStorm = null;
    }

    @NotNull
    public final Vec3 transformPosition(@NotNull Vec3 position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Vec3 vec3 = this.matrixWrapper.transformPosition(position);
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"matrixWrapper.transformPosition(position)");
        return vec3;
    }

    @NotNull
    public final Vec3 transformDirection(@NotNull Vec3 direction) {
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        return Matrix4fExtensionsKt.transformDirection(this.matrixWrapper.getMatrix(), direction);
    }

    private static final Object spawn$lambda$0(ParticleStorm this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        return new DoubleValue(this$0.entity.m_20191_().m_82362_());
    }

    private static final Object spawn$lambda$1(ParticleStorm this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        return new DoubleValue(this$0.entity.m_20191_().m_82376_());
    }

    private static final Object spawn$lambda$3(ParticleStorm this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        AABB $this$spawn_u24lambda_u243_u24lambda_u242 = this$0.entity.m_20191_();
        boolean bl = false;
        Double d = $this$spawn_u24lambda_u243_u24lambda_u242.m_82362_() > $this$spawn_u24lambda_u243_u24lambda_u242.m_82376_() ? $this$spawn_u24lambda_u243_u24lambda_u242.m_82362_() : $this$spawn_u24lambda_u243_u24lambda_u242.m_82376_();
        return new DoubleValue(d);
    }

    private static final Object spawn$lambda$5(ParticleStorm this$0, MoParams it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        AABB $this$spawn_u24lambda_u245_u24lambda_u244 = this$0.entity.m_20191_();
        boolean bl = false;
        Double d = ($this$spawn_u24lambda_u245_u24lambda_u244.m_82362_() > $this$spawn_u24lambda_u245_u24lambda_u244.m_82376_() ? $this$spawn_u24lambda_u245_u24lambda_u244.m_82362_() : $this$spawn_u24lambda_u245_u24lambda_u244.m_82376_()) / (double)2;
        return new DoubleValue(d);
    }

    private static final Object spawn$lambda$6(ParticleStorm this$0, MoParams it) {
        ShowdownIdentifiable showdownIdentifiable;
        PokemonEntity pokeEntity;
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        Entity entity2 = this$0.entity;
        PokemonEntity pokemonEntity = pokeEntity = entity2 instanceof PokemonEntity ? (PokemonEntity)entity2 : null;
        Pokemon pokemon = pokemonEntity != null ? pokemonEntity.getPokemon() : null;
        ShowdownIdentifiable showdownIdentifiable2 = pokemon;
        float baseScale = showdownIdentifiable2 != null && (showdownIdentifiable2 = ((Pokemon)showdownIdentifiable2).getForm()) != null ? ((FormData)showdownIdentifiable2).getBaseScale() : ((showdownIdentifiable = pokemon) != null && (showdownIdentifiable = ((Pokemon)showdownIdentifiable).getSpecies()) != null ? ((Species)showdownIdentifiable).getBaseScale() : 1.0f);
        Pokemon pokemon2 = pokemon;
        float pokemonScale = pokemon2 != null ? pokemon2.getScaleModifier() : 1.0f;
        PokemonEntity pokemonEntity2 = pokeEntity;
        float entityScale = pokemonEntity2 != null ? pokemonEntity2.m_6134_() : 1.0f;
        return new DoubleValue(Float.valueOf(baseScale * pokemonScale * entityScale));
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR$\u0010\u0003\u001a\u0004\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/particle/ParticleStorm$Companion;", "", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "contextStorm", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "getContextStorm", "()Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "setContextStorm", "(Lcom/cobblemon/mod/common/client/particle/ParticleStorm;)V", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @Nullable
        public final ParticleStorm getContextStorm() {
            return contextStorm;
        }

        public final void setContextStorm(@Nullable ParticleStorm particleStorm) {
            contextStorm = particleStorm;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[ParticleEmitterAction.values().length];
            try {
                nArray[ParticleEmitterAction.GO.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ParticleEmitterAction.NOTHING.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ParticleEmitterAction.STOP.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[ParticleEmitterAction.RESET.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

