/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function1
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctions;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR3\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u0002`\u00060\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunctions;", "", "", "", "Lkotlin/Function1;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "functions", "Ljava/util/Map;", "getFunctions", "()Ljava/util/Map;", "<init>", "()V", "common"})
public final class WaveFunctions {
    @NotNull
    public static final WaveFunctions INSTANCE = new WaveFunctions();
    @NotNull
    private static final Map<String, Function1<Float, Float>> functions;

    private WaveFunctions() {
    }

    @NotNull
    public final Map<String, Function1<Float, Float>> getFunctions() {
        return functions;
    }

    static {
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"symmetrical", (Object)((Object)functions.1.INSTANCE)), TuplesKt.to((Object)"symmetrical_wide", (Object)((Object)functions.2.INSTANCE)), TuplesKt.to((Object)"one", (Object)((Object)functions.3.INSTANCE))};
        functions = MapsKt.mutableMapOf((Pair[])pairArray);
    }
}

