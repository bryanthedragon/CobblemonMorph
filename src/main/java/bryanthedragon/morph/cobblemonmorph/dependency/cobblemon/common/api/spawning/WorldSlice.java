/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnCause;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u000e\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 c2\u00020\u0001:\u0002dcBk\u0012\u0006\u0010C\u001a\u00020B\u0012\u0006\u0010]\u001a\u00020\\\u0012\u0006\u00105\u001a\u00020\u0002\u0012\u0006\u00109\u001a\u00020\u0002\u0012\u0006\u0010;\u001a\u00020\u0002\u0012\u0018\u0010>\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160=0=0=\u0012\u0012\u0010R\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020=0=\u0012\f\u0010L\u001a\b\u0012\u0004\u0012\u00020K0.\u00a2\u0006\u0004\ba\u0010bJ/\u0010\b\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00022\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\b\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\b\u0010\fJA\u0010\u0014\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010\u0013\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J%\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0015\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u0017\u0010\u0019J/\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u0011\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u001f\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u0011\u00a2\u0006\u0004\b\u001b\u0010\u001dJ/\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00022\b\b\u0002\u0010\u001e\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001f\u0010 J\u001f\u0010\u001f\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\u001e\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001f\u0010!J/\u0010\"\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00022\b\b\u0002\u0010\u001e\u001a\u00020\u0002\u00a2\u0006\u0004\b\"\u0010 J\u001f\u0010\"\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\u001e\u001a\u00020\u0002\u00a2\u0006\u0004\b\"\u0010!J\u0015\u0010%\u001a\u00020$2\u0006\u0010#\u001a\u00020\n\u00a2\u0006\u0004\b%\u0010&JA\u0010'\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010\u0013\u001a\u00020\u0002\u00a2\u0006\u0004\b'\u0010\u0015JA\u0010(\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00022\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010\u0013\u001a\u00020\u0002\u00a2\u0006\u0004\b(\u0010\u0015J1\u0010(\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00060\u00102\u0006\u0010\u0013\u001a\u00020\u0002\u00a2\u0006\u0004\b(\u0010)J%\u0010*\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b*\u0010+J;\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00110.2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u00022\u0006\u0010,\u001a\u00020\u00022\u0006\u0010-\u001a\u00020\u0002\u00a2\u0006\u0004\b/\u00100J+\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00110.2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010,\u001a\u00020\u00022\u0006\u0010-\u001a\u00020\u0002\u00a2\u0006\u0004\b/\u00101J%\u00102\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b2\u00103J\u0015\u00102\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b2\u00104R\u0017\u00105\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b5\u00106\u001a\u0004\b7\u00108R\u0017\u00109\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b9\u00106\u001a\u0004\b:\u00108R\u0017\u0010;\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b;\u00106\u001a\u0004\b<\u00108R)\u0010>\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160=0=0=8\u0006\u00a2\u0006\f\n\u0004\b>\u0010?\u001a\u0004\b@\u0010AR\u0017\u0010C\u001a\u00020B8\u0006\u00a2\u0006\f\n\u0004\bC\u0010D\u001a\u0004\bE\u0010FR\u0017\u0010G\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\bG\u00106\u001a\u0004\bH\u00108R\u0017\u0010I\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\bI\u00106\u001a\u0004\bJ\u00108R(\u0010L\u001a\b\u0012\u0004\u0012\u00020K0.8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bL\u0010M\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR#\u0010R\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020=0=8\u0006\u00a2\u0006\f\n\u0004\bR\u0010S\u001a\u0004\bT\u0010UR \u0010X\u001a\u000e\u0012\u0004\u0012\u00020W\u0012\u0004\u0012\u00020$0V8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bX\u0010YR\u0017\u0010Z\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\bZ\u00106\u001a\u0004\b[\u00108R\u0017\u0010]\u001a\u00020\\8\u0006\u00a2\u0006\f\n\u0004\b]\u0010^\u001a\u0004\b_\u0010`\u00a8\u0006e"}, d2={"Lcom/cobblemon/mod/common/api/spawning/WorldSlice;", "", "", "x", "y", "z", "", "elseCanSeeSky", "canSeeSky", "(IIIZ)Z", "Lnet/minecraft/core/BlockPos;", "position", "(Lnet/minecraft/core/BlockPos;Z)Z", "centerX", "centerY", "centerZ", "Lkotlin/Function1;", "Lnet/minecraft/world/level/block/state/BlockState;", "condition", "maximum", "depthSpace", "(IIILkotlin/jvm/functions/Function1;I)I", "Lcom/cobblemon/mod/common/api/spawning/WorldSlice$BlockData;", "getBlockData", "(III)Lcom/cobblemon/mod/common/api/spawning/WorldSlice$BlockData;", "(Lnet/minecraft/core/BlockPos;)Lcom/cobblemon/mod/common/api/spawning/WorldSlice$BlockData;", "elseBlock", "getBlockState", "(IIILnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/state/BlockState;", "elseLight", "getLight", "(IIII)I", "(Lnet/minecraft/core/BlockPos;I)I", "getSkyLight", "pos", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache;", "getStructureCache", "(Lnet/minecraft/core/BlockPos;)Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext$StructureChunkCache;", "heightSpace", "horizontalSpace", "(Lnet/minecraft/core/BlockPos;Lkotlin/jvm/functions/Function1;I)I", "isInBounds", "(III)Z", "maxHorizontalRadius", "maxVerticalRadius", "", "nearbyBlocks", "(IIIII)Ljava/util/List;", "(Lnet/minecraft/core/BlockPos;II)Ljava/util/List;", "skySpaceAbove", "(III)I", "(Lnet/minecraft/core/BlockPos;)I", "baseX", "I", "getBaseX", "()I", "baseY", "getBaseY", "baseZ", "getBaseZ", "", "blocks", "[[[Lcom/cobblemon/mod/common/api/spawning/WorldSlice$BlockData;", "getBlocks", "()[[[Lcom/cobblemon/mod/common/api/spawning/WorldSlice$BlockData;", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "cause", "Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "getCause", "()Lcom/cobblemon/mod/common/api/spawning/SpawnCause;", "height", "getHeight", "length", "getLength", "Lnet/minecraft/world/phys/Vec3;", "nearbyEntityPositions", "Ljava/util/List;", "getNearbyEntityPositions", "()Ljava/util/List;", "setNearbyEntityPositions", "(Ljava/util/List;)V", "skyLevel", "[[Ljava/lang/Integer;", "getSkyLevel", "()[[Ljava/lang/Integer;", "", "Lnet/minecraft/world/level/ChunkPos;", "structureChunkCaches", "Ljava/util/Map;", "width", "getWidth", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/server/level/ServerLevel;", "getWorld", "()Lnet/minecraft/server/level/ServerLevel;", "<init>", "(Lcom/cobblemon/mod/common/api/spawning/SpawnCause;Lnet/minecraft/server/level/ServerLevel;III[[[Lcom/cobblemon/mod/common/api/spawning/WorldSlice$BlockData;[[Ljava/lang/Integer;Ljava/util/List;)V", "Companion", "BlockData", "common"})
@SourceDebugExtension(value={"SMAP\nWorldSlice.kt\nKotlin\n*S Kotlin\n*F\n+ 1 WorldSlice.kt\ncom/cobblemon/mod/common/api/spawning/WorldSlice\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,233:1\n361#2,7:234\n*S KotlinDebug\n*F\n+ 1 WorldSlice.kt\ncom/cobblemon/mod/common/api/spawning/WorldSlice\n*L\n58#1:234,7\n*E\n"})
public final class WorldSlice {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final SpawnCause cause;
    @NotNull
    private final ServerLevel world;
    private final int baseX;
    private final int baseY;
    private final int baseZ;
    @NotNull
    private final BlockData[][][] blocks;
    @NotNull
    private final Integer[][] skyLevel;
    @NotNull
    private List<? extends Vec3> nearbyEntityPositions;
    private final int length;
    private final int height;
    private final int width;
    @NotNull
    private final Map<ChunkPos, SpawningContext.StructureChunkCache> structureChunkCaches;
    private static final BlockState stoneState = Blocks.f_50069_.m_49966_();

    public WorldSlice(@NotNull SpawnCause cause, @NotNull ServerLevel world, int baseX, int baseY, int baseZ, @NotNull BlockData[][][] blocks, @NotNull Integer[][] skyLevel, @NotNull List<? extends Vec3> nearbyEntityPositions) {
        Intrinsics.checkNotNullParameter((Object)cause, (String)"cause");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)blocks, (String)"blocks");
        Intrinsics.checkNotNullParameter((Object)skyLevel, (String)"skyLevel");
        Intrinsics.checkNotNullParameter(nearbyEntityPositions, (String)"nearbyEntityPositions");
        this.cause = cause;
        this.world = world;
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;
        this.blocks = blocks;
        this.skyLevel = skyLevel;
        this.nearbyEntityPositions = nearbyEntityPositions;
        this.length = ((Object[])this.blocks).length;
        this.height = ((Object[])this.blocks[0]).length;
        this.width = this.blocks[0][0].length;
        this.structureChunkCaches = new LinkedHashMap();
    }

    @NotNull
    public final SpawnCause getCause() {
        return this.cause;
    }

    @NotNull
    public final ServerLevel getWorld() {
        return this.world;
    }

    public final int getBaseX() {
        return this.baseX;
    }

    public final int getBaseY() {
        return this.baseY;
    }

    public final int getBaseZ() {
        return this.baseZ;
    }

    @NotNull
    public final BlockData[][][] getBlocks() {
        return this.blocks;
    }

    @NotNull
    public final Integer[][] getSkyLevel() {
        return this.skyLevel;
    }

    @NotNull
    public final List<Vec3> getNearbyEntityPositions() {
        return this.nearbyEntityPositions;
    }

    public final void setNearbyEntityPositions(@NotNull List<? extends Vec3> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.nearbyEntityPositions = list;
    }

    public final int getLength() {
        return this.length;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getWidth() {
        return this.width;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final SpawningContext.StructureChunkCache getStructureCache(@NotNull BlockPos pos) {
        Object object;
        void $this$getOrPut$iv;
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Map<ChunkPos, SpawningContext.StructureChunkCache> map = this.structureChunkCaches;
        ChunkPos key$iv = new ChunkPos(pos);
        boolean $i$f$getOrPut = false;
        Object value$iv = $this$getOrPut$iv.get(key$iv);
        if (value$iv == null) {
            boolean bl = false;
            SpawningContext.StructureChunkCache answer$iv = new SpawningContext.StructureChunkCache();
            $this$getOrPut$iv.put(key$iv, answer$iv);
            object = answer$iv;
        } else {
            object = value$iv;
        }
        return (SpawningContext.StructureChunkCache)object;
    }

    public final boolean isInBounds(int x, int y, int z) {
        return x >= this.baseX && x < this.baseX + this.length && y >= this.baseY && y < this.baseY + this.height && z >= this.baseZ && z < this.baseZ + this.width;
    }

    @NotNull
    public final BlockData getBlockData(int x, int y, int z) {
        return this.blocks[x - this.baseX][y - this.baseY][z - this.baseZ];
    }

    @NotNull
    public final BlockData getBlockData(@NotNull BlockPos position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return this.getBlockData(position.m_123341_(), position.m_123342_(), position.m_123343_());
    }

    @NotNull
    public final BlockState getBlockState(int x, int y, int z, @NotNull BlockState elseBlock) {
        Intrinsics.checkNotNullParameter((Object)elseBlock, (String)"elseBlock");
        return !this.isInBounds(x, y, z) ? elseBlock : this.blocks[x - this.baseX][y - this.baseY][z - this.baseZ].getState();
    }

    public static /* synthetic */ BlockState getBlockState$default(WorldSlice worldSlice, int n, int n2, int n3, BlockState blockState, int n4, Object object) {
        if ((n4 & 8) != 0) {
            BlockState blockState2 = stoneState;
            Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"stoneState");
            blockState = blockState2;
        }
        return worldSlice.getBlockState(n, n2, n3, blockState);
    }

    @NotNull
    public final BlockState getBlockState(@NotNull BlockPos position, @NotNull BlockState elseBlock) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)elseBlock, (String)"elseBlock");
        return this.getBlockState(position.m_123341_(), position.m_123342_(), position.m_123343_(), elseBlock);
    }

    public static /* synthetic */ BlockState getBlockState$default(WorldSlice worldSlice, BlockPos blockPos2, BlockState blockState, int n, Object object) {
        if ((n & 2) != 0) {
            BlockState blockState2 = stoneState;
            Intrinsics.checkNotNullExpressionValue((Object)blockState2, (String)"stoneState");
            blockState = blockState2;
        }
        return worldSlice.getBlockState(blockPos2, blockState);
    }

    public final int getLight(int x, int y, int z, int elseLight) {
        return !this.isInBounds(x, y, z) ? elseLight : this.getBlockData(x, y, z).getLight();
    }

    public static /* synthetic */ int getLight$default(WorldSlice worldSlice, int n, int n2, int n3, int n4, int n5, Object object) {
        if ((n5 & 8) != 0) {
            n4 = 0;
        }
        return worldSlice.getLight(n, n2, n3, n4);
    }

    public final int getLight(@NotNull BlockPos position, int elseLight) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return this.getLight(position.m_123341_(), position.m_123342_(), position.m_123343_(), elseLight);
    }

    public static /* synthetic */ int getLight$default(WorldSlice worldSlice, BlockPos blockPos2, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return worldSlice.getLight(blockPos2, n);
    }

    public final int getSkyLight(int x, int y, int z, int elseLight) {
        return !this.isInBounds(x, y, z) ? elseLight : this.getBlockData(x, y, z).getSkyLight();
    }

    public static /* synthetic */ int getSkyLight$default(WorldSlice worldSlice, int n, int n2, int n3, int n4, int n5, Object object) {
        if ((n5 & 8) != 0) {
            n4 = 0;
        }
        return worldSlice.getSkyLight(n, n2, n3, n4);
    }

    public final int getSkyLight(@NotNull BlockPos position, int elseLight) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return this.getSkyLight(position.m_123341_(), position.m_123342_(), position.m_123343_(), elseLight);
    }

    public static /* synthetic */ int getSkyLight$default(WorldSlice worldSlice, BlockPos blockPos2, int n, int n2, Object object) {
        if ((n2 & 2) != 0) {
            n = 0;
        }
        return worldSlice.getSkyLight(blockPos2, n);
    }

    public final int skySpaceAbove(int x, int y, int z) {
        return !this.isInBounds(x, y, z) || this.skyLevel[x - this.baseX][z - this.baseZ] > y ? 0 : Math.max(0, this.world.m_151558_() - y);
    }

    public final int skySpaceAbove(@NotNull BlockPos position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return this.skySpaceAbove(position.m_123341_(), position.m_123342_(), position.m_123343_());
    }

    public final boolean canSeeSky(int x, int y, int z, boolean elseCanSeeSky) {
        return !this.isInBounds(x, y, z) ? elseCanSeeSky : y >= this.skyLevel[x - this.baseX][z - this.baseZ];
    }

    public static /* synthetic */ boolean canSeeSky$default(WorldSlice worldSlice, int n, int n2, int n3, boolean bl, int n4, Object object) {
        if ((n4 & 8) != 0) {
            bl = false;
        }
        return worldSlice.canSeeSky(n, n2, n3, bl);
    }

    public final boolean canSeeSky(@NotNull BlockPos position, boolean elseCanSeeSky) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return this.canSeeSky(position.m_123341_(), position.m_123342_(), position.m_123343_(), elseCanSeeSky);
    }

    public static /* synthetic */ boolean canSeeSky$default(WorldSlice worldSlice, BlockPos blockPos2, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        return worldSlice.canSeeSky(blockPos2, bl);
    }

    @NotNull
    public final List<BlockState> nearbyBlocks(@NotNull BlockPos position, int maxHorizontalRadius, int maxVerticalRadius) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        return this.nearbyBlocks(position.m_123341_(), position.m_123342_(), position.m_123343_(), maxHorizontalRadius, maxVerticalRadius);
    }

    @NotNull
    public final List<BlockState> nearbyBlocks(int centerX, int centerY, int centerZ, int maxHorizontalRadius, int maxVerticalRadius) {
        List blocks = new ArrayList();
        int minX = RangesKt.coerceAtLeast((int)(centerX - maxHorizontalRadius), (int)this.baseX);
        int minY = RangesKt.coerceAtLeast((int)(centerY - maxVerticalRadius), (int)this.baseY);
        int minZ = RangesKt.coerceAtLeast((int)(centerZ - maxHorizontalRadius), (int)this.baseZ);
        int maxX = RangesKt.coerceAtMost((int)(centerX + maxHorizontalRadius), (int)(this.baseX + this.length));
        int maxY = RangesKt.coerceAtMost((int)(centerY + maxVerticalRadius), (int)(this.baseY + this.height));
        int maxZ = RangesKt.coerceAtMost((int)(centerZ + maxHorizontalRadius), (int)(this.baseZ + this.width));
        int x = minX;
        if (x <= maxX) {
            while (true) {
                int y;
                if ((y = minY) <= maxY) {
                    while (true) {
                        int z;
                        if ((z = minZ) <= maxZ) {
                            while (true) {
                                blocks.add(WorldSlice.getBlockState$default(this, x, y, z, null, 8, null));
                                if (z == maxZ) break;
                                ++z;
                            }
                        }
                        if (y == maxY) break;
                        ++y;
                    }
                }
                if (x == maxX) break;
                ++x;
            }
        }
        return blocks;
    }

    public final int horizontalSpace(@NotNull BlockPos position, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        return this.horizontalSpace(position.m_123341_(), position.m_123342_(), position.m_123343_(), condition2, maximum);
    }

    public final int horizontalSpace(int centerX, int centerY, int centerZ, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum) {
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        int space = 1;
        int radius = 1;
        while (radius <= maximum) {
            int z;
            int minX = centerX - radius;
            int maxX = centerX + radius;
            int minZ = centerZ - radius;
            int maxZ = centerZ + radius;
            if (!this.isInBounds(minX, centerY, minZ) || !this.isInBounds(maxX, centerY, maxZ)) {
                return space;
            }
            int x = minX;
            for (z = minZ; z <= maxZ; ++z) {
                if (((Boolean)condition2.invoke((Object)WorldSlice.getBlockState$default(this, x, centerY, z, null, 8, null))).booleanValue()) continue;
                return space;
            }
            x = maxX;
            for (z = minZ; z <= maxZ; ++z) {
                if (((Boolean)condition2.invoke((Object)WorldSlice.getBlockState$default(this, x, centerY, z, null, 8, null))).booleanValue()) continue;
                return space;
            }
            z = minZ;
            for (x = minX + 1; x < maxX; ++x) {
                if (((Boolean)condition2.invoke((Object)WorldSlice.getBlockState$default(this, x, centerY, z, null, 8, null))).booleanValue()) continue;
                return space;
            }
            z = maxZ;
            for (x = minX + 1; x < maxX; ++x) {
                if (((Boolean)condition2.invoke((Object)WorldSlice.getBlockState$default(this, x, centerY, z, null, 8, null))).booleanValue()) continue;
                return space;
            }
            ++radius;
            space += 2;
        }
        return space;
    }

    public final int heightSpace(int centerX, int centerY, int centerZ, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum) {
        int space;
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        for (space = 1; space <= maximum; ++space) {
            int y = centerY + space;
            if (y >= this.baseY + this.height) {
                return space;
            }
            if (((Boolean)condition2.invoke((Object)WorldSlice.getBlockState$default(this, centerX, y, centerZ, null, 8, null))).booleanValue()) continue;
            return space;
        }
        return space;
    }

    public final int depthSpace(int centerX, int centerY, int centerZ, @NotNull Function1<? super BlockState, Boolean> condition2, int maximum) {
        int space;
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        for (space = 1; space <= maximum; ++space) {
            int y = centerY - space;
            if (y < this.baseY) {
                return space;
            }
            if (((Boolean)condition2.invoke((Object)WorldSlice.getBlockState$default(this, centerX, y, centerZ, null, 8, null))).booleanValue()) continue;
            return space;
        }
        return space;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/spawning/WorldSlice$BlockData;", "", "", "light", "I", "getLight", "()I", "skyLight", "getSkyLight", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/world/level/block/state/BlockState;", "getState", "()Lnet/minecraft/world/level/block/state/BlockState;", "<init>", "(Lnet/minecraft/world/level/block/state/BlockState;II)V", "common"})
    public static final class BlockData {
        @NotNull
        private final BlockState state;
        private final int light;
        private final int skyLight;

        public BlockData(@NotNull BlockState state, int light, int skyLight) {
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            this.state = state;
            this.light = light;
            this.skyLight = skyLight;
        }

        @NotNull
        public final BlockState getState() {
            return this.state;
        }

        public final int getLight() {
            return this.light;
        }

        public final int getSkyLight() {
            return this.skyLight;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/spawning/WorldSlice$Companion;", "", "Lnet/minecraft/world/level/block/state/BlockState;", "kotlin.jvm.PlatformType", "stoneState", "Lnet/minecraft/world/level/block/state/BlockState;", "getStoneState", "()Lnet/minecraft/world/level/block/state/BlockState;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final BlockState getStoneState() {
            return stoneState;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

