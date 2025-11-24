/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\n\b\u0016\u0018\u00002\u00020\u0001Bc\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u00100\u001a\u00020\u0002\u0012\u0006\u0010&\u001a\u00020\u001b\u0012\u0006\u00104\u001a\u00020\u001b\u0012\u0006\u0010\u0012\u001a\u00020\u000b\u0012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020!0 \u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\t0(\u0012\u0006\u00107\u001a\u000206\u00a2\u0006\u0004\b>\u0010?J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0017\u001a\u00020\u00168\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001c\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR \u0010\"\u001a\b\u0012\u0004\u0012\u00020!0 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010\u001d\u001a\u0004\b'\u0010\u001fR!\u0010-\u001a\b\u0012\u0004\u0012\u00020)0(8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010%R\u001d\u0010.\u001a\b\u0012\u0004\u0012\u00020\t0(8\u0006\u00a2\u0006\f\n\u0004\b.\u0010#\u001a\u0004\b/\u0010%R\u001a\u00100\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b0\u00101\u001a\u0004\b2\u00103R\u001a\u00104\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b4\u0010\u001d\u001a\u0004\b5\u0010\u001fR\u0017\u00107\u001a\u0002068\u0006\u00a2\u0006\f\n\u0004\b7\u00108\u001a\u0004\b9\u0010:R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010;\u001a\u0004\b<\u0010=\u00a8\u0006@"}, d2={"Lcom/cobblemon/mod/common/api/spawning/context/AreaSpawningContext;", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "Lnet/minecraft/core/BlockPos;", "pos", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache;", "getStructureCache", "(Lnet/minecraft/core/BlockPos;)Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache;", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "", "isSafeSpace", "(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;", "detail", "postFilter", "(Lcom/cobblemon/mod/common/api/spawning/detail/SpawnDetail;)Z", "canSeeSky", "Z", "getCanSeeSky", "()Z", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "cause", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "getCause", "()Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "", "height", "I", "getHeight", "()I", "", "Lcom/cobblemon/mod/common/api/spawning/influence/SpawningInfluence;", "influences", "Ljava/util/List;", "getInfluences", "()Ljava/util/List;", "light", "getLight", "", "Lnet/minecraft/world/level/block/Block;", "nearbyBlockTypes$delegate", "Lkotlin/Lazy;", "getNearbyBlockTypes", "nearbyBlockTypes", "nearbyBlocks", "getNearbyBlocks", "position", "Lnet/minecraft/core/BlockPos;", "getPosition", "()Lnet/minecraft/core/BlockPos;", "skyLight", "getSkyLight", "Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "slice", "Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "getSlice", "()Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "Lnet/minecraft/server/level/ServerLevel;", "getWorld", "()Lnet/minecraft/server/level/ServerLevel;", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;IIZLjava/util/List;ILjava/util/List;Lcom/cobblemon/mod/common/api/spawning/WorldSlice;)V", "common"})
@SourceDebugExtension(value={"SMAP\nAreaSpawningContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaSpawningContext.kt\ncom/cobblemon/mod/common/api/spawning/context/AreaSpawningContext\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,86:1\n1#2:87\n*E\n"})
public class AreaSpawningContext
extends SpawningContext {
    @NotNull
    private final SpawnCause cause;
    @NotNull
    private final ServerLevel world;
    @NotNull
    private final BlockPos position;
    private final int light;
    private final int skyLight;
    private final boolean canSeeSky;
    @NotNull
    private final List<SpawningInfluence> influences;
    private final int height;
    @NotNull
    private final List<BlockState> nearbyBlocks;
    @NotNull
    private final WorldSlice slice;
    @NotNull
    private final Lazy nearbyBlockTypes$delegate;

    public AreaSpawningContext(@NotNull SpawnCause cause, @NotNull ServerLevel world, @NotNull BlockPos position, int light, int skyLight, boolean canSeeSky, @NotNull List<SpawningInfluence> influences, int height, @NotNull List<? extends BlockState> nearbyBlocks, @NotNull WorldSlice slice) {
        Intrinsics.checkNotNullParameter((Object)cause, (String)"cause");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter(influences, (String)"influences");
        Intrinsics.checkNotNullParameter(nearbyBlocks, (String)"nearbyBlocks");
        Intrinsics.checkNotNullParameter((Object)slice, (String)"slice");
        this.cause = cause;
        this.world = world;
        this.position = position;
        this.light = light;
        this.skyLight = skyLight;
        this.canSeeSky = canSeeSky;
        this.influences = influences;
        this.height = height;
        this.nearbyBlocks = nearbyBlocks;
        this.slice = slice;
        this.nearbyBlockTypes$delegate = LazyKt.lazy((Function0)((Function0)new Function0<List<? extends Block>>(this){
            final /* synthetic */ AreaSpawningContext this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final List<Block> invoke() {
                void $this$mapNotNullTo$iv$iv;
                Iterable $this$mapNotNull$iv = this.this$0.getNearbyBlocks();
                boolean $i$f$mapNotNull = false;
                Iterable iterable = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList<E>();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator<T> iterator = $this$forEach$iv$iv$iv.iterator();
                while (iterator.hasNext()) {
                    Block it$iv$iv;
                    T element$iv$iv$iv;
                    T element$iv$iv = element$iv$iv$iv = iterator.next();
                    boolean bl = false;
                    BlockState it = (BlockState)element$iv$iv;
                    boolean bl2 = false;
                    if (it.m_60734_() == null) continue;
                    boolean bl3 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                return CollectionsKt.distinct((Iterable)((List)destination$iv$iv));
            }
        }));
    }

    @Override
    @NotNull
    public SpawnCause getCause() {
        return this.cause;
    }

    @Override
    @NotNull
    public ServerLevel getWorld() {
        return this.world;
    }

    @Override
    @NotNull
    public BlockPos getPosition() {
        return this.position;
    }

    @Override
    public int getLight() {
        return this.light;
    }

    @Override
    public int getSkyLight() {
        return this.skyLight;
    }

    @Override
    public boolean getCanSeeSky() {
        return this.canSeeSky;
    }

    @Override
    @NotNull
    public List<SpawningInfluence> getInfluences() {
        return this.influences;
    }

    public final int getHeight() {
        return this.height;
    }

    @NotNull
    public final List<BlockState> getNearbyBlocks() {
        return this.nearbyBlocks;
    }

    @NotNull
    public final WorldSlice getSlice() {
        return this.slice;
    }

    @NotNull
    public final List<Block> getNearbyBlockTypes() {
        Lazy lazy = this.nearbyBlockTypes$delegate;
        return (List)lazy.getValue();
    }

    @Override
    @NotNull
    public SpawningContext.StructureChunkCache getStructureCache(@NotNull BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        return this.slice.getStructureCache(pos);
    }

    public boolean isSafeSpace(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        return !state.m_60838_((BlockGetter)world, pos);
    }

    @Override
    public boolean postFilter(@NotNull SpawnDetail detail) {
        Intrinsics.checkNotNullParameter((Object)detail, (String)"detail");
        if (!super.postFilter(detail)) {
            return false;
        }
        if (detail.getWidth() > 1 || detail.getHeight() > 1) {
            Integer n = detail.getWidth();
            int it22 = ((Number)n).intValue();
            boolean bl = false;
            Integer n2 = it22 > 0 ? n : null;
            int sizeX = n2 != null ? n2 : 1;
            Integer it22 = detail.getHeight();
            int it = ((Number)it22).intValue();
            boolean bl2 = false;
            Integer n3 = it > 0 ? it22 : null;
            int sizeY = n3 != null ? n3 : 1;
            int minX = (int)Math.floor((double)this.getPosition().m_123341_() + 0.5 - (double)((float)(sizeX - 1) / 2.0f)) - 1;
            int maxX = (int)Math.ceil((double)this.getPosition().m_123341_() + 0.5 + (double)((float)(sizeX + 1) / 2.0f)) + 1;
            int maxY = (int)Math.ceil((float)this.getPosition().m_123342_() + (float)(sizeY + 1) / 2.0f) + 1;
            int minZ = (int)Math.floor((double)this.getPosition().m_123343_() + 0.5 - (double)((float)(sizeX - 1) / 2.0f)) - 1;
            int maxZ = (int)Math.ceil((double)this.getPosition().m_123343_() + 0.5 + (double)((float)(sizeX + 1) / 2.0f)) + 1;
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            block0: for (int x = minX; x < maxX; ++x) {
                int y = this.getPosition().m_123342_() + 1;
                if (y > maxY) continue;
                while (true) {
                    for (int z = minZ; z < maxZ; ++z) {
                        BlockState state = this.getWorld().m_8055_((BlockPos)mutable.m_122178_(x, y, z));
                        ServerLevel serverLevel = this.getWorld();
                        BlockPos blockPos2 = (BlockPos)mutable;
                        Intrinsics.checkNotNullExpressionValue((Object)state, (String)"state");
                        if (this.isSafeSpace(serverLevel, blockPos2, state)) continue;
                        return false;
                    }
                    if (y == maxY) continue block0;
                    ++y;
                }
            }
        }
        return true;
    }
}

