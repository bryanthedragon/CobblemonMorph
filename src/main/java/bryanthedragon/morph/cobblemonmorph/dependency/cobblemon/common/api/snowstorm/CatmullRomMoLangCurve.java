/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.codecs.ListCodec
 *  com.mojang.serialization.codecs.PrimitiveCodec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  com.mojang.serialization.codecs.RecordCodecBuilder$Instance
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.CatmullRomCurve;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 ;2\u00020\u0001:\u0001;B5\u0012\b\b\u0002\u0010'\u001a\u00020&\u0012\b\b\u0002\u0010#\u001a\u00020\u001c\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u001c\u0012\u000e\b\u0002\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00100-\u00a2\u0006\u0004\b9\u0010:JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0014\u0010\rR\"\u0010\u0016\u001a\u00020\u00158\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\"\u0010\u001d\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\"\u0010#\u001a\u00020\u001c8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010\u001e\u001a\u0004\b$\u0010 \"\u0004\b%\u0010\"R\"\u0010'\u001a\u00020&8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R(\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00100-8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010/\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00105\u001a\u0002048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b5\u00106\u001a\u0004\b7\u00108\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/CatmullRomMoLangCurve;", "Lcom/cobblemon/mod/common/api/snowstorm/MoLangCurve;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "inputValue", "resolve", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;D)D", "writeToBuffer", "Lcom/cobblemon/mod/common/util/math/CatmullRomCurve;", "curve", "Lcom/cobblemon/mod/common/util/math/CatmullRomCurve;", "getCurve", "()Lcom/cobblemon/mod/common/util/math/CatmullRomCurve;", "setCurve", "(Lcom/cobblemon/mod/common/util/math/CatmullRomCurve;)V", "Lcom/bedrockk/molang/Expression;", "horizontalRange", "Lcom/bedrockk/molang/Expression;", "getHorizontalRange", "()Lcom/bedrockk/molang/Expression;", "setHorizontalRange", "(Lcom/bedrockk/molang/Expression;)V", "input", "getInput", "setInput", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "", "nodes", "Ljava/util/List;", "getNodes", "()Ljava/util/List;", "setNodes", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "<init>", "(Ljava/lang/String;Lcom/bedrockk/molang/Expression;Lcom/bedrockk/molang/Expression;Ljava/util/List;)V", "Companion", "common"})
public final class CatmullRomMoLangCurve
implements MoLangCurve {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String name;
    @NotNull
    private Expression input;
    @NotNull
    private Expression horizontalRange;
    @NotNull
    private List<Double> nodes;
    @NotNull
    private CatmullRomCurve curve;
    @NotNull
    private final CurveType type;
    @NotNull
    private static final Codec<CatmullRomMoLangCurve> CODEC;

    public CatmullRomMoLangCurve(@NotNull String name, @NotNull Expression input, @NotNull Expression horizontalRange, @NotNull List<Double> nodes) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter((Object)horizontalRange, (String)"horizontalRange");
        Intrinsics.checkNotNullParameter(nodes, (String)"nodes");
        this.name = name;
        this.input = input;
        this.horizontalRange = horizontalRange;
        this.nodes = nodes;
        this.curve = new CatmullRomCurve(this.nodes);
        this.type = CurveType.CATMULL_ROM;
    }

    public /* synthetic */ CatmullRomMoLangCurve(String string, Expression expression, Expression expression2, List list, int n, DefaultConstructorMarker defaultConstructorMarker) {
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
            list = CollectionsKt.emptyList();
        }
        this(string, expression, expression2, list);
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

    @NotNull
    public final List<Double> getNodes() {
        return this.nodes;
    }

    public final void setNodes(@NotNull List<Double> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.nodes = list;
    }

    @NotNull
    public final CatmullRomCurve getCurve() {
        return this.curve;
    }

    public final void setCurve(@NotNull CatmullRomCurve catmullRomCurve) {
        Intrinsics.checkNotNullParameter((Object)catmullRomCurve, (String)"<set-?>");
        this.curve = catmullRomCurve;
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
        int segments = this.nodes.size() - 3;
        double position = inputValue / horizontalRange * (double)segments;
        double pso = (position + 1.0) / (double)(segments + 2);
        return this.curve.getY(pso);
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
        List list = buffer.m_236845_(arg_0 -> CatmullRomMoLangCurve.readFromBuffer$lambda$0(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { buffer.readDouble() }");
        this.nodes = list;
        this.curve = new CatmullRomCurve(this.nodes);
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.getName());
        buffer.m_130070_(MoLangExtensionsKt.getString(this.getInput()));
        buffer.m_130070_(MoLangExtensionsKt.getString(this.horizontalRange));
        buffer.m_236828_((Collection)this.nodes, CatmullRomMoLangCurve::writeToBuffer$lambda$1);
    }

    @Override
    public void apply(@NotNull MoLangRuntime runtime2) {
        MoLangCurve.DefaultImpls.apply(this, runtime2);
    }

    private static final Double readFromBuffer$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.readDouble();
    }

    private static final void writeToBuffer$lambda$1(FriendlyByteBuf pb, Double node) {
        Intrinsics.checkNotNullExpressionValue((Object)node, (String)"node");
        pb.writeDouble(node.doubleValue());
    }

    private static final String CODEC$lambda$8$lambda$2(CatmullRomMoLangCurve it) {
        return it.getType().name();
    }

    private static final String CODEC$lambda$8$lambda$3(CatmullRomMoLangCurve it) {
        return it.getName();
    }

    private static final Expression CODEC$lambda$8$lambda$4(CatmullRomMoLangCurve it) {
        return it.getInput();
    }

    private static final Expression CODEC$lambda$8$lambda$5(CatmullRomMoLangCurve it) {
        return it.horizontalRange;
    }

    private static final List CODEC$lambda$8$lambda$6(CatmullRomMoLangCurve it) {
        return it.nodes;
    }

    private static final CatmullRomMoLangCurve CODEC$lambda$8$lambda$7(String string, String name, Expression input, Expression horizontalRange, List nodes) {
        Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
        Intrinsics.checkNotNullExpressionValue((Object)input, (String)"input");
        Intrinsics.checkNotNullExpressionValue((Object)horizontalRange, (String)"horizontalRange");
        Intrinsics.checkNotNullExpressionValue((Object)nodes, (String)"nodes");
        return new CatmullRomMoLangCurve(name, input, horizontalRange, nodes);
    }

    private static final App CODEC$lambda$8(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$2), (App)PrimitiveCodec.STRING.fieldOf("name").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$3), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("input").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$4), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("horizontalRange").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$5), (App)new ListCodec((Codec)PrimitiveCodec.DOUBLE).fieldOf("nodes").forGetter(CatmullRomMoLangCurve::CODEC$lambda$8$lambda$6)).apply((Applicative)instance, CatmullRomMoLangCurve::CODEC$lambda$8$lambda$7);
    }

    public CatmullRomMoLangCurve() {
        this(null, null, null, null, 15, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(CatmullRomMoLangCurve::CODEC$lambda$8);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026Range, nodes) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/CatmullRomMoLangCurve$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/CatmullRomMoLangCurve;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<CatmullRomMoLangCurve> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

