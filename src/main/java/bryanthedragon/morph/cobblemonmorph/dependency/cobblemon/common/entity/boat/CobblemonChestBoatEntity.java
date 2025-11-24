/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.NonNullList
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.MenuProvider
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.HasCustomInventoryScreen
 *  net.minecraft.world.entity.monster.piglin.PiglinAi
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.entity.vehicle.ContainerEntity
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ChestMenu
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.gameevent.GameEvent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatEntity;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 F2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001FB\u0011\b\u0016\u0012\u0006\u0010;\u001a\u00020:\u00a2\u0006\u0004\b<\u0010=B)\b\u0016\u0012\u0006\u0010;\u001a\u00020:\u0012\u0006\u0010?\u001a\u00020>\u0012\u0006\u0010@\u001a\u00020>\u0012\u0006\u0010A\u001a\u00020>\u00a2\u0006\u0004\b<\u0010BB\u001d\u0012\f\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00000C\u0012\u0006\u0010;\u001a\u00020:\u00a2\u0006\u0004\b<\u0010EJ\u000f\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ)\u0010\u0014\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0015\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u0019J\u0011\u0010\u001c\u001a\u0004\u0018\u00010\u001bH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u000f\u0010\u001f\u001a\u00020\u001eH\u0016\u00a2\u0006\u0004\b\u001f\u0010 J\u0017\u0010\"\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\"\u0010#J\u000f\u0010$\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b$\u0010\u000eJ\u0017\u0010%\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b%\u0010&J\u0017\u0010'\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b'\u0010#J\u001f\u0010'\u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u000f2\u0006\u0010(\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b'\u0010)J\u000f\u0010*\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b*\u0010\u000eJ\u0019\u0010,\u001a\u00020\f2\b\u0010+\u001a\u0004\u0018\u00010\u001bH\u0016\u00a2\u0006\u0004\b,\u0010-J\u0017\u0010/\u001a\u00020\f2\u0006\u0010.\u001a\u00020\u001eH\u0016\u00a2\u0006\u0004\b/\u00100J\u001f\u00102\u001a\u00020\f2\u0006\u0010!\u001a\u00020\u000f2\u0006\u00101\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b2\u00103J\u000f\u00104\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b4\u00105R\u001c\u00106\u001a\b\u0012\u0004\u0012\u00020\u00170\u00168\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b6\u00107R\u0018\u0010+\u001a\u0004\u0018\u00010\u001b8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b+\u00108R\u0016\u0010.\u001a\u00020\u001e8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b.\u00109\u00a8\u0006G"}, d2={"Lcom/cobblemon/mod/common/entity/boat/CobblemonChestBoatEntity;", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatEntity;", "Lnet/minecraft/world/entity/HasCustomInventoryScreen;", "Lnet/minecraft/world/entity/vehicle/ContainerEntity;", "Lnet/minecraft/world/item/Item;", "asItem", "()Lnet/minecraft/world/item/Item;", "Lnet/minecraft/world/entity/player/Player;", "player", "", "canPlayerUse", "(Lnet/minecraft/world/entity/player/Player;)Z", "", "clear", "()V", "", "syncId", "Lnet/minecraft/world/entity/player/Inventory;", "playerInventory", "Lnet/minecraft/world/inventory/AbstractContainerMenu;", "createMenu", "(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/inventory/AbstractContainerMenu;", "Lnet/minecraft/core/NonNullList;", "Lnet/minecraft/world/item/ItemStack;", "emptyInventory", "()Lnet/minecraft/core/NonNullList;", "getInventory", "Lnet/minecraft/resources/ResourceLocation;", "getLootTableId", "()Lnet/minecraft/resources/ResourceLocation;", "", "getLootTableSeed", "()J", "slot", "getStack", "(I)Lnet/minecraft/world/item/ItemStack;", "markDirty", "openInventory", "(Lnet/minecraft/world/entity/player/Player;)V", "removeStack", "amount", "(II)Lnet/minecraft/world/item/ItemStack;", "resetInventory", "lootTableId", "setLootTableId", "(Lnet/minecraft/resources/ResourceLocation;)V", "lootTableSeed", "setLootTableSeed", "(J)V", "stack", "setStack", "(ILnet/minecraft/world/item/ItemStack;)V", "size", "()I", "inventory", "Lnet/minecraft/core/NonNullList;", "Lnet/minecraft/resources/ResourceLocation;", "J", "Lnet/minecraft/world/level/Level;", "world", "<init>", "(Lnet/minecraft/world/level/Level;)V", "", "x", "y", "z", "(Lnet/minecraft/world/level/Level;DDD)V", "Lnet/minecraft/world/entity/EntityType;", "entityType", "(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;)V", "Companion", "common"})
public final class CobblemonChestBoatEntity
extends CobblemonBoatEntity
implements HasCustomInventoryScreen,
ContainerEntity {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private NonNullList<ItemStack> inventory;
    @Nullable
    private ResourceLocation lootTableId;
    private long lootTableSeed;
    private static final int INVENTORY_SLOTS = 27;

    public CobblemonChestBoatEntity(@NotNull EntityType<CobblemonChestBoatEntity> entityType, @NotNull Level world) {
        Intrinsics.checkNotNullParameter(entityType, (String)"entityType");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        super(entityType, world);
        this.inventory = this.emptyInventory();
    }

    public CobblemonChestBoatEntity(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        this(CobblemonEntities.CHEST_BOAT, world);
    }

    public CobblemonChestBoatEntity(@NotNull Level world, double x, double y, double z) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        this(CobblemonEntities.CHEST_BOAT, world);
        this.m_6034_(x, y, z);
        this.f_19854_ = x;
        this.f_19855_ = y;
        this.f_19856_ = z;
    }

    public void m_213583_(@NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        player.m_5893_((MenuProvider)this);
        if (!player.m_9236_().f_46443_) {
            this.m_146852_(GameEvent.f_157803_, (Entity)player);
            PiglinAi.m_34873_((Player)player, (boolean)true);
        }
    }

    public void m_6211_() {
        this.m_219953_();
    }

    public int m_6643_() {
        return 27;
    }

    @NotNull
    public ItemStack m_8020_(int slot) {
        ItemStack itemStack = this.m_219947_(slot);
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"this.getInventoryStack(slot)");
        return itemStack;
    }

    @NotNull
    public ItemStack m_7407_(int slot, int amount) {
        ItemStack itemStack = this.m_219936_(slot, amount);
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"this.removeInventoryStack(slot, amount)");
        return itemStack;
    }

    @NotNull
    public ItemStack m_8016_(int slot) {
        ItemStack itemStack = this.m_219945_(slot);
        Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"this.removeInventoryStack(slot)");
        return itemStack;
    }

    public void m_6836_(int slot, @NotNull ItemStack stack) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        this.m_219940_(slot, stack);
    }

    public void m_6596_() {
    }

    public boolean m_6542_(@NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        return this.m_219954_(player);
    }

    @Nullable
    public AbstractContainerMenu m_7208_(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
        Intrinsics.checkNotNullParameter((Object)playerInventory, (String)"playerInventory");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (this.lootTableId != null && player.m_5833_()) {
            return null;
        }
        this.m_219949_(playerInventory.f_35978_);
        return (AbstractContainerMenu)ChestMenu.m_39237_((int)syncId, (Inventory)playerInventory, (Container)((Container)this));
    }

    @Nullable
    public ResourceLocation m_214142_() {
        return this.lootTableId;
    }

    public void m_214199_(@Nullable ResourceLocation lootTableId) {
        this.lootTableId = lootTableId;
    }

    public long m_213803_() {
        return this.lootTableSeed;
    }

    public void m_214065_(long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
    }

    @NotNull
    public NonNullList<ItemStack> m_213659_() {
        return this.inventory;
    }

    public void m_213775_() {
        this.inventory = this.emptyInventory();
    }

    @Override
    @NotNull
    public Item m_38369_() {
        return this.getBoatType().getChestBoatItem();
    }

    private final NonNullList<ItemStack> emptyInventory() {
        NonNullList nonNullList = NonNullList.m_122780_((int)27, (Object)ItemStack.f_41583_);
        Intrinsics.checkNotNullExpressionValue((Object)nonNullList, (String)"ofSize(INVENTORY_SLOTS, ItemStack.EMPTY)");
        return nonNullList;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/entity/boat/CobblemonChestBoatEntity$Companion;", "", "", "INVENTORY_SLOTS", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

