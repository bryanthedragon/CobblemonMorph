/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  kotlin.Metadata
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.Holder
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.data.worldgen.ProcessorLists
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement
 *  net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement
 *  net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement
 *  net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool
 *  net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool$Projection
 *  net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.StructurePoolAccessor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.CobblemonStructureIDs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors.CobblemonStructureProcessorLists;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.pools.LegacySinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0019\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b2\u00103J)\u0010\b\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0002\u00a2\u0006\u0004\b\b\u0010\tJc\u0010\u0015\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00022\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u00112\b\b\u0002\u0010\u0014\u001a\u00020\u0013H\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016JW\u0010\u0017\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00022\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011\u00a2\u0006\u0004\b\u0017\u0010\u0018J+\u0010\u0019\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0002H\u0002\u00a2\u0006\u0004\b\u0019\u0010\tJ+\u0010\u001a\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0002H\u0002\u00a2\u0006\u0004\b\u001a\u0010\tJ\u0015\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u001b\u00a2\u0006\u0004\b\u001d\u0010\u001eR8\u0010 \u001a&\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u00050\u0005 \u001f*\u0012\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00110\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\"\u0010#R\u0017\u0010$\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b(\u0010#R\u0017\u0010)\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b)\u0010%\u001a\u0004\b*\u0010'R\u0014\u0010+\u001a\u00020\r8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b+\u0010#R\u0017\u0010,\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b,\u0010%\u001a\u0004\b-\u0010'R\u0017\u0010.\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b.\u0010%\u001a\u0004\b/\u0010'R\u0017\u00100\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b0\u0010%\u001a\u0004\b1\u0010'\u00a8\u00064"}, d2={"Lcom/cobblemon/mod/common/world/CobblemonStructures;", "", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/world/level/levelgen/structure/pools/StructureTemplatePool;", "templatePoolRegistry", "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureProcessorList;", "processorListRegistry", "", "addBerryFarms", "(Lnet/minecraft/core/Registry;Lnet/minecraft/core/Registry;)V", "Lnet/minecraft/resources/ResourceLocation;", "poolRL", "nbtPieceRL", "", "weight", "Lnet/minecraft/structure/pool/StructurePool$Projection;", "projection", "Lnet/minecraft/resources/ResourceKey;", "processorListKey", "", "shouldUseLegacySingePoolElement", "addBuildingToPool", "(Lnet/minecraft/core/Registry;Lnet/minecraft/core/Registry;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;ILnet/minecraft/world/level/levelgen/structure/pools/StructureTemplatePool$Projection;Lnet/minecraft/resources/ResourceKey;Z)V", "addLegacyBuildingToPool", "(Lnet/minecraft/core/Registry;Lnet/minecraft/core/Registry;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;ILnet/minecraft/world/level/levelgen/structure/pools/StructureTemplatePool$Projection;Lnet/minecraft/resources/ResourceKey;)V", "addLongPaths", "addPokecenters", "Lnet/minecraft/server/MinecraftServer;", "server", "registerJigsaws", "(Lnet/minecraft/server/MinecraftServer;)V", "kotlin.jvm.PlatformType", "EMPTY_PROCESSOR_LIST_KEY", "Lnet/minecraft/resources/ResourceKey;", "berryFarmWeight", "I", "desertHousesPoolLocation", "Lnet/minecraft/resources/ResourceLocation;", "getDesertHousesPoolLocation", "()Lnet/minecraft/resources/ResourceLocation;", "longPathWeight", "plainsHousesPoolLocation", "getPlainsHousesPoolLocation", "pokecenterWeight", "savannaHousesPoolLocation", "getSavannaHousesPoolLocation", "snowyHousesPoolLocation", "getSnowyHousesPoolLocation", "taigaHousesPoolLocation", "getTaigaHousesPoolLocation", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonStructures.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonStructures.kt\ncom/cobblemon/mod/common/world/CobblemonStructures\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,311:1\n1#2:312\n*E\n"})
public final class CobblemonStructures {
    @NotNull
    public static final CobblemonStructures INSTANCE = new CobblemonStructures();
    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.m_135785_((ResourceKey)Registries.f_257011_, (ResourceLocation)new ResourceLocation("minecraft", "empty"));
    private static final int pokecenterWeight = 35;
    private static final int berryFarmWeight = 1;
    private static final int longPathWeight = 10;
    @NotNull
    private static final ResourceLocation plainsHousesPoolLocation = new ResourceLocation("minecraft", "village/plains/houses");
    @NotNull
    private static final ResourceLocation desertHousesPoolLocation = new ResourceLocation("minecraft", "village/desert/houses");
    @NotNull
    private static final ResourceLocation savannaHousesPoolLocation = new ResourceLocation("minecraft", "village/savanna/houses");
    @NotNull
    private static final ResourceLocation snowyHousesPoolLocation = new ResourceLocation("minecraft", "village/snowy/houses");
    @NotNull
    private static final ResourceLocation taigaHousesPoolLocation = new ResourceLocation("minecraft", "village/taiga/houses");

    private CobblemonStructures() {
    }

    @NotNull
    public final ResourceLocation getPlainsHousesPoolLocation() {
        return plainsHousesPoolLocation;
    }

    @NotNull
    public final ResourceLocation getDesertHousesPoolLocation() {
        return desertHousesPoolLocation;
    }

    @NotNull
    public final ResourceLocation getSavannaHousesPoolLocation() {
        return savannaHousesPoolLocation;
    }

    @NotNull
    public final ResourceLocation getSnowyHousesPoolLocation() {
        return snowyHousesPoolLocation;
    }

    @NotNull
    public final ResourceLocation getTaigaHousesPoolLocation() {
        return taigaHousesPoolLocation;
    }

    public final void registerJigsaws(@NotNull MinecraftServer server) {
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Registry templatePoolRegistry = server.m_206579_().m_175515_(Registries.f_256948_);
        Registry processorListRegistry = server.m_206579_().m_175515_(Registries.f_257011_);
        Intrinsics.checkNotNullExpressionValue((Object)templatePoolRegistry, (String)"templatePoolRegistry");
        Intrinsics.checkNotNullExpressionValue((Object)processorListRegistry, (String)"processorListRegistry");
        this.addBerryFarms((Registry<StructureTemplatePool>)templatePoolRegistry, (Registry<StructureProcessorList>)processorListRegistry);
    }

    public final void addBerryFarms(@NotNull Registry<StructureTemplatePool> templatePoolRegistry, @NotNull Registry<StructureProcessorList> processorListRegistry) {
        Intrinsics.checkNotNullParameter(templatePoolRegistry, (String)"templatePoolRegistry");
        Intrinsics.checkNotNullParameter(processorListRegistry, (String)"processorListRegistry");
        ResourceKey<StructureProcessorList> cropToBerryProcessorList = CobblemonStructureProcessorLists.CROP_TO_BERRY;
        Intrinsics.checkNotNullExpressionValue(cropToBerryProcessorList, (String)"cropToBerryProcessorList");
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, plainsHousesPoolLocation, CobblemonStructureIDs.PLAINS_BERRY_SMALL, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, plainsHousesPoolLocation, CobblemonStructureIDs.PLAINS_BERRY_LARGE, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, desertHousesPoolLocation, CobblemonStructureIDs.DESERT_BERRY_SMALL, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, desertHousesPoolLocation, CobblemonStructureIDs.DESERT_BERRY_LARGE, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, savannaHousesPoolLocation, CobblemonStructureIDs.SAVANNA_BERRY_SMALL, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, savannaHousesPoolLocation, CobblemonStructureIDs.SAVANNA_BERRY_LARGE, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, taigaHousesPoolLocation, CobblemonStructureIDs.TAIGA_BERRY_SMALL, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, taigaHousesPoolLocation, CobblemonStructureIDs.TAIGA_BERRY_LARGE, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, snowyHousesPoolLocation, CobblemonStructureIDs.SNOWY_BERRY_SMALL, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, snowyHousesPoolLocation, CobblemonStructureIDs.SNOWY_BERRY_LARGE, 1, StructureTemplatePool.Projection.RIGID, cropToBerryProcessorList, false, 128, null);
    }

    private final void addPokecenters(Registry<StructureTemplatePool> templatePoolRegistry, Registry<StructureProcessorList> processorListRegistry) {
        ResourceKey<StructureProcessorList> resourceKey = EMPTY_PROCESSOR_LIST_KEY;
        Intrinsics.checkNotNullExpressionValue(resourceKey, (String)"EMPTY_PROCESSOR_LIST_KEY");
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, plainsHousesPoolLocation, CobblemonStructureIDs.PLAINS_POKECENTER, 35, StructureTemplatePool.Projection.RIGID, resourceKey, false, 128, null);
        ResourceKey<StructureProcessorList> resourceKey2 = EMPTY_PROCESSOR_LIST_KEY;
        Intrinsics.checkNotNullExpressionValue(resourceKey2, (String)"EMPTY_PROCESSOR_LIST_KEY");
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, desertHousesPoolLocation, CobblemonStructureIDs.DESERT_POKECENTER, 35, StructureTemplatePool.Projection.RIGID, resourceKey2, false, 128, null);
        ResourceKey<StructureProcessorList> resourceKey3 = EMPTY_PROCESSOR_LIST_KEY;
        Intrinsics.checkNotNullExpressionValue(resourceKey3, (String)"EMPTY_PROCESSOR_LIST_KEY");
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, savannaHousesPoolLocation, CobblemonStructureIDs.SAVANNA_POKECENTER, 35, StructureTemplatePool.Projection.RIGID, resourceKey3, false, 128, null);
        ResourceKey<StructureProcessorList> resourceKey4 = EMPTY_PROCESSOR_LIST_KEY;
        Intrinsics.checkNotNullExpressionValue(resourceKey4, (String)"EMPTY_PROCESSOR_LIST_KEY");
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, snowyHousesPoolLocation, CobblemonStructureIDs.SNOWY_POKECENTER, 35, StructureTemplatePool.Projection.RIGID, resourceKey4, false, 128, null);
        ResourceKey resourceKey5 = ProcessorLists.f_127204_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey5, (String)"MOSSIFY_10_PERCENT");
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, taigaHousesPoolLocation, CobblemonStructureIDs.TAIGA_POKECENTER, 35, StructureTemplatePool.Projection.RIGID, resourceKey5, false, 128, null);
    }

    private final void addLongPaths(Registry<StructureTemplatePool> templatePoolRegistry, Registry<StructureProcessorList> processorListRegistry) {
        ResourceLocation plainsStreetsPoolLocation = new ResourceLocation("minecraft:village/plains/streets");
        ResourceLocation desertStreetsPoolLocation = new ResourceLocation("minecraft:village/desert/streets");
        ResourceLocation savannaStreetsPoolLocation = new ResourceLocation("minecraft:village/savanna/streets");
        ResourceLocation snowyStreetsPoolLocation = new ResourceLocation("minecraft:village/snowy/streets");
        ResourceLocation taigaStreetsPoolLocation = new ResourceLocation("minecraft:village/taiga/streets");
        ResourceKey resourceKey = ProcessorLists.f_127207_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"STREET_PLAINS");
        this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, plainsStreetsPoolLocation, CobblemonStructureIDs.PLAINS_LONG_PATH, 10, StructureTemplatePool.Projection.TERRAIN_MATCHING, (ResourceKey<StructureProcessorList>)resourceKey);
        ResourceKey<StructureProcessorList> resourceKey2 = EMPTY_PROCESSOR_LIST_KEY;
        Intrinsics.checkNotNullExpressionValue(resourceKey2, (String)"EMPTY_PROCESSOR_LIST_KEY");
        this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, desertStreetsPoolLocation, CobblemonStructureIDs.DESERT_LONG_PATH, 10, StructureTemplatePool.Projection.TERRAIN_MATCHING, resourceKey2);
        ResourceKey resourceKey3 = ProcessorLists.f_127208_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey3, (String)"STREET_SAVANNA");
        this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, savannaStreetsPoolLocation, CobblemonStructureIDs.SAVANNA_LONG_PATH, 10, StructureTemplatePool.Projection.TERRAIN_MATCHING, (ResourceKey<StructureProcessorList>)resourceKey3);
        ResourceKey resourceKey4 = ProcessorLists.f_127209_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey4, (String)"STREET_SNOWY_OR_TAIGA");
        this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, snowyStreetsPoolLocation, CobblemonStructureIDs.SNOWY_LONG_PATH, 10, StructureTemplatePool.Projection.TERRAIN_MATCHING, (ResourceKey<StructureProcessorList>)resourceKey4);
        ResourceKey resourceKey5 = ProcessorLists.f_127209_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey5, (String)"STREET_SNOWY_OR_TAIGA");
        this.addLegacyBuildingToPool(templatePoolRegistry, processorListRegistry, taigaStreetsPoolLocation, CobblemonStructureIDs.TAIGA_LONG_PATH, 10, StructureTemplatePool.Projection.TERRAIN_MATCHING, (ResourceKey<StructureProcessorList>)resourceKey5);
    }

    public final void addLegacyBuildingToPool(@NotNull Registry<StructureTemplatePool> templatePoolRegistry, @NotNull Registry<StructureProcessorList> processorListRegistry, @NotNull ResourceLocation poolRL, @NotNull ResourceLocation nbtPieceRL, int weight, @NotNull StructureTemplatePool.Projection projection, @NotNull ResourceKey<StructureProcessorList> processorListKey) {
        Intrinsics.checkNotNullParameter(templatePoolRegistry, (String)"templatePoolRegistry");
        Intrinsics.checkNotNullParameter(processorListRegistry, (String)"processorListRegistry");
        Intrinsics.checkNotNullParameter((Object)poolRL, (String)"poolRL");
        Intrinsics.checkNotNullParameter((Object)nbtPieceRL, (String)"nbtPieceRL");
        Intrinsics.checkNotNullParameter((Object)projection, (String)"projection");
        Intrinsics.checkNotNullParameter(processorListKey, (String)"processorListKey");
        this.addBuildingToPool(templatePoolRegistry, processorListRegistry, poolRL, nbtPieceRL, weight, projection, processorListKey, true);
    }

    @JvmOverloads
    public final void addBuildingToPool(@NotNull Registry<StructureTemplatePool> templatePoolRegistry, @NotNull Registry<StructureProcessorList> processorListRegistry, @NotNull ResourceLocation poolRL, @NotNull ResourceLocation nbtPieceRL, int weight, @NotNull StructureTemplatePool.Projection projection, @NotNull ResourceKey<StructureProcessorList> processorListKey, boolean shouldUseLegacySingePoolElement) {
        Intrinsics.checkNotNullParameter(templatePoolRegistry, (String)"templatePoolRegistry");
        Intrinsics.checkNotNullParameter(processorListRegistry, (String)"processorListRegistry");
        Intrinsics.checkNotNullParameter((Object)poolRL, (String)"poolRL");
        Intrinsics.checkNotNullParameter((Object)nbtPieceRL, (String)"nbtPieceRL");
        Intrinsics.checkNotNullParameter((Object)projection, (String)"projection");
        Intrinsics.checkNotNullParameter(processorListKey, (String)"processorListKey");
        if (processorListRegistry.m_203636_(processorListKey).isEmpty()) {
            return;
        }
        Object t = processorListRegistry.m_203636_(processorListKey).get();
        Intrinsics.checkNotNullExpressionValue(t, (String)"processorListRegistry.ge\u2026y(processorListKey).get()");
        Holder.Reference processorList = (Holder.Reference)t;
        Object object = templatePoolRegistry.m_7745_(poolRL);
        StructurePoolAccessor structurePoolAccessor = object instanceof StructurePoolAccessor ? (StructurePoolAccessor)object : null;
        if (structurePoolAccessor == null) {
            return;
        }
        StructurePoolAccessor pool = structurePoolAccessor;
        SinglePoolElement piece = shouldUseLegacySingePoolElement ? (SinglePoolElement)LegacySinglePoolElement.m_210512_((String)nbtPieceRL.toString(), (Holder)((Holder)processorList)).apply(projection) : (SinglePoolElement)SinglePoolElement.m_210531_((String)nbtPieceRL.toString(), (Holder)((Holder)processorList)).apply(projection);
        int n = 0;
        while (n < weight) {
            int it = n++;
            boolean bl = false;
            pool.getElements().add((Object)piece);
        }
        ArrayList<Pair> listOfPieceEntries = new ArrayList<Pair>((Collection)pool.getElementCounts());
        listOfPieceEntries.add(new Pair((Object)piece, (Object)weight));
        pool.getElements().add((Object)piece);
        pool.setElementCounts((List<Pair<StructurePoolElement, Integer>>)listOfPieceEntries);
    }

    public static /* synthetic */ void addBuildingToPool$default(CobblemonStructures cobblemonStructures, Registry registry, Registry registry2, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, int n, StructureTemplatePool.Projection projection, ResourceKey resourceKey, boolean bl, int n2, Object object) {
        if ((n2 & 0x80) != 0) {
            bl = false;
        }
        cobblemonStructures.addBuildingToPool((Registry<StructureTemplatePool>)registry, (Registry<StructureProcessorList>)registry2, resourceLocation, resourceLocation2, n, projection, (ResourceKey<StructureProcessorList>)resourceKey, bl);
    }

    @JvmOverloads
    public final void addBuildingToPool(@NotNull Registry<StructureTemplatePool> templatePoolRegistry, @NotNull Registry<StructureProcessorList> processorListRegistry, @NotNull ResourceLocation poolRL, @NotNull ResourceLocation nbtPieceRL, int weight, @NotNull StructureTemplatePool.Projection projection, @NotNull ResourceKey<StructureProcessorList> processorListKey) {
        Intrinsics.checkNotNullParameter(templatePoolRegistry, (String)"templatePoolRegistry");
        Intrinsics.checkNotNullParameter(processorListRegistry, (String)"processorListRegistry");
        Intrinsics.checkNotNullParameter((Object)poolRL, (String)"poolRL");
        Intrinsics.checkNotNullParameter((Object)nbtPieceRL, (String)"nbtPieceRL");
        Intrinsics.checkNotNullParameter((Object)projection, (String)"projection");
        Intrinsics.checkNotNullParameter(processorListKey, (String)"processorListKey");
        CobblemonStructures.addBuildingToPool$default(this, templatePoolRegistry, processorListRegistry, poolRL, nbtPieceRL, weight, projection, processorListKey, false, 128, null);
    }
}

