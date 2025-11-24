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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.InwardsMotionDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotionDirection;
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
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 @2\u00020\u0001:\u0001@BA\u0012\b\b\u0002\u0010*\u001a\u00020)\u0012\b\b\u0002\u00106\u001a\u00020\"\u0012\u001a\b\u0002\u0010#\u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"0!\u0012\b\b\u0002\u00100\u001a\u00020\"\u00a2\u0006\u0004\b>\u0010?JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ/\u0010\u0010\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J7\u0010\u0015\u001a\n \u0006*\u0004\u0018\u00010\r0\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J'\u0010\u0019\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0017\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010 \u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001bH\u0016\u00a2\u0006\u0004\b \u0010\u001fR4\u0010#\u001a\u0014\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\"0!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\"\u0010*\u001a\u00020)8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\"\u00100\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b0\u00101\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\"\u00106\u001a\u00020\"8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b6\u00101\u001a\u0004\b7\u00103\"\u0004\b8\u00105R\u001a\u0010:\u001a\u0002098\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\u00a8\u0006A"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/DynamicParticleMotion;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotion;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "storm", "Lnet/minecraft/world/phys/Vec3;", "particlePos", "emitterPos", "getInitialVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "velocity", "", "minSpeed", "getParticleDirection", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;", "Lcom/cobblemon/mod/common/client/render/SnowstormParticle;", "particle", "getVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/render/SnowstormParticle;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lkotlin/Triple;", "Lcom/bedrockk/molang/Expression;", "acceleration", "Lkotlin/Triple;", "getAcceleration", "()Lkotlin/Triple;", "setAcceleration", "(Lkotlin/Triple;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionDirection;", "direction", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionDirection;", "getDirection", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionDirection;", "setDirection", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionDirection;)V", "drag", "Lcom/bedrockk/molang/Expression;", "getDrag", "()Lcom/bedrockk/molang/Expression;", "setDrag", "(Lcom/bedrockk/molang/Expression;)V", "speed", "getSpeed", "setSpeed", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionType;", "<init>", "(Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionDirection;Lcom/bedrockk/molang/Expression;Lkotlin/Triple;Lcom/bedrockk/molang/Expression;)V", "Companion", "common"})
public final class DynamicParticleMotion
implements ParticleMotion {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private ParticleMotionDirection direction;
    @NotNull
    private Expression speed;
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> acceleration;
    @NotNull
    private Expression drag;
    @NotNull
    private final ParticleMotionType type;
    @NotNull
    private static final Codec<DynamicParticleMotion> CODEC;

    public DynamicParticleMotion(@NotNull ParticleMotionDirection direction, @NotNull Expression speed, @NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> acceleration, @NotNull Expression drag) {
        Intrinsics.checkNotNullParameter((Object)direction, (String)"direction");
        Intrinsics.checkNotNullParameter((Object)speed, (String)"speed");
        Intrinsics.checkNotNullParameter(acceleration, (String)"acceleration");
        Intrinsics.checkNotNullParameter((Object)drag, (String)"drag");
        this.direction = direction;
        this.speed = speed;
        this.acceleration = acceleration;
        this.drag = drag;
        this.type = ParticleMotionType.DYNAMIC;
    }

    public /* synthetic */ DynamicParticleMotion(ParticleMotionDirection particleMotionDirection, Expression expression, Triple triple, Expression expression2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            particleMotionDirection = new InwardsMotionDirection();
        }
        if ((n & 2) != 0) {
            expression = new NumberExpression(0.0);
        }
        if ((n & 4) != 0) {
            triple = new Triple((Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0));
        }
        if ((n & 8) != 0) {
            expression2 = new NumberExpression(0.0);
        }
        this(particleMotionDirection, expression, (Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple, expression2);
    }

    @NotNull
    public final ParticleMotionDirection getDirection() {
        return this.direction;
    }

    public final void setDirection(@NotNull ParticleMotionDirection particleMotionDirection) {
        Intrinsics.checkNotNullParameter((Object)particleMotionDirection, (String)"<set-?>");
        this.direction = particleMotionDirection;
    }

    @NotNull
    public final Expression getSpeed() {
        return this.speed;
    }

    public final void setSpeed(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.speed = expression;
    }

    @NotNull
    public final Triple<Expression, Expression, Expression> getAcceleration() {
        return this.acceleration;
    }

    public final void setAcceleration(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> triple) {
        Intrinsics.checkNotNullParameter(triple, (String)"<set-?>");
        this.acceleration = triple;
    }

    @NotNull
    public final Expression getDrag() {
        return this.drag;
    }

    public final void setDrag(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.drag = expression;
    }

    @Override
    @NotNull
    public ParticleMotionType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Vec3 getInitialVelocity(@NotNull MoLangRuntime runtime2, @NotNull ParticleStorm storm2, @NotNull Vec3 particlePos, @NotNull Vec3 emitterPos) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)((Object)storm2), (String)"storm");
        Intrinsics.checkNotNullParameter((Object)particlePos, (String)"particlePos");
        Intrinsics.checkNotNullParameter((Object)emitterPos, (String)"emitterPos");
        Vec3 vec3 = this.direction.getDirectionVector(runtime2, storm2, emitterPos, particlePos).m_82541_().m_82490_(MoLangExtensionsKt.resolveDouble(runtime2, this.speed));
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"direction.getDirectionVe\u2026ime.resolveDouble(speed))");
        return vec3;
    }

    @Override
    @NotNull
    public Vec3 getVelocity(@NotNull MoLangRuntime runtime2, @NotNull SnowstormParticle particle, @NotNull Vec3 velocity) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)((Object)particle), (String)"particle");
        Intrinsics.checkNotNullParameter((Object)velocity, (String)"velocity");
        Vec3 acceleration = new Vec3(MoLangExtensionsKt.resolveDouble(runtime2, (Expression)this.acceleration.getFirst()), MoLangExtensionsKt.resolveDouble(runtime2, (Expression)this.acceleration.getSecond()), MoLangExtensionsKt.resolveDouble(runtime2, (Expression)this.acceleration.getThird())).m_82546_(velocity.m_82490_((double)20 * MoLangExtensionsKt.resolveDouble(runtime2, this.drag))).m_82490_(0.05).m_82490_(0.05);
        return new Vec3(velocity.f_82479_ + acceleration.f_82479_, velocity.f_82480_ + acceleration.f_82480_, velocity.f_82481_ + acceleration.f_82481_);
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
        this.direction = (ParticleMotionDirection)ParticleMotionDirection.Companion.readFromBuffer(buffer);
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.speed = expression;
        this.acceleration = new Triple((Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression());
        Expression expression2 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.drag = expression2;
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        ParticleMotionDirection.Companion.writeToBuffer(buffer, (CodecMapped)this.direction);
        buffer.m_130070_(MoLangExtensionsKt.getString(this.speed));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.acceleration.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.acceleration.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.acceleration.getThird()));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.drag));
    }

    private static final String CODEC$lambda$8$lambda$0(DynamicParticleMotion it) {
        return it.getType().name();
    }

    private static final ParticleMotionDirection CODEC$lambda$8$lambda$1(DynamicParticleMotion it) {
        return it.direction;
    }

    private static final Expression CODEC$lambda$8$lambda$2(DynamicParticleMotion it) {
        return it.speed;
    }

    private static final Expression CODEC$lambda$8$lambda$3(DynamicParticleMotion it) {
        return (Expression)it.acceleration.getFirst();
    }

    private static final Expression CODEC$lambda$8$lambda$4(DynamicParticleMotion it) {
        return (Expression)it.acceleration.getSecond();
    }

    private static final Expression CODEC$lambda$8$lambda$5(DynamicParticleMotion it) {
        return (Expression)it.acceleration.getThird();
    }

    private static final Expression CODEC$lambda$8$lambda$6(DynamicParticleMotion it) {
        return it.drag;
    }

    private static final DynamicParticleMotion CODEC$lambda$8$lambda$7(String string, ParticleMotionDirection direction, Expression speed, Expression accelX, Expression accelY, Expression accelZ, Expression drag) {
        Intrinsics.checkNotNullExpressionValue((Object)direction, (String)"direction");
        Intrinsics.checkNotNullExpressionValue((Object)speed, (String)"speed");
        Triple triple = new Triple((Object)accelX, (Object)accelY, (Object)accelZ);
        Intrinsics.checkNotNullExpressionValue((Object)drag, (String)"drag");
        return new DynamicParticleMotion(direction, speed, (Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple, drag);
    }

    private static final App CODEC$lambda$8(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$0), (App)ParticleMotionDirection.Companion.getCodec().fieldOf("direction").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("speed").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("accelX").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$3), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("accelY").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$4), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("accelZ").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$5), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("drag").forGetter(DynamicParticleMotion::CODEC$lambda$8$lambda$6)).apply((Applicative)instance, DynamicParticleMotion::CODEC$lambda$8$lambda$7);
    }

    public DynamicParticleMotion() {
        this(null, null, null, null, 15, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(DynamicParticleMotion::CODEC$lambda$8);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026ccelZ), drag) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/DynamicParticleMotion$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/DynamicParticleMotion;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<DynamicParticleMotion> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

