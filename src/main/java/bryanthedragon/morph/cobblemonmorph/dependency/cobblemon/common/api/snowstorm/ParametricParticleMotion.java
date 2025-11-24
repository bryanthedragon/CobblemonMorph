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
 *  kotlin.Triple
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotionType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.SnowstormParticle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 32\u00020\u0001:\u00013B?\u0012\u001a\b\u0002\u0010)\u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"0!\u0012\u001a\b\u0002\u0010#\u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"0!\u00a2\u0006\u0004\b1\u00102JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ7\u0010\u0010\u001a\n \u0006*\u0004\u0018\u00010\r0\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J7\u0010\u0015\u001a\n \u0006*\u0004\u0018\u00010\r0\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J'\u0010\u0019\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010 \u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b \u0010\u001fR4\u0010#\u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"0!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R4\u0010)\u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"0!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010$\u001a\u0004\b*\u0010&\"\u0004\b+\u0010(R\u001a\u0010-\u001a\u00020,8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100\u00a8\u00064"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParametricParticleMotion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "storm", "Lnet/minecraft/world/phys/Vec3;", "particlePos", "emitterPos", "getInitialVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "velocity", "", "minSpeed", "getParticleDirection", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;", "Lcom/cobblemon/mod/common/client/render/SnowstormParticle;", "particle", "getVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/render/SnowstormParticle;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lkotlin/Triple;", "Lcom/bedrockk/molang/Expression;", "direction", "Lkotlin/Triple;", "getDirection", "()Lkotlin/Triple;", "setDirection", "(Lkotlin/Triple;)V", "offset", "getOffset", "setOffset", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "<init>", "(Lkotlin/Triple;Lkotlin/Triple;)V", "Companion", "common"})
public final class ParametricParticleMotion
implements ParticleMotion {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> offset;
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> direction;
    @NotNull
    private final ParticleMotionType type;
    @NotNull
    private static final Codec<ParametricParticleMotion> CODEC;

    public ParametricParticleMotion(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> offset, @NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> direction) {
        Intrinsics.checkNotNullParameter(offset, (String)"offset");
        Intrinsics.checkNotNullParameter(direction, (String)"direction");
        this.offset = offset;
        this.direction = direction;
        this.type = ParticleMotionType.PARAMETRIC;
    }

    public /* synthetic */ ParametricParticleMotion(Triple triple, Triple triple2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            triple = new Triple((Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0));
        }
        if ((n & 2) != 0) {
            triple2 = new Triple((Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0));
        }
        this((Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple, (Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple2);
    }

    @NotNull
    public final Triple<Expression, Expression, Expression> getOffset() {
        return this.offset;
    }

    public final void setOffset(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> triple) {
        Intrinsics.checkNotNullParameter(triple, (String)"<set-?>");
        this.offset = triple;
    }

    @NotNull
    public final Triple<Expression, Expression, Expression> getDirection() {
        return this.direction;
    }

    public final void setDirection(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> triple) {
        Intrinsics.checkNotNullParameter(triple, (String)"<set-?>");
        this.direction = triple;
    }

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
        Vec3 stormPosition = new Vec3(particle.getStorm().getX(), particle.getStorm().getY(), particle.getStorm().getZ());
        Vec3 offset = MoLangExtensionsKt.resolveVec3d(runtime2, this.offset);
        Vec3 particlePosition = new Vec3(particle.getX(), particle.getY(), particle.getZ());
        Vec3 desiredPosition = stormPosition.m_82549_(offset);
        Vec3 vec3 = desiredPosition.m_82546_(particlePosition);
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"desiredPosition.subtract(particlePosition)");
        return vec3;
    }

    @Override
    public Vec3 getParticleDirection(@NotNull MoLangRuntime runtime2, @NotNull ParticleStorm storm2, @NotNull Vec3 velocity, float minSpeed) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)((Object)storm2), (String)"storm");
        Intrinsics.checkNotNullParameter((Object)velocity, (String)"velocity");
        return MoLangExtensionsKt.resolveVec3d(runtime2, this.direction).m_82541_();
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.offset = new Triple((Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression());
        this.direction = new Triple((Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression());
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getThird()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.direction.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.direction.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.direction.getThird()));
    }

    private static final String CODEC$lambda$4$lambda$0(ParametricParticleMotion it) {
        return it.getType().name();
    }

    private static final List CODEC$lambda$4$lambda$1(ParametricParticleMotion it) {
        Object[] objectArray = new Expression[]{it.offset.getFirst(), it.offset.getSecond(), it.offset.getThird()};
        return CollectionsKt.listOf((Object[])objectArray);
    }

    private static final List CODEC$lambda$4$lambda$2(ParametricParticleMotion it) {
        Object[] objectArray = new Expression[]{it.direction.getFirst(), it.direction.getSecond(), it.direction.getThird()};
        return CollectionsKt.listOf((Object[])objectArray);
    }

    private static final ParametricParticleMotion CODEC$lambda$4$lambda$3(String string, List offset, List direction) {
        return new ParametricParticleMotion((Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple(offset.get(0), offset.get(1), offset.get(2)), (Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple(direction.get(0), direction.get(1), direction.get(2)));
    }

    private static final App CODEC$lambda$4(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(ParametricParticleMotion::CODEC$lambda$4$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().listOf().fieldOf("offset").forGetter(ParametricParticleMotion::CODEC$lambda$4$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().listOf().fieldOf("direction").forGetter(ParametricParticleMotion::CODEC$lambda$4$lambda$2)).apply((Applicative)instance, ParametricParticleMotion::CODEC$lambda$4$lambda$3);
    }

    public ParametricParticleMotion() {
        this(null, null, 3, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(ParametricParticleMotion::CODEC$lambda$4);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026irection[2])) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParametricParticleMotion$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/ParametricParticleMotion;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<ParametricParticleMotion> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

