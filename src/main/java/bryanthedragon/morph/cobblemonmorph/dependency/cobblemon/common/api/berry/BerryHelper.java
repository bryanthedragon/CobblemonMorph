/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.cache.CacheBuilder
 *  com.google.common.cache.CacheLoader
 *  com.google.common.cache.LoadingCache
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.Holder
 *  net.minecraft.world.level.biome.Biome
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.BerryHelper;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000-\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005*\u0001\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000fR,\u0010\u0011\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/berry/BerryHelper;", "", "Lnet/minecraft/core/Holder;", "Lnet/minecraft/world/level/biome/Biome;", "biome", "", "Lcom/cobblemon/mod/common/block/BerryBlock;", "getBerriesForBiome", "(Lnet/minecraft/core/Holder;)Ljava/util/List;", "getNaturallyGeneratingBerries", "()Ljava/util/List;", "com/cobblemon/mod/common/api/berry/BerryHelper$CACHE_LOADER$1", "CACHE_LOADER", "Lcom/cobblemon/mod/common/api/berry/BerryHelper$CACHE_LOADER$1;", "naturalBerries", "Ljava/util/List;", "Lcom/google/common/cache/LoadingCache;", "validBerryCache", "Lcom/google/common/cache/LoadingCache;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBerryHelper.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BerryHelper.kt\ncom/cobblemon/mod/common/api/berry/BerryHelper\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,48:1\n766#2:49\n857#2,2:50\n*S KotlinDebug\n*F\n+ 1 BerryHelper.kt\ncom/cobblemon/mod/common/api/berry/BerryHelper\n*L\n30#1:49\n30#1:50,2\n*E\n"})
public final class BerryHelper {
    @NotNull
    public static final BerryHelper INSTANCE;
    @NotNull
    private static final CACHE_LOADER.1 CACHE_LOADER;
    @NotNull
    private static final List<BerryBlock> naturalBerries;
    @NotNull
    private static final LoadingCache<Holder<Biome>, List<BerryBlock>> validBerryCache;

    private BerryHelper() {
    }

    @NotNull
    public final List<BerryBlock> getBerriesForBiome(@NotNull Holder<Biome> biome2) {
        Intrinsics.checkNotNullParameter(biome2, (String)"biome");
        Object object = validBerryCache.get(biome2);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"validBerryCache.get(biome)");
        return (List)object;
    }

    @NotNull
    public final List<BerryBlock> getNaturallyGeneratingBerries() {
        return naturalBerries;
    }

    public static final /* synthetic */ List access$getNaturalBerries$p() {
        return naturalBerries;
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var3_3;
        void $this$filterTo$iv$iv;
        INSTANCE = new BerryHelper();
        CACHE_LOADER = new CacheLoader<Holder<Biome>, List<? extends BerryBlock>>(){

            /*
             * WARNING - void declaration
             */
            @NotNull
            public List<BerryBlock> load(@NotNull Holder<Biome> key) {
                void $this$filterTo$iv$iv;
                Intrinsics.checkNotNullParameter(key, (String)"key");
                Iterable $this$filter$iv = BerryHelper.access$getNaturalBerries$p();
                boolean $i$f$filter = false;
                Iterable iterable = $this$filter$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$filterTo = false;
                for (T element$iv$iv : $this$filterTo$iv$iv) {
                    boolean bl;
                    block6: {
                        BerryBlock berryBlock = (BerryBlock)element$iv$iv;
                        boolean bl2 = false;
                        Berry berry = berryBlock.berry();
                        Object object = berry;
                        if (object != null && (object = ((Berry)object).getSpawnConditions()) != null) {
                            Iterable $this$any$iv = (Iterable)object;
                            boolean $i$f$any = false;
                            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                bl = false;
                            } else {
                                for (T element$iv : $this$any$iv) {
                                    BerrySpawnCondition it = (BerrySpawnCondition)element$iv;
                                    boolean bl3 = false;
                                    if (!it.canSpawn(berry, key)) continue;
                                    bl = true;
                                    break block6;
                                }
                                bl = false;
                            }
                        } else {
                            bl = false;
                        }
                    }
                    if (!bl) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                return (List)destination$iv$iv;
            }
        };
        Iterable $this$filter$iv = CobblemonBlocks.INSTANCE.berries().values();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            BerryBlock it = (BerryBlock)element$iv$iv;
            boolean bl = false;
            Object object = it.berry();
            if (!((object != null && (object = ((Berry)object).getSpawnConditions()) != null ? object.size() : 0) > 0)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        naturalBerries = (List)var3_3;
        LoadingCache loadingCache = CacheBuilder.newBuilder().maximumSize(4L).build((CacheLoader)CACHE_LOADER);
        Intrinsics.checkNotNullExpressionValue((Object)loadingCache, (String)"newBuilder()\n        .ma\u2026     .build(CACHE_LOADER)");
        validBerryCache = loadingCache;
    }
}

