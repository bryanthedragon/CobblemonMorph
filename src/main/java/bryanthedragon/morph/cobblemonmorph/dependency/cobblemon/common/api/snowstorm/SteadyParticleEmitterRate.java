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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterRate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleEmitterRateType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 /2\u00020\u0001:\u0001/B\u001b\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b-\u0010.JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\u000f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0015R\"\u0010\u0018\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001e\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001dR\"\u0010\"\u001a\u00020!8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001a\u0010)\u001a\u00020(8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\u00a8\u00060"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/SteadyParticleEmitterRate;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRate;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "started", "", "currentlyActive", "getEmitCount", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;ZI)I", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "maximum", "Lcom/bedrockk/molang/Expression;", "getMaximum", "()Lcom/bedrockk/molang/Expression;", "setMaximum", "(Lcom/bedrockk/molang/Expression;)V", "rate", "getRate", "setRate", "", "time", "J", "getTime", "()J", "setTime", "(J)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRateType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRateType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleEmitterRateType;", "<init>", "(Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;)V", "Companion", "common"})
public final class SteadyParticleEmitterRate
implements ParticleEmitterRate {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression rate;
    @NotNull
    private Expression maximum;
    private long time;
    @NotNull
    private final ParticleEmitterRateType type;
    @NotNull
    private static final Codec<SteadyParticleEmitterRate> CODEC;

    public SteadyParticleEmitterRate(@NotNull Expression rate, @NotNull Expression maximum) {
        Intrinsics.checkNotNullParameter((Object)rate, (String)"rate");
        Intrinsics.checkNotNullParameter((Object)maximum, (String)"maximum");
        this.rate = rate;
        this.maximum = maximum;
        this.time = System.currentTimeMillis();
        this.type = ParticleEmitterRateType.STEADY;
    }

    public /* synthetic */ SteadyParticleEmitterRate(Expression expression, Expression expression2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            expression = new NumberExpression(0.0);
        }
        if ((n & 2) != 0) {
            expression2 = new NumberExpression(0.0);
        }
        this(expression, expression2);
    }

    @NotNull
    public final Expression getRate() {
        return this.rate;
    }

    public final void setRate(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.rate = expression;
    }

    @NotNull
    public final Expression getMaximum() {
        return this.maximum;
    }

    public final void setMaximum(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.maximum = expression;
    }

    public final long getTime() {
        return this.time;
    }

    public final void setTime(long l) {
        this.time = l;
    }

    @Override
    @NotNull
    public ParticleEmitterRateType getType() {
        return this.type;
    }

    @Override
    public int getEmitCount(@NotNull MoLangRuntime runtime2, boolean started, int currentlyActive) {
        double currentOverflow;
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        int max2 = (int)MoLangExtensionsKt.resolveDouble(runtime2, this.maximum);
        MoStruct moStruct = runtime2.getEnvironment().getStructs().get("variable");
        Intrinsics.checkNotNull((Object)moStruct, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct");
        VariableStruct variables = (VariableStruct)moStruct;
        MoValue moValue = variables.getMap().get("emitter_overflow");
        double d = currentOverflow = moValue != null ? moValue.asDouble() : 0.0;
        if (currentlyActive >= max2) {
            return 0;
        }
        double perSecond = MoLangExtensionsKt.resolveDouble(runtime2, this.rate);
        double trySpawn = perSecond / 20.0 + currentOverflow;
        int intComponent = (int)trySpawn;
        double doubleComponent = trySpawn - (double)intComponent;
        Map<String, MoValue> map = variables.getMap();
        Intrinsics.checkNotNullExpressionValue(map, (String)"variables.map");
        Map<String, MoValue> map2 = map;
        String string = "emitter_overflow";
        DoubleValue doubleValue = new DoubleValue(doubleComponent);
        map2.put(string, doubleValue);
        return Integer.min(intComponent, max2 - currentlyActive);
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        Expression expression = MoLangExtensionsKt.asExpression(string);
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"buffer.readString().asExpression()");
        this.rate = expression;
        String string2 = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"buffer.readString()");
        Expression expression2 = MoLangExtensionsKt.asExpression(string2);
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"buffer.readString().asExpression()");
        this.maximum = expression2;
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString(this.rate));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.maximum));
    }

    private static final String CODEC$lambda$4$lambda$0(SteadyParticleEmitterRate it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$4$lambda$1(SteadyParticleEmitterRate it) {
        return it.rate;
    }

    private static final Expression CODEC$lambda$4$lambda$2(SteadyParticleEmitterRate it) {
        return it.maximum;
    }

    private static final SteadyParticleEmitterRate CODEC$lambda$4$lambda$3(String string, Expression rate, Expression maximum) {
        Intrinsics.checkNotNullExpressionValue((Object)rate, (String)"rate");
        Intrinsics.checkNotNullExpressionValue((Object)maximum, (String)"maximum");
        return new SteadyParticleEmitterRate(rate, maximum);
    }

    private static final App CODEC$lambda$4(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(SteadyParticleEmitterRate::CODEC$lambda$4$lambda$0), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("rate").forGetter(SteadyParticleEmitterRate::CODEC$lambda$4$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("maximum").forGetter(SteadyParticleEmitterRate::CODEC$lambda$4$lambda$2)).apply((Applicative)instance, SteadyParticleEmitterRate::CODEC$lambda$4$lambda$3);
    }

    public SteadyParticleEmitterRate() {
        this(null, null, 3, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(SteadyParticleEmitterRate::CODEC$lambda$4);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026ate, maximum) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/SteadyParticleEmitterRate$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/SteadyParticleEmitterRate;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<SteadyParticleEmitterRate> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

