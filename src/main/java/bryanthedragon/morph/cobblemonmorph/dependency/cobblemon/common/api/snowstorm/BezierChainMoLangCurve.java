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
 *  com.mojang.serialization.codecs.UnboundedMapCodec
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
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
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 <2\u00020\u0001:\u0004=>?<B1\u0012\b\b\u0002\u0010\u001f\u001a\u00020\u001e\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0017\u0012\u0014\b\u0002\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020.0-\u00a2\u0006\u0004\b:\u0010;J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004JG\u0010\n\u001a&\u0012\f\u0012\n \t*\u0004\u0018\u00018\u00008\u0000 \t*\u0012\u0012\f\u0012\n \t*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\b0\b\"\u0004\b\u0000\u0010\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\u000e\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001f\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u000fR\"\u0010\u0018\u001a\u00020\u00178\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\"\u0010\u001f\u001a\u00020\u001e8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R(\u0010'\u001a\b\u0012\u0004\u0012\u00020&0%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R.\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020.0-8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b/\u00100\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001a\u00106\u001a\u0002058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b8\u00109\u00a8\u0006@"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve;", "Lcom/cobblemon/mod/common/api/snowstorm/MoLangCurve;", "", "deriveNodePairs", "()V", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "inputValue", "resolve", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;D)D", "writeToBuffer", "Lcom/bedrockk/molang/Expression;", "input", "Lcom/bedrockk/molang/Expression;", "getInput", "()Lcom/bedrockk/molang/Expression;", "setInput", "(Lcom/bedrockk/molang/Expression;)V", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "", "Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainNodePair;", "nodePairs", "Ljava/util/List;", "getNodePairs", "()Ljava/util/List;", "setNodePairs", "(Ljava/util/List;)V", "", "Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainNode;", "nodes", "Ljava/util/Map;", "getNodes", "()Ljava/util/Map;", "setNodes", "(Ljava/util/Map;)V", "Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "<init>", "(Ljava/lang/String;Lcom/bedrockk/molang/Expression;Ljava/util/Map;)V", "Companion", "BezierChainNode", "BezierChainNodePair", "BezierChainPointData", "common"})
@SourceDebugExtension(value={"SMAP\nMoLangCurve.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoLangCurve.kt\ncom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,324:1\n1045#2:325\n451#2,6:326\n1#3:332\n*S KotlinDebug\n*F\n+ 1 MoLangCurve.kt\ncom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve\n*L\n178#1:325\n245#1:326,6\n*E\n"})
public final class BezierChainMoLangCurve
implements MoLangCurve {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String name;
    @NotNull
    private Expression input;
    @NotNull
    private Map<Double, BezierChainNode> nodes;
    @NotNull
    private List<BezierChainNodePair> nodePairs;
    @NotNull
    private final CurveType type;
    @NotNull
    private static final Codec<BezierChainMoLangCurve> CODEC;

    public BezierChainMoLangCurve(@NotNull String name, @NotNull Expression input, @NotNull Map<Double, BezierChainNode> nodes) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter(nodes, (String)"nodes");
        this.name = name;
        this.input = input;
        this.nodes = nodes;
        this.nodePairs = CollectionsKt.emptyList();
        this.deriveNodePairs();
        this.type = CurveType.BEZIER_CHAIN;
    }

    public /* synthetic */ BezierChainMoLangCurve(String string, Expression expression, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "variable";
        }
        if ((n & 2) != 0) {
            expression = new NumberExpression(0.5);
        }
        if ((n & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        this(string, expression, map);
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
    public final Map<Double, BezierChainNode> getNodes() {
        return this.nodes;
    }

    public final void setNodes(@NotNull Map<Double, BezierChainNode> map) {
        Intrinsics.checkNotNullParameter(map, (String)"<set-?>");
        this.nodes = map;
    }

    @NotNull
    public final List<BezierChainNodePair> getNodePairs() {
        return this.nodePairs;
    }

    public final void setNodePairs(@NotNull List<BezierChainNodePair> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.nodePairs = list;
    }

    public final void deriveNodePairs() {
        List nodePairs = new ArrayList();
        Iterable $this$sortedBy$iv = this.nodes.entrySet();
        boolean $i$f$sortedBy = false;
        List sortedNodes = CollectionsKt.sortedWith((Iterable)$this$sortedBy$iv, (Comparator)new Comparator(){

            public final int compare(T a, T b) {
                Map.Entry it = (Map.Entry)a;
                boolean bl = false;
                Comparable comparable = (Double)it.getKey();
                it = (Map.Entry)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)((Double)it.getKey()));
            }
        });
        int n = sortedNodes.size();
        for (int i = 1; i < n; ++i) {
            Map.Entry node1 = (Map.Entry)sortedNodes.get(i - 1);
            Map.Entry node2 = (Map.Entry)sortedNodes.get(i);
            nodePairs.add(new BezierChainNodePair(((Number)node1.getKey()).doubleValue(), ((Number)node2.getKey()).doubleValue(), (BezierChainNode)node1.getValue(), (BezierChainNode)node2.getValue()));
        }
        this.nodePairs = nodePairs;
    }

    @Override
    @NotNull
    public CurveType getType() {
        return this.type;
    }

    @Override
    public double resolve(@NotNull MoLangRuntime runtime2, double inputValue) {
        BezierChainNodePair element$iv;
        double position;
        block1: {
            Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
            position = RangesKt.coerceIn((double)inputValue, (double)0.0, (double)1.0);
            List<BezierChainNodePair> $this$last$iv = this.nodePairs;
            boolean $i$f$last = false;
            ListIterator<BezierChainNodePair> iterator$iv = $this$last$iv.listIterator($this$last$iv.size());
            while (iterator$iv.hasPrevious()) {
                BezierChainNodePair it = element$iv = iterator$iv.previous();
                boolean bl = false;
                if (!(position >= it.getStartTime())) continue;
                break block1;
            }
            throw new NoSuchElementException("List contains no element matching the predicate.");
        }
        BezierChainNodePair nodePair = element$iv;
        CubedBezierCurve curve2 = nodePair.getCurve();
        double t = (position - nodePair.getStartTime()) / (nodePair.getEndTime() - nodePair.getStartTime());
        return curve2.getY(t);
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
        Map map = buffer.m_236847_(arg_0 -> BezierChainMoLangCurve.readFromBuffer$lambda$2(buffer, arg_0), arg_0 -> BezierChainMoLangCurve.readFromBuffer$lambda$4(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)map, (String)"buffer.readMap({ buffer.\u2026adFromBuffer(buffer) } })");
        this.nodes = map;
        this.deriveNodePairs();
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.getName());
        buffer.m_130070_(MoLangExtensionsKt.getString(this.getInput()));
        buffer.m_236831_(this.nodes, BezierChainMoLangCurve::writeToBuffer$lambda$5, BezierChainMoLangCurve::writeToBuffer$lambda$6);
    }

    @Override
    public void apply(@NotNull MoLangRuntime runtime2) {
        MoLangCurve.DefaultImpls.apply(this, runtime2);
    }

    private static final Double readFromBuffer$lambda$2(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.readDouble();
    }

    private static final BezierChainNode readFromBuffer$lambda$4(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        BezierChainNode bezierChainNode;
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        BezierChainNode it2 = bezierChainNode = new BezierChainNode(0.0, 0.0);
        boolean bl = false;
        it2.readFromBuffer($buffer);
        return bezierChainNode;
    }

    private static final void writeToBuffer$lambda$5(FriendlyByteBuf pb, Double key) {
        Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
        pb.writeDouble(key.doubleValue());
    }

    private static final void writeToBuffer$lambda$6(FriendlyByteBuf pb, BezierChainNode value2) {
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        value2.writeToBuffer(pb);
    }

    private static final String CODEC$lambda$12$lambda$7(BezierChainMoLangCurve it) {
        return it.getType().name();
    }

    private static final String CODEC$lambda$12$lambda$8(BezierChainMoLangCurve it) {
        return it.getName();
    }

    private static final Expression CODEC$lambda$12$lambda$9(BezierChainMoLangCurve it) {
        return it.getInput();
    }

    private static final Map CODEC$lambda$12$lambda$10(BezierChainMoLangCurve it) {
        return it.nodes;
    }

    private static final BezierChainMoLangCurve CODEC$lambda$12$lambda$11(String string, String name, Expression input, Map nodes) {
        Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
        Intrinsics.checkNotNullExpressionValue((Object)input, (String)"input");
        Intrinsics.checkNotNullExpressionValue((Object)nodes, (String)"nodes");
        return new BezierChainMoLangCurve(name, input, nodes);
    }

    private static final App CODEC$lambda$12(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(BezierChainMoLangCurve::CODEC$lambda$12$lambda$7), (App)PrimitiveCodec.STRING.fieldOf("name").forGetter(BezierChainMoLangCurve::CODEC$lambda$12$lambda$8), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("input").forGetter(BezierChainMoLangCurve::CODEC$lambda$12$lambda$9), (App)new UnboundedMapCodec((Codec)PrimitiveCodec.DOUBLE, BezierChainNode.Companion.getCODEC()).fieldOf("nodes").forGetter(BezierChainMoLangCurve::CODEC$lambda$12$lambda$10)).apply((Applicative)instance, BezierChainMoLangCurve::CODEC$lambda$12$lambda$11);
    }

    public BezierChainMoLangCurve() {
        this(null, null, null, 7, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(BezierChainMoLangCurve::CODEC$lambda$12);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026input, nodes) }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\r\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0017\u0012\u0006\u0010\u000f\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\u0006R\"\u0010\t\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\n\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainNode;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "", "slope", "D", "getSlope", "()D", "setSlope", "(D)V", "value", "getValue", "setValue", "<init>", "(DD)V", "Companion", "common"})
    public static final class BezierChainNode {
        @NotNull
        public static final Companion Companion = new Companion(null);
        private double value;
        private double slope;
        private static final Codec<BezierChainNode> CODEC = RecordCodecBuilder.create(BezierChainNode::CODEC$lambda$2);

        public BezierChainNode(double value2, double slope) {
            this.value = value2;
            this.slope = slope;
        }

        public final double getValue() {
            return this.value;
        }

        public final void setValue(double d) {
            this.value = d;
        }

        public final double getSlope() {
            return this.slope;
        }

        public final void setSlope(double d) {
            this.slope = d;
        }

        public final void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            buffer.writeDouble(this.value);
            buffer.writeDouble(this.slope);
        }

        public final void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            this.value = buffer.readDouble();
            this.slope = buffer.readDouble();
        }

        private static final Double CODEC$lambda$2$lambda$0(BezierChainNode it) {
            return it.value;
        }

        private static final Double CODEC$lambda$2$lambda$1(BezierChainNode it) {
            return it.slope;
        }

        private static final App CODEC$lambda$2(RecordCodecBuilder.Instance instance) {
            return instance.group((App)PrimitiveCodec.DOUBLE.fieldOf("value").forGetter(BezierChainNode::CODEC$lambda$2$lambda$0), (App)PrimitiveCodec.DOUBLE.fieldOf("slope").forGetter(BezierChainNode::CODEC$lambda$2$lambda$1)).apply((Applicative)instance, BezierChainNode::new);
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR;\u0010\u0005\u001a&\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003 \u0004*\u0012\u0012\f\u0012\n \u0004*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainNode$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainNode;", "kotlin.jvm.PlatformType", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
        public static final class Companion {
            private Companion() {
            }

            public final Codec<BezierChainNode> getCODEC() {
                return CODEC;
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\r\u0010\u000b\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainNodePair;", "", "Lcom/cobblemon/mod/common/util/math/CubedBezierCurve;", "curve", "Lcom/cobblemon/mod/common/util/math/CubedBezierCurve;", "getCurve", "()Lcom/cobblemon/mod/common/util/math/CubedBezierCurve;", "", "endTime", "D", "getEndTime", "()D", "startTime", "getStartTime", "Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainNode;", "node1", "node2", "<init>", "(DDLcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainNode;Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainNode;)V", "common"})
    public static final class BezierChainNodePair {
        private final double startTime;
        private final double endTime;
        @NotNull
        private final CubedBezierCurve curve;

        public BezierChainNodePair(double startTime, double endTime, @NotNull BezierChainNode node1, @NotNull BezierChainNode node2) {
            Intrinsics.checkNotNullParameter((Object)node1, (String)"node1");
            Intrinsics.checkNotNullParameter((Object)node2, (String)"node2");
            this.startTime = startTime;
            this.endTime = endTime;
            double v1 = node1.getValue();
            double v2 = node1.getValue() + node1.getSlope() / (double)3;
            double v3 = node2.getValue() - node2.getSlope() / (double)3;
            double v4 = node2.getValue();
            this.curve = new CubedBezierCurve(v1, v2, v3, v4);
        }

        public final double getStartTime() {
            return this.startTime;
        }

        public final double getEndTime() {
            return this.endTime;
        }

        @NotNull
        public final CubedBezierCurve getCurve() {
            return this.curve;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$BezierChainPointData;", "", "", "slope", "D", "getSlope", "()D", "time", "getTime", "value", "getValue", "<init>", "(DDD)V", "common"})
    public static final class BezierChainPointData {
        private final double time;
        private final double value;
        private final double slope;

        public BezierChainPointData(double time, double value2, double slope) {
            this.time = time;
            this.value = value2;
            this.slope = slope;
        }

        public final double getTime() {
            return this.time;
        }

        public final double getValue() {
            return this.value;
        }

        public final double getSlope() {
            return this.slope;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/BezierChainMoLangCurve;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<BezierChainMoLangCurve> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

