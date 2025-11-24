/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemNameBlockItem
 *  net.minecraft.world.level.block.Block
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/item/BerryItem;", "Lnet/minecraft/world/item/ItemNameBlockItem;", "Lcom/cobblemon/mod/common/api/berry/Berry;", "berry", "()Lcom/cobblemon/mod/common/api/berry/Berry;", "Lcom/cobblemon/mod/common/block/BerryBlock;", "berryBlock", "Lcom/cobblemon/mod/common/block/BerryBlock;", "<init>", "(Lcom/cobblemon/mod/common/block/BerryBlock;)V", "common"})
public class BerryItem
extends ItemNameBlockItem {
    @NotNull
    private final BerryBlock berryBlock;

    public BerryItem(@NotNull BerryBlock berryBlock) {
        Intrinsics.checkNotNullParameter((Object)berryBlock, (String)"berryBlock");
        super((Block)berryBlock, new Item.Properties());
        this.berryBlock = berryBlock;
    }

    @Nullable
    public final Berry berry() {
        return this.berryBlock.berry();
    }
}

