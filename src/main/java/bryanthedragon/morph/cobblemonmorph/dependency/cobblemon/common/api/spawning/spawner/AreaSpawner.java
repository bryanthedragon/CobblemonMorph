/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.SectionPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.chunk.ChunkAccess
 *  net.minecraft.world.level.chunk.ChunkStatus
 *  net.minecraft.world.level.pathfinder.PathComputationType
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaContextResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.SpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.SpawningProspector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.AreaSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.SpawningArea;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.TickingSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000 62\u00020\u0001:\u00016B\u001f\u0012\u0006\u0010/\u001a\u00020.\u0012\u0006\u00101\u001a\u000200\u0012\u0006\u00103\u001a\u000202\u00a2\u0006\u0004\b4\u00105J\u0017\u0010\u0004\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\b\u0010\tJ%\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0011\u0010\u0012J%\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u00132\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017R,\u0010\u001a\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00190\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\"\u0010!\u001a\u00020 8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\"\u0010(\u001a\u00020'8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-\u00a8\u00067"}, d2={"Lcom/cobblemon/mod/common/api/spawning/spawner/AreaSpawner;", "Lcom/cobblemon/mod/common/api/spawning/spawner/TickingSpawner;", "Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "area", "constrainArea", "(Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;)Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "cause", "getArea", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;)Lcom/cobblemon/mod/common/api/spawning/spawner/SpawningArea;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/level/chunk/ChunkAccess;", "chunk", "Lnet/minecraft/util/math/BlockPos$Mutable;", "startPos", "", "isValidStartPoint", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos$MutableBlockPos;)Z", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "run", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;)Lkotlin/Pair;", "", "Lcom/cobblemon/mod/common/api/spawning/context/calculators/AreaSpawningContextCalculator;", "contextCalculators", "Ljava/util/List;", "getContextCalculators", "()Ljava/util/List;", "setContextCalculators", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;", "prospector", "Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;", "getProspector", "()Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;", "setProspector", "(Lcom/cobblemon/mod/common/api/spawning/prospecting/SpawningProspector;)V", "Lcom/cobblemon/mod/common/api/spawning/context/AreaContextResolver;", "resolver", "Lcom/cobblemon/mod/common/api/spawning/context/AreaContextResolver;", "getResolver", "()Lcom/cobblemon/mod/common/api/spawning/context/AreaContextResolver;", "setResolver", "(Lcom/cobblemon/mod/common/api/spawning/context/AreaContextResolver;)V", "", "name", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;", "spawns", "Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;", "manager", "<init>", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/spawning/detail/SpawnPool;Lcom/cobblemon/mod/common/api/spawning/SpawnerManager;)V", "Companion", "common"})
public abstract class AreaSpawner
extends TickingSpawner {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private SpawningProspector prospector;
    @NotNull
    private AreaContextResolver resolver;
    @NotNull
    private List<? extends AreaSpawningContextCalculator<?>> contextCalculators;
    public static final int CHUNK_REACH = 3;

    public AreaSpawner(@NotNull String name, @NotNull SpawnPool spawns2, @NotNull SpawnerManager manager) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)spawns2, (String)"spawns");
        Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
        super(name, spawns2, manager);
        this.prospector = Cobblemon.INSTANCE.getProspector();
        this.resolver = Cobblemon.INSTANCE.getAreaContextResolver();
        this.contextCalculators = SpawningContextCalculator.Companion.getPrioritizedAreaCalculators();
    }

    @Nullable
    public abstract SpawningArea getArea(@NotNull SpawnCause var1);

    @NotNull
    public final SpawningProspector getProspector() {
        return this.prospector;
    }

    public final void setProspector(@NotNull SpawningProspector spawningProspector) {
        Intrinsics.checkNotNullParameter((Object)spawningProspector, (String)"<set-?>");
        this.prospector = spawningProspector;
    }

    @NotNull
    public final AreaContextResolver getResolver() {
        return this.resolver;
    }

    public final void setResolver(@NotNull AreaContextResolver areaContextResolver2) {
        Intrinsics.checkNotNullParameter((Object)areaContextResolver2, (String)"<set-?>");
        this.resolver = areaContextResolver2;
    }

    @NotNull
    public final List<AreaSpawningContextCalculator<?>> getContextCalculators() {
        return this.contextCalculators;
    }

    public final void setContextCalculators(@NotNull List<? extends AreaSpawningContextCalculator<?>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.contextCalculators = list;
    }

    @Override
    @Nullable
    public Pair<SpawningContext, SpawnDetail> run(@NotNull SpawnCause cause) {
        SpawningArea constrainedArea;
        SpawningArea area;
        Intrinsics.checkNotNullParameter((Object)cause, (String)"cause");
        SpawningArea spawningArea = area = this.getArea(cause);
        SpawningArea spawningArea2 = constrainedArea = spawningArea != null ? this.constrainArea(spawningArea) : null;
        if (constrainedArea != null) {
            int chunksCovered;
            AABB areaBox = AABB.m_165882_((Vec3)new Vec3(Vec3ExtensionsKt.toVec3f(constrainedArea.getCenter())), (double)96.0, (double)1000.0, (double)96.0);
            ServerLevel serverLevel = constrainedArea.getWorld();
            Intrinsics.checkNotNullExpressionValue((Object)areaBox, (String)"areaBox");
            if (!WorldExtensionsKt.isBoxLoaded(serverLevel, areaBox)) {
                return null;
            }
            int numberNearby2 = constrainedArea.getWorld().m_6443_(PokemonEntity.class, areaBox, arg_0 -> AreaSpawner.run$lambda$0((Function1)run.numberNearby.1.INSTANCE, arg_0)).size();
            if ((float)numberNearby2 / (float)(chunksCovered = 9) >= Cobblemon.INSTANCE.getConfig().getPokemonPerChunk()) {
                return null;
            }
            WorldSlice slice = this.prospector.prospect(this, constrainedArea);
            List<AreaSpawningContext> contexts = this.resolver.resolve(this, this.contextCalculators, slice);
            return this.getSpawningSelector().select(this, contexts);
        }
        return null;
    }

    public final boolean isValidStartPoint(@NotNull Level world, @NotNull ChunkAccess chunk, @NotNull BlockPos.MutableBlockPos startPos) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)chunk, (String)"chunk");
        Intrinsics.checkNotNullParameter((Object)startPos, (String)"startPos");
        int y = startPos.m_123342_();
        if (!world.m_46749_((BlockPos)startPos) || !world.m_46749_((BlockPos)startPos.m_142448_(y + 1))) {
            return false;
        }
        BlockState mid = chunk.m_8055_((BlockPos)startPos.m_142448_(y));
        BlockState above = chunk.m_8055_((BlockPos)startPos.m_142448_(y + 1));
        if (!above.m_60647_((BlockGetter)world, (BlockPos)startPos, PathComputationType.AIR)) {
            return false;
        }
        return !mid.m_60795_();
    }

    @Nullable
    public final SpawningArea constrainArea(@NotNull SpawningArea area) {
        Intrinsics.checkNotNullParameter((Object)area, (String)"area");
        BlockPos.MutableBlockPos basePos = new BlockPos.MutableBlockPos(area.getBaseX(), area.getBaseY(), area.getBaseZ());
        int originalY = area.getBaseY();
        Pair pair = new Pair((Object)SectionPos.m_123171_((int)area.getBaseX()), (Object)SectionPos.m_123171_((int)area.getBaseZ()));
        int chunkX = ((Number)pair.component1()).intValue();
        int chunkZ = ((Number)pair.component2()).intValue();
        if (!area.getWorld().m_143319_(ChunkPos.m_45589_((int)chunkX, (int)chunkZ))) {
            return null;
        }
        ChunkAccess chunkAccess = area.getWorld().m_46819_(chunkX, chunkZ, ChunkStatus.f_62326_);
        if (chunkAccess == null) {
            return null;
        }
        ChunkAccess chunk = chunkAccess;
        boolean valid = this.isValidStartPoint((Level)area.getWorld(), chunk, basePos);
        if (!valid) {
            int offset = 1;
            do {
                Level level = (Level)area.getWorld();
                BlockPos.MutableBlockPos mutableBlockPos = basePos.m_142448_(originalY + offset);
                Intrinsics.checkNotNullExpressionValue((Object)mutableBlockPos, (String)"basePos.setY(originalY + offset)");
                if (this.isValidStartPoint(level, chunk, mutableBlockPos)) {
                    valid = true;
                    basePos.m_142448_(originalY + offset);
                    break;
                }
                Level level2 = (Level)area.getWorld();
                BlockPos.MutableBlockPos mutableBlockPos2 = basePos.m_142448_(originalY - offset);
                Intrinsics.checkNotNullExpressionValue((Object)mutableBlockPos2, (String)"basePos.setY(originalY - offset)");
                if (!this.isValidStartPoint(level2, chunk, mutableBlockPos2)) continue;
                valid = true;
                basePos.m_142448_(originalY + offset);
                break;
            } while (++offset <= Cobblemon.INSTANCE.getConfig().getMaxVerticalCorrectionBlocks());
        }
        if (valid) {
            BlockPos min2 = WorldExtensionsKt.squeezeWithinBounds((Level)area.getWorld(), (BlockPos)basePos);
            Level level = (Level)area.getWorld();
            BlockPos blockPos2 = basePos.m_7918_(area.getLength(), area.getHeight(), area.getWidth());
            Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"basePos.add(area.length, area.height, area.width)");
            BlockPos max2 = WorldExtensionsKt.squeezeWithinBounds(level, blockPos2);
            if (area.getWorld().m_46749_(min2) && area.getWorld().m_46749_(max2) && min2.m_123341_() < max2.m_123341_() && min2.m_123342_() < max2.m_123342_() && min2.m_123343_() < max2.m_123343_()) {
                return new SpawningArea(area.getCause(), area.getWorld(), min2.m_123341_(), min2.m_123342_(), min2.m_123343_(), max2.m_123341_() - min2.m_123341_(), max2.m_123342_() - min2.m_123342_(), max2.m_123343_() - min2.m_123343_());
            }
        }
        return null;
    }

    private static final boolean run$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/spawning/spawner/AreaSpawner$Companion;", "", "", "CHUNK_REACH", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

