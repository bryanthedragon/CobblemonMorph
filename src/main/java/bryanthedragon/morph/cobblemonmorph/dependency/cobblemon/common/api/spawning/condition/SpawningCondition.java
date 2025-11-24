/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.level.StructureManager
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.levelgen.structure.Structure
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.MoonPhaseRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.TimeRange;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.AppendageCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import com.mojang.datafixers.util.Either;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u0000 n*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003:\u0001nB\u0007\u00a2\u0006\u0004\bl\u0010mJ\u0017\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0004H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0001\u00a2\u0006\u0004\b\t\u0010\nJ#\u0010\u000f\u001a\u00020\u000e2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u00002\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00028\u0000H\u0014\u00a2\u0006\u0004\b\u0011\u0010\nJ\u0015\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0012\u0010\nR(\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR0\u0010\u001e\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\u001c\u0018\u00010\u001b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R$\u0010$\u001a\u0004\u0018\u00010\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R*\u0010+\u001a\n\u0012\u0004\u0012\u00020*\u0018\u00010\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010\u0016\u001a\u0004\b,\u0010\u0018\"\u0004\b-\u0010\u001aR$\u0010.\u001a\u0004\u0018\u00010\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010%\u001a\u0004\b.\u0010'\"\u0004\b/\u0010)R$\u00100\u001a\u0004\u0018\u00010\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b0\u0010%\u001a\u0004\b0\u0010'\"\u0004\b1\u0010)R$\u00103\u001a\u0004\u0018\u0001028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b3\u00104\u001a\u0004\b5\u00106\"\u0004\b7\u00108R$\u00109\u001a\u0004\u0018\u0001028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b9\u00104\u001a\u0004\b:\u00106\"\u0004\b;\u00108R$\u0010=\u001a\u0004\u0018\u00010<8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR$\u0010C\u001a\u0004\u0018\u00010<8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bC\u0010>\u001a\u0004\bD\u0010@\"\u0004\bE\u0010BR$\u0010F\u001a\u0004\u0018\u00010<8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bF\u0010>\u001a\u0004\bG\u0010@\"\u0004\bH\u0010BR$\u0010I\u001a\u0004\u0018\u0001028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bI\u00104\u001a\u0004\bJ\u00106\"\u0004\bK\u00108R$\u0010L\u001a\u0004\u0018\u0001028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bL\u00104\u001a\u0004\bM\u00106\"\u0004\bN\u00108R$\u0010O\u001a\u0004\u0018\u00010<8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bO\u0010>\u001a\u0004\bP\u0010@\"\u0004\bQ\u0010BR$\u0010R\u001a\u0004\u0018\u00010<8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bR\u0010>\u001a\u0004\bS\u0010@\"\u0004\bT\u0010BR$\u0010U\u001a\u0004\u0018\u00010<8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bU\u0010>\u001a\u0004\bV\u0010@\"\u0004\bW\u0010BR$\u0010Y\u001a\u0004\u0018\u00010X8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bY\u0010Z\u001a\u0004\b[\u0010\\\"\u0004\b]\u0010^R<\u0010b\u001a\u001c\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020*\u0012\n\u0012\b\u0012\u0004\u0012\u00020a0`0_\u0018\u00010\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bb\u0010\u0016\u001a\u0004\bc\u0010\u0018\"\u0004\bd\u0010\u001aR$\u0010f\u001a\u0004\u0018\u00010e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bf\u0010g\u001a\u0004\bh\u0010i\"\u0004\bj\u0010k\u00a8\u0006o"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "T", "", "Ljava/lang/Class;", "contextClass", "()Ljava/lang/Class;", "ctx", "", "contextMatches", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Z", "other", "Lcom/cobblemon/mod/common/util/Merger;", "merger", "", "copyFrom", "(Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;Lcom/cobblemon/mod/common/util/Merger;)V", "fits", "isSatisfiedBy", "", "Lcom/cobblemon/mod/common/api/spawning/condition/AppendageCondition;", "appendages", "Ljava/util/List;", "getAppendages", "()Ljava/util/List;", "setAppendages", "(Ljava/util/List;)V", "", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lnet/minecraft/world/level/biome/Biome;", "biomes", "Ljava/util/Set;", "getBiomes", "()Ljava/util/Set;", "setBiomes", "(Ljava/util/Set;)V", "canSeeSky", "Ljava/lang/Boolean;", "getCanSeeSky", "()Ljava/lang/Boolean;", "setCanSeeSky", "(Ljava/lang/Boolean;)V", "Lnet/minecraft/resources/ResourceLocation;", "dimensions", "getDimensions", "setDimensions", "isRaining", "setRaining", "isThundering", "setThundering", "", "maxLight", "Ljava/lang/Integer;", "getMaxLight", "()Ljava/lang/Integer;", "setMaxLight", "(Ljava/lang/Integer;)V", "maxSkyLight", "getMaxSkyLight", "setMaxSkyLight", "", "maxX", "Ljava/lang/Float;", "getMaxX", "()Ljava/lang/Float;", "setMaxX", "(Ljava/lang/Float;)V", "maxY", "getMaxY", "setMaxY", "maxZ", "getMaxZ", "setMaxZ", "minLight", "getMinLight", "setMinLight", "minSkyLight", "getMinSkyLight", "setMinSkyLight", "minX", "getMinX", "setMinX", "minY", "getMinY", "setMinY", "minZ", "getMinZ", "setMinZ", "Lcom/cobblemon/mod/common/api/spawning/MoonPhaseRange;", "moonPhase", "Lcom/cobblemon/mod/common/api/spawning/MoonPhaseRange;", "getMoonPhase", "()Lcom/cobblemon/mod/common/api/spawning/MoonPhaseRange;", "setMoonPhase", "(Lcom/cobblemon/mod/common/api/spawning/MoonPhaseRange;)V", "Lcom/mojang/datafixers/util/Either;", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/levelgen/structure/Structure;", "structures", "getStructures", "setStructures", "Lcom/cobblemon/mod/common/api/spawning/TimeRange;", "timeRange", "Lcom/cobblemon/mod/common/api/spawning/TimeRange;", "getTimeRange", "()Lcom/cobblemon/mod/common/api/spawning/TimeRange;", "setTimeRange", "(Lcom/cobblemon/mod/common/api/spawning/TimeRange;)V", "<init>", "()V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSpawningCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/SpawningCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,134:1\n2624#2,3:135\n1747#2,3:138\n2624#2,3:141\n*S KotlinDebug\n*F\n+ 1 SpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/SpawningCondition\n*L\n85#1:135,3\n99#1:138,3\n105#1:141,3\n*E\n"})
public abstract class SpawningCondition<T extends SpawningContext> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private List<ResourceLocation> dimensions;
    @Nullable
    private Set<RegistryLikeCondition<Biome>> biomes;
    @Nullable
    private MoonPhaseRange moonPhase;
    @Nullable
    private Boolean canSeeSky;
    @Nullable
    private Float minX;
    @Nullable
    private Float minY;
    @Nullable
    private Float minZ;
    @Nullable
    private Float maxX;
    @Nullable
    private Float maxY;
    @Nullable
    private Float maxZ;
    @Nullable
    private Integer minLight;
    @Nullable
    private Integer maxLight;
    @Nullable
    private Integer minSkyLight;
    @Nullable
    private Integer maxSkyLight;
    @Nullable
    private Boolean isRaining;
    @Nullable
    private Boolean isThundering;
    @Nullable
    private TimeRange timeRange;
    @Nullable
    private List<Either<ResourceLocation, TagKey<Structure>>> structures;
    @NotNull
    private transient List<AppendageCondition> appendages = new ArrayList();
    @NotNull
    private static final Map<String, Class<? extends SpawningCondition<?>>> conditionTypes = new LinkedHashMap();

    @Nullable
    public final List<ResourceLocation> getDimensions() {
        return this.dimensions;
    }

    public final void setDimensions(@Nullable List<ResourceLocation> list) {
        this.dimensions = list;
    }

    @Nullable
    public final Set<RegistryLikeCondition<Biome>> getBiomes() {
        return this.biomes;
    }

    public final void setBiomes(@Nullable Set<RegistryLikeCondition<Biome>> set2) {
        this.biomes = set2;
    }

    @Nullable
    public final MoonPhaseRange getMoonPhase() {
        return this.moonPhase;
    }

    public final void setMoonPhase(@Nullable MoonPhaseRange moonPhaseRange) {
        this.moonPhase = moonPhaseRange;
    }

    @Nullable
    public final Boolean getCanSeeSky() {
        return this.canSeeSky;
    }

    public final void setCanSeeSky(@Nullable Boolean bl) {
        this.canSeeSky = bl;
    }

    @Nullable
    public final Float getMinX() {
        return this.minX;
    }

    public final void setMinX(@Nullable Float f) {
        this.minX = f;
    }

    @Nullable
    public final Float getMinY() {
        return this.minY;
    }

    public final void setMinY(@Nullable Float f) {
        this.minY = f;
    }

    @Nullable
    public final Float getMinZ() {
        return this.minZ;
    }

    public final void setMinZ(@Nullable Float f) {
        this.minZ = f;
    }

    @Nullable
    public final Float getMaxX() {
        return this.maxX;
    }

    public final void setMaxX(@Nullable Float f) {
        this.maxX = f;
    }

    @Nullable
    public final Float getMaxY() {
        return this.maxY;
    }

    public final void setMaxY(@Nullable Float f) {
        this.maxY = f;
    }

    @Nullable
    public final Float getMaxZ() {
        return this.maxZ;
    }

    public final void setMaxZ(@Nullable Float f) {
        this.maxZ = f;
    }

    @Nullable
    public final Integer getMinLight() {
        return this.minLight;
    }

    public final void setMinLight(@Nullable Integer n) {
        this.minLight = n;
    }

    @Nullable
    public final Integer getMaxLight() {
        return this.maxLight;
    }

    public final void setMaxLight(@Nullable Integer n) {
        this.maxLight = n;
    }

    @Nullable
    public final Integer getMinSkyLight() {
        return this.minSkyLight;
    }

    public final void setMinSkyLight(@Nullable Integer n) {
        this.minSkyLight = n;
    }

    @Nullable
    public final Integer getMaxSkyLight() {
        return this.maxSkyLight;
    }

    public final void setMaxSkyLight(@Nullable Integer n) {
        this.maxSkyLight = n;
    }

    @Nullable
    public final Boolean isRaining() {
        return this.isRaining;
    }

    public final void setRaining(@Nullable Boolean bl) {
        this.isRaining = bl;
    }

    @Nullable
    public final Boolean isThundering() {
        return this.isThundering;
    }

    public final void setThundering(@Nullable Boolean bl) {
        this.isThundering = bl;
    }

    @Nullable
    public final TimeRange getTimeRange() {
        return this.timeRange;
    }

    public final void setTimeRange(@Nullable TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    @Nullable
    public final List<Either<ResourceLocation, TagKey<Structure>>> getStructures() {
        return this.structures;
    }

    public final void setStructures(@Nullable List<Either<ResourceLocation, TagKey<Structure>>> list) {
        this.structures = list;
    }

    @NotNull
    public final List<AppendageCondition> getAppendages() {
        return this.appendages;
    }

    public final void setAppendages(@NotNull List<AppendageCondition> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.appendages = list;
    }

    @NotNull
    public abstract Class<? extends T> contextClass();

    public final boolean contextMatches(@NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return this.contextClass().isAssignableFrom(ctx.getClass());
    }

    public final boolean isSatisfiedBy(@NotNull SpawningContext ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        return this.contextMatches(ctx) ? this.fits(ctx) : false;
    }

    protected boolean fits(@NotNull T ctx) {
        boolean bl;
        block34: {
            Intrinsics.checkNotNullParameter(ctx, (String)"ctx");
            if ((float)((SpawningContext)ctx).getPosition().m_123341_() < SimpleMathExtensionsKt.orMin(this.minX) || (float)((SpawningContext)ctx).getPosition().m_123341_() > SimpleMathExtensionsKt.orMax(this.maxX)) {
                return false;
            }
            if ((float)((SpawningContext)ctx).getPosition().m_123342_() < SimpleMathExtensionsKt.orMin(this.minY) || (float)((SpawningContext)ctx).getPosition().m_123342_() > SimpleMathExtensionsKt.orMax(this.maxY)) {
                return false;
            }
            if ((float)((SpawningContext)ctx).getPosition().m_123343_() < SimpleMathExtensionsKt.orMin(this.minZ) || (float)((SpawningContext)ctx).getPosition().m_123343_() > SimpleMathExtensionsKt.orMax(this.maxZ)) {
                return false;
            }
            if (this.dimensions != null) {
                List<ResourceLocation> list = this.dimensions;
                Intrinsics.checkNotNull(list);
                if (!((Collection)list).isEmpty()) {
                    List<ResourceLocation> list2 = this.dimensions;
                    Intrinsics.checkNotNull(list2);
                    if (!list2.contains(((SpawningContext)ctx).getWorld().m_220362_().m_135782_())) {
                        return false;
                    }
                }
            }
            if (this.moonPhase != null) {
                MoonPhaseRange moonPhaseRange = this.moonPhase;
                Intrinsics.checkNotNull((Object)moonPhaseRange);
                if (!moonPhaseRange.contains(((SpawningContext)ctx).getMoonPhase())) {
                    return false;
                }
            }
            if (this.biomes != null) {
                Set<RegistryLikeCondition<Biome>> set2 = this.biomes;
                Intrinsics.checkNotNull(set2);
                if (!((Collection)set2).isEmpty()) {
                    boolean bl2;
                    block33: {
                        Set<RegistryLikeCondition<Biome>> set3 = this.biomes;
                        Intrinsics.checkNotNull(set3);
                        Iterable $this$none$iv = set3;
                        boolean $i$f$none = false;
                        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                            bl2 = true;
                        } else {
                            for (Object element$iv : $this$none$iv) {
                                RegistryLikeCondition condition2 = (RegistryLikeCondition)element$iv;
                                boolean bl3 = false;
                                if (!condition2.fits(((SpawningContext)ctx).getBiome(), ((SpawningContext)ctx).getBiomeRegistry())) continue;
                                bl2 = false;
                                break block33;
                            }
                            bl2 = true;
                        }
                    }
                    if (bl2) {
                        return false;
                    }
                }
            }
            if (((SpawningContext)ctx).getLight() > SimpleMathExtensionsKt.orMax(this.maxLight) || ((SpawningContext)ctx).getLight() < SimpleMathExtensionsKt.orMin(this.minLight)) {
                return false;
            }
            if (((SpawningContext)ctx).getSkyLight() > SimpleMathExtensionsKt.orMax(this.maxSkyLight) || ((SpawningContext)ctx).getSkyLight() < SimpleMathExtensionsKt.orMin(this.minSkyLight)) {
                return false;
            }
            if (this.timeRange != null) {
                TimeRange timeRange = this.timeRange;
                Intrinsics.checkNotNull((Object)timeRange);
                if (!timeRange.contains((int)(((SpawningContext)ctx).getWorld().m_46468_() % (long)24000))) {
                    return false;
                }
            }
            if (this.canSeeSky != null && !Intrinsics.areEqual((Object)this.canSeeSky, (Object)((SpawningContext)ctx).getCanSeeSky())) {
                return false;
            }
            if (this.isRaining != null) {
                boolean bl4 = ((SpawningContext)ctx).getWorld().m_46471_();
                Boolean bl5 = this.isRaining;
                Intrinsics.checkNotNull((Object)bl5);
                if (bl4 != bl5) {
                    return false;
                }
            }
            if (this.isThundering != null) {
                boolean bl6 = ((SpawningContext)ctx).getWorld().m_46470_();
                Boolean bl7 = this.isThundering;
                Intrinsics.checkNotNull((Object)bl7);
                if (bl6 != bl7) {
                    return false;
                }
            }
            Iterable $this$any$iv = this.appendages;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    AppendageCondition it = (AppendageCondition)element$iv;
                    boolean bl8 = false;
                    if (!(!it.fits((SpawningContext)ctx))) continue;
                    bl = true;
                    break block34;
                }
                bl = false;
            }
        }
        if (bl) {
            return false;
        }
        if (this.structures != null) {
            List<Either<ResourceLocation, TagKey<Structure>>> list = this.structures;
            Intrinsics.checkNotNull(list);
            if (!((Collection)list).isEmpty()) {
                boolean bl9;
                block35: {
                    List<Either<ResourceLocation, TagKey<Structure>>> list3 = this.structures;
                    Intrinsics.checkNotNull(list3);
                    List<Either<ResourceLocation, TagKey<Structure>>> structures = list3;
                    boolean bl10 = false;
                    StructureManager structureAccess = ((SpawningContext)ctx).getWorld().m_215010_();
                    SpawningContext.StructureChunkCache cache = ((SpawningContext)ctx).getStructureCache(((SpawningContext)ctx).getPosition());
                    Iterable $this$none$iv = structures;
                    boolean $i$f$none = false;
                    if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                        bl9 = true;
                    } else {
                        for (Object element$iv : $this$none$iv) {
                            Either it = (Either)element$iv;
                            boolean bl11 = false;
                            Object object = it.map(arg_0 -> SpawningCondition.fits$lambda$5$lambda$4$lambda$2((Function1)new Function1<ResourceLocation, Boolean>(cache, structureAccess, ctx){
                                final /* synthetic */ SpawningContext.StructureChunkCache $cache;
                                final /* synthetic */ StructureManager $structureAccess;
                                final /* synthetic */ T $ctx;
                                {
                                    this.$cache = $cache;
                                    this.$structureAccess = $structureAccess;
                                    this.$ctx = $ctx;
                                    super(1);
                                }

                                public final Boolean invoke(ResourceLocation it) {
                                    StructureManager structureManager = this.$structureAccess;
                                    Intrinsics.checkNotNullExpressionValue((Object)structureManager, (String)"structureAccess");
                                    BlockPos blockPos2 = ((SpawningContext)this.$ctx).getPosition();
                                    Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
                                    return this.$cache.check(structureManager, blockPos2, it);
                                }
                            }, arg_0), arg_0 -> SpawningCondition.fits$lambda$5$lambda$4$lambda$3((Function1)new Function1<TagKey<Structure>, Boolean>(cache, structureAccess, ctx){
                                final /* synthetic */ SpawningContext.StructureChunkCache $cache;
                                final /* synthetic */ StructureManager $structureAccess;
                                final /* synthetic */ T $ctx;
                                {
                                    this.$cache = $cache;
                                    this.$structureAccess = $structureAccess;
                                    this.$ctx = $ctx;
                                    super(1);
                                }

                                public final Boolean invoke(TagKey<Structure> it) {
                                    StructureManager structureManager = this.$structureAccess;
                                    Intrinsics.checkNotNullExpressionValue((Object)structureManager, (String)"structureAccess");
                                    BlockPos blockPos2 = ((SpawningContext)this.$ctx).getPosition();
                                    Intrinsics.checkNotNullExpressionValue(it, (String)"it");
                                    return this.$cache.check(structureManager, blockPos2, it);
                                }
                            }, arg_0));
                            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"ctx: T): Boolean {\n     \u2026ess, ctx.position, it) })");
                            if (!((Boolean)object).booleanValue()) continue;
                            bl9 = false;
                            break block35;
                        }
                        bl9 = true;
                    }
                }
                if (bl9) {
                    return false;
                }
            }
        }
        return true;
    }

    public void copyFrom(@NotNull SpawningCondition<?> other, @NotNull Merger merger) {
        Intrinsics.checkNotNullParameter(other, (String)"other");
        Intrinsics.checkNotNullParameter((Object)merger, (String)"merger");
        Collection collection = merger.merge((Collection)this.dimensions, (Collection)other.dimensions);
        this.dimensions = collection != null ? CollectionsKt.toMutableList(collection) : null;
        Collection collection2 = merger.merge((Collection)this.biomes, (Collection)other.biomes);
        this.biomes = collection2 != null ? CollectionsKt.toMutableSet((Iterable)collection2) : null;
        this.moonPhase = merger.mergeSingle(this.moonPhase, other.moonPhase);
        this.canSeeSky = merger.mergeSingle(this.canSeeSky, other.canSeeSky);
        this.minX = merger.mergeSingle(this.minX, other.minX);
        this.minY = merger.mergeSingle(this.minY, other.minY);
        this.minZ = merger.mergeSingle(this.minZ, other.minZ);
        this.maxX = merger.mergeSingle(this.maxX, other.maxX);
        this.maxY = merger.mergeSingle(this.maxY, other.maxY);
        this.maxZ = merger.mergeSingle(this.maxZ, other.maxZ);
        this.minLight = merger.mergeSingle(this.minLight, other.minLight);
        this.maxLight = merger.mergeSingle(this.maxLight, other.maxLight);
        this.minSkyLight = merger.mergeSingle(this.minSkyLight, other.minSkyLight);
        this.maxSkyLight = merger.mergeSingle(this.maxSkyLight, other.maxSkyLight);
        this.timeRange = merger.mergeSingle(this.timeRange, other.timeRange);
        Collection collection3 = merger.merge((Collection)this.structures, (Collection)other.structures);
        this.structures = collection3 != null ? CollectionsKt.toMutableList(collection3) : null;
    }

    private static final Boolean fits$lambda$5$lambda$4$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    private static final Boolean fits$lambda$5$lambda$4$lambda$3(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J#\u0010\u0006\u001a\u0010\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u0005\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J=\u0010\r\u001a\u00020\f\"\b\b\u0001\u0010\t*\u00020\b\"\u000e\b\u0002\u0010\n*\b\u0012\u0004\u0012\u00028\u00010\u00052\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00020\u0004\u00a2\u0006\u0004\b\r\u0010\u000eR/\u0010\u0010\u001a\u001a\u0012\u0004\u0012\u00020\u0002\u0012\u0010\u0012\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00050\u00040\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition$Companion;", "", "", "name", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "getByName", "(Ljava/lang/String;)Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "T", "C", "clazz", "", "register", "(Ljava/lang/String;Ljava/lang/Class;)V", "", "conditionTypes", "Ljava/util/Map;", "getConditionTypes", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Map<String, Class<? extends SpawningCondition<?>>> getConditionTypes() {
            return conditionTypes;
        }

        @Nullable
        public final Class<? extends SpawningCondition<?>> getByName(@NotNull String name) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            return this.getConditionTypes().get(name);
        }

        public final <T extends SpawningContext, C extends SpawningCondition<T>> void register(@NotNull String name, @NotNull Class<C> clazz) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
            this.getConditionTypes().put(name, clazz);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

