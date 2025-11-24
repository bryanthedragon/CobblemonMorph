/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.ItemNameBlockItem
 *  net.minecraft.world.item.context.BlockPlaceContext
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornBlock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/item/ApricornItem;", "Lnet/minecraft/world/item/ItemNameBlockItem;", "Lnet/minecraft/world/item/context/BlockPlaceContext;", "context", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "", "canPlace", "(Lnet/minecraft/world/item/context/BlockPlaceContext;Lnet/minecraft/world/level/block/state/BlockState;)Z", "Lcom/cobblemon/mod/common/block/ApricornBlock;", "block", "<init>", "(Lcom/cobblemon/mod/common/block/ApricornBlock;)V", "common"})
public final class ApricornItem
extends ItemNameBlockItem {
    public ApricornItem(@NotNull ApricornBlock block) {
        Intrinsics.checkNotNullParameter((Object)block, (String)"block");
        super((Block)block, new Item.Properties());
    }

    protected boolean m_40610_(@NotNull BlockPlaceContext context, @NotNull BlockState state) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Player player = context.m_43723_();
        return !(player != null ? !player.m_7500_() : false) && super.m_40610_(context, state);
    }
}

