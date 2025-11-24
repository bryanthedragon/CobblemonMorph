/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.Container
 *  net.minecraft.world.ContainerHelper
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ChestMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.ContainerOpenersCounter
 *  net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockStateProperties
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest.GildedChestBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest.GildedState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 R2\u00020\u00012\u00020\u0002:\u0001RB!\u0012\u0006\u00100\u001a\u00020/\u0012\u0006\u00102\u001a\u000201\u0012\b\b\u0002\u0010)\u001a\u00020M\u00a2\u0006\u0004\bP\u0010QJ+\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ+\u0010\f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\f\u0010\u000bJ)\u0010\u0012\u001a\n \u0011*\u0004\u0018\u00010\u00100\u00102\u0006\u0010\r\u001a\u00020\u00032\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0014\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0019\u001a\n \u0011*\u0004\u0018\u00010\u00180\u0018H\u0014\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0015\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00050\u001bH\u0014\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0013\u0010\u001f\u001a\u0006\u0012\u0002\b\u00030\u001eH\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u0017\u0010$\u001a\u00020#2\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b$\u0010%J\u0017\u0010&\u001a\u00020#2\u0006\u0010\"\u001a\u00020!H\u0016\u00a2\u0006\u0004\b&\u0010%J\r\u0010'\u001a\u00020#\u00a2\u0006\u0004\b'\u0010(J\u001f\u0010+\u001a\u00020\t2\u0006\u0010)\u001a\u00020\u00032\u0006\u0010*\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b+\u0010,J5\u00105\u001a\u00020#2\u0006\u0010.\u001a\u00020-2\u0006\u00100\u001a\u00020/2\u0006\u00102\u001a\u0002012\u0006\u00103\u001a\u00020\u00032\u0006\u00104\u001a\u00020\u0003\u00a2\u0006\u0004\b5\u00106J\u0019\u00109\u001a\u00020#2\b\u00108\u001a\u0004\u0018\u000107H\u0016\u00a2\u0006\u0004\b9\u0010:J\u001d\u0010<\u001a\u00020#2\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00050\u001bH\u0014\u00a2\u0006\u0004\b<\u0010=J\u000f\u0010>\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b>\u0010?J\u0019\u0010@\u001a\u00020#2\b\u00108\u001a\u0004\u0018\u000107H\u0014\u00a2\u0006\u0004\b@\u0010:R(\u0010A\u001a\b\u0012\u0004\u0012\u00020\u00050\u001b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bA\u0010B\u001a\u0004\bC\u0010\u001d\"\u0004\bD\u0010=R\u0017\u0010F\u001a\u00020E8\u0006\u00a2\u0006\f\n\u0004\bF\u0010G\u001a\u0004\bH\u0010IR\u0014\u0010K\u001a\u00020J8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bK\u0010LR\u0017\u0010)\u001a\u00020M8\u0006\u00a2\u0006\f\n\u0004\b)\u0010N\u001a\u0004\b\u001f\u0010O\u00a8\u0006S"}, d2={"Lcom/cobblemon/mod/common/block/entity/GildedChestBlockEntity;", "Lnet/minecraft/world/level/block/entity/RandomizableContainerBlockEntity;", "Lnet/minecraft/world/WorldlyContainer;", "", "slot", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/core/Direction;", "dir", "", "canExtract", "(ILnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/Direction;)Z", "canInsert", "syncId", "Lnet/minecraft/world/entity/player/Inventory;", "playerInventory", "Lnet/minecraft/world/inventory/ChestMenu;", "kotlin.jvm.PlatformType", "createScreenHandler", "(ILnet/minecraft/world/entity/player/Inventory;)Lnet/minecraft/world/inventory/ChestMenu;", "side", "", "getAvailableSlots", "(Lnet/minecraft/core/Direction;)[I", "Lnet/minecraft/network/chat/MutableComponent;", "getContainerName", "()Lnet/minecraft/network/chat/MutableComponent;", "Lnet/minecraft/core/NonNullList;", "getInvStackList", "()Lnet/minecraft/core/NonNullList;", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "getType", "()Lnet/minecraft/world/level/block/entity/BlockEntityType;", "Lnet/minecraft/world/entity/player/Player;", "player", "", "onClose", "(Lnet/minecraft/world/entity/player/Player;)V", "onOpen", "onScheduledTick", "()V", "type", "data", "onSyncedBlockEvent", "(II)Z", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "oldViewerCount", "newViewerCount", "onViewerCountUpdate", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "readNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "list", "setInvStackList", "(Lnet/minecraft/core/NonNullList;)V", "size", "()I", "writeNbt", "inventoryContents", "Lnet/minecraft/core/NonNullList;", "getInventoryContents", "setInventoryContents", "Lcom/cobblemon/mod/common/block/chest/GildedState;", "poseableState", "Lcom/cobblemon/mod/common/block/chest/GildedState;", "getPoseableState", "()Lcom/cobblemon/mod/common/block/chest/GildedState;", "Lnet/minecraft/world/level/block/entity/ContainerOpenersCounter;", "stateManager", "Lnet/minecraft/world/level/block/entity/ContainerOpenersCounter;", "Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Type;", "Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Type;", "()Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Type;", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/block/chest/GildedChestBlock$Type;)V", "Companion", "common"})
public final class GildedChestBlockEntity
extends RandomizableContainerBlockEntity
implements WorldlyContainer {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final GildedChestBlock.Type type;
    @NotNull
    private NonNullList<ItemStack> inventoryContents;
    @NotNull
    private final GildedState poseableState;
    @NotNull
    private final ContainerOpenersCounter stateManager;
    private static final int NUM_SLOTS = 27;

    public GildedChestBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state, @NotNull GildedChestBlock.Type type) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        super(CobblemonBlockEntities.GILDED_CHEST, pos, state);
        this.type = type;
        NonNullList nonNullList = NonNullList.m_122780_((int)NUM_SLOTS, (Object)ItemStack.f_41583_);
        Intrinsics.checkNotNullExpressionValue((Object)nonNullList, (String)"ofSize(NUM_SLOTS, ItemStack.EMPTY)");
        this.inventoryContents = nonNullList;
        this.poseableState = new GildedState();
        this.stateManager = new ContainerOpenersCounter(this){
            final /* synthetic */ GildedChestBlockEntity this$0;
            {
                this.this$0 = $receiver;
            }

            protected void m_142292_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
                Intrinsics.checkNotNullParameter((Object)world, (String)"world");
                Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
                Intrinsics.checkNotNullParameter((Object)state, (String)"state");
                GildedChestBlockEntity.Companion.playSound(world, pos, state, CobblemonSounds.GILDED_CHEST_OPEN);
            }

            protected void m_142289_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state) {
                Intrinsics.checkNotNullParameter((Object)world, (String)"world");
                Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
                Intrinsics.checkNotNullParameter((Object)state, (String)"state");
                GildedChestBlockEntity.Companion.playSound(world, pos, state, CobblemonSounds.GILDED_CHEST_CLOSE);
            }

            protected void m_142148_(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, int oldViewerCount, int newViewerCount) {
                Intrinsics.checkNotNullParameter((Object)world, (String)"world");
                Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
                Intrinsics.checkNotNullParameter((Object)state, (String)"state");
                this.this$0.onViewerCountUpdate(world, pos, state, oldViewerCount, newViewerCount);
            }

            protected boolean m_142718_(@NotNull Player player) {
                Intrinsics.checkNotNullParameter((Object)player, (String)"player");
                if (player.f_36096_ instanceof ChestMenu) {
                    AbstractContainerMenu abstractContainerMenu = player.f_36096_;
                    Intrinsics.checkNotNull((Object)abstractContainerMenu, (String)"null cannot be cast to non-null type net.minecraft.screen.GenericContainerScreenHandler");
                    Container inventory = ((ChestMenu)abstractContainerMenu).m_39261_();
                    return inventory == this.this$0;
                }
                return false;
            }
        };
    }

    public /* synthetic */ GildedChestBlockEntity(BlockPos blockPos2, BlockState blockState, GildedChestBlock.Type type, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            type = GildedChestBlock.Type.RED;
        }
        this(blockPos2, blockState, type);
    }

    @NotNull
    public final GildedChestBlock.Type getType() {
        return this.type;
    }

    @NotNull
    public final NonNullList<ItemStack> getInventoryContents() {
        return this.inventoryContents;
    }

    public final void setInventoryContents(@NotNull NonNullList<ItemStack> nonNullList) {
        Intrinsics.checkNotNullParameter(nonNullList, (String)"<set-?>");
        this.inventoryContents = nonNullList;
    }

    @NotNull
    public final GildedState getPoseableState() {
        return this.poseableState;
    }

    @NotNull
    public BlockEntityType<?> m_58903_() {
        return CobblemonBlockEntities.GILDED_CHEST;
    }

    public int m_6643_() {
        return NUM_SLOTS;
    }

    protected MutableComponent getContainerName() {
        return Component.m_237115_((String)"block.cobblemon.gilded_chest");
    }

    protected ChestMenu createScreenHandler(int syncId, @Nullable Inventory playerInventory) {
        return ChestMenu.m_39237_((int)syncId, (Inventory)playerInventory, (Container)((Container)this));
    }

    public void m_5856_(@NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!this.f_58859_ && !player.m_5833_() && this.type != GildedChestBlock.Type.FAKE) {
            this.stateManager.m_155452_(player, this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    public void m_5785_(@NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (!this.f_58859_ && !player.m_5833_()) {
            this.stateManager.m_155468_(player, this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    @NotNull
    public int[] m_7071_(@NotNull Direction side) {
        int[] nArray;
        Intrinsics.checkNotNullParameter((Object)side, (String)"side");
        if (this.type == GildedChestBlock.Type.FAKE) {
            nArray = new int[]{};
        } else {
            int n = 0;
            int n2 = NUM_SLOTS;
            int[] nArray2 = new int[n2];
            while (n < n2) {
                int n3;
                nArray2[n3] = n3 = n++;
            }
            nArray = nArray2;
        }
        return nArray;
    }

    public boolean m_7155_(int slot, @Nullable ItemStack stack, @Nullable Direction dir) {
        if (this.type == GildedChestBlock.Type.FAKE) {
            return false;
        }
        return dir != Direction.DOWN;
    }

    public boolean m_7157_(int slot, @Nullable ItemStack stack, @Nullable Direction dir) {
        if (this.type == GildedChestBlock.Type.FAKE) {
            return false;
        }
        return dir == Direction.DOWN;
    }

    @NotNull
    protected NonNullList<ItemStack> m_7086_() {
        return this.inventoryContents;
    }

    protected void m_6520_(@NotNull NonNullList<ItemStack> list) {
        Intrinsics.checkNotNullParameter(list, (String)"list");
        this.inventoryContents = list;
    }

    public boolean m_7531_(int type, int data) {
        if (type == 1) {
            boolean isNowOpen = data > 0;
            boolean wasOpen = Intrinsics.areEqual((Object)this.poseableState.getCurrentPose(), (Object)"OPEN");
            PoseableEntityModel poseableEntityModel = this.poseableState.getCurrentModel();
            if (poseableEntityModel == null) {
                return true;
            }
            PoseableEntityModel model = poseableEntityModel;
            if (isNowOpen && !wasOpen) {
                PoseableEntityState poseableEntityState = this.poseableState;
                Pose pose = model.getPose("OPEN");
                Intrinsics.checkNotNull(pose);
                model.moveToPose(null, poseableEntityState, pose);
            } else if (!isNowOpen && wasOpen) {
                PoseableEntityState poseableEntityState = this.poseableState;
                Pose pose = model.getPose("CLOSED");
                Intrinsics.checkNotNull(pose);
                model.moveToPose(null, poseableEntityState, pose);
            }
            return true;
        }
        return super.m_7531_(type, data);
    }

    public final void onViewerCountUpdate(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, int oldViewerCount, int newViewerCount) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Block block = state.m_60734_();
        world.m_7696_(pos, block, 1, newViewerCount);
    }

    protected void m_183515_(@Nullable CompoundTag nbt) {
        super.m_183515_(nbt);
        if (!this.m_59634_(nbt)) {
            ContainerHelper.m_18973_((CompoundTag)nbt, this.inventoryContents);
        }
    }

    public void m_142466_(@Nullable CompoundTag nbt) {
        super.m_142466_(nbt);
        NonNullList nonNullList = NonNullList.m_122780_((int)this.m_6643_(), (Object)ItemStack.f_41583_);
        Intrinsics.checkNotNullExpressionValue((Object)nonNullList, (String)"ofSize(\n            size\u2026ItemStack.EMPTY\n        )");
        this.inventoryContents = nonNullList;
        if (!this.m_59631_(nbt)) {
            ContainerHelper.m_18980_((CompoundTag)nbt, this.inventoryContents);
        }
    }

    public final void onScheduledTick() {
        if (!this.f_58859_) {
            this.stateManager.m_155476_(this.m_58904_(), this.m_58899_(), this.m_58900_());
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J-\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0006X\u0086D\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/block/entity/GildedChestBlockEntity$Companion;", "", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/sounds/SoundEvent;", "sound", "", "playSound", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/sounds/SoundEvent;)V", "", "NUM_SLOTS", "I", "getNUM_SLOTS", "()I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final int getNUM_SLOTS() {
            return NUM_SLOTS;
        }

        public final void playSound(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull SoundEvent sound2) {
            Intrinsics.checkNotNullParameter((Object)world, (String)"world");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter((Object)state, (String)"state");
            Intrinsics.checkNotNullParameter((Object)sound2, (String)"sound");
            double d = (double)pos.m_123341_() + 0.5;
            double e = (double)pos.m_123342_() + 0.5;
            double f = (double)pos.m_123343_() + 0.5;
            Direction direction = (Direction)state.m_61143_((Property)BlockStateProperties.f_61374_);
            world.m_6263_(null, d += (double)direction.m_122429_() * 0.5, e, f += (double)direction.m_122431_() * 0.5, sound2, SoundSource.BLOCKS, 0.5f, world.f_46441_.m_188501_() * 0.1f + 0.9f);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

