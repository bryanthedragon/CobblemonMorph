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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CurveType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.CubedBezierCurve;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0017\u0018\u0000 C2\u00020\u0001:\u0001CBM\u0012\b\b\u0002\u0010'\u001a\u00020&\u0012\b\b\u0002\u0010#\u001a\u00020\u001c\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u001c\u0012\b\b\u0002\u00102\u001a\u00020\u0010\u0012\b\b\u0002\u00108\u001a\u00020\u0010\u0012\b\b\u0002\u0010;\u001a\u00020\u0010\u0012\b\b\u0002\u0010>\u001a\u00020\u0010\u00a2\u0006\u0004\bA\u0010BJG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0014\u0010\rR\"\u0010\u0016\u001a\u00020\u00158\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\"\u0010\u001d\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\"\u0010#\u001a\u00020\u001c8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010\u001e\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R\"\u0010'\u001a\u00020&8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010.\u001a\u00020-8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b0\u00101R\"\u00102\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b2\u00103\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\"\u00108\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b8\u00103\u001a\u0004\b9\u00105\"\u0004\b:\u00107R\"\u0010;\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b;\u00103\u001a\u0004\b<\u00105\"\u0004\b=\u00107R\"\u0010>\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b>\u00103\u001a\u0004\b?\u00105\"\u0004\b@\u00107\u00a8\u0006D"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BezierMoLangCurve;", "Lcom/cobblemon/mod/common/api/snowstorm/MoLangCurve;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "inputValue", "resolve", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;D)D", "writeToBuffer", "Lcom/cobblemon/mod/common/util/math/CubedBezierCurve;", "curve", "Lcom/cobblemon/mod/common/util/math/CubedBezierCurve;", "getCurve", "()Lcom/cobblemon/mod/common/util/math/CubedBezierCurve;", "setCurve", "(Lcom/cobblemon/mod/common/util/math/CubedBezierCurve;)V", "Lcom/bedrockk/molang/Expression;", "horizontalRange", "Lcom/bedrockk/molang/Expression;", "getHorizontalRange", "()Lcom/bedrockk/molang/Expression;", "setHorizontalRange", "(Lcom/bedrockk/molang/Expression;)V", "input", "getInput", "setInput", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "v0", "D", "getV0", "()D", "setV0", "(D)V", "v1", "getV1", "setV1", "v2", "getV2", "setV2", "v3", "getV3", "setV3", "<init>", "(Ljava/lang/String;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;DDDD)V", "Companion", "common"})
public final class BezierMoLangCurve
implements MoLangCurve {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String name;
    @NotNull
    private Expression input;
    @NotNull
    private Expression horizontalRange;
    private double v0;
    private double v1;
    private double v2;
    private double v3;
    @NotNull
    private CubedBezierCurve curve;
    @NotNull
    private final CurveType type;
    @NotNull
    private static final Codec<BezierMoLangCurve> CODEC;

    public BezierMoLangCurve(@NotNull String name, @NotNull Expression input, @NotNull Expression horizontalRange, double v0, double v1, double v2, double v3) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)horizontalRange, (String)"horizontalRange");
        this.name = name;
        this.input = input;
        this.horizontalRange = horizontalRange;
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.curve = new CubedBezierCurve(this.v0, this.v1, this.v2, this.v3);
        this.type = CurveType.BEZIER;
    }

    public /* synthetic */ BezierMoLangCurve(String string, Expression expression, Expression expression2, double d, double d2, double d3, double d4, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "variable";
        }
        if ((n & 2) != 0) {
            expression = new NumberExpression(0.5);
        }
        if ((n & 4) != 0) {
            expression2 = new NumberExpression(1.0);
        }
        if ((n & 8) != 0) {
            d = 0.0;
        }
        if ((n & 0x10) != 0) {
            d2 = 0.0;
        }
        if ((n & 0x20) != 0) {
            d3 = 0.0;
        }
        if ((n & 0x40) != 0) {
            d4 = 0.0;
        }
        this(string, expression, expression2, d, d2, d3, d4);
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.name = string;
    }

    @Override
    @NotNull
    public Expression getInput() {
        return this.input;
    }

    @Override
    public void setInput(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.input = expression;
    }

    @NotNull
    public final Expression getHorizontalRange() {
        return this.horizontalRange;
    }

    public final void setHorizontalRange(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.horizontalRange = expression;
    }

    public final double getV0() {
        return this.v0;
    }

    public final void setV0(double d) {
        this.v0 = d;
    }

    public final double getV1() {
        return this.v1;
    }

    public final void setV1(double d) {
        this.v1 = d;
    }

    public final double getV2() {
        return this.v2;
    }

    public final void setV2(double d) {
        this.v2 = d;
    }

    public final double getV3() {
        return this.v3;
    }

    public final void setV3(double d) {
        this.v3 = d;
    }

    @NotNull
    public final CubedBezierCurve getCurve() {
        return this.curve;
    }

    public final void setCurve(@NotNull CubedBezierCurve cubedBezierCurve) {
        Intrinsics.checkNotNullParameter((Object)cubedBezierCurve, (String)"<set-?>");
        this.curve = cubedBezierCurve;
    }

    @Override
    @NotNull
    public CurveType getType() {
        return this.type;
    }

    @Override
    public double resolve(@NotNull MoLangRuntime runtime2, double inputValue) {
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        double horizontalRange = MoLangExtensionsKt.resolveDouble(runtime2, this.horizontalRange);
        double position = inputValue / horizontalRange;
        return this.curve.getY(position);
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
        this.setName(string);
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.setInput(expression);
        Expression expression2 = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression2, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.horizontalRange = expression2;
        this.v0 = buffer.readDouble();
        this.v1 = buffer.readDouble();
        this.v2 = buffer.readDouble();
        this.v3 = buffer.readDouble();
        this.curve = new CubedBezierCurve(this.v0, this.v1, this.v2, this.v3);
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.getName());
        buffer.m_130070_(MoLangExtensionsKt.getString(this.getInput()));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.horizontalRange));
        buffer.writeDouble(this.v0);
        buffer.writeDouble(this.v1);
        buffer.writeDouble(this.v2);
        buffer.writeDouble(this.v3);
    }

    @Override
    public void apply(@NotNull MoLangRuntime runtime2) {
        MoLangCurve.DefaultImpls.apply(this, runtime2);
    }

    private static final String CODEC$lambda$9$lambda$0(BezierMoLangCurve it) {
        return it.getType().name();
    }

    private static final String CODEC$lambda$9$lambda$1(BezierMoLangCurve it) {
        return it.getName();
    }

    private static final Expression CODEC$lambda$9$lambda$2(BezierMoLangCurve it) {
        return it.getInput();
    }

    private static final Expression CODEC$lambda$9$lambda$3(BezierMoLangCurve it) {
        return it.horizontalRange;
    }

    private static final Double CODEC$lambda$9$lambda$4(BezierMoLangCurve it) {
        return it.v0;
    }

    private static final Double CODEC$lambda$9$lambda$5(BezierMoLangCurve it) {
        return it.v1;
    }

    private static final Double CODEC$lambda$9$lambda$6(BezierMoLangCurve it) {
        return it.v2;
    }

    private static final Double CODEC$lambda$9$lambda$7(BezierMoLangCurve it) {
        return it.v3;
    }

    private static final BezierMoLangCurve CODEC$lambda$9$lambda$8(String string, String name, Expression input, Expression horizontalRange, Double v0, Double v1, Double v2, Double v3) {
        Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
        Intrinsics.checkNotNullExpressionValue((Object)input, (String)"input");
        Intrinsics.checkNotNullExpressionValue((Object)horizontalRange, (String)"horizontalRange");
        Intrinsics.checkNotNullExpressionValue((Object)v0, (String)"v0");
        double d = v0;
        Intrinsics.checkNotNullExpressionValue((Object)v1, (String)"v1");
        double d2 = v1;
        Intrinsics.checkNotNullExpressionValue((Object)v2, (String)"v2");
        double d3 = v2;
        Intrinsics.checkNotNullExpressionValue((Object)v3, (String)"v3");
        return new BezierMoLangCurve(name, input, horizontalRange, d, d2, d3, v3);
    }

    private static final App CODEC$lambda$9(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$0), (App)PrimitiveCodec.STRING.fieldOf("name").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$1), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("input").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$2), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("horizontalRange").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$3), (App)PrimitiveCodec.DOUBLE.fieldOf("v0").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$4), (App)PrimitiveCodec.DOUBLE.fieldOf("v1").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$5), (App)PrimitiveCodec.DOUBLE.fieldOf("v2").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$6), (App)PrimitiveCodec.DOUBLE.fieldOf("v3").forGetter(BezierMoLangCurve::CODEC$lambda$9$lambda$7)).apply((Applicative)instance, BezierMoLangCurve::CODEC$lambda$9$lambda$8);
    }

    public BezierMoLangCurve() {
        this(null, null, null, 0.0, 0.0, 0.0, 0.0, 127, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(BezierMoLangCurve::CODEC$lambda$9);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026, v1, v2, v3) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BezierMoLangCurve$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/BezierMoLangCurve;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<BezierMoLangCurve> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

