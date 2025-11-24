/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.BakingOverride;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010!\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b$\u0010%J\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\n\u001a\u0004\b\u000e\u0010\fR\u0017\u0010\u000f\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\u0010\u0010\fR\u0017\u0010\u0011\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\n\u001a\u0004\b\u0012\u0010\fR\u0017\u0010\u0013\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\n\u001a\u0004\b\u0014\u0010\fR\u0017\u0010\u0015\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\n\u001a\u0004\b\u0016\u0010\fR\u0017\u0010\u0017\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\n\u001a\u0004\b\u0018\u0010\fR\u0017\u0010\u0019\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\n\u001a\u0004\b\u001a\u0010\fR\u0017\u0010\u001b\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\n\u001a\u0004\b\u001c\u0010\fR\u0017\u0010\u001d\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\n\u001a\u0004\b\u001e\u0010\fR\u001d\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/client/CobblemonBakingOverrides;", "", "Lnet/minecraft/resources/ResourceLocation;", "modelLocation", "Lnet/minecraft/client/resources/model/ModelResourceLocation;", "modelIdentifier", "Lcom/cobblemon/mod/common/BakingOverride;", "registerOverride", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/resources/model/ModelResourceLocation;)Lcom/cobblemon/mod/common/BakingOverride;", "RESTORATION_TANK_CONNECTOR", "Lcom/cobblemon/mod/common/BakingOverride;", "getRESTORATION_TANK_CONNECTOR", "()Lcom/cobblemon/mod/common/BakingOverride;", "RESTORATION_TANK_FLUID_BUBBLING", "getRESTORATION_TANK_FLUID_BUBBLING", "RESTORATION_TANK_FLUID_CHUNKED_1", "getRESTORATION_TANK_FLUID_CHUNKED_1", "RESTORATION_TANK_FLUID_CHUNKED_2", "getRESTORATION_TANK_FLUID_CHUNKED_2", "RESTORATION_TANK_FLUID_CHUNKED_3", "getRESTORATION_TANK_FLUID_CHUNKED_3", "RESTORATION_TANK_FLUID_CHUNKED_4", "getRESTORATION_TANK_FLUID_CHUNKED_4", "RESTORATION_TANK_FLUID_CHUNKED_5", "getRESTORATION_TANK_FLUID_CHUNKED_5", "RESTORATION_TANK_FLUID_CHUNKED_6", "getRESTORATION_TANK_FLUID_CHUNKED_6", "RESTORATION_TANK_FLUID_CHUNKED_7", "getRESTORATION_TANK_FLUID_CHUNKED_7", "RESTORATION_TANK_FLUID_CHUNKED_8", "getRESTORATION_TANK_FLUID_CHUNKED_8", "", "models", "Ljava/util/List;", "getModels", "()Ljava/util/List;", "<init>", "()V", "common"})
public final class CobblemonBakingOverrides {
    @NotNull
    public static final CobblemonBakingOverrides INSTANCE = new CobblemonBakingOverrides();
    @NotNull
    private static final List<BakingOverride> models = new ArrayList();
    @NotNull
    private static final BakingOverride RESTORATION_TANK_FLUID_BUBBLING = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_bubbling"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_bubbling", "none"));
    @NotNull
    private static final BakingOverride RESTORATION_TANK_FLUID_CHUNKED_1 = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_1"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "1"));
    @NotNull
    private static final BakingOverride RESTORATION_TANK_FLUID_CHUNKED_2 = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_2"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "2"));
    @NotNull
    private static final BakingOverride RESTORATION_TANK_FLUID_CHUNKED_3 = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_3"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "3"));
    @NotNull
    private static final BakingOverride RESTORATION_TANK_FLUID_CHUNKED_4 = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_4"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "4"));
    @NotNull
    private static final BakingOverride RESTORATION_TANK_FLUID_CHUNKED_5 = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_5"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "5"));
    @NotNull
    private static final BakingOverride RESTORATION_TANK_FLUID_CHUNKED_6 = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_6"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "6"));
    @NotNull
    private static final BakingOverride RESTORATION_TANK_FLUID_CHUNKED_7 = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_7"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "7"));
    @NotNull
    private static final BakingOverride RESTORATION_TANK_FLUID_CHUNKED_8 = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_8"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "8"));
    @NotNull
    private static final BakingOverride RESTORATION_TANK_CONNECTOR = INSTANCE.registerOverride(MiscUtilsKt.cobblemonResource("block/restoration_tank_connector"), MiscUtilsKt.cobblemonModel("restoration_tank_connector", "none"));

    private CobblemonBakingOverrides() {
    }

    @NotNull
    public final List<BakingOverride> getModels() {
        return models;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_FLUID_BUBBLING() {
        return RESTORATION_TANK_FLUID_BUBBLING;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_FLUID_CHUNKED_1() {
        return RESTORATION_TANK_FLUID_CHUNKED_1;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_FLUID_CHUNKED_2() {
        return RESTORATION_TANK_FLUID_CHUNKED_2;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_FLUID_CHUNKED_3() {
        return RESTORATION_TANK_FLUID_CHUNKED_3;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_FLUID_CHUNKED_4() {
        return RESTORATION_TANK_FLUID_CHUNKED_4;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_FLUID_CHUNKED_5() {
        return RESTORATION_TANK_FLUID_CHUNKED_5;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_FLUID_CHUNKED_6() {
        return RESTORATION_TANK_FLUID_CHUNKED_6;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_FLUID_CHUNKED_7() {
        return RESTORATION_TANK_FLUID_CHUNKED_7;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_FLUID_CHUNKED_8() {
        return RESTORATION_TANK_FLUID_CHUNKED_8;
    }

    @NotNull
    public final BakingOverride getRESTORATION_TANK_CONNECTOR() {
        return RESTORATION_TANK_CONNECTOR;
    }

    @NotNull
    public final BakingOverride registerOverride(@NotNull ResourceLocation modelLocation, @NotNull ModelResourceLocation modelIdentifier) {
        Intrinsics.checkNotNullParameter((Object)modelLocation, (String)"modelLocation");
        Intrinsics.checkNotNullParameter((Object)modelIdentifier, (String)"modelIdentifier");
        BakingOverride result = new BakingOverride(modelLocation, modelIdentifier);
        models.add(result);
        return result;
    }
}

