/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.codec.CodecMapped;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ArbitrarilyMappedSerializableCompanion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierChainMoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.BezierMoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CatmullRomMoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.CurveType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.LinearMoLangCurve;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\t\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\t\u0010\nR\u001c\u0010\u0010\u001a\u00020\u000b8&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0016\u001a\u00020\u00118&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0014\u0010\u001a\u001a\u00020\u00178&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/MoLangCurve;", "Lcom/cobblemon/mod/common/api/codec/CodecMapped;", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "", "apply", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;)V", "", "inputValue", "resolve", "(Lcom/bedrockk/molang/runtime/MoLangRuntime;D)D", "Lcom/bedrockk/molang/Expression;", "getInput", "()Lcom/bedrockk/molang/Expression;", "setInput", "(Lcom/bedrockk/molang/Expression;)V", "input", "", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "name", "Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "getType", "()Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "type", "Companion", "common"})
public interface MoLangCurve
extends CodecMapped {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve$Companion.$$INSTANCE;

    @NotNull
    public String getName();

    public void setName(@NotNull String var1);

    @NotNull
    public CurveType getType();

    @NotNull
    public Expression getInput();

    public void setInput(@NotNull Expression var1);

    public double resolve(@NotNull MoLangRuntime var1, double var2);

    public void apply(@NotNull MoLangRuntime var1);

    static {
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve$Companion.$$INSTANCE.registerSubtype(CurveType.LINEAR, LinearMoLangCurve.class, LinearMoLangCurve.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve$Companion.$$INSTANCE.registerSubtype(CurveType.CATMULL_ROM, CatmullRomMoLangCurve.class, CatmullRomMoLangCurve.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve$Companion.$$INSTANCE.registerSubtype(CurveType.BEZIER, BezierMoLangCurve.class, BezierMoLangCurve.Companion.getCODEC());
        bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm.MoLangCurve$Companion.$$INSTANCE.registerSubtype(CurveType.BEZIER_CHAIN, BezierChainMoLangCurve.class, BezierChainMoLangCurve.Companion.getCODEC());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/snowstorm/MoLangCurve$Companion;", "Lcom/cobblemon/mod/common/api/data/ArbitrarilyMappedSerializableCompanion;", "Lcom/cobblemon/mod/common/api/snowstorm/MoLangCurve;", "Lcom/cobblemon/mod/common/api/snowstorm/CurveType;", "<init>", "()V", "common"})
    public static final class Companion
    extends ArbitrarilyMappedSerializableCompanion<MoLangCurve, CurveType> {
        static final /* synthetic */ Companion $$INSTANCE;

        private Companion() {
            super(1.INSTANCE, 2.INSTANCE, 3.INSTANCE);
        }

        static {
            $$INSTANCE = new Companion();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static void apply(@NotNull MoLangCurve $this, @NotNull MoLangRuntime runtime2) {
            Intrinsics.checkNotNullParameter((Object)runtime2, (String)"runtime");
            runtime2.getEnvironment().setSimpleVariable($this.getName(), new DoubleValue($this.resolve(runtime2, MoLangExtensionsKt.resolveDouble(runtime2, $this.getInput()))));
        }
    }
}

