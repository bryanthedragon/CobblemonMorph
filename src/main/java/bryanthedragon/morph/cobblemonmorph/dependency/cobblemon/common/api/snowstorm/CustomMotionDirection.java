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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotionDirection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleMotionDirectionType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.ParticleStorm;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.Matrix4fExtensionsKt;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 &2\u00020\u0001:\u0001&B#\u0012\u001a\b\u0002\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00190\u0018\u00a2\u0006\u0004\b%\u0010\u001fJG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ/\u0010\u0010\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0016R4\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00190\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010!\u001a\u00020 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/CustomMotionDirection;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionDirection;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/cobblemon/mod/common/client/particle/ParticleStorm;", "storm", "Lnet/minecraft/world/phys/Vec3;", "emitterPos", "particlePos", "getDirectionVector", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;Lcom/cobblemon/mod/common/client/particle/ParticleStorm;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lkotlin/Triple;", "Lcom/bedrockk/molang/Expression;", "direction", "Lkotlin/Triple;", "getDirection", "()Lkotlin/Triple;", "setDirection", "(Lkotlin/Triple;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionDirectionType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionDirectionType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleMotionDirectionType;", "<init>", "Companion", "common"})
public final class CustomMotionDirection
implements ParticleMotionDirection {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Triple<? extends Expression, ? extends Expression, ? extends Expression> direction;
    @NotNull
    private final ParticleMotionDirectionType type;
    @NotNull
    private static final Codec<CustomMotionDirection> CODEC;

    public CustomMotionDirection(@NotNull Triple<? extends Expression, ? extends Expression, ? extends Expression> direction) {
        Intrinsics.checkNotNullParameter(direction, (String)"direction");
        this.direction = direction;
        this.type = ParticleMotionDirectionType.CUSTOM;
    }

    public /* synthetic */ CustomMotionDirection(Triple triple, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            triple = new Triple((Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0), (Object)new NumberExpression(0.0));
        }
        this((Triple<? extends Expression, ? extends Expression, ? extends Expression>)triple);
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
    public ParticleMotionDirectionType getType() {
        return this.type;
    }

    @Override
    @NotNull
    public Vec3 getDirectionVector(@NotNull MoLangRuntime runtime2, @NotNull ParticleStorm storm2, @NotNull Vec3 emitterPos, @NotNull Vec3 particlePos) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        Intrinsics.checkNotNullParameter((Object)((Object)storm2), (String)"storm");
        Intrinsics.checkNotNullParameter((Object)emitterPos, (String)"emitterPos");
        Intrinsics.checkNotNullParameter((Object)particlePos, (String)"particlePos");
        Vec3 v = new Vec3(MoLangExtensionsKt.resolveDouble(runtime2, (Expression)this.direction.getFirst()), MoLangExtensionsKt.resolveDouble(runtime2, (Expression)this.direction.getSecond()), MoLangExtensionsKt.resolveDouble(runtime2, (Expression)this.direction.getThird()));
        return Matrix4fExtensionsKt.transformDirection(storm2.getMatrixWrapper().getMatrix(), v);
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.direction = new Triple((Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression(), (Object)MoLang.createParser(buffer.m_130277_()).parseExpression());
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.direction.getFirst()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.direction.getSecond()));
        buffer.m_130070_(MoLangExtensionsKt.getString((Expression)this.direction.getThird()));
    }

    private static final String CODEC$lambda$5$lambda$0(CustomMotionDirection it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$5$lambda$1(CustomMotionDirection it) {
        return (Expression)it.direction.getFirst();
    }

    private static final Expression CODEC$lambda$5$lambda$2(CustomMotionDirection it) {
        return (Expression)it.direction.getSecond();
    }

    private static final Expression CODEC$lambda$5$lambda$3(CustomMotionDirection it) {
        return (Expression)it.direction.getThird();
    }

    private static final CustomMotionDirection CODEC$lambda$5$lambda$4(String string, Expression dirX, Expression dirY, Expression dirZ) {
        return new CustomMotionDirection((Triple<? extends Expression, ? extends Expression, ? extends Expression>)new Triple((Object)dirX, (Object)dirY, (Object)dirZ));
    }

    private static final App CODEC$lambda$5(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(CustomMotionDirection::CODEC$lambda$5$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionX").forGetter(CustomMotionDirection::CODEC$lambda$5$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionY").forGetter(CustomMotionDirection::CODEC$lambda$5$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("directionZ").forGetter(CustomMotionDirection::CODEC$lambda$5$lambda$3)).apply((Applicative)instance, CustomMotionDirection::CODEC$lambda$5$lambda$4);
    }

    public CustomMotionDirection() {
        this(null, 1, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(CustomMotionDirection::CODEC$lambda$5);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026 dirY, dirZ)) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/CustomMotionDirection$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/CustomMotionDirection;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<CustomMotionDirection> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

