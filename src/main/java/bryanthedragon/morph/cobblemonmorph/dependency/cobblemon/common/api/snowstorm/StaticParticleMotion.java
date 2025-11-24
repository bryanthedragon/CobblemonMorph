/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotionType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 (2\u00020\u0001:\u0001(B\u0007\u00a2\u0006\u0004\b&\u0010'JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ7\u0010\u0010\u001a\n \u0006*\u0004\u0018\u00010\r0\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J7\u0010\u0015\u001a\n \u0006*\u0004\u0018\u00010\r0\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J'\u0010\u0019\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010 \u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b \u0010\u001fR\u001a\u0010\"\u001a\u00020!8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/StaticParticleMotion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "storm", "Lnet/minecraft/world/phys/Vec3;", "particlePos", "emitterPos", "getInitialVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "velocity", "", "minSpeed", "getParticleDirection", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;", "Lcom/cobblemon/mod/common/client/render/SnowstormParticle;", "particle", "getVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/render/SnowstormParticle;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "<init>", "()V", "Companion", "common"})
public final class StaticParticleMotion
implements ParticleMotion {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final transient ParticleMotionType type = ParticleMotionType.STATIC;
    @NotNull
    private static final Codec<StaticParticleMotion> CODEC;

    @Override
    @NotNull
    public ParticleMotionType getType() {
        return this.type;
    }

    @Override
    public Vec3 getInitialVelocity(@NotNull MoLangRuntime runtime2, @NotNull ParticleStorm storm2, @NotNull Vec3 particlePos, @NotNull Vec3 emitterPos) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)((Object)storm2), (String)"storm");
        Intrinsics.checkNotNullParameter((Object)particlePos, (String)"particlePos");
        Intrinsics.checkNotNullParameter((Object)emitterPos, (String)"emitterPos");
        return Vec3.f_82478_;
    }

    @Override
    @NotNull
    public Vec3 getVelocity(@NotNull MoLangRuntime runtime2, @NotNull SnowstormParticle particle, @NotNull Vec3 velocity) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)((Object)particle), (String)"particle");
        Intrinsics.checkNotNullParameter((Object)velocity, (String)"velocity");
        return velocity;
    }

    @Override
    public Vec3 getParticleDirection(@NotNull MoLangRuntime runtime2, @NotNull ParticleStorm storm2, @NotNull Vec3 velocity, float minSpeed) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)((Object)storm2), (String)"storm");
        Intrinsics.checkNotNullParameter((Object)velocity, (String)"velocity");
        return velocity.m_82541_();
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
    }

    private static final String CODEC$lambda$2$lambda$0(StaticParticleMotion it) {
        return it.getType().name();
    }

    private static final StaticParticleMotion CODEC$lambda$2$lambda$1(String it) {
        return new StaticParticleMotion();
    }

    private static final App CODEC$lambda$2(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(StaticParticleMotion::CODEC$lambda$2$lambda$0)).apply((Applicative)instance, StaticParticleMotion::CODEC$lambda$2$lambda$1);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(StaticParticleMotion::CODEC$lambda$2);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026ticleMotion() }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/StaticParticleMotion$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/StaticParticleMotion;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<StaticParticleMotion> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

