/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.advancements.critereon.BlockPredicate
 *  net.minecraft.advancements.critereon.BlockPredicate$Builder
 *  net.minecraft.advancements.critereon.StatePropertiesPredicate$Builder
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.builder.MultiblockStructureBuilder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.condition.BlockRelativeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RestorationTankBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u000f\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R \u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/block/multiblock/FossilMultiblockBuilder;", "Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;", "Lnet/minecraft/server/level/ServerLevel;", "world", "", "form", "(Lnet/minecraft/server/level/ServerLevel;)V", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "boundingBox", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getBoundingBox", "()Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lnet/minecraft/core/BlockPos;", "centerPos", "Lnet/minecraft/core/BlockPos;", "getCenterPos", "()Lnet/minecraft/core/BlockPos;", "", "Lcom/cobblemon/mod/common/api/multiblock/condition/BlockRelativeCondition;", "conditions", "Ljava/util/List;", "getConditions", "()Ljava/util/List;", "<init>", "(Lnet/minecraft/core/BlockPos;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nFossilMultiblockBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilMultiblockBuilder.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockBuilder\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,170:1\n766#2:171\n857#2,2:172\n1549#2:174\n1620#2,3:175\n1549#2:178\n1620#2,2:179\n1855#2,2:181\n1622#2:183\n350#2,7:184\n766#2:191\n857#2,2:192\n*S KotlinDebug\n*F\n+ 1 FossilMultiblockBuilder.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockBuilder\n*L\n89#1:171\n89#1:172,2\n93#1:174\n93#1:175,3\n98#1:178\n98#1:179,2\n102#1:181,2\n98#1:183\n110#1:184,7\n126#1:191\n126#1:192,2\n*E\n"})
public final class FossilMultiblockBuilder
implements MultiblockStructureBuilder {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final BlockPos centerPos;
    @NotNull
    private final VoxelShape boundingBox;
    @NotNull
    private final List<BlockRelativeCondition> conditions;
    @NotNull
    private static final CompoundTag NBT_TO_CHECK;
    private static final BlockPredicate MONITOR_PRED;
    private static final BlockPredicate FOSSIL_ANALYZER_PRED;
    private static final BlockPredicate RESTORATION_TANK_PRED;

    public FossilMultiblockBuilder(@NotNull BlockPos centerPos) {
        Intrinsics.checkNotNullParameter((Object)centerPos, (String)"centerPos");
        this.centerPos = centerPos;
        VoxelShape voxelShape = Shapes.m_83110_((VoxelShape)Shapes.m_83048_((double)((double)this.centerPos.m_123341_() - 1.0), (double)((double)this.centerPos.m_123342_() - 1.0), (double)this.centerPos.m_123343_(), (double)((double)this.centerPos.m_123341_() + 2.0), (double)((double)this.centerPos.m_123342_() + 2.0), (double)((double)this.centerPos.m_123343_() + 1.0)), (VoxelShape)Shapes.m_83048_((double)this.centerPos.m_123341_(), (double)((double)this.centerPos.m_123342_() - 1.0), (double)((double)this.centerPos.m_123343_() - 1.0), (double)((double)this.centerPos.m_123341_() + 1.0), (double)((double)this.centerPos.m_123342_() + 2.0), (double)((double)this.centerPos.m_123343_() + 2.0)));
        Intrinsics.checkNotNullExpressionValue((Object)voxelShape, (String)"union(\n            Voxel\u2026)\n            )\n        )");
        this.boundingBox = voxelShape;
        Object[] objectArray = new BlockRelativeCondition[2];
        BlockPredicate blockPredicate = FOSSIL_ANALYZER_PRED;
        Intrinsics.checkNotNullExpressionValue((Object)blockPredicate, (String)"FOSSIL_ANALYZER_PRED");
        BlockPredicate blockPredicate2 = MONITOR_PRED;
        Intrinsics.checkNotNullExpressionValue((Object)blockPredicate2, (String)"MONITOR_PRED");
        Direction[] directionArray = new Direction[]{Direction.UP};
        objectArray[0] = new BlockRelativeCondition(blockPredicate, blockPredicate2, directionArray);
        BlockPredicate blockPredicate3 = FOSSIL_ANALYZER_PRED;
        Intrinsics.checkNotNullExpressionValue((Object)blockPredicate3, (String)"FOSSIL_ANALYZER_PRED");
        BlockPredicate blockPredicate4 = RESTORATION_TANK_PRED;
        Intrinsics.checkNotNullExpressionValue((Object)blockPredicate4, (String)"RESTORATION_TANK_PRED");
        directionArray = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
        objectArray[1] = new BlockRelativeCondition(blockPredicate3, blockPredicate4, directionArray);
        this.conditions = CollectionsKt.listOf((Object[])objectArray);
    }

    @NotNull
    public final BlockPos getCenterPos() {
        return this.centerPos;
    }

    @Override
    @NotNull
    public VoxelShape getBoundingBox() {
        return this.boundingBox;
    }

    @NotNull
    public List<BlockRelativeCondition> getConditions() {
        return this.conditions;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void form(@NotNull ServerLevel world) {
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        int fossilTankIndex;
        Object item$iv2;
        List restorationTankPositions;
        Iterable $this$forEach$iv;
        List fossilAnalyzerPositions;
        Object object;
        List monitorPositions;
        List dirsToCheck;
        block19: {
            int n;
            void $this$mapTo$iv$iv;
            void $this$mapTo$iv$iv2;
            void $this$filterTo$iv$iv2;
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            List<BlockPos> blocks = MiscUtils.blockPositionsAsList(this.getBoundingBox());
            Object[] objectArray = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
            dirsToCheck = CollectionsKt.listOf((Object[])objectArray);
            Iterable $this$filter$iv2 = blocks;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv2;
            Iterable destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                BlockPos it = (BlockPos)element$iv$iv;
                boolean bl = false;
                if (!MONITOR_PRED.m_17914_(world, it)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            monitorPositions = (List)destination$iv$iv;
            Iterable $this$map$iv = monitorPositions;
            boolean $i$f$map = false;
            destination$iv$iv = $this$map$iv;
            Iterable destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv2) {
                void it;
                BlockPos bl = (BlockPos)item$iv$iv;
                object = destination$iv$iv2;
                boolean bl2 = false;
                object.add(FOSSIL_ANALYZER_PRED.m_17914_(world, it.m_7495_()) ? it.m_7495_() : null);
            }
            fossilAnalyzerPositions = (List)destination$iv$iv2;
            Iterable $this$map$iv2 = fossilAnalyzerPositions;
            boolean $i$f$map2 = false;
            destination$iv$iv2 = $this$map$iv2;
            Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
            boolean $i$f$mapTo2 = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                BlockPos blockPos2;
                block18: {
                    void analyzerPosition;
                    BlockPos bl2 = (BlockPos)item$iv$iv;
                    object = destination$iv$iv3;
                    boolean bl4 = false;
                    if (analyzerPosition == null) {
                        blockPos2 = null;
                    } else {
                        $this$forEach$iv = dirsToCheck;
                        boolean $i$f$forEach = false;
                        for (Object element$iv : $this$forEach$iv) {
                            Direction it = (Direction)element$iv;
                            boolean bl3 = false;
                            if (!RESTORATION_TANK_PRED.m_17914_(world, analyzerPosition.m_121945_(it))) continue;
                            blockPos2 = analyzerPosition.m_121945_(it);
                            break block18;
                        }
                        blockPos2 = null;
                    }
                }
                object.add(blockPos2);
            }
            List $this$indexOfFirst$iv = restorationTankPositions = (List)destination$iv$iv3;
            boolean $i$f$indexOfFirst = false;
            int index$iv = 0;
            for (Object item$iv2 : $this$indexOfFirst$iv) {
                BlockPos it = (BlockPos)item$iv2;
                boolean bl = false;
                if (it != null) {
                    n = index$iv;
                    break block19;
                }
                ++index$iv;
            }
            n = fossilTankIndex = -1;
        }
        if (fossilTankIndex == -1) {
            Cobblemon.INSTANCE.getLOGGER().error("FossilMultiblockBuilder form called on invalid structure! This should never happen!");
            return;
        }
        BlockPos monitorPos = (BlockPos)monitorPositions.get(fossilTankIndex);
        Object e = fossilAnalyzerPositions.get(fossilTankIndex);
        Intrinsics.checkNotNull(e);
        BlockPos fossilAnalyzerPos = (BlockPos)e;
        Object e2 = restorationTankPositions.get(fossilTankIndex);
        Intrinsics.checkNotNull(e2);
        BlockPos restorationTankPos = (BlockPos)e2;
        item$iv2 = world.m_7702_(monitorPos);
        MultiblockEntity monitorEntity = item$iv2 instanceof MultiblockEntity ? (MultiblockEntity)((Object)item$iv2) : null;
        BlockEntity it = world.m_7702_(fossilAnalyzerPos);
        MultiblockEntity analyzerEntity = it instanceof MultiblockEntity ? (MultiblockEntity)it : null;
        BlockEntity bl = world.m_7702_(restorationTankPos);
        MultiblockEntity tankBaseEntity = bl instanceof MultiblockEntity ? (MultiblockEntity)bl : null;
        BlockEntity bl4 = world.m_7702_(restorationTankPos.m_7494_());
        MultiblockEntity tankTopEntity = bl4 instanceof MultiblockEntity ? (MultiblockEntity)bl4 : null;
        FossilMultiblockStructure structure = new FossilMultiblockStructure(monitorPos, fossilAnalyzerPos, restorationTankPos, 0, 0.0f, 24, null);
        $this$forEach$iv = dirsToCheck;
        object = structure;
        boolean $i$f$filter = false;
        Iterator iterator = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Direction it2 = (Direction)element$iv$iv;
            boolean bl5 = false;
            BlockPos adjPos = restorationTankPos.m_121945_(it2);
            if (!Intrinsics.areEqual((Object)adjPos, (Object)fossilAnalyzerPos)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        ((FossilMultiblockStructure)object).setTankConnectorDirection((Direction)CollectionsKt.first((List)((List)destination$iv$iv)));
        MultiblockEntity multiblockEntity = analyzerEntity;
        if (multiblockEntity != null) {
            multiblockEntity.setMultiblockStructure(structure);
        }
        MultiblockEntity multiblockEntity2 = tankBaseEntity;
        if (multiblockEntity2 != null) {
            multiblockEntity2.setMultiblockStructure(structure);
        }
        MultiblockEntity multiblockEntity3 = tankTopEntity;
        if (multiblockEntity3 != null) {
            multiblockEntity3.setMultiblockStructure(structure);
        }
        MultiblockEntity multiblockEntity4 = monitorEntity;
        if (multiblockEntity4 != null) {
            multiblockEntity4.setMultiblockStructure(structure);
        }
        structure.syncToClient((Level)world);
        structure.markDirty((Level)world);
        world.m_247517_(null, this.centerPos, CobblemonSounds.FOSSIL_MACHINE_ASSEMBLE, SoundSource.BLOCKS);
        MultiblockEntity multiblockEntity5 = analyzerEntity;
        if (multiblockEntity5 != null) {
            multiblockEntity5.setMultiblockBuilder(null);
        }
        MultiblockEntity multiblockEntity6 = tankBaseEntity;
        if (multiblockEntity6 != null) {
            multiblockEntity6.setMultiblockBuilder(null);
        }
        MultiblockEntity multiblockEntity7 = tankTopEntity;
        if (multiblockEntity7 != null) {
            multiblockEntity7.setMultiblockBuilder(null);
        }
        MultiblockEntity multiblockEntity8 = monitorEntity;
        if (multiblockEntity8 != null) {
            multiblockEntity8.setMultiblockBuilder(null);
        }
    }

    @Override
    public boolean validate(@NotNull ServerLevel world) {
        return MultiblockStructureBuilder.DefaultImpls.validate(this, world);
    }

    /*
     * WARNING - void declaration
     */
    static {
        void var3_2;
        Companion $this$NBT_TO_CHECK_u24lambda_u246 = Companion = new Companion(null);
        boolean bl = false;
        CompoundTag nbt = new CompoundTag();
        nbt.m_128379_("Formed", false);
        NBT_TO_CHECK = var3_2;
        Block[] blockArray = new Block[]{CobblemonBlocks.MONITOR};
        MONITOR_PRED = BlockPredicate.Builder.m_17924_().m_146726_(blockArray).m_146724_(NBT_TO_CHECK).m_17931_();
        blockArray = new Block[]{CobblemonBlocks.FOSSIL_ANALYZER};
        FOSSIL_ANALYZER_PRED = BlockPredicate.Builder.m_17924_().m_146726_(blockArray).m_146724_(NBT_TO_CHECK).m_17931_();
        blockArray = new Block[]{CobblemonBlocks.RESTORATION_TANK};
        RESTORATION_TANK_PRED = BlockPredicate.Builder.m_17924_().m_146724_(NBT_TO_CHECK).m_146726_(blockArray).m_17929_(StatePropertiesPredicate.Builder.m_67693_().m_67697_((Property)RestorationTankBlock.Companion.getPART(), (Comparable)((Object)RestorationTankBlock.TankPart.BOTTOM)).m_67706_()).m_17931_();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u001f\u0010\b\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0005\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u001f\u0010\u000f\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0005\u001a\u0004\b\u0010\u0010\u0007\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/block/multiblock/FossilMultiblockBuilder$Companion;", "", "Lnet/minecraft/advancements/critereon/BlockPredicate;", "kotlin.jvm.PlatformType", "FOSSIL_ANALYZER_PRED", "Lnet/minecraft/advancements/critereon/BlockPredicate;", "getFOSSIL_ANALYZER_PRED", "()Lnet/minecraft/advancements/critereon/BlockPredicate;", "MONITOR_PRED", "getMONITOR_PRED", "Lnet/minecraft/nbt/CompoundTag;", "NBT_TO_CHECK", "Lnet/minecraft/nbt/CompoundTag;", "getNBT_TO_CHECK", "()Lnet/minecraft/nbt/CompoundTag;", "RESTORATION_TANK_PRED", "getRESTORATION_TANK_PRED", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final CompoundTag getNBT_TO_CHECK() {
            return NBT_TO_CHECK;
        }

        public final BlockPredicate getMONITOR_PRED() {
            return MONITOR_PRED;
        }

        public final BlockPredicate getFOSSIL_ANALYZER_PRED() {
            return FOSSIL_ANALYZER_PRED;
        }

        public final BlockPredicate getRESTORATION_TANK_PRED() {
            return RESTORATION_TANK_PRED;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

