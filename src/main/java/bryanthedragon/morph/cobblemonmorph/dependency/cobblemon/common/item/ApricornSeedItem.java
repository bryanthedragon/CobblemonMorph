/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemNameBlockItem
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornSaplingBlock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/item/ApricornSeedItem;", "Lnet/minecraft/world/item/ItemNameBlockItem;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "context", "Lnet/minecraft/world/level/block/state/BlockState;", "getPlacementState", "(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;", "Lcom/cobblemon/mod/common/block/ApricornBlock;", "apricornBlock", "Lcom/cobblemon/mod/common/block/ApricornBlock;", "getApricornBlock", "()Lcom/cobblemon/mod/common/block/ApricornBlock;", "Lcom/cobblemon/mod/common/block/ApricornSaplingBlock;", "block", "<init>", "(Lcom/cobblemon/mod/common/block/ApricornSaplingBlock;Lcom/cobblemon/mod/common/block/ApricornBlock;)V", "common"})
public final class ApricornSeedItem
extends ItemNameBlockItem {
    @NotNull
    private final ApricornBlock apricornBlock;

    public ApricornSeedItem(@NotNull ApricornSaplingBlock block, @NotNull ApricornBlock apricornBlock) {
        Intrinsics.checkNotNullParameter((Object)((Object)block), (String)"block");
        Intrinsics.checkNotNullParameter((Object)apricornBlock, (String)"apricornBlock");
        super((Block)block, new Item.Properties());
        this.apricornBlock = apricornBlock;
    }

    @NotNull
    public final ApricornBlock getApricornBlock() {
        return this.apricornBlock;
    }

    @Nullable
    protected BlockState m_5965_(@NotNull BlockPlaceContext context) {
        BlockState apricornState;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (this.apricornBlock.m_245993_(context.m_43725_().m_246046_()) && (apricornState = this.apricornBlock.m_5573_(context)) != null && this.m_40610_(context, apricornState)) {
            return apricornState;
        }
        return super.m_5965_(context);
    }
}

