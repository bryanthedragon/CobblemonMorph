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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 02\u00020\u0001:\u00010B/\u0012\b\b\u0002\u0010&\u001a\u00020\u0019\u0012\b\b\u0002\u0010#\u001a\u00020\u0019\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0019\u0012\b\b\u0002\u0010 \u001a\u00020\u0019\u00a2\u0006\u0004\b.\u0010/JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0011J\u0017\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0017R\"\u0010\u001a\u001a\u00020\u00198\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\"\u0010 \u001a\u00020\u00198\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b \u0010\u001b\u001a\u0004\b!\u0010\u001d\"\u0004\b\"\u0010\u001fR\"\u0010#\u001a\u00020\u00198\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010\u001b\u001a\u0004\b$\u0010\u001d\"\u0004\b%\u0010\u001fR\"\u0010&\u001a\u00020\u00198\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010\u001b\u001a\u0004\b'\u0010\u001d\"\u0004\b(\u0010\u001fR\u001a\u0010*\u001a\u00020)8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\u00a8\u00061"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/DynamicParticleRotation;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotation;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "angle", "angularVelocity", "getAngularVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;DD)D", "getInitialAngularVelocity", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)D", "getInitialRotation", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "acceleration", "Lcom/bedrockk/molang/Expression;", "getAcceleration", "()Lcom/bedrockk/molang/Expression;", "setAcceleration", "(Lcom/bedrockk/molang/Expression;)V", "drag", "getDrag", "setDrag", "speed", "getSpeed", "setSpeed", "startRotation", "getStartRotation", "setStartRotation", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotationType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotationType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleRotationType;", "<init>", "(Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;)V", "Companion", "common"})
public final class DynamicParticleRotation
implements ParticleRotation {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression startRotation;
    @NotNull
    private Expression speed;
    @NotNull
    private Expression acceleration;
    @NotNull
    private Expression drag;
    @NotNull
    private final ParticleRotationType type;
    @NotNull
    private static final Codec<DynamicParticleRotation> CODEC;

    public DynamicParticleRotation(@NotNull Expression startRotation, @NotNull Expression speed, @NotNull Expression acceleration, @NotNull Expression drag) {
        Intrinsics.checkNotNullParameter((Object)startRotation, (String)"startRotation");
        Intrinsics.checkNotNullParameter((Object)speed, (String)"speed");
        Intrinsics.checkNotNullParameter((Object)acceleration, (String)"acceleration");
        Intrinsics.checkNotNullParameter((Object)drag, (String)"drag");
        this.startRotation = startRotation;
        this.speed = speed;
        this.acceleration = acceleration;
        this.drag = drag;
        this.type = ParticleRotationType.DYNAMIC;
    }

    public /* synthetic */ DynamicParticleRotation(Expression expression, Expression expression2, Expression expression3, Expression expression4, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            expression = new NumberExpression(0.0);
        }
        if ((n & 2) != 0) {
            expression2 = new NumberExpression(0.0);
        }
        if ((n & 4) != 0) {
            expression3 = new NumberExpression(0.0);
        }
        if ((n & 8) != 0) {
            expression4 = new NumberExpression(0.0);
        }
        this(expression, expression2, expression3, expression4);
    }

    @NotNull
    public final Expression getStartRotation() {
        return this.startRotation;
    }

    public final void setStartRotation(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.startRotation = expression;
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
    public final Expression getAcceleration() {
        return this.acceleration;
    }

    public final void setAcceleration(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.acceleration = expression;
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
        return MoLangExtensionsKt.resolveDouble(runtime2, this.startRotation);
    }

    @Override
    public double getInitialAngularVelocity(@NotNull MoLangRuntime runtime2) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        return MoLangExtensionsKt.resolveDouble(runtime2, this.speed) / (double)20;
    }

    @Override
    public double getAngularVelocity(@NotNull MoLangRuntime runtime2, double angle, double angularVelocity) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        double acceleration = MoLangExtensionsKt.resolveDouble(runtime2, this.acceleration);
        double nextVelocity = angularVelocity * (double)20 + acceleration;
        double drag = nextVelocity * MoLangExtensionsKt.resolveDouble(runtime2, this.drag);
        return angularVelocity + (Math.abs(drag) > Math.abs(nextVelocity) ? 0.0 : nextVelocity - drag - angularVelocity * (double)20);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.startRotation = expression;
        Expression expression2 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.speed = expression2;
        Expression expression3 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression3, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.acceleration = expression3;
        Expression expression4 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression4, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.drag = expression4;
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString(this.startRotation));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.speed));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.acceleration));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.drag));
    }

    private static final String CODEC$lambda$6$lambda$0(DynamicParticleRotation it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$6$lambda$1(DynamicParticleRotation it) {
        return it.startRotation;
    }

    private static final Expression CODEC$lambda$6$lambda$2(DynamicParticleRotation it) {
        return it.speed;
    }

    private static final Expression CODEC$lambda$6$lambda$3(DynamicParticleRotation it) {
        return it.acceleration;
    }

    private static final Expression CODEC$lambda$6$lambda$4(DynamicParticleRotation it) {
        return it.drag;
    }

    private static final DynamicParticleRotation CODEC$lambda$6$lambda$5(String string, Expression startRotation, Expression speed, Expression acceleration, Expression drag) {
        Intrinsics.checkNotNullExpressionValue((Object)startRotation, (String)"startRotation");
        Intrinsics.checkNotNullExpressionValue((Object)speed, (String)"speed");
        Intrinsics.checkNotNullExpressionValue((Object)acceleration, (String)"acceleration");
        Intrinsics.checkNotNullExpressionValue((Object)drag, (String)"drag");
        return new DynamicParticleRotation(startRotation, speed, acceleration, drag);
    }

    private static final App CODEC$lambda$6(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("startRotation").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("speed").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("acceleration").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$3), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("drag").forGetter(DynamicParticleRotation::CODEC$lambda$6$lambda$4)).apply((Applicative)instance, DynamicParticleRotation::CODEC$lambda$6$lambda$5);
    }

    public DynamicParticleRotation() {
        this(null, null, null, null, 15, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(DynamicParticleRotation::CODEC$lambda$6);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/DynamicParticleRotation$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/DynamicParticleRotation;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<DynamicParticleRotation> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

