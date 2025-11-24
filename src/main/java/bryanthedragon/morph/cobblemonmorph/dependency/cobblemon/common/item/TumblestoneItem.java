/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.Item$Properties
 *  net.minecraft.world.item.context.UseOnContext
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.DirectionalBlock
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/item/TumblestoneItem;", "Lnet/minecraft/world/item/Item;", "Lnet/minecraft/world/item/context/UseOnContext;", "context", "Lnet/minecraft/world/InteractionResult;", "useOnBlock", "(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/world/level/block/Block;", "block", "Lnet/minecraft/world/level/block/Block;", "getBlock", "()Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/item/Item$Settings;", "settings", "<init>", "(Lnet/minecraft/world/item/Item$Properties;Lnet/minecraft/world/level/block/Block;)V", "common"})
public final class TumblestoneItem
extends Item {
    @NotNull
    private final Block block;

    public TumblestoneItem(@NotNull Item.Properties settings, @NotNull Block block) {
        Intrinsics.checkNotNullParameter((Object)settings, (String)"settings");
        Intrinsics.checkNotNullParameter((Object)block, (String)"block");
        super(settings);
        this.block = block;
    }

    @NotNull
    public final Block getBlock() {
        return this.block;
    }

    @NotNull
    public InteractionResult m_6225_(@NotNull UseOnContext context) {
        Direction direction;
        BlockPos pos;
        Level world;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (context.m_43723_() == null) {
            return InteractionResult.FAIL;
        }
        BlockState state = context.m_43725_().m_8055_(context.m_8083_());
        if (state.m_60783_((BlockGetter)(world = context.m_43725_()), pos = context.m_8083_(), direction = context.m_43719_())) {
            if (!world.m_8055_(pos.m_121945_(direction)).m_60795_()) {
                return InteractionResult.FAIL;
            }
            Player player = context.m_43723_();
            Intrinsics.checkNotNull((Object)player);
            if (!player.m_7500_()) {
                context.m_43722_().m_41774_(1);
            }
            world.m_46597_(pos.m_121945_(direction), (BlockState)this.block.m_49966_().m_61124_((Property)DirectionalBlock.f_52588_, (Comparable)direction));
            world.m_247517_(null, pos, CobblemonSounds.TUMBLESTONE_PLACE, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}

