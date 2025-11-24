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
 *  kotlin.random.Random
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShape;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterShapeType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
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
import kotlin.random.Random;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 22\u00020\u0001:\u00012B7\u0012\u001a\b\u0002\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u0016\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0017\u0012\b\b\u0002\u0010%\u001a\u00020$\u00a2\u0006\u0004\b0\u00101J)\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J!\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ!\u0010\u000f\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u000eJ\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0014R4\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001e\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\"\u0010%\u001a\u00020$8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u001a\u0010,\u001a\u00020+8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/\u00a8\u00063"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/SphereParticleEmitterShape;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/world/phys/Vec3;", "getCenter", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/phys/Vec3;", "getNewParticlePosition", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lkotlin/Triple;", "Lcom/bedrockk/molang/Expression;", "offset", "Lkotlin/Triple;", "getOffset", "()Lkotlin/Triple;", "setOffset", "(Lkotlin/Triple;)V", "radius", "Lcom/bedrockk/molang/Expression;", "getRadius", "()Lcom/bedrockk/molang/Expression;", "setRadius", "(Lcom/bedrockk/molang/Expression;)V", "", "surfaceOnly", "Z", "getSurfaceOnly", "()Z", "setSurfaceOnly", "(Z)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "<init>", "(Lkotlin/Triple;Lcom/bedrockk/molang/Expression;Z)V", "Companion", "common"})
public final class SphereParticleEmitterShape
implements ParticleEmitterShape {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> offset;
    @NotNull
    private Expression radius;
    private boolean surfaceOnly;
    @NotNull
    private final ParticleEmitterShapeType type;
    @NotNull
    private static final Codec<SphereParticleEmitterShape> CODEC;

    public SphereParticleEmitterShape(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> offset, @NotNull Expression radius, boolean surfaceOnly) {
        Intrinsics.checkNotNullParameter(offset, (String)"offset");
        Intrinsics.checkNotNullParameter((Object)radius, (String)"radius");
        this.offset = offset;
        this.radius = radius;
        this.surfaceOnly = surfaceOnly;
        this.type = ParticleEmitterShapeType.SPHERE;
    }

    public /* synthetic */ SphereParticleEmitterShape(Triple triple, Expression expression, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            triple = new Triple((Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0));
        }
        if ((n & 2) != 0) {
            expression = new NumberExpression(0.0);
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        this((Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple, expression, bl);
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
    public final Expression getRadius() {
        return this.radius;
    }

    public final void setRadius(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.radius = expression;
    }

    public final boolean getSurfaceOnly() {
        return this.surfaceOnly;
    }

    public final void setSurfaceOnly(boolean bl) {
        this.surfaceOnly = bl;
    }

    @Override
    @NotNull
    public ParticleEmitterShapeType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        DataResult dataResult = CODEC.encodeStart(ops, (Object)this);
        Intrinsics.checkNotNullExpressionValue((Object)dataResult, (String)"CODEC.encodeStart(ops, this)");
        return dataResult;
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.offset = new Triple((Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression());
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.radius = expression;
        this.surfaceOnly = buffer.readBoolean();
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getThird()));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.radius));
        buffer.writeBoolean(this.surfaceOnly);
    }

    @Override
    @NotNull
    public Vec3 getCenter(@NotNull MoLangRuntime runtime2, @Nullable Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return MoLangExtensionsKt.resolveVec3d(runtime2, this.offset);
    }

    @Override
    @NotNull
    public Vec3 getNewParticlePosition(@NotNull MoLangRuntime runtime2, @Nullable Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        double radius = MoLangExtensionsKt.resolveDouble(runtime2, this.radius) * (this.surfaceOnly ? 1.0 : Random.Default.nextDouble());
        double theta = Math.PI * 2 * Random.Default.nextDouble();
        double psi = Math.PI * 2 * Random.Default.nextDouble();
        Vec3 vec3 = this.getCenter(runtime2, entity2).m_82549_(SimpleMathExtensionsKt.convertSphericalToCartesian(radius, theta, psi));
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"getCenter(runtime, entit\u2026heta = theta, psi = psi))");
        return vec3;
    }

    private static final String CODEC$lambda$7$lambda$0(SphereParticleEmitterShape it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$7$lambda$1(SphereParticleEmitterShape it) {
        return (Expression)it.offset.getFirst();
    }

    private static final Expression CODEC$lambda$7$lambda$2(SphereParticleEmitterShape it) {
        return (Expression)it.offset.getSecond();
    }

    private static final Expression CODEC$lambda$7$lambda$3(SphereParticleEmitterShape it) {
        return (Expression)it.offset.getThird();
    }

    private static final Expression CODEC$lambda$7$lambda$4(SphereParticleEmitterShape it) {
        return it.radius;
    }

    private static final Boolean CODEC$lambda$7$lambda$5(SphereParticleEmitterShape it) {
        return it.surfaceOnly;
    }

    private static final SphereParticleEmitterShape CODEC$lambda$7$lambda$6(String string, Expression offsetX, Expression offsetY, Expression offsetZ, Expression radius, Boolean surfaceOnly) {
        Triple triple = new Triple((Object)offsetX, (Object)offsetY, (Object)offsetZ);
        Intrinsics.checkNotNullExpressionValue((Object)radius, (String)"radius");
        Intrinsics.checkNotNullExpressionValue((Object)surfaceOnly, (String)"surfaceOnly");
        return new SphereParticleEmitterShape((Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple, radius, surfaceOnly);
    }

    private static final App CODEC$lambda$7(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetX").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetY").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetZ").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$3), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("radius").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$4), (App)PrimitiveCodec.BOOL.fieldOf("surfaceOnly").forGetter(SphereParticleEmitterShape::CODEC$lambda$7$lambda$5)).apply((Applicative)instance, SphereParticleEmitterShape::CODEC$lambda$7$lambda$6);
    }

    public SphereParticleEmitterShape() {
        this(null, null, false, 7, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(SphereParticleEmitterShape::CODEC$lambda$7);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/SphereParticleEmitterShape$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/SphereParticleEmitterShape;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<SphereParticleEmitterShape> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

