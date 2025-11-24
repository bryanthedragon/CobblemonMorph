/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.ContainerHelper
 *  net.minecraft.world.SimpleContainer
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterials;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.builder.MultiblockStructureBuilder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure;
import java.util.Arrays;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u0015B\u001f\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0007\u0010\u0006R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity;", "Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "", "readNbt", "(Lnet/minecraft/nbt/CompoundTag;)V", "writeNbt", "Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity$RestorationTankInventory;", "inv", "Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity$RestorationTankInventory;", "getInv", "()Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity$RestorationTankInventory;", "Lnet/minecraft/core/BlockPos;", "pos", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;", "multiblockBuilder", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lcom/cobblemon/mod/common/api/multiblock/builder/MultiblockStructureBuilder;)V", "RestorationTankInventory", "common"})
@SourceDebugExtension(value={"SMAP\nRestorationTankBlockEntity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RestorationTankBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,144:1\n37#2,2:145\n*S KotlinDebug\n*F\n+ 1 RestorationTankBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity\n*L\n45#1:145,2\n*E\n"})
public final class RestorationTankBlockEntity
extends FossilMultiblockEntity {
    @NotNull
    private final RestorationTankInventory inv;

    public RestorationTankBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state, @NotNull MultiblockStructureBuilder multiblockBuilder) {
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)multiblockBuilder, (String)"multiblockBuilder");
        super(pos, state, multiblockBuilder, CobblemonBlockEntities.RESTORATION_TANK);
        this.inv = new RestorationTankInventory(this);
    }

    @NotNull
    public final RestorationTankInventory getInv() {
        return this.inv;
    }

    @Override
    protected void m_183515_(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.m_183515_(nbt);
        nbt.m_128365_("inventory", (Tag)this.inv.m_7927_());
    }

    @Override
    public void m_142466_(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        super.m_142466_(nbt);
        if (nbt.m_128441_("Items")) {
            NonNullList items = NonNullList.m_122780_((int)this.inv.m_6643_(), (Object)ItemStack.f_41583_);
            ContainerHelper.m_18980_((CompoundTag)nbt, (NonNullList)items);
            Intrinsics.checkNotNullExpressionValue((Object)items, (String)"items");
            Collection $this$toTypedArray$iv = (Collection)items;
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            ItemStack[] itemStackArray = thisCollection$iv.toArray(new ItemStack[0]);
            RestorationTankInventory restorationTankInventory = new RestorationTankInventory(this, Arrays.copyOf(itemStackArray, itemStackArray.length));
        } else if (nbt.m_128441_("inventory")) {
            Tag tag = nbt.m_128423_("inventory");
            Intrinsics.checkNotNull((Object)tag, (String)"null cannot be cast to non-null type net.minecraft.nbt.NbtList");
            this.inv.m_7797_((ListTag)tag);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0011\b\u0016\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001f\u0010 B#\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u0012\u0012\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050!\"\u00020\u0005\u00a2\u0006\u0004\b\u001f\u0010#J+\u0010\n\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ+\u0010\f\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00032\b\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\f\u0010\u000bJ\u0019\u0010\u000f\u001a\u00020\t2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0019\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u000f\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001b\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity$RestorationTankInventory;", "Lnet/minecraft/world/SimpleContainer;", "Lnet/minecraft/world/WorldlyContainer;", "", "slot", "Lnet/minecraft/world/item/ItemStack;", "stack", "Lnet/minecraft/core/Direction;", "dir", "", "canExtract", "(ILnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/Direction;)Z", "canInsert", "Lnet/minecraft/world/entity/player/Player;", "player", "canPlayerUse", "(Lnet/minecraft/world/entity/player/Player;)Z", "side", "", "getAvailableSlots", "(Lnet/minecraft/core/Direction;)[I", "getMaxCountPerStack", "()I", "", "markDirty", "()V", "Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity;", "tankEntity", "Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity;", "getTankEntity", "()Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity;", "<init>", "(Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity;)V", "", "items", "(Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity;[Lnet/minecraft/world/item/ItemStack;)V", "common"})
    @SourceDebugExtension(value={"SMAP\nRestorationTankBlockEntity.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RestorationTankBlockEntity.kt\ncom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity$RestorationTankInventory\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,144:1\n1#2:145\n*E\n"})
    public static final class RestorationTankInventory
    extends SimpleContainer
    implements WorldlyContainer {
        @NotNull
        private final RestorationTankBlockEntity tankEntity;

        public RestorationTankInventory(@NotNull RestorationTankBlockEntity tankEntity, ItemStack ... items) {
            Intrinsics.checkNotNullParameter((Object)((Object)tankEntity), (String)"tankEntity");
            Intrinsics.checkNotNullParameter((Object)items, (String)"items");
            super(Arrays.copyOf(items, items.length));
            this.tankEntity = tankEntity;
        }

        @NotNull
        public final RestorationTankBlockEntity getTankEntity() {
            return this.tankEntity;
        }

        public RestorationTankInventory(@NotNull RestorationTankBlockEntity tankEntity) {
            Intrinsics.checkNotNullParameter((Object)((Object)tankEntity), (String)"tankEntity");
            int n = 0;
            ItemStack[] itemStackArray = new ItemStack[8];
            RestorationTankBlockEntity restorationTankBlockEntity = tankEntity;
            RestorationTankInventory restorationTankInventory = this;
            while (n < 8) {
                int n2 = n++;
                itemStackArray[n2] = ItemStack.f_41583_;
            }
            restorationTankInventory(restorationTankBlockEntity, itemStackArray);
        }

        public int m_6893_() {
            return 1;
        }

        public void m_6596_() {
            block5: {
                super.m_6596_();
                int n = this.m_6643_();
                for (int i = 0; i < n; ++i) {
                    boolean bl;
                    ItemStack itemStack;
                    Intrinsics.checkNotNullExpressionValue((Object)this.m_8020_(i), (String)"this.getStack(i)");
                    if (itemStack.m_41619_()) continue;
                    MultiblockStructure multiblockStructure = this.tankEntity.getMultiblockStructure();
                    FossilMultiblockStructure struct2 = multiblockStructure instanceof FossilMultiblockStructure ? (FossilMultiblockStructure)multiblockStructure : null;
                    ResourceLocation returnIdentifier = NaturalMaterials.INSTANCE.getReturnItem(itemStack);
                    if (this.tankEntity.f_58857_ == null) continue;
                    FossilMultiblockStructure fossilMultiblockStructure = struct2;
                    if (fossilMultiblockStructure != null) {
                        Level level = this.tankEntity.f_58857_;
                        Intrinsics.checkNotNull((Object)level);
                        bl = fossilMultiblockStructure.insertOrganicMaterial(itemStack, level);
                    } else {
                        bl = false;
                    }
                    if (!bl) continue;
                    this.m_8016_(i);
                    if (returnIdentifier == null) continue;
                    Object object = BuiltInRegistries.f_257033_.m_7745_(returnIdentifier);
                    Intrinsics.checkNotNullExpressionValue((Object)object, (String)"ITEM.get(returnIdentifier)");
                    Item returnItem = (Item)object;
                    ItemStack returnStack = new ItemStack((ItemLike)returnItem, itemStack.m_41613_());
                    boolean done = false;
                    int n2 = this.m_6643_();
                    for (int j = 1; j < n2; ++j) {
                        ItemStack existingStack;
                        Intrinsics.checkNotNullExpressionValue((Object)this.m_8020_(j), (String)"getStack(j)");
                        if (!existingStack.m_41619_() && (!Intrinsics.areEqual((Object)BuiltInRegistries.f_257033_.m_7981_((Object)existingStack.m_41720_()), (Object)returnIdentifier) || existingStack.m_41613_() + returnStack.m_41613_() >= existingStack.m_41741_())) continue;
                        this.m_6836_(j, new ItemStack((ItemLike)returnItem, existingStack.m_41613_() + returnStack.m_41613_()));
                        done = true;
                    }
                    if (done) continue;
                    this.m_6836_(i, new ItemStack((ItemLike)returnItem, itemStack.m_41613_()));
                }
                Level level = this.tankEntity.f_58857_;
                if (level == null) break block5;
                Level it = level;
                boolean bl = false;
                this.tankEntity.m_6596_();
                MultiblockStructure multiblockStructure = this.tankEntity.getMultiblockStructure();
                if (multiblockStructure != null) {
                    multiblockStructure.markDirty(it);
                }
            }
        }

        public boolean m_6542_(@Nullable Player player) {
            return false;
        }

        @NotNull
        public int[] m_7071_(@Nullable Direction side) {
            int[] nArray;
            if (side == Direction.DOWN) {
                int[] nArray2 = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
                nArray = nArray2;
            } else {
                int[] nArray3 = new int[]{0};
                nArray = nArray3;
            }
            return nArray;
        }

        public boolean m_7155_(int slot, @Nullable ItemStack stack, @Nullable Direction dir) {
            if (this.tankEntity.getMultiblockStructure() instanceof FossilMultiblockStructure && dir != Direction.DOWN) {
                boolean bl;
                MultiblockStructure multiblockStructure = this.tankEntity.getMultiblockStructure();
                Intrinsics.checkNotNull((Object)multiblockStructure, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockStructure");
                FossilMultiblockStructure structure = (FossilMultiblockStructure)multiblockStructure;
                ItemStack itemStack = stack;
                if (itemStack != null) {
                    ItemStack it = itemStack;
                    boolean bl2 = false;
                    bl = NaturalMaterials.INSTANCE.isNaturalMaterial(it);
                } else {
                    bl = false;
                }
                boolean canUtilize = bl && structure.getOrganicMaterialInside() < 128 && !structure.getHasCreatedPokemon();
                ItemStack itemStack2 = stack;
                Intrinsics.checkNotNull((Object)itemStack2);
                ResourceLocation resourceLocation = NaturalMaterials.INSTANCE.getReturnItem(itemStack2);
                if (resourceLocation == null) {
                    return canUtilize;
                }
                ResourceLocation returnItem = resourceLocation;
                ItemStack returnStack = new ItemStack((ItemLike)BuiltInRegistries.f_257033_.m_7745_(returnItem), stack.m_41613_());
                if (canUtilize && super.m_19183_(returnStack)) {
                    if (Intrinsics.areEqual((Object)returnStack, (Object)ItemStack.f_41583_) && Intrinsics.areEqual((Object)this.m_8020_(0), (Object)ItemStack.f_41583_)) {
                        return true;
                    }
                    int emptyCount = 0;
                    int n = this.m_6643_();
                    for (int i = 1; i < n; ++i) {
                        ItemStack existingStack;
                        Intrinsics.checkNotNullExpressionValue((Object)this.m_8020_(i), (String)"getStack(i)");
                        if (!Intrinsics.areEqual((Object)existingStack, (Object)ItemStack.f_41583_)) continue;
                        if (++emptyCount <= 1) continue;
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean m_7157_(int slot, @Nullable ItemStack stack, @Nullable Direction dir) {
            return dir == Direction.DOWN && slot >= 0 && slot < this.m_6643_();
        }
    }
}

