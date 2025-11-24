/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ClientGamePacketListener
 *  net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.ContainerHelper
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010E\u001a\u00020D\u0012\u0006\u0010F\u001a\u00020!\u00a2\u0006\u0004\bG\u0010HJ+\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ+\u0010\f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\f\u0010\u000bJ\u0019\u0010\u000f\u001a\u00020\t2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0019\u0010\u0016\u001a\u00020\u00152\b\u0010\u0014\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0018\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\r\u0010\u001a\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001cJ\u000f\u0010\u001d\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eJ'\u0010$\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020!H\u0002\u00a2\u0006\u0004\b$\u0010%J\u0017\u0010(\u001a\u00020\u00112\u0006\u0010'\u001a\u00020&H\u0016\u00a2\u0006\u0004\b(\u0010)J\u0017\u0010*\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b*\u0010\u001cJ\u001f\u0010*\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010+\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b*\u0010,J\u0017\u0010.\u001a\u00020\u00112\u0006\u0010-\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b.\u0010/J\u001f\u00100\u001a\u00020\u00112\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b0\u00101J\u000f\u00102\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b2\u0010\u0019J\u000f\u00103\u001a\u00020&H\u0016\u00a2\u0006\u0004\b3\u00104J\u0017\u00107\u001a\n\u0012\u0004\u0012\u000206\u0018\u000105H\u0016\u00a2\u0006\u0004\b7\u00108J\u001d\u0010<\u001a\u00020;2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010:\u001a\u000209\u00a2\u0006\u0004\b<\u0010=J\u0017\u0010>\u001a\u00020\u00112\u0006\u0010'\u001a\u00020&H\u0014\u00a2\u0006\u0004\b>\u0010)R\u001d\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00050?8\u0006\u00a2\u0006\f\n\u0004\b@\u0010A\u001a\u0004\bB\u0010C\u00a8\u0006I"}, d2={"Lcom/cobblemon/mod/common/block/entity/DisplayCaseBlockEntity;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "Lnet/minecraft/world/WorldlyContainer;", "", "slot", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/core/Direction;", "dir", "", "canExtract", "(ILnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/Direction;)Z", "canInsert", "Lnet/minecraft/world/entity/player/Player;", "player", "canPlayerUse", "(Lnet/minecraft/world/entity/player/Player;)Z", "", "clear", "()V", "side", "", "getAvailableSlots", "(Lnet/minecraft/core/Direction;)[I", "getMaxCountPerStack", "()I", "getStack", "()Lnet/minecraft/world/item/ItemStack;", "(I)Lnet/minecraft/world/item/ItemStack;", "isEmpty", "()Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/level/block/state/BlockState;", "oldState", "newState", "onItemUpdated", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;)V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "readNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "removeStack", "amount", "(II)Lnet/minecraft/world/item/ItemStack;", "newStack", "setCaseStack", "(Lnet/minecraft/world/item/ItemStack;)V", "setStack", "(ILnet/minecraft/world/item/ItemStack;)V", "size", "toInitialChunkDataNbt", "()Lnet/minecraft/nbt/CompoundTag;", "Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/network/protocol/game/ClientGamePacketListener;", "toUpdatePacket", "()Lnet/minecraft/network/protocol/Packet;", "Lnet/minecraft/world/InteractionHand;", "hand", "Lnet/minecraft/world/InteractionResult;", "updateItem", "(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;", "writeNbt", "Lnet/minecraft/core/NonNullList;", "inv", "Lnet/minecraft/core/NonNullList;", "getInv", "()Lnet/minecraft/core/NonNullList;", "Lnet/minecraft/core/BlockPos;", "pos", "state", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V", "common"})
public final class DisplayCaseBlockEntity
extends BlockEntity
implements WorldlyContainer {
    @NotNull
    private final NonNullList<ItemStack> inv;

    public DisplayCaseBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        super(CobblemonBlockEntities.DISPLAY_CASE, pos, state);
        NonNullList nonNullList = NonNullList.m_122780_((int)1, (Object)ItemStack.f_41583_);
        Intrinsics.checkNotNullExpressionValue((Object)nonNullList, (String)"ofSize(1, ItemStack.EMPTY)");
        this.inv = nonNullList;
    }

    @NotNull
    public final NonNullList<ItemStack> getInv() {
        return this.inv;
    }

    @NotNull
    public final InteractionResult updateItem(@NotNull Player player, @NotNull InteractionHand hand) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
        ItemStack playerStack = player.m_21120_(hand);
        if (Intrinsics.areEqual((Object)playerStack.m_41720_(), (Object)this.getStack().m_41720_())) {
            return !Intrinsics.areEqual((Object)playerStack.m_41720_(), (Object)Items.f_41852_) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
        if (playerStack.m_41619_() && !this.getStack().m_41619_()) {
            if (!player.m_7500_()) {
                player.m_21008_(hand, this.getStack());
            }
            ItemStack itemStack = ItemStack.f_41583_;
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"EMPTY");
            this.setCaseStack(itemStack);
            InteractionResult interactionResult = InteractionResult.m_19078_((boolean)true);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(true)");
            return interactionResult;
        }
        if (this.getStack().m_41619_() && !playerStack.m_41619_()) {
            ItemStack itemStack = playerStack.m_41777_();
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"playerStack.copy()");
            this.setCaseStack(itemStack);
            if (!player.m_7500_()) {
                playerStack.m_41774_(1);
            }
            InteractionResult interactionResult = InteractionResult.m_19078_((boolean)true);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(true)");
            return interactionResult;
        }
        if (!this.getStack().m_41619_() && !playerStack.m_41619_()) {
            ItemStack oldCaseStack = this.getStack();
            ItemStack itemStack = playerStack.m_41777_();
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"playerStack.copy()");
            this.setCaseStack(itemStack);
            if (!player.m_7500_()) {
                playerStack.m_41774_(1);
                player.m_36356_(oldCaseStack);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @NotNull
    public final ItemStack getStack() {
        Object object = this.inv.get(0);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"inv[0]");
        return (ItemStack)object;
    }

    private final void setCaseStack(ItemStack newStack) {
        if (this.f_58857_ == null) {
            return;
        }
        Level level = this.f_58857_;
        Intrinsics.checkNotNull((Object)level);
        BlockState oldState = level.m_8055_(this.f_58858_);
        newStack.m_41764_(1);
        this.inv.set(0, (Object)newStack);
        if (newStack.m_41619_()) {
            Level level2 = this.f_58857_;
            Intrinsics.checkNotNull((Object)level2);
            level2.m_247517_(null, this.f_58858_, CobblemonSounds.DISPLAY_CASE_REMOVE_ITEM, SoundSource.BLOCKS);
        } else {
            Level level3 = this.f_58857_;
            Intrinsics.checkNotNull((Object)level3);
            level3.m_247517_(null, this.f_58858_, CobblemonSounds.DISPLAY_CASE_ADD_ITEM, SoundSource.BLOCKS);
        }
        Level level4 = this.f_58857_;
        Intrinsics.checkNotNull((Object)level4);
        Intrinsics.checkNotNullExpressionValue((Object)oldState, (String)"oldState");
        Level level5 = this.f_58857_;
        Intrinsics.checkNotNull((Object)level5);
        BlockState blockState = level5.m_8055_(this.f_58858_);
        Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"world!!.getBlockState(pos)");
        this.onItemUpdated(level4, oldState, blockState);
    }

    protected void m_183515_(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.m_183515_(nbt);
        ContainerHelper.m_18976_((CompoundTag)nbt, this.inv, (boolean)true);
    }

    public void m_142466_(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.m_142466_(nbt);
        this.inv.clear();
        ContainerHelper.m_18980_((CompoundTag)nbt, this.inv);
    }

    @Nullable
    public Packet<ClientGamePacketListener> m_58483_() {
        return (Packet)ClientboundBlockEntityDataPacket.m_195640_((BlockEntity)this);
    }

    @NotNull
    public CompoundTag m_5995_() {
        CompoundTag compoundTag = this.m_187482_();
        Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"this.createNbt()");
        return compoundTag;
    }

    private final void onItemUpdated(Level world, BlockState oldState, BlockState newState) {
        world.m_7260_(this.f_58858_, oldState, newState, 2);
        world.m_46717_(this.f_58858_, world.m_8055_(this.f_58858_).m_60734_());
        this.m_6596_();
    }

    public void m_6211_() {
        this.inv.clear();
    }

    public int m_6643_() {
        return this.inv.size();
    }

    public boolean m_7983_() {
        return this.getStack().m_41619_();
    }

    @NotNull
    public ItemStack m_8020_(int slot) {
        return this.getStack();
    }

    @NotNull
    public ItemStack m_7407_(int slot, int amount) {
        BlockState oldState = this.m_58900_();
        ItemStack result = ContainerHelper.m_18969_((List)((List)this.inv), (int)slot, (int)amount);
        if (this.f_58857_ != null) {
            Level level = this.f_58857_;
            Intrinsics.checkNotNull((Object)level);
            Intrinsics.checkNotNullExpressionValue((Object)oldState, (String)"oldState");
            Level level2 = this.f_58857_;
            Intrinsics.checkNotNull((Object)level2);
            BlockState blockState = level2.m_8055_(this.f_58858_);
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"world!!.getBlockState(pos)");
            this.onItemUpdated(level, oldState, blockState);
        }
        Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
        return result;
    }

    @NotNull
    public ItemStack m_8016_(int slot) {
        BlockState oldState = this.m_58900_();
        ItemStack result = ContainerHelper.m_18966_((List)((List)this.inv), (int)slot);
        if (this.f_58857_ != null) {
            Level level = this.f_58857_;
            Intrinsics.checkNotNull((Object)level);
            Intrinsics.checkNotNullExpressionValue((Object)oldState, (String)"oldState");
            Level level2 = this.f_58857_;
            Intrinsics.checkNotNull((Object)level2);
            BlockState blockState = level2.m_8055_(this.f_58858_);
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"world!!.getBlockState(pos)");
            this.onItemUpdated(level, oldState, blockState);
        }
        Intrinsics.checkNotNullExpressionValue((Object)result, (String)"result");
        return result;
    }

    public void m_6836_(int slot, @NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        BlockState oldState = this.m_58900_();
        this.inv.set(slot, (Object)stack);
        if (stack.m_41613_() > stack.m_41741_()) {
            stack.m_41764_(stack.m_41741_());
        }
        if (this.f_58857_ != null) {
            Level level = this.f_58857_;
            Intrinsics.checkNotNull((Object)level);
            Intrinsics.checkNotNullExpressionValue((Object)oldState, (String)"oldState");
            Level level2 = this.f_58857_;
            Intrinsics.checkNotNull((Object)level2);
            BlockState blockState = level2.m_8055_(this.f_58858_);
            Intrinsics.checkNotNullExpressionValue((Object)blockState, (String)"world!!.getBlockState(pos)");
            this.onItemUpdated(level, oldState, blockState);
        }
    }

    public boolean m_6542_(@Nullable Player player) {
        return false;
    }

    @NotNull
    public int[] m_7071_(@Nullable Direction side) {
        int[] result = new int[this.inv.size()];
        int n = result.length;
        for (int i = 0; i < n; ++i) {
            result[i] = i;
        }
        return result;
    }

    public int m_6893_() {
        return 1;
    }

    public boolean m_7155_(int slot, @Nullable ItemStack stack, @Nullable Direction dir) {
        if (dir == Direction.DOWN) {
            return false;
        }
        return this.getStack().m_41619_();
    }

    public boolean m_7157_(int slot, @Nullable ItemStack stack, @Nullable Direction dir) {
        return dir == Direction.DOWN;
    }
}

