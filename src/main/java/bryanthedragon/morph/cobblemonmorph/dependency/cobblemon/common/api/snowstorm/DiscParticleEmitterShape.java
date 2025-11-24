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
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Matrix3f
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
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 62\u00020\u0001:\u00016BS\u0012\u001a\b\u0002\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u0017\u0012\b\b\u0002\u0010\"\u001a\u00020\u0018\u0012\u001a\b\u0002\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u0017\u0012\b\b\u0002\u0010)\u001a\u00020(\u00a2\u0006\u0004\b4\u00105JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ!\u0010\u000e\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ!\u0010\u0010\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\b\u0010\f\u001a\u0004\u0018\u00010\u000bH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\u0017\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0015R4\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR4\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010\u001a\u001a\u0004\b \u0010\u001c\"\u0004\b!\u0010\u001eR\"\u0010\"\u001a\u00020\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\"\u0010)\u001a\u00020(8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.R\u001a\u00100\u001a\u00020/8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b0\u00101\u001a\u0004\b2\u00103\u00a8\u00067"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/DiscParticleEmitterShape;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/world/phys/Vec3;", "getCenter", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/phys/Vec3;", "getNewParticlePosition", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lkotlin/Triple;", "Lcom/bedrockk/molang/Expression;", "normal", "Lkotlin/Triple;", "getNormal", "()Lkotlin/Triple;", "setNormal", "(Lkotlin/Triple;)V", "offset", "getOffset", "setOffset", "radius", "Lcom/bedrockk/molang/Expression;", "getRadius", "()Lcom/bedrockk/molang/Expression;", "setRadius", "(Lcom/bedrockk/molang/Expression;)V", "", "surfaceOnly", "Z", "getSurfaceOnly", "()Z", "setSurfaceOnly", "(Z)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "<init>", "(Lkotlin/Triple;Lcom/bedrockk/molang/Expression;Lkotlin/Triple;Z)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nParticleEmitterShape.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleEmitterShape.kt\ncom/cobblemon/mod/common/api/snowstorm/DiscParticleEmitterShape\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,384:1\n1#2:385\n*E\n"})
public final class DiscParticleEmitterShape
implements ParticleEmitterShape {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> offset;
    @NotNull
    private Expression radius;
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> normal;
    private boolean surfaceOnly;
    @NotNull
    private final ParticleEmitterShapeType type;
    @NotNull
    private static final Codec<DiscParticleEmitterShape> CODEC;

    public DiscParticleEmitterShape(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> offset, @NotNull Expression radius, @NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> normal2, boolean surfaceOnly) {
        Intrinsics.checkNotNullParameter(offset, (String)"offset");
        Intrinsics.checkNotNullParameter((Object)radius, (String)"radius");
        Intrinsics.checkNotNullParameter(normal2, (String)"normal");
        this.offset = offset;
        this.radius = radius;
        this.normal = normal2;
        this.surfaceOnly = surfaceOnly;
        this.type = ParticleEmitterShapeType.DISC;
    }

    public /* synthetic */ DiscParticleEmitterShape(Triple triple, Expression expression, Triple triple2, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            triple = new Triple((Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0));
        }
        if ((n & 2) != 0) {
            expression = MoLangExtensionsKt.asExpression(0.0);
        }
        if ((n & 4) != 0) {
            triple2 = new Triple((Object)new NumberExpression(0.0), (Object)new NumberExpression(1.0), (Object)new NumberExpression(0.0));
        }
        if ((n & 8) != 0) {
            bl = false;
        }
        this((Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple, expression, (Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple2, bl);
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

    @NotNull
    public final Triple<Expression, Expression, Expression> getNormal() {
        return this.normal;
    }

    public final void setNormal(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> triple) {
        Intrinsics.checkNotNullParameter(triple, (String)"<set-?>");
        this.normal = triple;
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
    public Vec3 getNewParticlePosition(@NotNull MoLangRuntime runtime2, @Nullable Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Vec3 center = this.getCenter(runtime2, entity2);
        Vec3 it = MoLangExtensionsKt.resolveVec3d(runtime2, this.normal);
        boolean bl = false;
        Vec3 normal2 = (Intrinsics.areEqual((Object)it, (Object)Vec3.f_82478_) ? new Vec3(0.0, 1.0, 0.0) : it).m_82541_();
        Vec3 baseLine = new Vec3(0.0, 1.0, 0.0);
        double radius = MoLangExtensionsKt.resolveDouble(runtime2, this.radius);
        Intrinsics.checkNotNullExpressionValue((Object)normal2, (String)"normal");
        Matrix3f rotation = SimpleMathExtensionsKt.getRotationMatrix(baseLine, normal2);
        double distance = this.surfaceOnly ? radius : Random.Default.nextDouble(radius);
        double theta = Random.Default.nextDouble() * (double)2 * Math.PI;
        double x = distance * Math.cos(theta);
        double z = distance * Math.sin(theta);
        Vec3 displacement = SimpleMathExtensionsKt.times(rotation, new Vec3(x, 0.0, z));
        Vec3 vec3 = center.m_82549_(displacement);
        Intrinsics.checkNotNullExpressionValue((Object)vec3, (String)"center.add(displacement)");
        return vec3;
    }

    @Override
    @NotNull
    public Vec3 getCenter(@NotNull MoLangRuntime runtime2, @Nullable Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return MoLangExtensionsKt.resolveVec3d(runtime2, this.offset);
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
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.radius = expression;
        this.normal = new Triple((Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression());
        this.surfaceOnly = buffer.readBoolean();
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getThird()));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.radius));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.normal.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.normal.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.normal.getThird()));
        buffer.writeBoolean(this.surfaceOnly);
    }

    private static final String CODEC$lambda$11$lambda$1(DiscParticleEmitterShape it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$11$lambda$2(DiscParticleEmitterShape it) {
        return (Expression)it.offset.getFirst();
    }

    private static final Expression CODEC$lambda$11$lambda$3(DiscParticleEmitterShape it) {
        return (Expression)it.offset.getSecond();
    }

    private static final Expression CODEC$lambda$11$lambda$4(DiscParticleEmitterShape it) {
        return (Expression)it.offset.getThird();
    }

    private static final Expression CODEC$lambda$11$lambda$5(DiscParticleEmitterShape it) {
        return it.radius;
    }

    private static final Expression CODEC$lambda$11$lambda$6(DiscParticleEmitterShape it) {
        return (Expression)it.normal.getFirst();
    }

    private static final Expression CODEC$lambda$11$lambda$7(DiscParticleEmitterShape it) {
        return (Expression)it.normal.getSecond();
    }

    private static final Expression CODEC$lambda$11$lambda$8(DiscParticleEmitterShape it) {
        return (Expression)it.normal.getThird();
    }

    private static final Boolean CODEC$lambda$11$lambda$9(DiscParticleEmitterShape it) {
        return it.surfaceOnly;
    }

    private static final DiscParticleEmitterShape CODEC$lambda$11$lambda$10(String string, Expression offsetX, Expression offsetY, Expression offsetZ, Expression radius, Expression normalX, Expression normalY, Expression normalZ, Boolean surfaceOnly) {
        Triple triple = new Triple((Object)offsetX, (Object)offsetY, (Object)offsetZ);
        Intrinsics.checkNotNullExpressionValue((Object)radius, (String)"radius");
        Triple triple2 = new Triple((Object)normalX, (Object)normalY, (Object)normalZ);
        Intrinsics.checkNotNullExpressionValue((Object)surfaceOnly, (String)"surfaceOnly");
        return new DiscParticleEmitterShape((Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple, radius, (Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple2, surfaceOnly);
    }

    private static final App CODEC$lambda$11(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetX").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetY").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$3), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetZ").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$4), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("radius").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$5), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("normalX").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$6), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("normalY").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$7), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("normalZ").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$8), (App)PrimitiveCodec.BOOL.fieldOf("surfaceOnly").forGetter(DiscParticleEmitterShape::CODEC$lambda$11$lambda$9)).apply((Applicative)instance, DiscParticleEmitterShape::CODEC$lambda$11$lambda$10);
    }

    public DiscParticleEmitterShape() {
        this(null, null, null, false, 15, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(DiscParticleEmitterShape::CODEC$lambda$11);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/DiscParticleEmitterShape$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/DiscParticleEmitterShape;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<DiscParticleEmitterShape> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

