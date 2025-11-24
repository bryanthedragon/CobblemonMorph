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
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleRotation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleRotationType;
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
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 &2\u00020\u0001:\u0001&B\u0011\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b%\u0010\u001fJG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0011J\u0017\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0017R\"\u0010\u001a\u001a\u00020\u00198\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010!\u001a\u00020 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParametricParticleRotation;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotation;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "angle", "angularVelocity", "getAngularVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;DD)D", "getInitialAngularVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)D", "getInitialRotation", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "expression", "Lcom/bedrockk/molang/Expression;", "getExpression", "()Lcom/bedrockk/molang/Expression;", "setExpression", "(Lcom/bedrockk/molang/Expression;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotationType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotationType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotationType;", "<init>", "Companion", "common"})
public final class ParametricParticleRotation
implements ParticleRotation {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression expression;
    @NotNull
    private final ParticleRotationType type;
    @NotNull
    private static final Codec<ParametricParticleRotation> CODEC;

    public ParametricParticleRotation(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"expression");
        this.expression = expression;
        this.type = ParticleRotationType.PARAMETRIC;
    }

    public /* synthetic */ ParametricParticleRotation(Expression expression, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            expression = new NumberExpression(0.0);
        }
        this(expression);
    }

    @NotNull
    public final Expression getExpression() {
        return this.expression;
    }

    public final void setExpression(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.expression = expression;
    }

    @Override
    @NotNull
    public ParticleRotationType getType() {
        return this.type;
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public double getInitialRotation(@NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return MoLangExtensionsKt.resolveDouble(runtime2, this.expression);
    }

    @Override
    public double getInitialAngularVelocity(@NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return 0.0;
    }

    @Override
    public double getAngularVelocity(@NotNull MoLangRuntime runtime2, double angle, double angularVelocity) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return MoLangExtensionsKt.resolveDouble(runtime2, this.expression) - angle;
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.expression = expression;
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString(this.expression));
    }

    private static final String CODEC$lambda$3$lambda$0(ParametricParticleRotation it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$3$lambda$1(ParametricParticleRotation it) {
        return it.expression;
    }

    private static final ParametricParticleRotation CODEC$lambda$3$lambda$2(String string, Expression expression) {
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"expression");
        return new ParametricParticleRotation(expression);
    }

    private static final App CODEC$lambda$3(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(ParametricParticleRotation::CODEC$lambda$3$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("expression").forGetter(ParametricParticleRotation::CODEC$lambda$3$lambda$1)).apply((Applicative)instance, ParametricParticleRotation::CODEC$lambda$3$lambda$2);
    }

    public ParametricParticleRotation() {
        this(null, 1, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(ParametricParticleRotation::CODEC$lambda$3);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/ParametricParticleRotation$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/ParametricParticleRotation;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<ParametricParticleRotation> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

