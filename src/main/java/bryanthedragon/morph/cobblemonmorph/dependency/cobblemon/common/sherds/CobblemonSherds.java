/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.Item
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.sherds;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.sherds.CobblemonSherd;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0010\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b&\u0010\u000bJ\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ\r\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\r\u001a\u0004\b\u0011\u0010\u000fR\u0017\u0010\u0012\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0013\u0010\u000fR\u0017\u0010\u0014\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\r\u001a\u0004\b\u0015\u0010\u000fR\u0017\u0010\u0016\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\r\u001a\u0004\b\u0017\u0010\u000fR\u0017\u0010\u0018\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\r\u001a\u0004\b\u0019\u0010\u000fR\u001d\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR)\u0010\"\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020!0 0\u001f8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/sherds/CobblemonSherds;", "", "Lnet/minecraft/resources/ResourceLocation;", "patternId", "Lnet/minecraft/world/item/Item;", "item", "Lcom/cobblemon/mod/common/sherds/CobblemonSherd;", "addSherd", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/world/item/Item;)Lcom/cobblemon/mod/common/sherds/CobblemonSherd;", "", "registerSherds", "()V", "BYGONE_SHERD", "Lcom/cobblemon/mod/common/sherds/CobblemonSherd;", "getBYGONE_SHERD", "()Lcom/cobblemon/mod/common/sherds/CobblemonSherd;", "CAPTURE_SHERD", "getCAPTURE_SHERD", "DOME_SHERD", "getDOME_SHERD", "HELIX_SHERD", "getHELIX_SHERD", "NOSTALGIC_SHERD", "getNOSTALGIC_SHERD", "SUSPICIOUS_SHERD", "getSUSPICIOUS_SHERD", "", "allSherds", "Ljava/util/List;", "getAllSherds", "()Ljava/util/List;", "", "Lnet/minecraft/resources/ResourceKey;", "", "sherdToPattern", "Ljava/util/Map;", "getSherdToPattern", "()Ljava/util/Map;", "<init>", "common"})
public final class CobblemonSherds {
    @NotNull
    public static final CobblemonSherds INSTANCE = new CobblemonSherds();
    @NotNull
    private static final List<CobblemonSherd> allSherds = new ArrayList();
    @NotNull
    private static final Map<Item, ResourceKey<String>> sherdToPattern = new LinkedHashMap();
    @NotNull
    private static final CobblemonSherd BYGONE_SHERD = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("bygone_pottery_pattern"), CobblemonItems.BYGONE_SHERD);
    @NotNull
    private static final CobblemonSherd CAPTURE_SHERD = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("capture_pottery_pattern"), CobblemonItems.CAPTURE_SHERD);
    @NotNull
    private static final CobblemonSherd DOME_SHERD = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("dome_pottery_pattern"), CobblemonItems.DOME_SHERD);
    @NotNull
    private static final CobblemonSherd HELIX_SHERD = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("helix_pottery_pattern"), CobblemonItems.HELIX_SHERD);
    @NotNull
    private static final CobblemonSherd NOSTALGIC_SHERD = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("nostalgic_pottery_pattern"), CobblemonItems.NOSTALGIC_SHERD);
    @NotNull
    private static final CobblemonSherd SUSPICIOUS_SHERD = INSTANCE.addSherd(MiscUtilsKt.cobblemonResource("suspicious_pottery_pattern"), CobblemonItems.SUSPICIOUS_SHERD);

    private CobblemonSherds() {
    }

    @NotNull
    public final List<CobblemonSherd> getAllSherds() {
        return allSherds;
    }

    @NotNull
    public final Map<Item, ResourceKey<String>> getSherdToPattern() {
        return sherdToPattern;
    }

    @NotNull
    public final CobblemonSherd getBYGONE_SHERD() {
        return BYGONE_SHERD;
    }

    @NotNull
    public final CobblemonSherd getCAPTURE_SHERD() {
        return CAPTURE_SHERD;
    }

    @NotNull
    public final CobblemonSherd getDOME_SHERD() {
        return DOME_SHERD;
    }

    @NotNull
    public final CobblemonSherd getHELIX_SHERD() {
        return HELIX_SHERD;
    }

    @NotNull
    public final CobblemonSherd getNOSTALGIC_SHERD() {
        return NOSTALGIC_SHERD;
    }

    @NotNull
    public final CobblemonSherd getSUSPICIOUS_SHERD() {
        return SUSPICIOUS_SHERD;
    }

    @NotNull
    public final CobblemonSherd addSherd(@NotNull ResourceLocation patternId, @NotNull Item item) {
        Intrinsics.checkNotNullParameter((Object)patternId, (String)"patternId");
        Intrinsics.checkNotNullParameter((Object)item, (String)"item");
        CobblemonSherd sherd = new CobblemonSherd(patternId, item);
        ResourceKey registryKey = ResourceKey.m_135785_((ResourceKey)Registries.f_271200_, (ResourceLocation)patternId);
        Intrinsics.checkNotNullExpressionValue((Object)registryKey, (String)"registryKey");
        sherdToPattern.put(item, (ResourceKey<String>)registryKey);
        allSherds.add(sherd);
        return sherd;
    }

    public final void registerSherds() {
        Registry registry = BuiltInRegistries.f_271353_;
        for (CobblemonSherd sherd : allSherds) {
            ResourceKey regKey = ResourceKey.m_135785_((ResourceKey)Registries.f_271200_, (ResourceLocation)sherd.getPatternId());
            Registry.m_194579_((Registry)registry, (ResourceKey)regKey, (Object)sherd.getPatternId().m_135815_());
        }
    }
}

