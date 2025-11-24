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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 /2\u00020\u0001:\u0001/BI\u0012\u001a\b\u0002\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u0016\u0012\u001a\b\u0002\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u0016\u0012\b\b\u0002\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b-\u0010.J)\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J!\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ!\u0010\u000f\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u000eJ\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0014R4\u0010\u0018\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR4\u0010\u001e\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00170\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001dR\"\u0010\"\u001a\u00020!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010)\u001a\u00020(8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\u00a8\u00060"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BoxParticleEmitterShape;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShape;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/world/phys/Vec3;", "getCenter", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/phys/Vec3;", "getNewParticlePosition", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lkotlin/Triple;", "Lcom/bedrockk/molang/Expression;", "boxSize", "Lkotlin/Triple;", "getBoxSize", "()Lkotlin/Triple;", "setBoxSize", "(Lkotlin/Triple;)V", "offset", "getOffset", "setOffset", "", "surfaceOnly", "Z", "getSurfaceOnly", "()Z", "setSurfaceOnly", "(Z)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterShapeType;", "<init>", "(Lkotlin/Triple;Lkotlin/Triple;Z)V", "Companion", "common"})
public final class BoxParticleEmitterShape
implements ParticleEmitterShape {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> offset;
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> boxSize;
    private boolean surfaceOnly;
    @NotNull
    private final ParticleEmitterShapeType type;
    @NotNull
    private static final Codec<BoxParticleEmitterShape> CODEC;

    public BoxParticleEmitterShape(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> offset, @NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> boxSize, boolean surfaceOnly) {
        Intrinsics.checkNotNullParameter(offset, (String)"offset");
        Intrinsics.checkNotNullParameter(boxSize, (String)"boxSize");
        this.offset = offset;
        this.boxSize = boxSize;
        this.surfaceOnly = surfaceOnly;
        this.type = ParticleEmitterShapeType.BOX;
    }

    public /* synthetic */ BoxParticleEmitterShape(Triple triple, Triple triple2, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            triple = new Triple((Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0));
        }
        if ((n & 2) != 0) {
            triple2 = new Triple((Object)new NumberExpression(1.0), (Object)new NumberExpression(1.0), (Object)new NumberExpression(1.0));
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        this((Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple, (Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple2, bl);
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
    public final Triple<Expression, Expression, Expression> getBoxSize() {
        return this.boxSize;
    }

    public final void setBoxSize(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> triple) {
        Intrinsics.checkNotNullParameter(triple, (String)"<set-?>");
        this.boxSize = triple;
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
        this.boxSize = new Triple((Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression());
        this.surfaceOnly = buffer.readBoolean();
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.offset.getThird()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.boxSize.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.boxSize.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.boxSize.getThird()));
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
        Vec3 vec3;
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Vec3 center = this.getCenter(runtime2, entity2);
        Vec3 sizes = MoLangExtensionsKt.resolveVec3d(runtime2, this.boxSize).m_82490_(2.0).m_82520_(1.0E-4, 1.0E-4, 1.0E-4);
        if (this.surfaceOnly) {
            switch (Random.Default.nextInt(6)) {
                case 0: {
                    vec3 = new Vec3(-0.5 * sizes.f_82479_, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0);
                    break;
                }
                case 1: {
                    vec3 = new Vec3(0.5 * sizes.f_82479_, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0);
                    break;
                }
                case 2: {
                    vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0, -0.5 * sizes.f_82480_, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0);
                    break;
                }
                case 3: {
                    vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0, 0.5 * sizes.f_82480_, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0);
                    break;
                }
                case 4: {
                    vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0, -0.5 * sizes.f_82481_);
                    break;
                }
                default: {
                    vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0, 0.5 * sizes.f_82481_);
                    break;
                }
            }
        } else {
            vec3 = new Vec3(Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / (double)2, Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / (double)2, Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / (double)2);
        }
        Vec3 disposition = vec3;
        Vec3 vec32 = center.m_82549_(disposition);
        Intrinsics.checkNotNullExpressionValue((Object)vec32, (String)"center.add(disposition)");
        return vec32;
    }

    private static final String CODEC$lambda$9$lambda$0(BoxParticleEmitterShape it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$9$lambda$1(BoxParticleEmitterShape it) {
        return (Expression)it.offset.getFirst();
    }

    private static final Expression CODEC$lambda$9$lambda$2(BoxParticleEmitterShape it) {
        return (Expression)it.offset.getSecond();
    }

    private static final Expression CODEC$lambda$9$lambda$3(BoxParticleEmitterShape it) {
        return (Expression)it.offset.getThird();
    }

    private static final Expression CODEC$lambda$9$lambda$4(BoxParticleEmitterShape it) {
        return (Expression)it.boxSize.getFirst();
    }

    private static final Expression CODEC$lambda$9$lambda$5(BoxParticleEmitterShape it) {
        return (Expression)it.boxSize.getSecond();
    }

    private static final Expression CODEC$lambda$9$lambda$6(BoxParticleEmitterShape it) {
        return (Expression)it.boxSize.getThird();
    }

    private static final Boolean CODEC$lambda$9$lambda$7(BoxParticleEmitterShape it) {
        return it.surfaceOnly;
    }

    private static final BoxParticleEmitterShape CODEC$lambda$9$lambda$8(String string, Expression offsetX, Expression offsetY, Expression offsetZ, Expression boxX, Expression boxY, Expression boxZ, Boolean surfaceOnly) {
        Triple triple = new Triple((Object)offsetX, (Object)offsetY, (Object)offsetZ);
        Triple triple2 = new Triple((Object)boxX, (Object)boxY, (Object)boxZ);
        Intrinsics.checkNotNullExpressionValue((Object)surfaceOnly, (String)"surfaceOnly");
        return new BoxParticleEmitterShape((Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple, (Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple2, surfaceOnly);
    }

    private static final App CODEC$lambda$9(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetX").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetY").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("offsetZ").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$3), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeX").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$4), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeY").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$5), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("sizeZ").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$6), (App)PrimitiveCodec.BOOL.fieldOf("surfaceOnly").forGetter(BoxParticleEmitterShape::CODEC$lambda$9$lambda$7)).apply((Applicative)instance, BoxParticleEmitterShape::CODEC$lambda$9$lambda$8);
    }

    public BoxParticleEmitterShape() {
        this(null, null, false, 7, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(BoxParticleEmitterShape::CODEC$lambda$9);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BoxParticleEmitterShape$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/BoxParticleEmitterShape;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<BoxParticleEmitterShape> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

