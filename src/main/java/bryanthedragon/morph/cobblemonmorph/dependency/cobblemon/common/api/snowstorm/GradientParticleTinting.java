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
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.util.Mth
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Vector4f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.MoLang;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.NumberExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTinting;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.ParticleTintingType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec.ExpressionCodecKt;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector4f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 *2\u00020\u0001:\u0002*+B'\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u001c\u0012\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u000b0\u0014\u00a2\u0006\u0004\b(\u0010)JG\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0018\u00010\u00050\u0005\"\u0004\b\u0000\u0010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0012R.\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u000b0\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\"\u0010\u001d\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001a\u0010$\u001a\u00020#8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting;", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTinting;", "T", "Lcom/mojang/serialization/DynamicOps;", "ops", "Lcom/mojang/serialization/DataResult;", "kotlin.jvm.PlatformType", "encode", "(Lcom/mojang/serialization/DynamicOps;)Lcom/mojang/serialization/DataResult;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lorg/joml/Vector4f;", "getTint", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)Lorg/joml/Vector4f;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToBuffer", "", "", "gradient", "Ljava/util/Map;", "getGradient", "()Ljava/util/Map;", "setGradient", "(Ljava/util/Map;)V", "Lcom/bedrockk/molang/Expression;", "interpolant", "Lcom/bedrockk/molang/Expression;", "getInterpolant", "()Lcom/bedrockk/molang/Expression;", "setInterpolant", "(Lcom/bedrockk/molang/Expression;)V", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "type", "Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/ParticleTintingType;", "<init>", "(Lcom/bedrockk/molang/Expression;Ljava/util/Map;)V", "Companion", "GradientEntry", "common"})
@SourceDebugExtension(value={"SMAP\nParticleTinting.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParticleTinting.kt\ncom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,178:1\n766#2:179\n857#2,2:180\n2333#2,14:182\n766#2:196\n857#2,2:197\n2333#2,14:199\n1549#2:213\n1620#2,3:214\n1549#2:217\n1620#2,3:218\n37#3,2:221\n*S KotlinDebug\n*F\n+ 1 ParticleTinting.kt\ncom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting\n*L\n133#1:179\n133#1:180,2\n134#1:182,14\n136#1:196\n136#1:197,2\n137#1:199,14\n119#1:213\n119#1:214,3\n123#1:217\n123#1:218,3\n123#1:221,2\n*E\n"})
public final class GradientParticleTinting
implements ParticleTinting {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Expression interpolant;
    @NotNull
    private Map<Double, ? extends Vector4f> gradient;
    @NotNull
    private final ParticleTintingType type;
    @NotNull
    private static final Codec<GradientParticleTinting> CODEC;

    public GradientParticleTinting(@NotNull Expression interpolant, @NotNull Map<Double, ? extends Vector4f> gradient) {
        Intrinsics.checkNotNullParameter((Object)interpolant, (String)"interpolant");
        Intrinsics.checkNotNullParameter(gradient, (String)"gradient");
        this.interpolant = interpolant;
        this.gradient = gradient;
        this.type = ParticleTintingType.GRADIENT;
    }

    public /* synthetic */ GradientParticleTinting(Expression expression, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            expression = new NumberExpression(0.0);
        }
        if ((n & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        this(expression, map);
    }

    @NotNull
    public final Expression getInterpolant() {
        return this.interpolant;
    }

    public final void setInterpolant(@NotNull Expression expression) {
        Intrinsics.checkNotNullParameter((Object)expression, (String)"<set-?>");
        this.interpolant = expression;
    }

    @NotNull
    public final Map<Double, Vector4f> getGradient() {
        return this.gradient;
    }

    public final void setGradient(@NotNull Map<Double, ? extends Vector4f> map) {
        Intrinsics.checkNotNullParameter(map, (String)"<set-?>");
        this.gradient = map;
    }

    @Override
    @NotNull
    public ParticleTintingType getType() {
        return this.type;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public Vector4f getTint(@NotNull MoLangRuntime runtime2) {
        Object v1;
        void $this$minByOrNull$iv;
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv;
        Object minElem$iv;
        Object v0;
        void $this$minByOrNull$iv2;
        void $this$filterTo$iv$iv2;
        Iterable $this$filter$iv2;
        Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
        double interpolant = MoLangExtensionsKt.resolveDouble(runtime2, this.interpolant);
        Iterable iterable = this.gradient.entrySet();
        boolean $i$f$filter = false;
        void var7_7 = $this$filter$iv2;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv2) {
            Map.Entry it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            if (!(((Number)it.getKey()).doubleValue() <= interpolant)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filter$iv2 = (List)destination$iv$iv;
        boolean $i$f$minByOrNull22 = false;
        Iterator iterator$iv = $this$minByOrNull$iv2.iterator();
        if (!iterator$iv.hasNext()) {
            v0 = null;
        } else {
            minElem$iv = iterator$iv.next();
            if (!iterator$iv.hasNext()) {
                v0 = minElem$iv;
            } else {
                Map.Entry it = (Map.Entry)minElem$iv;
                boolean bl = false;
                double minValue$iv = Math.abs(((Number)it.getKey()).doubleValue() - interpolant);
                do {
                    Object e$iv = iterator$iv.next();
                    Map.Entry it2 = (Map.Entry)e$iv;
                    $i$a$-minByOrNull-GradientParticleTinting$getTint$closestBelowNode$2 = false;
                    double v$iv = Math.abs(((Number)it2.getKey()).doubleValue() - interpolant);
                    if (Double.compare(minValue$iv, v$iv) <= 0) continue;
                    minElem$iv = e$iv;
                    minValue$iv = v$iv;
                } while (iterator$iv.hasNext());
                v0 = minElem$iv;
            }
        }
        Map.Entry closestBelowNode = v0;
        Iterable $i$f$minByOrNull22 = this.gradient.entrySet();
        boolean $i$f$filter2 = false;
        minElem$iv = $this$filter$iv;
        Collection destination$iv$iv2 = new ArrayList();
        boolean $i$f$filterTo2 = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Map.Entry it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            if (!(((Number)it.getKey()).doubleValue() >= interpolant)) continue;
            destination$iv$iv2.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv2;
        boolean $i$f$minByOrNull = false;
        Iterator iterator$iv2 = $this$minByOrNull$iv.iterator();
        if (!iterator$iv2.hasNext()) {
            v1 = null;
        } else {
            Object minElem$iv2 = iterator$iv2.next();
            if (!iterator$iv2.hasNext()) {
                v1 = minElem$iv2;
            } else {
                Map.Entry it = (Map.Entry)minElem$iv2;
                boolean bl = false;
                double minValue$iv = Math.abs(((Number)it.getKey()).doubleValue() - interpolant);
                do {
                    Object e$iv = iterator$iv2.next();
                    Map.Entry it3 = (Map.Entry)e$iv;
                    $i$a$-minByOrNull-GradientParticleTinting$getTint$closestAboveNode$2 = false;
                    double v$iv = Math.abs(((Number)it3.getKey()).doubleValue() - interpolant);
                    if (Double.compare(minValue$iv, v$iv) <= 0) continue;
                    minElem$iv2 = e$iv;
                    minValue$iv = v$iv;
                } while (iterator$iv2.hasNext());
                v1 = minElem$iv2;
            }
        }
        Map.Entry closestAboveNode = v1;
        if (closestBelowNode == null && closestAboveNode == null) {
            throw new IllegalStateException("A gradient particle tinting had no below node and no above node, which is probably only possible if the gradient has no points.");
        }
        if (closestBelowNode == null) {
            Map.Entry entry = closestAboveNode;
            Intrinsics.checkNotNull((Object)entry);
            return (Vector4f)entry.getValue();
        }
        if (closestAboveNode == null) {
            return (Vector4f)closestBelowNode.getValue();
        }
        float progression = (float)((interpolant - ((Number)closestBelowNode.getKey()).doubleValue()) / (((Number)closestAboveNode.getKey()).doubleValue() - ((Number)closestBelowNode.getKey()).doubleValue()));
        return new Vector4f(Mth.m_14179_((float)progression, (float)((Vector4f)closestBelowNode.getValue()).x, (float)((Vector4f)closestAboveNode.getValue()).x), Mth.m_14179_((float)progression, (float)((Vector4f)closestBelowNode.getValue()).y, (float)((Vector4f)closestAboveNode.getValue()).y), Mth.m_14179_((float)progression, (float)((Vector4f)closestBelowNode.getValue()).z, (float)((Vector4f)closestAboveNode.getValue()).z), Mth.m_14179_((float)progression, (float)((Vector4f)closestBelowNode.getValue()).w, (float)((Vector4f)closestAboveNode.getValue()).w));
    }

    @Override
    public <T> DataResult<T> encode(@NotNull DynamicOps<T> ops) {
        Intrinsics.checkNotNullParameter(ops, (String)"ops");
        return CODEC.encodeStart(ops, (Object)this);
    }

    @Override
    public void readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Expression expression = MoLang.createParser(buffer.m_130277_()).parseExpression();
        Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"createParser(buffer.read\u2026ring()).parseExpression()");
        this.interpolant = expression;
        List list = buffer.m_236845_(arg_0 -> GradientParticleTinting.readFromBuffer$lambda$4(buffer, arg_0));
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer\n            .read\u2026(), buffer.readFloat()) }");
        this.gradient = MapsKt.toMap((Iterable)list);
    }

    @Override
    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(MoLangExtensionsKt.getString(this.interpolant));
        buffer.m_236828_((Collection)this.gradient.entrySet(), (arg_0, arg_1) -> GradientParticleTinting.writeToBuffer$lambda$5(buffer, arg_0, arg_1));
    }

    private static final Pair readFromBuffer$lambda$4(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return TuplesKt.to((Object)$buffer.readDouble(), (Object)new Vector4f($buffer.readFloat(), $buffer.readFloat(), $buffer.readFloat(), $buffer.readFloat()));
    }

    private static final void writeToBuffer$lambda$5(FriendlyByteBuf $buffer, FriendlyByteBuf pb, Map.Entry entry) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        Intrinsics.checkNotNullExpressionValue((Object)entry, (String)"(key, colour)");
        double key = ((Number)entry.getKey()).doubleValue();
        Vector4f colour = (Vector4f)entry.getValue();
        $buffer.writeDouble(key);
        $buffer.writeFloat(colour.x);
        $buffer.writeFloat(colour.y);
        $buffer.writeFloat(colour.z);
        $buffer.writeFloat(colour.w);
    }

    private static final String CODEC$lambda$12$lambda$6(GradientParticleTinting it) {
        return it.getType().name();
    }

    private static final Expression CODEC$lambda$12$lambda$7(GradientParticleTinting it) {
        return it.interpolant;
    }

    /*
     * WARNING - void declaration
     */
    private static final List CODEC$lambda$12$lambda$9(GradientParticleTinting it) {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = it.gradient.entrySet();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            Map.Entry entry = (Map.Entry)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            double key = ((Number)entry.getKey()).doubleValue();
            Vector4f colour = (Vector4f)entry.getValue();
            collection.add(new GradientEntry(key, colour));
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    private static final GradientParticleTinting CODEC$lambda$12$lambda$11(String string, Expression interpolant, List gradient) {
        void $this$toTypedArray$iv;
        void $this$mapTo$iv$iv;
        Collection $this$map$iv;
        Intrinsics.checkNotNullExpressionValue((Object)interpolant, (String)"interpolant");
        Intrinsics.checkNotNullExpressionValue((Object)gradient, (String)"gradient");
        Iterable iterable = gradient;
        Expression expression = interpolant;
        boolean $i$f$map = false;
        void var6_6 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            GradientEntry gradientEntry = (GradientEntry)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.toEntry());
        }
        $this$map$iv = (List)destination$iv$iv;
        boolean $i$f$toTypedArray = false;
        void thisCollection$iv = $this$toTypedArray$iv;
        Pair[] pairArray = thisCollection$iv.toArray(new Pair[0]);
        Map map = MapsKt.mapOf((Pair[])Arrays.copyOf(pairArray, pairArray.length));
        Expression expression2 = expression;
        return new GradientParticleTinting(expression2, map);
    }

    private static final App CODEC$lambda$12(RecordCodecBuilder.Instance instance) {
        return instance.group((App)PrimitiveCodec.STRING.fieldOf("type").forGetter(GradientParticleTinting::CODEC$lambda$12$lambda$6), (App)ExpressionCodecKt.getEXPRESSION_CODEC().fieldOf("interpolant").forGetter(GradientParticleTinting::CODEC$lambda$12$lambda$7), (App)new ListCodec(GradientEntry.Companion.getCODEC()).fieldOf("gradient").forGetter(GradientParticleTinting::CODEC$lambda$12$lambda$9)).apply((Applicative)instance, GradientParticleTinting::CODEC$lambda$12$lambda$11);
    }

    public GradientParticleTinting() {
        this(null, null, 3, null);
    }

    static {
        Codec codec2 = RecordCodecBuilder.create(GradientParticleTinting::CODEC$lambda$12);
        Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026)\n            }\n        }");
        CODEC = codec2;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Codec<GradientParticleTinting> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0017\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0019\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting$GradientEntry;", "", "Lkotlin/Pair;", "", "Lorg/joml/Vector4f;", "toEntry", "()Lkotlin/Pair;", "colour", "Lorg/joml/Vector4f;", "getColour", "()Lorg/joml/Vector4f;", "key", "D", "getKey", "()D", "<init>", "(DLorg/joml/Vector4f;)V", "Companion", "common"})
    public static final class GradientEntry {
        @NotNull
        public static final Companion Companion = new Companion(null);
        private final double key;
        @NotNull
        private final Vector4f colour;
        @NotNull
        private static final Codec<GradientEntry> CODEC;

        public GradientEntry(double key, @NotNull Vector4f colour) {
            Intrinsics.checkNotNullParameter((Object)colour, (String)"colour");
            this.key = key;
            this.colour = colour;
        }

        public final double getKey() {
            return this.key;
        }

        @NotNull
        public final Vector4f getColour() {
            return this.colour;
        }

        @NotNull
        public final Pair<Double, Vector4f> toEntry() {
            return TuplesKt.to((Object)this.key, (Object)this.colour);
        }

        private static final Double CODEC$lambda$6$lambda$0(GradientEntry it) {
            return it.key;
        }

        private static final Float CODEC$lambda$6$lambda$1(GradientEntry it) {
            return Float.valueOf(it.colour.x);
        }

        private static final Float CODEC$lambda$6$lambda$2(GradientEntry it) {
            return Float.valueOf(it.colour.y);
        }

        private static final Float CODEC$lambda$6$lambda$3(GradientEntry it) {
            return Float.valueOf(it.colour.z);
        }

        private static final Float CODEC$lambda$6$lambda$4(GradientEntry it) {
            return Float.valueOf(it.colour.w);
        }

        private static final GradientEntry CODEC$lambda$6$lambda$5(Double key, Float red, Float green, Float blue, Float alpha) {
            Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
            double d = key;
            Intrinsics.checkNotNullExpressionValue((Object)red, (String)"red");
            float f = red.floatValue();
            Intrinsics.checkNotNullExpressionValue((Object)green, (String)"green");
            float f2 = green.floatValue();
            Intrinsics.checkNotNullExpressionValue((Object)blue, (String)"blue");
            float f3 = blue.floatValue();
            Intrinsics.checkNotNullExpressionValue((Object)alpha, (String)"alpha");
            return new GradientEntry(d, new Vector4f(f, f2, f3, alpha.floatValue()));
        }

        private static final App CODEC$lambda$6(RecordCodecBuilder.Instance instance) {
            return instance.group((App)PrimitiveCodec.DOUBLE.fieldOf("key").forGetter(GradientEntry::CODEC$lambda$6$lambda$0), (App)PrimitiveCodec.FLOAT.fieldOf("red").forGetter(GradientEntry::CODEC$lambda$6$lambda$1), (App)PrimitiveCodec.FLOAT.fieldOf("green").forGetter(GradientEntry::CODEC$lambda$6$lambda$2), (App)PrimitiveCodec.FLOAT.fieldOf("blue").forGetter(GradientEntry::CODEC$lambda$6$lambda$3), (App)PrimitiveCodec.FLOAT.fieldOf("alpha").forGetter(GradientEntry::CODEC$lambda$6$lambda$4)).apply((Applicative)instance, GradientEntry::CODEC$lambda$6$lambda$5);
        }

        static {
            Codec codec2 = RecordCodecBuilder.create(GradientEntry::CODEC$lambda$6);
            Intrinsics.checkNotNullExpressionValue((Object)codec2, (String)"create { instance ->\n   \u2026, alpha)) }\n            }");
            CODEC = codec2;
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting$GradientEntry$Companion;", "", "Lcom/mojang/serialization/Codec;", "Lcom/cobblemon/mod/common/api/snowstorm/GradientParticleTinting$GradientEntry;", "CODEC", "Lcom/mojang/serialization/Codec;", "getCODEC", "()Lcom/mojang/serialization/Codec;", "<init>", "()V", "common"})
        public static final class Companion {
            private Companion() {
            }

            @NotNull
            public final Codec<GradientEntry> getCODEC() {
                return CODEC;
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }
}

