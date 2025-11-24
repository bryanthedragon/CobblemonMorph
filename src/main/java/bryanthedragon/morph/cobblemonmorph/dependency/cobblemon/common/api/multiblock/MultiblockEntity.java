/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.builder.MultiblockStructureBuilder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B-\u0012\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030!\u0012\u0006\u0010#\u001a\u00020\u000e\u0012\u0006\u0010%\u001a\u00020$\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0014\u00a2\u0006\u0004\b&\u0010'J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\u0007\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\r\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\r\u0010\u0006R\u001e\u0010\u0013\u001a\u0004\u0018\u00010\u000e8&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R$\u0010\u0015\u001a\u0004\u0018\u00010\u00148\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001e\u0010 \u001a\u0004\u0018\u00010\u001b8&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/api/multiblock/MultiblockEntity;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "", "readNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "toInitialChunkDataNbt", "()Lnet/minecraft/nbt/CompoundTag;", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "toUpdatePacket", "()Lnet/minecraft/network/protocol/Packet;", "writeNbt", "Lnet/minecraft/core/BlockPos;", "getMasterBlockPos", "()Lnet/minecraft/core/BlockPos;", "setMasterBlockPos", "(Lnet/minecraft/core/BlockPos;)V", "masterBlockPos", "Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;", "multiblockBuilder", "Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;", "getMultiblockBuilder", "()Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;", "setMultiblockBuilder", "(Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;)V", "Lcom/cobblemon/mod/common/api/multiblock/MultiblockStructure;", "getMultiblockStructure", "()Lcom/cobblemon/mod/common/api/multiblock/MultiblockStructure;", "setMultiblockStructure", "(Lcom/cobblemon/mod/common/api/multiblock/MultiblockStructure;)V", "multiblockStructure", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "type", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "<init>", "(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;)V", "common"})
public abstract class MultiblockEntity
extends BlockEntity {
    @Nullable
    private MultiblockStructureBuilder multiblockBuilder;

    public MultiblockEntity(@NotNull BlockEntityType<?> type, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable MultiblockStructureBuilder multiblockBuilder) {
        Intrinsics.checkNotNullParameter(type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        super(type, pos, state);
        this.multiblockBuilder = multiblockBuilder;
    }

    @Nullable
    public final MultiblockStructureBuilder getMultiblockBuilder() {
        return this.multiblockBuilder;
    }

    public final void setMultiblockBuilder(@Nullable MultiblockStructureBuilder multiblockStructureBuilder) {
        this.multiblockBuilder = multiblockStructureBuilder;
    }

    @Nullable
    public abstract MultiblockStructure getMultiblockStructure();

    public abstract void setMultiblockStructure(@Nullable MultiblockStructure var1);

    @Nullable
    public abstract BlockPos getMasterBlockPos();

    public abstract void setMasterBlockPos(@Nullable BlockPos var1);

    @Nullable
    public Packet<ClientGamePacketListener> m_58483_() {
        return (Packet)ClientboundBlockEntityDataPacket.m_195640_((BlockEntity)this);
    }

    @NotNull
    public CompoundTag m_5995_() {
        CompoundTag result = new CompoundTag();
        this.m_183515_(result);
        return result;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void m_183515_(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.m_183515_(nbt);
        nbt.m_128379_("Formed", this.getMasterBlockPos() != null);
        if (this.getMultiblockStructure() != null) {
            MultiblockStructure multiblockStructure = this.getMultiblockStructure();
            Intrinsics.checkNotNull((Object)multiblockStructure);
            if (Intrinsics.areEqual((Object)multiblockStructure.getControllerBlockPos(), (Object)this.f_58858_)) {
                MultiblockStructure multiblockStructure2 = this.getMultiblockStructure();
                Intrinsics.checkNotNull((Object)multiblockStructure2);
                nbt.m_128365_("MultiblockStore", (Tag)multiblockStructure2.writeToNbt());
                return;
            }
        }
        if (this.getMasterBlockPos() == null) return;
        nbt.m_128365_("ControllerBlock", (Tag)NbtUtils.m_129224_((BlockPos)this.getMasterBlockPos()));
    }

    public abstract void m_142466_(@NotNull CompoundTag var1);
}

