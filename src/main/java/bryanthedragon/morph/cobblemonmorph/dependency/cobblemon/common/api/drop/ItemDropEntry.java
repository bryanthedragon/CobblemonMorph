/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropMethod;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b-\u0010.J3\u0010\u000b\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\u00020\u00128\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0018\u001a\u00020\u00178\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001c\u0010\u001d\u001a\u0004\u0018\u00010\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u001a\u0010\"\u001a\u00020!8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u00178\u0016X\u0096D\u00a2\u0006\f\n\u0004\b&\u0010\u0019\u001a\u0004\b'\u0010\u001bR\u001c\u0010)\u001a\u0004\u0018\u00010(8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/api/drop/ItemDropEntry;", "Lcom/cobblemon/mod/common/api/drop/DropEntry;", "Lnet/minecraft/world/entity/LivingEntity;", "entity", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/phys/Vec3;", "pos", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "drop", "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/cobblemon/mod/common/api/drop/ItemDropMethod;", "dropMethod", "Lcom/cobblemon/mod/common/api/drop/ItemDropMethod;", "getDropMethod", "()Lcom/cobblemon/mod/common/api/drop/ItemDropMethod;", "Lnet/minecraft/resources/ResourceLocation;", "item", "Lnet/minecraft/resources/ResourceLocation;", "getItem", "()Lnet/minecraft/resources/ResourceLocation;", "", "maxSelectableTimes", "I", "getMaxSelectableTimes", "()I", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "Lnet/minecraft/nbt/CompoundTag;", "getNbt", "()Lnet/minecraft/nbt/CompoundTag;", "", "percentage", "F", "getPercentage", "()F", "quantity", "getQuantity", "Lkotlin/ranges/IntRange;", "quantityRange", "Lkotlin/ranges/IntRange;", "getQuantityRange", "()Lkotlin/ranges/IntRange;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nItemDropEntry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ItemDropEntry.kt\ncom/cobblemon/mod/common/api/drop/ItemDropEntry\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"})
public class ItemDropEntry
implements DropEntry {
    private final float percentage;
    private final int quantity;
    @Nullable
    private final IntRange quantityRange;
    private final int maxSelectableTimes;
    @Nullable
    private final ItemDropMethod dropMethod;
    @NotNull
    private final ResourceLocation item = new ResourceLocation("minecraft:fish");
    @Nullable
    private final CompoundTag nbt;

    public ItemDropEntry() {
        this.percentage = 100.0f;
        this.quantity = 1;
        this.maxSelectableTimes = 1;
    }

    @Override
    public float getPercentage() {
        return this.percentage;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Nullable
    public IntRange getQuantityRange() {
        return this.quantityRange;
    }

    @Override
    public int getMaxSelectableTimes() {
        return this.maxSelectableTimes;
    }

    @Nullable
    public ItemDropMethod getDropMethod() {
        return this.dropMethod;
    }

    @NotNull
    public ResourceLocation getItem() {
        return this.item;
    }

    @Nullable
    public CompoundTag getNbt() {
        return this.nbt;
    }

    @Override
    public void drop(@Nullable LivingEntity entity2, @NotNull ServerLevel world, @NotNull Vec3 pos, @Nullable ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Item item = (Item)world.m_9598_().m_175515_(Registries.f_256913_).m_7745_(this.getItem());
        if (item == null) {
            Cobblemon.INSTANCE.getLOGGER().error("Unable to load drop item: " + this.getItem());
            return;
        }
        Item item2 = item;
        IntRange intRange = this.getQuantityRange();
        ItemStack stack = new ItemStack((ItemLike)item2, intRange != null ? RangesKt.random((IntRange)intRange, (Random)((Random)Random.Default)) : this.getQuantity());
        boolean inLava = Intrinsics.areEqual((Object)world.m_8055_(Vec3ExtensionsKt.toBlockPos(pos)).m_60734_(), (Object)Blocks.f_49991_);
        ItemDropMethod itemDropMethod = this.getDropMethod();
        if (itemDropMethod == null) {
            itemDropMethod = Cobblemon.INSTANCE.getConfig().getDefaultDropItemMethod();
        }
        ItemDropMethod it = itemDropMethod;
        boolean bl = false;
        ItemDropMethod dropMethod = inLava ? ItemDropMethod.TO_INVENTORY : it;
        CompoundTag compoundTag = this.getNbt();
        if (compoundTag != null) {
            CompoundTag it2 = compoundTag;
            boolean bl2 = false;
            stack.m_41751_(it2);
        }
        if (dropMethod == ItemDropMethod.ON_PLAYER && player != null) {
            world.m_7967_((Entity)new ItemEntity(player.m_9236_(), player.m_20185_(), player.m_20186_(), player.m_20189_(), stack));
        } else if (dropMethod == ItemDropMethod.TO_INVENTORY && player != null) {
            Component name = stack.m_41786_();
            int count = stack.m_41613_();
            boolean succeeded = player.m_36356_(stack);
            if (Cobblemon.INSTANCE.getConfig().getAnnounceDropItems()) {
                MutableComponent mutableComponent;
                if (succeeded) {
                    Object[] objectArray = new Object[2];
                    objectArray[0] = count;
                    MutableComponent mutableComponent2 = name.m_6881_();
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"name.copy()");
                    objectArray[1] = TextKt.green(mutableComponent2);
                    mutableComponent = LocalizationUtilsKt.lang("drop.item.inventory", objectArray);
                } else {
                    Object[] objectArray = new Object[1];
                    Intrinsics.checkNotNullExpressionValue((Object)name, (String)"name");
                    objectArray[0] = name;
                    MutableComponent mutableComponent3 = LocalizationUtilsKt.lang("drop.item.full", objectArray);
                    Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"lang(\"drop.item.full\", name)");
                    mutableComponent = TextKt.red(mutableComponent3);
                }
                player.m_213846_((Component)mutableComponent);
            }
        } else if (dropMethod == ItemDropMethod.ON_ENTITY && entity2 != null) {
            world.m_7967_((Entity)new ItemEntity(entity2.m_9236_(), entity2.m_20185_(), entity2.m_20186_(), entity2.m_20189_(), stack));
        } else {
            world.m_7967_((Entity)new ItemEntity((Level)world, pos.f_82479_, pos.f_82480_, pos.f_82481_, stack));
        }
    }
}

