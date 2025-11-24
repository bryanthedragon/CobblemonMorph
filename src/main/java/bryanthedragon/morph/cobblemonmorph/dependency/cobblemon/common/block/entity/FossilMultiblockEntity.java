/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.world.level.ChunkPos
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.builder.MultiblockStructureBuilder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0018\u001a\u00020\t\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\f\b\u0002\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001d\u00a2\u0006\u0004\b\u001f\u0010 J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR$\u0010\n\u001a\u0004\u0018\u00010\t8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR.\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00108V@VX\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "Lcom/cobblemon/mod/common/api/multiblock/MultiblockEntity;", "", "markRemoved", "()V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "readNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "Lnet/minecraft/core/BlockPos;", "masterBlockPos", "Lnet/minecraft/core/BlockPos;", "getMasterBlockPos", "()Lnet/minecraft/core/BlockPos;", "setMasterBlockPos", "(Lnet/minecraft/core/BlockPos;)V", "Lcom/cobblemon/mod/common/api/multiblock/MultiblockStructure;", "structure", "multiblockStructure", "Lcom/cobblemon/mod/common/api/multiblock/MultiblockStructure;", "getMultiblockStructure", "()Lcom/cobblemon/mod/common/api/multiblock/MultiblockStructure;", "setMultiblockStructure", "(Lcom/cobblemon/mod/common/api/multiblock/MultiblockStructure;)V", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;", "multiblockBuilder", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "type", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;Lnet/minecraft/world/level/block/entity/BlockEntityType;)V", "common"})
public class FossilMultiblockEntity
extends MultiblockEntity {
    @Nullable
    private BlockPos masterBlockPos;
    @Nullable
    private MultiblockStructure multiblockStructure;

    public FossilMultiblockEntity(@NotNull BlockPos pos, @NotNull BlockState state, @NotNull MultiblockStructureBuilder multiblockBuilder, @NotNull BlockEntityType<?> type) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)multiblockBuilder, (String)"multiblockBuilder");
        Intrinsics.checkNotNullParameter(type, (String)"type");
        super(type, pos, state, multiblockBuilder);
    }

    public /* synthetic */ FossilMultiblockEntity(BlockPos blockPos2, BlockState blockState, MultiblockStructureBuilder multiblockStructureBuilder, BlockEntityType blockEntityType, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 8) != 0) {
            blockEntityType = CobblemonBlockEntities.FOSSIL_MULTIBLOCK;
        }
        this(blockPos2, blockState, multiblockStructureBuilder, blockEntityType);
    }

    @Override
    @Nullable
    public BlockPos getMasterBlockPos() {
        return this.masterBlockPos;
    }

    @Override
    public void setMasterBlockPos(@Nullable BlockPos blockPos2) {
        this.masterBlockPos = blockPos2;
    }

    @Override
    @Nullable
    public MultiblockStructure getMultiblockStructure() {
        if (this.getMasterBlockPos() != null && !Intrinsics.areEqual((Object)this.getMasterBlockPos(), (Object)this.f_58858_)) {
            ChunkPos chunkPos = new ChunkPos(this.getMasterBlockPos());
            Level level = this.f_58857_;
            boolean bl = level != null && (level = level.m_7726_()) != null ? level.m_5563_(chunkPos.f_45578_, chunkPos.f_45579_) : false;
            if (bl) {
                FossilMultiblockEntity entity2;
                Level level2 = this.f_58857_;
                FossilMultiblockEntity fossilMultiblockEntity = entity2 = (FossilMultiblockEntity)(level2 != null ? level2.m_7702_(this.getMasterBlockPos()) : null);
                this.multiblockStructure = fossilMultiblockEntity != null ? fossilMultiblockEntity.getMultiblockStructure() : null;
            }
        }
        return this.multiblockStructure;
    }

    @Override
    public void setMultiblockStructure(@Nullable MultiblockStructure structure) {
        this.multiblockStructure = structure;
        if (structure != null) {
            this.setMasterBlockPos(structure.getControllerBlockPos());
        }
    }

    public void m_7651_() {
        super.m_7651_();
        if (this.getMultiblockStructure() != null && this.f_58857_ != null) {
            MultiblockStructure multiblockStructure = this.getMultiblockStructure();
            Intrinsics.checkNotNull((Object)multiblockStructure);
            Level level = this.f_58857_;
            Intrinsics.checkNotNull((Object)level);
            multiblockStructure.markRemoved(level);
        }
    }

    @Override
    public void m_142466_(@NotNull CompoundTag nbt) {
        MultiblockStructure multiblockStructure;
        FossilMultiblockStructure oldMultiblockStructure;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        MultiblockStructure multiblockStructure2 = this.getMultiblockStructure();
        FossilMultiblockStructure fossilMultiblockStructure = oldMultiblockStructure = multiblockStructure2 instanceof FossilMultiblockStructure ? (FossilMultiblockStructure)multiblockStructure2 : null;
        if (nbt.m_128441_("MultiblockStore")) {
            FossilMultiblockStructure fossilMultiblockStructure2;
            FossilMultiblockStructure fossilMultiblockStructure3 = oldMultiblockStructure;
            if ((fossilMultiblockStructure3 != null ? fossilMultiblockStructure3.getFossilState() : null) != null) {
                int animAge = oldMultiblockStructure.getFossilState().peekAge();
                float partialTicks = oldMultiblockStructure.getFossilState().getPartialTicks();
                CompoundTag compoundTag = nbt.m_128469_("MultiblockStore");
                Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"nbt.getCompound(DataKeys.MULTIBLOCK_STORAGE)");
                fossilMultiblockStructure2 = FossilMultiblockStructure.Companion.fromNbt(compoundTag, animAge, partialTicks);
            } else {
                CompoundTag compoundTag = nbt.m_128469_("MultiblockStore");
                Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"nbt.getCompound(DataKeys.MULTIBLOCK_STORAGE)");
                fossilMultiblockStructure2 = FossilMultiblockStructure.Companion.fromNbt$default(FossilMultiblockStructure.Companion, compoundTag, 0, 0.0f, 6, null);
            }
            multiblockStructure = fossilMultiblockStructure2;
        } else {
            multiblockStructure = null;
        }
        this.setMultiblockStructure(multiblockStructure);
        this.setMasterBlockPos(nbt.m_128441_("ControllerBlock") ? NbtUtils.m_129239_((CompoundTag)nbt.m_128469_("ControllerBlock")) : null);
    }
}

